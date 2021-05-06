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
import pe.com.sedapal.agc.dao.ICicloDAO;
import pe.com.sedapal.agc.mapper.CicloDetalleMapper;
import pe.com.sedapal.agc.mapper.CicloMapper;
import pe.com.sedapal.agc.model.Ciclo;
import pe.com.sedapal.agc.model.CicloDetalle;
import pe.com.sedapal.agc.model.request.CicloDetalleRequest;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class CicloDAOImpl implements ICicloDAO{
	private static final Logger logger = LoggerFactory.getLogger(CicloDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	private Error error;
	private Paginacion paginacion;

	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}

	@Override
	public Error getError() {
		return this.error;
	}
	
	@Override
	public List<Ciclo> listarCiclo(CicloRequest cicloRequest) {
		this.paginacion = new Paginacion();
		this.error = null;
		
		Map<String, Object> out = null;
		List<Ciclo> listaCiclo = new ArrayList<Ciclo>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LISTAR_CICLO)
				.declareParameters(
						new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
						new SqlParameter("V_IDOFIC", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_PERIODO", cicloRequest.getD_periodo())  
					.addValue("V_IDOFIC", cicloRequest.getN_idofic());			
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				listaCiclo = CicloMapper.mapRows(out);			
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al obtener ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al obtener ciclo", e.getMessage());
		}				
		return listaCiclo;
	}
	

	@Override
	public List<Ciclo>  modificarCiclo(CicloRequest cicloRequest) {
		this.error = null;
		Map<String, Object> out = null;
		List<Ciclo> listaCiclo = new ArrayList<Ciclo>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_MODIFICAR_CICLO)
				.declareParameters(
						new SqlParameter("N_IDCICLO", OracleTypes.NUMBER),
						new SqlParameter("N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("D_PERIODO", OracleTypes.VARCHAR),
						new SqlParameter("N_CICLO", OracleTypes.NUMBER),
						new SqlParameter("N_ID_ESTADO", OracleTypes.NUMBER),
						new SqlParameter("V_USU_MODI", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("N_IDCICLO", cicloRequest.getN_idciclo())
					.addValue("N_IDOFIC", cicloRequest.getN_idofic()) 
					.addValue("D_PERIODO", cicloRequest.getD_periodo()) 
					.addValue("N_CICLO", cicloRequest.getN_ciclo())
					.addValue("N_ID_ESTADO", cicloRequest.getN_id_estado())
					.addValue("V_USU_MODI", cicloRequest.getA_v_usumod());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				
				listaCiclo = CicloMapper.mapRows(out);	
				
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al modificar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al modificar ciclo", e.getMessage());
		}		
		return listaCiclo;
	}

	@Override
	public List<Ciclo>  registrarCiclo(CicloRequest cicloRequest) {
		this.error = null;
		Map<String, Object> out = null;
		List<Ciclo> listaCiclo = new ArrayList<Ciclo>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REGISTRAR_CICLO)
				.declareParameters(
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_D_PERIODO", OracleTypes.VARCHAR),
						new SqlParameter("V_N_CANT_PERIODOS", OracleTypes.NUMBER),	
						new SqlParameter("V_USU_CREA", OracleTypes.VARCHAR),				
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDOFIC", cicloRequest.getN_idofic()) 
					.addValue("V_D_PERIODO", cicloRequest.getD_periodo())
					.addValue("V_N_CANT_PERIODOS", cicloRequest.getN_cant_periodos())
					.addValue("V_USU_CREA", cicloRequest.getA_v_usucre());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				listaCiclo = CicloMapper.mapRows(out);			
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al registrar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al registrar ciclo", e.getMessage());
		}
		return listaCiclo;
	}

	@Override
	public Integer eliminarCiclo(CicloRequest cicloRequest) {
		this.error = null;
		Integer resultado = 0;
		Map<String, Object> out = null;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_ELIMINAR_CICLO)
				.declareParameters(
						new SqlParameter("V_N_IDCICLO", OracleTypes.NUMBER),
						new SqlParameter("V_USU_MOD", OracleTypes.VARCHAR),						
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDCICLO", cicloRequest.getN_idciclo())
					.addValue("V_USU_MOD", cicloRequest.getA_v_usumod());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				Integer resultadoValidacion = ((BigDecimal) out.get("N_EJEC")).intValue();
				if(resultadoValidacion == 1) {
					String mensajeValidacion= (String)out.get("V_EJEC");
					this.error = new Error(resultado, mensajeValidacion);					
				}
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al eliminar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al eliminar ciclo", e.getMessage());
		}		
		return resultado;
	}
	
	@Override
	public List<CicloDetalle> listarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		this.paginacion = new Paginacion();
		this.error = null;
		
		Map<String, Object> out = null;
		List<CicloDetalle> listaCicloDetalle = new ArrayList<CicloDetalle>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_LISTAR_CICLO_DETALLE)
				.declareParameters(
						new SqlParameter("V_N_IDCICLO", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDCICLO", cicloDetalleRequest.getN_idciclo());			
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				listaCicloDetalle = CicloDetalleMapper.mapRows(out);			
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al obtener ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al obtener ciclo", e.getMessage());
		}				
		return listaCicloDetalle;
	}
	

	@Override
	public List<CicloDetalle>  modificarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		this.error = null;
		Map<String, Object> out = null;
		List<CicloDetalle> listaCicloDetalle = new ArrayList<CicloDetalle>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_MODIFICAR_CICLO_DETALLE)
				.declareParameters(
						new SqlParameter("N_IDCICLO", OracleTypes.NUMBER),
						new SqlParameter("N_IDCICLODET", OracleTypes.NUMBER),
						new SqlParameter("V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_IDACCION", OracleTypes.VARCHAR),
						new SqlParameter("N_ID_ESTADO", OracleTypes.NUMBER),
						new SqlParameter("V_USU_MODI", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("N_IDCICLO", cicloDetalleRequest.getN_idciclo())
					.addValue("N_IDCICLODET", cicloDetalleRequest.getN_idciclodet()) 
					.addValue("V_IDACTI", cicloDetalleRequest.getV_idacti()) 
					.addValue("V_IDACCION", cicloDetalleRequest.getV_idaccion())
					.addValue("N_ID_ESTADO", cicloDetalleRequest.getN_id_estado())
					.addValue("V_USU_MODI", cicloDetalleRequest.getA_v_usumod());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				
				listaCicloDetalle = CicloDetalleMapper.mapRows(out);	
				
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al modificar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al modificar ciclo", e.getMessage());
		}		
		return listaCicloDetalle;
	}

	@Override
	public List<CicloDetalle>  registrarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		this.error = null;
		Map<String, Object> out = null;
		List<CicloDetalle> listaCicloDetalle = new ArrayList<CicloDetalle>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_REGISTRAR_CICLO_DETALLE)
				.declareParameters(
						new SqlParameter("V_N_IDCICLO", OracleTypes.NUMBER),
						new SqlParameter("V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_IDACCION", OracleTypes.VARCHAR),
						new SqlParameter("V_USU_CREA", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDCICLO", cicloDetalleRequest.getN_idciclo()) 
					.addValue("V_IDACTI", cicloDetalleRequest.getV_idacti()) 
					.addValue("V_IDACCION", cicloDetalleRequest.getV_idaccion())
					.addValue("V_USU_CREA", cicloDetalleRequest.getA_v_usucre());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				listaCicloDetalle = CicloDetalleMapper.mapRows(out);			
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al registrar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al registrar ciclo", e.getMessage());
		}
		return listaCicloDetalle;
	}

	@Override
	public List<CicloDetalle> eliminarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		Map<String, Object> out = null;
		List<CicloDetalle> listaCicloDetalle = new ArrayList<CicloDetalle>();
		Integer resultado = 0;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_ELIMINAR_CICLO_DETALLE)
				.declareParameters(
						new SqlParameter("V_N_IDCICLO", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDCICLODET", OracleTypes.NUMBER),
						new SqlParameter("V_USU_MODI", OracleTypes.VARCHAR),						
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDCICLO", cicloDetalleRequest.getN_idciclo())
					.addValue("V_N_IDCICLODET", cicloDetalleRequest.getN_idciclodet())
					.addValue("V_USU_MODI", cicloDetalleRequest.getA_v_usumod());
		
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if(resultado == 1) {
				listaCicloDetalle = CicloDetalleMapper.mapRows(out);			
			} else {
				Integer codigoErrorBD = ((BigDecimal)out.get("N_EJEC")).intValue();
				String mensajeErrorBD = (String)out.get("V_EJEC");
				this.error = new Error(codigoErrorBD, mensajeErrorBD);
				logger.error("Error al registrar ciclo", mensajeErrorBD);
			}
		}catch(Exception e){
			this.error = new Error(resultado,e.getMessage());			
			logger.error("Error al registrar ciclo", e.getMessage());
		}
		return listaCicloDetalle;
	
	}
}
