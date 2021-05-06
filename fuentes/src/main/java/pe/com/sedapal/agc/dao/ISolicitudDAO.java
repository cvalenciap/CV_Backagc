package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Solicitud;

public interface ISolicitudDAO {
	List<Solicitud> listarSolicitudCambioCargo(Integer codigoEmpleado);
	void registrarSolicitudCambioCargo(Solicitud solicitud);
	List<Solicitud> listarSolicitudMovimiento(Integer codigoEmpleado);
	List<Item> listarItemEmpesa(Integer idEmpresa);
	List<Oficina> listarOficinaItem(Integer idEmpresa, Integer idItem);
	void registrarSolicitudMovimiento(Solicitud solicitud);
	void aprobarSolicitud(Solicitud solicitud);
	void rechazarSolicitud(Solicitud solicitud);
}
