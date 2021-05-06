package pe.com.sedapal.agc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import pe.com.sedapal.agc.model.Archivo;

public class UtilAdjunto {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilAdjunto.class);

	public static byte[] convertirStringBase64ToBytes(String cadena) {
		byte[] buf = null;
		try {
			buf = Base64.getDecoder().decode(cadena);
		} catch (Exception e) {
			logger.error("Error al convertir string base64 a bytes.", e.getMessage(), e);
			throw e;
		}
		return buf;
	}

	public static File base64ToFile(Archivo archivo) throws Exception {
		File fileTemp = null;
		try {
			String[] splitData = archivo.getDataArchivo().split(",");
			String data = splitData[1];
			byte[] dataByte = convertirStringBase64ToBytes(data);
			Path pathTempFile = Files.createTempFile("AGC-Temp-", "-"+archivo.getNombreArchivo());
			Files.write(pathTempFile, dataByte);
			fileTemp = pathTempFile.toFile();
		} catch (RestClientException | IOException e) {
			logger.error("Error al convertir base64 a File.", e.getMessage(), e);
			throw e;
		}
		return fileTemp;
	}
	
	public static String obtenerNombreArchivoFromPath(String path) {
		try {
			Path ruta = Paths.get(path);
			return ruta.getFileName().toString();
		} catch (Exception e) {
			return path;
		}
	}
	
}
