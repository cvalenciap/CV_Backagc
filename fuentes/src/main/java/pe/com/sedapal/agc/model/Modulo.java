package pe.com.sedapal.agc.model;

import java.util.List;

public class Modulo {
	private Integer codigo;
	private String descripcion;
	private List<Formulario> formularios;

	public Modulo() {
	}

	public Modulo(Integer codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Formulario> getFormularios() {
		return formularios;
	}

	public void setFormularios(List<Formulario> formularios) {
		this.formularios = formularios;
	}

}
