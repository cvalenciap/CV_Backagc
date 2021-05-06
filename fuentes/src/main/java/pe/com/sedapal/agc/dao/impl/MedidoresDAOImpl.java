package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IMedidoresDAO;
import pe.com.sedapal.agc.model.Medidores;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;
import pe.com.sedapal.agc.model.request.PageRequest;

@Repository
public class MedidoresDAOImpl implements IMedidoresDAO {
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
	public List<Medidores> ListarDetalleMedidores(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) {
		Map<String, Object> out = null;
		List<Medidores> lista = new ArrayList<>();
		
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_MEDIDORE)
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
				lista = mapeaMedidores(out);
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
			logger.error("[AGC: MedidoresDAOImpl - ListarDetalleMedidores()] - "+e.getMessage());
		}
		return lista;
	}
	
	@Override
	public List<Medidores> ListarDetalleMedidoresTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		List<Medidores> lista = new ArrayList<>();
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_MEDIDORE_TRAMA)
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
				lista = mapeaMedidores(out);
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
			logger.error("[AGC: MedidoresDAOImpl - ListarDetalleMedidoresTrama()] - "+e.getMessage());
		}
		return lista;
	}			

	private List<Medidores> mapeaMedidores(Map<String, Object> resultados)
	{
		Medidores item;
		List<Medidores> listaMedidores = new ArrayList<>();
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		
		for(Map<String, Object> map : lista) {
			item = new Medidores();
			item.setCodigoCarga((String)map.get("v_idcarga"));
			item.setCodigoRegistro(((BigDecimal)map.get("n_idreg")).intValue());
			item.setNroSuministro((String)map.get("nis_rad"));
			item.setNombreCliente((String)map.get("cliente"));
			item.setNumeroOrdenServicio((String)map.get("num_os"));
			item.setNombreCalle((String)map.get("nom_calle"));
			item.setNroPuerta((String)map.get("num_puerta"));
			item.setDuplicador((String)map.get("duplicador"));
			item.setCgv((String)map.get("cgv"));
			item.setMz((String)map.get("mz"));
			item.setLote((String)map.get("lote"));
			item.setLocalidad((String)map.get("localidad"));
			item.setDistrito((String)map.get("distrito"));
			item.setCus((String)map.get("cus"));
			item.setDireccionReferencia((String)map.get("ref_dir"));
			item.setMedidorActual((String)map.get("med_actual"));
			item.setDiametro((String)map.get("diametro"));
			item.setAccion((String)map.get("accion"));
			item.setComentario((String)map.get("coment_os"));
			item.setTexto((String)map.get("texto"));
			item.setTpi((String)map.get("tpi"));
			item.setFechaResolucion(map.get("f_res") != null ? ((Timestamp)map.get("f_res")).getTime(): " ");
			item.setFechaInspeccion((String)map.get("f_insp"));
			item.setTipoOrdenServicio((String)map.get("tip_os"));
			item.setHoraInspeccion((String)map.get("hora"));
			item.setEstadoOT((String)map.get("dbestado"));
			item.setNroRecepcion(((BigDecimal)map.get("dbnrecep")).intValue());
			item.setNroOT((String)map.get("dbnumord"));
			item.setNroCompromiso((String)map.get("dbnumcom"));
			item.setFechaEjecucion(map.get("dbfejec") != null ? ((Timestamp)map.get("dbfejec")).getTime(): " ");
			item.setCodigoDistrito((String)map.get("dbdist"));
			item.setNroSuministroCliente((String)map.get("dbnis"));
			item.setCodigoActividadEjecutada1((String)map.get("dbact01"));
			item.setCantidadEjecutadaActividad1(((BigDecimal)map.get("dbcan01")).doubleValue());
			item.setCodigoActividadEjecutada2((String)map.get("dbact02"));
			item.setCantidadEjecutadaActividad2(((BigDecimal)map.get("dbcan02")).doubleValue());
			item.setCodigoActividadEjecutada3((String)map.get("dbact03"));			
			item.setCantidadEjecutadaActividad3(((BigDecimal)map.get("dbcan03")).doubleValue());
			item.setCodigoActividadEjecutada4((String)map.get("dbact04"));
			item.setCantidadEjecutadaActividad4(((BigDecimal)map.get("dbcan04")).doubleValue());
			item.setCodigoActividadEjecutada5((String)map.get("dbact05"));
			item.setCantidadEjecutadaActividad5(((BigDecimal)map.get("dbcan05")).doubleValue());
			item.setCodigoActividadEjecutada6((String)map.get("dbact06"));
			item.setCantidadEjecutadaActividad6(((BigDecimal)map.get("dbcan06")).doubleValue());
			item.setCodigoActividadEjecutada7((String)map.get("dbact07"));
			item.setCantidadEjecutadaActividad7(((BigDecimal)map.get("dbcan07")).doubleValue());
			item.setCodigoActividadEjecutada8((String)map.get("dbact08"));			
			item.setCantidadEjecutadaActividad8(((BigDecimal)map.get("dbcan08")).doubleValue());
			item.setCodigoActividadEjecutada9((String)map.get("dbact09"));
			item.setCantidadEjecutadaActividad9(((BigDecimal)map.get("dbcan09")).doubleValue());
			item.setCodigoActividadEjecutada10((String)map.get("dbact10"));
			item.setCantidadEjecutadaActividad10(((BigDecimal)map.get("dbcan10")).doubleValue());
			item.setLargoActiComplEjec(((BigDecimal)map.get("dblargo")).doubleValue());
			item.setAnchoActividadComplementariaEjecutada(((BigDecimal)map.get("dbancho")).doubleValue());
			item.setEstadoTapa((String)map.get("dbesttapa"));
			item.setMarTap((String)map.get("dbmartap"));
			item.setValvulasBronce(((BigDecimal)map.get("dbvalbron")).intValue());
			item.setValPVC(((BigDecimal)map.get("dbvalpvc")).intValue());
			item.setTuberiaPlomo(((BigDecimal)map.get("dbtubplom")).doubleValue());
			item.setTipoActividadAMM((String)map.get("dbtipoact"));
			item.setMotivoLevantamiento((String)map.get("dbmotlvto"));
			item.setNroMedidorRetirado((String)map.get("dbnumret"));
			item.setLectura(((BigDecimal)map.get("dbestret")).intValue());
			item.setTipoMatePredomina((String)map.get("dbtipret"));
			item.setDbDiametro((String)map.get("dbdiam"));
			item.setUbicacionCajaControl((String)map.get("dbubic"));
			item.setEvaluacionConexionFraudulenta((String)map.get("dbcnxfra"));
			item.setOtroTipoConexFraudulenta((String)map.get("dbcnxfotro"));			
			item.setTipoLecturaMedidorInstalado((String)map.get("dbtiplect"));
			item.setNroMedidorInstalado((String)map.get("dbnuminst"));
			item.setLecturaM3(((BigDecimal)map.get("dbestinst")).intValue());
			item.setVerificaMedidorInstalado((String)map.get("dbverminst"));
			item.setNroDispositivoSeguridad((String)map.get("dbdispseg"));
			item.setNroAbonado((String)map.get("dbabonado"));
			item.setValvulaPuntoMedicion(((BigDecimal)map.get("dbvalpmed")).intValue());
			item.setMaterialValvulaPuntoMedicion((String)map.get("dbmatvpm"));
			item.setValvulaTelescopia(((BigDecimal)map.get("dbvaltele")).intValue());
			item.setMaterialValvulaTelescopia((String)map.get("dbmatvtel"));
			item.setDniOperarioOT((String)map.get("dbcod_ope"));
			item.setDniSupervisorOT((String)map.get("dbcod_tsu"));
			item.setDniSupervisorAsignadoSedapal((String)map.get("dbcod_sup"));
			item.setMaterialTuberiaConexionDomicilia((String)map.get("dbtuberia"));
			item.setLongitudTubeMatriz(((BigDecimal)map.get("dblong")).doubleValue());
			item.setMaterialRed((String)map.get("dbmatred"));
			item.setTipoConexion((String)map.get("dbtipcnx"));
			item.setTipoPavimento((String)map.get("dbtippav"));
			item.setSupervisadaOT((String)map.get("dbfugas"));
			item.setFugasInstainternas((String)map.get("dbtelfcli"));
			item.setNroTelefonoCliente((String)map.get("dbsuperv"));
			item.setCodigoObservacion((String)map.get("dbcodobs"));
			item.setObsCampo((String)map.get("dbobserv"));
			item.setDispoSeguridadMedidor((String)map.get("dbdsegmed"));
			item.setTuercasValvula(((BigDecimal)map.get("dbtuer_val")).intValue());
			item.setCantAdaptadorPresion(((BigDecimal)map.get("dbmat01")).doubleValue());
			item.setCantidadCodos(((BigDecimal)map.get("dbmat02")).doubleValue());
			item.setCantidadCurvas3(((BigDecimal)map.get("dbmat03")).doubleValue());
			item.setCantidadNiplesRemplazo(((BigDecimal)map.get("dbmat04")).doubleValue());
			item.setCantidadNipleSTD(((BigDecimal)map.get("dbmat05")).doubleValue());			
			item.setCantidadPresintosSeguridad(((BigDecimal)map.get("dbmat06")).doubleValue());
			item.setCantidadTransicion(((BigDecimal)map.get("dbmat07")).doubleValue());
			item.setCantidadTuberia(((BigDecimal)map.get("dbmat08")).doubleValue());
			item.setCantidadPolietileno(((BigDecimal)map.get("dbmat09")).doubleValue());
			item.setCantidadValvulaSimple(((BigDecimal)map.get("dbmat10")).doubleValue());
			item.setCantidadFiltroMedidor(((BigDecimal)map.get("dbmat11")).doubleValue());
			item.setCantidadDispositivo(((BigDecimal)map.get("dbmat12")).doubleValue());
			item.setTermoplasticoMT(((BigDecimal)map.get("dbmat13")).doubleValue());
			item.setValvulaTelescopicaPVC(((BigDecimal)map.get("dbmat14")).doubleValue());
			item.setValvulaTelescopicaPmed(((BigDecimal)map.get("dbmat15")).doubleValue());
			item.setDbMat16(((BigDecimal)map.get("dbmat16")).doubleValue());
			item.setDbMat17(((BigDecimal)map.get("dbmat17")).doubleValue());
			item.setDbMat18(((BigDecimal)map.get("dbmat18")).doubleValue());
			item.setDbMat18D((String)map.get("dbmat18_d"));
			item.setDbMat19(((BigDecimal)map.get("dbmat19")).doubleValue());
			item.setDbMat20(((BigDecimal)map.get("dbmat20")).doubleValue());
			item.setDbMat21(((BigDecimal)map.get("dbmat21")).doubleValue());
			item.setDbMat22(((BigDecimal)map.get("dbmat22")).doubleValue());
			item.setDbMat23(((BigDecimal)map.get("dbmat23")).doubleValue());
			item.setDbMat24(((BigDecimal)map.get("dbmat24")).doubleValue());
			item.setDbMat25(((BigDecimal)map.get("dbmat25")).doubleValue());
			item.setDbMat26(((BigDecimal)map.get("dbmat26")).doubleValue());
			item.setDbMat27(((BigDecimal)map.get("dbmat27")).doubleValue());
			item.setDbMat28(((BigDecimal)map.get("dbmat28")).doubleValue());
			item.setDbMat29(((BigDecimal)map.get("dbmat29")).doubleValue());
			item.setDbMat30(((BigDecimal)map.get("dbmat30")).doubleValue());
			item.setEstadoCaja((String)map.get("dbestcaja"));
			item.setEstadoValTeles((String)map.get("dbestvtel"));
			item.setEstadoValPunMedicion((String)map.get("dbestvpm"));
			item.setEstadoValAbrazadera((String)map.get("dbestabra"));
			item.setEstadoMedidor((String)map.get("dbestmed"));
			item.setEstadoTubForro((String)map.get("dbesttubf"));
			item.setSelladoraRatonera((String)map.get("dbsellrat"));
			item.setEstadoSoldado((String)map.get("dbestsolad"));
			item.setTuboForroRetirado(((BigDecimal)map.get("dbtfret")).intValue());
			item.setDbNroDisp((String)map.get("dbnum_disp"));
			item.setDbNroTelf((String)map.get("dbnum_telf"));
			item.setMotivoAct((String)map.get("dbmotiact"));
			item.setEjecOperario((String)map.get("dbejeoper"));
			item.setEjecTecnico((String)map.get("dbejetecn"));
			item.setInspec((String)map.get("dbinspec"));
			item.setCodigoOficina((String)map.get("dboficina"));			
			listaMedidores.add(item);		
			if (map.get("totalRegistros") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("totalRegistros")).intValue());
			}
		}
		return listaMedidores;
	}
}
