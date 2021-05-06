package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.Ciclo;
import pe.com.sedapal.agc.model.CicloDetalle;
import pe.com.sedapal.agc.model.request.CicloDetalleRequest;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ICicloDAO {

	public Paginacion getPaginacion();
	public Error getError();
	public List<Ciclo> listarCiclo(CicloRequest cicloRequest);
	public List<Ciclo> modificarCiclo(CicloRequest cicloRequest);
	public List<Ciclo>  registrarCiclo(CicloRequest cicloRequest);
	public Integer eliminarCiclo(CicloRequest cicloRequest);
	public List<CicloDetalle> listarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	public List<CicloDetalle> modificarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	public List<CicloDetalle> registrarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	public List<CicloDetalle> eliminarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);

}
