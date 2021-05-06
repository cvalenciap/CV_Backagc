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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.DistribucionComunicaciones;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IDistribucionComunicacionesService;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

@RestController
@RequestMapping(value = "/distribucion-comunicaciones")
public class DistribucionComunicacionesApi {

	@Autowired IDistribucionComunicacionesService servicio;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@PutMapping(path = "/actualizar-dc-linea")
	@ResponseBody
	public ResponseEntity<ResponseObject> listaPaginaCargasTrabajo(@RequestBody Map<String,String> request) throws ParseException, Exception {
		
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = servicio.modificarDistComLine(request);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()==0) {
			BigDecimal bdCodError =  (BigDecimal)resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String)resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
		
	}
	
	@GetMapping(path = "/consultar-distribucion-comunicaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarDistribucionComunicaciones(PageRequest pageRequest, @RequestParam("V_IDCARGA") String V_IDCARGA, @RequestParam("N_IDREG") Long N_IDREG, @RequestParam("V_N_CON_ADJ") Integer V_N_CON_ADJ) throws Exception {
		ResponseObject response = new ResponseObject();
		try {
		List<DistribucionComunicaciones> lista = this.servicio.ListarDetalleDisCom(pageRequest, V_IDCARGA,  N_IDREG, V_N_CON_ADJ);
		if (this.servicio.getError() == null) {
			response.setResultado(lista);
			response.setPaginacion(this.servicio.getPaginacion());
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
			logger.error("[AGC: DistribucionComunicacionesApi - consultarDistribucionComunicaciones()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
