package pe.com.sedapal.agc.servicio;

import java.io.File;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IDigitalizadoServicio {
	Map<String, Object> listarDigitalizadosPorActividad(DigitalizadoRequest requestDigitalizado, Integer pagina, Integer registros);
	List<Digitalizado> listarDigitalizados(DigitalizadoRequest requestDigitalizado, Integer pagina, Integer registros);
	File generarArchivoExcelDigitalizado(DigitalizadoRequest requestDigitalizado, Integer pagina, Integer registros);
	ParametrosCargaBandeja obtenerParametrosBusquedaDigitalizados(Integer idPers, Integer idPerfil);
	String obtenerAdjuntosDigitalizados(VisorDigitalizadoRequest requestVisorDigitalizado);
	String[] obtenerAdjuntosDigitalizadosImagen(VisorDigitalizadoRequest request);
	Integer registrarLogAccion(VisorDigitalizadoRequest requestVisorDigitalizado);
	Paginacion getPaginacion();
	Error getError();
	Error getErrorServicio();
	void setErrorServicio(Error error);
	public DuracionDigitalizados obtenerParametrosDuracion();
}
