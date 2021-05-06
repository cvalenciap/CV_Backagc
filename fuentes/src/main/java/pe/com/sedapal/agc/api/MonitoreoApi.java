package pe.com.sedapal.agc.api;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.model.DuracionParametro;
import pe.com.sedapal.agc.model.MonitoreoCabecera;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.request.MonitoreoCabeceraRequest;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestCR;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestDA;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestDC;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestIC;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestME;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestSO;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestTE;
//import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequest;
import pe.com.sedapal.agc.model.request.MonitoreoRequest;
import pe.com.sedapal.agc.model.request.ReprogramacionRequest;
import pe.com.sedapal.agc.model.request.RequestExcel;
import pe.com.sedapal.agc.model.request.VisorMonitoreoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IMonitoreoServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/monitoreo")
public class MonitoreoApi {

	@Autowired
	private IMonitoreoServicio servicio;	
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@PostMapping(path = "/listaCargaTrabajo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerCargaTrabajo(@RequestBody MonitoreoRequest requestMonitoreo){
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaMonitoreo = this.servicio.listarAsignacionTrabajo(requestMonitoreo) ;
				if(listaMonitoreo.get("listaMonitoreoCab") != null) {
					responseObject.setEstado(Estado.OK);
					responseObject.setResultado(listaMonitoreo);				
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}else {
					System.out.println("La cadena regreso vacia...");
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(this.servicio.getError());
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros-busqueda", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametrosBusquedaMonitoreoCab(@RequestParam("idPers") Integer idPers, @RequestParam("idPerfil") Integer idPerfil) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			ParametrosCargaBandeja resultado = this.servicio.obtenerParametrosBusquedaMonitoreoCab(idPers, idPerfil);
			
			if (resultado == null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(resultado);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerParametrosBusquedaMonitoreoCab()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(path = "/listaDetalleCargaTrabajo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDetalleCargaTrabajo(@RequestBody MonitoreoRequest requestMonitoreo){
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaDetalleMonitoreo = this.servicio.listarDetalleAsignacionTrabajo(requestMonitoreo);
			if(listaDetalleMonitoreo.get("listaMonitoreoDet") != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDetalleMonitoreo);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaDetalleCargaTrabajoXCab", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ResponseObject> obtenerDetalleCargaTrabajoXCab(@RequestBody MonitoreoRequest requestMonitoreo){
	public ResponseEntity<ResponseObject> obtenerDetalleCargaTrabajoXCab(@RequestBody Integer idCabecera){
		
		ResponseObject responseObject = new ResponseObject();
		MonitoreoRequest requestMonitoreo = new MonitoreoRequest();
		
		requestMonitoreo.setIdCab(idCabecera);
		
		try {
			Map<String, Object> listaDetalleMonitoreo = this.servicio.listarDetalleAsignacionTrabajo(requestMonitoreo);
			if(listaDetalleMonitoreo.get("listaMonitoreoDet") != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDetalleMonitoreo);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setMensaje("No se encontraron registros para esta cabecera");
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaDetalleCargaTrabajoXFiltro", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDetalleCargaTrabajoXFiltro(@RequestBody MonitoreoRequest requestMonitoreo){
		
		ResponseObject responseObject = new ResponseObject();
		
		try {
			Map<String, Object> listaDetalleMonitoreo = this.servicio.listarDetalleAsignacionTrabajo(requestMonitoreo);
			if(listaDetalleMonitoreo.get("listaMonitoreoDet") != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDetalleMonitoreo);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/anularCabecera", consumes = MediaType.APPLICATION_JSON_VALUE)
	//public ResponseEntity<ResponseObject> anularCabecera(@RequestBody MonitoreoRequest request, 
	  public ResponseEntity<ResponseObject> anularCabecera(@RequestBody Integer idCab,
														 @RequestParam("codEmp") Integer codEmp) {
		ResponseObject responseObject = new ResponseObject();
		try {
			//Integer idCab = 0;
			//idCab = request.getIdCabecera();
			Map<String,Object> resultado = this.servicio.anularRegistroCabecera(idCab, codEmp);

			if (((BigDecimal) resultado.get("N_RESP")).intValue() == 0) {
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - anularCabecera()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/periodoMonitoreo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDiasPeriodo() {
		ResponseObject responseObject = new ResponseObject();
		try {
			DuracionParametro duracion = this.servicio.obtenerParametrosDuracion();
			if(duracion==null) {
				responseObject.setResultado(this.servicio.getError().getMensajeInterno());
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);				
			} else {
				responseObject.setResultado(duracion);
				responseObject.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			responseObject.setResultado(null);
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDiasPeriodo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/cargaArchivoProgramacion")
	  public ResponseEntity<ResponseObject> cargaArchivoProgramacion(@RequestParam("files") MultipartFile file,
																	@RequestParam("codEmp") Integer codEmp, 
																	@RequestParam("codActividad") String codActividad,
																	@RequestParam("codEmpresa") Integer codEmpresa,
																	@RequestParam("codOficina") Integer codOficina) {
		ResponseObject responseObject = new ResponseObject();
		try {

			Map<String,Object> resultado = this.servicio.cargaArchivoProgramacion(file, codEmp, codActividad, codEmpresa, codOficina);

			if ((resultado.get("N_RESP")).equals("0")) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setMensaje(resultado.get("mensaje").toString());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				
				int n_ejec = ((BigDecimal) resultado.get("N_EJEC")).intValue();
				
				if (n_ejec == -1) {
					Error error = new Error(1, resultado.get("V_EJEC").toString());

					responseObject.setError(error);
					responseObject.setEstado(Estado.OK);
					responseObject.setMensaje(resultado.get("V_EJEC").toString());
				}else {
					responseObject.setEstado(Estado.OK);
				}
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - cargaArchivoProgramacion()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros-busqueda-monitoreo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametrosBusquedaBandejaMonitoreo(@RequestParam("idPers") Integer idPers, @RequestParam("idPerfil") Integer idPerfil) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			ParametrosCargaBandeja resultado = this.servicio.obtenerParametrosBusquedaBandejaMonitoreo(idPers, idPerfil);
			
			if (resultado == null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(resultado);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerParametrosBusquedaMonitoreoCab()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(path = "/listaMonitoreoCabecera", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoCabecera(@RequestBody MonitoreoCabeceraRequest requestMonitoreo, @RequestParam("idActividad") String idActividad){
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaMonitoreo = this.servicio.listarMonitoreoCabecera(requestMonitoreo, idActividad);
				if(listaMonitoreo.get("listarMonitoreoCabecera") != null) {
					responseObject.setEstado(Estado.OK);
					responseObject.setResultado(listaMonitoreo);				
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}else {
					System.out.println("La cadena regreso vacia...");
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(this.servicio.getError());
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros-busqueda-ciclo")
	public ResponseEntity<ResponseObject> obtenerParametrosBusquedaBandejaCiclo(@RequestParam("idOficina") Integer idOficina, @RequestParam("idActividad") String idActividad,
			@RequestParam("idPeriodo") String idPeriodo) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			ParametrosCargaBandeja resultado = this.servicio.obtenerParametrosBusquedaCiclo(idOficina, idActividad, idPeriodo);
			
			if (resultado == null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(resultado);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: CargaTrabajoApi - obtenerParametrosBusquedaMonitoreoCab()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(path = "/listaMonitoreoDetalle", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalle(@RequestBody MonitoreoCabecera monitoreo){
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaMonitoreo = this.servicio.listarMonitoreoDetalle(String.valueOf(monitoreo.getTrabajador().getCodigo()), monitoreo.getFechaProgramacion()
					, monitoreo.getActividad().getCodigo());
				if(listaMonitoreo.get("listarMonitoreoDetalle") != null) {
					responseObject.setEstado(Estado.OK);
					responseObject.setResultado(listaMonitoreo);				
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}else {
					System.out.println("La cadena regreso vacia...");
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(this.servicio.getError());
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalle()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleTE", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleTE(@RequestBody MonitoreoDetalleRequestTE requestMonitoreo){
		
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaMonitoreo = this.servicio.listarMonitoreoDetalleTE(requestMonitoreo);
				if(listaMonitoreo.get("listarMonitoreoDetalle") != null) {
					responseObject.setEstado(Estado.OK);
					responseObject.setResultado(listaMonitoreo);				
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}else {
					System.out.println("La cadena regreso vacia...");
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(this.servicio.getError());
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleTE()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleIC", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleIC(@RequestBody MonitoreoDetalleRequestIC requestMonitoreo){
		
		ResponseObject responseObject = new ResponseObject();
		try {
			Map<String, Object> listaMonitoreo = this.servicio.listarMonitoreoDetalleIC(requestMonitoreo);
				if(listaMonitoreo.get("listarMonitoreoDetalle") != null) {
					responseObject.setEstado(Estado.OK);
					responseObject.setResultado(listaMonitoreo);				
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}else {
					System.out.println("La cadena regreso vacia...");
					responseObject.setEstado(Estado.ERROR);
					responseObject.setResultado(this.servicio.getError());
					return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
				}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleIC()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleDA", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreDetalleDA(@RequestBody MonitoreoDetalleRequestDA requestMonitoreo) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> listaDetalle = this.servicio.listarMonitoreoDetalleDA(requestMonitoreo);
			if (listaDetalle.get("listarMonitoreoDetalle") != null) {
				response.setEstado(Estado.OK);
				response.setResultado(listaDetalle);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				System.out.println("La cadena regreso vacia...");
				response.setEstado(Estado.ERROR);
				response.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreDetalleDA()] -"+e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleDC", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleDC(@RequestBody MonitoreoDetalleRequestDC requestMonitoreo) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> listaDetalle = this.servicio.listarMonitoreoDetalleDC(requestMonitoreo);
			if (listaDetalle.get("listarMonitoreoDetalle") != null) {
				response.setEstado(Estado.OK);
				response.setResultado(listaDetalle);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				System.out.println("La cadena regreso vacia...");
				response.setEstado(Estado.ERROR);
				response.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleDC()] -"+e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleME", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleME(@RequestBody MonitoreoDetalleRequestME requestMonitoreo) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> listaDetalle = this.servicio.listarMonitoreoDetalleME(requestMonitoreo);
			if (listaDetalle.get("listarMonitoreoDetalle") != null) {
				response.setEstado(Estado.OK);
				response.setResultado(listaDetalle);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				System.out.println("La cadena regreso vacia...");
				response.setEstado(Estado.ERROR);
				response.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleME()] -"+e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleCR", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleCR(@RequestBody MonitoreoDetalleRequestCR requestMonitoreo) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> listaDetalle = this.servicio.listarMonitoreoDetalleCR(requestMonitoreo);
			if (listaDetalle.get("listarMonitoreoDetalle") != null) {
				response.setEstado(Estado.OK);
				response.setResultado(listaDetalle);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				System.out.println("La cadena regreso vacia...");
				response.setEstado(Estado.ERROR);
				response.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleCR()] -"+e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaMonitoreoDetalleSO", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarMonitoreoDetalleCR(@RequestBody MonitoreoDetalleRequestSO requestMonitoreo) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> listaDetalle = this.servicio.listarMonitoreoDetalleSO(requestMonitoreo);
			if (listaDetalle.get("listarMonitoreoDetalle") != null) {
				response.setEstado(Estado.OK);
				response.setResultado(listaDetalle);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				System.out.println("La cadena regreso vacia...");
				response.setEstado(Estado.ERROR);
				response.setResultado(this.servicio.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleCR()] -"+e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/genera-excel-monitoreo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void generaExcelMonitoreo(HttpServletResponse servletResponse, @RequestBody MonitoreoCabeceraRequest requestMonitoreo, 
			@RequestParam("idActividad") String idActividad) {
		ResponseObject response = new ResponseObject();
		try {
			File documento = this.servicio.generarArchivoExcelMonitoreo(requestMonitoreo, idActividad);
			
			if(documento.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=" + documento.getName());
				Files.copy(documento.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				documento.delete();
			}else {
				servletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			}

		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - listarMonitoreoDetalleCR()] -"+e.getMessage());
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@PostMapping(path = "/genera-excel-monitoreo-detalle", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void generaExcelMonitoreoDetalle(HttpServletResponse servletResponse, @RequestBody RequestExcel excelObj/*, 
			@RequestBody List<Object> listaObjetos*/, @RequestParam("idActividad") String idActividad) {
		ResponseObject response = new ResponseObject();
		try {
			MonitoreoCabecera monitoreo = excelObj.getMonitoreo();
			List<Object> listaObjetos = excelObj.getListaObjetos();
			
			String porc_avance;
			Double por_avance;
			por_avance = monitoreo.getCargaProgramada() == 0 ? 0 : monitoreo.getCargaEjecutada().doubleValue() / monitoreo.getCargaProgramada().doubleValue();
			por_avance = por_avance * 100;
			
			if (por_avance < 40) {
				monitoreo.setSemaforo(2);
			}
			if (por_avance > 40 && por_avance < 70) {
				monitoreo.setSemaforo(3);
			}
			if (por_avance > 70) {
				monitoreo.setSemaforo(4);
			}
			
			porc_avance = String.format("%.2f", por_avance.floatValue());
			porc_avance = porc_avance + " %";
			monitoreo.setAvance(porc_avance);
			
			File documento = this.servicio.generarArchivoExcelMonitoreoDetalle(monitoreo, listaObjetos, idActividad);
			
			if(documento.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=" + documento.getName());
				Files.copy(documento.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				documento.delete();
			}else {
				servletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			}

		} catch (Exception e) {
			response.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - generaExcelMonitoreoDetalle()] -"+e.getMessage());
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@PostMapping(path = "/cargaArchivoProgramacionMasiva")
	  public ResponseEntity<ResponseObject> cargaArchivoProgramacionMasiva(@RequestParam("files") MultipartFile file,
																	@RequestParam("codEmp") Integer codEmp, 
																	@RequestParam("codActividad") String codActividad,
																	@RequestParam("codEmpresa") Integer codEmpresa,
																	@RequestParam("nroCarga") String nroCarga,
																	@RequestParam("fecReasignacion") String fecReasignacion) {
		ResponseObject responseObject = new ResponseObject();
		try {

			Map<String,Object> resultado = this.servicio.cargaArchivoProgramacionMasiva(file, codEmp, codActividad, codEmpresa, nroCarga,
					fecReasignacion);

			if ((resultado.get("N_RESP")).equals("0")) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setMensaje(resultado.get("mensaje").toString());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				
				int n_ejec = ((BigDecimal) resultado.get("N_EJEC")).intValue();
				
				if (n_ejec == -1) {
					Error error = new Error(1, resultado.get("V_EJEC").toString());

					responseObject.setError(error);
					responseObject.setEstado(Estado.OK);
					responseObject.setMensaje(resultado.get("V_EJEC").toString());
				}else {
					responseObject.setEstado(Estado.OK);
				}
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch(Exception e) {
			responseObject.setError(1, "Error Interno", e.getMessage());
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - cargaArchivoProgramacion()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/listaDetalleReasignaciones", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listaDetalleReasignaciones(@RequestBody MonitoreoRequest requestMonitoreo){
	//public ResponseEntity<ResponseObject> listaDetalleReasignaciones(@RequestParam("idCabecera") Integer idCabecera){	
		ResponseObject responseObject = new ResponseObject();
	//	MonitoreoRequest requestMonitoreo = new MonitoreoRequest();
	//	
	//	requestMonitoreo.setIdCab(idCabecera);
		
		try {
			Map<String, Object> listaDetalleReprogramacion = this.servicio.listaDetalleReasignaciones(requestMonitoreo);
			if(listaDetalleReprogramacion.get("listarReprogramacionDetalle") != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDetalleReprogramacion);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setMensaje("No se encontraron registros para re programación");
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping(path = "/listaTrabajadoresManual", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listaTrabajadoresManual(@RequestBody MonitoreoRequest requestMonitoreo){
		
		ResponseObject responseObject = new ResponseObject();
		
		try {
			Map<String, Object> listaDetalleMonitoreo = this.servicio.listaDetalleReasignaciones(requestMonitoreo);
			if(listaDetalleMonitoreo.get("listarReprogramacionDetalle") != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDetalleMonitoreo);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/reasignarTrabajadorManual", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reasignarTrabajadorManual(@RequestBody ReprogramacionRequest requestReprogramacion){
		
		ResponseObject responseObject = new ResponseObject();

		try {
			Map<String, Object> resultado = this.servicio.reasignarTrabajadorManual(requestReprogramacion);
			if (((BigDecimal) resultado.get("N_RESP")).intValue() == 0) {
				responseObject.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseObject.setEstado(Estado.OK);
				responseObject.setMensaje((String)resultado.get("V_EJEC"));
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: MonitoreoApi - obtenerDetalleCargaTrabajo()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/visor-monitoreo-jpg", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseObject> obtenerAdjuntosDigitalizadosJpg(@RequestBody VisorMonitoreoRequest requestVisor){
		ResponseObject responseObject = new ResponseObject();
		try {
			requestVisor.setTipoArchivo("JPG");
			String []imagenes = this.servicio.obtenerAdjuntosMonitoreoImagen(requestVisor);
			if(imagenes != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(imagenes);				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				if(this.servicio.getError() != null) {
					responseObject.setResultado(this.servicio.getError());	
				}else {
					responseObject.setResultado(this.servicio.getErrorServicio());
				}							
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: DigitalizadoApi - obtenerAdjuntosDigitalizados()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
