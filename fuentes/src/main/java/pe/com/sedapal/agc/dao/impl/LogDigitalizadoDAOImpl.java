package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.ILogDigitalizadoDAO;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.LogDigitalizado;
import pe.com.sedapal.agc.model.request.DigitalizadoLogRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class LogDigitalizadoDAOImpl implements ILogDigitalizadoDAO {
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);	
	private Error error;
	private Paginacion paginacion;

	@Override
	public List<LogDigitalizado> listarLogDigitalizados(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina,
			Integer registros) {
		this.error = null;	
		this.paginacion = new Paginacion();
		this.paginacion.setPagina(pagina);
		this.paginacion.setRegistros(registros);
		List<LogDigitalizado> listaLogDigitalizados = new ArrayList<LogDigitalizado>();
		Map<String, Object> out = null;
		
		try {		
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_LOG_DIGITALIZADO)
					.withProcedureName(DbConstants.PRC_LIST_ACCI_LOG_DIG)
					.declareParameters(new SqlParameter("V_N_SUMINISTRO", OracleTypes.NUMBER),
									   new SqlParameter("V_V_USUARIO", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ACCION", OracleTypes.VARCHAR),
									   new SqlParameter("V_D_FECINI", OracleTypes.DATE),
									   new SqlParameter("V_D_FECFIN", OracleTypes.DATE),
									   new SqlParameter("V_N_PAGINA", OracleTypes.NUMBER),
									   new SqlParameter("V_N_REGISTROS", OracleTypes.NUMBER),
									   new SqlParameter("V_V_EXTENSION", OracleTypes.VARCHAR),
									   new SqlOutParameter("C_LISTA_LOG_DIG", OracleTypes.CURSOR),
									   new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
									   new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
									   new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SUMINISTRO", requestLogDigitalizado.getSuministro())
					.addValue("V_V_USUARIO", requestLogDigitalizado.getUsuario())
					.addValue("V_V_ACCION", requestLogDigitalizado.getTipoAccion())
					.addValue("V_D_FECINI", requestLogDigitalizado.getFechaInicio())
					.addValue("V_D_FECFIN", requestLogDigitalizado.getFechaFin())
					.addValue("V_N_PAGINA", pagina)
					.addValue("V_N_REGISTROS", registros)
					.addValue("V_V_EXTENSION",  requestLogDigitalizado.getTipoArchivo()) ;
			
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				listaLogDigitalizados = mapeaLogDigitalizados(out);
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: LogDigitalizadoDAOImpl - listarLogDigitalizados()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: LogDigitalizadoDAOImpl - listarLogDigitalizados()] - "+e.getMessage());
		}	
		
		return listaLogDigitalizados;
	}
	
	private List<LogDigitalizado> mapeaLogDigitalizados(Map<String, Object> resultados) {
		LogDigitalizado item;
		List<LogDigitalizado> listaLogDigitalizados = new ArrayList<LogDigitalizado>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_LISTA_LOG_DIG");
		for (Map<String, Object> map : lista) {
			item = new LogDigitalizado();		
			item.setIdLog(((BigDecimal)map.get("N_IDLOG")).intValue());
			item.setSuministro(((BigDecimal)map.get("V_IDNIS")).intValue());
			Actividad actividad = new Actividad();
			actividad.setCodigo((String) map.get("V_IDACTI"));
			actividad.setDescripcion((String) map.get("V_DESCACTI"));
			item.setActividad(actividad);
			item.setOrdTrabOrdServCedu((String) map.get("V_OT_OS_CED"));
			item.setTipologia((String) map.get("V_TIPO_OT_OS"));
			Timestamp fecAccion = (Timestamp) map.get("V_D_USUACCI");	
			item.setFechaHoraAccion(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecAccion));
			item.setIpAccion((String) map.get("V_C_IPACCI"));
			item.setUsuarioAccion((String) map.get("V_C_USUACCI"));
			item.setTipoAccion((String) map.get("V_C_TIPACCI"));
			item.setTipoArchivo((String) map.get("V_EXTENSION"));
						
			if (map.get("RESULT_COUNT") != null) {				
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("RESULT_COUNT")).intValue());
			}
			listaLogDigitalizados.add(item);
		}
		return listaLogDigitalizados;
	}

	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}

	@Override
	public Error getError() {
		return this.error;
	}

	@Override
	public Integer registrarAccionLog(VisorDigitalizadoRequest request) {
		this.error = null;	
		Integer resultado = 0;
		Map<String, Object> out = null;
		try {		
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_LOG_DIGITALIZADO)
					.withProcedureName(DbConstants.PRC_REG_ACCI_LOG)
					.declareParameters(new SqlParameter("V_N_SUMINISTRO", OracleTypes.NUMBER),
									   new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_OT_OS_CED", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_TIPO_OT_OS", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_IPACCI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_USUACCI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_TIPACCI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_EXTENSION", OracleTypes.VARCHAR), 
									   new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
									   new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
									   new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SUMINISTRO", request.getSuministro())
					.addValue("V_V_IDACTI", request.getActividad())
					.addValue("V_V_OT_OS_CED", request.getOrdTrabOrdServCedu())
					.addValue("V_V_TIPO_OT_OS", request.getTipologia())
					.addValue("V_V_IPACCI", request.getIp())
					.addValue("V_V_USUACCI", request.getUsuario())
					.addValue("V_V_TIPACCI", request.getAccion())
					.addValue("V_V_EXTENSION", request.getTipoArchivo());
			
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();			
			if (resultado != 1) {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: LogDigitalizadoDAOImpl - registrarAccionLog()] - " + mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: LogDigitalizadoDAOImpl - registrarAccionLog()] - " + e.getMessage());
		}			
		return resultado;
	}

}
