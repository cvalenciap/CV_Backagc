package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.Map;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IParametroServicio {
	Error getError();
	Map<String,Object> listaParametro(Map<String,String> requestParm) throws ParseException, Exception;
	Map<String,Object> listarTipoParametro(Map<String, String> requestParm, PageRequest pageRequest);
	Map<String,Object> obtenerTipoParametro(Map<String, String> requestParm);
	Integer registrarTipoParametro(Map<String, String> requestParm);
	Integer modificarTipoParametro(Map<String, String> requestParm);
	Integer eliminarTipoParametro(Integer codigo, String usuario);
	
	Map<String,Object> listarParametros(Map<String, String> requestParm, PageRequest pageRequest);
	Integer registrarParametro(Map<String, String> requestParm);
	Integer modificarParametro(Map<String, String> requestParm);
	Integer eliminarParametro(Integer codigo,Integer tipoCodigo, String usuario);
	Map<String,Object> obtenerParametro(Map<String, String> requestParm);
}
