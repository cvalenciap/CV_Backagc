package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IDigitalizadoDAO {
	Map<String, Object> listarDigitalizadosPorActividad(DigitalizadoRequest requestDigitalizado, Integer pagina, Integer registros);
    List<Digitalizado> listarDigitalizados(DigitalizadoRequest requestDigitalizado, Integer pagina, Integer registros);
	ParametrosCargaBandeja obtenerParametrosBusquedaDigitalizados(Integer idPers, Integer idPerfil);
	List<Adjunto> listarAdjuntosDigitalizadosCT(VisorDigitalizadoRequest requestVisorDigitalizado);
	List<Adjunto> listarAdjuntosDigitalizadosSGIO(VisorDigitalizadoRequest requestVisorDigitalizado);
	Paginacion getPaginacion();
	Error getError();
	public DuracionDigitalizados obtenerParametrosDuracion();
}
