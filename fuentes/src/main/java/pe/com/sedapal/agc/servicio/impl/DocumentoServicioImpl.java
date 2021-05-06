package pe.com.sedapal.agc.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.dao.IDocumentoDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.exceptions.FileServerException;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.servicio.IDocumentoServicio;
import pe.com.sedapal.agc.util.CargaArchivosUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.FileStorageService;

@Service
public class DocumentoServicioImpl implements IDocumentoServicio {
	private Error error;

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private RestTemplateBuilder builder;

	@Autowired
	private IDocumentoDAO documentoDAO;

	@Autowired
	ICargaTrabajoServicio cargaTrabajoService;

	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;

	@Override
	public String copiarArchivos(MultipartFile archivo) {
		return fileStorageService.storeFile(archivo);
	}

	@Override
	public String cargarInformacion() {
		return documentoDAO.invocarProcedimiento();
	}

	@Override
	public Error obtenerError() {
		return this.error;
	}

	@Override
	public void registerUploadedFilesOnline(MultipartFile[] files) {
		try {
			this.error = null;
			if (!documentoDAO.validateFilesOnline(files, "LROJAS")) {
				this.error = documentoDAO.obtenerError();
			}
		} catch (Exception exception) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: DocumentoServicioImpl - registerUploadedFilesOnline()] - " + exception.getMessage());
		}
	}

	@Override
	public Adjunto saveAttachFile(Adjunto adjunto) {
		this.error = null;
		if (documentoDAO.saveAttachFileDataBase(adjunto)) {
			return adjunto;
		} else {
			this.error = documentoDAO.obtenerError();
			return null;
		}
	}

	@Override
	public String deleteAttachFile(Adjunto adjunto) {
		this.error = null;
		if (documentoDAO.deleteAttachFileDataBase(adjunto)) {
			return "Se eliminó archivo adjunto asociado al código de carga de trabajo [" + adjunto.getUidCarga() + "]";
		} else {
			this.error = documentoDAO.obtenerError();
			return null;
		}
	}

	@Override
	public List<Adjunto> obtenerAdjuntosPorCargaRegistroRepository(String uidCarga, Integer uidRegistro,
			PageRequest pageRequest) {
		this.error = null;
		return documentoDAO.obtenerAdjuntosPorCargaRegistro(uidCarga, uidRegistro, pageRequest);
	}

	@Override
	public String eliminarAdjuntoPorCargaRegistro(Adjunto adjunto) {
		this.error = null;
		if (documentoDAO.eliminarAdjuntoPorCargaRegistro(adjunto)) {
			return "Se eliminó archivo adjunto asociado al código de carga de trabajo [" + adjunto.getUidCarga() + "]";
		} else {
			this.error = documentoDAO.obtenerError();
			return null;
		}
	}

	@Override
	public Map<Integer, Object> enviarFileServer(List<MultipartFile> adjuntos)
			throws FileServerException, RestClientException, URISyntaxException {
		this.error = null;
		Map<Integer, Object> listasArchivosSubidosAndErrores = new HashMap<>();
		List<Error> listaErrores = new ArrayList<>();
		List<FileServerResponse> datosArchivosSubidos = new ArrayList<>();
		String nombreArchivo = "";
		for (MultipartFile adjunto : adjuntos) {
//			Comentado por configuracion del servidor que bloquea peticiones HEAD
//			if (CargaArchivosUtil.verificarEstadoFileServer(apiEndpointFileServer)) {
//				
//			} else {
//				listaErrores.add(new Error(1, Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER,
//						Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER));
//				trazarLog("enviarFileServer()", Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER);
//			}
			try {
				nombreArchivo = adjunto.getOriginalFilename();
				FileServerResponse fileServerResponse = enviarArchivoToFileServer(adjunto);
				if (fileServerResponse != null) {
					datosArchivosSubidos.add(fileServerResponse);
				} else {
					listaErrores.add(new Error(1,
							Constantes.CargaArchivos.devolverMensajeErrorArchivo(adjunto.getOriginalFilename()),
							Constantes.CargaArchivos.MENSAJE_ERROR_TAMANIOS));
				}
			} catch (HttpClientErrorException e) {
				listaErrores.add(new Error(1, Constantes.CargaArchivos.devolverMensajeErrorArchivo(nombreArchivo),
						Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER));
				trazarLog("enviarFileServer()", e.getMessage());
			} catch (ResourceAccessException e) {
				listaErrores.add(new Error(1, Constantes.CargaArchivos.devolverMensajeErrorArchivo(nombreArchivo),
						Constantes.CargaArchivos.MENSAJE_ERROR_TAMANIOS));
				trazarLog("enviarFileServer()", e.getMessage());
			} catch (SocketException e) {
				listaErrores.add(new Error(1, Constantes.CargaArchivos.devolverMensajeErrorArchivo(nombreArchivo),
						Constantes.CargaArchivos.MENSAJE_ERROR_TAMANIOS));
				trazarLog("enviarFileServer()", e.getMessage());
			} catch (Exception e) {
				listaErrores.add(new Error(1, Constantes.CargaArchivos.devolverMensajeErrorArchivo(nombreArchivo),
						Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER));
				trazarLog("enviarFileServer()", e.getMessage());
			}
		}

		listasArchivosSubidosAndErrores.put(Constantes.CargaArchivos.LISTA_ERRORES, listaErrores);
		listasArchivosSubidosAndErrores.put(Constantes.CargaArchivos.LISTA_VALIDA, datosArchivosSubidos);
		return listasArchivosSubidosAndErrores;
	}

	@Override
	public FileServerResponse enviarArchivoToFileServer(MultipartFile adjunto) throws Exception {
		FileServerResponse fileServerResponse = null;
		File temporal = null;
		try {
			if (adjunto != null) {
				temporal = Constantes.convert(adjunto);
				fileServerResponse = CargaArchivosUtil.enviarArchivoToFileServer(temporal, apiEndpointFileServer);
			}
			return fileServerResponse;
		} finally {
			CargaArchivosUtil.eliminarArchivoTemporal(temporal);
		}
	}

	@Override
	public Map<Integer, Object> obtenerListaValidasAndNoValidas(List<MultipartFile> adjuntos, Double tamanioMaxPdf,
			Double tamanioMaxJpg, boolean validarNombreArchivo) throws Exception {
		List<Error> listaErrores = new ArrayList<>();
		List<MultipartFile> listaAdjuntosValidos = new ArrayList<>();
		Map<Integer, Object> listasValidasAndErrores = new HashMap<>();
		for (MultipartFile adjunto : adjuntos) {
			Double tamanioMax = CargaArchivosUtil.esFormatoPdf(adjunto.getOriginalFilename()) ? tamanioMaxPdf
					: tamanioMaxJpg;
			if (CargaArchivosUtil.validarTamanioArchivo(CargaArchivosUtil.tamanioArchivoToDouble(adjunto.getSize()),
					tamanioMax)) {
				if (validarNombreArchivo) {
					if (CargaArchivosUtil.validarNombreArchivo(adjunto.getOriginalFilename())) {
						listaAdjuntosValidos.add(adjunto);
					} else {
						listaErrores.add(new Error(1, Constantes.CargaArchivos
								.devolverMensajeErrorNomenclatura(adjunto.getOriginalFilename())));
					}
				} else {
					listaAdjuntosValidos.add(adjunto);
				}
			} else {
				listaErrores.add(new Error(1, Constantes.CargaArchivos
						.devolverMensajeErrorTamanioArchivo(adjunto.getOriginalFilename(), tamanioMax)));
			}
		}
		listasValidasAndErrores.put(Constantes.CargaArchivos.LISTA_VALIDA, listaAdjuntosValidos);
		listasValidasAndErrores.put(Constantes.CargaArchivos.LISTA_ERRORES, listaErrores);
		return listasValidasAndErrores;
	}

	@Override
	public List<Error> guardarRegistroDetalleAdjunto(List<FileServerResponse> archivosSubidos, String usuario,
			String uidActividad) throws SQLException {
		return documentoDAO.guardarRegistrosAdjuntosDetalle(archivosSubidos, usuario, uidActividad);
	}

	public static boolean validarArchivo(String nombreArchivo, Double tamanioArchivo, Double tamanioMax,
			boolean validarNomenclatura) {
		boolean valido = true;
		if (!CargaArchivosUtil.validarTamanioArchivo(tamanioArchivo, tamanioMax)) {
			valido = false;
			trazarLog("validarArchivo()",
					Constantes.CargaArchivos.devolverMensajeErrorTamanioArchivo(nombreArchivo, tamanioMax));
		} else {
			if (!CargaArchivosUtil.validarNombreArchivo(nombreArchivo)) {
				valido = false;
				trazarLog("validarArchivo", Constantes.CargaArchivos.devolverMensajeErrorNomenclatura(nombreArchivo));
			}
		}
		return valido;
	}

	private void capturarMensaje(String metodo, Exception ex) {
		this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), ex.getCause().toString());
		logger.error(String.format("[AGC: DocumentoServicioImpl - %s] - ", metodo) + ex.getMessage());
	}

	private static void trazarLog(String metodo, String mensaje) {
		logger.error(String.format("[AGC: DocumentoServicioImpl - %s] - %s", metodo, mensaje));
	}

	@Override
	public String obtenerArchivoBase64FileServer(String rutaArchivo) {
		String archivoBase64 = null;
		final HttpGet request = new HttpGet(rutaArchivo);
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(request);
			InputStream instream = response.getEntity().getContent();
			if (instream.available() > 0) {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
			    byte[] data = new byte[1024];
			    while ((nRead = instream.read(data, 0, data.length)) != -1) {
			        buffer.write(data, 0, nRead);
			    }			 
			    buffer.flush();
			    byte[] byteArray = buffer.toByteArray();				
				archivoBase64 = Base64.getEncoder().encodeToString(byteArray);
			} else {
				throw new AgcException(Constantes.recuperaArchivos.MENSAJE_ERROR_FILESERVER,
						new Error(Constantes.CodigoErrores.ERROR_SERVICIO, Constantes.recuperaArchivos.MENSAJE_ERROR_FILESERVER));
			}
		}catch (AgcException e) { 
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, e.getMessage())); 
		}catch (Exception e) {
			logger.error(Constantes.recuperaArchivos.MENSAJE_ERROR_FILESERVER, e);
			throw new AgcException(Constantes.recuperaArchivos.MENSAJE_ERROR_FILESERVER,
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, e.getMessage()));
		}
		return archivoBase64;
	}

}
