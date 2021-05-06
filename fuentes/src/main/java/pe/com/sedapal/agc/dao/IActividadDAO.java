package pe.com.sedapal.agc.dao;

import java.util.Map;

import pe.com.sedapal.agc.model.Actividad;

public interface IActividadDAO {

	Map<String,Object> listarActividad(Actividad actividad);
}
