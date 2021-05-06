package pe.com.sedapal.agc.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IPermisosAGC_DAO;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IPermisosAGC_Service;

@Service
public class PermisosAGC_ServiceImpl implements IPermisosAGC_Service {
	private Error error;
	private List<Error> errors;
	
	@Autowired
	private IPermisosAGC_DAO dao;

	@Override
	public String consultarPermisos(Integer N_IDPERFIL) {
		return dao.consultarPermisos(N_IDPERFIL);
	}
	
}
