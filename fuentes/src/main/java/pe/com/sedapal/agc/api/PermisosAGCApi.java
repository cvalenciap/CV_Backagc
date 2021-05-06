package pe.com.sedapal.agc.api;

import java.text.ParseException;
import java.util.Optional;

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

import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IPermisosAGC_Service;

@RestController
@RequestMapping("/permisos")
public class PermisosAGCApi {
	@Autowired
	private IPermisosAGC_Service service;
	
	@GetMapping(path = "")
	public ResponseEntity<ResponseObject> consultarPermisos(@RequestParam("N_IDPERFIL") Integer N_IDPERFIL) {
		ResponseObject responseObject = new ResponseObject();		
		String retorno = "";
		retorno=service.consultarPermisos(N_IDPERFIL);
		if(retorno=="") {
			responseObject.setResultado(retorno);
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.NOT_FOUND);
		} else {
			responseObject.setResultado(retorno);
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
}
