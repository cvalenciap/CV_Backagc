package pe.com.sedapal.agc.dao;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import pe.com.sedapal.agc.model.*;
import pe.com.sedapal.agc.model.request.AnularCargaRequest;
import pe.com.sedapal.agc.model.request.CargaTrabajoRequest;
import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface ICargaTrabajoDAO {
		Paginacion getPaginacion();
		Error getError();
		Map<String,Object> obtenerCargas(CargaTrabajo ct, Integer iPagina, Integer iRegistros) throws ParseException;
		Integer registrarEnvioCargaTrabajo(EnvioCargaRequest carga);		
		Integer guardarAdjunto(Adjunto adj);
		List<Adjunto> obtenerAdjuntos(Adjunto adj);
		Integer anularCarga(AnularCargaRequest ct);
		List<Personal> obtenerDatosCorreo(Personal datos);
		String obtenerResponsablesEnvio(Responsable datos, String carga, String V_V_IDESTADO, List<Adjunto> V_V_LISTA_ADJ, String V_V_NOMCONTRA);
		List<Object> obtenerListaRegistros(String uidCarga);
		Integer registrarMovCargaTrabajoCab(EnvioCargaRequest carga, String estado);
		List<CargaTrabajo> obtenerDetalleCarga(String uidCarga);
		ParametrosCargaBandeja obtenerParametros(Integer V_N_IDPERS, Integer V_N_IDPERFIL);
		Integer modificarEstadoCarga(CargaTrabajoRequest ct);
		List<Error> registrarTramaDistribucionAvisoCobranza(DistribucionAvisoCobranza distribucionAvisoCobranza, CargaTrabajoRequest cargaTrabajoRequest);
		List<Error> registrarTramaDistribucionComunicaciones(DistribucionComunicaciones distribucionComunicaciones, CargaTrabajoRequest cargaTrabajoRequest);
		List<Error> registrarTramaInspeccionesComerciales(InspeccionesComerciales inspeccionesComerciales, CargaTrabajoRequest cargaTrabajoRequest);
		List<Error> registrarTramaTomaEstados(TomaEstados tomaEstados, CargaTrabajoRequest cargaTrabajoRequest);
		List<Error> registrarTramaMedidores(Medidores medidores, CargaTrabajoRequest cargaTrabajoRequest);
		Integer anularCargaTrabajo(String V_V_IDCARGA, String V_A_V_USUMOD, String V_MOTIVMOV);
		File generarTrama(String uidCarga, String uidActividad, String idPerfil, int filtro, String usuario);
		Integer cargarArchivoEjecucion(MultipartHttpServletRequest request, String uidActividad,
				String uidCargaTrabajo, String usuario, Integer codOficExt);
		List<Adjunto> obtenerListaAdjuntos(String V_V_IDCARGA);
		List<Adjunto> obtenerListaAdjuntosSedapal(String V_V_IDCARGA, PageRequest pageSedapal);
		List<Adjunto> obtenerListaAdjuntosContratista(String V_V_IDCARGA, PageRequest pageContratista);
		Adjunto buscarAdjuntoDetalleCarga(String idCarga, Integer idRegistro, String nombreArchivo);
		TamanioAdjuntos obtenerSize();
}
