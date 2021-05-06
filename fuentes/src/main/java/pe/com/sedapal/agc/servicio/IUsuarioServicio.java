package pe.com.sedapal.agc.servicio;

import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Usuario;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.request.UsuarioRequest;
import pe.com.sedapal.agc.model.request.ActualizarClaveRequest;

public interface IUsuarioServicio {

	String actualizarCredenciales(ActualizarClaveRequest actualizarClaveRequest);

	Usuario autentificarCredenciales(UsuarioRequest usuarioRequest);
	
	Usuario obtenerPerfilesModulos(UsuarioRequest usuarioRequest);
	
	Trabajador obtenerTrabajador(String usuario);

	String recuperarCredenciales(String usuario);
	
	String logoutToken(String token);
	
	Error getError();
	
	Trabajador obtenerPersonal(String usuario);
	
	Error getErrorUsuario();

}
