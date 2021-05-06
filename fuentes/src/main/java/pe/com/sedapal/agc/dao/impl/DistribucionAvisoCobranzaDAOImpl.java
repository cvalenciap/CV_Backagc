package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IDistribucionAvisoCobbranzaDAO;
import pe.com.sedapal.agc.model.DistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class DistribucionAvisoCobranzaDAOImpl implements IDistribucionAvisoCobbranzaDAO {

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
	public Map<String, Object> actualizarDistAvisCobLine(DistribucionAvisoCobranza dac, String sUsuario) {

		Map<String, Object> out = null;
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
					.withProcedureName(DbConstants.PRC_MOD_DIS_AVI_LINE)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
							new SqlParameter("V_TIPO_ENTR", OracleTypes.VARCHAR),
							new SqlParameter("V_HORA_DISTR", OracleTypes.NUMBER),
							new SqlParameter("V_FECH_DISTR", OracleTypes.DATE),
							new SqlParameter("V_COD_PERSON", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_IMP", OracleTypes.VARCHAR),
							new SqlParameter("V_CICLO", OracleTypes.NUMBER),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);

			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_V_IDCARGA", dac.getCodigoCarga())
																		.addValue("V_N_IDREG", dac.getCodigoRegistro())
																		.addValue("V_TIPO_ENTR", dac.getTipoEntrada())
																		.addValue("V_HORA_DISTR", dac.getHoraDistribucion())
																		.addValue("V_FECH_DISTR", dac.getFechaDistribucion())
																		.addValue("V_COD_PERSON", dac.getCodigoDistribuidor())
																		.addValue("V_COD_IMP", dac.getCodigoIncidecia())
																		.addValue("V_CICLO", dac.getCiclo())
																		.addValue("V_A_V_USUMOD", sUsuario)
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: DistribucionAvisoCobranzaDAOImpl - actualizarDistAvisCobLine()] - "+e.getMessage());
			
		}
		
		return out;

	}
	
	@Override
	public List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranza(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) {
		Map<String, Object> out = null;
		List<DistribucionAvisoCobranza> lista = new ArrayList<>();
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_DIS_AVIS)
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
				
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", V_IDCARGA)
				.addValue("V_N_IDREG", N_IDREG)
				.addValue("V_N_CON_ADJ", V_N_CON_ADJ)
				.addValue("N_PAGINA", paginacion.getPagina())
				.addValue("N_REGISTROS", paginacion.getRegistros());		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				lista = mapeaAvisoCob(out);
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
			logger.error("[AGC: DistribucionAvisoCobranzaDAOImpl - ListarDetalleAvisoCobranza()] - "+e.getMessage());
		}
		return lista;
	}
	
	@Override
	public List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranzaTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		List<DistribucionAvisoCobranza> lista = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_DIS_AVIS_TRAMA)
				.declareParameters(
						new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_FILTRO", OracleTypes.NUMBER),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);		
				
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", V_IDCARGA)
				.addValue("V_N_FILTRO", filtro)
				.addValue("V_A_V_USUMOD", usuario);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				lista = mapeaAvisoCob(out);
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
			logger.error("[AGC: DistribucionAvisoCobranzaDAOImpl - ListarDetalleAvisoCobranzaTrama()] - "+e.getMessage());
		}
		return lista;
	}
	
	private List<DistribucionAvisoCobranza> mapeaAvisoCob(Map<String, Object> resultados)
	{
		DistribucionAvisoCobranza item;
		List<DistribucionAvisoCobranza> listaAvisoCob = new ArrayList<>();
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		
		for(Map<String, Object> map : lista) {
			item = new DistribucionAvisoCobranza();
			item.setCodigoCarga((String)map.get("v_idcarga"));			
			item.setCodigoRegistro(((BigDecimal)map.get("n_idreg")).intValue());	
			item.setOficinaComercial(((BigDecimal)map.get("cod_unicom")).intValue());
			item.setSecuenciaTrabajo(((BigDecimal)map.get("sec_trabajo")).doubleValue());
			item.setRutaEstablecida(((BigDecimal)map.get("ruta")).intValue());
			item.setCampItinerario(((BigDecimal)map.get("num_itin")).intValue());
			item.setCampSuministro(((BigDecimal)map.get("nis_rad")).intValue());
			item.setSecuenciaRecibo(((BigDecimal)map.get("num_secuencia")).intValue());
			item.setNroMedidor((String)map.get("num_apa"));
			item.setCalle((String)map.get("nom_calle"));
			item.setNroPuerta(((BigDecimal)map.get("num_puerta")).intValue());
			item.setDuplicador((String)map.get("duplicador"));
			item.setCgv((String)map.get("cgv"));
			item.setAol(((BigDecimal)map.get("aol")).intValue());
			item.setCusp((String)map.get("cusp"));
			item.setRepresentaMunicipio((String)map.get("distrito"));
			item.setRepresentaLocalidad((String)map.get("localidad"));
			item.setReferenciaDireccion((String)map.get("ref_dir"));
			item.setIndicadorFueraRuta((String)map.get("caso"));
			item.setCiclo(((BigDecimal)map.get("ciclo")).intValue());
			item.setCodigoDistribuidor((String)map.get("cod_persona"));
			item.setImposibilidad((String)map.get("cod_imp"));			
			item.setFechaDistribucion(map.get("f_distrib") != null ? new Date(((Timestamp)map.get("f_distrib")).getTime()) : null);
			item.setHoraDistribucion(((BigDecimal)map.get("hora_distrib")).intValue());
			item.setDuplicador((String)map.get("duplicador"));
			item.setTipoEntrada((String)map.get("tip_ent"));			
			listaAvisoCob.add(item);			
			if (map.get("totalRegistros") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("totalRegistros")).intValue());
			}
		}
		return listaAvisoCob;
	}

}
