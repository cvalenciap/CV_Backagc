package pe.com.sedapal.agc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ITipoCargoServicio;

@RestController
@RequestMapping(value = "/api")
public class TipoCargoApi {
	
	@Autowired
	private ITipoCargoServicio tipoCargoServicio;
		
	@GetMapping(value = "/tipo-cargo/listar-tipos-cargo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTiposCargo() {
		ResponseObject response = new ResponseObject();
		
		try {
			List<TipoCargo> lista = this.tipoCargoServicio.listarTiposCargo();
			response.setEstado(Estado.OK);
			response.setResultado(lista);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		}catch(AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
}
