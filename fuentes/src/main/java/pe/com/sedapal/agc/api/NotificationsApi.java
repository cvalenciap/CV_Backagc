package pe.com.sedapal.agc.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import pe.com.sedapal.agc.model.Alerta;
import pe.com.sedapal.agc.model.AlertasQuerie;
import pe.com.sedapal.agc.model.AlertasTemplate;
import pe.com.sedapal.agc.model.CronAlerta;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.INotificationsService;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping("/api")
public class NotificationsApi {
	
	Integer iExitValue;
	@Autowired
	private INotificationsService service;
	

	@GetMapping("/obtenerusuario")
	public List<String> obtenerUsuario() {
		List<String> lista = new ArrayList<String>();
		Map<String,String> env = System.getenv();
		Set<String> keys = env.keySet();
		     for (String key: keys){
		        if (key.toString().trim() == "USERPROFILE") {
		        	lista.add(key + " = "+env.get(key));	
		        }		    	 
		     }
		return lista;
	}

	@GetMapping(path = "/listash", produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] listaSH() {
		String sCarpAct = "/scripts";
		
		File carpeta = new File(sCarpAct);
		String[] lista = carpeta.list();
		return lista;
	}
	
	@GetMapping(path = "/templates", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerTemplates() {
		ResponseObject response = new ResponseObject();
		try {
			List<AlertasTemplate> lista = service.obtenerNotificaciones(null);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			response.setResultado("Error obteniedo Lista");
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/templates/insertar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> insertarTemplates(@RequestBody AlertasTemplate template) {
		ResponseObject response = new ResponseObject();
		try {
			List<AlertasTemplate> lista = service.insertarAlertasTemplate(template);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			response.setResultado("Error Actualizando");
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/templates/editar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> editarTemplates(@RequestBody AlertasTemplate template) {
		ResponseObject response = new ResponseObject();
		try {
			List<AlertasTemplate> lista = service.editarAlertasTemplate(template);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			response.setResultado("Error Actualizando");
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/templates/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> eliminarTemplate(@RequestBody AlertasTemplate template) {
		ResponseObject response = new ResponseObject();
		try {
			List<AlertasTemplate> lista = service.eliminarAlertasTemplate(template);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception excepcion) {
			response.setResultado("Error Eliminando");
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/alerta/editar-periodo-alerta", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> editarPeriodoAlerta(@RequestBody Integer totalPeriodo) {
		ResponseObject responseObject = new ResponseObject();
		Integer resultado = this.service.editarPeriodoAlerta(totalPeriodo);
		if (resultado == 0) {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(this.service.getError());
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {			
			if(this.service.getError() != null) {
				responseObject.setEstado(Estado.ERROR);
				responseObject.setError(this.service.getError());
			}else {
				responseObject.setEstado(Estado.OK);
				responseObject.setResultado(Constantes.MESSAGE_ERROR.get(1000));
			}
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}
	
	
	
	@GetMapping(path = "/lanza_alertas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> lanzaAlertas(@RequestParam("n_sec_template") Long n_sec_template) {
		ResponseObject response = new ResponseObject();	
		String retornoHtml="";
		try {
			retornoHtml = service.lanzaAlertas(n_sec_template);
			System.out.println(retornoHtml);
			response.setResultado(retornoHtml);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setResultado("Error");
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@GetMapping(path = "/crontab", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> contenidoCrontab() throws IOException {
		String ruta = "/var/spool/cron/tomcat";
		//String ruta = "/var/spool/cron/crontabs/alvi";
		File archivo = new File(ruta);
		List<String> resultado = new ArrayList<String>();
		try {
			FileReader fr =new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea;
		      while((linea = br.readLine()) != null)
		         resultado.add(linea);		   
		         fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	@PostMapping(path ="/editar-cron", produces = MediaType.APPLICATION_JSON_VALUE)
	public void editarcron(@RequestBody Map<String, Object> cronAlerta) {
		String ruta ="/var/spool/cron/tomcat";
		//String ruta = "/var/spool/cron/crontabs/alvi";
		String cadena = " curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -H \"Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBR0MxNDAwNyIsImlhdCI6MTU4NTc1NzM1OCwiZXhwIjoxNTg1NzU3Mzc4fQ.7biAzwjCX70_PVnGONWuzOjr0hCsyvmERbv3i-V8SpL5Q0dqtZ9idKn7zmc2jdGi1pbesZDR1d6GzmNTzpLd7A\" -X GET ";
		String salto = "\n\n";
		File archivo = new File(ruta);
        List<CronAlerta> listatemplates = new ArrayList<>();
        CronAlerta item = null;
		List<Map<String, Object>> lista = (List<Map<String, Object>>)cronAlerta.get("data");
		BufferedWriter writer;
		if(archivo.exists()) {
			System.out.println("Existe el archivo");
			try {	
			writer = new BufferedWriter(new FileWriter(archivo));
			writer.write("");
		    for(Map<String, Object> map : lista) {
		    	writer.write(map.get("v_cron_tiempo") + cadena + map.get("v_url") + salto);
		    }
			writer.close();
			Runtime.getRuntime().exec("crontab /var/spool/cron/tomcat");
			//Runtime.getRuntime().exec("crontab /var/spool/cron/crontabs/alvi");
			} catch (IOException e) {
				System.out.println("error " + e.getMessage());
				e.printStackTrace();
			}
		}else {
			System.out.println("No existe el archivo");
		}
		
		
		/*
		
		String cadena = " * * * curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -H \"Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBR0MxNDAwNyIsImlhdCI6MTU4NTc1NzM1OCwiZXhwIjoxNTg1NzU3Mzc4fQ.7biAzwjCX70_PVnGONWuzOjr0hCsyvmERbv3i-V8SpL5Q0dqtZ9idKn7zmc2jdGi1pbesZDR1d6GzmNTzpLd7A\" -X GET http://localhost:8082/api/lanza_alertas?n_sec_template=1";
		String salto = "\n \n";
		File archivo = new File(ruta);
		//String[] tareas = {"25 16","26 16","27 16"};
		BufferedWriter writer;
		if (archivo.exists()) {
		System.out.println("Si existe el archivo");
			try {
				writer = new BufferedWriter(new FileWriter(archivo));
				writer.write("");
				for(int e=0; e<=tareas.length-1; e++ ) {
					writer.write(tareas[e] + cadena + salto);	
					System.out.println(tareas[e] + cadena );
				}
				writer.close();
			} catch (IOException e) {
				System.out.println("error " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("No existe el archivo ");
		}*/
	}
	
	@GetMapping(value = "/alertas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerAlertas(@RequestParam("v_v_tipo_query") Long v_v_tipo_query) throws IOException {
		ResponseObject response = new ResponseObject();
		List<Alerta> alertas = null;
		try {
			alertas = service.obtenerAlertas(v_v_tipo_query);
			if (service.getError() != null) {
				response = Constantes.putAllParameters(Estado.ERROR, service.getError(), null);
			} else {
				response = Constantes.putAllParameters(Estado.OK, null, alertas);
				if (alertas.isEmpty()) {
					return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
				}
			}
			if (response.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception exception) {
			response.setEstado(Estado.ERROR);
			response.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/alertas-queries", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerAlertasQueries(@RequestParam("v_id_alerta") Long v_id_alerta, @RequestParam("v_v_tipo_query") Long v_v_tipo_query) throws IOException {
		ResponseObject response = new ResponseObject();	
		List<AlertasQuerie> alertas = null;
		try {
			alertas = service.obtenerAlertasQueries(v_id_alerta,v_v_tipo_query);
			if (service.getError() != null) {
				response = Constantes.putAllParameters(Estado.ERROR, service.getError(), null);
			} else {
				response = Constantes.putAllParameters(Estado.OK, null, alertas);
				if (alertas.isEmpty()) {
					return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
				}
			}
			if (response.getEstado() == Estado.ERROR) {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		} catch (Exception exception) {
			response.setEstado(Estado.ERROR);
			response.setError(new Error(exception.hashCode(), exception.getMessage(), exception.getStackTrace().toString()));
			
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
	}
}
