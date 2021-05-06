package pe.com.sedapal.agc.dao;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;

import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface IDocumentoDAO {

	Error obtenerError();
	String invocarProcedimiento();
	boolean validateFilesOnline(MultipartFile[] files, String usuario);
	boolean saveAttachFileDataBase(Adjunto adjunto);
	boolean deleteAttachFileDataBase(Adjunto adjunto);
	List<Adjunto> obtenerAdjuntosPorCargaRegistro(String uidCarga, Integer uidRegistro, PageRequest pageRequest);
	boolean eliminarAdjuntoPorCargaRegistro(Adjunto adjunto);
//	List<Error> guardarRegistroAdjuntoDetalle(String nombreOriginal, String ruta, String usuario, String uidActividad);
	List<Error> guardarRegistrosAdjuntosDetalle(List<FileServerResponse> archivosSubidos, String usuario, String uidActividad) throws SQLException;
}
