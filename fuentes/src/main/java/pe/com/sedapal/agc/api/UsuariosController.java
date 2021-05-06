package pe.com.sedapal.agc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Usuario;
import pe.com.sedapal.agc.model.request.ActualizarClaveRequest;
import pe.com.sedapal.agc.model.request.UsuarioRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IUsuarioServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

@RestController
@RequestMapping("/api")
public class UsuariosController {

	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@PostMapping(path = "/credenciales/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> autentificarCredenciales(@RequestBody UsuarioRequest usuarioRequest) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Usuario usuario = usuarioServicio.autentificarCredenciales(usuarioRequest);
			
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(usuario);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - autentificarCredenciales()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/credenciales/modulos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerMenuSistema(@RequestBody UsuarioRequest usuarioRequest){
		ResponseObject responseObject = new ResponseObject();
		try {
			Usuario usuario = usuarioServicio.obtenerPerfilesModulos(usuarioRequest);
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(usuario);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - obtenerMenuSistema()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/credenciales/{usuario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> recuperarCredenciales(@PathVariable("usuario") String usuario) {
		ResponseObject responseObject = new ResponseObject();
		try {
			String mensaje = usuarioServicio.recuperarCredenciales(usuario);
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(mensaje);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - recuperarCredenciales()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/credenciales/trabajador/{usuario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTrabajador(@PathVariable("usuario") String usuario) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Trabajador trabajador = usuarioServicio.obtenerTrabajador(usuario);
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(trabajador);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - obtenerTrabajador()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/credenciales/personal/{usuario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerPersonal(@PathVariable("usuario") String usuario) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Trabajador trabajador = usuarioServicio.obtenerPersonal(usuario);
			if (this.usuarioServicio.getErrorUsuario() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getErrorUsuario());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(trabajador);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - obtenerTrabajador()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/credenciales/token/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> logoutToken(@PathVariable("token") String token) {
		ResponseObject responseObject = new ResponseObject();
		try {
			String mensaje = usuarioServicio.logoutToken(token);
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(mensaje);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - logoutToken()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/credenciales/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> actualizarCredenciales(@RequestBody ActualizarClaveRequest actualizarClaveRequest){
		ResponseObject responseObject = new ResponseObject();
		try {
			String mensaje = usuarioServicio.actualizarCredenciales(actualizarClaveRequest);
			if (this.usuarioServicio.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.usuarioServicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado("Se cambio su clave satisfactoriamente");
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", e.getMessage());
			logger.error("[AGC: UsuariosController - actualizarCredenciales()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
