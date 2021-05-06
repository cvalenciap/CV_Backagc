package pe.com.sedapal.agc.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IResponsableServicio;

@RestController
@RequestMapping("/responsable")
public class ResponsableApi {
	
	@Autowired
	private IResponsableServicio servicio;
	
	@GetMapping(path = "/obtenerEmpresas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerEmpresas() throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerEmpresa();
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/obtenerOficina/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerOficina(@PathVariable("codigo") Integer codigo) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerOficina(codigo);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@GetMapping(path = "/obtenerActividad/{codigoEmpresa}/{codigoOficina}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerActividad(@PathVariable("codigoEmpresa") Integer codigoEmpresa,@PathVariable("codigoOficina") Integer codigoOficina) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerActividad(codigoOficina, codigoEmpresa);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@GetMapping(path = "/obtenerPersonal/{codigoEmpresa}/{codigoOficina}/{codigoActividad}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerPersonal(@PathVariable("codigoEmpresa") Integer codigoEmpresa,@PathVariable("codigoOficina") Integer codigoOficina,@PathVariable("codigoActividad") String codigoActividad) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerPersonal(codigoOficina, codigoEmpresa, codigoActividad);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@GetMapping(path = "/obtenerPersonalSelec/{codigoEmpresa}/{codigoOficina}/{codigoActividad}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerPersonalSeleccionado(@PathVariable("codigoEmpresa") Integer codigoEmpresa,@PathVariable("codigoOficina") Integer codigoOficina,@PathVariable("codigoActividad") String codigoActividad) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerPersonalSeleccionado(codigoOficina, codigoEmpresa, codigoActividad);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@PostMapping(path = "/guardarPersonal/{codigoEmpresa}/{codigoOficina}/{codigoActividad}/{usuario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> guardarPersonal(@RequestBody List<Integer> requestParm,
														@PathVariable("codigoEmpresa") Integer codigoEmpresa,
														@PathVariable("codigoOficina") Integer codigoOficina,
														@PathVariable("codigoActividad") String codigoActividad,
														@PathVariable("usuario") String usuario) throws ParseException, Exception {
		List<Integer> listaCodigo = requestParm;
		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.guardarPersonalSeleccionado(listaCodigo,codigoOficina, codigoEmpresa, codigoActividad, usuario);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
}
