package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import pe.com.sedapal.agc.dao.ICargoDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.CargoMapper;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.request.CargoOpenRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class CargoDAOImpl implements ICargoDAO {

	private static final Logger logger = LoggerFactory.getLogger(CargoDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Autowired
	private CargoMapper cargoMapper;

	@Override
	public List<Cargo> listarCargo(String codigoTipoCargo) {
		List<Cargo> listaCargo = new ArrayList<>();
		try {
			Map<String, Object> respuestaConsulta = null;
			Integer resultadoEjecucion = 0;
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_LISTAR_CARGO).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_TIPO_CARGO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_LISTA_CARGOS", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource parametros = new MapSqlParameterSource().addValue("I_V_TIPO_CARGO", codigoTipoCargo);

			respuestaConsulta = this.jdbcCall.execute(parametros);
			resultadoEjecucion = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaCargo = CargoMapper.mapRows(respuestaConsulta);
			} else {
				Integer codigoErrorBD = ((BigDecimal) respuestaConsulta.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaConsulta.get("O_V_EJEC");
				logger.error("Error al obtener Cargos.", mensajeErrorBD);
				throw new AgcException(Constantes.Mensajes.ERROR_BD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (Exception e) {
			logger.error("Error al obtener Cargos.", e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD,
					new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage()));
		}
		return listaCargo;
	}

	@Override
	public ListaPaginada<Cargo> consultarListaCargosOpen(CargoOpenRequest request) {
		Map<String, Object> respuestaBd = new HashMap<>();
		ListaPaginada<Cargo> paginaCargos = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANT_CARGO_OPEN)
					.withProcedureName(DbConstants.PRC_CONSULTAR_CARGOS).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_DESCRIPCION", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTADO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_RESULTADO", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("I_V_DESCRIPCION", request.getDescripcion())
					.addValue("I_V_ESTADO", request.getEstado() != null ? request.getEstado().getId() : null);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOperacion == 1) {
				paginaCargos = cargoMapper.mapperConsultaCargosOpen(respuestaBd);
			} else {
				logger.info(respuestaBd.toString());
				logger.info("Error en consulta de cargos open", CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBd);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return paginaCargos;
	}

	@Override
	public List<Actividad> consultarActividadesDisponibles(String codCargo) {
		Map<String, Object> respuestaBd = new HashMap<>();
		List<Actividad> listaActividades = new ArrayList<>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANT_CARGO_OPEN)
					.withProcedureName(DbConstants.PRC_CONSULTAR_ACTIV_DISPONIBLES)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_CODCARGO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_RESULTADO", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_V_CODCARGO", codCargo);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOperacion == 1) {
				listaActividades = Actividad.mapperActividadBD(respuestaBd);
			} else {
				logger.info(respuestaBd.toString());
				logger.info("Error en consulta de actividades disponibles",
						CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBd);
			}

		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return listaActividades;
	}

	@Override
	public Integer actualizarActividadesCargo(Cargo cargoRequest, String usuario) {
		Map<String, Object> respuestaBd = new HashMap<>();
		Integer resultadoOperacion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANT_CARGO_OPEN)
					.withProcedureName(DbConstants.PRC_ACTUALIZAR_ACTIVIDAD_CARGO)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_CODCARGO", OracleTypes.VARCHAR),
							new SqlParameter("I_TAB_COD_ACTIVIDADES", OracleTypes.ARRAY),
							new SqlParameter("I_V_USUARIO_MOD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			
			List<String> listaActividad = new ArrayList<>();
			cargoRequest.getActividades().stream().forEach(e -> listaActividad.add(e.getCodigo()));

			Connection conn = this.jdbcCall.getJdbcTemplate().getDataSource().getConnection()
					.unwrap(OracleConnection.class);
			Array array = ((OracleConnection) conn).createOracleArray("AGC.TYPE_AGC_TAB_COD_ACTIVIDAD",
					listaActividad.toArray());
			
			MapSqlParameterSource params = new MapSqlParameterSource()
					.addValue("I_V_CODCARGO", cargoRequest.getCodigo())
					.addValue("I_TAB_COD_ACTIVIDADES", array)
					.addValue("I_V_USUARIO_MOD", usuario);
			
			respuestaBd = this.jdbcCall.execute(params);
			resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");
			if (resultadoOperacion != 1) {
				logger.info(respuestaBd.toString());
				logger.info("Error al actualizar actividades de cargo",
						CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBd);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return resultadoOperacion;
	}

}
