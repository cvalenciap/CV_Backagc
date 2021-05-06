package pe.com.sedapal.agc.dao;

import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.response.Error;

public interface IUsuarioDAO {
	
	Trabajador obtenerPersonal(String usuario);
	Error getError();
}
