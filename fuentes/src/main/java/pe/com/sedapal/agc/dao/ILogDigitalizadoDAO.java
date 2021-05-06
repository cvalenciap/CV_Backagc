package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.LogDigitalizado;
import pe.com.sedapal.agc.model.request.DigitalizadoLogRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ILogDigitalizadoDAO {
	List<LogDigitalizado> listarLogDigitalizados(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina, Integer registros);
	Integer registrarAccionLog(VisorDigitalizadoRequest requestVisorDigitalizado);
	Paginacion getPaginacion();
	Error getError();
}
