package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ITipoCargoDAO;
import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.ITipoCargoServicio;

@Service
public class TipoCargoImpl implements ITipoCargoServicio{

	@Autowired
	private ITipoCargoDAO tipoCargoDAO;

	@Override
	public List<TipoCargo> listarTiposCargo() {
		return tipoCargoDAO.listarTiposCargo();
	}

}
