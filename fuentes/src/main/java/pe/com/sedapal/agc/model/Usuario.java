package pe.com.sedapal.agc.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {
	private String usuario;
	private Integer uidSistema;
	private String descripcionSistema;
	private String token;
	private Integer numPerfilesActivos;
	private List<Perfil> menu;
	private List<Perfil> perfiles;
	private List<Modulo> modulos;
	private List<String> permisos;
	private String tiempoSesion;		

	public Usuario() {
		super();
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public String getToken() {
		return token;
	}

	public Integer getNumPerfilesActivos() {
		return numPerfilesActivos;
	}

	public void setNumPerfilesActivos(Integer numPerfilesActivos) {
		this.numPerfilesActivos = numPerfilesActivos;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getUidSistema() {
		return uidSistema;
	}

	public void setUidSistema(Integer uidSistema) {
		this.uidSistema = uidSistema;
	}

	public String getDescripcionSistema() {
		return descripcionSistema;
	}

	public void setDescripcionSistema(String descripcionSistema) {
		this.descripcionSistema = descripcionSistema;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	
	public List<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<String> permisos) {
		this.permisos = permisos;
	}
	
	public List<Perfil> getMenu() {
		return menu;
	}

	public void setMenu(List<Perfil> menu) {
		this.menu = menu;
	}
	
	public String getTiempoSesion() {
		return tiempoSesion;
	}

	public void setTiempoSesion(String tiempoSesion) {
		this.tiempoSesion = tiempoSesion;
	}

}
