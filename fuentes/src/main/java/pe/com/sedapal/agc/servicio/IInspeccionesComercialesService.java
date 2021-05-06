package pe.com.sedapal.agc.servicio;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.InspeccionesComerciales;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.Error;

public interface IInspeccionesComercialesService {
	Paginacion getPaginacion();
	Error getError();
	List<InspeccionesComerciales> ListarInspeccionesComerciales(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception;
	Map<String,Object> modificarInspComLine(Map<String,String> requestParm) throws ParseException, Exception;

}
