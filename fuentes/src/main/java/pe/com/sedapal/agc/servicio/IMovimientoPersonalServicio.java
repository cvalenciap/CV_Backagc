package pe.com.sedapal.agc.servicio;

import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.request.HistoricoPersonalRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;

public interface IMovimientoPersonalServicio {
	
	ListaPaginada<PersonaContratista> consultarHistoricoPersonal(HistoricoPersonalRequest request, Integer pagina, Integer registros);
	
	ListaPaginada<Movimiento> consultarHistoricoCargosPersonal(HistoricoPersonalRequest request, Integer pagina, Integer registros);

}
