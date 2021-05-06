package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ICicloDAO;
import pe.com.sedapal.agc.model.Ciclo;
import pe.com.sedapal.agc.model.CicloDetalle;
import pe.com.sedapal.agc.model.request.CicloDetalleRequest;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.ICicloServicio;

@Service
public class CicloServicioImpl implements ICicloServicio{
    
	@Autowired
	private ICicloDAO cicloDAO;
	
	@Override
	public Paginacion getPaginacion() {
		return cicloDAO.getPaginacion();
	}

	@Override
	public Error getError() {
		return cicloDAO.getError();
	}

	@Override
	public List<Ciclo> listarCiclo(CicloRequest cicloRequest) {
		return cicloDAO.listarCiclo(cicloRequest);		
	}
	
	@Override
	public List<Ciclo> modificarCiclo(CicloRequest cicloRequest) {
		return cicloDAO.modificarCiclo(cicloRequest);
	}

	@Override
	public List<Ciclo> registrarCiclo(CicloRequest cicloRequest) {
		return cicloDAO.registrarCiclo(cicloRequest);
	}

	@Override
	public Integer eliminarCiclo(CicloRequest cicloRequest) {
		return cicloDAO.eliminarCiclo(cicloRequest);
	}
	
	@Override
	public List<CicloDetalle> listarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		return cicloDAO.listarCicloDetalle(cicloDetalleRequest);		
	}
	
	@Override
	public List<CicloDetalle> modificarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		return cicloDAO.modificarCicloDetalle(cicloDetalleRequest);
	}

	@Override
	public List<CicloDetalle> registrarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		return cicloDAO.registrarCicloDetalle(cicloDetalleRequest);
	}

	@Override
	public List<CicloDetalle> eliminarCicloDetalle(CicloDetalleRequest cicloDetalleRequest) {
		return cicloDAO.eliminarCicloDetalle(cicloDetalleRequest);
	}
}
