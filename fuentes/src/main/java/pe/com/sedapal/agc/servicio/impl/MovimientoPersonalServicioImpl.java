package pe.com.sedapal.agc.servicio.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.impl.MovimientoDAOImpl;
import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.request.HistoricoPersonalRequest;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.servicio.IMovimientoPersonalServicio;

@Service
public class MovimientoPersonalServicioImpl implements IMovimientoPersonalServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(MovimientoPersonalServicioImpl.class);
	
	@Autowired
	MovimientoDAOImpl movimientoDao;
	
	@Override
	public ListaPaginada<PersonaContratista> consultarHistoricoPersonal(HistoricoPersonalRequest request,
			Integer pagina, Integer registros) {
		return movimientoDao.listarMovimientoPersonal(request, pagina, registros);
	}

	@Override
	public ListaPaginada<Movimiento> consultarHistoricoCargosPersonal(HistoricoPersonalRequest request, Integer pagina,
			Integer registros) {
		return movimientoDao.listarMovimientoCargos(request, pagina, registros);
	}

}
