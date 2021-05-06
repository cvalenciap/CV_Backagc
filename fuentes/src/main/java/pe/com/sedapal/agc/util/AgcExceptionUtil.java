package pe.com.sedapal.agc.util;

import java.util.Map;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;

public class AgcExceptionUtil {
	
	public static ResponseObject capturarAgcException(AgcException e) {
		ResponseObject response = new ResponseObject();
		response.setError(e.getError());
		response.setEstado(Estado.ERROR);
		response.setMensaje(setMensaje(e.getError().getCodigo()));
		return response;
	}
	
	public static ResponseObject capturarException(Exception e) {
		ResponseObject response = new ResponseObject();
		response.setError(new Error(Constantes.CodigoErrores.ERROR_CAPTURADO_API, e.getMessage()));
		response.setEstado(Estado.ERROR);
		response.setMensaje(setMensaje(response.getError().getCodigo()));
		return response;
	}
	
	public static AgcException lanzarAgcException(Exception e, Integer codigoError) {
		return new AgcException(e.getMessage(),
				new Error(codigoError, e.toString(), e.getMessage()));
	}
	
	public static String setMensaje(Integer codigoError) {
		switch (codigoError) {
		case Constantes.CodigoErrores.ERROR_BD:
			return Constantes.Mensajes.ERROR_BD;
		default:
			return Constantes.Mensajes.ERROR_PROCESO;
		}
	}
	
	public static void errorOperacionBD(Map<String, Object> respuestaConsulta) {
		Integer codigoErrorBD = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_EJEC");
		String mensajeErrorBD = CastUtil.leerValorMapString(respuestaConsulta, "O_V_EJEC");
		throw new AgcException(Constantes.Mensajes.ERROR_BD,
				new Error(codigoErrorBD, mensajeErrorBD, mensajeErrorBD));
	}

}
