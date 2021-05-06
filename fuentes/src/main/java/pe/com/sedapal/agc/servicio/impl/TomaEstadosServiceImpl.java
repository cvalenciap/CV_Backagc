package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ITomaEstadosDAO;
import pe.com.sedapal.agc.model.TomaEstados;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.ITomaEstadosService;

@Service
public class TomaEstadosServiceImpl implements ITomaEstadosService {
	@Autowired
	ITomaEstadosDAO dao;
	
	@Override
	public List<TomaEstados> ListarTomaEstados(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception {
		return dao.ListarTomaEstados(pageRequest, V_IDCARGA, N_IDREG, V_N_CON_ADJ);
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
