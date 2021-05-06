package pe.com.sedapal.agc.util;

public class CharacterUtil {
	public String reemplazaCharSet(String cadena) {
		String respuesta = "";
		respuesta = cadena.replaceAll("￭", "Í");
		respuesta = respuesta.replaceAll("ￍ", "Í");
		respuesta = respuesta.replaceAll("￡", "Á");
		respuesta = respuesta.replaceAll("￁", "Á"); 
		respuesta = respuesta.replaceAll("￉", "É");
		respuesta = respuesta.replaceAll("￩", "É"); 
		respuesta = respuesta.replaceAll("￳", "Ó");
		respuesta = respuesta.replaceAll("ￓ", "Ó");
		respuesta = respuesta.replaceAll("￺", "Ú");
		respuesta = respuesta.replaceAll("ￚ", "Ú");
		respuesta = respuesta.replaceAll("￱", "Ñ");
		respuesta = respuesta.replaceAll("￑", "Ñ");
		respuesta = respuesta.replaceAll("￼", "Ü");
		return respuesta;
	}
}
