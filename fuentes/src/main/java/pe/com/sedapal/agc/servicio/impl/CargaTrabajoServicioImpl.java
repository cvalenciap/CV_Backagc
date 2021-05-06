package pe.com.sedapal.agc.servicio.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import pe.com.sedapal.agc.dao.ICargaTrabajoDAO;
import pe.com.sedapal.agc.model.*;
import pe.com.sedapal.agc.model.request.AnularCargaRequest;
import pe.com.sedapal.agc.model.request.CargaTrabajoRequest;
import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.EmailService;
import pe.com.sedapal.agc.model.enums.Estados;

@Service
public class CargaTrabajoServicioImpl implements ICargaTrabajoServicio {

	private Error error;

	private List<Error> errors;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Autowired
	private ICargaTrabajoDAO dao;

	@Autowired
	EmailService mailService;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date f_Aux;
	Date f_cast;

	public Error getError() {
		if (this.error != null) {
			return this.error;
		} else {
			return this.dao.getError();
		}
	}
	
	private Paginacion paginacion;
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}

	@Override
	public TamanioAdjuntos obtenerSize() {
		this.error = null;
		return dao.obtenerSize();
	}	
	
	@Override
	public List<Adjunto> obtenerListaAdjuntosSedapal(String V_V_IDCARGA, PageRequest pageSedapal) {
		this.error = null;
		return dao.obtenerListaAdjuntosSedapal(V_V_IDCARGA, pageSedapal);
	}
	
	@Override
	public List<Adjunto> obtenerListaAdjuntosContratista(String V_V_IDCARGA, PageRequest pageContratista) {
		this.error = null;
		return dao.obtenerListaAdjuntosContratista(V_V_IDCARGA, pageContratista);
	}
	
	@Override
	public List<LineaFallaCarga> validaTrama(List<CargaTrabajoRequest> cargaTrabajoRequest) throws Throwable, SecurityException {
		Integer x;
		List<LineaFallaCarga> lineas = new ArrayList<LineaFallaCarga>();
		for(x=0;x<=cargaTrabajoRequest.size()-1; x++) {
			Object o = new Object();
			switch (cargaTrabajoRequest.get(x).getUidActividad().toUpperCase()){
				case Constantes.ACT_DIST_AVISO_COBRANZA:
					o = new DistribucionAvisoCobranza();
					break;
				case Constantes.ACT_DIST_COMUNIC:
					o = new DistribucionComunicaciones();
					break;
				case Constantes.ACT_INSP_COMERCIAL:
					o = new InspeccionesComerciales();
					break;	
				case Constantes.ACT_MEDIDORES:
					o = new Medidores();
					break;
				case Constantes.ACT_TOMA_ESTADO:
					o = new TomaEstados();
					break;
			}
			Class<?> c = o.getClass();
			Boolean continuar=true;
			Integer res;
			String campos = "";
			Integer nroLinea=0;
			Map map = (Map) cargaTrabajoRequest.get(x).getActividad();
			Integer i;
			Collection<String> values = map.keySet();
			String[] targetArray = values.toArray(new String[values.size()]);
			LineaFallaCarga falla;
			for(i=0;i<=targetArray.length-1;i++) {
				Field f = c.getDeclaredField(targetArray[i]);
				f.setAccessible(true);
				if(f.getType()==java.lang.Integer.class) {
					try {
						res=Integer.parseInt(map.get(targetArray[i]).toString());				
					} catch (Exception e ) {
						campos = campos + " , " + targetArray[i];
					}
				}else if(f.getType()==java.sql.Date.class || f.getType()==java.time.LocalDate.class) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						java.util.Date d = sdf.parse(map.get(targetArray[i]).toString());
					} catch (Exception e) {
						campos = campos + " , " + targetArray[i];
					}
				}
				nroLinea=x+1;
			}
			if(campos!="") {
				falla = new LineaFallaCarga();
				falla.setLinea(nroLinea);
				falla.setCampos(campos.substring(3,campos.length()));
				falla.setErrores(" Linea [" + nroLinea.toString() + "] Error de formato en los campos [" + campos.substring(3,campos.length()) + "]");
				lineas.add(falla);
			}
		}
		return lineas;
	}
	
	@Override
	public List<Adjunto> obtenerListaAdjuntos(String V_V_IDCARGA) {
		return this.dao.obtenerListaAdjuntos(V_V_IDCARGA);
	}
	
	@Override
	public Integer anularCargaTrabajo(String V_V_IDCARGA, String V_A_V_USUMOD, String V_MOTIVMOV) {
		return this.dao.anularCargaTrabajo(V_V_IDCARGA, V_A_V_USUMOD, V_MOTIVMOV);
	}
	
	public String obtenerResponsablesEnvio(Responsable datos, String carga, String V_V_IDESTADO, List<Adjunto> V_V_LISTA_ADJ, String V_V_NOMCONTRA) {
		return this.dao.obtenerResponsablesEnvio(datos, carga, V_V_IDESTADO, V_V_LISTA_ADJ, V_V_NOMCONTRA);
	}

	@Override
	public Map<String, Object> obtenerCargas(CargaTrabajo requestParm, Integer iPagina, Integer iRegistros)
			throws ParseException, Exception {
		return this.dao.obtenerCargas(requestParm, iPagina, iRegistros);
	}

	private List<CargaTrabajo> listaCargasTrabajo = null;

	@Override
	public Page<CargaTrabajo> buscarPaginaCargaTrabajo(Pageable paginacion) {

		int pageSize = paginacion.getPageSize();
		int currentPage = paginacion.getPageNumber();
		int startItem = currentPage * pageSize;
		
		PageRequest pageRequest = new PageRequest(currentPage, pageSize);
		List<CargaTrabajo> listaCarga;

		if (listaCargasTrabajo.size() < startItem) {
			listaCarga = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listaCargasTrabajo.size());
			listaCarga = listaCargasTrabajo.subList(startItem, toIndex);
		}

		return null;
	}

	@Override
	public void listarCargaTrabajo(List<Map<String, Object>> map) {

		listaCargasTrabajo = new ArrayList<>();
		CargaTrabajo itemCargaTrabajo;
		BigDecimal bdCast;
		String tsCast;

		if (map.size() > 0) {
			for (Map<String, Object> map_1 : map) {
				itemCargaTrabajo = new CargaTrabajo();

				itemCargaTrabajo.setUidCargaTrabajo((String) map_1.get("uidCargaTrabajo"));
				itemCargaTrabajo.setMotivoAnula((String) map_1.get("motivoAnula"));
				String sUidUsuarioE = (String) map_1.get("uidUsuarioE");
				itemCargaTrabajo.setUsuarioNombreE(sUidUsuarioE);
				String sUidUsuarioC = (String) map_1.get("uidUsuarioC");
				itemCargaTrabajo.setUsuarioNombreC(sUidUsuarioC);
				bdCast = (BigDecimal) map_1.get("uidOficina");
				itemCargaTrabajo.setUidOficina(bdCast.intValue());
				itemCargaTrabajo.setDescOficina((String) map_1.get("descOficina"));
				bdCast = (BigDecimal) map_1.get("uidGrupo");
				itemCargaTrabajo.setUidGrupo(bdCast.intValue());
				String estado = (String) map_1.get("uidEstado");
				itemCargaTrabajo.setUidEstado(estado);
				itemCargaTrabajo.setEstado((String) map_1.get("estado"));
				bdCast = (BigDecimal) map_1.get("uidContratista");
				itemCargaTrabajo.setUidContratista(bdCast.intValue());
				itemCargaTrabajo.setDescContratista((String) map_1.get("descContratista"));
				bdCast = (BigDecimal) map_1.get("uidActividad");
				itemCargaTrabajo.setUidContratista(bdCast.intValue());
				itemCargaTrabajo.setDescContratista((String) map_1.get("descActividad"));
				bdCast = (BigDecimal) map_1.get("cantidadEjecutada");
				itemCargaTrabajo.setCantidadEjecutada(bdCast.intValue());
				bdCast = (BigDecimal) map_1.get("cantidadCarga");
				itemCargaTrabajo.setCantidadCarga(bdCast.intValue());
				tsCast = (String) map_1.get("fechaSedapal");
				itemCargaTrabajo.setFechaSedapal(tsCast);
				tsCast = (String) map_1.get("fechaContratista");
				itemCargaTrabajo.setFechaContratista(tsCast);
				tsCast = (String) map_1.get("fechaContratista");
				itemCargaTrabajo.setFechaCarga(tsCast);
				listaCargasTrabajo.add(itemCargaTrabajo);
			}
		}
	}

	@Override
	public Integer registrarEnvioCargaTrabajo(EnvioCargaRequest carga) {
		return this.dao.registrarEnvioCargaTrabajo(carga);
	}

	@Override
	public Integer guardarAdjunto(Adjunto adj) {
		return this.dao.guardarAdjunto(adj);
	}

	@Override
	public List<Adjunto> obtenerAdjuntos(Map<String, String> requestParm) throws ParseException, Exception {

		Adjunto adj = new Adjunto();
		String suidCarga = requestParm.get("uidCarga");
		Integer suidAdjunto = Integer.parseInt(requestParm.get("uidAdjunto"));
		String stipoOrigen = requestParm.get("tipoOrigen");
		String stipo = requestParm.get("tipo");
		String sruta = requestParm.get("ruta");

		adj.setUidCarga(suidCarga);
		adj.setUidAdjunto(suidAdjunto);
		adj.setTipoOrigen(stipoOrigen);
		adj.setTipo(stipo);
		adj.setRuta(sruta);

		return this.dao.obtenerAdjuntos(adj);

	}

	@Override
	public Integer anularCarga(AnularCargaRequest ct) {
		return this.dao.anularCarga(ct);
	}

	@Override
	public Integer enviarCarga(EnvioCargaRequest carga) throws Exception {
		Integer rpta = 1;
		try {
			this.dao.registrarMovCargaTrabajoCab(carga, Estados.ENVIADO.getValorEstado());	

		} catch (Exception ex) {
			logger.error("[AGC: CargaTrabajoServicioImpl - enviarCarga()] - "+ex.getMessage());
			rpta = 0;
		}
		return rpta;

	}

	@Override
	public File generarTrama(String uidCarga, String uidActividad, String idPerfil, int filtro, String usuario) {
		this.error = null;
		return this.dao.generarTrama(uidCarga, uidActividad, idPerfil, filtro, usuario);		
	}
	
	@Override
	public Integer cargarArchivoEjecucion(MultipartHttpServletRequest request, String uidActividad, String uidCargaTrabajo,
			String usuario, Integer codOficExt) {
		return this.dao.cargarArchivoEjecucion(request, uidActividad, uidCargaTrabajo, usuario, codOficExt);
	}	
	
	@Override
	public List<CargaTrabajo> obtenerDetalleCarga(String uidCarga) {
		return this.dao.obtenerDetalleCarga(uidCarga);
	}
	@Override
	public ParametrosCargaBandeja obtenerParametros(Integer V_N_IDPERS, Integer V_N_IDPERFIL) throws ParseException, Exception {
		return this.dao.obtenerParametros(V_N_IDPERS, V_N_IDPERFIL);
	}

	@Override
	public Integer modificarEstadoCarga(CargaTrabajoRequest ct) {
		return this.dao.modificarEstadoCarga(ct);
	}		
	@Override
	public boolean cargarTramaEnLinea(CargaTrabajoRequest cargaTrabajoRequest) throws IOException {
		errors = new ArrayList<Error>();
		ObjectMapper objectMapper = new ObjectMapper();
		String registryActiviy = objectMapper.writeValueAsString(cargaTrabajoRequest.getActividad());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> data = (Map<String, String>) cargaTrabajoRequest.getActividad();	
		try {
			switch (cargaTrabajoRequest.getUidActividad().toUpperCase()){
				case Constantes.ACT_DIST_AVISO_COBRANZA:		
					DistribucionAvisoCobranza distribucionAvisoCobranza = objectMapper.readValue(registryActiviy, DistribucionAvisoCobranza.class);					
					// Inicio - Seteo de fechas					
					distribucionAvisoCobranza.setFechaDistribucion(new java.sql.Date(sdf.parse(data.get("fechaDistribucion")).getTime()));
					// Fin - Seteo de fechas										
					distribucionAvisoCobranza.setCodigoCarga(cargaTrabajoRequest.getUidCargaTrabajo());
					errors = dao.registrarTramaDistribucionAvisoCobranza(distribucionAvisoCobranza, cargaTrabajoRequest);
					if (errors.isEmpty()) { return true; } else { return false; }
				case Constantes.ACT_DIST_COMUNIC:		
					DistribucionComunicaciones distribucionComunicaciones = objectMapper.readValue(registryActiviy, DistribucionComunicaciones.class);
					// Inicio - Seteo de fechas					
					distribucionComunicaciones.setFechaVisita1(new java.sql.Date(sdf.parse(data.get("fechaVisita1")).getTime()));
					distribucionComunicaciones.setFechaVisita2(new java.sql.Date(sdf.parse(data.get("fechaVisita2")).getTime()));
					distribucionComunicaciones.setFechaConcretaNotificacion(new java.sql.Date(sdf.parse(data.get("fechaConcretaNotificacion")).getTime()));
					// Fin - Seteo de fechas
					distribucionComunicaciones.setCodigoCarga(cargaTrabajoRequest.getUidCargaTrabajo());
					errors = dao.registrarTramaDistribucionComunicaciones(distribucionComunicaciones, cargaTrabajoRequest);
					if (errors.isEmpty()) { return true; } else { return false; }
				case Constantes.ACT_INSP_COMERCIAL:		
					InspeccionesComerciales inspeccionesComerciales = objectMapper.readValue(registryActiviy, InspeccionesComerciales.class);
					// Inicio - Seteo de fechas					
					inspeccionesComerciales.setDate_c(new java.sql.Date(sdf.parse(data.get("date_c")).getTime()));
					// Fin - Seteo de fechas
					inspeccionesComerciales.setCodigoCarga(cargaTrabajoRequest.getUidCargaTrabajo());
					errors = dao.registrarTramaInspeccionesComerciales(inspeccionesComerciales, cargaTrabajoRequest);
					if (errors.isEmpty()) { return true; } else { return false; }
				case Constantes.ACT_MEDIDORES:		
					Medidores medidores = objectMapper.readValue(registryActiviy, Medidores.class);
					// Inicio - Seteo de fechas					
					medidores.setFechaEjecucion(new java.sql.Date(sdf.parse(data.get("fechaEjecucion")).getTime()));
					// Fin - Seteo de fechas
					medidores.setCodigoCarga(cargaTrabajoRequest.getUidCargaTrabajo());
					errors = dao.registrarTramaMedidores(medidores, cargaTrabajoRequest);
					if (errors.isEmpty()) { return true; } else { return false; }
				case Constantes.ACT_TOMA_ESTADO:		
					TomaEstados tomaEstados = objectMapper.readValue(registryActiviy, TomaEstados.class);
					tomaEstados.setCodigoCarga(cargaTrabajoRequest.getUidCargaTrabajo());
					errors = dao.registrarTramaTomaEstados(tomaEstados, cargaTrabajoRequest);
					if (errors.isEmpty()) { return true; } else { return false; }
				default:
					errors.add(new Error(9999, "El código del tipo de actividad es incorrecta"));
					return  false;
			}
		} catch (Exception exception) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().getMessage());
			logger.error("[AGC: CargaTrabajoServicioImpl - cargarTramaEnLinea()] - "+exception.getMessage());
		}
		return true;
	}

	@Override
	public List<Error> getAllErrrors() {
		return this.errors;
	}

	@Override
	public Adjunto buscarAdjuntoDetalleCarga(String idCarga, Integer idRegistro, String nombreArchivo) {
		this.error = null;
		return this.dao.buscarAdjuntoDetalleCarga(idCarga, idRegistro, nombreArchivo);
	}	

}
