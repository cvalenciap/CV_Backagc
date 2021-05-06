package pe.com.sedapal.agc.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import pe.com.sedapal.agc.util.DbConstants;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IPermisosAGC_DAO;

@Service
public class PermisosAGC_DAOImpl implements IPermisosAGC_DAO {
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	public String consultarPermisos(Integer N_IDPERFIL) {
		String retorno;
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ACCIONES)
				.withProcedureName(DbConstants.PRC_CONS_ACCIONES)
				.declareParameters(
						new SqlParameter("V_N_IDPERFIL", OracleTypes.NUMBER),
						new SqlOutParameter("V_PERMISOS", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);

			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_IDPERFIL", N_IDPERFIL);
			out = this.jdbcCall.execute(paraMap);
			//out.entrySet().forEach(System.out::println);
			retorno = (String) out.get("V_PERMISOS");
		}
		catch (Exception e){
			System.out.println(e+"ERROR EJECUTANDO PRC_LIS_ACTIVIDADES");
			retorno="";
		}
		return retorno;
	}
}
