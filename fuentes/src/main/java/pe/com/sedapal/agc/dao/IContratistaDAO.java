package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.response.Error;

public interface IContratistaDAO {

	
	Map<String,Object> listaContratista(Empresa empresa);
	List<Empresa> listaContratistaPersonal(Long idPers) throws Exception;
	Error getError();
}
