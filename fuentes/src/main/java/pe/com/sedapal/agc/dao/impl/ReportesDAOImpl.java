package pe.com.sedapal.agc.dao.impl;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IReportesDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.TipoCargoMapper;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.ProgramaValores;
import pe.com.sedapal.agc.model.Rendimientos;
import pe.com.sedapal.agc.model.RepoCumpActiCierre;
import pe.com.sedapal.agc.model.RepoCumpActiInsp;
import pe.com.sedapal.agc.model.RepoCumpActiNoti;
import pe.com.sedapal.agc.model.RepoCumpActiReapertura;
import pe.com.sedapal.agc.model.RepoCumpActiReci;
import pe.com.sedapal.agc.model.RepoCumpCicloLector;
import pe.com.sedapal.agc.model.RepoEfecActiAvisCob;
import pe.com.sedapal.agc.model.RepoEfecActiTomaEst;
import pe.com.sedapal.agc.model.RepoEfecApertura;
import pe.com.sedapal.agc.model.RepoEfecCierre;
import pe.com.sedapal.agc.model.RepoEfecInspeComer;
import pe.com.sedapal.agc.model.RepoEfecInspeInt;
import pe.com.sedapal.agc.model.RepoEfecLectorTomaEst;
import pe.com.sedapal.agc.model.RepoEfecNotificaciones;
import pe.com.sedapal.agc.model.RepoEfecSostenibilidad;
import pe.com.sedapal.agc.model.RepoInfActiEjec;
import pe.com.sedapal.agc.model.request.ReportesRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.CharacterUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class ReportesDAOImpl implements IReportesDAO {

	private Error error;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Override
	public Error getError() {
		return this.error;
	}
//	@Override
//	public void cleanError() {
//		this.error = null;
//	}
	
	@Override
	public List<RepoInfActiEjec> obtenerListaRepoInfActiEjec(ReportesRequest request) {
		List<RepoInfActiEjec> reportes = new ArrayList<RepoInfActiEjec>();
		String nombrePRC = null;
		
		if(request.getV_subacti() == null) {
			nombrePRC = DbConstants.PRC_REPO_INF_ACTI_EJEC;
		}else {
			nombrePRC = DbConstants.PRC_REPO_INF_SUBACTI_EJEC;
		}

		
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(nombrePRC)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_IDSUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_PERFIL", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
					new SqlParameter("V_USUARIO", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoInfActiEjec.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_IDACTI", request.getV_idacti())
				.addValue("V_IDSUBACTI", request.getV_subacti())
				.addValue("V_PERFIL", request.getV_perfil())
				.addValue("V_PERIODO", request.getV_periodo())
				.addValue("V_USUARIO", request.getV_usuario());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoInfActiEjec>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	@Override
	public List<String> obtenerPeriodos() {
		List<String> periodos = new ArrayList<String>();

		try {
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_REPORTES)
					.withProcedureName(DbConstants.PRC_REPO_PERIODOS)
					.declareParameters(
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource();
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				periodos = (List<String>)out.get("O_CURSOR");				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");					
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+e.getMessage());
		}
		return periodos;	
		
		
	}
	
	@Override
	public List<String> obtenerTipoInspe() {
		List<String> periodos = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LIST_TIPO_INSPE)
				.declareParameters(
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			MapSqlParameterSource in = new MapSqlParameterSource();
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				periodos = (List<String>)out.get("O_CURSOR");				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");					
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: ReportesDAOImpl - obtenerTipoInspe()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: ReportesDAOImpl - obtenerTipoInspe()] - "+e.getMessage());
		}
		return periodos;	
	}
	
	
	@Override
	public List<String> obtenerItems(String oficina) {
		List<String> items = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_OBTENER_ITEM)
				.declareParameters(
				    new SqlParameter("V_N_IDOFIC", OracleTypes.VARCHAR),	
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			
			MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDOFIC", oficina);
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			items = (List<String>)out.get("O_CURSOR");
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return items;
	}
	
	@Override
	public List<String> obtenerActividades(Long item) {
		List<String> actividades = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_OBTENER_ACTIVIDAD_REND)
				.declareParameters(	
				    new SqlParameter("V_N_ITEM", OracleTypes.NUMBER),	
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			
			MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_ITEM", item);
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			actividades = (List<String>)out.get("O_CURSOR");
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return actividades;
	}
	
	@Override
	public List<String> obtenerSubactividades(Long item, String actividad) {
		List<String> subactividades = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_OBTENER_SUBACTIVIDAD_REND)
				.declareParameters(	
				    new SqlParameter("V_N_ITEM", OracleTypes.NUMBER),	
				    new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),	
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_ITEM", item)
					.addValue("V_V_IDACTI", actividad);
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			subactividades = (List<String>)out.get("O_CURSOR");
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return subactividades;
	}
	
	@Override
	public List<String> obtenerCiclos(String periodo) {
		List<String> ciclos = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CICLOS)
				.declareParameters(
				    new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),	
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			
			MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_PERIODO", periodo);
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			ciclos = (List<String>)out.get("O_CURSOR");
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return ciclos;
	}

	@Override
	public List<String> obtenerUsuarios(ReportesRequest request) {
		List<String> lstUsuarios = new ArrayList<String>();
		CharacterUtil characterUtil = new CharacterUtil();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_OBTIENE_USUARIOS_EMPRESA)
				.declareParameters(
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
					new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
					new SqlOutParameter("LST_USUARIOS", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("V_N_IDCONTRATI", request.getN_idcontrati());
			
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			lstUsuarios = (List<String>) out.get("LST_USUARIOS");
			String value = "";
			
			for (int e=0; e<=lstUsuarios.size()-1; e++) {
				value = characterUtil.reemplazaCharSet(((Object) lstUsuarios.get(e)).toString());
				value = value.replace("{REG=", "");
				value = value.replace("}}", "}");
				lstUsuarios.set(e, value);				
			}
			
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return lstUsuarios;	}

	@Override
	public List<String> obtenerSubactividades(ReportesRequest request) {
		List<String> lstSubacti = new ArrayList<String>();
		CharacterUtil characterUtil = new CharacterUtil();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_OBTIENE_SUBACTIVIDADES)
				.declareParameters(
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
					new SqlOutParameter("LST_SUBACTI", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_N_IDOFIC", request.getN_idofic());
			
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			lstSubacti = (List<String>) out.get("LST_SUBACTI");
		    String value = "";
			for (int e=0; e<=lstSubacti.size()-1; e++) {
				value = characterUtil.reemplazaCharSet(((Object) lstSubacti.get(e)).toString());
				value = value.replace("{REG=", "");
				value = value.replace("}}", "}");
				lstSubacti.set(e, value);				
			}
			
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+exception.getMessage());
		}
		return lstSubacti;
	}

	@Override
	public List<String> obtenerFrecAlerta() {
		List<String> frecuencia = new ArrayList<String>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LIST_FREC_ALERTA)
				.declareParameters(
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			MapSqlParameterSource in = new MapSqlParameterSource();
			Map<String, Object> out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				frecuencia = (List<String>)out.get("O_CURSOR");				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");					
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: ReportesDAOImpl - obtenerFrecAlerta()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: ReportesDAOImpl - obtenerFrecAlerta()] - "+e.getMessage());
		}
		return frecuencia;	
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaValores> listarProgramaValores(ProgramaValores request) {    	    	
		List<ProgramaValores> lista = new ArrayList<ProgramaValores>();
		SimpleJdbcCall caller = new SimpleJdbcCall(this.jdbcTemplate);
		caller.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LST_MON_PROGRAMA_MENSUAL)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter(
								"O_CURSOR", OracleTypes.CURSOR, new RowMapper<ProgramaValores>() {
									@Override
									public ProgramaValores mapRow(ResultSet rs, int rowNum) throws SQLException {
										ProgramaValores record = new ProgramaValores();
										record.setN_id_programa_mensual(rs.getLong(1));
										record.setN_id_ofic(rs.getLong(2));
										record.setV_n_id_contrati(rs.getLong(3));
										record.setV_idacti(rs.getString(4));
										record.setV_idsubacti_1(rs.getString(5));
										record.setV_idsubacti_2(rs.getString(6));
										record.setN_val_prog_mensual(rs.getBigDecimal(7));
										record.setN_id_estado(rs.getInt(8));
										record.setD_periodo(rs.getDate(9));
										record.setV_periodo(rs.getString(10));
										record.setN_val_prog_total(rs.getBigDecimal(11));
										record.setN_cant_periodos(rs.getBigDecimal(12));
										record.setA_v_usucre(rs.getString(13));
										record.setA_d_feccre(rs.getDate(14));
										record.setA_v_usumod(rs.getString(15));
										record.setA_d_fecmod(rs.getDate(16));
										record.setV_v_mes_inicio(rs.getBigDecimal(17));
										record.setV_v_anio_inicio(rs.getInt(18));
										record.setV_editable(rs.getInt(19));
										return record;										
									}
								}))
				.withSchemaName(DbConstants.DBSCHEMA);

		MapSqlParameterSource in = new MapSqlParameterSource()
		.addValue("V_V_IDACTI", request.getV_idacti())
		.addValue("V_N_IDOFIC", request.getN_id_ofic())
		.addValue("V_N_IDCONTRATI", request.getV_n_id_contrati());
		Map<String, Object> results = caller.execute(in);		
		lista = (List<ProgramaValores>) results.get("O_CURSOR");
		return lista;
	}
	
	
    @SuppressWarnings("unchecked")
	@Override
    public List<ProgramaValores> crearProgramaValores(ProgramaValores request) {
		List<ProgramaValores> lista = new ArrayList<ProgramaValores>();
		Map<String, Object> out = null;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_CREA_PROGRAMA_VALORES)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_2", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
						new SqlParameter("V_N_VAL_PROG_TOTAL", OracleTypes.NUMBER),
						new SqlParameter("V_N_CANT_PERIODOS", OracleTypes.NUMBER),
						new SqlParameter("V_V_USUCRE", OracleTypes.VARCHAR),
						new SqlParameter("V_V_MES_INICIO", OracleTypes.INTEGER),
						new SqlParameter("V_V_ANIO_INICIO", OracleTypes.INTEGER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter(
								"O_CURSOR", OracleTypes.CURSOR, new RowMapper<ProgramaValores>() {
									@Override
									public ProgramaValores mapRow(ResultSet rs, int rowNum) throws SQLException {
										ProgramaValores record = new ProgramaValores();
										record.setN_id_programa_mensual(rs.getLong(1));
										record.setN_id_ofic(rs.getLong(2));
										record.setV_n_id_contrati(rs.getLong(3));
										record.setV_idacti(rs.getString(4));
										record.setV_idsubacti_1(rs.getString(5));
										record.setV_idsubacti_2(rs.getString(6));
										record.setN_val_prog_mensual(rs.getBigDecimal(7));
										record.setN_id_estado(rs.getInt(8));
										record.setD_periodo(rs.getDate(9));
										record.setV_periodo(rs.getString(10));
										record.setN_val_prog_total(rs.getBigDecimal(11));
										record.setN_cant_periodos(rs.getBigDecimal(12));
										record.setA_v_usucre(rs.getString(13));
										record.setA_d_feccre(rs.getDate(14));
										record.setA_v_usumod(rs.getString(15));
										record.setA_d_fecmod(rs.getDate(16));
										record.setV_v_mes_inicio(rs.getBigDecimal(17));
										record.setV_v_anio_inicio(rs.getInt(18));
										record.setV_editable(rs.getInt(19));
										return record;										
									}
								}));
				this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(ProgramaValores.class));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())
				.addValue("V_V_IDSUBACTI_2", request.getV_idsubacti_2())
				.addValue("V_N_IDOFIC", request.getN_id_ofic())
				.addValue("V_N_IDCONTRATI", request.getV_n_id_contrati())
				.addValue("V_N_VAL_PROG_TOTAL", request.getN_val_prog_total())
				.addValue("V_N_CANT_PERIODOS", request.getN_cant_periodos())
				.addValue("V_V_USUCRE", request.getA_v_usucre())
				.addValue("V_V_MES_INICIO", request.getV_v_mes_inicio())
				.addValue("V_V_ANIO_INICIO", request.getV_v_anio_inicio());

		try {
			out = this.simpleJdbcCall.execute(in);
			lista = (List<ProgramaValores>) out.get("O_CURSOR");
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
		}
		return lista;
    }
    
    @Override
    public List<ProgramaValores> eliminarProgramaValores(ProgramaValores request) {
		List<ProgramaValores> lista = new ArrayList<ProgramaValores>();
		Map<String, Object> out = null;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_DEL_MON_PROGRAMA_MENSUAL)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
						new SqlParameter("V_V_USUCRE", OracleTypes.VARCHAR),
						new SqlParameter("V_V_MES_INICIO", OracleTypes.INTEGER),
						new SqlParameter("V_V_ANIO_INICIO", OracleTypes.INTEGER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter(
								"O_CURSOR", OracleTypes.CURSOR, new RowMapper<ProgramaValores>() {
									@Override
									public ProgramaValores mapRow(ResultSet rs, int rowNum) throws SQLException {
										ProgramaValores record = new ProgramaValores();
										record.setN_id_programa_mensual(rs.getLong(1));
										record.setN_id_ofic(rs.getLong(2));
										record.setV_n_id_contrati(rs.getLong(3));
										record.setV_idacti(rs.getString(4));
										record.setV_idsubacti_1(rs.getString(5));
										record.setV_idsubacti_2(rs.getString(6));
										record.setN_val_prog_mensual(rs.getBigDecimal(7));
										record.setN_id_estado(rs.getInt(8));
										record.setD_periodo(rs.getDate(9));
										record.setV_periodo(rs.getString(10));
										record.setN_val_prog_total(rs.getBigDecimal(11));
										record.setN_cant_periodos(rs.getBigDecimal(12));
										record.setA_v_usucre(rs.getString(13));
										record.setA_d_feccre(rs.getDate(14));
										record.setA_v_usumod(rs.getString(15));
										record.setA_d_fecmod(rs.getDate(16));
										record.setV_v_mes_inicio(rs.getBigDecimal(17));
										record.setV_v_anio_inicio(rs.getInt(18));
										record.setV_editable(rs.getInt(19));
										return record;										
									}
								}));
	
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_N_IDOFIC", request.getN_id_ofic())
				.addValue("V_N_IDCONTRATI", request.getV_n_id_contrati())
				.addValue("V_V_USUCRE", request.getA_v_usucre())
				.addValue("V_V_MES_INICIO", request.getV_v_mes_inicio())
				.addValue("V_V_ANIO_INICIO", request.getV_v_anio_inicio());
		try {
			out = this.simpleJdbcCall.execute(in);
			lista = (List<ProgramaValores>) out.get("O_CURSOR");
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
		}
		return lista;
    }
    
    

    @Override
    public Integer updateProgramaValores(ProgramaValores request) {
		Map<String, Object> out = null;
		Integer resultado = 0;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_UPD_MON_PROGRAMA_MENSUAL)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_2", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
						new SqlParameter("V_V_USUMOD", OracleTypes.VARCHAR),
						new SqlParameter("V_V_MES_INICIO", OracleTypes.INTEGER),
						new SqlParameter("V_V_ANIO_INICIO", OracleTypes.INTEGER),
						new SqlParameter("V_N_VAL_PROG_MENSUAL", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
	
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())
				.addValue("V_V_IDSUBACTI_2", request.getV_idsubacti_2())
				.addValue("V_N_IDOFIC", request.getN_id_ofic())
				.addValue("V_N_IDCONTRATI", request.getV_n_id_contrati())
				.addValue("V_V_USUMOD", request.getA_v_usumod())				
				.addValue("V_V_MES_INICIO", request.getV_v_mes_inicio())
				.addValue("V_V_ANIO_INICIO", request.getV_v_anio_inicio())
				.addValue("V_N_VAL_PROG_MENSUAL", request.getN_val_prog_mensual());

		try {
			out = this.simpleJdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
		}
		return resultado;
    }

	@Override
	public List<Rendimientos> listarRendimientos(Rendimientos request) {
		List<Rendimientos> lista = new ArrayList<Rendimientos>();
		Map<String, Object> out = null;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LST_MON_RENDIMIENTOS)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.NUMBER),
						new SqlParameter("N_N_NUMITEM", OracleTypes.NUMBER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter(
								"O_CURSOR", OracleTypes.CURSOR)
						)//fin del call
				.withSchemaName(DbConstants.DBSCHEMA);
