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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IInspeccionesComercialesDAO;
import pe.com.sedapal.agc.model.InspeccionesComerciales;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class InspeccionesComercialesDAOImpl implements IInspeccionesComercialesDAO {

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
	public List<InspeccionesComerciales> ListarInspeccionesComerciales(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) {
		Map<String, Object> out = null;
		List<InspeccionesComerciales> lista = new ArrayList<>();
		
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_INS_COME)
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
				lista = mapeaInspeccionesComerciales(out);
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
			logger.error("[AGC: InspeccionesComercialesDAOImpl - ListarInspeccionesComerciales()] - "+e.getMessage());
		}
		return lista;
	}

	@Override
	public List<InspeccionesComerciales> ListarInspeccionesComercialesTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		List<InspeccionesComerciales> lista = new ArrayList<>();
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_DET_INS_COME_TRAMA)
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
				lista = mapeaInspeccionesComerciales(out);
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
			logger.error("[AGC: InspeccionesComercialesDAOImpl - ListarInspeccionesComercialesTrama()] - "+e.getMessage());
		}
		return lista;
	}
	
	@Override
	public Map<String, Object> actualizarDistComLine(InspeccionesComerciales ic, String sUsuario) {
		Map<String, Object> out = null;
		try {
			
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
					.withProcedureName(DbConstants.PRC_MOD_INS_COM_LINE)
					.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
									new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
									new SqlParameter("V_VAR_ACT_PREDIO", OracleTypes.VARCHAR),
									new SqlParameter("V_VALV_S_P", OracleTypes.VARCHAR),
									new SqlParameter("V_VALV_E_TLSC", OracleTypes.VARCHAR),
									new SqlParameter("V_UU_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_UU_DOCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_USO_UNIC", OracleTypes.VARCHAR),
									new SqlParameter("V_URIN_F", OracleTypes.VARCHAR),
									new SqlParameter("V_URIN_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_URIN_B", OracleTypes.VARCHAR),
									new SqlParameter("V_URB_C", OracleTypes.VARCHAR),
									new SqlParameter("V_TIP_SUELO_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_TIP_MAT_TUB_D", OracleTypes.VARCHAR),
									new SqlParameter("V_TIP_MAT_TAP_D", OracleTypes.VARCHAR),
									new SqlParameter("V_TIP_MAT_CAJ_D", OracleTypes.VARCHAR),
									new SqlParameter("V_TIP_DES_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_TELF_EMP", OracleTypes.NUMBER),
									new SqlParameter("V_TELEF", OracleTypes.VARCHAR),
									new SqlParameter("V_TANQU_F", OracleTypes.VARCHAR),
									new SqlParameter("V_TANQU_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_TANQU_B", OracleTypes.VARCHAR),
									new SqlParameter("V_SOC_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_SOC_DESC", OracleTypes.VARCHAR),
									new SqlParameter("V_SIS_TRAT_RES", OracleTypes.VARCHAR),
									new SqlParameter("V_SILO", OracleTypes.VARCHAR),
									new SqlParameter("V_SERV_DT", OracleTypes.VARCHAR),
									new SqlParameter("V_SERV", OracleTypes.VARCHAR),
									new SqlParameter("V_SEGURO_TAP", OracleTypes.VARCHAR),
									new SqlParameter("V_SECTOR_F", OracleTypes.NUMBER),
									new SqlParameter("V_RUC", OracleTypes.VARCHAR),
									new SqlParameter("V_REF_C", OracleTypes.VARCHAR),
									new SqlParameter("V_RAZSOC", OracleTypes.VARCHAR),
									new SqlParameter("V_PROF_CJA_DES", OracleTypes.NUMBER),
									new SqlParameter("V_PRED_NO_UBIC", OracleTypes.VARCHAR),
									new SqlParameter("V_PRECINT", OracleTypes.VARCHAR),
									new SqlParameter("V_PISO_DPTO_F", OracleTypes.VARCHAR),
									new SqlParameter("V_PISC_F", OracleTypes.VARCHAR),
									new SqlParameter("V_PISC_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_PISC_B", OracleTypes.VARCHAR),
									new SqlParameter("V_PATERNO", OracleTypes.VARCHAR),
									new SqlParameter("V_OBSERVM2", OracleTypes.VARCHAR),
									new SqlParameter("V_OBSERVM1", OracleTypes.VARCHAR),
									new SqlParameter("V_OBSERV", OracleTypes.VARCHAR),
									new SqlParameter("V_OBS_ATENDIO", OracleTypes.VARCHAR),
									new SqlParameter("V_NUM_MED", OracleTypes.VARCHAR),
									new SqlParameter("V_NUM_C", OracleTypes.VARCHAR),
									new SqlParameter("V_NSE_F", OracleTypes.VARCHAR),
									new SqlParameter("V_NOMBRE", OracleTypes.VARCHAR),
									new SqlParameter("V_NIV_FINC", OracleTypes.VARCHAR),
									new SqlParameter("V_N_CONEX", OracleTypes.VARCHAR),
									new SqlParameter("V_MZA_C", OracleTypes.VARCHAR),
									new SqlParameter("V_MOT_NO_ING", OracleTypes.VARCHAR),
									new SqlParameter("V_MNTO_CARTOG", OracleTypes.VARCHAR),
									new SqlParameter("V_MED3", OracleTypes.VARCHAR),
									new SqlParameter("V_MED2", OracleTypes.VARCHAR),
									new SqlParameter("V_MATERNO", OracleTypes.VARCHAR),
									new SqlParameter("V_MAT_VALV_S", OracleTypes.VARCHAR),
									new SqlParameter("V_MAT_VALV_E", OracleTypes.VARCHAR),
									new SqlParameter("V_LOTE_C", OracleTypes.VARCHAR),
									new SqlParameter("V_LONG_CNX_DES", OracleTypes.NUMBER),
									new SqlParameter("V_LEC_MED3", OracleTypes.NUMBER),
									new SqlParameter("V_LEC_MED2", OracleTypes.NUMBER),
									new SqlParameter("V_LEC", OracleTypes.VARCHAR),
									new SqlParameter("V_LAVA_F", OracleTypes.VARCHAR),
									new SqlParameter("V_LAVA_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_LAVA_B", OracleTypes.VARCHAR),
									new SqlParameter("V_INSPEC_REALIZ", OracleTypes.VARCHAR),
									new SqlParameter("V_INOD_F", OracleTypes.VARCHAR),
									new SqlParameter("V_INOD_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_INOD_B", OracleTypes.VARCHAR),
									new SqlParameter("V_IND_TRAM_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_IND_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_IND_DESC", OracleTypes.VARCHAR),
									new SqlParameter("V_INCI_MEDIDOR", OracleTypes.VARCHAR),
									new SqlParameter("V_IMPOSIBILIDAD", OracleTypes.VARCHAR),
									new SqlParameter("V_HORA_I", OracleTypes.VARCHAR),
									new SqlParameter("V_HORA_F", OracleTypes.VARCHAR),
									new SqlParameter("V_GRIFO_F", OracleTypes.VARCHAR),
									new SqlParameter("V_GRIFO_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_GRIFO_B", OracleTypes.VARCHAR),
									new SqlParameter("V_FUN_MED1", OracleTypes.VARCHAR),
									new SqlParameter("V_FUGA_CAJ", OracleTypes.VARCHAR),
									new SqlParameter("V_FTE_CONEXION_C", OracleTypes.VARCHAR),
									new SqlParameter("V_FTE_CNXC", OracleTypes.VARCHAR),
									new SqlParameter("V_FEC_VIS", OracleTypes.VARCHAR),
									new SqlParameter("V_FAX", OracleTypes.VARCHAR),
									new SqlParameter("V_ESTADO", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_TUB_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_TAPA_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_TAPA", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_DESC", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_CAJA_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_EST_CAJA", OracleTypes.VARCHAR),
									new SqlParameter("V_EMAIL", OracleTypes.VARCHAR),
									new SqlParameter("V_DUPLI_C", OracleTypes.VARCHAR),
									new SqlParameter("V_DUCH_F", OracleTypes.VARCHAR),
									new SqlParameter("V_DUCH_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_DUCH_B", OracleTypes.VARCHAR),
									new SqlParameter("V_DOM_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_DOM_DESC", OracleTypes.VARCHAR),
									new SqlParameter("V_DNI", OracleTypes.VARCHAR),
									new SqlParameter("V_DIST_C", OracleTypes.VARCHAR),
									new SqlParameter("V_DISPOSIT", OracleTypes.VARCHAR),
									new SqlParameter("V_DIA_MED3", OracleTypes.VARCHAR),
									new SqlParameter("V_DIA_MED2", OracleTypes.VARCHAR),
									new SqlParameter("V_DIA_MED", OracleTypes.VARCHAR),
									new SqlParameter("V_DIA_CONEX_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_DIA_CONEX_AGUA", OracleTypes.NUMBER),
									new SqlParameter("V_COTA_VER_DES", OracleTypes.NUMBER),
									new SqlParameter("V_COTA_SUM_C", OracleTypes.VARCHAR),
									new SqlParameter("V_COTA_HOR_DES", OracleTypes.NUMBER),
									new SqlParameter("V_CORR_CUP", OracleTypes.VARCHAR),
									new SqlParameter("V_COM_OCUP", OracleTypes.VARCHAR),
									new SqlParameter("V_COM_DESC", OracleTypes.VARCHAR),
									new SqlParameter("V_COD_UBICC", OracleTypes.VARCHAR),
									new SqlParameter("V_COD_SUP", OracleTypes.NUMBER),
									new SqlParameter("V_COD_EMP", OracleTypes.VARCHAR),
									new SqlParameter("V_COD_DIGIT", OracleTypes.NUMBER),
									new SqlParameter("V_CNX3_CON", OracleTypes.VARCHAR),
									new SqlParameter("V_CNX2_CON", OracleTypes.VARCHAR),
									new SqlParameter("V_CNX_CON", OracleTypes.VARCHAR),
									new SqlParameter("V_CLANDES_DES", OracleTypes.VARCHAR),
									new SqlParameter("V_CLANDES", OracleTypes.VARCHAR),
									new SqlParameter("V_CISTE_F", OracleTypes.VARCHAR),
									new SqlParameter("V_CISTE_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_CISTE_B", OracleTypes.VARCHAR),
									new SqlParameter("V_CEL_CLI_C", OracleTypes.NUMBER),
									new SqlParameter("V_CARTOGRAFIA", OracleTypes.VARCHAR),
									new SqlParameter("V_CALLE_C", OracleTypes.VARCHAR),
									new SqlParameter("V_CAJA", OracleTypes.VARCHAR),
									new SqlParameter("V_BIDET_F", OracleTypes.VARCHAR),
									new SqlParameter("V_BIDET_CL", OracleTypes.VARCHAR),
									new SqlParameter("V_BIDET_B", OracleTypes.VARCHAR),
									new SqlParameter("V_ATENDIO", OracleTypes.VARCHAR),
									new SqlParameter("V_ABAST", OracleTypes.VARCHAR),
									new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR)							
					);

			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_V_IDCARGA", ic.getCodigoCarga())
																		.addValue("V_N_IDREG", ic.getCodigoRegistro())
																		.addValue("V_VAR_ACT_PREDIO", ic.getVar_act_predio())
																		.addValue("V_VALV_S_P", ic.getValv_s_p())
																		.addValue("V_VALV_E_TLSC", ic.getValv_e_tlsc())
																		.addValue("V_UU_OCUP", ic.getUu_ocup())
																		.addValue("V_UU_DOCUP", ic.getUu_docup())
																		.addValue("V_USO_UNIC", ic.getUso_unic())
																		.addValue("V_URIN_F", ic.getUrin_f())
																		.addValue("V_URIN_CL", ic.getUrin_cl())
																		.addValue("V_URIN_B", ic.getUrin_b())
																		.addValue("V_URB_C", ic.getUrb_c())
																		.addValue("V_TIP_SUELO_DES", ic.getTip_suelo_des())
																		.addValue("V_TIP_MAT_TUB_D", ic.getTip_mat_tub_d())
																		.addValue("V_TIP_MAT_TAP_D", ic.getTip_mat_tap_d())
																		.addValue("V_TIP_MAT_CAJ_D", ic.getTip_mat_caj_d())
																		.addValue("V_TIP_DES_DES", ic.getTip_des_des())
																		.addValue("V_TELF_EMP", ic.getTelf_emp())
																		.addValue("V_TELEF", ic.getTelef())
																		.addValue("V_TANQU_F", ic.getTanqu_f())
																		.addValue("V_TANQU_CL", ic.getTanqu_cl())
																		.addValue("V_TANQU_B", ic.getTanqu_b())
																		.addValue("V_SOC_OCUP", ic.getSoc_ocup())
																		.addValue("V_SOC_DESC", ic.getSoc_desc())
																		.addValue("V_SIS_TRAT_RES", ic.getSis_trat_res())
																		.addValue("V_SILO", ic.getSilo())
																		.addValue("V_SERV_DT", ic.getServDT())
																		.addValue("V_SERV", ic.getServ())
																		.addValue("V_SEGURO_TAP", ic.getSeguro_tap())
																		.addValue("V_SECTOR_F", ic.getSector_f())
																		.addValue("V_RUC", ic.getRuc())
																		.addValue("V_REF_C", ic.getRef_c())
																		.addValue("V_RAZSOC", ic.getRazsoc())
																		.addValue("V_PROF_CJA_DES", ic.getProf_cja_des())
																		.addValue("V_PRED_NO_UBIC", ic.getPred_no_ubic())
																		.addValue("V_PRECINT", ic.getPrescinto())
																		.addValue("V_PISO_DPTO_F", ic.getPiso_dpto_f())
																		.addValue("V_PISC_F", ic.getPisc_f())
																		.addValue("V_PISC_CL", ic.getPisc_cl())
																		.addValue("V_PISC_B", ic.getPisc_b())
																		.addValue("V_PATERNO", ic.getPaterno())
																		.addValue("V_OBSERVM2", ic.getObservm2())
																		.addValue("V_OBSERVM1", ic.getObservm1())
																		.addValue("V_OBSERV", ic.getObserv())
																		.addValue("V_OBS_ATENDIO", ic.getObs_atendio())
																		.addValue("V_NUM_MED", ic.getNum_med())
																		.addValue("V_NUM_C", ic.getNum_c())
																		.addValue("V_NSE_F", ic.getNse_f())
																		.addValue("V_NOMBRE", ic.getNombre())
																		.addValue("V_NIV_FINC", ic.getNiv_finc())
																		.addValue("V_N_CONEX", ic.getNroConexiones())
																		.addValue("V_MZA_C", ic.getMza_c())
																		.addValue("V_MOT_NO_ING", ic.getMot_no_ing())
																		.addValue("V_MNTO_CARTOG", ic.getMnto_cartog())
																		.addValue("V_MED3", ic.getMedidorEncontrado3())
																		.addValue("V_MED2", ic.getMedidorEncontrado2())
																		.addValue("V_MATERNO", ic.getMaterno())
																		.addValue("V_MAT_VALV_S", ic.getMat_valv_s())
																		.addValue("V_MAT_VALV_E", ic.getMat_valv_e())
																		.addValue("V_LOTE_C", ic.getLote_c())
																		.addValue("V_LONG_CNX_DES", ic.getLong_cnx_des())
																		.addValue("V_LEC_MED3", ic.getLec_med3())
																		.addValue("V_LEC_MED2", ic.getLec_med2())
																		.addValue("V_LEC", ic.getLecturaMedidor())
																		.addValue("V_LAVA_F", ic.getLava_f())
																		.addValue("V_LAVA_CL", ic.getLava_cl())
																		.addValue("V_LAVA_B", ic.getLava_b())
																		.addValue("V_INSPEC_REALIZ", ic.getInspec_realiz())
																		.addValue("V_INOD_F", ic.getInod_f())
																		.addValue("V_INOD_CL", ic.getInod_cl())
																		.addValue("V_INOD_B", ic.getInod_b())
																		.addValue("V_IND_TRAM_DES", ic.getInd_tram_des())
																		.addValue("V_IND_OCUP", ic.getInd_ocup())
																		.addValue("V_IND_DESC", ic.getInd_desc())
																		.addValue("V_INCI_MEDIDOR", ic.getInci_medidor())
																		.addValue("V_IMPOSIBILIDAD", ic.getImposibilidad())
																		.addValue("V_HORA_I", ic.getHora_i())
																		.addValue("V_HORA_F", ic.getHora_f())
																		.addValue("V_GRIFO_F", ic.getGrifo_f())
																		.addValue("V_GRIFO_CL", ic.getGrifo_cl())
																		.addValue("V_GRIFO_B", ic.getGrifo_b())
																		.addValue("V_FUN_MED1", ic.getEstMedidorEncontrado1())
																		.addValue("V_FUGA_CAJ", ic.getFugaCaja())
																		.addValue("V_FTE_CONEXION_C", ic.getFte_conexion_c())
																		.addValue("V_FTE_CNXC", ic.getFte_conexion())
																		.addValue("V_FEC_VIS", ic.getFechaVisita())
																		.addValue("V_FAX", ic.getFax())
																		.addValue("V_ESTADO", ic.getEstado())
																		.addValue("V_EST_TUB_DES", ic.getEst_tub_des())
																		.addValue("V_EST_TAPA_DES", ic.getEst_tapa_des())
																		.addValue("V_EST_TAPA", ic.getEst_tapa())
																		.addValue("V_EST_OCUP", ic.getEst_ocup())
																		.addValue("V_EST_DESC", ic.getEst_desc())
																		.addValue("V_EST_CAJA_DES", ic.getEst_caja_des())
																		.addValue("V_EST_CAJA", ic.getEstadoCaja())
																		.addValue("V_EMAIL", ic.getEmail())
																		.addValue("V_DUPLI_C", ic.getDupli_c())
																		.addValue("V_DUCH_F", ic.getDuch_f())
																		.addValue("V_DUCH_CL", ic.getDuch_cl())
																		.addValue("V_DUCH_B", ic.getDuch_b())
																		.addValue("V_DOM_OCUP", ic.getDom_ocup())
																		.addValue("V_DOM_DESC", ic.getDom_desc())
																		.addValue("V_DNI", ic.getDni())
																		.addValue("V_DIST_C", ic.getDist_c())
																		.addValue("V_DISPOSIT", ic.getDisposit())
																		.addValue("V_DIA_MED3", ic.getDia_med3())
																		.addValue("V_DIA_MED2", ic.getDia_med2())
																		.addValue("V_DIA_MED", ic.getDia_med())
																		.addValue("V_DIA_CONEX_DES", ic.getDia_conex_des())
																		.addValue("V_DIA_CONEX_AGUA", ic.getDia_conex_agua())
																		.addValue("V_COTA_VER_DES", ic.getCota_ver_des())
																		.addValue("V_COTA_SUM_C", ic.getCota_sum_c())
																		.addValue("V_COTA_HOR_DES", ic.getCota_hor_des())
																		.addValue("V_CORR_CUP", ic.getCorr_cup())
																		.addValue("V_COM_OCUP", ic.getCom_ocup())
																		.addValue("V_COM_DESC", ic.getCom_desc())
																		.addValue("V_COD_UBICC", ic.getCod_ubic())
																		.addValue("V_COD_SUP", ic.getCod_sup())
																		.addValue("V_COD_EMP", ic.getCodEmpleado())
																		.addValue("V_COD_DIGIT", ic.getCod_digit())
																		.addValue("V_CNX3_CON", ic.getCnx3_con())
																		.addValue("V_CNX2_CON", ic.getCnx2_con())
																		.addValue("V_CNX_CON", ic.getCnxCon())
																		.addValue("V_CLANDES_DES", ic.getClandes_des())
																		.addValue("V_CLANDES", ic.getClandes())
																		.addValue("V_CISTE_F", ic.getCiste_f())
																		.addValue("V_CISTE_CL", ic.getCiste_cl())
																		.addValue("V_CISTE_B", ic.getCiste_b())
																		.addValue("V_CEL_CLI_C", ic.getCel_cli_c())
																		.addValue("V_CARTOGRAFIA", ic.getCartografia())
																		.addValue("V_CALLE_C", ic.getCalle_c())
																		.addValue("V_CAJA", ic.getCaja())
																		.addValue("V_BIDET_F", ic.getBidet_f())
																		.addValue("V_BIDET_CL", ic.getBidet_cl())
																		.addValue("V_BIDET_B", ic.getBidet_b())
																		.addValue("V_ATENDIO", ic.getAtendio())
																		.addValue("V_ABAST", ic.getAbast())
																		.addValue("V_A_V_USUMOD",sUsuario);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: InspeccionesComercialesDAOImpl - actualizarDistComLine()] - "+e.getMessage());
		}
		return out;
	}

	private List<InspeccionesComerciales> mapeaInspeccionesComerciales(Map<String, Object> resultados)
	{
		InspeccionesComerciales item;
		List<InspeccionesComerciales> listaInspeccionesComerciales = new ArrayList<>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		for(Map<String, Object> map : lista) {
			item = new InspeccionesComerciales();
			item.setCodigoCarga((String)map.get("v_idcarga"));
			item.setCodigoRegistro(((BigDecimal)map.get("n_idreg")).intValue());
			item.setOficinaComercial((String)map.get("of_comer"));
			item.setGeneroUsuario((String)map.get("gen_por"));
			item.setCus((String)map.get("cus"));
			item.setRuta((String)map.get("ruta"));
			item.setItin((String)map.get("itin"));
			item.setTipsum((String)map.get("tipsum"));
			item.setFechaEmision((String)map.get("fec_emis"));
			item.setObservacion((String)map.get("observac"));
			item.setNombreRazon((String)map.get("nom_raz"));
			item.setLeRuc((String)map.get("le_ruc"));
			item.setTelefonoFax((String)map.get("iame_fax"));
			item.setCodAbastecimiento((String)map.get("cod_abas"));
			item.setReferenciaDireccion((String)map.get("refer"));
			item.setCua((String)map.get("cua"));
			item.setMedidor((String)map.get("medidor"));
			item.setDiametroMedidor((String)map.get("diam"));
			item.setUltLectura((String)map.get("ult_lec"));
			item.setPtoMedia((String)map.get("pto_med"));
			item.setAcom((String)map.get("acom"));
			item.setNisRad(((BigDecimal)map.get("nis_rad")).intValue());
			item.setNumOS(((BigDecimal)map.get("num_os")).intValue());
			item.setTipOS((String)map.get("tip_os"));
			item.setFechaResolucion((String)map.get("fe_res"));
			item.setCodEmpleado((String)map.get("cod_emp"));
			item.setFechaVisita((String)map.get("fec_vis"));
			item.setCoAccejec((String)map.get("co_accej"));
			item.setServ((String)map.get("serv"));
			item.setServDT((String)map.get("serv_dt"));
			item.setCaja((String)map.get("caja"));
			item.setEstadoCaja((String)map.get("est_caja"));
			item.setCnxCon((String)map.get("cnx_con"));
			item.setCxCDT((String)map.get("cx_c_dt"));
			item.setLecturaMedidor((String)map.get("lec"));
			item.setEstMedidorEncontrado1((String)map.get("fun_med1"));
            item.setEstMedidorEncontrado2((String)map.get("fun_med2"));
            item.setEstMedidorEncontrado3((String)map.get("fun_med3"));
            item.setPrescinto((String)map.get("precint"));
            item.setFugaCaja((String)map.get("fuga_caj"));
            item.setNroConexiones((String)map.get("n_conex"));
            item.setMedidorEncontrado2((String)map.get("med2"));
            item.setMedidorEncontrado3((String)map.get("med3"));
            item.setNiple2((String)map.get("niple2"));
            item.setNiple3((String)map.get("niple3"));
            item.setValvulas((String)map.get("valvulas"));
            item.setDisposit((String)map.get("disposit"));
            item.setClandes((String)map.get("clandes"));
            item.setNiv_finc((String)map.get("niv_finc"));
            item.setEstado((String)map.get("estado"));
            item.setN_habit((String)map.get("n_habit"));
            item.setUu_ocup((String)map.get("uu_ocup"));
            item.setSoc_ocup((String)map.get("soc_ocup"));
            item.setDom_ocup((String)map.get("dom_ocup"));
            item.setCom_ocup((String)map.get("com_ocup"));
            item.setInd_ocup((String)map.get("ind_ocup"));
            item.setEst_ocup((String)map.get("est_ocup"));
            item.setUu_docup((String)map.get("uu_docup"));
            item.setSoc_desc((String)map.get("soc_desc"));
            item.setDom_desc((String)map.get("dom_desc"));
            item.setCom_desc((String)map.get("com_desc"));
            item.setInd_desc((String)map.get("ind_desc"));
            item.setEst_desc((String)map.get("est_desc"));
            item.setUso_unic((String)map.get("uso_unic"));
            item.setCalle((String)map.get("calle"));
            item.setNumero((String)map.get("numero"));
            item.setDuplicad((String)map.get("duplicad"));
            item.setMza((String)map.get("mza"));
            item.setLote((String)map.get("lote"));
            item.setCgv((String)map.get("cgv"));
            item.setUrbaniza((String)map.get("urbaniza"));
            item.setDistrito((String)map.get("distrito"));
            item.setCalle_c((String)map.get("calle_c"));
            item.setNum_c((String)map.get("num_c"));
            item.setDupli_c((String)map.get("dupli_c"));
            item.setMza_c((String)map.get("mza_c"));
            item.setLote_c((String)map.get("lote_c"));
            item.setUrb_c((String)map.get("urb_c"));
            item.setRef_c((String)map.get("ref_c"));
            item.setDist_c((String)map.get("dist_c"));
            item.setAtendio((String)map.get("atendio"));
            item.setPaterno((String)map.get("paterno"));
            item.setMaterno((String)map.get("materno"));
            item.setNombre((String)map.get("nombre"));
            item.setRazsoc((String)map.get("razsoc"));
            item.setDni((String)map.get("dni"));
            item.setTelef((String)map.get("telef"));
            item.setFax((String)map.get("fax"));
            item.setRuc((String)map.get("ruc"));
            item.setInod_b((String)map.get("inod_b"));
            item.setInod_f((String)map.get("inod_f"));
            item.setInod_cl((String)map.get("inod_cl"));
            item.setLava_b((String)map.get("lava_b"));
            item.setLava_f((String)map.get("lava_f"));
            item.setLava_cl((String)map.get("lava_cl"));
            item.setDuch_b((String)map.get("duch_b"));
            item.setDuch_f((String)map.get("duch_f"));
            item.setDuch_cl((String)map.get("duch_cl"));
            item.setUrin_b((String)map.get("urin_b"));
            item.setUrin_f((String)map.get("urin_f"));
            item.setUrin_cl((String)map.get("urin_cl"));
            item.setGrifo_b((String)map.get("grifo_b"));
            item.setGrifo_f((String)map.get("grifo_f"));
            item.setGrifo_cl((String)map.get("grifo_cl"));
            item.setCiste_b((String)map.get("ciste_b"));
            item.setCiste_f((String)map.get("ciste_f"));
            item.setCiste_cl((String)map.get("ciste_cl"));
            item.setTanqu_b((String)map.get("tanqu_b"));
            item.setTanqu_f((String)map.get("tanqu_f"));
            item.setTanqu_cl((String)map.get("tanqu_cl"));
            item.setPisc_b((String)map.get("pisc_b"));
            item.setPisc_f((String)map.get("pisc_f"));
            item.setPisc_cl((String)map.get("pisc_cl"));
            item.setBidet_b((String)map.get("bidet_b"));
            item.setBidet_f((String)map.get("bidet_f"));
            item.setBidet_cl((String)map.get("bidet_cl"));
            item.setAbast((String)map.get("abast"));
            item.setPtome_ca((String)map.get("ptome_ca"));
            item.setNum_p((String)map.get("num_p"));
            item.setDup_p((String)map.get("dup_p"));
            item.setMza_p((String)map.get("mza_p"));
            item.setLote_p((String)map.get("lote_p"));
            item.setObserv((String)map.get("observ"));
            item.setObservm1((String)map.get("observm1"));
            item.setObservm2((String)map.get("observm2"));
            item.setCota_sum_c((String)map.get("cota_sum_c"));
            item.setPisos_c((String)map.get("pisos_c"));
            item.setCod_ubic_c((String)map.get("cod_ubic_c"));
            item.setFte_conexion_c((String)map.get("fte_conexion_c"));
            item.setCup_c((String)map.get("cup_c"));
            item.setCota_sum((String)map.get("cota_sum"));
            item.setPisos((String)map.get("pisos"));
            item.setCod_ubic((String)map.get("cod_ubic"));
            item.setFte_conexion((String)map.get("fte_conexion"));
            item.setCup((String)map.get("cup"));
            item.setDevuelto((String)map.get("devuelto"));
            item.setDate_d((String)map.get("fecha_d"));
            item.setPrior((String)map.get("prior_1"));
            item.setActualiza((String)map.get("actualiza"));
            item.setDate_c(map.get("fecha_c") != null ? new Date(((Timestamp)map.get("fecha_c")).getTime()) : null);
            item.setResuelto((String)map.get("resuelto"));
            item.setMulti((String)map.get("multi"));
            item.setParcial((String)map.get("parcial"));
            item.setFte_cnx((String)map.get("fte_cnx"));
            item.setCota_sumc((String)map.get("cota_sumc"));
            item.setPisosc((String)map.get("pisosc"));
            item.setCod_ubicc((String)map.get("cod_ubicc"));
            item.setFte_cnxc((String)map.get("fte_cnxc"));
            item.setCupc((String)map.get("cupc"));
            item.setHora_i((String)map.get("hora_i"));
            item.setHora_f((String)map.get("hora_f"));
            item.setValv_e_tlsc((String)map.get("valv_e_tlsc"));
            item.setMat_valv_e((String)map.get("mat_valv_e"));
            item.setValv_s_p((String)map.get("valv_s_p"));
            item.setMat_valv_s((String)map.get("mat_valv_s"));
			item.setSector(((BigDecimal)map.get("sector")).intValue());
            item.setCup_pred((String)map.get("cup_pred"));
			item.setAol(((BigDecimal)map.get("aol")).intValue());
            item.setCiclo((String)map.get("ciclo"));
            item.setNreclamo((String)map.get("nreclamo"));
            item.setCelular_cli(((BigDecimal)map.get("celular_cli")).doubleValue());
            item.setFax_cli(((BigDecimal)map.get("fax_cli")).intValue());
            item.setNiv_predio(((BigDecimal)map.get("niv_predio")).intValue());
            item.setNse_pre((String)map.get("nse_pre"));
            item.setSoc_ocup_cat(((BigDecimal)map.get("soc_ocup_cat")).intValue());
            item.setDom_ocup_cat(((BigDecimal)map.get("dom_ocup_cat")).intValue());
            item.setCom_ocup_cat(((BigDecimal)map.get("com_ocup_cat")).intValue());
            item.setInd_ocup_cat(((BigDecimal)map.get("ind_ocup_cat")).intValue());
            item.setEst_ocup_cat(((BigDecimal)map.get("est_ocup_cat")).intValue());
            item.setSoc_desoc_cat(((BigDecimal)map.get("soc_desoc_cat")).intValue());
            item.setDom_desoc_cat(((BigDecimal)map.get("dom_desoc_cat")).intValue());
            item.setCom_desoc_cat(((BigDecimal)map.get("com_desoc_cat")).intValue());
            item.setInd_desoc_cat(((BigDecimal)map.get("ind_desoc_cat")).intValue());
            item.setEst_desoc_cat(((BigDecimal)map.get("est_desoc_cat")).intValue());
            item.setEst_sum((String)map.get("est_sum"));
            item.setFte_conex((String)map.get("fte_conex"));
            item.setUbic_pred((String)map.get("ubic_pred"));
            item.setDia_conex(((BigDecimal)map.get("dia_conex")).doubleValue());
            item.setCota_conex(((BigDecimal)map.get("cota_conex")).doubleValue());
            item.setDisp_seg((String)map.get("DISP_SEG"));
            item.setTip_lec((String)map.get("tip_lec"));
            item.setHor_abasc((String)map.get("hor_abasc"));
            item.setNia(((BigDecimal)map.get("nia")).intValue());
            item.setDia_alcan((String)map.get("dia_alcan"));
            item.setFte_alcan((String)map.get("fte_alcan"));
            item.setUbic_alcan((String)map.get("ubic_alcan"));
            item.setTip_suelo_alca((String)map.get("tip_suelo_alcan"));
            item.setTip_descar_alc((String)map.get("tip_descar_alcan"));
            item.setCotah_alcan(((BigDecimal)map.get("cotah_alcan")).doubleValue());
            item.setCotav_alcan(((BigDecimal)map.get("cotav_alcan")).doubleValue());
            item.setLong_alcan(((BigDecimal)map.get("long_alcan")).doubleValue());
            item.setProfund_alcan(((BigDecimal)map.get("profund_alcan")).doubleValue());
            item.setTr_grasas(((BigDecimal)map.get("tr_grasas")).intValue());
            item.setSistrat((String)map.get("sistrat"));
            item.setMat_tapa_alcan((String)map.get("mat_tapa_alcan"));
            item.setEst_tapa_alcan((String)map.get("est_tapa_alcan"));
            item.setMat_caja_alcan((String)map.get("mat_caja_alcan"));
            item.setEst_caja_alcan((String)map.get("est_caja_alcan"));
            item.setMat_tub_alcan((String)map.get("mat_tub_alcan"));
            item.setEst_tub_alcan((String)map.get("est_tub_alcan"));
            item.setEmail((String)map.get("email"));            
            item.setCel_cli_c(((BigDecimal)map.get("cel_cli_c")).doubleValue());
            item.setTelf_emp(((BigDecimal)map.get("telf_emp")).intValue());
            item.setPiso_dpto_f((String)map.get("piso_dpto_f"));
            item.setSector_f(((BigDecimal)map.get("sector_f")).intValue());
            item.setFte_finca((String)map.get("fte_finca"));
            item.setNse_f((String)map.get("nse_f"));
            item.setObs_f((String)map.get("obs_f"));
            item.setCorr_cup((String)map.get("corr_cup"));
            item.setPred_no_ubic((String)map.get("pred_no_ubic"));
            item.setVar_act_predio((String)map.get("var_act_predio"));
            item.setInspec_realiz((String)map.get("inspec_realiz"));
            item.setMot_no_ing((String)map.get("mot_no_ing"));
            item.setDia_conex_agua(((BigDecimal)map.get("dia_conex_agua")).doubleValue());
            item.setImposibilidad((String)map.get("imposibilidad"));
            item.setSeguro_tap((String)map.get("seguro_tap"));
            item.setEst_tapa((String)map.get("est_tapa"));
            item.setInci_medidor((String)map.get("inci_medidor"));
            item.setAcceso_inmueb((String)map.get("acceso_inmueb"));
            item.setObs_atendio((String)map.get("obs_atendio"));
            item.setCartografia((String)map.get("cartografia"));
            item.setMnto_cartog((String)map.get("mnto_cartog"));
            item.setCorr_cup_f((String)map.get("corr_cup_f"));
            item.setNum_med((String)map.get("num_med"));
            item.setDia_med((String)map.get("dia_med"));
            item.setCnx2_con((String)map.get("cnx2_con"));
            item.setCnx3_con((String)map.get("cnx3_con"));
            item.setDia_med2((String)map.get("dia_med2"));
            item.setDia_med3((String)map.get("dia_med3"));
            item.setLec_med2(((BigDecimal)map.get("lec_med2")).intValue());
            item.setLec_med3(((BigDecimal)map.get("lec_med3")).intValue());            
            item.setCota_hor_des(((BigDecimal)map.get("cota_hor_des")).doubleValue());
            item.setCota_ver_des(((BigDecimal)map.get("cota_ver_des")).doubleValue());
            item.setLong_cnx_des(((BigDecimal)map.get("long_cnx_des")).doubleValue());
            item.setProf_cja_des(((BigDecimal)map.get("prof_cja_des")).doubleValue());
            item.setTip_suelo_des((String)map.get("tip_suelo_des"));
            item.setInd_tram_des((String)map.get("ind_tram_des"));
            item.setDia_conex_des((String)map.get("dia_conex_des"));
            item.setSilo((String)map.get("silo"));
            item.setEst_tapa_des((String)map.get("est_tapa_des"));
            item.setEst_caja_des((String)map.get("est_caja_des"));
            item.setEst_tub_des((String)map.get("est_tub_des"));
            item.setTip_mat_tap_d((String)map.get("tip_mat_tap_d"));
            item.setTip_mat_caj_d((String)map.get("tip_mat_caj_d"));
            item.setTip_mat_tub_d((String)map.get("tip_mat_tub_d"));
            item.setSis_trat_res((String)map.get("sis_trat_res"));
            item.setTip_des_des((String)map.get("tip_des_des"));
            item.setClandes_des((String)map.get("clandes_des"));
            item.setCod_sup(((BigDecimal)map.get("cod_sup")).intValue());            
            item.setCod_digit(((BigDecimal)map.get("cod_digit")).intValue());
            listaInspeccionesComerciales.add(item);		
			if (map.get("totalRegistros") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("totalRegistros")).intValue());
			}
		}
		return listaInspeccionesComerciales;
	}

	
}
