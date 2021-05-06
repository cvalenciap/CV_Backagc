package pe.com.sedapal.agc.dao;

import java.util.List;
import pe.com.sedapal.agc.model.Medidores;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.Error;

public interface IMedidoresDAO {
	Paginacion getPaginacion();
	Error getError();
	public List<Medidores> ListarDetalleMedidores(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ);
	public List<Medidores> ListarDetalleMedidoresTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario);
}
