package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movimiento extends Auditoria {

	private Integer nro;
	private Integer idMovimiento;
	private Integer codigoEmpleado;
	private String numeroDocumento;
	private String nombreEmpleado;
	private Empresa empresa;
	private Solicitud solicitud;
	private Cargo cargoActual;
	private Oficina oficinaActual;
	private Item itemActual;
	private Cargo cargoDestino;
	private Oficina oficinaDestino;
	private Item itemDestino;
	private String fechaIngreso;
	private String fechaAltaCargo;
	private String fechaBajaCargo;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Integer getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Integer idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Cargo getCargoActual() {
		return cargoActual;
	}

	public void setCargoActual(Cargo cargoActual) {
		this.cargoActual = cargoActual;
	}

	public Oficina getOficinaActual() {
		return oficinaActual;
	}

	public void setOficinaActual(Oficina oficinaActual) {
		this.oficinaActual = oficinaActual;
	}

	public Item getItemActual() {
		return itemActual;
	}

	public void setItemActual(Item itemActual) {
		this.itemActual = itemActual;
	}

	public Cargo getCargoDestino() {
		return cargoDestino;
	}

	public void setCargoDestino(Cargo cargoDestino) {
		this.cargoDestino = cargoDestino;
	}

	public Oficina getOficinaDestino() {
		return oficinaDestino;
	}

	public void setOficinaDestino(Oficina oficinaDestino) {
		this.oficinaDestino = oficinaDestino;
	}

	public Item getItemDestino() {
		return itemDestino;
	}

	public void setItemDestino(Item itemDestino) {
		this.itemDestino = itemDestino;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaAltaCargo() {
		return fechaAltaCargo;
	}

	public void setFechaAltaCargo(String fechaAltaCargo) {
		this.fechaAltaCargo = fechaAltaCargo;
	}

	public String getFechaBajaCargo() {
		return fechaBajaCargo;
	}

	public void setFechaBajaCargo(String fechaBajaCargo) {
		this.fechaBajaCargo = fechaBajaCargo;
	}

}
