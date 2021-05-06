package pe.com.sedapal.agc.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IParametroServicio;

@RestController
@RequestMapping("/par")
public class Parametro {

	@Autowired
	private IParametroServicio servicio;
	
	private Paginacion paginacion;
	
	@PostMapping(path = "/listar-tipoparametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> ListarTipoParametro(@RequestParam("pagina") Optional<Integer> iPagina,
			@RequestParam("registros") Optional<Integer> iRegistros,@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		PageRequest page = new PageRequest();
		page.setPagina(iPagina.hashCode());
		page.setRegistros(iRegistros.hashCode());
		this.paginacion = new Paginacion();
		this.paginacion.setRegistros(iRegistros.hashCode());
		this.paginacion.setPagina(iPagina.hashCode());
		Map<String, Object> resultadoCons = this.servicio.listarTipoParametro(requestParm,page);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			BigDecimal bdCodError =  (BigDecimal)resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String)resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			if ((List<Map<String, Object>>) resultadoCons.get("C_OUT")!=null &&
					((List<Map<String, Object>>) resultadoCons.get("C_OUT")).size()>0 &&
					((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("RESULT_COUNT")!=null) {
					Integer totalReg = ((BigDecimal)((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("RESULT_COUNT")).intValue();
					this.paginacion.setTotalRegistros(totalReg);
			}
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setPaginacion(this.paginacion);
			responseObject.setResultado(listaParam);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@PostMapping(path = "/obtener-tipoparametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTipoParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Map<String, Object> resultadoCons = this.servicio.obtenerTipoParametro(requestParm);
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
	@PostMapping(path = "/registrar-tipoparametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarTipoParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Integer bdError = this.servicio.registrarTipoParametro(requestParm);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			if(this.servicio.getError().getMensajeInterno().equals("20000")) {
				responseObject.setError(this.servicio.getError().getCodigo(), this.servicio.getError().getMensaje(), this.servicio.getError().getMensajeInterno());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}						
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@PostMapping(path = "/modificar-tipoparametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarTipoParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Integer bdError = this.servicio.modificarTipoParametro(requestParm);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@DeleteMapping(path = "/eliminar-tipoparametro/{codigo}/{usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarTipoParametro(@PathVariable("codigo") Integer codigo, @PathVariable("usuario") String usuario) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Integer bdError = this.servicio.eliminarTipoParametro(codigo, usuario);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	

	@PostMapping(path = "/listar-parametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> ListarParametro(@RequestParam("pagina") Optional<Integer> iPagina,
			@RequestParam("registros") Optional<Integer> iRegistros,@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		PageRequest page = new PageRequest();
		page.setPagina(iPagina.hashCode());
		page.setRegistros(iRegistros.hashCode());
		this.paginacion = new Paginacion();
		this.paginacion.setRegistros(iRegistros.hashCode());
		this.paginacion.setPagina(iPagina.hashCode());
		Map<String, Object> resultadoCons = this.servicio.listarParametros(requestParm, page);
		BigDecimal bdError = (BigDecimal)resultadoCons.get("N_RESP");
		if(bdError.intValue()!=1) {
			BigDecimal bdCodError =  (BigDecimal)resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String)resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			if ((List<Map<String, Object>>) resultadoCons.get("C_OUT")!=null &&
					((List<Map<String, Object>>) resultadoCons.get("C_OUT")).size()>0 &&
					((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("RESULT_COUNT")!=null) {
					Integer totalReg = ((BigDecimal)((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("RESULT_COUNT")).intValue();
					this.paginacion.setTotalRegistros(totalReg);
			}
			List<Map<String, Object>> listaParam = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(listaParam);
			responseObject.setPaginacion(this.paginacion);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@PostMapping(path = "/obtener-parametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Map<String, Object> resultadoCons = this.servicio.obtenerParametro(requestParm);
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
	@PostMapping(path = "/registrar-parametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Integer bdError = this.servicio.registrarParametro(requestParm);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			if(this.servicio.getError().getMensajeInterno().equals("30000")) {
				responseObject.setError(this.servicio.getError().getCodigo(), this.servicio.getError().getMensaje(), this.servicio.getError().getMensajeInterno());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
			   responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			   return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
		
	}
	@PostMapping(path = "/modificar-parametro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarParametro(@RequestBody Map<String,String> requestParm) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Integer bdError = this.servicio.modificarParametro(requestParm);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	@DeleteMapping(path = "/eliminar-parametro/{codigoTipo}/{codigo}/{usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarParametro(@PathVariable("codigoTipo") Integer codigoTipo,@PathVariable("codigo") Integer codigo
			, @PathVariable("usuario") String usuario) throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		Integer bdError = this.servicio.eliminarParametro(codigo,codigoTipo,usuario);
		if(bdError!=1) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensajeInterno());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
}