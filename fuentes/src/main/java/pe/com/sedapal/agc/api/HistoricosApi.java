package pe.com.sedapal.agc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.request.HistoricoPersonalRequest;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.IMovimientoPersonalServicio;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.Constantes;

@RestController
@RequestMapping(path = "/api")
public class HistoricosApi {
	
	
	private static final Logger logger = LoggerFactory.getLogger(HistoricosApi.class);
	
	@Autowired
	IMovimientoPersonalServicio movimientoServicio;
	
	
	@PostMapping(path = "/consulta-historico/listar-movimiento-persona")
	public ResponseObject consultarHistoricoPersonal(@RequestBody HistoricoPersonalRequest request,
			@RequestParam(name = "pagina", defaultValue = "1") Integer pagina,
			@RequestParam(name = "registros", defaultValue = "10") Integer registros) {
		ResponseObject response = new ResponseObject();
		try {
			ListaPaginada<PersonaContratista> paginaHistorico = movimientoServicio.consultarHistoricoPersonal(request, pagina, registros);
			if (paginaHistorico.getLista().isEmpty()) {
				response.setResultado(paginaHistorico);
				response.setMensaje(Constantes.Mensajes.CONSULTA_VACIA);
				response.setEstado(Estado.OK);
			} else {
				response.setResultado(paginaHistorico);
				response.setEstado(Estado.OK);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarAgcException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarException(e);
		}
		return response;
	}
	
	@PostMapping(path = "/consulta-historico/listar-mov-cargo")
	public ResponseObject consultarHistoricoCargosPersonal(@RequestBody HistoricoPersonalRequest request,
			@RequestParam(name = "pagina", defaultValue = "1") Integer pagina,
			@RequestParam(name = "registros", defaultValue = "10") Integer registros) {
		ResponseObject response = new ResponseObject();
		try {
			ListaPaginada<Movimiento> paginaHistorico = movimientoServicio.consultarHistoricoCargosPersonal(request, pagina, registros);
			if (paginaHistorico.getLista().isEmpty()) {
				response.setResultado(paginaHistorico);
				response.setMensaje(Constantes.Mensajes.CONSULTA_VACIA);
				response.setEstado(Estado.OK);
			} else {
				response.setResultado(paginaHistorico);
				response.setEstado(Estado.OK);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarAgcException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = AgcExceptionUtil.capturarException(e);
		}
		return response;
	}
	
}
