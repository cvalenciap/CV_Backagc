package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Trabajador;

public class ReprogramacionRequest {
	private Trabajador trabajadorAntiguo;
	private Trabajador trabajadorNuevo;
	private String nroCarga;
	private Integer suministro;
	
	public Trabajador getTrabajadorAntiguo() {
		return trabajadorAntiguo;
	}
	public void setTrabajadorAntiguo(Trabajador trabajadorAntiguo) {
		this.trabajadorAntiguo = trabajadorAntiguo;
	}
	public Trabajador getTrabajadorNuevo() {
		return trabajadorNuevo;
	}
	public void setTrabajadorNuevo(Trabajador trabajadorNuevo) {
		this.trabajadorNuevo = trabajadorNuevo;
	}
	public String getNroCarga() {
		return nroCarga;
	}
	public void setNroCarga(String nroCarga) {
		this.nroCarga = nroCarga;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	
}
