package pe.com.sedapal.agc.servicio;

import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.Error;

import java.util.List;

import pe.com.sedapal.agc.model.Medidores;

public interface IMedidoresService {
	Paginacion getPaginacion();
	Error getError();
	List<Medidores> ListarDetalleMedidores(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception;;
}
