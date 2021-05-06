package pe.com.sedapal.agc.mapper;

import java.util.List;
import java.util.Map;

public interface ObjectMapper<T> {
	
	public List<T> mapearRespuestaBd(Map<String, Object> map);
	
	public Integer obtenerTotalregistros(Map<String, Object> map);

}