//		this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(Rendimientos.class));
		MapSqlParameterSource in = new MapSqlParameterSource()
		.addValue("V_V_IDACTI", request.getV_idacti())
		.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())
		.addValue("N_N_NUMITEM", request.getItem().getCodigo());
		try {
			out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				lista = mapeaListadoRendimiento(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: ReportesDAOImpl - listarRendimientos()] - " + mensajeInterno);
			}
			
//			lista = (List<Rendimientos>) out.get("O_CURSOR");
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
		}
		return lista;
		}
	
	private List<Rendimientos> mapeaListadoRendimiento(Map<String, Object> out) throws Exception {
		Rendimientos item;

		List<Rendimientos> result = new ArrayList<Rendimientos>();
//		List<MonitoreoCab> listaMonitoreoCab = new ArrayList<MonitoreoCab>();

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("O_CURSOR");

		try {
			if (lista.size() > 0) {
				for (Map<String, Object> map : lista) {

					item = new Rendimientos();

					item.setN_idrendimiento(((BigDecimal) map.get("N_IDRENDIMIENTO")).longValue());
					item.setV_idacti_seda(((String) map.get("V_IDACTI_SEDA")));
					item.setV_idacti_seda_desc(((String) map.get("V_ACTIVIDAD_SEDAPAL_DESC")));
					item.setV_idacti(((String) map.get("V_IDACTI")));
					item.setV_idacti_desc(((String) map.get("V_ACTIVIDAD_DESC")));
					item.setV_ind_clte_espe(((String) map.get("V_IND_CLTE_ESPE")));
					item.setV_idsubacti_1(((String) map.get("V_IDSUBACTI_1")));
					item.setV_idsubacti_2(((String) map.get("V_IDSUBACTI_2")));
					item.setN_numitem(((BigDecimal) map.get("N_NUMITEM")).longValue());
					item.setN_id_estado(((BigDecimal) map.get("N_ID_ESTADO")).intValue());
					item.setA_v_usumod(((String) map.get("A_V_USUMOD")));
					item.setA_v_usucre(((String) map.get("A_V_USUCRE")));
					item.setV_uni_medida(map.get("V_UNI_MEDIDA")!=null?((String) map.get("V_UNI_MEDIDA")):"");
					item.setN_numcuad(map.get("N_VALOR_CUADRILLA")!=null?((BigDecimal) map.get("N_VALOR_CUADRILLA")).longValue():0);
//					item.setN_numcuad((map.get("N_VALOR_CUADRILLA") == null)?((BigDecimal) map.get("N_VALOR_CUADRILLA")).longValue():0);
					item.setN_valor_trabajador(map.get("N_VALOR_TRABAJADOR")!=null?((BigDecimal) map.get("N_VALOR_TRABAJADOR")).longValue():0);
					item.setN_valor_suministro(map.get("N_VALOR_SUMINISTRO")!=null?((BigDecimal) map.get("N_VALOR_SUMINISTRO")).longValue():0);
					
//					item.setN_valor_trabajador(((BigDecimal) map.get("N_VALOR_TRABAJADOR")).longValue());
//					item.setN_valor_suministro(((BigDecimal) map.get("N_VALOR_SUMINISTRO")).longValue());
					item.setV_codigo_ub1(((String) map.get("V_CODIGO_SUB1")));
					item.setV_codigo_val2(((String) map.get("V_CODIGO_VAL2")));
					item.setV_codigo_val1(((String) map.get("V_CODIGO_VAL1")));
					item.setV_editable(((BigDecimal) map.get("V_EDITABLE")).intValue());
					if (map.get("A_D_FECCRE") != null && !map.get("A_D_FECCRE").equals(" ")) {
						Timestamp fecAsig = (Timestamp) map.get("A_D_FECCRE");
						item.setA_d_feccre(new SimpleDateFormat("dd/MM/yyyy").format(fecAsig));
					}
					if (map.get("A_D_FECMOD") != null && !map.get("A_D_FECMOD").equals(" ")) {
						Timestamp fecAsig = (Timestamp) map.get("A_D_FECMOD");
						item.setA_d_feccre(new SimpleDateFormat("dd/MM/yyyy").format(fecAsig));
					}

					result.add(item);
				}
			} else {
				result = null;
			}
		} catch (Exception e) {

			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
			throw new Exception();
		}


		return result;
	}	

    @SuppressWarnings("unchecked")
	@Override
    public String crearRendimientos(Rendimientos request) {
    	String mensaje = "";
		Map<String, Object> out = null;
//		String subActividad = request.getV_idsubacti_1().split("\\.")[0].toString();
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_CREA_MON_RENDIMIENTOS)
				.declareParameters(
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_2", OracleTypes.VARCHAR),
						new SqlParameter("V_N_NUMITEM", OracleTypes.NUMBER),
						new SqlParameter("V_N_VALOR_CUADRILLA", OracleTypes.INTEGER),
						new SqlParameter("V_V_USUCRE", OracleTypes.VARCHAR),
						new SqlParameter("V_V_UNI_MEDIDA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_VALOR_TRABAJADOR", OracleTypes.INTEGER),
						new SqlParameter("V_N_VALOR_SUMINISTRO", OracleTypes.INTEGER),
						new SqlParameter("V_V_CODIGO_VAL2", OracleTypes.VARCHAR),
						new SqlParameter("V_V_CODIGO_VAL1", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR))
				.withSchemaName(DbConstants.DBSCHEMA);
