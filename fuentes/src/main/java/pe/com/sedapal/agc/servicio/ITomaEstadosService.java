package pe.com.sedapal.agc.servicio;

import java.util.List;

import pe.com.sedapal.agc.model.TomaEstados;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.Error;

public interface ITomaEstadosService {
	Paginacion getPaginacion();
	Error getError();
	List<TomaEstados> ListarTomaEstados(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception;
}
