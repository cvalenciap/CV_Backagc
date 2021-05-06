package pe.com.sedapal.agc.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.ProgramaValores;
import pe.com.sedapal.agc.model.Rendimientos;
import pe.com.sedapal.agc.model.RepoCumpActiCierre;
import pe.com.sedapal.agc.model.RepoCumpActiInsp;
import pe.com.sedapal.agc.model.RepoCumpActiNoti;
import pe.com.sedapal.agc.model.RepoCumpActiReapertura;
import pe.com.sedapal.agc.model.RepoCumpActiReci;
import pe.com.sedapal.agc.model.RepoCumpCicloLector;
import pe.com.sedapal.agc.model.RepoEfecActiAvisCob;
import pe.com.sedapal.agc.model.RepoEfecActiTomaEst;
import pe.com.sedapal.agc.model.RepoEfecApertura;
import pe.com.sedapal.agc.model.RepoEfecCierre;
import pe.com.sedapal.agc.model.RepoEfecInspeComer;
import pe.com.sedapal.agc.model.RepoEfecInspeInt;
import pe.com.sedapal.agc.model.RepoEfecLectorTomaEst;
import pe.com.sedapal.agc.model.RepoEfecNotificaciones;
import pe.com.sedapal.agc.model.RepoEfecSostenibilidad;
import pe.com.sedapal.agc.model.RepoInfActiEjec;
import pe.com.sedapal.agc.model.request.ReportesRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IReportesServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(value = "/reportes")
public class ReportesApi {
	@Autowired
	private IReportesServicio servicio;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	@GetMapping(value = "/obtener-periodos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerPeriodos(HttpServletResponse servletResponse) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> periodos = null;
		try {
			periodos = servicio.obtenerPeriodos();
			if(periodos==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(periodos);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerPeriodos()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/obtener-tipo-inspe", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTipoInspe(HttpServletResponse servletResponse) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> tipos = null;
		try {
			tipos = servicio.obtenerTipoInspe();
			if(tipos==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(tipos);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerTipoInspe()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/obtener-item", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerItems(@RequestParam("n_idofic") String oficina) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> items = null;
		try {
			items = servicio.obtenerItems(oficina);
			if(items==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(items);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerItems()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/obtener-actividad", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerActividades(@RequestParam("v_n_item") Long item) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> actividades = null;
		try {
			actividades = servicio.obtenerActividades(item);
			if(actividades==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(actividades);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerActividades()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/obtener-subactividad", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerSubactividades(@RequestParam("v_n_item") Long item,@RequestParam("v_idacti") String actividad ) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> subactividades = null;
		try {
			subactividades = servicio.obtenerSubactividades(item, actividad );
			if(subactividades==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(subactividades);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerSubactividades()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/obtener-frecuencia-alerta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerFrecAlerta(HttpServletResponse servletResponse) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> tipos = null;
		try {
			tipos = servicio.obtenerFrecAlerta();
			if(tipos==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(tipos);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerFrecAlerta()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/repoinfactiejec.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoinfactiejec(HttpServletResponse servletResponse, @RequestBody List<RepoInfActiEjec> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
 			rutaPDF = servicio.generarReporteInfActiEjec(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}

	@PostMapping(value = "/repoinfactiejeccons.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoinfactiejeccons(HttpServletResponse servletResponse, @RequestBody List<RepoInfActiEjec> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarReporteInfActiEjecCons(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}	
	
	@PostMapping(path = "/repoinfactiejec.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void escribirExcelMesa(HttpServletResponse servletResponse, @RequestBody List<RepoInfActiEjec> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoinfactiejec", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> getAll(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoInfActiEjec> reportes = servicio.obtenerListaRepoInfActiEjec(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping(path="/obtener-usuarios", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerUsuarios(@RequestBody ReportesRequest request) throws IOException{
		List<String> resultado = new ArrayList<String>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.obtenerUsuarios(request);
			if(resultado==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(resultado);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerUsuarios()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}	
	
	@PostMapping(path="/obtener-subactividad", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerSubactividad(@RequestBody ReportesRequest request) throws IOException{
		List<String> resultado = new ArrayList<String>();
		ResponseObject response = new ResponseObject();
		
		try {
			resultado = servicio.obtenerSubactividades(request);
			if(resultado==null) {
				response.setResultado(this.servicio.getError().getMensajeInterno());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);				
			} else {
				response.setResultado(resultado);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response.setResultado(null);
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: ReportesApi - obtenerSubactividad()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
	}


	//PROGRAMA-VALORES
	
	@PostMapping(path="/listar-programa-valores")
	public ResponseEntity<ResponseObject> listarProgramaValores(@RequestBody ProgramaValores request) throws IOException{
		List<ProgramaValores> resultado = new ArrayList<ProgramaValores>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.listarProgramaValores(request);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/programa-valores")
	public ResponseEntity<ResponseObject> creaProgramaValores(@RequestBody ProgramaValores request) throws IOException{
		List<ProgramaValores> resultado = new ArrayList<ProgramaValores>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.crearProgramaValores(request);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path="/programa-valores")
	public ResponseEntity<ResponseObject> updateProgramaValores(@RequestBody ProgramaValores request) throws IOException{
		ResponseObject response = new ResponseObject();
		Integer resultado = 0;
		try {
			resultado = servicio.updateProgramaValores(request);
			switch (resultado) {
			case 0:
				response.setResultado("No se pudo actualizar el Registro");
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			case 1:
				response.setResultado("Registro Actualizado Satisfactoriamente");
				response.setEstado(Estado.OK);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			case 2:
				response.setResultado("Registro No existente");
				response.setEstado(Estado.ERROR);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
			default:
				response.setResultado("Error de Servicio");
				response.setEstado(Estado.ERROR);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);				
			}
				
		} catch (Exception excepcion) {
			response.setResultado("Error de Servicio");
			response.setEstado(Estado.ERROR);								
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/eliminar-programa-valores")
	public ResponseEntity<ResponseObject> eliminaProgramaValores(@RequestBody ProgramaValores request) throws IOException{
		List<ProgramaValores> resultado = new ArrayList<ProgramaValores>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.eliminarProgramaValores(request);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// RENDIMIENTOS
	
	@PostMapping(path="/listar-rendimientos")
	public ResponseEntity<ResponseObject> listarRendimientos(@RequestBody Rendimientos request) throws IOException{
		List<Rendimientos> resultado = new ArrayList<Rendimientos>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.listarRendimientos(request);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path="/rendimientos")
	public ResponseEntity<ResponseObject> creaRendimientos(@RequestBody Rendimientos request) throws IOException{
//		List<Rendimientos> resultado = new ArrayList<Rendimientos>();
		ResponseObject response = new ResponseObject();
		String mensaje = "";
		try {
			mensaje = servicio.crearRendimientos(request);
//			resultado = 
			response.setResultado(mensaje);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			response.setResultado(mensaje);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		}
	}

	@PutMapping(path="/rendimientos")
	public ResponseEntity<ResponseObject> updateRendimientos(@RequestBody Rendimientos request) throws IOException{
		ResponseObject response = new ResponseObject();
		Integer resultado = 0;
		try {
			resultado = servicio.updateRendimientos(request);
			switch (resultado) {
			case 0:
				response.setResultado("No se pudo actualizar el Registro");
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			case 1:
				response.setResultado("Registro Actualizado Satisfactoriamente");
				response.setEstado(Estado.OK);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			case 2:
				response.setResultado("Registro No existente");
				response.setEstado(Estado.ERROR);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
			default:
				response.setResultado("Error de Servicio");
				response.setEstado(Estado.ERROR);								
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);				
			}
				
		} catch (Exception excepcion) {
			response.setResultado("Error de Servicio");
			response.setEstado(Estado.ERROR);								
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path="/eliminar-rendimientos")
	public ResponseEntity<ResponseObject> eliminaRendimientos(@RequestBody Rendimientos request) throws IOException{
		List<Rendimientos> resultado = new ArrayList<Rendimientos>();
		ResponseObject response = new ResponseObject();
		try {
			resultado = servicio.eliminarRendimientos(request);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/repoefecactitomaest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteTomaEstado(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecActiTomaEst> reportes = servicio.obtenerListaRepoEfecActiTomaEst(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/repoefecactitomaest.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecactitomaest(HttpServletResponse servletResponse, @RequestBody List<RepoEfecActiTomaEst> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecActiTomaEst(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecactitomaest.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecactitomaestExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecActiTomaEst> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecActiTomaEstExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@GetMapping(value = "/obtener-ciclos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerCiclos(@RequestParam("periodo") String periodo) throws IOException {
		ResponseObject response = new ResponseObject();
		List<String> ciclos = null;
		try {
			ciclos = servicio.obtenerCiclos(periodo);
			if (servicio.getError() != null) {
				response = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				response = Constantes.putAllParameters(Estado.OK, null, ciclos);
			}
			if (response.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception exception) {
			response.setEstado(Estado.ERROR);
			response.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/repoefeclectortomaest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteLectorTomaEstado(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecLectorTomaEst> reportes = servicio.obtenerListaRepoEfecLectorTomaEst(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/repoefeclectortomaest.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefeclectortomaest(HttpServletResponse servletResponse, @RequestBody List<RepoEfecLectorTomaEst> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecLectorTomaEst(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefeclectortomaest.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefeclectortomaestExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecLectorTomaEst> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecLectorTomaEstExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefecnotificaciones", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteNotificaciones(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecNotificaciones> reportes = servicio.obtenerListaRepoEfecNotificaciones(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecnotificaciones.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecnotificaciones(HttpServletResponse servletResponse, @RequestBody List<RepoEfecNotificaciones> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecNotificaciones(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecnotificaciones.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecnotificacionesExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecNotificaciones> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecNotificacionesExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefecinspecomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteInspeComer(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecInspeComer> reportes = servicio.obtenerListaRepoEfecInspeComer(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecinspecomer.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecinspecomer(HttpServletResponse servletResponse, @RequestBody List<RepoEfecInspeComer> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecInspeComer(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecinspecomer.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecinspecomerExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecInspeComer> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecInspeComerExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	
	@RequestMapping(value = "/repoefecactiaviscob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteEfecActiAvisCob(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecActiAvisCob> reportes = servicio.obtenerListaRepoEfecActiAvisCob(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecactiaviscob.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecactiaviscob(HttpServletResponse servletResponse, @RequestBody List<RepoEfecActiAvisCob> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecActiAvisCob(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecactiaviscob.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteEfecActiAvisCobExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecActiAvisCob> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecActiAvisCobExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefecinspeint", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteEfecInspeInt(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecInspeInt> reportes = servicio.obtenerListaRepoEfecInspeInt(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecinspeint.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecinspeint(HttpServletResponse servletResponse, @RequestBody List<RepoEfecInspeInt> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecInspeInt(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecinspeint.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteEfecInspeIntExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecInspeInt> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecInspeIntExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefeccierre", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteEfecCierre(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecCierre> reportes = servicio.obtenerListaRepoEfecCierre(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefeccierre.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefeccierre(HttpServletResponse servletResponse, @RequestBody List<RepoEfecCierre> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecCierre(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefeccierre.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteEfecCierreExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecCierre> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecCierreExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefecapertura", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteEfecApertura(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecApertura> reportes = servicio.obtenerListaRepoEfecApertura(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecapertura.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecapertura(HttpServletResponse servletResponse, @RequestBody List<RepoEfecApertura> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecApertura(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecapertura.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteEfecAperturaExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecApertura> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecAperturaExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repoefecsostenibilidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteEfecSostenibilidad(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecSostenibilidad> reportes = servicio.obtenerListaRepoEfecSostenibilidad(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repoefecsostenibilidad.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repoefecsostenibilidad(HttpServletResponse servletResponse, @RequestBody List<RepoEfecSostenibilidad> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoEfecSostenibilidad(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repoefecsostenibilidad.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteEfecSostenibilidadExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecSostenibilidad> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoEfecSostenibilidadExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpciclolector", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpCicloLector(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpCicloLector> reportes = servicio.obtenerListaRepoCumpCicloLector(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpciclolector.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpciclolector(HttpServletResponse servletResponse, @RequestBody List<RepoCumpCicloLector> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpCicloLector(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpciclolector.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpCicloLectorExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpCicloLector> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpCicloLectorExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpactinoti", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpActiNoti(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpActiNoti> reportes = servicio.obtenerListaRepoCumpActiNoti(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpactinoti.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpactinoti(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiNoti> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpActiNoti(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpactinoti.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpActiNotiExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiNoti> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpActiNotiExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpactireci", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpActiReci(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpActiReci> reportes = servicio.obtenerListaRepoCumpActiReci(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpactireci.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpactireci(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiReci> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpActiReci(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpactireci.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpActiReciExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiReci> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpActiReciExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpactiinsp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpActiInsp(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpActiInsp> reportes = servicio.obtenerListaRepoCumpActiInsp(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpactiinsp.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpactiinsp(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiInsp> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpActiInsp(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpactiinsp.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpActiInspExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiInsp> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpActiInspExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpacticierre", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpActiCierre(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpActiCierre> reportes = servicio.obtenerListaRepoCumpActiCierre(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpacticierre.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpacticierre(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiCierre> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpActiCierre(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpacticierre.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpActiCierreExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiCierre> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpActiCierreExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpactireapertura", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpActiReapertura(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoCumpActiReapertura> reportes = servicio.obtenerListaRepoCumpActiReapertura(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpactireapertura.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpactireapertura(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiReapertura> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpActiReapertura(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpactireapertura.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void reporteRepoCumpActiReaperturaExcel(HttpServletResponse servletResponse, @RequestBody List<RepoCumpActiReapertura> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpActiReaperturaExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	@RequestMapping(value = "/repocumpnotificaciones", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> reporteCumpNotificaciones(@RequestBody ReportesRequest request){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<RepoEfecNotificaciones> reportes = servicio.obtenerListaRepoCumpNotificaciones(request);
			if (servicio.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, servicio.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, reportes);
				if (!reportes.isEmpty()) {
					paginacion.setPagina(1);
					paginacion.setRegistros(10);
					paginacion.setTotalRegistros(reportes.size());
					responseObject.setPaginacion(paginacion);
				}
			}
			if (responseObject.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		} catch (Exception exception) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			logger.error("[AGC: EmpresaController - getAllCompanies()] - "+exception.getMessage());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/repocumpnotificaciones.pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpnotificaciones(HttpServletResponse servletResponse, @RequestBody List<RepoEfecNotificaciones> detalle) throws IOException {
		String rutaPDF=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaPDF = servicio.generarRepoCumpNotificaciones(detalle);
			File filedelete = new File(rutaPDF);
			if (filedelete.exists()) {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/pdf");
				servletResponse.addHeader("Content-Disposition", "attachment; filename="+rutaPDF);
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			} else {
			}
		} catch (Exception excepcion) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter pw = servletResponse.getWriter();
			pw.println("{\"error\": \""+ excepcion.getMessage() +"\"}");
		}
	}
	
	@PostMapping(path = "/repocumpnotificaciones.xls", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void repocumpnotificacionesExcel(HttpServletResponse servletResponse, @RequestBody List<RepoEfecNotificaciones> detalle) throws  IOException{		
		String rutaXlsx=null;
		ResponseObject response = new ResponseObject();
		try {
			rutaXlsx = servicio.generarRepoCumpNotificacionesExcel(detalle);			
			File filedelete = new File(rutaXlsx);			
			if (!filedelete.exists()) {
                servletResponse.setStatus(HttpStatus.NOT_FOUND.value());	
			} else {
				servletResponse.setStatus(HttpStatus.OK.value());
				servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filedelete.getName() + "\"");
				Files.copy(filedelete.toPath(), servletResponse.getOutputStream());
				servletResponse.getOutputStream().flush();
				filedelete.delete();
			}
		} catch (Exception e) {
			servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	
     	}
	}
	
	
}