//				this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(Rendimientos.class));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())
				.addValue("V_V_IDSUBACTI_2", request.getV_idsubacti_2())				
				.addValue("V_N_NUMITEM", request.getN_numitem())
				.addValue("V_V_USUCRE", request.getA_v_usucre())
				.addValue("V_V_UNI_MEDIDA", request.getV_uni_medida())
				.addValue("V_N_VALOR_CUADRILLA", request.getN_numcuad())
				.addValue("V_N_VALOR_TRABAJADOR", request.getN_valor_trabajador())
				.addValue("V_N_VALOR_SUMINISTRO", request.getN_valor_suministro())
				.addValue("V_V_CODIGO_VAL2", request.getV_codigo_val2())
				.addValue("V_V_CODIGO_VAL1", request.getV_codigo_val1());
		try {
			out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				mensaje = (String) out.get("V_EJEC");
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - " + mensajeInterno);
			}
//			lista = (List<Rendimientos>) out.get("O_CURSOR");
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - obtenerPeriodos()] - "+ e.getMessage());
		}
		return mensaje;
    }

	@Override
	public Integer updateRendimientos(Rendimientos request) {
		Map<String, Object> out = null;
		Integer resultado = 0;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_UPD_MON_RENDIMIENTOS)
				.declareParameters(
						new SqlParameter("V_N_IDRENDIMIENTO", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDACTI_SEDAPAL", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IND_CLTE_ESPE", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDSUBACTI_2", OracleTypes.VARCHAR),
						new SqlParameter("V_N_NUMITEM", OracleTypes.NUMBER),
						new SqlParameter("V_V_UNI_MEDIDA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_VALOR_CUADRILLA", OracleTypes.NUMBER),						
						new SqlParameter("V_N_VALOR_TRABAJADOR", OracleTypes.NUMBER),
						new SqlParameter("V_N_VALOR_SUMINISTRO", OracleTypes.NUMBER),
						new SqlParameter("V_V_CODIGO_VAL1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_CODIGO_VAL2", OracleTypes.VARCHAR),
						new SqlParameter("V_V_USUCRE", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
	
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI_SEDAPAL", request.getV_idacti_seda())
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_IND_CLTE_ESPE", request.getV_ind_clte_espe())
				.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())				
				.addValue("V_V_IDSUBACTI_2", request.getV_idsubacti_2())
				.addValue("V_N_NUMITEM", request.getN_numitem())
				.addValue("V_V_USUCRE", request.getA_v_usucre())
				.addValue("V_N_IDRENDIMIENTO", request.getN_idrendimiento())	
				.addValue("V_V_UNI_MEDIDA", request.getV_uni_medida())	
				.addValue("V_N_VALOR_CUADRILLA", request.getN_numcuad())	
				.addValue("V_N_VALOR_TRABAJADOR", request.getN_valor_trabajador())	
				.addValue("V_N_VALOR_SUMINISTRO", request.getN_valor_suministro())		
				.addValue("V_V_CODIGO_VAL1", request.getV_codigo_val1())	
				.addValue("V_V_CODIGO_VAL2", request.getV_codigo_val2())		
				;
		try {
			out = this.simpleJdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - actualizarRendimientos()] - "+ e.getMessage());
		}
		return resultado;	
	}

	@Override
	public List<Rendimientos> eliminarRendimientos(Rendimientos request) {
		List<Rendimientos> lista = new ArrayList<Rendimientos>();
		Map<String, Object> out = null;
		this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_DEL_MON_RENDIMIENTOS)
				.declareParameters(
						new SqlParameter("V_N_IDRENDIMIENTO", OracleTypes.NUMBER),
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_N_NUMITEM", OracleTypes.NUMBER),
						new SqlParameter("V_V_IDSUBACTI_1", OracleTypes.VARCHAR),
						new SqlParameter("V_V_USUCRE", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR));
		
				
