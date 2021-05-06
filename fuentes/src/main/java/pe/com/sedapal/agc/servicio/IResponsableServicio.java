package pe.com.sedapal.agc.servicio;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.response.Error;

public interface IResponsableServicio {
	public Map<String,Object> obtenerEmpresa();
	public Map<String,Object> obtenerOficina(Integer idEmpresa);
	public Map<String,Object> obtenerActividad(Integer idOficina,Integer idEmpresa);
	public Map<String, Object> obtenerPersonalSeleccionado(Integer idOficina, Integer idEmpresa, String idActividad);
	public Map<String, Object> obtenerPersonal(Integer idOficina, Integer idEmpresa, String idActividad);
	public Map<String, Object> guardarPersonalSeleccionado(List<Integer> listaIdPersona,Integer idOficina, Integer idEmpresa, String idActividad, String usuario);
	public Error getError();
}
