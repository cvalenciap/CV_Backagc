package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import pe.com.sedapal.agc.dao.INotificationsDAO;
import pe.com.sedapal.agc.model.Alerta;
import pe.com.sedapal.agc.model.AlertasQuerie;
import pe.com.sedapal.agc.model.AlertasTemplate;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class NotificationsDAOImpl implements INotificationsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationsDAOImpl.class);
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	private Error error;
	public Error getError() {
		return this.error;
	}
	
    public List<AlertasTemplate> mapearTemplates(Map<String, Object> resultados) throws Exception{
    	this.error = null;
        List<AlertasTemplate> listatemplates = new ArrayList<>();
        AlertasTemplate item = null;
        try{
        	List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_CURSOR");
            for(Map<String, Object> map : lista) {
                   item = new AlertasTemplate();
                   item.setN_sec_template(((BigDecimal)map.get("N_SEC_TEMPLATE")).longValue());
                   item.setV_act_template((String)map.get("V_ACT_TEMPLATE"));
                   item.setV_nom_template((String)map.get("V_NOM_TEMPLATE"));
                   item.setV_con_template_ca((String)map.get("V_CON_TEMPLATE_CA"));
                   item.setN_id_sec_queries(((BigDecimal)map.get("N_ID_SEC_QUERIES")).longValue());
                   item.setA_v_usucre((String)map.get("A_V_USUCRE"));                   
                   item.setA_d_feccre((Date) map.get("A_D_FECCRE"));
                   item.setA_v_usumod((String)map.get("A_V_USUMOD"));                   
                   item.setA_d_fecmod((Date) map.get("A_D_FECMOD"));                   
                   listatemplates.add(item);
            }
        }catch(Exception excepcion){
        	throw new Exception(excepcion);
        }        
        return listatemplates;
    }
	
    public List<AlertasQuerie> mapearAlertasQueries(Map<String, Object> resultados) throws Exception{
    	this.error = null;
        List<AlertasQuerie> listaAlertas = new ArrayList<>();
        AlertasQuerie item = null;
        try{
        	List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_CURSOR");
            for(Map<String, Object> map : lista) {
                   item = new AlertasQuerie();
                   item.setN_sec_queries(((BigDecimal)map.get("N_SEC_QUERIES")).longValue());
                   item.setV_query_title_columns((String)map.get("V_QUERY_TITLE_COLUMNS"));                  
                   listaAlertas.add(item);
            }
        }catch(Exception excepcion){
        	throw new Exception(excepcion);
        }        
        return listaAlertas;
    }
    
    public List<Alerta> mapearAlertas(Map<String, Object> resultados) throws Exception{
    	this.error = null;
        List<Alerta> listaAlertas = new ArrayList<>();
        Alerta item = null;
        try{
        	List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_CURSOR");
            for(Map<String, Object> map : lista) {
                   item = new Alerta();
                   item.setV_id_alerta(((BigDecimal)map.get("V_ID_ALERTA")).longValue());
                   item.setV_description((String)map.get("V_DESCRIPTION"));                  
                   listaAlertas.add(item);
            }
        }catch(Exception excepcion){
        	throw new Exception(excepcion);
        }        
        return listaAlertas;
    }
	@Override
	public List<AlertasTemplate> obtenerNotificaciones(Long n_sec_template) throws Exception {
		Map<String, Object> out = null;
		List<AlertasTemplate> lista = new ArrayList<AlertasTemplate>();
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE)
					.withProcedureName(DbConstants.PRC_OBTENER_ALERTAS_TEMPLATE)
					.declareParameters(
							new SqlParameter("N_SEC_ALERTA", OracleTypes.NUMBER),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_EJEC", OracleTypes.INTEGER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("N_SEC_ALERTA", null);

			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = (Integer) out.get("N_EJEC");
			if (resultado == 1) {
				lista = mapearTemplates(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return lista;	
	}
	
	@Override
	public List<AlertasTemplate> eliminarAlertasTemplate(AlertasTemplate template) throws Exception {
		Map<String, Object> out = null;
		List<AlertasTemplate> lista = new ArrayList<AlertasTemplate>();
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE)
					.withProcedureName(DbConstants.PRC_ELIMINAR_ALERTAS_TEMPLATE)
					.declareParameters(
							new SqlParameter("V_N_SEC_TEMPLATE", OracleTypes.NUMBER),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_EJEC", OracleTypes.INTEGER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SEC_TEMPLATE", template.getN_sec_template());
			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = (Integer) out.get("N_EJEC");
			if (resultado == 1) {
				lista = mapearTemplates(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return lista;	
	}

	@Override
	public Integer editarPeriodoAlerta(Integer totalPeriodo) {
		this.error = null;
		Integer resultado = 0;
		Map<String, Object> out = null;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_REPORTES).withProcedureName(DbConstants.PRC_EDITAR_PERIODO_ALERTA)
				.declareParameters(
						new SqlParameter("V_TOTAL_PERIODO", OracleTypes.NUMBER),				
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_TOTAL_PERIODO", totalPeriodo);
		
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
	public List<AlertasTemplate> editarAlertasTemplate(AlertasTemplate template) throws Exception {
		Map<String, Object> out = null;
		List<AlertasTemplate> lista = new ArrayList<AlertasTemplate>();
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE)
					.withProcedureName(DbConstants.PRC_EDITAR_ALERTAS_TEMPLATE)
					.declareParameters(
							new SqlParameter("V_N_SEC_TEMPLATE", OracleTypes.NUMBER),
							new SqlParameter("V_V_NOM_TEMPLATE", OracleTypes.VARCHAR),
							new SqlParameter("V_V_CON_TEMPLATE_CA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_EJEC", OracleTypes.INTEGER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SEC_TEMPLATE", template.getN_sec_template())
					.addValue("V_V_NOM_TEMPLATE", template.getV_nom_template())
					.addValue("V_V_CON_TEMPLATE_CA", template.getV_con_template_ca())
					.addValue("V_A_V_USUMOD", template.getA_v_usumod());
			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = (Integer) out.get("N_EJEC");
			if (resultado == 1) {
				lista = mapearTemplates(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return lista;	
	}

	@Override
	public List<AlertasTemplate> insertarAlertasTemplate(AlertasTemplate template) throws Exception {
		Map<String, Object> out = null;
		List<AlertasTemplate> lista = new ArrayList<AlertasTemplate>();
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE)
					.withProcedureName(DbConstants.PRC_INSERTAR_ALERTAS_TEMPLATE)
					.declareParameters(
							new SqlParameter("V_V_NOM_TEMPLATE", OracleTypes.VARCHAR),
							new SqlParameter("V_V_CON_TEMPLATE_CA", OracleTypes.VARCHAR),
							new SqlParameter("V_N_ID_SEC_QUERIES", OracleTypes.NUMBER),
							new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_EJEC", OracleTypes.INTEGER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_NOM_TEMPLATE", template.getV_nom_template())
					.addValue("V_V_CON_TEMPLATE_CA", template.getV_con_template_ca())
					.addValue("V_N_ID_SEC_QUERIES", template.getN_id_sec_queries())
					.addValue("V_A_V_USUCRE", template.getA_v_usucre());
			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = (Integer) out.get("N_EJEC");
			if (resultado == 1) {
				lista = mapearTemplates(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return lista;	
	}	
	
	@Override
	public String lanzaAlertas(Long n_sec_template) {
		Map<String, Object> out = null;
		Integer resultado = 0;
		String html = "";
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS)
					.withProcedureName(DbConstants.PRC_AGC_MON_ENVIA_ALERTA)
					.declareParameters(
							new SqlParameter("V_N_SEC_TEMPLATE", OracleTypes.NUMBER),
							new SqlOutParameter("html", OracleTypes.VARCHAR),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("v_ejec", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_SEC_TEMPLATE", n_sec_template);
			out = this.jdbcCall.execute(in);
			resultado = ((BigDecimal) out.get("N_EJEC")).intValue();
			if (resultado==0) {
				this.error =new Error(0, "Error", "Error en Envío de Correo");
				html = null;
			} else {
				this.error = new Error(1, "Aviso","Envío satisfactorio de correo");
				//error.setMensajeInterno("Envío satisfactorio");
				html = (String) out.get("html"); 
			}
		}
		catch (Exception e){
			this.error = new Error(0,"Error en Envío de Correo", e.getMessage());
		}
		return html;		
	}
	
	@Override
	public List<Alerta> obtenerAlertas(Long v_v_tipo_query) throws Exception {
		List<Alerta> alertas = new ArrayList<Alerta>();
		Map<String, Object> out = null;
		try {			
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE).withProcedureName(DbConstants.PRC_OBTENER_ALERTAS)
				.declareParameters(
					new SqlParameter("V_V_TIPO_QUERY", OracleTypes.NUMBER),	
					new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
				);
			SqlParameterSource in = new MapSqlParameterSource()
			    .addValue("V_V_TIPO_QUERY", v_v_tipo_query);
			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = ((BigDecimal) out.get("N_EJEC")).intValue();
			if (resultado == 1) {
				alertas = mapearAlertas(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return alertas;	
	}
	
	@Override
	public List<AlertasQuerie> obtenerAlertasQueries(Long v_id_alerta, Long v_v_tipo_query) throws Exception {
		Map<String, Object> out = null;
		List<AlertasQuerie> lista = new ArrayList<AlertasQuerie>();
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ALERTAS_TEMPLATE)
					.withProcedureName(DbConstants.PRC_OBTENER_ALERTAS_QUERIES)
					.declareParameters(
							new SqlParameter("V_V_TIPO_QUERY", OracleTypes.NUMBER),
							new SqlParameter("N_V_ID_ALERTA", OracleTypes.NUMBER),
							new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
							new SqlOutParameter("N_EJEC", OracleTypes.INTEGER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource in = new MapSqlParameterSource()
				    .addValue("V_V_TIPO_QUERY", v_v_tipo_query)
					.addValue("N_V_ID_ALERTA", v_id_alerta);

			out = this.jdbcCall.execute(in);
			jdbc.getDataSource().getConnection().close();			
			Integer resultado = (Integer) out.get("N_EJEC");
			if (resultado == 1) {
				lista = mapearAlertasQueries(out);
			} else {
				throw new Exception((String) out.get("v_ejec"));
			}
		}
		catch (Exception excepcion) {
			throw new Exception(excepcion);
		} finally {
			try {
				jdbc.getDataSource().getConnection().close();
			} catch (Exception excepcion) {
				// TODO Auto-generated catch block
				throw new Exception(excepcion);
			}
		}
        return lista;	
	}
}
