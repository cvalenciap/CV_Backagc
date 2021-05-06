package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Grupo {
	
	private Integer idGrupo;
	private String descGrupo;
	private String estdGrupo;
	
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getDescGrupo() {
		return descGrupo;
	}
	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}
	public String getEstdGrupo() {
		return estdGrupo;
	}
	public void setEstdGrupo(String estdGrupo) {
		this.estdGrupo = estdGrupo;
	}
		
}
