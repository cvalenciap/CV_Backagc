package pe.com.sedapal.agc.servicio;

import java.util.List;

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.request.CargoOpenRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;

public interface ICargoServicio {

	List<Cargo> listarCargo(String codigoTipoCargo);
	
	ListaPaginada<Cargo> consultarCargosOpen(CargoOpenRequest request);

	List<Actividad> consultarActividadesDisponibles(String codCargo);

	Integer actualizarActividadesCargo(Cargo cargoRequest, String usuario);
}
