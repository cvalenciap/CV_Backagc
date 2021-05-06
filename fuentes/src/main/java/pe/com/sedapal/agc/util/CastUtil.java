package pe.com.sedapal.agc.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.util.StringUtils;

public class CastUtil {
	
	public static String leerValorMapString(Map<String, Object> map, String key) {
		return !StringUtils.isEmpty(map.get(key)) ? String.valueOf(map.get(key)).toUpperCase() : null;
	}
	
	public static String leerValorMapStringOriginCase(Map<String, Object> map, String key) {
		return !StringUtils.isEmpty(map.get(key)) ? String.valueOf(map.get(key)) : null;
	}
	
	public static String leerValorMapStringLowerCase(Map<String, Object> map, String key) {
		return !StringUtils.isEmpty(map.get(key)) ? String.valueOf(map.get(key)) : null;
	}
	
	public static Integer leerValorMapInteger(Map<String, Object> map, String key) {
		return map.get(key) != null ? Integer.parseInt(String.valueOf(map.get(key))) : null;
	}
	
	public static Double leerValorMapDouble(Map<String, Object> map, String key) {
		return map.get(key) != null ? Double.parseDouble(String.valueOf(map.get(key))) : null;
	}
	
	public static String[] leerArregloCadena(Map<String, Object> map, String key, String separador) {
		String cadena = leerValorMapString(map, key);
		return cadena == null ? new String[0] : cadena.trim().split(separador);
	}
	
	public static <T> Predicate<T> distinctPorAtributo(Function<? super T, Object> obtenerAtributo) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(obtenerAtributo.apply(t), Boolean.TRUE) == null;
	}

}
