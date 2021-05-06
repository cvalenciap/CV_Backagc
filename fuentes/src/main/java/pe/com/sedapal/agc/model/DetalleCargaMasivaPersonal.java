package pe.com.sedapal.agc.model;

public class DetalleCargaMasivaPersonal extends Auditoria {

	private Integer idCarga;
	private Integer idDetalle;
	private Integer codigoEmpleado;
	private String fechaCarga;
	private String estadoCarga;
	private String detalleError;
	private String resultado;

	public Integer getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(Integer idCarga) {
		this.idCarga = idCarga;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getEstadoCarga() {
		return estadoCarga;
	}

	public void setEstadoCarga(String estadoCarga) {
		this.estadoCarga = estadoCarga;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
