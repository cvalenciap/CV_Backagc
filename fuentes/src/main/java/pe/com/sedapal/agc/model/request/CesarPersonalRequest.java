package pe.com.sedapal.agc.model.request;

public class CesarPersonalRequest {

	private Integer codigoEmpleado;
	private Integer codMotCese;
	private String observacion;
	private String fechaCese;
	private String usuarioMod;

	public String getUsuarioMod() {
		return usuarioMod;
	}

	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public Integer getCodMotCese() {
		return codMotCese;
	}

	public void setCodMotCese(Integer codMotCese) {
		this.codMotCese = codMotCese;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getFechaCese() {
		return fechaCese;
	}

	public void setFechaCese(String fechaCese) {
		this.fechaCese = fechaCese;
	}

}
