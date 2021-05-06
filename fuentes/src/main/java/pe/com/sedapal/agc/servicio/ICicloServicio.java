package pe.com.sedapal.agc.servicio;

import java.util.List;

import pe.com.sedapal.agc.model.Ciclo;
import pe.com.sedapal.agc.model.CicloDetalle;
import pe.com.sedapal.agc.model.request.CicloDetalleRequest;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ICicloServicio {
	Paginacion getPaginacion();
	Error getError();
	List<Ciclo> listarCiclo(CicloRequest cicloRequest);
	List<Ciclo>  modificarCiclo(CicloRequest cicloRequest);
	List<Ciclo>  registrarCiclo(CicloRequest cicloRequest);
	Integer eliminarCiclo(CicloRequest cicloRequest);
	List<CicloDetalle> listarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	List<CicloDetalle> modificarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	List<CicloDetalle> registrarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	List<CicloDetalle> eliminarCicloDetalle(CicloDetalleRequest cicloDetalleRequest);
	
}
