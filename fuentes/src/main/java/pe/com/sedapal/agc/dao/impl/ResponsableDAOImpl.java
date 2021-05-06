package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.sql.Array;
import java.sql.Connection;

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
import oracle.jdbc.driver.OracleConnection;
import pe.com.sedapal.agc.dao.IResponsableDAO;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class ResponsableDAOImpl implements IResponsableDAO{

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Error error;
	public Error getError() {
		return this.error;
	}
	
	
	@Override
	public Map<String, Object> obtenerEmpresa() {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_EMPRESA)
					.declareParameters(
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			
			out = this.jdbcCall.execute();
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - obtenerEmpresa()] - "+e.getMessage());
		}
		
		return out;
	}

	@Override
	public Map<String, Object> obtenerOficina(Integer idEmpresa) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_OFICINA_EMPRESA)
					.declareParameters(
							new SqlParameter("V_N_IDEMPR",OracleTypes.INTEGER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_N_IDEMPR", idEmpresa);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - obtenerOficina()] - "+e.getMessage());
		}
		
		return out;
	}

	@Override
	public Map<String, Object> obtenerActividad(Integer idOficina, Integer idEmpresa) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_ACTIVIDAD)
					.declareParameters(
							new SqlParameter("V_N_IDEMPR",OracleTypes.INTEGER),
							new SqlParameter("V_N_IDOFIC",OracleTypes.INTEGER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_N_IDEMPR", idEmpresa)
					.addValue("V_N_IDOFIC", idOficina);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - obtenerActividad()] - "+e.getMessage());
		}
		
		return out;
	}
	
	@Override
	public Map<String, Object> obtenerPersonal(Integer idOficina, Integer idEmpresa, String idActividad) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_PERSONAL)
					.declareParameters(
							new SqlParameter("V_N_IDOFIC",OracleTypes.INTEGER),
							new SqlParameter("V_N_IDEMPR",OracleTypes.INTEGER),
							new SqlParameter("V_V_IDACTI",OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_N_IDOFIC", idOficina)
					.addValue("V_N_IDEMPR", idEmpresa)
					.addValue("V_V_IDACTI", idActividad);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - obtenerPersonal()] - "+e.getMessage());
		}
		
		return out;
	}
	
	@Override
	public Map<String, Object> obtenerPersonalSeleccionado(Integer idOficina, Integer idEmpresa, String idActividad) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_PERSONAL_SELEC)
					.declareParameters(
							new SqlParameter("V_N_IDOFIC",OracleTypes.INTEGER),
							new SqlParameter("V_N_IDEMPR",OracleTypes.INTEGER),
							new SqlParameter("V_V_IDACTI",OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_N_IDOFIC", idOficina)
					.addValue("V_N_IDEMPR", idEmpresa)
					.addValue("V_V_IDACTI", idActividad);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - obtenerPersonalSeleccionado()] - "+e.getMessage());
		}
		
		return out;
	}
	public Map<String, Object> guardarPersonalSeleccionado(List<Integer> listaIdPersona,Integer idOficina, Integer idEmpresa, String idActividad,Integer idGrupo, String usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_REG_PERSONAL_SELEC)
					.declareParameters(
							new SqlParameter("V_N_IDOFIC",OracleTypes.NUMBER),
							new SqlParameter("V_N_IDEMPR",OracleTypes.NUMBER),
							new SqlParameter("V_V_IDACTI",OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDGRUP",OracleTypes.NUMBER),
							new SqlParameter("V_V_USUR",OracleTypes.VARCHAR),
							new SqlParameter("A_V_IDPERS",OracleTypes.ARRAY),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			
			Connection conn = this.jdbcCall.getJdbcTemplate().getDataSource().getConnection().unwrap(OracleConnection.class);
			Array array = ((OracleConnection) conn).createOracleArray("AGC.SYS_PLSQL_COD_TRAB_ARRAY", listaIdPersona.toArray());
			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_N_IDOFIC", idOficina)
					.addValue("V_N_IDEMPR", idEmpresa)
					.addValue("V_V_IDACTI", idActividad)
					.addValue("V_N_IDGRUP", idGrupo)
					.addValue("V_V_USUR", usuario)
					.addValue("A_V_IDPERS", array);
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ResponsableDAOImpl - guardarPersonalSeleccionado()] - "+e.getMessage());
		}
		
		return out;
	}
}
