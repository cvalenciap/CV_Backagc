package pe.com.sedapal.agc.model;

public class LineaFallaCarga {
	private Integer linea;
	private String errores;
	private String campos;
	
	public LineaFallaCarga() {
		super();
	}
	
	public LineaFallaCarga(Integer linea, String errores, String campos) {
		super();
		this.linea = linea;
		this.campos = campos;
		this.errores = errores;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public String getErrores() {
		return errores;
	}

	public void setErrores(String errores) {
		this.errores = errores;
	}

	public String getCampos() {
		return campos;
	}

	public void setCampos(String campos) {
		this.campos = campos;
	}
	
}
