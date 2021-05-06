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
import pe.com.sedapal.agc.dao.ITomaEstadosDAO;
import pe.com.sedapal.agc.model.TomaEstados;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class TomaEstadosDAOImpl implements ITomaEstadosDAO{
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Paginacion paginacion;
	public Paginacion getPaginacion() {
		return this.paginacion;
	}

	private Error error;
	public Error getError() {
		return this.error;
	}
	
	@Override
	public List<TomaEstados> ListarTomaEstados(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) {
		Map<String, Object> out = null;
		List<TomaEstados> lista = new ArrayList<>();
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_TOM_ESTA)
				.declareParameters(
						new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
						new SqlParameter("V_N_CON_ADJ", OracleTypes.INTEGER),
						new SqlParameter("N_PAGINA", OracleTypes.INTEGER),
						new SqlParameter("N_REGISTROS", OracleTypes.INTEGER),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);		
				
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", V_IDCARGA)
				.addValue("V_N_IDREG", N_IDREG)
				.addValue("V_N_CON_ADJ", V_N_CON_ADJ)
				.addValue("N_PAGINA", paginacion.getPagina())
				.addValue("N_REGISTROS", paginacion.getRegistros());
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				lista = mapeaTomaEstados(out);
			} else {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado,mensaje,mensajeInterno);
			logger.error("[AGC: TomaEstadosDAOImpl - ListarTomaEstados()] - "+e.getMessage());
		}
		return lista;
	}
	
	@Override
	public List<TomaEstados> ListarTomaEstadosTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		List<TomaEstados> lista = new ArrayList<>();
				
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_TOM_ESTA_TRAMA)
				.declareParameters(
						new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_FILTRO", OracleTypes.NUMBER),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);		
				
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", V_IDCARGA)
				.addValue("V_N_FILTRO", filtro)
				.addValue("V_A_V_USUMOD", usuario);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				lista = mapeaTomaEstados(out);
			} else {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado,mensaje,mensajeInterno);
			logger.error("[AGC: TomaEstadosDAOImpl - ListarTomaEstadosTrama()] - "+e.getMessage());
		}
		return lista;
	}
	
	private List<TomaEstados> mapeaTomaEstados(Map<String, Object> resultados)
	{
		TomaEstados item;
		List<TomaEstados> listaTomaEstados = new ArrayList<>();
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		
		for(Map<String, Object> map : lista) {
			item = new TomaEstados();
			item.setCodigoCarga((String)map.get("v_idcarga"));
			item.setCodigoRegistro(((BigDecimal)map.get("n_idreg")).intValue());
			item.setCodigoOficinaComercial((String)map.get("cod_unicom"));
			item.setAnno((String)map.get("anno"));
			item.setMes((String)map.get("mes"));
			item.setCicloComercial((String)map.get("ciclo_fact"));
			item.setRuta((String)map.get("ruta"));
			item.setNumeroIntenerario((String)map.get("num_itin"));
			item.setAOL((String)map.get("aol"));
			item.setNombreRazonSocial((String)map.get("nom_raz_social"));
			item.setNombreUrbanizacion((String)map.get("nom_local"));
			item.setNombreCalle((String)map.get("nom_calle"));
			item.setComplementoDireccion((String)map.get("compl_direc"));
			item.setManzanaLote((String)map.get("mz_lot"));
			item.setCGV((String)map.get("cgv"));
			item.setAccesoPredio((String)map.get("acc_predio"));
			item.setLecturaAnterior((String)map.get("lect_ant"));
			item.setNumeroRuedasMedidor((String)map.get("num_rue"));
			item.setCodigoTarifa((String)map.get("cod_tar"));
			item.setSuministro((String)map.get("nis_rad"));
			item.setCodigoCNAE((String)map.get("cod_cnae"));
			item.setNumeroApaObs((String)map.get("num_apa_obs"));
			item.setNumeroApa((String)map.get("num_apa"));
			item.setLecturaMedidor((String)map.get("lect"));
			item.setPrimerIncidente((String)map.get("inc_1"));
			item.setDetallePrimerIncidente((String)map.get("det_1"));
			item.setSegundoIncidente((String)map.get("inc_2"));
			item.setDetalleSegundoIncidente((String)map.get("det_2"));
			item.setTercerIncidente((String)map.get("inc_3"));
			item.setDetalleTercerIncidente((String)map.get("det_3"));
			item.setFechaLectura((String)map.get("fecha"));
			item.setHoraLectura((String)map.get("hora"));
			item.setCodigoTomadorEstado((String)map.get("cod_lector"));
			item.setMedio((String)map.get("medio"));
			item.setTipoLectura((String)map.get("tip_lectura"));
			item.setNumeroActaIncidencia((String)map.get("num_act_inc"));
			item.setCup((String)map.get("cup"));
			item.setSector((String)map.get("sector"));
			item.setCsmoPromedioCalculado((String)map.get("CSMO_PROM"));
			item.setNumeroPuerta((String)map.get("cod_munic"));
			item.setAccesoPuntoMedida((String)map.get("acc_caja"));
			item.setCsmoActual(((BigDecimal)map.get("csmo_actual")).intValue());
			item.setDesIncidente1((String)map.get("des_inc_1"));
			item.setDesIncidente2((String)map.get("des_inc_2"));
			item.setDesIncidente3((String)map.get("des_inc_3"));
			item.setDesDetalle1((String)map.get("des_det1"));
			item.setDesDetalle2((String)map.get("des_det2"));
			item.setDesDetalle3((String)map.get("des_det3"));
			item.setLecturaOriginal((String)map.get("lect_ori"));
			item.setFechaOrigen((String)map.get("fecha_ori"));
			item.setHoraOrigen((String)map.get("hora_ori"));
			item.setObservacion((String)map.get("obs"));
			listaTomaEstados.add(item);			
			if (map.get("totalRegistros") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("totalRegistros")).intValue());
			}
		}
		return listaTomaEstados;
	}
	
}
