package pe.com.sedapal.agc.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IContratistaServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

@RestController
@RequestMapping("/cont")
public class ConstratistaApi {

	@Autowired
	private IContratistaServicio servicio;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@PostMapping(path = "/lista-contratistas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarContratistas(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.listaContratista(requestParm);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()==0) {
			BigDecimal bdCodError =  (BigDecimal)resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String)resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(resultadoCons.get("C_OUT"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/lista-contratistas-personal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarContratistas(@RequestParam("idPers") Long idPers) throws Exception {
		ResponseObject response = new ResponseObject();
		try {
			List<Empresa> lista = this.servicio.listaContratistaPersonal(idPers);
			if (this.servicio.getError() == null) {
				response.setResultado(lista);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);		
			} else {
				response.setError(1, "Error Interno", "Error");
				response.setResultado(lista);
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ConstratistaApi - listarContratistas()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
