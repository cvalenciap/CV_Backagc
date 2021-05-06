package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IEmpresaDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.EmpresaMapper;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.request.EmpresaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.MantenimientoEmpresas;
import pe.com.sedapal.agc.util.DbConstants;


@Service
public class EmpresaDAOImpl implements IEmpresaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	private Error error;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Override
	public Error getError() {
		return this.error;
	}

	@Override
	public List<Empresa> getCompaniesFromRepository(EmpresaRequest empresaRequest, PageRequest pageRequest) {
		List<Empresa> companies = new ArrayList<Empresa>();
		try {
			this.error = null;
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA).withProcedureName(DbConstants.PRC_LIS_EMPRESA)
				.declareParameters(
					new SqlParameter("NESTADO", OracleTypes.VARCHAR),
					new SqlParameter("VTIPO", OracleTypes.VARCHAR),
					new SqlParameter("VNOMBRE", OracleTypes.VARCHAR),
					new SqlParameter("VRUC", OracleTypes.VARCHAR),
					new SqlParameter("I_FECHA_INI", OracleTypes.DATE),
					new SqlParameter("I_FECHA_FIN", OracleTypes.DATE),
					new SqlParameter("I_NPAGINA", OracleTypes.NUMBER),
					new SqlParameter("I_NREGISTROS", OracleTypes.NUMBER),
					new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
					new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			this.simpleJdbcCall.returningResultSet("C_OUT", BeanPropertyRowMapper.newInstance(Empresa.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("NESTADO", (empresaRequest.getEstadoEmpresa() != null)? empresaRequest.getEstadoEmpresa(): "")
				.addValue("VTIPO", (empresaRequest.getTipoEmpresa() != null)? empresaRequest.getTipoEmpresa(): "")
				.addValue("VNOMBRE", (empresaRequest.getRazonSocial() != null)? empresaRequest.getRazonSocial(): "")
				.addValue("VRUC", (empresaRequest.getRuc() != null)? empresaRequest.getRuc(): "")
				.addValue("I_FECHA_INI", (empresaRequest.getFechaInicio()!= null)? empresaRequest.getFechaInicio(): null)
				.addValue("I_FECHA_FIN", (empresaRequest.getFechaFin()!= null)? empresaRequest.getFechaFin(): null)
				.addValue("I_NPAGINA", pageRequest.getPagina())
				.addValue("I_NREGISTROS", pageRequest.getRegistros());

			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				companies = (List<Empresa>) executionOracle.get("C_OUT");
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompaniesFromRepository()] - "+exception.getMessage());
		}
		return companies;
	}

	@Override
	public boolean addCompanyToRepository(Empresa company, String user) {
		try{
			this.error = null;
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA).withProcedureName(DbConstants.PRC_REG_EMPRESA)
					.declareParameters(
						new SqlParameter("V_V_TIPOEMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_V_RUCEMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_V_NUMECONT", OracleTypes.VARCHAR),
						new SqlParameter("V_V_NOMBREMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_V_TELEEMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_V_DIREEMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_V_COMEEMPR", OracleTypes.VARCHAR),
						new SqlParameter("V_D_FECHINIVIGE", OracleTypes.DATE),
						new SqlParameter("V_D_FECHFINVIGE", OracleTypes.DATE),
						new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR)
					);

			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_V_TIPOEMPR", company.getTipoEmpresa())
				.addValue("V_V_RUCEMPR", company.getNroRUC())
				.addValue("V_V_NUMECONT", company.getNumeroContrato())
				.addValue("V_V_NOMBREMPR", company.getDescripcion())
				.addValue("V_V_TELEEMPR", company.getTelefono())
				.addValue("V_V_DIREEMPR", company.getDireccion())
				.addValue("V_V_COMEEMPR", company.getComentario())
				.addValue("V_D_FECHINIVIGE", java.sql.Date.valueOf(company.getFechaInicioVigencia()))
				.addValue("V_D_FECHFINVIGE", java.sql.Date.valueOf(company.getFechaFinVigencia()))				
				.addValue("V_A_V_USUCRE", user);

			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			BigDecimal executionCode = (BigDecimal) executionOracle.get("N_RESP");
			if (executionCode.intValue() != 1) { return false; } else { return true; }
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - addCompanyToRepository()] - "+exception.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteCompanyFromRepository(Integer code, String user) {
		try{
			this.error = null;
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA).withProcedureName(DbConstants.PRC_ANU_EMPRESA)
				.declareParameters(
					new SqlParameter("V_N_IDEMPR", OracleTypes.NUMBER),
					new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR)
				);

			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("V_N_IDEMPR", code)
					.addValue("V_A_V_USUMOD", user);

			Map<String, Object> executionDeleteOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			return validateExecutionOracle(executionDeleteOracle);
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - deleteCompanyFromRepository()] - "+exception.getMessage());
			return false;
		}
	}

	@Override
	public Empresa getCompanyByIdFromRepository(Integer code) {
		try{
			List<Empresa> companies = new ArrayList<Empresa>();
			this.error = null;
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA).withProcedureName(DbConstants.PRC_OBT_EMPRESA)
				.declareParameters(
					new SqlParameter("V_N_IDEMPR", OracleTypes.NUMBER)
				);

			this.simpleJdbcCall.returningResultSet("C_OUT", BeanPropertyRowMapper.newInstance(Empresa.class));
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("V_N_IDEMPR", code);

			Map<String, Object> executionOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if(this.error == null){
				companies = (List<Empresa>) executionOracle.get("C_OUT");
				return companies.get(0);
			} else {
				return null;
			}
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - getCompanyByIdFromRepository()] - "+exception.getMessage());
			return null;
		}
	}

	@Override
	public boolean updateCompanyFromRepository(Empresa company, String user) {
		try{
			this.error = null;
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA).withProcedureName(DbConstants.PRC_MOD_EMPRESA)
				.declareParameters(
					new SqlParameter("V_N_IDEMPR", OracleTypes.NUMBER),
					new SqlParameter("V_V_TIPOEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_RUCEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_NUMECONT", OracleTypes.VARCHAR),
					new SqlParameter("V_V_NOMBREMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_TELEEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_ESTAEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_DIREEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_V_COMEEMPR", OracleTypes.VARCHAR),
					new SqlParameter("V_D_FECHINIVIGE", OracleTypes.DATE),
					new SqlParameter("V_D_FECHFINVIGE", OracleTypes.DATE),
					new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR)
				);

			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("V_N_IDEMPR", company.getCodigo())
					.addValue("V_V_TIPOEMPR", company.getTipoEmpresa())
					.addValue("V_V_RUCEMPR", company.getNroRUC())
					.addValue("V_V_NUMECONT", company.getNumeroContrato())
					.addValue("V_V_NOMBREMPR", company.getDescripcion())
					.addValue("V_V_TELEEMPR", company.getTelefono())
					.addValue("V_V_ESTAEMPR", company.getEstado())
					.addValue("V_V_DIREEMPR", company.getDireccion())
					.addValue("V_V_COMEEMPR", company.getComentario())
					.addValue("V_D_FECHINIVIGE", java.sql.Date.valueOf(company.getFechaInicioVigencia()))
					.addValue("V_D_FECHFINVIGE", java.sql.Date.valueOf(company.getFechaFinVigencia()))
					.addValue("V_A_V_USUMOD", user);

			Map<String, Object> executionUpdateOracle = this.simpleJdbcCall.execute(mapSqlParameterSource);
			return validateExecutionOracle(executionUpdateOracle);
		} catch (Exception exception){
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: EmpresaDAOImpl - updateCompanyFromRepository()] - "+exception.getMessage());
			return false;
		}
	}

	private boolean validateExecutionOracle(Map<String, Object> executionOracle){
		BigDecimal executionUpdateCode = (BigDecimal) executionOracle.get("N_RESP");
		if (executionUpdateCode.intValue() != 1) { return false; } else { return true; }
	}

	@Override
	public Map<String, Object> listarEmpresaItem(Integer idEmpresa) {
		Map<String, Object> mapRespuestaBD = null;
		Map<String, Object> mapEmpresaItem = null;
		try {

			Integer resultadoEjecucion = 0;  
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA)
					.withProcedureName(DbConstants.PRC_LIS_EMPRESA_ITEM)
					.declareParameters(
							new SqlParameter("I_N_IDEMPR", OracleTypes.INTEGER),
							new SqlOutParameter("C_OUT_LIS_ITEM_DISP", OracleTypes.CURSOR),
							new SqlOutParameter("C_OUT_LIS_EMPR_ITEM", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("I_N_IDEMPR", idEmpresa);

			mapRespuestaBD = this.simpleJdbcCall.execute(paraMap);
			
			resultadoEjecucion = CastUtil.leerValorMapInteger(mapRespuestaBD, "O_N_RESP");
			if ( resultadoEjecucion == 1 ) {
				mapEmpresaItem = (Map<String, Object>) EmpresaMapper.mapRowsEmpresaItem(mapRespuestaBD);
			} else {
				Integer codigoErrorBD = ((BigDecimal) mapRespuestaBD.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) mapRespuestaBD.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_CONSULTA_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, "Error al obtener lista de Empresa Items."));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
				  new Error(Constantes.CodigoErrores.ERROR_SERVICIO, "Error al obtener lista de Empresa Items."));
		}
		return mapEmpresaItem;
	}

	@Override
	public void registrarEmpresaItem(String tramaItem, Integer idEmpresa) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTEMPRESA)
					.withProcedureName(DbConstants.PRC_REG_EMPRESA_ITEM)
					.declareParameters(new SqlParameter("I_N_IDEMPR", OracleTypes.NUMBER),
							new SqlParameter("I_V_TRAMA", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_IDEMPR", idEmpresa).addValue("I_V_TRAMA",
					tramaItem);
			out = this.simpleJdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_BD, MantenimientoEmpresas.MSE001));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, MantenimientoEmpresas.MSE001));
		}
	}

}
