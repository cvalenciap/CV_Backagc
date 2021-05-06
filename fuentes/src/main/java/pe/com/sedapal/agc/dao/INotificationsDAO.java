package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.Alerta;
import pe.com.sedapal.agc.model.AlertasQuerie;
import pe.com.sedapal.agc.model.AlertasTemplate;
import pe.com.sedapal.agc.model.response.Error;

public interface INotificationsDAO {

	public Error getError();
	public String lanzaAlertas(Long n_sec_template);
	public List<AlertasTemplate> obtenerNotificaciones(Long n_sec_template) throws Exception;
	public List<AlertasTemplate> editarAlertasTemplate(AlertasTemplate template) throws Exception;
	List<AlertasTemplate> insertarAlertasTemplate(AlertasTemplate template) throws Exception;
	public List<Alerta> obtenerAlertas(Long v_v_tipo_query) throws Exception;
	public List<AlertasQuerie> obtenerAlertasQueries(Long v_id_alerta, Long v_v_tipo_query) throws Exception;
	public List<AlertasTemplate> eliminarAlertasTemplate(AlertasTemplate template)throws Exception;
	public Integer editarPeriodoAlerta(Integer totalPeriodo);

}
