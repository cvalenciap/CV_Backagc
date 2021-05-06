package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
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
import pe.com.sedapal.agc.dao.IUsuarioDAO;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class UsuarioDAOImpl implements IUsuarioDAO{
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	private Error error;
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Override
	public Trabajador obtenerPersonal(String usuario) {
		this.error = null;
		Map<String, Object> out = null;
		Trabajador personal = new Trabajador();;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ACCIONES)
				.withProcedureName(DbConstants.PRC_OBTENER_PERSONAL)
				.declareParameters(
						new SqlParameter("V_VCODUSUARIO", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);	
		
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_VCODUSUARIO", usuario);
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");
				if(!lista.isEmpty() && lista != null) {					
										
					for(Map<String, Object> map : lista) {
						personal.setCodigo(((BigDecimal)map.get("COD_EMP")).intValue());
						personal.setNombre((String)map.get("nombreCompleto"));
						personal.setEstado((String)map.get("EST_EMP"));	
					}										
														
				}else {
					this.error = new Error(999, "Error", "Usuario externo no registrado");
				}				
			} else {
				String mensaje = (String)out.get("N_EJEC");
				String mensajeInterno = (String)out.get("V_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		}catch(Exception e){			
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().toString());
			logger.error("[AGC: UsuarioDAOImpl - obtenerPersonal()] - "+e.getCause().toString());
		}
		
		return personal;
	}

	@Override
	public Error getError() {
		return this.error;
	}
	
}
