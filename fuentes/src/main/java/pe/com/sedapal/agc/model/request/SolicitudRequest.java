package pe.com.sedapal.agc.model.request;

public class SolicitudRequest {

	private Integer idSolicitud;
	private Integer codigoEmpleado;
	private String tipoSolicitud;
	private String fechaSolicitud;
	private String descripcionSolicitud;
	private String idMotivoSolicitud;
	private String idEstadoSolicitud;
	private String fechaAprobacion;
	private String fechaRechazo;
	private String observacionRechazo;
	private String idCargoActual;
	private Integer idOficinaActual;
	private Integer idItemActual;
	private Integer idOficinaDestino;
	private String dCargoDestino;
	private Integer idItemDestino;

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

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

	public String getIdMotivoSolicitud() {
		return idMotivoSolicitud;
	}

	public void setIdMotivoSolicitud(String idMotivoSolicitud) {
		this.idMotivoSolicitud = idMotivoSolicitud;
	}

	public String getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(String idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
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

	public String getIdCargoActual() {
		return idCargoActual;
	}

	public void setIdCargoActual(String idCargoActual) {
		this.idCargoActual = idCargoActual;
	}

	public Integer getIdOficinaActual() {
		return idOficinaActual;
	}

	public void setIdOficinaActual(Integer idOficinaActual) {
		this.idOficinaActual = idOficinaActual;
	}

	public Integer getIdItemActual() {
		return idItemActual;
	}

	public void setIdItemActual(Integer idItemActual) {
		this.idItemActual = idItemActual;
	}

	public Integer getIdOficinaDestino() {
		return idOficinaDestino;
	}

	public void setIdOficinaDestino(Integer idOficinaDestino) {
		this.idOficinaDestino = idOficinaDestino;
	}

	public String getdCargoDestino() {
		return dCargoDestino;
	}

	public void setdCargoDestino(String dCargoDestino) {
		this.dCargoDestino = dCargoDestino;
	}

	public Integer getIdItemDestino() {
		return idItemDestino;
	}

	public void setIdItemDestino(Integer idItemDestino) {
		this.idItemDestino = idItemDestino;
	}

}
