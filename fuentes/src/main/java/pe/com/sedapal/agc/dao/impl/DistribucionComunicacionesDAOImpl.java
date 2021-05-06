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
import pe.com.sedapal.agc.dao.IDistribucionComunicacionesDAO;
import pe.com.sedapal.agc.model.DistribucionComunicaciones;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class DistribucionComunicacionesDAOImpl implements IDistribucionComunicacionesDAO {

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Paginacion paginacion;
	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}
	
	private Error error;
	@Override
	public Error getError() {
		return this.error;
	}
	
	@Override
	public Map<String, Object> actualizarDistComLine(DistribucionComunicaciones dc, String sUsuario) {

		Map<String, Object> out = null;
		try {
			
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
					.withProcedureName(DbConstants.PRC_MOD_DIS_COM_LINE)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_ENTREGA", OracleTypes.VARCHAR),
							new SqlParameter("V_PARENTESCO_N", OracleTypes.VARCHAR),
							new SqlParameter("V_OBS2", OracleTypes.VARCHAR),
							new SqlParameter("V_OBS", OracleTypes.VARCHAR),
							new SqlParameter("V_NVISITA", OracleTypes.VARCHAR),
							new SqlParameter("V_NOMNOTIF", OracleTypes.VARCHAR),
							new SqlParameter("V_NOM_EMP2", OracleTypes.VARCHAR),
							new SqlParameter("V_NOM_EMP1", OracleTypes.VARCHAR),
							new SqlParameter("V_HNOTIF2", OracleTypes.VARCHAR),
							new SqlParameter("V_HNOTIF1", OracleTypes.VARCHAR),
							new SqlParameter("V_HNOTIF", OracleTypes.VARCHAR),
							new SqlParameter("V_FNOTIF2", OracleTypes.VARCHAR),
							new SqlParameter("V_FNOTIF1", OracleTypes.VARCHAR),
							new SqlParameter("V_FNOTIF", OracleTypes.VARCHAR),
							new SqlParameter("V_DNINOTIF", OracleTypes.VARCHAR),
							new SqlParameter("V_DNI_EMP2", OracleTypes.VARCHAR),
							new SqlParameter("V_DNI_EMP1", OracleTypes.VARCHAR),
							new SqlParameter("V_DIFICUL", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_EMP2", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_EMP1", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);

			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_V_IDCARGA", dc.getCodigoCarga())
																		.addValue("V_N_IDREG", dc.getCodigoRegistro())
																		.addValue("V_TIP_ENTREGA", dc.getTipoEntrega())
																		.addValue("V_PARENTESCO_N", dc.getParentezcoNotificada())
																		.addValue("V_OBS2", dc.getObsVisita2())
																		.addValue("V_OBS", dc.getObsVisita1())
																		.addValue("V_NVISITA", dc.getNroVisitasNotificador())
																		.addValue("V_NOMNOTIF", dc.getNombreNotificada())
																		.addValue("V_NOM_EMP2", dc.getNombreNotificadorVisita2())
																		.addValue("V_NOM_EMP1", dc.getNombreNotificadorVisita1())
																		.addValue("V_HNOTIF2", dc.getHoraVisita2())
																		.addValue("V_HNOTIF1", dc.getHoraVisita1())
																		.addValue("V_HNOTIF", dc.getHoraConNotificacion())
																		.addValue("V_FNOTIF2", dc.getFechaVisita2())
																		.addValue("V_FNOTIF1", dc.getFechaVisita1())
																		.addValue("V_FNOTIF", dc.getFechaConcretaNotificacion() )
																		.addValue("V_DNINOTIF", dc.getDniNotificada())
																		.addValue("V_DNI_EMP2", dc.getDniNotificador2())
																		.addValue("V_DNI_EMP1", dc.getDniNotificador1())
																		.addValue("V_DIFICUL", dc.getDificultad())
																		.addValue("V_COD_EMP2", dc.getCodigoNotificadorVisita2())
																		.addValue("V_COD_EMP1", dc.getCodigoNotificadorVisita1())
																		.addValue("V_A_V_USUMOD", sUsuario)
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: DistribucionComunicacionesDAOImpl - actualizarDistComLine()] - "+e.getMessage());
		}
		
		return out;
	}
	
	@Override
	public List<DistribucionComunicaciones> ListarDetalleDisCom(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) {
		Map<String, Object> out = null;
		List<DistribucionComunicaciones> lista = new ArrayList<>();
		
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());

		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_DIS_COMU)
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
				lista = mapeaDisCom(out);
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
			logger.error("[AGC: DistribucionComunicacionesDAOImpl - ListarDetalleDisCom()] - "+e.getMessage());
		}
		return lista;
	}

	@Override
	public List<DistribucionComunicaciones> ListarDetalleDisComTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		List<DistribucionComunicaciones> lista = new ArrayList<>();
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_DIS_COMU_TRAMA)
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
				lista = mapeaDisCom(out);
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
			logger.error("[AGC: DistribucionComunicacionesDAOImpl - ListarDetalleDisComTrama()] - "+e.getMessage());
		}
		return lista;
	}
	
	private List<DistribucionComunicaciones> mapeaDisCom(Map<String, Object> resultados)
	{
		DistribucionComunicaciones item;
		List<DistribucionComunicaciones> listaDisCom = new ArrayList<>();
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		
		for(Map<String, Object> map : lista) {
			item = new DistribucionComunicaciones();
			item.setCodigoCarga((String)map.get("v_idcarga"));
			item.setCodigoRegistro(((BigDecimal)map.get("n_idreg")).intValue());
			item.setCodigoOficinaComercial((map.get("oc")).toString());
			item.setNroIdentCarga((String)map.get("carga"));
			item.setFechaEntraga((String)map.get("fenvio"));
			item.setNroSuministro(map.get("nis").toString());
			item.setCorrCedulaNotificacion((String)map.get("ncedula"));
			item.setFechaEmisionDocumento((String)map.get("femision"));
			item.setApellidoPatDestinatario((String)map.get("ap1"));
			item.setApellidoMatDestinatario((String)map.get("ap2"));
			item.setNombresDestinatario((String)map.get("nom"));
			item.setTipoDocumentoDestinatario((String)map.get("tipdni"));
			item.setNroDocumento((String)map.get("dni"));
			item.setRazonSocial((String)map.get("rsocial"));
			item.setCalle((String)map.get("calle"));
			item.setNro((String)map.get("nro"));
			item.setPiso((String)map.get("cgv_cli"));
			item.setManzana((String)map.get("mza"));
			item.setLote((String)map.get("lote"));
			item.setUrb((String)map.get("urb"));
			item.setProvincia((String)map.get("prov"));
			item.setDistrito((String)map.get("distrito"));			
			item.setNroReclamoOpen((String)map.get("nreclamo"));
			item.setTipoReclamo((String)map.get("treclamo"));
			item.setCodigoTipoDocu((String)map.get("tdoc"));
			item.setNroDocumentoNotificar((String)map.get("nrodoc"));
			item.setNroVisitasNotificador((String)map.get("nvisita"));
			item.setFechaVisita1(map.get("fnotif1") != null ? new Date(((Timestamp)map.get("fnotif1")).getTime()) : null);
			item.setHoraVisita1((String)map.get("hnotif1"));
			item.setFechaVisita2(map.get("fnotif2") != null ? new Date(((Timestamp)map.get("fnotif2")).getTime()) : null);
			item.setHoraVisita2((String)map.get("hnotif2"));
			item.setNombreNotificada((String)map.get("nomnotif"));
			item.setDniNotificada((String)map.get("dninotif"));
			item.setParentezcoNotificada((String)map.get("parentesco_n"));
			item.setObsVisita1((String)map.get("obs"));
			item.setObsVisita2((String)map.get("obs2"));
			item.setCodigoNotificadorVisita1((String)map.get("cod_emp1"));			
			item.setNombreNotificadorVisita1((String)map.get("nom_emp1"));
			item.setDniNotificador1((String)map.get("dni_emp1"));
			item.setCodigoNotificadorVisita2((String)map.get("cod_emp2"));
			item.setNombreNotificadorVisita2((String)map.get("nom_emp2"));
			item.setDniNotificador2((String)map.get("dni_emp2"));
			item.setFechaConcretaNotificacion(map.get("fnotif") != null ? new Date(((Timestamp)map.get("fnotif")).getTime()) : null);
			item.setHoraConNotificacion((String)map.get("hnotif"));
			item.setTipoEntrega((String)map.get("tip_entrega"));
			item.setDificultad((String)map.get("dificul"));
			item.setGrupoFuncional((String)map.get("gf"));
			item.setCodInternoDocuRemitido((String)map.get("tdocnot"));
			item.setCdsd((String)map.get("cdsd"));
			item.setUsuario((String)map.get("usuario"));			
			listaDisCom.add(item);		
			if (map.get("totalRegistros") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("totalRegistros")).intValue());
			}
		}
		return listaDisCom;
	}

}
