package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OficinaGrupo {
	private Integer indice;
	private Empresa empresa;
	private Oficina oficina;
	private Grupo grupo;
	
	
	public Integer getIndice() {
		return indice;
	}
	public void setIndice(Integer indice) {
		this.indice = indice;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}		
	
}
