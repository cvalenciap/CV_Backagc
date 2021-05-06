package pe.com.sedapal.agc.api;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.HeadersIpServicc;

@RestController
public class HeadersApi { 

	@Autowired
	private HeadersIpServicc servicio;  
	@RequestMapping(value = "ip", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtieneIp() {
		ResponseObject response = new ResponseObject();
		String ip = null;
		ip = servicio.devuelveIp();
		response.setResultado(ip);
		response.setEstado(Estado.OK);
		response.setError(null);
		return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
	}

} 