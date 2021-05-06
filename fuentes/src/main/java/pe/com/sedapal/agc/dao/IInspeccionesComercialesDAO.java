package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.InspeccionesComerciales;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IInspeccionesComercialesDAO {
	Paginacion getPaginacion();
	Error getError();
	public List<InspeccionesComerciales> ListarInspeccionesComerciales(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ);
	public List<InspeccionesComerciales> ListarInspeccionesComercialesTrama(PageRequest pageRequest, String V_IDCARGA, int filtro, String usuario);
	Map<String,Object> actualizarDistComLine(InspeccionesComerciales ic, String sUsuario);
}
