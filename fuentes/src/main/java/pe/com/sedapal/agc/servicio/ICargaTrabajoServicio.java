package pe.com.sedapal.agc.servicio;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.LineaFallaCarga;
import pe.com.sedapal.agc.model.CargaTrabajo;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.Responsable;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.AnularCargaRequest;
import pe.com.sedapal.agc.model.request.CargaTrabajoRequest;
import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ICargaTrabajoServicio {
	Paginacion getPaginacion();
	Error getError();
	Map<String,Object> obtenerCargas(CargaTrabajo requestParm, Integer iPagina, Integer iRegistros) throws ParseException, Exception;
	Page<CargaTrabajo> buscarPaginaCargaTrabajo(Pageable paginacion);
	void listarCargaTrabajo(List<Map<String, Object>> map);
	Integer registrarEnvioCargaTrabajo(EnvioCargaRequest carga);
	Integer guardarAdjunto(Adjunto adj);
	List<Adjunto> obtenerAdjuntos(Map<String, String> requestParm) throws ParseException, Exception;
	Integer anularCarga(AnularCargaRequest ct);
	Integer enviarCarga(EnvioCargaRequest carga)  throws Exception;
	File generarTrama(String uidCarga, String uidActividad, String idPerfil, int filtro, String usuario);
	Integer cargarArchivoEjecucion(MultipartHttpServletRequest request, String uidActividad, String uidCargaTrabajo,
			String usuario, Integer codOficExt);
	List<CargaTrabajo> obtenerDetalleCarga(String uidCarga);
	ParametrosCargaBandeja obtenerParametros(Integer V_N_IDPERS, Integer V_N_IDPERFIL) throws ParseException, Exception;
	Integer modificarEstadoCarga(CargaTrabajoRequest ct);
	boolean cargarTramaEnLinea(CargaTrabajoRequest cargaTrabajoRequest) throws IOException;
	List<Error> getAllErrrors();
	public Integer anularCargaTrabajo(String V_V_IDCARGA, String V_A_V_USUMOD, String V_MOTIVMOV);
	String obtenerResponsablesEnvio(Responsable datos, String carga, String V_V_IDESTADO, List<Adjunto> V_V_LISTA_ADJ, String V_V_NOMCONTRA);
	List<Adjunto> obtenerListaAdjuntos(String V_V_IDCARGA);
	List<LineaFallaCarga> validaTrama(List<CargaTrabajoRequest> cargaTrabajoRequest) throws Throwable, SecurityException;
	List<Adjunto> obtenerListaAdjuntosSedapal(String V_V_IDCARGA, PageRequest pageSedapal);
	List<Adjunto> obtenerListaAdjuntosContratista(String V_V_IDCARGA, PageRequest pageContratista);
	Adjunto buscarAdjuntoDetalleCarga(String idCarga, Integer idRegistro, String nombreArchivo);
	TamanioAdjuntos obtenerSize();

}
