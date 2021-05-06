package pe.com.sedapal.agc.servicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.DataCorreoCarga;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.ResponseCargaArchivo;
import pe.com.sedapal.agc.model.Trabajador;
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
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IPersonalContratistaServicio {
	Paginacion getPaginacion();

	Error getError();

	ListaPaginada<PersonaContratista> listarPersonalContratista(PersonalContratistaRequest request, Integer pagina,
			Integer registros);

//	Integer registrarPersonalContratista(PersonaContratista personaContratista);
	void registrarPersonalContratista(PersonaContratista personaContratista);

	void actualizarPersonalContratista(PersonaContratista personaContratista);

	Map<String, Object> obtenerParametroBandejaPersonal(Integer idPersonal, Integer idPerfil) throws AgcException;

	List<CargaPersonalResponse> procesarCargaPersonal(List<TramaPersonal> dataPersonal, Integer idEmpresa,
			String usuarioCarga, String nomContratista, String nombreArchivo) throws Exception;

	List<TramaPersonal> recuperarDataArchivo(MultipartFile archivo, Integer idEmpresa, String usuarioCarga)
			throws AgcException, IOException, Exception;

	void enviarCorreoCargaMasivaPersonal(String usuarioCarga, String loteCarga, Integer idEmpresa, Integer idOficina,
			String nomContratista);

	Map<String, List<DataCorreoCarga>> agruparPersonalPorCargo(List<DataCorreoCarga> personalPendiente);

	String construirBodyCorreo(Map<String, List<DataCorreoCarga>> data);

	List<ResponseCargaArchivo> enviarArchivosFileServer(MultipartFile archivoFoto, MultipartFile archivoCv, String dni,
			Integer codEmpleado1, Integer codEmpleado2, Integer idEmpresa, String usuario);

	Future<ResponseCargaArchivo> subirArchivo(MultipartFile file, String extension, String dni, Integer codEmpleado1,
			Integer codEmpleado2, Integer idEmpresa, String usuario) throws Exception;

	DarAltaResponse darAltaPersonal(DarAltaRequest request) throws AgcException, SQLException;
	
	void enviarCorreosAlta(DarAltaResponse resultadoAlta);
	
	CesarPersonalResponse cesarPersonal(CesarPersonalRequest request) throws AgcException, SQLException;

	ResultadoCarga completarAltaPersonal(Trabajador trabajador, String usuarioAuditoria);

	CeseMasivoResponse ceseMasivoPersonal(Integer idEmpresa, String usuarioPeticion);
}
