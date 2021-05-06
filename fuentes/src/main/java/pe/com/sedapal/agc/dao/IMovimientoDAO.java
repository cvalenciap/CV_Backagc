package pe.com.sedapal.agc.dao;

import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.request.HistoricoPersonalRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;

public interface IMovimientoDAO {
	
	ListaPaginada<PersonaContratista> listarMovimientoPersonal(HistoricoPersonalRequest request, Integer pagina, Integer registros);
	
	ListaPaginada<Movimiento> listarMovimientoCargos(HistoricoPersonalRequest request, Integer pagina, Integer registros);

}
