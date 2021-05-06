package pe.com.sedapal.agc.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.exceptions.FileServerException;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.Documento;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.CargaMasivaResponse;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.servicio.IDocumentoServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.CargaArchivosUtil;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping("")
public class DocumentosController {

	@Autowired
	private IDocumentoServicio documentoServicio;

	@Autowired
	private ICargaTrabajoServicio cargaTrabajoServicio;

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@PostMapping("/piloto/carga")
	public ResponseEntity<ResponseObject> cargar() {
		ResponseObject responseObject = new ResponseObject();
		String resultado = documentoServicio.cargarInformacion();
		responseObject.setEstado(Estado.OK);
		responseObject.setResultado(resultado);

		return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
	}

	@PostMapping("/documentos")
	public ResponseEntity<ResponseObject> uploadFilesOnline(@RequestParam("files") MultipartFile[] files) {
		ResponseObject responseObject = new ResponseObject();
		try {
			documentoServicio.registerUploadedFilesOnline(files);
			if (documentoServicio.obtenerError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(documentoServicio.obtenerError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado("Se realizó correctamente la carga");
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: DocumentosController - uploadFilesOnline()] - " + exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/piloto/documentos")
	public ResponseEntity<CargaMasivaResponse> uploadMultipleFiles(
			@RequestParam("files") MultipartFile[] files,
			@RequestParam("usuario") String user,
			@RequestParam("uidActividad") String codeActivity,
			@RequestParam("tipoAdjunto") String tipoAdjunto) {
		CargaMasivaResponse response = new CargaMasivaResponse();
		boolean validarNombreArchivo = tipoAdjunto.toUpperCase().equals(Constantes.CargaArchivos.ADJUNTO_DETALLE);
		List<Error> listaErrores = new ArrayList<>();
		Map<Integer, Object> listasDevueltas = null;
		Integer cantidadSubidos = 0;
		Integer cantidadErroneos = 0;
		Integer cantidadTotal = files.length;
		List<Error> erroresValidacion = new ArrayList<>();
		List<MultipartFile> archivosToProcesar = new ArrayList<>();
		List<Error> erroresFileServer = new ArrayList<>();
		List<FileServerResponse> archivosSubidos = new ArrayList<>();
		List<Error> erroresTransaccionBd = new ArrayList<>();
		try {
			List<MultipartFile> adjuntosValidos = CargaArchivosUtil.filtrarArchivosJpgOrPdf(files);
			TamanioAdjuntos tamanioAdjuntos = cargaTrabajoServicio.obtenerSize();
			if (tamanioAdjuntos == null) {
				throw new SQLException(Constantes.CargaArchivos.MENSAJE_ERROR_BD);
			} else {
				Double tamanioMaxPdf = tamanioAdjuntos.getSizeMaxPDF();
				Double tamanioMaxJpg = tamanioAdjuntos.getSizeMaxJPG();
				
				listasDevueltas = documentoServicio.obtenerListaValidasAndNoValidas(adjuntosValidos, tamanioMaxPdf,
						tamanioMaxJpg, validarNombreArchivo);
				erroresValidacion = (List<Error>) listasDevueltas
						.get(Constantes.CargaArchivos.LISTA_ERRORES);
				
				listaErrores.addAll(erroresValidacion);
				archivosToProcesar = (List<MultipartFile>) listasDevueltas
						.get(Constantes.CargaArchivos.LISTA_VALIDA);
				listasDevueltas.clear();
				
				cantidadTotal = archivosToProcesar.size() + erroresValidacion.size();

				if(!archivosToProcesar.isEmpty()) {
					listasDevueltas = documentoServicio.enviarFileServer(archivosToProcesar);
					erroresFileServer = (List<Error>) listasDevueltas
							.get(Constantes.CargaArchivos.LISTA_ERRORES);
					listaErrores.addAll(erroresFileServer);
					
					archivosSubidos = (List<FileServerResponse>) listasDevueltas
							.get(Constantes.CargaArchivos.LISTA_VALIDA);
					listasDevueltas.clear();
					
					erroresTransaccionBd = documentoServicio.guardarRegistroDetalleAdjunto(archivosSubidos, user, codeActivity);
					listaErrores.addAll(erroresTransaccionBd);
				}
			}
		} catch (Exception e) {
			trazarLog("uploadMultipleFiles()", e.getMessage(), e);
			response.setEstado(Estado.OK);
			Error error = new Error(1,Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER, Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER);
			response.setError(error);
			listaErrores.add(error);
		} finally {
			cantidadSubidos = archivosSubidos.size() - erroresTransaccionBd.size();
			cantidadErroneos = cantidadTotal - cantidadSubidos;
			response.setEstado(Estado.OK);
			response.setTotal(cantidadTotal);
			response.setProcesados(cantidadSubidos);
			response.setFallidos(cantidadErroneos);
			response.setListaErrores((cantidadSubidos == 0 && cantidadErroneos == 0) ? listaErrores 
									: (cantidadErroneos == 0) ? null : listaErrores);
			if(!listaErrores.isEmpty()) {
				response.setError(listaErrores.get(listaErrores.size()-1));
			}
			response.setMensaje((cantidadSubidos == 0 && cantidadErroneos == 0) ? Constantes.CargaArchivos.MENSAJE_RESPOSE_ERROR
					: (cantidadErroneos == 0) ? Constantes.CargaArchivos.MENSAJE_RESPONSE_OK
											  : Constantes.CargaArchivos.MENSAJE_RESPOSE_ERROR);
		}
		return new ResponseEntity<CargaMasivaResponse>(response, HttpStatus.OK);
	}

	public Documento cargarDocumento(MultipartFile file) {
		String nombreArchivo = documentoServicio.copiarArchivos(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/descarga/")
				.path(nombreArchivo).toUriString();
		return new Documento(nombreArchivo, fileDownloadUri, file.getContentType());
	}

	@RequestMapping(value = "/adjunto/carga-trabajo", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<ResponseObject> saveAttachFile(@RequestBody Adjunto adjunto) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Adjunto adjuntoGuardado = documentoServicio.saveAttachFile(adjunto);
			if (adjuntoGuardado != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(adjuntoGuardado);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(documentoServicio.obtenerError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", exception.getMessage());
			logger.error("[AGC: DocumentosController - saveAttachFile()] - " + exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/adjunto/carga-trabajo", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> deleteAttachFile(Adjunto adjunto) {
		ResponseObject responseObject = new ResponseObject();
		try {
			String escrito = documentoServicio.deleteAttachFile(adjunto);
			if (escrito != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(escrito);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(documentoServicio.obtenerError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", exception.getMessage());
			logger.error("[AGC: DocumentosController - deleteAttachFile()] - " + exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/adjuntos/detalle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarAdjuntosDetalle(@RequestParam("uidCarga") String uidCarga,
			@RequestParam("uidRegistro") Integer uidRegistro, PageRequest pageRequest) {
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<Adjunto> adjuntos = documentoServicio.obtenerAdjuntosPorCargaRegistroRepository(uidCarga, uidRegistro,
					pageRequest);
			if (documentoServicio.obtenerError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, documentoServicio.obtenerError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, adjuntos);
				if (!adjuntos.isEmpty()) {
					paginacion.setPagina(pageRequest.getPagina());
					paginacion.setRegistros(pageRequest.getRegistros());
					paginacion.setTotalRegistros(adjuntos.get(0).getTotalRegistros());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(
					new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: DocumentosController - listarAdjuntosDetalle()] - " + exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/adjunto/detalle", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarAdjuntoDetalle(Adjunto adjunto) {
		ResponseObject responseObject = new ResponseObject();
		try {
			String escrito = documentoServicio.eliminarAdjuntoPorCargaRegistro(adjunto);
			if (escrito != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(escrito);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(documentoServicio.obtenerError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(9999, "Error Interno", exception.getMessage());
			logger.error("[AGC: DocumentosController - eliminarAdjuntoDetalle()] - " + exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private static void trazarLog(String metodo, String mensaje, Exception e) {
		logger.error(String.format("[AGC: DocumentosController - %s] - %s", metodo, mensaje), e);
}
	@PostMapping(value = "/adjunto/obtener-archivo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerArchivoBase64FileServer(@RequestBody Map<String,String> requestParm) {
		ResponseObject response = new ResponseObject();
		try {
			String archivoBase64 = documentoServicio.obtenerArchivoBase64FileServer(requestParm.get("url"));
			response.setResultado(archivoBase64);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(new Error(12, e.getMessage()));
			response.setEstado(Estado.ERROR);
			response.setMensaje(e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
