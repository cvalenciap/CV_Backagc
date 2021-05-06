package pe.com.sedapal.agc.dao.impl;

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
import pe.com.sedapal.agc.dao.IMovimientoDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.MovimientoMapper;
import pe.com.sedapal.agc.mapper.PersonalContratistaMapper;
import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.request.HistoricoPersonalRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class MovimientoDAOImpl implements IMovimientoDAO {

	private static final Logger logger = LoggerFactory.getLogger(MovimientoDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Autowired
	private PersonalContratistaMapper mapper;
	
	@Autowired
	private MovimientoMapper movimientoMapper;

	@Override
	public ListaPaginada<PersonaContratista> listarMovimientoPersonal(HistoricoPersonalRequest request, Integer pagina,
			Integer registros) {
		ListaPaginada<PersonaContratista> paginaHistorico = new ListaPaginada<>();
		Map<String, Object> respuestaBd = new HashMap<String, Object>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MOVIMIENTO_PERSONA)
					.withProcedureName(DbConstants.PRC_CONSULTA_HISTORICO_PERSONAL)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_DNI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NOMBRES", OracleTypes.VARCHAR),
							new SqlParameter("I_V_APE_PATERNO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_APE_MATERNO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_EST_CONTRAT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_COD_CONTRAT", OracleTypes.NUMBER),
							new SqlParameter("I_V_EST_LABORAL", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_INICIO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_FIN", OracleTypes.VARCHAR),
							new SqlParameter("I_N_PAGINA", OracleTypes.NUMBER),
							new SqlParameter("I_N_REGISTROS", OracleTypes.NUMBER),
							new SqlOutParameter("O_C_RESULTADO", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_V_DNI", request.getNumeroDocumento())
					.addValue("I_V_NOMBRES", request.getNombres())
					.addValue("I_V_APE_PATERNO", request.getApellidoPaterno())
					.addValue("I_V_APE_MATERNO", request.getApellidoMaterno())
					.addValue("I_V_EST_CONTRAT",
							request.getEstadoEmpresa() == null ? null : request.getEstadoEmpresa().getId())
					.addValue("I_N_COD_CONTRAT", request.getEmpresa() == null ? 0 : request.getEmpresa().getCodigo())
					.addValue("I_V_EST_LABORAL",
							request.getEstadoLaboral() == null ? null : request.getEstadoLaboral().getId())
					.addValue("I_V_FEC_INICIO", request.getFechaDesde())
					.addValue("I_V_FEC_FIN", request.getFechaHasta()).addValue("I_N_PAGINA", pagina)
					.addValue("I_N_REGISTROS", registros);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOperacion == 1) {
				List<PersonaContratista> lista = mapper.mapearRespuestaBd(respuestaBd);
				if (!lista.isEmpty()) {
					paginaHistorico.setLista(lista);
					paginaHistorico.setPagina(pagina);
					paginaHistorico.setRegistros(registros);
					paginaHistorico.setTotalRegistros(mapper.obtenerTotalregistros(respuestaBd));
				} else {
					paginaHistorico.setLista(lista);
					paginaHistorico.setPagina(pagina);
					paginaHistorico.setRegistros(registros);
					paginaHistorico.setTotalRegistros(0);
				}
			} else {
				logger.info(respuestaBd.toString());
				logger.info("Error en consulta de historico personal",
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
		return paginaHistorico;
	}

	@Override
	public ListaPaginada<Movimiento> listarMovimientoCargos(HistoricoPersonalRequest request, Integer pagina,
			Integer registros) {
		ListaPaginada<Movimiento> paginaHistorico = new ListaPaginada<>();
		Map<String, Object> respuestaBd = new HashMap<String, Object>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MOVIMIENTO_PERSONA)
					.withProcedureName(DbConstants.PRC_CONSULTA_HISTORICO_MOVIMIENTO)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(
							new SqlParameter("I_V_DNI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NOMBRES", OracleTypes.VARCHAR),
							new SqlParameter("I_V_APE_PATERNO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_APE_MATERNO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_EST_CONTRAT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_COD_CONTRAT", OracleTypes.NUMBER),
							new SqlParameter("I_V_EST_CARGO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_INICIO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_FIN", OracleTypes.VARCHAR),
							new SqlParameter("I_N_PAGINA", OracleTypes.NUMBER),
							new SqlParameter("I_N_REGISTROS", OracleTypes.NUMBER),
							new SqlOutParameter("O_C_RESULTADO", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("I_V_DNI", request.getNumeroDocumento())
					.addValue("I_V_NOMBRES", request.getNombres())
					.addValue("I_V_APE_PATERNO", request.getApellidoPaterno())
					.addValue("I_V_APE_MATERNO", request.getApellidoMaterno())
					.addValue("I_V_EST_CONTRAT",
							request.getEstadoEmpresa() == null ? null : request.getEstadoEmpresa().getId())
					.addValue("I_N_COD_CONTRAT", request.getEmpresa() == null ? 0 : request.getEmpresa().getCodigo())
					.addValue("I_V_EST_CARGO",
							request.getEstadoCargo() == null ? null : request.getEstadoCargo().getId())
					.addValue("I_V_FEC_INICIO", request.getFechaDesde())
					.addValue("I_V_FEC_FIN", request.getFechaHasta())
					.addValue("I_N_PAGINA", pagina)
					.addValue("I_N_REGISTROS", registros);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOperacion == 1) {
				List<Movimiento> lista = movimientoMapper.mapearRespuestaBd(respuestaBd);
				if (!lista.isEmpty()) {
					paginaHistorico.setLista(lista);
					paginaHistorico.setPagina(pagina);
					paginaHistorico.setRegistros(registros);
					paginaHistorico.setTotalRegistros(movimientoMapper.obtenerTotalregistros(respuestaBd));
				} else {
					paginaHistorico.setLista(lista);
					paginaHistorico.setPagina(pagina);
					paginaHistorico.setRegistros(registros);
					paginaHistorico.setTotalRegistros(0);
				}
			} else {
				logger.info(respuestaBd.toString());
				logger.info("Error en consulta de historico personal",
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
		return paginaHistorico;
	}

}
