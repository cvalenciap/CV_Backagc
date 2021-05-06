package pe.com.sedapal.agc.servicio;

import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.exceptions.FileServerException;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDocumentoServicio {

	String copiarArchivos(MultipartFile archivo);

	String cargarInformacion();

	Error obtenerError();

	void registerUploadedFilesOnline(MultipartFile[] files);

	Adjunto saveAttachFile(Adjunto adjunto);

	String deleteAttachFile(Adjunto adjunto);

	List<Adjunto> obtenerAdjuntosPorCargaRegistroRepository(String uidCarga, Integer uidRegistro,
			PageRequest pageRequest);

	String eliminarAdjuntoPorCargaRegistro(Adjunto adjunto);

	Map<Integer, Object> enviarFileServer(List<MultipartFile> adjuntos) throws Exception;

	FileServerResponse enviarArchivoToFileServer(MultipartFile adjunto) throws Exception;

	Map<Integer, Object> obtenerListaValidasAndNoValidas(List<MultipartFile> adjuntos, Double tamanioMaxPdf,
			Double tamanioMaxJpg, boolean validarNombreArchivo) throws Exception;
	
	List<Error> guardarRegistroDetalleAdjunto(List<FileServerResponse> archivosSubidos, String usuario,
			String uidActividad) throws SQLException;
	String obtenerArchivoBase64FileServer(String rutaArchivo);
}
