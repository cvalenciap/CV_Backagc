package pe.com.sedapal.agc.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.response.Error;

public class CargaArchivosUtil {

	public static final Logger logger = LoggerFactory.getLogger(CargaArchivosUtil.class);

//	Comentado por configuracion del servidor que bloquea peticiones HEAD
/*	public static boolean verificarEstadoFileServer(String apiEndpointFileServer)
			throws RestClientException, URISyntaxException {
		try {
			URI uri = URI.create(apiEndpointFileServer);
			RequestEntity<Object> request = new RequestEntity<>(HttpMethod.HEAD, uri);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Object> response = restTemplate.exchange(request, Object.class);
			logger.info("=> Servicio disponible");
			logger.debug(response.getHeaders().toString());
			return response.getStatusCode() == HttpStatus.OK;
		} catch (ResourceAccessException e) {
			logger.error(e.getMessage(), e);
			return false;
		} catch (HttpClientErrorException e) {
			if (e.getMessage().contains("400")) {
				logger.info("Error 400 => Servicio disponible, error en la petición");
				return true;
			} else if (e.getMessage().contains("404")) {
				logger.info("Error 404 => Servicio no disponible");
				return false;
			} else {
				logger.error(e.getMessage(), e);
				return false;
			}
			
		}
	}*/

	public static FileServerResponse enviarArchivoToFileServer(File archivoTemporal, String apiEndpointFileServer)
			throws Exception {
		try {
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", new FileSystemResource(archivoTemporal));
			RequestEntity<Object> request = new RequestEntity<Object>(body, HttpMethod.PUT,
					URI.create(apiEndpointFileServer));
//			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body);
			RestTemplate restTemplate = new RestTemplate();
//			ResponseEntity<FileServerResponse> responseFileServer = restTemplate.exchange(apiEndpointFileServer,
//					HttpMethod.PUT, request, FileServerResponse.class);
			ResponseEntity<FileServerResponse> responseFileServer = restTemplate.exchange(request,
					FileServerResponse.class);
			return responseFileServer.getBody();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Async
	public static void eliminarArchivoFileServer(String rutaArchivo, String apiEndpointFileServer) throws Exception {
		String urlDelete = apiEndpointFileServer + (rutaArchivo.startsWith("/") ? rutaArchivo : "/" + rutaArchivo);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		RequestEntity<Object> request = new RequestEntity<Object>(body, headers, HttpMethod.DELETE,
				URI.create(urlDelete));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(request, Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			logger.info(String.format("Archivo [%s] eliminado en el FileServer", rutaArchivo));
		}
	}
	
	public static File crearArchivoCargaTemporal(MultipartFile file) throws Exception {
		if (file.getOriginalFilename().toUpperCase().endsWith(Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_CARGA.toUpperCase())) {
			File tempFile = File.createTempFile(UUID.randomUUID().toString(), Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_CARGA);
			file.transferTo(tempFile);
			return tempFile;
		} else {
			throw new AgcException(Constantes.CargaMasivaPersonal.MSE002,
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, Constantes.CargaMasivaPersonal.MSE002, Constantes.CargaMasivaPersonal.MSE002));
		}
	}
	
	public static File crearArchivoTemporal(MultipartFile file, String extension) throws Exception {
		File tempFile = File.createTempFile(UUID.randomUUID().toString(), extension);
		file.transferTo(tempFile);
		return tempFile;
	}

	public static boolean validarTamanioArchivo(Double tamanio, Double tamanioMax) {
		return tamanio <= tamanioMax * 1024 * 1024;
	}

	public static boolean validarNombreArchivo(String nombreArchivo) {
		return true;
	}

	public static void eliminarArchivoTemporal(File tempFile) throws Exception {
		if (tempFile != null) {
			if (tempFile.exists())
				tempFile.delete();
		}
	}
	
	public static boolean validarFormatoArchivo(String nombreArchivo) throws Exception {
		if (nombreArchivo.toUpperCase().endsWith(Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_FOTO)) {
			return nombreArchivo.matches(Constantes.CargaMasivaPersonal.REGEXP_FOTO);
		} else if (nombreArchivo.toUpperCase().endsWith(Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_CV)) {
			return nombreArchivo.matches(Constantes.CargaMasivaPersonal.REGEXP_CV);
		} else {
			throw new AgcException(Constantes.CargaMasivaPersonal.MSE003);
		}
	}

	public static Double tamanioArchivoToDouble(long tamanioArchivo) {
		return (tamanioArchivo > 0) ? new Long(tamanioArchivo).doubleValue() : null;
	}

	public static List<MultipartFile> filtrarArchivosJpgOrPdf(MultipartFile[] adjuntos) {
		return Arrays.asList(adjuntos).stream().filter(adjunto -> esFormatoImagenOrPdf(adjunto))
				.collect(Collectors.toList());
	}

	public static boolean esFormatoImagenOrPdf(MultipartFile archivo) {
		String nombreArchivo = archivo.getOriginalFilename().toUpperCase();
		return esFormatoPdf(nombreArchivo) || esFormatoJpg(nombreArchivo);
	}

	public static boolean esFormatoPdf(String nombreArchivo) {
		return nombreArchivo.toUpperCase().endsWith(Constantes.CargaArchivos.FORMATO_PDF);
	}

	public static boolean esFormatoJpg(String nombreArchivo) {
		return nombreArchivo.toUpperCase().endsWith(Constantes.CargaArchivos.FORMATO_JPG);
	}
	
	public static boolean esFormatoUTF8(byte[] inputBytes) {
		final String textoConvertido = new String(inputBytes, StandardCharsets.UTF_8);
		final byte[] bytesTexto = textoConvertido.getBytes();
		return Arrays.equals(inputBytes, bytesTexto);
	}

}
