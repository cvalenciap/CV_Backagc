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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IOficinaDAO;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class OficinaDAOImpl implements IOficinaDAO {

	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	public Oficina retornarOficinaLogin(Integer n_idpers) {
		Oficina oficina=null;
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_RET_OFICINA)
					.declareParameters(
							new SqlParameter("V_N_IDPERS", OracleTypes.NUMBER),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR)
							);
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDPERS", n_idpers);
			out = this.jdbcCall.execute(paraMap);
			List<Map<String, Object>> lista = (List<Map<String, Object>>)out.get("O_CURSOR");
			for(Map<String, Object> map : lista) {
				oficina = new Oficina();
				oficina.setCodigo(((BigDecimal)map.get("N_IDOFIC")).intValue());
				oficina.setDescripcion((String) map.get("V_DESCOFIC"));
			}
		}
		catch (Exception e){
			logger.error("[AGC: OficinaDAOImpl - retornarOficinaLogin()] - "+e.getMessage());
		}
		return oficina;
	}
	
	@Override
	public Map<String,Object> obtenerOficina(Oficina oficina) {

		Map<String, Object> out = null;
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_OFICINA_EMPRESA)
					.declareParameters(
							new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTAOFIC", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DIREOFIC", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCOFIC", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDEMPR", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
				
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDOFIC", oficina.getCodigo())
																		.addValue("V_V_ESTAOFIC", oficina.getEstado())
																		.addValue("V_V_DIREOFIC", oficina.getDireccion())
																		.addValue("V_V_DESCOFIC", oficina.getDescripcion())
																		.addValue("V_N_IDEMPR", oficina.getIdEmpresa())
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: OficinaDAOImpl - obtenerOficina()] - "+e.getMessage());
		}
		
		return out;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> obtenerGruposFuncionales(Integer idOficina, Integer idGrupo) {
		List<Map<String, String>> grupos = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_DEV_COMBO_FILTRO_USUARIO)
					.withProcedureName(DbConstants.PRC_OBTENER_GRUPOS_FUNCIONALES)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("N_IDOFICINA", OracleTypes.INTEGER),
							new SqlParameter("N_IDGRUPO", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			MapSqlParameterSource paraMap = new MapSqlParameterSource();
			paraMap.addValue("N_IDOFICINA", idOficina);
			paraMap.addValue("N_IDGRUPO", idGrupo);
			
			Map<String, Object> respuesta = this.jdbcCall.execute(paraMap);
			Integer resultado = ((BigDecimal) respuesta.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				grupos = ((respuesta.get("C_OUT") != null)
						? (List<Map<String, String>>) respuesta.get("C_OUT")
						: new ArrayList<>());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return grupos;
	}

}
