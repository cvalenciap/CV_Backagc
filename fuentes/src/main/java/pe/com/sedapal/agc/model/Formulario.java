package pe.com.sedapal.agc.model;

public class Formulario {
	private Integer codigo;
	private String descripcion;
	private Integer estado;
	private String descripcionEstado;
	private Integer nivel;
	private Integer orden;
	private Integer codigoPadre;
	private String url;

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

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(Integer codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
