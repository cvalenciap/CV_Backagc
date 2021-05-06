package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;

public class HistoricoPersonalRequest {

	private String numeroDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Empresa empresa;
	private Estado estadoEmpresa;
	private Estado estadoLaboral;
	private Estado estadoCargo;
	private String fechaDesde;
	private String fechaHasta;

	public Estado getEstadoCargo() {
		return estadoCargo;
	}

	public void setEstadoCargo(Estado estadoCargo) {
		this.estadoCargo = estadoCargo;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Estado getEstadoEmpresa() {
		return estadoEmpresa;
	}

	public void setEstadoEmpresa(Estado estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}

	public Estado getEstadoLaboral() {
		return estadoLaboral;
	}

	public void setEstadoLaboral(Estado estadoLaboral) {
		this.estadoLaboral = estadoLaboral;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
