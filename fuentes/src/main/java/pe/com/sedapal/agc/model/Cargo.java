package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cargo {

	private String id;
	private String descripcion;

	private String codigo;
	private String descripcionCargo;
	private Estado estadoCargo;
	private TipoCargo tipoCargo;
	private List<Actividad> actividades = new ArrayList<>();

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcionCargo() {
		return descripcionCargo;
	}

	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}

	public Estado getEstadoCargo() {
		return estadoCargo;
	}

	public void setEstadoCargo(Estado estadoCargo) {
		this.estadoCargo = estadoCargo;
	}

	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
