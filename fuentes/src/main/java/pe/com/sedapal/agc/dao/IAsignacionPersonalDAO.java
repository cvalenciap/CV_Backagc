package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.GrupoPersonal;
import pe.com.sedapal.agc.model.OficinaGrupo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.request.GrupoPersonalRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IAsignacionPersonalDAO {
	Error getError();
	List<OficinaGrupo> listarOficinaGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo);
	List<GrupoPersonal> listarPersonalGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo);
	Trabajador obtenerTrabajador(String codigoUsuario, String tipoEmpresa);
	List<GrupoPersonal> registrarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal);
	List<GrupoPersonal> eliminarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal);
	Map<String, Object> validarExistenciaPersonal(GrupoPersonalRequest requestGrupoPersonalRequest);
	Map<String, Object> validarEliminarPersonal(GrupoPersonalRequest requestGrupoPersonalRequest);
}
