package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Responsable extends Personal {
		private Number uidPersona;
		private Number uidOficina;
		private Number uidGrupo;
		private String uidActividad;
		private String estadoResponsable;
		private Number uidOficinaRes;
		private Number uidGrupoRes;
		private Number tipoEmpresa;
		
	public Responsable() {
		super();
	}
	
	public Responsable(Number uidPersona, Number uidOficina, Number uidGrupo, String uidActividad, String estadoResponsable, Number uidOficinaRes, Number uidGrupoRes) {
		super();
		this.uidPersona = uidPersona;
		this.uidOficina = uidOficina;
		this.uidGrupo = uidGrupo;
		this.uidActividad = uidActividad;
		this.estadoResponsable = estadoResponsable;
		this.uidOficinaRes = uidOficinaRes;
		this.uidGrupoRes = uidGrupoRes;
	}
	public Number getUidPersona() {
		return uidPersona;
	}
	
	public void setUidPersona(Number valor) {
		this.uidPersona = valor;
	}
	
	public Number getUidOficina() {
		return uidOficina;
	}
	
	public void setUidOficina(Number valor) {
		this.uidOficina = valor;
	}
	
	public Number getUidGrupo() {
		return uidGrupo;
	}
	
	public void setUidGrupo(Number valor) {
		this.uidGrupo = valor;
	}
	
	public String getUidActividad() {
		return uidActividad;
	}
	
	public void setUidActividad(String valor) {
		this.uidActividad = valor;
	}
	
	public String getEstadoResponsable() {
		return estadoResponsable;
	}
	
	public void setEstadoResponsable(String valor) {
		this.estadoResponsable = valor;
	}
	
	public Number getUidOficinaRes() {
		return uidOficinaRes;
	}
	
	public void setUidOficinaRes(Number valor) {
		this.uidOficinaRes = valor;
	}
	
	public Number getUidGrupoRes() {
		return uidGrupoRes;
	}
	
	public void setUidGrupoRes(Number valor) {
		this.uidGrupoRes = valor;
	}
	
	public Number getTipoEmpresa() {
		return tipoEmpresa;
	}
	
	public void setTipoEmpresa(Number valor) {
		this.tipoEmpresa = valor;
	}
}
