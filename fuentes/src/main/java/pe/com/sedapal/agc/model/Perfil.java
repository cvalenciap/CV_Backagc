package pe.com.sedapal.agc.model;

import java.util.List;

public class Perfil {
	private Integer codigo;
	private String descripcion;
	private List<Modulo> modulos;
	
	public Perfil() {}

	public Perfil(Integer codigo, String descripcion) {
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

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

}
