package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.response.Error;

public interface IContratistaServicio {

	Map<String,Object> listaContratista(Map<String,String> requestParm) throws ParseException, Exception;
	List<Empresa> listaContratistaPersonal(Long idPers) throws Exception;
	Error getError();
}
