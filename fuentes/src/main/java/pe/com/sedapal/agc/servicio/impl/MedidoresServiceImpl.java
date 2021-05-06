package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.sedapal.agc.model.Medidores;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IMedidoresService;
import pe.com.sedapal.agc.dao.IMedidoresDAO;
import pe.com.sedapal.agc.model.response.Error;

@Service
public class MedidoresServiceImpl implements IMedidoresService {
	@Autowired
	IMedidoresDAO dao;
	
	@Override
	public List<Medidores> ListarDetalleMedidores(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception {
		return dao.ListarDetalleMedidores(pageRequest, V_IDCARGA, N_IDREG, V_N_CON_ADJ);
	}
	@Override
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}
	@Override
	public Error getError() {
		return this.dao.getError();
	}
}
