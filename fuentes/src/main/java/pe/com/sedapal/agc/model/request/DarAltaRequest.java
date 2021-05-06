package pe.com.sedapal.agc.model.request;

import java.util.List;

import pe.com.sedapal.agc.model.PersonaContratista;

public class DarAltaRequest {

	private List<PersonaContratista> listaPersonal;
	private String usuarioAlta;
	private Integer idEmpresa;
	private Integer idOficina;

	public List<PersonaContratista> getListaPersonal() {
		return listaPersonal;
	}

	public void setListaPersonal(List<PersonaContratista> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
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

}
