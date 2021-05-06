package pe.com.sedapal.agc.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.GrupoPersonal;
import pe.com.sedapal.agc.model.OficinaGrupo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.request.GrupoPersonalRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IAsignacionPersonalService;
import pe.com.sedapal.agc.servicio.IPersonalContratistaServicio;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping("/api/asignacion-personal")
public class AsignacionPersonalApi {

	private static final Logger logger = LoggerFactory.getLogger(AsignacionPersonalApi.class);

	@Autowired
	private IAsignacionPersonalService servicioAsignacionPersonal;

	@Autowired
	private IPersonalContratistaServicio personalContratistaService;

	@GetMapping(path = "/grupo-oficina/{codigoEmpresa}/{codigoOficina}/{codigoGrupo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerGrupoOficina(@PathVariable("codigoEmpresa") Integer codigoEmpresa,
			@PathVariable("codigoOficina") Integer codigoOficina, @PathVariable("codigoGrupo") Integer codigoGrupo) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<OficinaGrupo> listaOficinaGrupo = this.servicioAsignacionPersonal.listarOficinaGrupo(codigoEmpresa,
					codigoOficina, codigoGrupo);
			if (this.servicioAsignacionPersonal.getError() == null) {
				if (listaOficinaGrupo.size() > 0) {
					AtomicInteger contadorindice = new AtomicInteger(1);
					listaOficinaGrupo.forEach(item -> item.setIndice(contadorindice.getAndAdd(1)));
				}
				responseObject.setResultado(listaOficinaGrupo);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/personal-grupo/{codigoEmpresa}/{codigoOficina}/{codigoGrupo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerPersonalGrupo(@PathVariable("codigoEmpresa") Integer codigoEmpresa,
			@PathVariable("codigoOficina") Integer codigoOficina, @PathVariable("codigoGrupo") Integer codigoGrupo) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<GrupoPersonal> listaGrupoPersonal = this.servicioAsignacionPersonal.listarPersonalGrupo(codigoEmpresa,
					codigoOficina, codigoGrupo);
			if (this.servicioAsignacionPersonal.getError() == null) {
				if (listaGrupoPersonal.size() > 0) {
					AtomicInteger contadorindice = new AtomicInteger(1);
					listaGrupoPersonal.forEach(item -> item.setIndice(contadorindice.getAndAdd(1)));
				}
				responseObject.setResultado(listaGrupoPersonal);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/trabajador/{codigoUsuario}/{tipoEmpresa}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTrabajador(@PathVariable("codigoUsuario") String codigoUsuario,
			@PathVariable("tipoEmpresa") String tipoEmpresa) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Trabajador trabajador = this.servicioAsignacionPersonal.obtenerTrabajador(codigoUsuario, tipoEmpresa);
			if (this.servicioAsignacionPersonal.getError() == null) {
				responseObject.setResultado(trabajador);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			if (e.getCause() == null) {
				responseObject.setError(1, e.getMessage(), e.getMessage());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				logger.error(e.getMessage(), e.getCause());
				responseObject.setError(1, "Error Interno", e.getMessage());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@PostMapping(path = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarGrupoPersonal(
			@RequestBody GrupoPersonalRequest requestGrupoPersonal) {
		ResponseObject responseObject = new ResponseObject();

		try {
			List<GrupoPersonal> listaGrupoPersonal = this.servicioAsignacionPersonal
					.registrarGrupoPersonal(requestGrupoPersonal);
			if (requestGrupoPersonal.getTrabajador().getFlagCompletarAlta() == Constantes.DarAltaPersonal.IND_ALTA_ON) {
				ResultadoCarga resultadoAlta = this.personalContratistaService.completarAltaPersonal(
						requestGrupoPersonal.getTrabajador(), requestGrupoPersonal.getUsuarioAuditoria());
				if (resultadoAlta.equals(ResultadoCarga.CORRECTO)) {
					responseObject.setMensaje("Se completo el alta del personal - "+requestGrupoPersonal.getTrabajador().getNombre());
				}
			}
			if (this.servicioAsignacionPersonal.getError() == null) {
				if (listaGrupoPersonal.size() > 0) {
					AtomicInteger contadorindice = new AtomicInteger(1);
					listaGrupoPersonal.forEach(item -> item.setIndice(contadorindice.getAndAdd(1)));
				}
				responseObject.setResultado(listaGrupoPersonal);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarGrupoPersonal(
			@RequestBody GrupoPersonalRequest requestGrupoPersonal) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<GrupoPersonal> listaGrupoPersonal = this.servicioAsignacionPersonal
					.eliminarGrupoPersonal(requestGrupoPersonal);
			if (this.servicioAsignacionPersonal.getError() == null) {
				if (listaGrupoPersonal.size() > 0) {
					AtomicInteger contadorindice = new AtomicInteger(1);
					listaGrupoPersonal.forEach(item -> item.setIndice(contadorindice.getAndAdd(1)));
				}
				responseObject.setResultado(listaGrupoPersonal);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				return retornarResponseError();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/validar/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> validarEliminarPersonal(
			@RequestBody GrupoPersonalRequest requestGrupoPersonal) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> validacion = this.servicioAsignacionPersonal
					.validarEliminarPersonal(requestGrupoPersonal);

			if (this.servicioAsignacionPersonal.getError() == null) {
				responseObject.setResultado(validacion);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/validar/agregar")
	public ResponseEntity<ResponseObject> validarExistenciaPersonal(
			@RequestBody GrupoPersonalRequest requestGrupoPersonal) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> validacion = this.servicioAsignacionPersonal
					.validarExistenciaPersonal(requestGrupoPersonal);

			if (this.servicioAsignacionPersonal.getError() == null) {
				responseObject.setResultado(validacion);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setError(this.servicioAsignacionPersonal.getError());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<ResponseObject> retornarResponseError() {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setError(this.servicioAsignacionPersonal.getError());
		responseObject.setEstado(Estado.ERROR);
		return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
