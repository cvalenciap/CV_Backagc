package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.DistribucionComunicaciones;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IDistribucionComunicacionesDAO {
	Paginacion getPaginacion();
	Error getError();
	Map<String,Object> actualizarDistComLine(DistribucionComunicaciones dc, String sUsuario);
	List<DistribucionComunicaciones> ListarDetalleDisCom(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ);
	List<DistribucionComunicaciones> ListarDetalleDisComTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario);
}
