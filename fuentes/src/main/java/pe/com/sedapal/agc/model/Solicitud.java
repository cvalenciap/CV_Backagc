package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Solicitud extends Auditoria {

	private Integer idSolicitud;
	private Integer codigoEmpleado;
	private String tipoSolicitud;
	private String fechaSolicitud;
	private String descripcionSolicitud;
	private Parametro motivoSolicitud;
	private Estado estadoSolicitud;
	private String fechaAprobacion;
	private String fechaRechazo;
	private String observacionRechazo;
	private Cargo cargoActual;
	private Oficina oficinaActual;
	private Item itemActual;
	private Oficina oficinaDestino;
	private Cargo cargoDestino;
	private Item itemDestino;
	private PersonaContratista personal;

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getDescripcionSolicitud() {
		return descripcionSolicitud;
	}

	public void setDescripcionSolicitud(String descripcionSolicitud) {
		this.descripcionSolicitud = descripcionSolicitud;
	}

	public Parametro getMotivoSolicitud() {
		return motivoSolicitud;
	}

	public void setMotivoSolicitud(Parametro motivoSolicitud) {
		this.motivoSolicitud = motivoSolicitud;
	}
	
	public Estado getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(Estado estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}	

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public String getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public String getFechaRechazo() {
		return fechaRechazo;
	}

	public void setFechaRechazo(String fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}

	public String getObservacionRechazo() {
		return observacionRechazo;
	}

	public void setObservacionRechazo(String observacionRechazo) {
		this.observacionRechazo = observacionRechazo;
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

	public Oficina getOficinaDestino() {
		return oficinaDestino;
	}

	public void setOficinaDestino(Oficina oficinaDestino) {
		this.oficinaDestino = oficinaDestino;
	}

	public Cargo getCargoDestino() {
		return cargoDestino;
	}

	public void setCargoDestino(Cargo cargoDestino) {
		this.cargoDestino = cargoDestino;
	}

	public Item getItemDestino() {
		return itemDestino;
	}

	public void setItemDestino(Item itemDestino) {
		this.itemDestino = itemDestino;
	}
	
	public PersonaContratista getPersonal() {
		return personal;
	}

	public void setPersonal(PersonaContratista personal) {
		this.personal = personal;
	}

}
