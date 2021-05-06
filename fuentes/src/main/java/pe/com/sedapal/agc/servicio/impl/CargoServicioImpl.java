package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ICargoDAO;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.request.CargoOpenRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.servicio.ICargoServicio;

@Service
public class CargoServicioImpl implements ICargoServicio{

	@Autowired
	private ICargoDAO cargoDAO;
	
	@Override
	public List<Cargo> listarCargo(String codigoTipoCargo) {
		return cargoDAO.listarCargo(codigoTipoCargo);
	}

	@Override
	public ListaPaginada<Cargo> consultarCargosOpen(CargoOpenRequest request) {
		return cargoDAO.consultarListaCargosOpen(request);
	}

	@Override
	public List<Actividad> consultarActividadesDisponibles(String codCargo) {
		return cargoDAO.consultarActividadesDisponibles(codCargo);
	}

	@Override
	public Integer actualizarActividadesCargo(Cargo cargoRequest, String usuario) {
		return cargoDAO.actualizarActividadesCargo(cargoRequest, usuario);
	}

}
