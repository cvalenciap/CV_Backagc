package pe.com.sedapal.agc.api;

import java.io.File;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.LogDigitalizado;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.request.DigitalizadoLogRequest;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ILogDigitalizadoServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/logDigitalizado")
public class LogDigitalizadoApi {
	

	@Autowired
	private ILogDigitalizadoServicio servicio;	
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
			
	@PostMapping(path = "/logDigitalizados", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerLogDigitalizados(@RequestBody DigitalizadoLogRequest requestLogDigitalizado,
															   	  @RequestParam("pagina") Integer pagina,
															   	  @RequestParam("registros") Integer registros){
		ResponseObject responseObject = new ResponseObject();
		try {
			List<LogDigitalizado> listaLogDigitalizados = this.servicio.listarLogDigitalizados(requestLogDigitalizado, pagina, registros);
			if(listaLogDigitalizados != null && listaLogDigitalizados.size() > 0) {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(listaLogDigitalizados);				
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
			logger.error("[AGC: LogDigitalizadoApi - obtenerLogDigitalizados()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/logDigitalizados.excel/{fecInicio}/{fecFin}")
	public void generarArchivoExcelDigitalizados(HttpServletResponse servletResponse, DigitalizadoLogRequest requestLogDigitalizado, PageRequest pageRequest,
			@PathVariable("fecInicio") String fecInicio, @PathVariable("fecFin") String fecFin){
		try {
			
			if(!fecInicio.equals("NULO") && !fecFin.equals("NULO")) {
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date1 = format.parse(fecInicio);
				Date dateInicio = new Date(date1.getTime());
				
				java.util.Date date2 = format.parse(fecFin);
				Date dateFin = new Date(date2.getTime());						
				
				requestLogDigitalizado.setFechaInicio(dateInicio);
				requestLogDigitalizado.setFechaFin(dateFin);				
			}
			
			File documento = servicio.generarArchivoExcelLogDigitalizado(requestLogDigitalizado, pageRequest.getPagina(), pageRequest.getRegistros());
			
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
			logger.error("[AGC: LogDigitalizadoApi - generarArchivoExcelDigitalizados()] - "+e.getMessage());
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	

}
