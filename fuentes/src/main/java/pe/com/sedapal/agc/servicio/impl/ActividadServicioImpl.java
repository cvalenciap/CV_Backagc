package pe.com.sedapal.agc.servicio.impl;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IActividadDAO;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.servicio.IActividadServicio;

@Service
public class ActividadServicioImpl implements IActividadServicio {

	@Autowired
	IActividadDAO dao;
	
	@Override
	public Map<String, Object> listaActividad(Map<String, String> requestParm) throws ParseException, Exception {
		
		Actividad actividad = new Actividad();
		
		String sIdActi = requestParm.get("codigo");
		String sEstaActi = requestParm.get("estado");
		String sDesActi = requestParm.get("descripcion");
		
		actividad.setCodigo((String)(sIdActi));
		actividad.setEstado(sEstaActi);
		actividad.setDescripcion(sDesActi);
		
		return dao.listarActividad(actividad);
	}

}
