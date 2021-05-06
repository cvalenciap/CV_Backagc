package pe.com.sedapal.agc.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IActividadServicio;

@RestController
@RequestMapping("/act")
public class Actividad {
	
	@Autowired
	private IActividadServicio servicio;
	
	@PostMapping(path = "/listar-actividad", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> ListarActividad(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.listaActividad(requestParm);
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
}
