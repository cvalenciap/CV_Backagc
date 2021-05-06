package pe.com.sedapal.agc.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.ResponseCargaArchivo;
import pe.com.sedapal.agc.model.TramaPersonal;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.request.CesarPersonalRequest;
import pe.com.sedapal.agc.model.request.DarAltaRequest;
import pe.com.sedapal.agc.model.request.PersonalContratistaRequest;
import pe.com.sedapal.agc.model.response.CargaPersonalResponse;
import pe.com.sedapal.agc.model.response.CesarPersonalResponse;
import pe.com.sedapal.agc.model.response.CeseMasivoResponse;
import pe.com.sedapal.agc.model.response.DarAltaResponse;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IPersonalContratistaServicio;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/api")
public class PersonalContratistaApi {

	private static final Logger logger = LoggerFactory.getLogger(PersonalContratistaApi.class);

	@Autowired
	private IPersonalContratistaServicio personalContratistaServicio;

	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping(path = "/personal-contratista/parametros")
	public ResponseObject obtenerParametrosBandejaPersonal(@RequestParam Integer idPersonal,
			@RequestParam Integer idPerfil) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> parametros = personalContratistaServicio.obtenerParametroBandejaPersonal(idPersonal,
					idPerfil);
			response.setEstado(Estado.OK);
			response.setResultado(parametros);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			response.setError(new Error(12, e.getMessage()));
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/listar")
	public ResponseObject obtenerListaPersonalContratista(@RequestBody PersonalContratistaRequest personalRequest,
			@RequestParam(required = false, name = "pagina") Integer pagina,
			@RequestParam(required = false, name = "registros") Integer registros) {
		ResponseObject response = new ResponseObject();
		try {
			ListaPaginada<PersonaContratista> lista = personalContratistaServicio
					.listarPersonalContratista(personalRequest, pagina, registros);
			if (lista.getLista().isEmpty()) {
				response.setResultado(lista);
				response.setMensaje(Constantes.Mensajes.CONSULTA_VACIA);
				response.setEstado(Estado.OK);
			} else {
				response.setResultado(lista);
				response.setEstado(Estado.OK);
			}
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			response.setError(new Error(12, e.getMessage()));
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarPersonalContratista(
			@RequestBody PersonaContratista personaContratista) {
		ResponseObject responseObject = new ResponseObject();
		try {
			this.personalContratistaServicio.registrarPersonalContratista(personaContratista);
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} catch (AgcException e) {
			responseObject.setError(e.getError());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(path = "/personal-contratista/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> actualizarPersonalContratista(
			@RequestBody PersonaContratista personaContratista) {
		ResponseObject responseObject = new ResponseObject();
		try {
			this.personalContratistaServicio.actualizarPersonalContratista(personaContratista);
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} catch (AgcException e) {
			responseObject.setError(e.getError());
			responseObject.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/personal-contratista/procesar")
	public ResponseObject procesarTxtCargaMasivaPersonal(@RequestParam(name = "archivoCarga") MultipartFile archivodata,
			@RequestParam(name = "idEmpresa") Integer idEmpresa, @RequestParam(name = "idOficina") Integer idOficina,
			@RequestParam(name = "idGrupo") Integer idGrupo, @RequestParam(name = "nomEmpresa") String nomEmpresa,
			@RequestParam(name = "usuarioCarga") String usuarioCarga) {
		ResponseObject response = new ResponseObject();
		try {
			List<TramaPersonal> respuestaTramas = personalContratistaServicio.recuperarDataArchivo(archivodata,
					idEmpresa, usuarioCarga);
			response.setEstado(Estado.OK);
			response.setResultado(respuestaTramas);
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage());
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/carga-masiva")
	public ResponseObject procesarCargaMasivaPersonal(@RequestBody List<TramaPersonal> tramaPersonal,
			@RequestParam(name = "idEmpresa") Integer idEmpresa, @RequestParam(name = "idOficina") Integer idOficina,
			@RequestParam(name = "idGrupo") Integer idGrupo, @RequestParam(name = "nomEmpresa") String nomEmpresa,
			@RequestParam(name = "usuarioCarga") String usuarioCarga,
			@RequestParam(name = "nombreArchivo") String nombreArchivo) {
		ResponseObject response = new ResponseObject();
		try {
			List<CargaPersonalResponse> respuestaCarga = personalContratistaServicio
					.procesarCargaPersonal(tramaPersonal, idEmpresa, usuarioCarga, nomEmpresa, nombreArchivo);
			if (!respuestaCarga.isEmpty()) {
				personalContratistaServicio.enviarCorreoCargaMasivaPersonal(usuarioCarga,
						respuestaCarga.get(0).getLoteCarga(), idEmpresa, idOficina, nomEmpresa);
			}
			response.setEstado(Estado.OK);
			response.setResultado(respuestaCarga);
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage());
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/carga-archivos")
	public ResponseObject cargarArchivoFileServer(
			@RequestParam(name = "foto", required = false) MultipartFile archivoFoto,
			@RequestParam(name = "cv", required = false) MultipartFile archivoCv,
			@RequestParam(name = "dni") String dni, @RequestParam(name = "codEmpleado1") Integer codEmpleado1,
			@RequestParam(name = "codEmpleado2", required = false) Integer codEmpleado2,
			@RequestParam(name = "idEmpresa") Integer idEmpresa, @RequestParam(name = "usuario") String usuario) {
		ResponseObject response = new ResponseObject();
		try {
			List<ResponseCargaArchivo> resultadosCarga = personalContratistaServicio.enviarArchivosFileServer(
					archivoFoto, archivoCv, dni, codEmpleado1, codEmpleado2, idEmpresa, usuario);

			response.setEstado(Estado.OK);
			response.setResultado(resultadosCarga);
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage());
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/alta")
	public ResponseObject darAltaPersonal(@RequestBody DarAltaRequest request) {
		ResponseObject response = new ResponseObject();
		try {
			DarAltaResponse darAltaResultado = personalContratistaServicio.darAltaPersonal(request);
			response.setEstado(Estado.OK);
			response.setResultado(darAltaResultado);
			if (darAltaResultado.getEstado().equals(ResultadoCarga.CORRECTO)) {
				personalContratistaServicio.enviarCorreosAlta(darAltaResultado);
			}
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage());
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getLocalizedMessage());
		}
		return response;
	}

	@PostMapping(path = "/personal-contratista/cesar-personal")
	public ResponseObject cesarPersonalContratista(@RequestBody CesarPersonalRequest request) {
		ResponseObject response = new ResponseObject();
		try {
			CesarPersonalResponse resultadoCese = personalContratistaServicio.cesarPersonal(request);
			response.setEstado(Estado.OK);
			response.setResultado(resultadoCese);
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage());
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getLocalizedMessage());
		}
		return response;
	}

	@GetMapping(path = "/personal-contratista/cese-masivo")
	public ResponseObject ceseMasivoPersonal(@RequestParam(name = "idEmpresa") Integer idEmpresa,
			@RequestParam(name = "usuarioPeticion") String usuarioPeticion) {
		ResponseObject response = new ResponseObject();
		try {
			CeseMasivoResponse resultadoCese = personalContratistaServicio.ceseMasivoPersonal(idEmpresa,
					usuarioPeticion);
			response.setEstado(Estado.OK);
			response.setResultado(resultadoCese);
		} catch (AgcException e) {
			response = AgcExceptionUtil.capturarAgcException(e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			response = AgcExceptionUtil.capturarException(e);
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	@GetMapping(path = "/personal-contratista/plantilla")
	public void descargarPlantillaCargaPersonal(HttpServletResponse response) throws IOException {
		try {
			File file = new ClassPathResource("templates/plantilla_carga_personal.txt").getFile();
			if (file != null) {
				response.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
				response.setHeader("Content-Disposition",
						String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				try {
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener plantilla");
				}

			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener plantilla");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

}
