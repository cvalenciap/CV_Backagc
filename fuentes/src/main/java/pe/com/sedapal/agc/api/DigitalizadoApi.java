package pe.com.sedapal.agc.api;

import java.io.File;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IDigitalizadoServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/digitalizado")
public class DigitalizadoApi {
	@Autowired
	private IDigitalizadoServicio servicio;	
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@PostMapping(path = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarLogAccion(@RequestBody VisorDigitalizadoRequest requesVisorDigitalizado) {
		ResponseObject response = new ResponseObject();
		Integer resultado = this.servicio.registrarLogAccion(requesVisorDigitalizado);
		if(resultado==1) {
			response.setEstado(Estado.OK);
			response.setResultado("Se ha ingresado la acción");
			response.setError(null);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} else {
			response.setEstado(Estado.ERROR);
			response.setResultado("Error en la Inserción del Log");
			response.setError(null);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/digitalizados", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDigitalizados(@RequestBody DigitalizadoRequest requestDigitalizado,
															   @RequestParam("pagina") Integer pagina,
															   @RequestParam("registros") Integer registros){
		ResponseObject responseObject = new ResponseObject();
		try {
//			List<Digitalizado> listaDigitalizados = this.servicio.listarDigitalizados(requestDigitalizado, pagina, registros);
			Map<String, Object> listaDigitalizados = this.servicio.listarDigitalizadosPorActividad(requestDigitalizado, pagina, registros);
			if(listaDigitalizados != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaDigitalizados);				
				responseObject.setPaginacion(this.servicio.getPaginacion());
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}else {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setResultado(this.servicio.getError());				
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch(Exception e) {
			responseObject.setResultado(Constantes.MESSAGE_ERROR.get(9999));
			responseObject.setEstado(Estado.ERROR);
			logger.error("[AGC: DigitalizadoApi - obtenerDigitalizados()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/digitalizados.excel/{idActividad}/{idOficina}/{fecInicio}/{fecFin}")
	public void generarArchivoExcelDigitalizados(HttpServletResponse servletResponse, DigitalizadoRequest requestDigitalizado, PageRequest pageRequest,
			@PathVariable("idActividad") String idActividad, @PathVariable("idOficina") Integer idOficina,
			@PathVariable("fecInicio") String fecInicio, @PathVariable("fecFin") String fecFin){
		try {
			// Actividad y Oficina
			Actividad actividad = new Actividad();
			Oficina oficina = new Oficina();
			if(!idActividad.equals("0")) {
				actividad.setCodigo(idActividad);
			}
			if(idOficina > 0) {
				oficina.setCodigo(idOficina);
			}
			requestDigitalizado.setActividad(actividad);
			requestDigitalizado.setOficina(oficina);
			// Fecha Inicio/Fin
			if(!fecInicio.equals("NULO") && !fecFin.equals("NULO")) {
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date1 = format.parse(fecInicio);
				Date dateInicio = new Date(date1.getTime());
				
				java.util.Date date2 = format.parse(fecFin);
				Date dateFin = new Date(date2.getTime());						
				
				requestDigitalizado.setFechaInicio(dateInicio);
				requestDigitalizado.setFechaFin(dateFin);				
			}
			
			File documento = servicio.generarArchivoExcelDigitalizado(requestDigitalizado, pageRequest.getPagina(), pageRequest.getRegistros());
			
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
			logger.error("[AGC: DigitalizadoApi - generarArchivoExcelDigitalizados()] - "+e.getMessage());
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@GetMapping(path = "/parametros-busqueda", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametrosBusquedaDigitalizados(@RequestParam("idPers") Integer idPers, @RequestParam("idPerfil") Integer idPerfil) {
		ResponseObject responseObject = new ResponseObject();
		try {
			
			ParametrosCargaBandeja resultado = this.servicio.obtenerParametrosBusquedaDigitalizados(idPers, idPerfil);
			
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
			logger.error("[AGC: CargaTrabajoApi - obtenerParametrosBandeja()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(path = "/visor-digitalizado", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseObject> obtenerAdjuntosDigitalizados(@RequestBody VisorDigitalizadoRequest requestVisorDigitalizado){
		ResponseObject responseObject = new ResponseObject();
		try {
			requestVisorDigitalizado.setTipoArchivo("PDF");
			String rutaDigitalizado = this.servicio.obtenerAdjuntosDigitalizados(requestVisorDigitalizado);
			if(rutaDigitalizado != null) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(rutaDigitalizado);				
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
	
	@PostMapping(path = "/visor-digitalizado-jpg", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseObject> obtenerAdjuntosDigitalizadosJpg(@RequestBody VisorDigitalizadoRequest requestVisorDigitalizado){
		ResponseObject responseObject = new ResponseObject();
		try {
			requestVisorDigitalizado.setTipoArchivo("JPG");
			String []imagenes = this.servicio.obtenerAdjuntosDigitalizadosImagen(requestVisorDigitalizado);
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
	
	@GetMapping(path = "/digitalizados/obtener-parametros-periodo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerDiasPeriodo() {
		ResponseObject responseObject = new ResponseObject();
		try {
			DuracionDigitalizados duracion = this.servicio.obtenerParametrosDuracion();
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
			logger.error("[AGC: DigitalizadoApi - obtenerDias()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
