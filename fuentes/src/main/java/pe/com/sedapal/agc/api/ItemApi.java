package pe.com.sedapal.agc.api;

import java.util.List;
import java.util.Map;

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
import pe.com.sedapal.agc.model.ItemOficina;
import pe.com.sedapal.agc.model.request.ItemRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IItemServicio;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/api")
public class ItemApi {

	@Autowired
	private IItemServicio itemServicio;

	@GetMapping(value = "/items/listar-item-empresa", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerListaItemEmpresa(
			@RequestParam(required = true, name = "codigoContratista") Integer codigoContratista,
			@RequestParam(required = true, name = "codigoOficina") Integer codigoOficina) {
		ResponseObject response = new ResponseObject();
		try {
			List<Item> lista = this.itemServicio.listarItemEmpresa(codigoContratista, codigoOficina);
			response.setEstado(Estado.OK);
			response.setResultado(lista);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/items/listar-item", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarItems(
			@RequestParam(required = false, value = "descripcion") String descripcion,
			@RequestParam(required = false, value = "estado") String estado) {
			ResponseObject response = new ResponseObject();
		try {
			ItemRequest itemRequest = new ItemRequest();
			itemRequest.setDescripcion(descripcion);
			itemRequest.setEstado(estado);
			List<Item> lista = this.itemServicio.listarItem(itemRequest);
			if (this.itemServicio.getError() == null){
				response.setEstado(Estado.OK);
				response.setResultado(lista);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setEstado(Estado.ERROR);
				response.setError(this.itemServicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@PostMapping(path = "/items/modificar-item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarItem(@RequestBody ItemRequest itemRequest) {
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.itemServicio.modificarItem(itemRequest);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.itemServicio.getError());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {

			if (this.itemServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.itemServicio.getError());
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/items/agregar-item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> agregarItem(@RequestBody ItemRequest itemRequest) {
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.itemServicio.agregarItem(itemRequest);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.itemServicio.getError());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			if (this.itemServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.itemServicio.getError());
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/items/eliminar-item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarItem(@RequestBody ItemRequest itemRequest) {
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.itemServicio.eliminarItem(itemRequest);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.itemServicio.getError());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			if (this.itemServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.itemServicio.getError());
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/items/listar-item-oficinas", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarItemOficinaMantenimiento(
			@RequestParam(required = true, value = "idItem") Integer idItem) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> lista = this.itemServicio.listarItemOficinaMantenimiento(idItem);
				response.setEstado(Estado.OK);
				response.setResultado(lista);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} catch (AgcException e) {
			  response.setError(e.getError());
			  response.setEstado(Estado.ERROR);
			  return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	} 	
	
	@PostMapping(path = "/items/registrar-item-oficina", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarItemOficina(@RequestBody List<ItemOficina> listaItemOficina) {
		ResponseObject response = new ResponseObject();
		try {			
			this.itemServicio.registrarItemOficina(listaItemOficina);
			response.setEstado(Estado.OK);
			response.setResultado(Constantes.MantenimientoItems.MSE000);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
