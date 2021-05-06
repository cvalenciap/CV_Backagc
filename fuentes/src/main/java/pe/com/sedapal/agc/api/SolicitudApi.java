package pe.com.sedapal.agc.api;

import java.util.List;

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
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Solicitud;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ISolicitudServicio;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/api")
public class SolicitudApi {

	@Autowired
	private ISolicitudServicio solicitudServicio;

	@GetMapping(path = "/solicitud/listar-solicitud-cambio-cargo")
	public ResponseEntity<ResponseObject> obtenerListaSolicitudCambioCargo(
			@RequestParam(required = true) Integer codigoEmpleado) {
		ResponseObject response = new ResponseObject();
		try {
			List<Solicitud> listaSolicitud = this.solicitudServicio.listarSolicitudCambioCargo(codigoEmpleado);
			response.setEstado(Estado.OK);
			response.setResultado(listaSolicitud);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/solicitud/registrar-solicitud-cambio-cargo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarSolicitudCambioCargo(
			@RequestBody Solicitud solicitud) {
		ResponseObject response = new ResponseObject();
		try {			
			this.solicitudServicio.registrarSolicitudCambioCargo(solicitud);
			response.setEstado(Estado.OK);
			response.setResultado(Constantes.RegistroSolicitudPersonal.MSE000);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/solicitud/registrar-solicitud-movimiento", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarSolicitudMovimiento(
			@RequestBody Solicitud solicitud) {
		ResponseObject response = new ResponseObject();
		try {			
			this.solicitudServicio.registrarSolicitudMovimiento(solicitud);
			response.setEstado(Estado.OK);
			response.setResultado(Constantes.RegistroSolicitudPersonal.MSE002);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/solicitud/listar-solicitud-movimiento")
	public ResponseEntity<ResponseObject> obtenerListaSolicitudMovimiento(
			@RequestParam(required = true) Integer codigoEmpleado) {
		ResponseObject response = new ResponseObject();
		try {
			List<Solicitud> listaSolicitud = this.solicitudServicio.listarSolicitudMovimiento(codigoEmpleado);
			response.setEstado(Estado.OK);
			response.setResultado(listaSolicitud);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/solicitud/listar-item-empresa")
	public ResponseEntity<ResponseObject> obtenerListaItemEmpresa(
			@RequestParam(required = true) Integer idEmpresa) {
		ResponseObject response = new ResponseObject();
		try {
			List<Item> listaItem = this.solicitudServicio.listarItemEmpesa(idEmpresa);
			response.setEstado(Estado.OK);
			response.setResultado(listaItem);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/solicitud/listar-oficina-item")
	public ResponseEntity<ResponseObject> obtenerListaOficinaItem(
			@RequestParam(required = true) Integer idEmpresa,
			@RequestParam(required = true) Integer idItem) {
		ResponseObject response = new ResponseObject();
		try {
			List<Oficina> listaOficina = this.solicitudServicio.listarOficinaItem(idEmpresa, idItem);
			response.setEstado(Estado.OK);
			response.setResultado(listaOficina);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/solicitud/aprobar-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> aprobarSolicitud(
			@RequestBody Solicitud solicitud) {
		ResponseObject response = new ResponseObject();
		try {			
			this.solicitudServicio.aprobarSolicitud(solicitud);
			response.setEstado(Estado.OK);
			if (solicitud.getTipoSolicitud().equals(Constantes.Solicitud.TIPO_SOL_CAMB_CARGO)) {
				response.setResultado(Constantes.RegistroSolicitudPersonal.MSE003);
			} else {
				response.setResultado(Constantes.RegistroSolicitudPersonal.MSE005);
			}
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/solicitud/rechazar-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> rechazarSolicitud(
			@RequestBody Solicitud solicitud) {
		ResponseObject response = new ResponseObject();
		try {			
			this.solicitudServicio.rechazarSolicitud(solicitud);
			response.setEstado(Estado.OK);
			if (solicitud.getTipoSolicitud().equals(Constantes.Solicitud.TIPO_SOL_CAMB_CARGO)) {
				response.setResultado(Constantes.RegistroSolicitudPersonal.MSE004);
			} else {
				response.setResultado(Constantes.RegistroSolicitudPersonal.MSE006);
			}
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