//		this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(Rendimientos.class));		
				MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_N_NUMITEM", request.getN_numitem())
				.addValue("V_N_IDRENDIMIENTO", request.getN_idrendimiento())
				.addValue("V_V_IDSUBACTI_1", request.getV_idsubacti_1())
				.addValue("V_V_USUCRE", request.getA_v_usucre());
		try {
			out = this.simpleJdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				lista = mapeaListadoRendimiento(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: ReportesDAOImpl - eliminarRendimientos()] - "+ e.getMessage());
		}
		return lista;
	}
	
	
	@Override
	public List<RepoEfecActiTomaEst> obtenerListaRepoEfecActiTomaEst(ReportesRequest request) {
		List<RepoEfecActiTomaEst> reportes = new ArrayList<RepoEfecActiTomaEst>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_ACTI_TOMA_EST)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.NUMBER),
					new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
					new SqlParameter("V_CICLO", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecActiTomaEst.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO", request.getV_periodo())
				.addValue("V_CICLO", request.getV_ciclo());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecActiTomaEst>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecLectorTomaEst> obtenerListaRepoEfecLectorTomaEst(ReportesRequest request) {
		List<RepoEfecLectorTomaEst> reportes = new ArrayList<RepoEfecLectorTomaEst>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_LECTOR_TOMA_EST)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlParameter("V_COD_LECTOR", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecLectorTomaEst.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final())
				.addValue("V_COD_LECTOR", request.getV_codigo_lector());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecLectorTomaEst>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecNotificaciones> obtenerListaRepoEfecNotificaciones(ReportesRequest request) {
		List<RepoEfecNotificaciones> reportes = new ArrayList<RepoEfecNotificaciones>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_NOTIFICACION)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecNotificaciones.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecNotificaciones>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecInspeComer> obtenerListaRepoEfecInspeComer(ReportesRequest request) {
		List<RepoEfecInspeComer> reportes = new ArrayList<RepoEfecInspeComer>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_INSPE_COMER)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlParameter("V_TIPO_INSPE", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecInspeComer.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final())
				.addValue("V_TIPO_INSPE", request.getV_tipo_inspe());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecInspeComer>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecActiAvisCob> obtenerListaRepoEfecActiAvisCob(ReportesRequest request) {
		List<RepoEfecActiAvisCob> reportes = new ArrayList<RepoEfecActiAvisCob>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_ACTI_AVIS_COB)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecActiAvisCob.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecActiAvisCob>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}

	@Override
	public List<RepoEfecInspeInt> obtenerListaRepoEfecInspeInt(ReportesRequest request) {
		List<RepoEfecInspeInt> reportes = new ArrayList<RepoEfecInspeInt>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_INSPE_COMER_INT)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlParameter("V_TIPO_INSPE", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecInspeInt.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final())
				.addValue("V_TIPO_INSPE", request.getV_tipo_inspe());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecInspeInt>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecCierre> obtenerListaRepoEfecCierre(ReportesRequest request) {
		List<RepoEfecCierre> reportes = new ArrayList<RepoEfecCierre>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_CIERRE)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecCierre.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecCierre>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecApertura> obtenerListaRepoEfecApertura(ReportesRequest request) {
		List<RepoEfecApertura> reportes = new ArrayList<RepoEfecApertura>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_REAPERTURA)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecApertura.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecApertura>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecSostenibilidad> obtenerListaRepoEfecSostenibilidad(ReportesRequest request) {
		List<RepoEfecSostenibilidad> reportes = new ArrayList<RepoEfecSostenibilidad>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_SOSTENIBILIDAD)
				.declareParameters(
					new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
					new SqlParameter("V_N_ITEM", OracleTypes.NUMBER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_V_SUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_CICLO", OracleTypes.NUMBER),
					new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecSostenibilidad.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_IDCONTRATI", request.getV_n_idempr())
				.addValue("V_N_ITEM", request.getV_n_item())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_SUBACTI", request.getV_subacti())
				.addValue("V_CICLO", request.getV_ciclo())
				.addValue("V_PERIODO", request.getV_periodo());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecSostenibilidad>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpCicloLector> obtenerListaRepoCumpCicloLector(ReportesRequest request) {
		List<RepoCumpCicloLector> reportes = new ArrayList<RepoCumpCicloLector>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_CICLO_LECTOR)
				.declareParameters(
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_V_SUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_CICLO", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO", OracleTypes.INTEGER),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpCicloLector.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_ITEM", request.getV_n_item())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_SUBACTI", request.getV_subacti())
				.addValue("V_CICLO", request.getV_ciclo())
				.addValue("V_PERIODO", request.getV_periodo());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpCicloLector>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpActiNoti> obtenerListaRepoCumpActiNoti(ReportesRequest request) {
		List<RepoCumpActiNoti> reportes = new ArrayList<RepoCumpActiNoti>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_NOTIFICACION)
				.declareParameters(
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_V_SUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("D_FEMISION", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpActiNoti.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("V_N_ITEM", request.getV_n_item())
					.addValue("V_N_IDOFIC", request.getN_idofic())
					.addValue("V_V_IDACTI", request.getV_idacti())
					.addValue("V_V_SUBACTI", request.getV_subacti())
				    .addValue("D_FEMISION", request.getD_femision());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpActiNoti>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpActiReci> obtenerListaRepoCumpActiReci(ReportesRequest request) {
		List<RepoCumpActiReci> reportes = new ArrayList<RepoCumpActiReci>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_RECIBI_DIARIO)
				.declareParameters(
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_V_SUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_CICLO", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpActiReci.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("V_N_ITEM", request.getV_n_item())
					.addValue("V_N_IDOFIC", request.getN_idofic())
					.addValue("V_V_IDACTI", request.getV_idacti())
					.addValue("V_V_SUBACTI", request.getV_subacti())
					.addValue("V_CICLO", request.getV_ciclo())
					.addValue("V_PERIODO", request.getV_periodo());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpActiReci>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpActiInsp> obtenerListaRepoCumpActiInsp(ReportesRequest request) {
		List<RepoCumpActiInsp> reportes = new ArrayList<RepoCumpActiInsp>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_INSP_COMER)
				.declareParameters(
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),	
					new SqlParameter("D_FEMISION", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpActiInsp.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_ITEM", request.getV_n_item())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("D_FEMISION", request.getD_femision());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpActiInsp>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpActiCierre> obtenerListaRepoCumpActiCierre(ReportesRequest request) {
		List<RepoCumpActiCierre> reportes = new ArrayList<RepoCumpActiCierre>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_CIERRE_SIMPLE)
				.declareParameters(
					new SqlParameter("V_N_IDEMPR", OracleTypes.INTEGER),
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_V_SUBACTI", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpActiCierre.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_IDEMPR", request.getV_n_idempr())
				.addValue("V_N_ITEM", request.getV_n_item())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("V_V_IDACTI", request.getV_idacti())
				.addValue("V_V_SUBACTI", request.getV_subacti())
				.addValue("V_PERIODO", request.getV_periodo());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpActiCierre>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoCumpActiReapertura> obtenerListaRepoCumpActiReapertura(ReportesRequest request) {
		List<RepoCumpActiReapertura> reportes = new ArrayList<RepoCumpActiReapertura>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_EFEC_REAPERTURA)
				.declareParameters(
					new SqlParameter("N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("N_IDCONTRATI", OracleTypes.INTEGER),
					new SqlParameter("V_PERIODO_INICIO", OracleTypes.VARCHAR),
					new SqlParameter("V_PERIODO_FIN", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoCumpActiReapertura.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("N_IDOFIC", request.getN_idofic())
				.addValue("N_IDCONTRATI", request.getN_idcontrati())
				.addValue("V_PERIODO_INICIO", request.getV_periodo_inicio())
				.addValue("V_PERIODO_FIN", request.getV_periodo_final());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoCumpActiReapertura>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
	@Override
	public List<RepoEfecNotificaciones> obtenerListaRepoCumpNotificaciones(ReportesRequest request) {
		List<RepoEfecNotificaciones> reportes = new ArrayList<RepoEfecNotificaciones>();
		try {			
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REPO_CUMP_NOTIFICACIONES)
				.declareParameters(
					new SqlParameter("V_N_IDEMPR", OracleTypes.INTEGER),
					new SqlParameter("V_N_ITEM", OracleTypes.INTEGER),
					new SqlParameter("V_N_IDOFIC", OracleTypes.INTEGER),
					new SqlParameter("D_FEMISION", OracleTypes.VARCHAR),
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("O_CURSOR", BeanPropertyRowMapper.newInstance(RepoEfecNotificaciones.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_IDEMPR", request.getV_n_idempr())
				.addValue("V_N_ITEM", request.getV_n_item())
				.addValue("V_N_IDOFIC", request.getN_idofic())
				.addValue("D_FEMISION", request.getD_femision());
			
			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				reportes = (List<RepoEfecNotificaciones>) executionOracle.get("O_CURSOR");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return reportes;
	}
	
}
