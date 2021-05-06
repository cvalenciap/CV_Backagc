package pe.com.sedapal.agc.dao.impl;

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
import pe.com.sedapal.agc.dao.IActividadDAO;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class ActividadDAOImpl implements IActividadDAO {

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@Override
	public Map<String, Object> listarActividad(Actividad actividad) {

		Map<String, Object> out = null;
		
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_MANTRESPONSABLE)
					.withProcedureName(DbConstants.PRC_LIS_ACTIVIDADES)
					.declareParameters(
							new SqlParameter("V_V_IDACTI", OracleTypes.NUMBER),
							new SqlParameter("V_V_ESTAACTI", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DESCACTI", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);

			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_V_IDACTI", actividad.getCodigo())
																		.addValue("V_V_ESTAACTI", actividad.getEstado())
																		.addValue("V_V_DESCACTI", actividad.getDescripcion())
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: ActividadDAOImpl - listarActividad()] - "+e.getMessage());
		}
		
		return out;
		
	}

}
