package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Oficina;

public interface IOficinaDAO {
	
	Map<String,Object> obtenerOficina(Oficina oficina);
	
	Oficina retornarOficinaLogin(Integer n_idpers);
	
	List<Map<String, String>> obtenerGruposFuncionales(Integer idOficina, Integer idGrupo);

}
