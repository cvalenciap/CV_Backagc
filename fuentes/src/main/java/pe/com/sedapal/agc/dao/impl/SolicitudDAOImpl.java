package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import pe.com.sedapal.agc.dao.ISolicitudDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.SolicitudMapper;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Solicitud;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class SolicitudDAOImpl implements ISolicitudDAO {

	private static final Logger logger = LoggerFactory.getLogger(SolicitudDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Override
	public List<Solicitud> listarSolicitudCambioCargo(Integer codigoEmpleado) {
		List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_LIST_SOLI_CAMB_CARG)
					.declareParameters(new SqlParameter("I_N_COD_SED", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_LISTA_SOLICITUDES", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_COD_SED", codigoEmpleado);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaSolicitud = SolicitudMapper.mapRowsCambioCargo(out);
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener solicitudes de Cambio de Cargo."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al obtener solicitudes de Cambio de Cargo."));
		}
		return listaSolicitud;
	}

	@Override
	public void registrarSolicitudCambioCargo(Solicitud solicitud) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_INS_SOLI_CAMB_CARG)
					.declareParameters(new SqlParameter("I_N_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_TIPSOLI", OracleTypes.NUMBER),
							new SqlParameter("I_D_FECSOLI", OracleTypes.DATE),
							new SqlParameter("I_V_DESCSOLI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTASOLI", OracleTypes.VARCHAR),							
							new SqlParameter("I_N_CODMOTSOLI", OracleTypes.NUMBER),
							new SqlParameter("I_V_CODCARGACT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_CODOFICACT", OracleTypes.NUMBER),
							new SqlParameter("I_N_IDITEMACT", OracleTypes.NUMBER),
							new SqlParameter("I_N_CODOFICDEST", OracleTypes.NUMBER),
							new SqlParameter("I_V_CODCARGDEST", OracleTypes.VARCHAR),
							new SqlParameter("I_N_IDITEMDEST", OracleTypes.NUMBER),							
							new SqlParameter("I_V_NUM_DNI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NOMB", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_PAT", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_MAT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ID_EMPR", OracleTypes.NUMBER),
							new SqlParameter("I_V_USUCRE", OracleTypes.VARCHAR),							
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_EMP", solicitud.getPersonal().getCodigoEmpleado())
					.addValue("I_N_TIPSOLI", solicitud.getTipoSolicitud())
					.addValue("I_D_FECSOLI", formatter.parse(solicitud.getFechaSolicitud()))
					.addValue("I_V_DESCSOLI", solicitud.getDescripcionSolicitud())
					.addValue("I_V_ESTASOLI", solicitud.getEstadoSolicitud().getId())					
					.addValue("I_N_CODMOTSOLI", solicitud.getMotivoSolicitud().getCodigo())
					.addValue("I_V_CODCARGACT", solicitud.getCargoActual().getId())
					.addValue("I_N_CODOFICACT", solicitud.getOficinaActual().getCodigo())
					.addValue("I_N_IDITEMACT", solicitud.getItemActual().getId())
					.addValue("I_N_CODOFICDEST", solicitud.getOficinaDestino().getCodigo())
					.addValue("I_V_CODCARGDEST", solicitud.getCargoDestino().getId())
					.addValue("I_N_IDITEMDEST", solicitud.getItemDestino().getId())									
					.addValue("I_V_NUM_DNI", solicitud.getPersonal().getNumeroDocumento())
					.addValue("I_V_NOMB", solicitud.getPersonal().getNombres())
					.addValue("I_V_AP_PAT", solicitud.getPersonal().getApellidoPaterno())
					.addValue("I_V_AP_MAT", solicitud.getPersonal().getApellidoMaterno())
					.addValue("I_N_ID_EMPR", solicitud.getPersonal().getContratista().getCodigo())
					.addValue("I_V_USUCRE", solicitud.getUsuarioCreacion());

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {				
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			
			if (e.getError().getCodigo() == 10000) {
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, Constantes.RegistroSolicitudPersonal.MSE001));
			} else {
				logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, "Error al registrar solicitud para Cambio de Cargo."));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al registrar solicitud para Cambio de Cargo."));
		}
	}

	@Override
	public List<Solicitud> listarSolicitudMovimiento(Integer codigoEmpleado) {
		List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_LIST_SOLI_MOVI)
					.declareParameters(new SqlParameter("I_N_COD_SED", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_LISTA_SOLICITUDES", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_COD_SED", codigoEmpleado);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaSolicitud = SolicitudMapper.mapRowsMovimientos(out);
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener solicitudes de Movimiento de Personal."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al obtener solicitudes de Movimiento de Personal."));
		}
		return listaSolicitud;
	}

	@Override
	public List<Oficina> listarOficinaItem(Integer idEmpresa, Integer idItem) {
		List<Oficina> listaOficina = new ArrayList<Oficina>();
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_LISTAR_OFICINA_ITEM)
					.declareParameters(new SqlParameter("I_N_COD_ITEM", OracleTypes.NUMBER),
							new SqlParameter("I_N_COD_CONTRATISTA", OracleTypes.NUMBER),
							new SqlOutParameter("O_C_LISTA_OFICINA_ITEM", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_ITEM", idItem)
					.addValue("I_N_COD_CONTRATISTA", idEmpresa);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaOficina = SolicitudMapper.mapRowsOfinaItem(out);
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener oficinas item."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al obtener oficinas item."));
		}
		return listaOficina;
	}

	@Override
	public List<Item> listarItemEmpesa(Integer idEmpresa) {
		List<Item> listaItem = new ArrayList<Item>();
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_LISTAR_ITEM_EMPRESA)
					.declareParameters(new SqlParameter("I_N_COD_CONTRATISTA", OracleTypes.NUMBER),
							new SqlOutParameter("O_C_LISTA_ITEM_EMPRESA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_CONTRATISTA", idEmpresa);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaItem = SolicitudMapper.mapRowsItemEmpresa(out);
			} else {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener item empresa."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al obtener item empresa."));
		}
		return listaItem;
	}

	@Override
	public void registrarSolicitudMovimiento(Solicitud solicitud) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_INS_SOLI_MOVI)
					.declareParameters(new SqlParameter("I_N_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_TIPSOLI", OracleTypes.NUMBER),
							new SqlParameter("I_D_FECSOLI", OracleTypes.DATE),
							new SqlParameter("I_V_DESCSOLI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTASOLI", OracleTypes.VARCHAR),							
							new SqlParameter("I_N_CODMOTSOLI", OracleTypes.NUMBER),
							new SqlParameter("I_V_CODCARGACT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_CODOFICACT", OracleTypes.NUMBER),
							new SqlParameter("I_N_IDITEMACT", OracleTypes.NUMBER),
							new SqlParameter("I_N_CODOFICDEST", OracleTypes.NUMBER),
							new SqlParameter("I_V_CODCARGDEST", OracleTypes.VARCHAR),
							new SqlParameter("I_N_IDITEMDEST", OracleTypes.NUMBER),							
							new SqlParameter("I_V_NUM_DNI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NOMB", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_PAT", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_MAT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ID_EMPR", OracleTypes.NUMBER),
							new SqlParameter("I_V_USUCRE", OracleTypes.VARCHAR),							
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_EMP", solicitud.getPersonal().getCodigoEmpleado())
					.addValue("I_N_TIPSOLI", solicitud.getTipoSolicitud())
					.addValue("I_D_FECSOLI", formatter.parse(solicitud.getFechaSolicitud()))
					.addValue("I_V_DESCSOLI", solicitud.getDescripcionSolicitud())
					.addValue("I_V_ESTASOLI", solicitud.getEstadoSolicitud().getId())					
					.addValue("I_N_CODMOTSOLI", solicitud.getMotivoSolicitud().getCodigo())
					.addValue("I_V_CODCARGACT", solicitud.getCargoActual().getId())
					.addValue("I_N_CODOFICACT", solicitud.getOficinaActual().getCodigo())
					.addValue("I_N_IDITEMACT", solicitud.getItemActual().getId())
					.addValue("I_N_CODOFICDEST", solicitud.getOficinaDestino().getCodigo())
					.addValue("I_V_CODCARGDEST", solicitud.getCargoDestino().getId())
					.addValue("I_N_IDITEMDEST", solicitud.getItemDestino().getId())									
					.addValue("I_V_NUM_DNI", solicitud.getPersonal().getNumeroDocumento())
					.addValue("I_V_NOMB", solicitud.getPersonal().getNombres())
					.addValue("I_V_AP_PAT", solicitud.getPersonal().getApellidoPaterno())
					.addValue("I_V_AP_MAT", solicitud.getPersonal().getApellidoMaterno())
					.addValue("I_N_ID_EMPR", solicitud.getPersonal().getContratista().getCodigo())
					.addValue("I_V_USUCRE", solicitud.getUsuarioCreacion());

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {				
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {			
			if (e.getError().getCodigo() == 10000) {
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, Constantes.RegistroSolicitudPersonal.MSE001));
			} else {
				logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, "Error al registrar solicitud de movimiento de personal."));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al registrar solicitud de movimiento de personal."));
		}		
	}

	@Override
	public void aprobarSolicitud(Solicitud solicitud) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_APROBAR_SOLI)
					.declareParameters(new SqlParameter("I_N_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_IDSOLI", OracleTypes.NUMBER),							
							new SqlParameter("I_V_USUMOD", OracleTypes.VARCHAR),							
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_EMP", solicitud.getCodigoEmpleado())
					.addValue("I_N_IDSOLI", solicitud.getIdSolicitud())					
					.addValue("I_V_USUMOD", solicitud.getUsuarioModificacion());

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {				
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_ACTUALIZAR_BD, e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_BD,
					"Error al aprobar solicitud de cambio de cargo."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al aprobar solicitud de cambio de cargo."));
		}			
	}

	@Override
	public void rechazarSolicitud(Solicitud solicitud) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_SOLICITUD_PERSONA)
					.withProcedureName(DbConstants.PRC_RECHAZAR_SOLI)
					.declareParameters(new SqlParameter("I_N_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_IDSOLI", OracleTypes.NUMBER),	
							new SqlParameter("I_V_OBSRECSOLI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_USUMOD", OracleTypes.VARCHAR),							
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_EMP", solicitud.getCodigoEmpleado())
					.addValue("I_N_IDSOLI", solicitud.getIdSolicitud())		
					.addValue("I_V_OBSRECSOLI", solicitud.getObservacionRechazo())	
					.addValue("I_V_USUMOD", solicitud.getUsuarioModificacion());

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {				
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_ACTUALIZAR_BD, e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_BD,
					"Error al rechazar solicitud de cambio de cargo."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					"Error al rechazar solicitud de cambio de cargo."));
		}
	}

}
