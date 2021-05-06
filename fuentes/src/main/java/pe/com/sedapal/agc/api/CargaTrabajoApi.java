package pe.com.sedapal.agc.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.CargaTrabajo;
import pe.com.sedapal.agc.model.LineaFallaCarga;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.Responsable;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.AnularCargaRequest;
import pe.com.sedapal.agc.model.request.CargaTrabajoRequest;
import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.request.RequestEnvio;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.FileStorageService;

@RestController
@RequestMapping(value = "/init")
public class CargaTrabajoApi {

	@Autowired
	private ICargaTrabajoServicio servicio;

	@Autowired
	private FileStorageService fileStorageService;
	
	private Paginacion paginacion;
	
	private static final Logger logger = LoggerFactory.getLogger(CargaTrabajoApi.class);

	
	@PostMapping(path = "/listar-cargas-trabajos")
	@ResponseBody
	public ResponseEntity<ResponseObject> listaPaginaCargasTrabajo(@RequestParam("pagina") Optional<Integer> iPagina,
			@RequestParam("registros") Optional<Integer> iRegistros, @RequestParam("uidCarga") String sUidCarga,
			@RequestBody CargaTrabajo request) throws ParseException, Exception {
		this.paginacion = new Paginacion();
		this.paginacion.setRegistros(iRegistros.hashCode());
		this.paginacion.setPagina(iPagina.hashCode());	
		ResponseObject responseObject = new ResponseObject();		
		Map<String, Object> resultadoCons = this.servicio.obtenerCargas(request, iPagina.orElse(1),
				iRegistros.orElse(1));
		BigDecimal bdError = (BigDecimal) resultadoCons.get("N_RESP");
		if (bdError.intValue() == 0) {
			BigDecimal bdCodError = (BigDecimal) resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String) resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			responseObject.setEstado(Estado.OK);
			if ((List<Map<String, Object>>) resultadoCons.get("C_OUT")!=null &&
				((List<Map<String, Object>>) resultadoCons.get("C_OUT")).size()>0 &&
				((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("totalRegistros")!=null) {
				Integer totalReg = ((BigDecimal)((List<Map<String, Object>>) resultadoCons.get("C_OUT")).get(0).get("totalRegistros")).intValue();
				this.paginacion.setTotalRegistros(totalReg);
			}
			List<Map<String, Object>> listaCarga = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setPaginacion(this.paginacion);
			responseObject.setResultado(listaCarga);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@PostMapping(path = "/modificarEstadoCarga",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> modificarEstadoCarga( @RequestBody CargaTrabajoRequest request){
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.servicio.modificarEstadoCarga(request);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(500, "Error Interno",  Constantes.MESSAGE_ERROR.get(9999));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	@PostMapping(path = "/cargas-trabajos/obtener-responsables-envio",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerResponsablesEnvio(@RequestBody RequestEnvio requestEnvio, 
																	@RequestParam("carga") String carga,
																	@RequestParam("V_V_IDESTADO") String V_V_IDESTADO,
																	@RequestParam("V_V_NOMCONTRA") String V_V_NOMCONTRA) 
																	{
		ResponseObject responseObject = new ResponseObject();
		try {
			Responsable responsable = new Responsable();
			List<Adjunto> V_V_LISTA_ADJ = new ArrayList<>();
			responsable = requestEnvio.getResponsable();
			V_V_LISTA_ADJ = requestEnvio.getListaAdjuntos();
			String listaMail = this.servicio.obtenerResponsablesEnvio(responsable, carga, V_V_IDESTADO, V_V_LISTA_ADJ, V_V_NOMCONTRA);
			if(listaMail == null) {
				responseObject.setResultado(listaMail);
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.servicio.getError().getCodigo(), "No existe información", this.servicio.getError().getMensaje());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.NOT_FOUND);				
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaMail);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			}
		} catch (Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerResponsablesEnvio()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
	}
	
	@PostMapping(path = "/cargas-trabajos/obtener-adjuntos-sedapal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerListaAdjuntosSedapal(@RequestBody PageRequest pageSedapal, @RequestParam("V_V_IDCARGA") String V_V_IDCARGA) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Adjunto> listaAdjuntos = this.servicio.obtenerListaAdjuntosSedapal(V_V_IDCARGA, pageSedapal);
			if(listaAdjuntos==null) {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			} else {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.OK);
				responseObject.setPaginacion(this.servicio.getPaginacion());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setResultado(null);
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerListaAdjuntosSedapal()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/cargas-trabajos/obtener-adjuntos-contratista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerListaAdjuntosContratista(@RequestParam("V_V_IDCARGA") String V_V_IDCARGA, @RequestBody PageRequest pageContratista ) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Adjunto> listaAdjuntos = this.servicio.obtenerListaAdjuntosContratista(V_V_IDCARGA, pageContratista);
			if(listaAdjuntos==null) {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			} else {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.OK);
				responseObject.setPaginacion(this.servicio.getPaginacion());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setResultado(null);
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerListaAdjuntosContratista()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/cargas-trabajos/obtener-adjuntos-carga", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerListaAdjuntos(@RequestParam("V_V_IDCARGA") String V_V_IDCARGA) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Adjunto> listaAdjuntos = this.servicio.obtenerListaAdjuntos(V_V_IDCARGA);
			if(listaAdjuntos==null) {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			} else {
				responseObject.setResultado(listaAdjuntos);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			responseObject.setResultado(null);
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerListaAdjuntos()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/cargas-trabajos/obtener-size", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerSize() {
		ResponseObject responseObject = new ResponseObject();
		try {
			TamanioAdjuntos sizeAdjuntos = this.servicio.obtenerSize();
			if(sizeAdjuntos==null) {
				responseObject.setResultado(this.servicio.getError().getMensajeInterno());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			} else {
				responseObject.setResultado(sizeAdjuntos);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			responseObject.setResultado(null);
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerSize()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@GetMapping(path = "/cargas-trabajos/obtener-parametros", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametrosBandeja(@RequestParam("V_N_IDPERS") Integer V_N_IDPERS, @RequestParam("V_N_IDPERFIL") Integer V_N_IDPERFIL) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			ParametrosCargaBandeja resultadoCons = this.servicio.obtenerParametros(V_N_IDPERS, V_N_IDPERFIL);
			
			if (resultadoCons == null) {
				responseObject.setResultado(resultadoCons);
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensaje());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(resultadoCons);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerParametrosBandeja()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(path = "/adjunto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> guardarAdjunto(@RequestBody Adjunto adj,
			@RequestParam("files") MultipartFile[] files) {
		ResponseObject response = new ResponseObject();
		try {

			if (files.length == 0) {
				response.setEstado(Estado.ERROR);
				response.setError(8000, "No se ha enviado archivo", "No se ha enviado archivo");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				for (int i = 0; i < files.length; i++) {
					String fileName = fileStorageService.storeFile(files[i]);
				}
			}
			Integer item = this.servicio.guardarAdjunto(adj);
			if (item == 1) {
				response.setResultado(item);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.servicio.getError());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - guardarAdjunto()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping(path = "/adjuntos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarAdjuntos(@RequestBody Map<String, String> requestParm)
			throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		List<Adjunto> lista = this.servicio.obtenerAdjuntos(requestParm);
		if (this.servicio.getError() == null) {
			responseObject.setResultado(lista);
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} else {
			responseObject.setError(this.servicio.getError());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/anular-carga", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> guardarAdjunto(@RequestBody AnularCargaRequest ct) {
		ResponseObject response = new ResponseObject();
		try {
			Integer item = this.servicio.anularCarga(ct);
			if (item == 1) {
				response.setResultado(item);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.servicio.getError());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - guardarAdjunto()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/cargas-trabajos/enviar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> enviarCarga(@RequestBody EnvioCargaRequest ct) {
		ResponseObject response = new ResponseObject();
		try {
			Integer item = this.servicio.enviarCarga(ct);
			if (item == 1) {
				response.setResultado(item);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.servicio.getError());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - enviarCarga()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/cargas-trabajos/trama/{uidCarga}/{uidActividad}/{idPerfil}/{filtro}/{usuario}", produces = MediaType.TEXT_PLAIN_VALUE)
	public void generarTrama(@PathVariable("uidCarga") String uidCarga, @PathVariable("uidActividad") String uidActividad,
			@PathVariable("idPerfil") String idPerfil, @PathVariable("filtro") Integer filtro, @PathVariable("usuario") String usuario,  HttpServletResponse response) {
				
		File archivoGenerado = servicio.generarTrama(uidCarga, uidActividad, idPerfil, filtro.intValue(), usuario);
		
		if(this.servicio.getError() == null) {
			String mimeType = URLConnection.guessContentTypeFromName(archivoGenerado.getName());
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + archivoGenerado.getName() + "\""));
			response.setContentLength((int) archivoGenerado.length());

			InputStream inputStream;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(archivoGenerado));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			} catch (IOException e) {
				logger.error("[AGC: CargaTrabajoApi - generarTrama()] - "+e.getMessage());
			}
			if(archivoGenerado.exists()) {
				archivoGenerado.delete();
			}
		}				
	}
	
	@PostMapping(path = "/cargas-trabajos/archivo-ejecucion/{uidActividad}/{uidCargaTrabajo}/{usuario}/{codOficExt}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> cargarArchivosEjecucion(MultipartHttpServletRequest request,
			@PathVariable("uidActividad") String uidActividad, @PathVariable("uidCargaTrabajo") String uidCargaTrabajo,
			@PathVariable("usuario") String usuario, @PathVariable("codOficExt") Integer codOficExt) {
		ResponseObject response = new ResponseObject();
		try {
			Integer item = this.servicio.cargarArchivoEjecucion(request, uidActividad, uidCargaTrabajo, usuario, codOficExt);
			if (item == 1) {
				response.setEstado(Estado.OK);
				if(this.servicio.getError() != null) {
					response.setResultado(this.servicio.getError().getMensajeInterno());
				}else {
					response.setResultado(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1002));
				}
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setEstado(Estado.ERROR);
				response.setError(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - cargarArchivosEjecucion()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
	}
	
	
	@DeleteMapping(path="/cargas-trabajos/anular-carga-trabajo")
	public ResponseEntity<ResponseObject> anularCargaTrabajo(@RequestParam("V_IDCARGA") String V_IDCARGA, @RequestParam("V_USUMOD") String V_USUMOD, @RequestParam("V_MOTIVMOV") String V_MOTIVMOV) {
		ResponseObject response = new ResponseObject();
		try {
			Integer resultado = this.servicio.anularCargaTrabajo(V_IDCARGA, V_USUMOD, V_MOTIVMOV);
			if (this.servicio.getError() == null) {
				response.setResultado(resultado);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);		
			} else {
				response.setError(1, "Error Interno", "Error");
				response.setResultado(resultado);
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - anularCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/detalle-carga", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDetalleCarga(@RequestParam("uidCarga") String sUidCarga)
			throws ParseException, Exception {
		ResponseObject responseObject = new ResponseObject();
		List<CargaTrabajo> lista = this.servicio.obtenerDetalleCarga(sUidCarga);
		if (this.servicio.getError() == null) {
			responseObject.setResultado(lista);
			responseObject.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} else {
			responseObject.setError(this.servicio.getError());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/cargas-trabajos/trama", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> cargarTramaEnLinea(@RequestBody @Valid List<CargaTrabajoRequest> cargaTrabajoRequest, BindingResult bindingResult) throws Throwable{
		ResponseObject responseObject = new ResponseObject();
		List<LineaFallaCarga> campos=servicio.validaTrama(cargaTrabajoRequest);
		if(campos.size()==0) {		
			List<Error> errors = validarCargaTrabajoRequest(bindingResult);
			if(errors.size() <= 0){
				if(!servicio.cargarTramaEnLinea(cargaTrabajoRequest.get(0))) {
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(servicio.getAllErrrors());
				} else {
					if(servicio.getError() != null) {
						responseObject.setEstado(Estado.ERROR);
						responseObject.setError(servicio.getError());
					} else {
						responseObject.setEstado(Estado.OK);
						responseObject.setResultado("La transacción ha sido realizada de forma satisfactoria");
					}
				}
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(errors);
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} else {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setResultado(campos);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<Error> validarCargaTrabajoRequest(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<Error>();
		if (bindingResult.hasErrors()) {
			for (ObjectError objectError: bindingResult.getAllErrors()){
				String[] parameters = objectError.getDefaultMessage().split("-");
				if (parameters.length == 2) {
					errors.add(Constantes.obtenerError(Integer.parseInt(parameters[0]), parameters[1]));
				}
			}
		}
		return  errors;
	}
	
	@GetMapping(path = "/buscar-adjunto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> buscarAdjuntoDetalleCarga(@RequestParam("carga") String idCarga, @RequestParam("registro") Integer idRegistro, @RequestParam("nombre") String nombreArchivo) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			Adjunto adjunto = this.servicio.buscarAdjuntoDetalleCarga(idCarga, idRegistro, nombreArchivo);
			
			if (this.servicio.getError() != null) {
				responseObject.setResultado(0);
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.servicio.getError().getCodigo(), "Error Interno", this.servicio.getError().getMensaje());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(adjunto);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - buscarAdjuntoDetalleCarga()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
