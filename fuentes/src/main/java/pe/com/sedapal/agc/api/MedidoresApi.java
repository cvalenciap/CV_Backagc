package pe.com.sedapal.agc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.model.Medidores;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IMedidoresService;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

@RestController
@RequestMapping(value = "/medidores")
public class MedidoresApi {
	@Autowired
	private IMedidoresService service;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@GetMapping(path = "/consultar-detalle-medidores", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarDetalleMedidores(PageRequest pageRequest, @RequestParam("V_IDCARGA") String V_IDCARGA, @RequestParam("N_IDREG") Long N_IDREG, @RequestParam("V_N_CON_ADJ") Integer V_N_CON_ADJ) {
		ResponseObject response = new ResponseObject();
		try {
			List<Medidores> lista = this.service.ListarDetalleMedidores(pageRequest, V_IDCARGA, N_IDREG, V_N_CON_ADJ);
			if (this.service.getError() == null) {
				response.setResultado(lista);
				response.setPaginacion(this.service.getPaginacion());
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);		
			} else {
				response.setError(1, "Error Interno", "Error");
				response.setResultado(lista);
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			logger.error("[AGC: MedidoresApi - consultarDetalleMedidores()] - "+e.getMessage());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
