package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.DistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IDistribucionAvisoCobranzaService {
	Paginacion getPaginacion();
	Error getError();
	Map<String,Object> modificarDistAvisCobLine(Map<String,String> requestParm) throws ParseException, Exception;
	List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranza(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception;
}
