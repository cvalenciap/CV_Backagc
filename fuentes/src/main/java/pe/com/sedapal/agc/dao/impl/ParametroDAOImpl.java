package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
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
import pe.com.sedapal.agc.dao.IParametroDAO;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;
import pe.com.sedapal.agc.model.response.Error;

@Service
public class ParametroDAOImpl implements IParametroDAO {

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Error error;
	public Error getError() {
		return this.error;
	}
	
	private Paginacion paginacion;
	public Paginacion getPaginacion() {
		return this.paginacion;
	}
	
	
	@Override
	public Map<String,Object> listarTipoParametro(Parametro tipoParametro, PageRequest pageRequest){
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_LIS_TIPO_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTAPARA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCPARA", OracleTypes.VARCHAR),
							new SqlParameter("I_NPAGINA", OracleTypes.NUMBER),
							new SqlParameter("I_NREGISTROS", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", tipoParametro.getCodigo())
																	.addValue("V_V_ESTAPARA", tipoParametro.getEstado())
																	.addValue("V_V_DESCPARA", tipoParametro.getDetalle())
																	.addValue("I_NPAGINA", pageRequest.getPagina())
																	.addValue("I_NREGISTROS", pageRequest.getRegistros())
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - listarTipoParametro()] - "+e.getMessage());
		}
		
		return out;
		
	}
	
	public Integer registrarTipoParametro(Parametro tipoParametro, String usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_REG_TIPO_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_V_DESCPARA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_V_DESCPARA", tipoParametro.getDetalle())
																		.addValue("V_A_V_USUCRE", usuario);
			out = this.jdbcCall.execute(paraMap);
				resultado = (BigDecimal) out.get("N_RESP");
			if(resultado.intValue() != 1) {				
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC").toString();
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);			
			}
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - registrarTipoParametro()] - "+e.getMessage());
		}
		return resultado.intValue();
	}
	public Integer modificarTipoParametro(Parametro tipoParametro,String Usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_MOD_TIPO_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTAPARA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCPARA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", tipoParametro.getCodigo())
																	.addValue("V_V_ESTAPARA", tipoParametro.getEstado())
																	.addValue("V_V_DESCPARA", tipoParametro.getDetalle())
																	.addValue("V_A_V_USUMOD", Usuario);
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - modificarTipoParametro()] - "+e.getMessage());
		}
		
		return resultado.intValue();
	}
	public Integer eliminarTipoParametro(Parametro tipoParametro, String usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_ELI_TIPO_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", tipoParametro.getCodigo())
																	.addValue("V_A_V_USUMOD", usuario);
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - eliminarTipoParametro()] - "+e.getMessage());
		}
		
		return resultado.intValue();
	}
	@Override
	public Map<String,Object> obtenerTipoParametro(Parametro tipoParametro){
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_OBT_TIPO_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", tipoParametro.getCodigo());
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - obtenerTipoParametro()] - "+e.getMessage());
		}
		
		return out;
		
	}
	@Override
	public Map<String,Object> listarParametros(Parametro parametro, PageRequest pageRequest){
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_LIS_PARAMETRO_PARAM)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_N_IDPARAMETR", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTADESC", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCDETA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCCORT", OracleTypes.VARCHAR),
							new SqlParameter("V_V_IDVALO", OracleTypes.VARCHAR),
							new SqlParameter("I_NPAGINA", OracleTypes.NUMBER),
							new SqlParameter("I_NREGISTROS", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", parametro.getTipo())
																	.addValue("V_N_IDPARAMETR", parametro.getCodigo())
																	.addValue("V_V_ESTADESC", parametro.getEstado())
																	.addValue("V_V_DESCDETA", parametro.getDetalle())
																	.addValue("V_V_DESCCORT", parametro.getDescripcionCorta())
																	.addValue("V_V_IDVALO", parametro.getValor())
																	.addValue("I_NPAGINA", pageRequest.getPagina())
																	.addValue("I_NREGISTROS", pageRequest.getRegistros())
																	;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - listarParametros()] - "+e.getMessage());
		}
		
		return out;
	}
	@Override
	public Integer registrarParametro(Parametro parametro, String usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_REG_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_V_DESCDETA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCCORT", OracleTypes.VARCHAR),
							new SqlParameter("V_V_IDVALO", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", parametro.getTipo())
																		.addValue("V_V_DESCDETA", parametro.getDetalle())
																		.addValue("V_V_DESCCORT", parametro.getDescripcionCorta())
																		.addValue("V_V_IDVALO", parametro.getValor())
																		.addValue("V_A_V_USUCRE", usuario);
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
			
			if(resultado.intValue() != 1) {				
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC").toString();
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);			
			}
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - registrarParametro()] - "+e.getMessage());
		}
		return resultado.intValue();
	}
	@Override
	public Integer modificarParametro(Parametro parametro,String Usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_MOD_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_N_IDPARAMETR", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTADESC", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCDETA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCCORT", OracleTypes.VARCHAR),
							new SqlParameter("V_V_IDVALO", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", parametro.getTipo())
																	.addValue("V_N_IDPARAMETR", parametro.getCodigo())
																	.addValue("V_V_ESTADESC", parametro.getEstado())
																	.addValue("V_V_DESCDETA", parametro.getDetalle())
																	.addValue("V_V_DESCCORT", parametro.getDescripcionCorta())
																	.addValue("V_V_IDVALO", parametro.getValor())
																	.addValue("V_A_V_USUMOD", Usuario);
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - modificarParametro()] - "+e.getMessage());
		}
		
		return resultado.intValue();
	}
	@Override
	public Integer eliminarParametro(Parametro parametro, String usuario) {
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_ELI_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_N_IDPARAMETR", OracleTypes.NUMBER),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", parametro.getTipo())
																	.addValue("V_N_IDPARAMETR", parametro.getCodigo())
																	.addValue("V_A_V_USUMOD", usuario);
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - eliminarParametro()] - "+e.getMessage());
		}
		
		return resultado.intValue();
	}
	@Override
	public Map<String,Object> obtenerParametro(Parametro parametro){
		Map<String, Object> out = null;
		BigDecimal resultado = new BigDecimal("0");
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTPARAMETRO)
					.withProcedureName(DbConstants.PRC_OBT_PARAMETRO)
					.declareParameters(
							new SqlParameter("V_N_IDTIPOPARA", OracleTypes.NUMBER),
							new SqlParameter("V_N_IDPARAMETR", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDTIPOPARA", parametro.getTipo())
																		.addValue("V_N_IDPARAMETR", parametro.getCodigo());
																		;
			out = this.jdbcCall.execute(paraMap);
			if(out!=null)
				resultado = (BigDecimal) out.get("N_RESP");
		}
		catch (Exception e){
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: ParametroDAOImpl - obtenerParametro()] - "+e.getMessage());
		}
		
		return out;
		
	}
}
