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

import pe.com.sedapal.agc.model.Ciclo;
import pe.com.sedapal.agc.model.CicloDetalle;
import pe.com.sedapal.agc.model.request.CicloDetalleRequest;
import pe.com.sedapal.agc.model.request.CicloRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ICicloServicio;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/api")
public class CicloApi {

	@Autowired
	private ICicloServicio cicloservicio; 
	
	@GetMapping(path = "/ciclo/listado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listaCiclo(
			@RequestParam(value = "periodo", required = false) String periodo,
			@RequestParam(value = "oficina", required = false) Long oficina){
		ResponseObject response = new ResponseObject();
		try {
			CicloRequest cicloRequest = new CicloRequest();
			cicloRequest.setD_periodo(periodo);
			cicloRequest.setN_idofic(oficina);
			List<Ciclo> lista = this.cicloservicio.listarCiclo(cicloRequest);
			if (this.cicloservicio.getError() == null) {
				response.setEstado(Estado.OK);
				response.setResultado(lista);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setEstado(Estado.ERROR);
				response.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(path = "/ciclo/modificar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarCiclo(@RequestBody CicloRequest cicloRequest) {
		ResponseObject responseObject = new ResponseObject();
		List<Ciclo> lista =  this.cicloservicio.modificarCiclo(cicloRequest);	
		try {
			if (this.cicloservicio.getError() == null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(lista);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/ciclo/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarCiclo(@RequestBody CicloRequest cicloRequest) {
		ResponseObject responseObject = new ResponseObject();
		List<Ciclo> lista =  this.cicloservicio.registrarCiclo(cicloRequest);	
		try {
			if (this.cicloservicio.getError() == null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(lista);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/ciclo/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarCiclo(@RequestBody CicloRequest cicloRequest) {
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.cicloservicio.eliminarCiclo(cicloRequest);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.cicloservicio.getError());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {			
			if(this.cicloservicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
			}else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	
	
	@GetMapping(path = "/ciclo-detalle/listado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listaCicloDetalle(
			@RequestParam(value = "idciclo", required = false) Long idciclo){
		ResponseObject response = new ResponseObject();
		try {
			CicloDetalleRequest cicloDetalleRequest = new CicloDetalleRequest();
			cicloDetalleRequest.setN_idciclo(idciclo);
			List<CicloDetalle> lista = this.cicloservicio.listarCicloDetalle(cicloDetalleRequest);
			if (this.cicloservicio.getError() == null) {
				response.setEstado(Estado.OK);
				response.setResultado(lista);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setEstado(Estado.ERROR);
				response.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(path = "/ciclo-detalle/modificar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarCicloDetalle(@RequestBody CicloDetalleRequest cicloDetalleRequest) {
		ResponseObject responseObject = new ResponseObject();
		List<CicloDetalle> lista =  this.cicloservicio.modificarCicloDetalle(cicloDetalleRequest);	
		try {
			if (this.cicloservicio.getError() == null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(lista);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/ciclo-detalle/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarCicloDetalle(@RequestBody CicloDetalleRequest cicloDetalleRequest) {
		ResponseObject responseObject = new ResponseObject();
		List<CicloDetalle> lista =  this.cicloservicio.registrarCicloDetalle(cicloDetalleRequest);	
		try {
			if (this.cicloservicio.getError() == null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(lista);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/ciclo-detalle/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarCicloDetalle(@RequestBody CicloDetalleRequest cicloDetalleRequest) {
		ResponseObject responseObject = new ResponseObject();
		List<CicloDetalle> lista = this.cicloservicio.eliminarCicloDetalle(cicloDetalleRequest);
		try {
			if (this.cicloservicio.getError() == null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(lista);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.cicloservicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
