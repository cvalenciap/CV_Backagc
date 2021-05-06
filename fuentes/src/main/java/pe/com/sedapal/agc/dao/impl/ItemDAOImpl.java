package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.com.sedapal.agc.dao.IItemDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.ItemMapper;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.request.ItemRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.MantenimientoItems;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class ItemDAOImpl implements IItemDAO {

	private static final Logger logger = LoggerFactory.getLogger(ItemDAOImpl.class);
	private Error error;
	private Paginacion paginacion;

	@Override
	public Error getError() {
		return this.error;
	}
	
	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}
	
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Override
	public List<Item> listarItemEmpresa(Integer codigoContratista, Integer codigoOficina) {
		List<Item> listaItems = new ArrayList<>();
		try {
			Map<String, Object> respuestaConsulta = null;
			Integer resultadoEjecucion = 0;
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_LISTAR_ITEM_EMPRESA_OFICINA)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(
							new SqlParameter("I_N_COD_CONTRATISTA", OracleTypes.NUMBER),
							new SqlParameter("I_N_COD_OFICINA", OracleTypes.NUMBER),
							new SqlOutParameter("O_C_LISTA_ITEMS_EMPRESA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource parametros = new MapSqlParameterSource()
					.addValue("I_N_COD_CONTRATISTA", codigoContratista)
					.addValue("I_N_COD_OFICINA", codigoOficina);
			
			respuestaConsulta = this.jdbcCall.execute(parametros);
			resultadoEjecucion = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaItems = ItemMapper.mapRows(respuestaConsulta);
			} else {
				Integer codigoErrorBD = ((BigDecimal) respuestaConsulta.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaConsulta.get("O_V_EJEC");
				logger.error("Error al obtener Items.", mensajeErrorBD);
				throw new AgcException(Constantes.Mensajes.ERROR_BD, new Error(codigoErrorBD, mensajeErrorBD));				
			}
		} catch (Exception e) {
			logger.error("Error al obtener Items.", e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD,
					new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage()));
		}
		return listaItems;
	}

	@Override
	public List<Item> listarItem(ItemRequest itemRequest) {
		this.paginacion = new Paginacion();
		this.error = null;
				
		Map<String, Object> out = null;
		List<Item> listaItems = new ArrayList<Item>();
		Integer resultado= 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ITEM)
					.withProcedureName(DbConstants.PRC_LIS_ITEM)
					.declareParameters(
							new SqlParameter("I_V_DES_ITEM", OracleTypes.VARCHAR),
							new SqlParameter("I_V_EST_ITEM", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_LIST_ITEM", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			try {
				SqlParameterSource in = new MapSqlParameterSource()
						.addValue("I_V_DES_ITEM", itemRequest.getDescripcion())
						.addValue("I_V_EST_ITEM", itemRequest.getEstado());

				out = this.jdbcCall.execute(in);
				resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();
			if (resultado == 1) {
				listaItems = ItemMapper.mapRowsItems(out);
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al obtener Items ", mensajeErrorBD);
			}
		} catch (Exception e) {
			this.error = new Error(resultado, e.getMessage());
			logger.error("Error al obtener Items ", e.getMessage());
		}
		return listaItems;
	}

	@Override
	public Integer modificarItem(ItemRequest itemRequest) {
		this.error = null;
		Integer resultado = 0;
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).
				withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_ITEM)
				.withProcedureName(DbConstants.PRC_MOD_ITEM)
				.declareParameters(
						new SqlParameter("I_N_ID_ITEM", OracleTypes.NUMBER),
						new SqlParameter("I_V_DES_ITEM", OracleTypes.VARCHAR),
						new SqlParameter("I_V_EST_ITEM", OracleTypes.VARCHAR),
						new SqlParameter("I_V_USU_MOD", OracleTypes.VARCHAR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_ITEM", itemRequest.getIdItem())
					.addValue("I_V_DES_ITEM", itemRequest.getDescripcion())
					.addValue("I_V_EST_ITEM", itemRequest.getEstado())
					.addValue("I_V_USU_MOD", itemRequest.getUsuarioModificacion());

			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();
			if (resultado == 1) {
				Integer resultadoValidacion = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				if (resultadoValidacion == 1) {
					String mensajeValidacion = (String) out.get("O_V_EJEC");
					this.error = new Error(resultado, mensajeValidacion);
				}
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al modificar Item", mensajeErrorBD);
			}
		} catch (Exception e) {
			this.error = new Error(resultado, e.getMessage());
			logger.error("Error al modificar Item", e.getMessage());
		}
		return resultado;
	}

	@Override
	public Integer agregarItem(ItemRequest itemRequest) {
		this.error = null;
		Integer resultado = 0;
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_ITEM)
				.withProcedureName(DbConstants.PRC_REG_ITEM)
				.declareParameters(
						new SqlParameter("I_V_DES_ITEM", OracleTypes.VARCHAR),
						new SqlParameter("I_V_USU_CREA", OracleTypes.VARCHAR),
						new SqlOutParameter("O_NO_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_V_DES_ITEM", itemRequest.getDescripcion())
					.addValue("I_V_USU_CREA", itemRequest.getUsuarioCreacion());

			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();
			if (resultado == 1) {
				Integer resultadoValidacion = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				if (resultadoValidacion == 1) {
					String mensajeValidacion = (String) out.get("O_V_EJEC");
					this.error = new Error(resultado, mensajeValidacion);
				}
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al registrar item", mensajeErrorBD);
			}
		} catch (Exception e) {
			this.error = new Error(resultado, e.getMessage());
			logger.error("Error al registrar item", e.getMessage());
		}
		return resultado;
	}

	@Override
	public Integer eliminarItem(ItemRequest itemRequest) {
		this.error = null;
		Integer resultado = 0;
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_ITEM)
				.withProcedureName(DbConstants.PRC_ELI_ITEM)
				.declareParameters(
						new SqlParameter("I_N_ID_ITEM", OracleTypes.NUMBER),
						new SqlParameter("I_V_USU_MOD", OracleTypes.VARCHAR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_ITEM", itemRequest.getIdItem())
					.addValue("I_V_USU_MOD", itemRequest.getUsuarioModificacion());

			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();
			if (resultado == 1) {
				Integer resultadoValidacion = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				if (resultadoValidacion == 1) {
					String mensajeValidacion = (String) out.get("O_V_EJEC");
					this.error = new Error(resultado, mensajeValidacion);
				}
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al eliminar item", mensajeErrorBD);
			}
		} catch (Exception e) {
			this.error = new Error(resultado, e.getMessage());
			logger.error("Error al eliminar item", e.getMessage());
		}
		return resultado;
	}

	@Override
	public Map<String, Object> listarItemOficinaMantenimiento(Integer idItem) {

		Map<String, Object> respuestaMapBD = null;
		Map<String, Object> mapItemOficinaMantenimiento = null;
		try {

			Integer resultadoEjecucion = 0;  
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ITEM)
					.withProcedureName(DbConstants.PRC_LIS_ITEM_OFIC)
					.declareParameters(
							new SqlParameter("I_N_ID_ITEM", OracleTypes.INTEGER),
							new SqlOutParameter("C_OUT_LIS_OFIC_DISP", OracleTypes.CURSOR),
							new SqlOutParameter("C_OUT_LIS_OFIC_ITEM", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("I_N_ID_ITEM", idItem);

			respuestaMapBD = this.jdbcCall.execute(paraMap);
			
			resultadoEjecucion = CastUtil.leerValorMapInteger(respuestaMapBD, "O_N_RESP");
			if ( resultadoEjecucion == 1 ) {
				mapItemOficinaMantenimiento = (Map<String, Object>) ItemMapper.mapRowsItemOficina(respuestaMapBD);
			} else {
				Integer codigoErrorBD = ((BigDecimal) respuestaMapBD.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaMapBD.get("O_V_EJEC");
				throw new AgcException(Constantes.Mensajes.ERROR_CONSULTA_BD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener oficinas."));
		} catch (Exception e) {			
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al obtener oficinas."));
		}
		  return mapItemOficinaMantenimiento;
	}

	@Override
	public void registrarItemOficina(String tramaItemOficina) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ITEM)
					.withProcedureName(DbConstants.PRC_REG_ITEM_OFICINA)
					.declareParameters(new SqlParameter("I_V_TRAMA", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_V_TRAMA", tramaItemOficina);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {				
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {			
			logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, MantenimientoItems.MSE001));			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					MantenimientoItems.MSE001));
		}				
	}

}