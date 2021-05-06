package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IDigitalizadoDAO;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class DigitalizadoDAOImpl implements IDigitalizadoDAO {
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);	
	private Error error;
	private Paginacion paginacion;

	@Override
	public List<Digitalizado> listarDigitalizados(DigitalizadoRequest requestDigitalizado, Integer pagina,
			Integer registros) {
		this.error = null;
		this.paginacion = new Paginacion();
		this.paginacion.setPagina(pagina);
		this.paginacion.setRegistros(registros);
		List<Digitalizado> listaDigitalizados = new ArrayList<Digitalizado>();
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
					.withProcedureName(DbConstants.PRC_BUSQ_GENERAL_DIG)
					.declareParameters(new SqlParameter("V_N_SUMINISTRO", OracleTypes.NUMBER),
									   new SqlParameter("V_V_IDCARG", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ID_ACTI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ORD_SER", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ORD_TRA", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_C_CEDULA", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_C_RECLAMO", OracleTypes.VARCHAR),
									   new SqlParameter("V_N_ID_OFIC", OracleTypes.NUMBER),
									   new SqlParameter("V_D_F_INI", OracleTypes.DATE),
									   new SqlParameter("V_D_F_FIN", OracleTypes.DATE),
									   new SqlParameter("V_N_CON_ADJ", OracleTypes.NUMBER),
									   new SqlParameter("V_N_PAGINA", OracleTypes.NUMBER),
									   new SqlParameter("V_N_REGISTROS", OracleTypes.NUMBER),
									   new SqlOutParameter("C_LISTA_DETALLE", OracleTypes.CURSOR),
									   new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
									   new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
									   new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SUMINISTRO", requestDigitalizado.getSuministro())
					.addValue("V_V_IDCARG", requestDigitalizado.getNumeroCarga())
					.addValue("V_V_ID_ACTI", requestDigitalizado.getActividad().getCodigo())
					.addValue("V_V_ORD_SER", requestDigitalizado.getOrdenServicio())
					.addValue("V_V_ORD_TRA", requestDigitalizado.getOrdenTrabajo())
					.addValue("V_V_C_CEDULA", requestDigitalizado.getNumeroCedula())
					.addValue("V_V_C_RECLAMO", requestDigitalizado.getNumeroReclamo())
					.addValue("V_N_ID_OFIC", requestDigitalizado.getOficina().getCodigo())
					.addValue("V_D_F_INI", requestDigitalizado.getFechaInicio())
					.addValue("V_D_F_FIN", requestDigitalizado.getFechaFin())
					.addValue("V_N_CON_ADJ", requestDigitalizado.getDigitalizado())
					.addValue("V_N_PAGINA", pagina)
					.addValue("V_N_REGISTROS", registros)
					;
			
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaDigitalizados = mapeaDigitalizados(out);
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - "+e.getMessage());
		}				
		return listaDigitalizados;
	}
	@Override
	public Map<String, Object> listarDigitalizadosPorActividad(DigitalizadoRequest requestDigitalizado, Integer pagina,
			Integer registros) {
		this.error = null;
		this.paginacion = new Paginacion();
		this.paginacion.setPagina(pagina);
		this.paginacion.setRegistros(registros);
		Map<String, Object> listaDigitalizados = new HashMap<String, Object>();
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
					.withProcedureName(DbConstants.PRC_BUSQ_GENERAL_DIG)
					.declareParameters(new SqlParameter("V_N_SUMINISTRO", OracleTypes.NUMBER),
									   new SqlParameter("V_V_IDCARG", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ID_ACTI", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ORD_SER", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_ORD_TRA", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_C_CEDULA", OracleTypes.VARCHAR),
									   new SqlParameter("V_V_C_RECLAMO", OracleTypes.VARCHAR),
									   new SqlParameter("V_N_ID_OFIC", OracleTypes.NUMBER),
									   new SqlParameter("V_D_F_INI", OracleTypes.DATE),
									   new SqlParameter("V_D_F_FIN", OracleTypes.DATE),
									   new SqlParameter("V_N_CON_ADJ", OracleTypes.NUMBER),
									   new SqlParameter("V_N_PAGINA", OracleTypes.NUMBER),
									   new SqlParameter("V_N_REGISTROS", OracleTypes.NUMBER),
									   new SqlOutParameter("C_LISTA_DETALLE", OracleTypes.CURSOR),
									   new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
									   new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
									   new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SUMINISTRO", requestDigitalizado.getSuministro())
					.addValue("V_V_IDCARG", requestDigitalizado.getNumeroCarga())
					.addValue("V_V_ID_ACTI", requestDigitalizado.getActividad().getCodigo())
					.addValue("V_V_ORD_SER", requestDigitalizado.getOrdenServicio())
					.addValue("V_V_ORD_TRA", requestDigitalizado.getOrdenTrabajo())
					.addValue("V_V_C_CEDULA", requestDigitalizado.getNumeroCedula())
					.addValue("V_V_C_RECLAMO", requestDigitalizado.getNumeroReclamo())
					.addValue("V_N_ID_OFIC", requestDigitalizado.getOficina().getCodigo())
					.addValue("V_D_F_INI", requestDigitalizado.getFechaInicio())
					.addValue("V_D_F_FIN", requestDigitalizado.getFechaFin())
					.addValue("V_N_CON_ADJ", requestDigitalizado.getDigitalizado())
					.addValue("V_N_PAGINA", pagina)
					.addValue("V_N_REGISTROS", registros);
			
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaDigitalizados = mapeaDigitalizadosPorActividad(out);
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - "+e.getMessage());
		}				
		return listaDigitalizados;
	}
	
	private List<Digitalizado> mapeaDigitalizados(Map<String, Object> resultados) {
		Digitalizado item;
		List<Digitalizado> listaDigitalizados = new ArrayList<Digitalizado>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_LISTA_DETALLE");
		for (Map<String, Object> map : lista) {
			item = new Digitalizado();
			if(map.get("NIS_RAD") != null && !map.get("NIS_RAD").equals(" ")) {
			    item.setSuministro(Integer.parseInt((String) map.get("NIS_RAD")));
			}
			Actividad actividad = new Actividad();
			actividad.setCodigo((String) map.get("V_IDACTI"));
			actividad.setDescripcion((String) map.get("V_DESCACTI"));
			item.setActividad(actividad);			
			if(map.get("V_NUM_SEDA") != null && !map.get("V_NUM_SEDA").equals(" ")) {
				item.setOrdTrabOrdServCedu((String) map.get("V_NUM_SEDA"));	
			}
			if(map.get("V_TIPO_OSOT") != null && !map.get("V_TIPO_OSOT").equals(" ")) {
				item.setTipologia((String) map.get("V_TIPO_OSOT"));	
			}
			if(map.get("V_ID_CARG") != null && !map.get("V_ID_CARG").equals(" ")) {
				item.setNumeroCarga((String) map.get("V_ID_CARG"));	
			}		
			if(map.get("V_ID_CARG_DET") != null && !map.get("V_ID_CARG_DET").equals(" ")) {
				item.setIdCargaDetalle(((BigDecimal) map.get("V_ID_CARG_DET")).intValue());
			}			
			if(map.get("V_FEC_EJE") != null && !map.get("V_FEC_EJE").equals(" ")) {
				Timestamp fecEje = (Timestamp) map.get("V_FEC_EJE");			
				item.setFechaEjecucion(new SimpleDateFormat("dd/MM/yyyy").format(fecEje));				
			}
			if(map.get("V_N_OT")!=null && !map.get("V_N_OT").equals(" ")) {
				item.setNumeroOT((String)map.get("V_N_OT"));	
			}
			if(map.get("V_IN_DIG")!=null && !map.get("V_IN_DIG").equals(" ")) {
				item.setCantAdj(((BigDecimal) map.get("V_IN_DIG")).intValue());	
			}
			if(map.get("V_IN_DIG_JPG")!=null && !map.get("V_IN_DIG_JPG").equals(" ")) {
				item.setCantImg(((BigDecimal) map.get("V_IN_DIG_JPG")).intValue());	
			}
			if (map.get("RESULT_COUNT") != null) {				
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("RESULT_COUNT")).intValue());
			}
			listaDigitalizados.add(item);
		}
		return listaDigitalizados;
	}
	
	private Map<String, Object> mapeaDigitalizadosPorActividad(Map<String, Object> resultados) {
		Digitalizado item;
		Map<String, Object> result = new HashMap<String, Object>();
		List<Digitalizado> listaDyC = new ArrayList<Digitalizado>();
		List<Digitalizado> listaDAC = new ArrayList<Digitalizado>();
		List<Digitalizado> listaIC = new ArrayList<Digitalizado>();
		List<Digitalizado> listaMed = new ArrayList<Digitalizado>();
		List<Digitalizado> listaSGIO = new ArrayList<Digitalizado>();
		List<Digitalizado> listaTdE = new ArrayList<Digitalizado>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_LISTA_DETALLE");
		for (Map<String, Object> map : lista) {
			item = new Digitalizado();
			
			if(map.get("NIS_RAD") != null && !map.get("NIS_RAD").equals(" ")) {
			    item.setSuministro(Integer.parseInt((String) map.get("NIS_RAD")));
			}
			Actividad actividad = new Actividad();
			actividad.setCodigo((String) map.get("V_IDACTI"));
			actividad.setDescripcion((String) map.get("V_DESCACTI"));
			item.setActividad(actividad);			
			if(map.get("V_NUM_SEDA") != null && !map.get("V_NUM_SEDA").equals(" ")) {
				item.setOrdTrabOrdServCedu((String) map.get("V_NUM_SEDA"));	
			}
			if(map.get("V_TIPO_OSOT") != null && !map.get("V_TIPO_OSOT").equals(" ")) {
				item.setTipologia((String) map.get("V_TIPO_OSOT"));	
			}
			if(map.get("V_ID_CARG") != null && !map.get("V_ID_CARG").equals(" ")) {
				item.setNumeroCarga((String) map.get("V_ID_CARG"));	
			}		
			if(map.get("V_ID_CARG_DET") != null && !map.get("V_ID_CARG_DET").equals(" ")) {
				item.setIdCargaDetalle(((BigDecimal) map.get("V_ID_CARG_DET")).intValue());
			}			
			if(map.get("V_FEC_EJE") != null && !map.get("V_FEC_EJE").equals(" ")) {
				Timestamp fecEje = (Timestamp) map.get("V_FEC_EJE");			
				item.setFechaEjecucion(new SimpleDateFormat("dd/MM/yyyy").format(fecEje));				
			}
			if(map.get("V_N_OT")!=null && !map.get("V_N_OT").equals(" ")) {
				item.setNumeroOT((String)map.get("V_N_OT"));	
			}
			if(map.get("V_IN_DIG")!=null && !map.get("V_IN_DIG").equals(" ")) {
				item.setCantAdj(((BigDecimal) map.get("V_IN_DIG")).intValue());	
			}
			if(map.get("V_IN_DIG_JPG")!=null && !map.get("V_IN_DIG_JPG").equals(" ")) {
				item.setCantImg(((BigDecimal) map.get("V_IN_DIG_JPG")).intValue());	
			}
			if (map.get("RESULT_COUNT") != null) {				
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("RESULT_COUNT")).intValue());
			}
			
			switch ((String) map.get("V_IDACTI")) {
			case "DC":

				listaDyC.add(item);
				break;
			case "DA":

				listaDAC.add(item);
				break;
			case "IC":

				listaIC.add(item);
				break;
			case "ME":

				listaMed.add(item);
				break;
			case "SG":

				listaSGIO.add(item);
				break;
			case "TE":

				listaTdE.add(item);
				break;
				
			default:
				break;
			}//fin del switch
		}//fin del for

		result.put("listaDyC", listaDyC);
		result.put("listaDAC", listaDAC);
		result.put("listaIC", listaIC);
		result.put("listaMed", listaMed);
		result.put("listaSGIO", listaSGIO);
		result.put("listaTdE", listaTdE);
		
		return result;
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
	public ParametrosCargaBandeja obtenerParametrosBusquedaDigitalizados(Integer idPers, Integer idPerfil) {
		Map<String, Object> out = null;
		ParametrosCargaBandeja parametros = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
				.withProcedureName(DbConstants.PRC_OBT_PARAM_BUSQ_DIGI)
				.declareParameters(
						new SqlParameter("V_N_IDPERS", OracleTypes.INTEGER),
						new SqlParameter("V_N_IDPERFIL", OracleTypes.INTEGER),
						new SqlOutParameter("C_OUT_OFIC", OracleTypes.CURSOR),						
						new SqlOutParameter("C_OUT_ACTI", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_N_IDPERS", idPers)
				.addValue("V_N_IDPERFIL", idPerfil);
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				parametros = mapearParametrosBusqueda(out);
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - obtenerParametrosBusquedaDigitalizados()] - "+mensajeInterno);
			}
			
		}catch(Exception e){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - obtenerParametrosBusquedaDigitalizados()] - "+e.getMessage());
		}
		return parametros;
	}
	
	private ParametrosCargaBandeja mapearParametrosBusqueda(Map<String, Object> resultados) {
		ParametrosCargaBandeja params = new ParametrosCargaBandeja();
		List<Map<String, Object>> mapListaOficina = (List<Map<String, Object>>)resultados.get("C_OUT_OFIC");
		List<Map<String, Object>> mapListaActividad = (List<Map<String, Object>>)resultados.get("C_OUT_ACTI");
		List<Oficina> listaOficina = new ArrayList<Oficina>();
		List<Actividad> listaActividad = new ArrayList<Actividad>();
		Oficina itemOficina;
		Actividad itemActividad;
		
		for(Map<String, Object> map: mapListaOficina) {
			itemOficina = new Oficina();
			itemOficina.setCodigo(Integer.parseInt(((BigDecimal)map.get("N_IDOFIC")).toString()));
			itemOficina.setDescripcion((String)map.get("V_DESCOFIC"));
			listaOficina.add(itemOficina);
		}
		
		for(Map<String, Object> map: mapListaActividad) {
			itemActividad = new Actividad();
			itemActividad.setCodigo((String)map.get("V_IDACTI"));
			itemActividad.setDescripcion((String)map.get("V_DESCACTI"));
			listaActividad.add(itemActividad);
		}
		params.setListaOficina(listaOficina);
		params.setListaActividad(listaActividad);
		return params;
	}

	@Override
	public List<Adjunto> listarAdjuntosDigitalizadosCT(VisorDigitalizadoRequest request) {
		Map<String, Object> out = null;
		List<Adjunto> listaAdjuntos = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
				.withProcedureName(DbConstants.PRC_LIST_ADJU_CT_DIG)
				.declareParameters(
						new SqlParameter("V_V_IDCARG", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDREG", OracleTypes.INTEGER),
						new SqlOutParameter("C_OUT_ADJU", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARG", request.getNumeroCarga())				
				.addValue("V_N_IDREG", request.getIdCargaDetalle());
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				listaAdjuntos = mapearDocumentosAdjuntos(out, request.getActividad());
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
				logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosCT()] - "+mensajeInterno);
			}
			
		}catch(Exception e){
			this.error = new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
			logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosCT()] - "+e.getMessage());
		}
		return listaAdjuntos;			
	}
	
	private List<Adjunto> mapearDocumentosAdjuntos(Map<String, Object> resultados, String tipoActividad){
		List<Adjunto> listaAdjuntos = new ArrayList<Adjunto>();
		Adjunto adjunto = null;
		
		List<Map<String, Object>> mapListaAdjuntos = (List<Map<String, Object>>)resultados.get("C_OUT_ADJU");
		
		for(Map<String, Object> map: mapListaAdjuntos) {
			adjunto = new Adjunto();
			if(tipoActividad.equals("SG")) {
				adjunto.setUidCarga((String)map.get("N_NRO_OT").toString());
			}else {
				adjunto.setUidCarga((String)map.get("V_IDCARGA"));
				adjunto.setUidRegistro(((BigDecimal) map.get("N_IDREG")).intValue());
			}
			adjunto.setUidAdjunto(((BigDecimal) map.get("N_IDARCHI")).intValue());
			adjunto.setNombre((String)map.get("V_NOMBRADJUN"));
			adjunto.setExtension((String)map.get("V_EXTENSION"));
			adjunto.setRuta((String)map.get("V_RUTAADJU"));
			/*agregado para visor de imagenes - rramirez*/
			Timestamp fecCreacion = (Timestamp) map.get("A_D_FECCRE");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String fechaString  = dateFormat.format(fecCreacion);
			adjunto.setFechaCreacionConHora(fechaString);
			/**/
			listaAdjuntos.add(adjunto);
		}		
		return listaAdjuntos;
	}

	@Override
	public List<Adjunto> listarAdjuntosDigitalizadosSGIO(VisorDigitalizadoRequest request) {
		Map<String, Object> out = null;
		List<Adjunto> listaAdjuntos = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
				.withProcedureName(DbConstants.PRC_LIST_ADJU_SGIO_DIG)
				.declareParameters(
						new SqlParameter("V_V_ORD_TRA", OracleTypes.NUMBER),
						new SqlOutParameter("C_OUT_ADJU", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_ORD_TRA", Integer.parseInt(request.getNumeroOT()));
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				listaAdjuntos = mapearDocumentosAdjuntos(out, request.getActividad());
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
				logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosSGIO()] - "+mensajeInterno);
			}
			
		}catch(Exception e){
			this.error = new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
			logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosSGIO()] - "+e.getMessage());
		}
		return listaAdjuntos;	
	}
	
	@Override
	public DuracionDigitalizados obtenerParametrosDuracion() {
		DuracionDigitalizados duracion = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_DIGITALIZADO)
					.withProcedureName(DbConstants.PRC_OBTE_PARAM_DURACION)
					.declareParameters(
							new SqlOutParameter("N_MESES", OracleTypes.VARCHAR),
							new SqlOutParameter("N_DIAS_RANGO", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource();
					
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				duracion = new DuracionDigitalizados();	
				String mesesConsulta = (String) out.get("N_MESES");
				String diasRango = (String) out.get("N_DIAS_RANGO");
				try {
					duracion.setMesesConsulta(Integer.parseInt(mesesConsulta));
					duracion.setDiasRango(Integer.parseInt(diasRango));
					
				} catch (NumberFormatException e) {
					duracion = null;
					this.error = new Error(0,"Error", "El valor de los parámetros de meses de consulta y rango de días no tiene un valor numérico.");
				}				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");					
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: DigitalizadoDAOImpl - obtenerDias()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - obtenerDiasPeriodo()] - "+e.getMessage());
		}
		return duracion;		
	}


}
