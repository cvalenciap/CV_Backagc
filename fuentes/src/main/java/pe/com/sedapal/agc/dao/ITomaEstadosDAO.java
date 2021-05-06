package pe.com.sedapal.agc.dao;

import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.TomaEstados;
import java.util.List;

public interface ITomaEstadosDAO {
	Paginacion getPaginacion();
	Error getError();
	public List<TomaEstados> ListarTomaEstados(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ);
	public List<TomaEstados> ListarTomaEstadosTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario);
}
