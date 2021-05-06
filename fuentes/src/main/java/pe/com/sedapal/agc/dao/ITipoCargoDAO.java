package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.response.Error;

public interface ITipoCargoDAO {
	List<TipoCargo> listarTiposCargo();
}
