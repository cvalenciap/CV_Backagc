package pe.com.sedapal.agc.servicio;

import java.io.File;
import java.util.List;

import pe.com.sedapal.agc.model.LogDigitalizado;
import pe.com.sedapal.agc.model.request.DigitalizadoLogRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ILogDigitalizadoServicio {
	List<LogDigitalizado> listarLogDigitalizados(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina, Integer registros);
	File generarArchivoExcelLogDigitalizado(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina, Integer registros);
	Paginacion getPaginacion();
	Error getError();
}
