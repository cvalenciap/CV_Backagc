package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.INotificationsDAO;
import pe.com.sedapal.agc.model.Alerta;
import pe.com.sedapal.agc.model.AlertasQuerie;
import pe.com.sedapal.agc.model.AlertasTemplate;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.servicio.INotificationsService;
import pe.com.sedapal.agc.model.response.Error;

@Service
public class NotificationsServiceImpl implements INotificationsService {

	@Autowired
	private INotificationsDAO dao;
	Error error = null;
	
	public Error getError() {
		return dao.getError();
	}
	
	@Override
	public String lanzaAlertas(Long n_sec_template) {
		this.error = dao.getError();
		return dao.lanzaAlertas(n_sec_template);
	}
	
	@Override
	public List<AlertasTemplate> obtenerNotificaciones(Long n_sec_template) throws Exception {
		return dao.obtenerNotificaciones(n_sec_template);
	}

	@Override
	public List<AlertasTemplate> insertarAlertasTemplate(AlertasTemplate template) throws Exception {
		return dao.insertarAlertasTemplate(template);
	}
	
	@Override
	public List<AlertasTemplate> editarAlertasTemplate(AlertasTemplate template) throws Exception {
		return dao.editarAlertasTemplate(template);
	}

	@Override
	public List<AlertasTemplate>eliminarAlertasTemplate(AlertasTemplate template) throws Exception {
		return dao.eliminarAlertasTemplate(template);
	}
	
	@Override
	public Integer editarPeriodoAlerta(Integer totalPeriodo) {
		return dao.editarPeriodoAlerta(totalPeriodo);
	}
	
	@Override
	public List<Alerta> obtenerAlertas(Long v_v_tipo_query) throws Exception {
		return dao.obtenerAlertas(v_v_tipo_query); 
	}
	
	@Override
	public List<AlertasQuerie> obtenerAlertasQueries(Long v_id_alerta, Long v_v_tipo_query) throws Exception {
		return dao.obtenerAlertasQueries(v_id_alerta, v_v_tipo_query);
	}
}
