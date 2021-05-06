package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Oficina;

public interface IOficinaServicio {

	Oficina obtenerOficina(Integer codigo) throws ParseException, Exception;
	Map<String,Object> devuelveOficina(Map<String,String> requestParm) throws ParseException, Exception;
	Map<String,Object> listaOficina(Map<String,String> requestParm) throws ParseException, Exception;
	Oficina retornarOficinaLogin(Integer n_idpers);
	List<Map<String, String>> obtenerGruposFuncionales(Integer idOficina, Integer idGrupo);
}
