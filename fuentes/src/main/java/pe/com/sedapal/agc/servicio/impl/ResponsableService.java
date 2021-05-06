package pe.com.sedapal.agc.servicio.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IResponsableDAO;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IResponsableServicio;

@Service
public class ResponsableService implements IResponsableServicio{
	
	@Autowired
	IResponsableDAO dao;
	
	private Integer idGrupo = 1;
	
	@Override
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public Map<String, Object> obtenerEmpresa() {
		return this.dao.obtenerEmpresa();
	}

	@Override
	public Map<String, Object> obtenerOficina(Integer idEmpresa) {
		return this.dao.obtenerOficina(idEmpresa);
	}

	@Override
	public Map<String, Object> obtenerActividad(Integer idOficina, Integer idEmpresa) {
		return this.dao.obtenerActividad(idOficina, idEmpresa);
	}

	@Override
	public Map<String, Object> obtenerPersonalSeleccionado(Integer idOficina, Integer idEmpresa, String idActividad) {
		return this.dao.obtenerPersonalSeleccionado(idOficina, idEmpresa, idActividad);
	}

	@Override
	public Map<String, Object> obtenerPersonal(Integer idOficina, Integer idEmpresa, String idActividad) {
		return this.dao.obtenerPersonal(idOficina, idEmpresa, idActividad);
	}

	@Override
	public Map<String, Object> guardarPersonalSeleccionado(List<Integer> listaIdPersona, Integer idOficina,
			Integer idEmpresa, String idActividad, String usuario) {
		return this.dao.guardarPersonalSeleccionado(listaIdPersona, idOficina, idEmpresa, idActividad, idGrupo, usuario);
	}

}
