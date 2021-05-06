package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Trabajador;

public class GrupoPersonalRequest {
	private Integer idPersona;
	private Integer idEmpresa;
	private Integer idOficina;
	private Integer idGrupo;
	private String usuarioAgcPers;
	private String usuarioAuditoria;
	private Trabajador trabajador;

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public String getUsuarioAgcPers() {
		return usuarioAgcPers;
	}

	public void setUsuarioAgcPers(String usuarioAgcPers) {
		this.usuarioAgcPers = usuarioAgcPers;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

}
