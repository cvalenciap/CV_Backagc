package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.DistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IDistribucionAvisoCobbranzaDAO {
	Paginacion getPaginacion();
	Error getError();
	Map<String,Object> actualizarDistAvisCobLine(DistribucionAvisoCobranza dac, String sUsuario);
	List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranza(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ);
	List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranzaTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario);
}
