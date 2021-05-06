package pe.com.sedapal.agc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.request.CargoOpenRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ICargoServicio;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/api")
public class CargoApi {

	private static final Logger logger = LoggerFactory.getLogger(CargoApi.class);

	@Autowired
	private ICargoServicio cargoServicio;

	@GetMapping(value = "/cargo/listar-cargo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerListaCargos(
			@RequestParam(required = false, name = "codigoTipoCargo") String codigoTipoCargo) {
		ResponseObject response = new ResponseObject();
		try {
			List<Cargo> lista = this.cargoServicio.listarCargo(codigoTipoCargo);
			response.setEstado(Estado.OK);
			response.setResultado(lista);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/cargo/consultar-cargos-open")
	public ResponseObject obtenerListaCargosOpen(@RequestBody CargoOpenRequest request) {
		ResponseObject response = new ResponseObject();
		try {
			ListaPaginada<Cargo> listaCargos = cargoServicio.consultarCargosOpen(request);
			response.setEstado(Estado.OK);
			response.setResultado(listaCargos);
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarAgcException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarException(e);
		}
		return response;
	}

	@GetMapping(value = "/cargo/consultar-acti-disponible")
	public ResponseObject obtenerActividadesDisponibles(@RequestParam(name = "codCargo") String codCargo) {
		ResponseObject response = new ResponseObject();
		try {
			List<Actividad> actividadesDisponibles = cargoServicio.consultarActividadesDisponibles(codCargo);
			response.setEstado(Estado.OK);
			response.setResultado(actividadesDisponibles);
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarAgcException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarException(e);
		}
		return response;
	}
	
	@PostMapping(value = "/cargo/actualizar-actividades")
	public ResponseObject actualizarActividadesDeCargo(@RequestBody Cargo cargoRequest,
			@RequestParam(name = "usuario") String usuario) {
		ResponseObject response = new ResponseObject();
		try {
			Integer resultadoOperacion = cargoServicio.actualizarActividadesCargo(cargoRequest, usuario);
			response.setEstado(Estado.OK);
			if (resultadoOperacion == 1) {				
				response.setResultado(ResultadoCarga.CORRECTO);
				response.setMensaje("Se ha guardado los cambios correctamente.");
			} else {
				response.setResultado(ResultadoCarga.INCORRECTO);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarAgcException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarException(e);
		}
		return response;
	}
}
