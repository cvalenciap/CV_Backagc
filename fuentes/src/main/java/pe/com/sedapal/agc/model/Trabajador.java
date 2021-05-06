package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Trabajador {
	private Integer codigo;
	private String nombre;
	private String estado;

	private Integer ficha;
	private String codUsuario;
	private String dirElectronica;
	private Perfil perfil;

	private Integer flagCompletarAlta;
	private String usuarioAgc;
	private String passwordTemporal;
	
	private String documento;

	public String getPasswordTemporal() {
		return passwordTemporal;
	}

	public void setPasswordTemporal(String passwordTemporal) {
		this.passwordTemporal = passwordTemporal;
	}

	public Integer getFlagCompletarAlta() {
		return flagCompletarAlta;
	}

	public void setFlagCompletarAlta(Integer flagCompletarAlta) {
		this.flagCompletarAlta = flagCompletarAlta;
	}

	public String getUsuarioAgc() {
		return usuarioAgc;
	}

	public void setUsuarioAgc(String usuarioAgc) {
		this.usuarioAgc = usuarioAgc;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getFicha() {
		return ficha;
	}

	public void setFicha(Integer ficha) {
		this.ficha = ficha;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getDirElectronica() {
		return dirElectronica;
	}

	public void setDirElectronica(String dirElectronica) {
		this.dirElectronica = dirElectronica;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

}
