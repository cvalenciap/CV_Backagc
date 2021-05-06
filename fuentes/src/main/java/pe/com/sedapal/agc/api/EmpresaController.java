package pe.com.sedapal.agc.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.CargaTrabajo;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.request.EmpresaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.servicio.IEmpresaServicio;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.FileStorageService;

@RestController
@RequestMapping(value = "")
public class EmpresaController {

	@Autowired
	private ICargaTrabajoServicio servicio;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private IEmpresaServicio serviceCompany;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@RequestMapping(value = "/empresas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> getAllCompanies(EmpresaRequest empresaRequest, PageRequest pageRequest){
		ResponseObject responseObject = new ResponseObject();
		Paginacion paginacion = new Paginacion();
		try {
			List<Empresa> companies = serviceCompany.getCompanies(empresaRequest, pageRequest);
			if (serviceCompany.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, serviceCompany.getError(), null);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, companies);
				if (!companies.isEmpty()) {
					paginacion.setPagina(pageRequest.getPagina());
					paginacion.setRegistros(pageRequest.getRegistros());
					paginacion.setTotalRegistros(companies.get(0).getResult_count());
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

	@RequestMapping(value = "/empresas", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> addCompany(@RequestBody EmpresaRequest empresaRequest){
		ResponseObject responseObject = new ResponseObject();
		String executionMessage = serviceCompany.addCompany(empresaRequest);
		Error error = serviceCompany.getError();
		if(error == null) {
			responseObject.setEstado(Estado.OK);
			responseObject.setResultado(executionMessage);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		} else  {
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(error);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/empresas/listar-cargas-trabajos")
	@ResponseBody
	public ResponseEntity<ResponseObject> listaPaginasEmpresa(@RequestParam("pagina") Optional<Integer> iPagina,
			@RequestParam("registros") Optional<Integer> iRegistros, @RequestParam("uidCarga") String sUidCarga,
			@RequestBody CargaTrabajo request) throws ParseException, Exception {

		ResponseObject responseObject = new ResponseObject();
		Map<String, Object> resultadoCons = this.servicio.obtenerCargas(request, iPagina.orElse(1),
				iRegistros.orElse(1));
		BigDecimal bdError = (BigDecimal) resultadoCons.get("N_RESP");
		if (bdError.intValue() == 0) {
			BigDecimal bdCodError = (BigDecimal) resultadoCons.get("N_EJEC");
			responseObject.setEstado(Estado.ERROR);
			responseObject.setError(bdCodError.intValue(), "Error Interno", (String) resultadoCons.get("V_EJEC"));
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			responseObject.setEstado(Estado.OK);

			List<Map<String, Object>> listaCarga = (List<Map<String, Object>>) resultadoCons.get("C_OUT");
			responseObject.setResultado(listaCarga);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/empresas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> updateCompany(@RequestBody EmpresaRequest empresaRequest){
		ResponseObject responseObject = new ResponseObject();
		try {
			Empresa company = serviceCompany.updateCompany(empresaRequest);
			if(serviceCompany.getError() != null) {
				responseObject = Constantes.putAllParameters(Estado.ERROR, serviceCompany.getError(), null);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			} else {
				responseObject = Constantes.putAllParameters(Estado.OK, null, company);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			}
		}catch (Exception exception){
			logger.error("[AGC: EmpresaController - updateCompany()] - "+exception.getMessage());
			return  null;			
		}
	}

	@RequestMapping(value = "/empresas/{companyCode}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<ResponseObject> deleteCompany(@PathVariable("companyCode") Integer companyCode, @RequestParam("usuario") String user){
		ResponseObject responseObject = new ResponseObject();
		try {
			String messageExecution = serviceCompany.deleteCompany(companyCode, user);
			if(serviceCompany.getError() == null) {
				responseObject = Constantes.putAllParameters(Estado.OK, null, messageExecution);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject = Constantes.putAllParameters(Estado.ERROR, serviceCompany.getError(), null);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exception){
			logger.error("[AGC: EmpresaController - deleteCompany()] - "+exception.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "empresas/{companyCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> getCompanyByCode(@PathVariable("companyCode") Integer companyCode){
		ResponseObject responseObject = new ResponseObject();
		if (companyCode != null || companyCode > 0) {
			Empresa company = serviceCompany.getCompanyByCode(companyCode);
			if (serviceCompany.getError() == null) {
				responseObject = Constantes.putAllParameters(Estado.OK, null, company);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.OK);
			} else {
				responseObject = Constantes.putAllParameters(Estado.ERROR, serviceCompany.getError(), null);
				return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} else {
			responseObject = Constantes.putAllParameters(Estado.ERROR, Constantes.obtenerError(1001, "Código Empresa"), null);
			return new ResponseEntity<ResponseObject>(responseObject, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping(path = "/empresas/listar-empresa-item", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> listarEmpresaItem(
			@RequestParam(required = true, value = "idEmpresa") Integer idEmpresa) {
		ResponseObject response = new ResponseObject();
		try {
			Map<String, Object> lista = this.serviceCompany.listarEmpresaItem(idEmpresa);
				response.setEstado(Estado.OK);
				response.setResultado(lista);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} catch (AgcException e) {
			  response.setError(e.getError());
			  response.setEstado(Estado.ERROR);
			  return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	} 	
	
	@PostMapping(path = "/empresas/registrar-empresa-item/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarEmpresaItem(@RequestBody List<Item> listaItem,
			@PathVariable("idEmpresa") Integer idEmpresa) {
		ResponseObject response = new ResponseObject();
		try {
			this.serviceCompany.registrarEmpresaItem(listaItem, idEmpresa);
			response.setEstado(Estado.OK);
			response.setResultado(Constantes.MantenimientoEmpresas.MSE000);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (AgcException e) {
			response.setError(e.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
