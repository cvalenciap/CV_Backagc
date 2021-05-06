package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ISolicitudDAO;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Solicitud;
import pe.com.sedapal.agc.servicio.ISolicitudServicio;

@Service
public class SolicitudServicioImpl implements ISolicitudServicio{
	
	@Autowired
	private ISolicitudDAO solicitudDAO;

	@Override
	public List<Solicitud> listarSolicitudCambioCargo(Integer codigoEmpleado) {
		return this.solicitudDAO.listarSolicitudCambioCargo(codigoEmpleado);
	}

	@Override
	public void registrarSolicitudCambioCargo(Solicitud solicitud) {
		this.solicitudDAO.registrarSolicitudCambioCargo(solicitud);		
	}

	@Override
	public List<Solicitud> listarSolicitudMovimiento(Integer codigoEmpleado) {		
		return this.solicitudDAO.listarSolicitudMovimiento(codigoEmpleado);
	}

	@Override
	public List<Oficina> listarOficinaItem(Integer idEmpresa, Integer idItem) {
		return this.solicitudDAO.listarOficinaItem(idEmpresa, idItem);
	}

	@Override
	public List<Item> listarItemEmpesa(Integer idEmpresa) {
		return this.solicitudDAO.listarItemEmpesa(idEmpresa);
	}

	@Override
	public void registrarSolicitudMovimiento(Solicitud solicitud) {
		this.solicitudDAO.registrarSolicitudMovimiento(solicitud);
	}

	@Override
	public void aprobarSolicitud(Solicitud solicitud) {
		this.solicitudDAO.aprobarSolicitud(solicitud);		
	}

	@Override
	public void rechazarSolicitud(Solicitud solicitud) {
		this.solicitudDAO.rechazarSolicitud(solicitud);
	}

}
