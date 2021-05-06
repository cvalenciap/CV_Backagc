package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.Map;

public interface IActividadServicio {
	Map<String,Object> listaActividad(Map<String,String> requestParm) throws ParseException, Exception;

}
