package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Estado;

public class CargoOpenRequest {

	private String descripcion;
	private Estado estado;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
