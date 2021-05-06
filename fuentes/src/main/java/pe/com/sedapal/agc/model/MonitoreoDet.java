package pe.com.sedapal.agc.model;

public class MonitoreoDet {

	private Integer idDetalle;
	private Integer idCabecera;
	private Integer suministro;
	private Trabajador trabajador;
	private Empresa contratista;
	private Actividad actividad;
	private Zona zona;
	private String fechaProgramacion;
	private Estado estadoAsignacion;
	private String estadoProgramacion;

	public Empresa getContratista() {
		return contratista;
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Estado getEstadoAsignacion() {
		return estadoAsignacion;
	}
	public void setEstadoAsignacion(Estado estadoAsignacion) {
		this.estadoAsignacion = estadoAsignacion;
	}
	public Integer getIdDetalle() {
		return idDetalle;
	}
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}
	public Integer getIdCabecera() {
		return idCabecera;
	}
	public void setIdCabecera(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	public String getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public String getEstadoProgramacion() {
		return estadoProgramacion;
	}
	public void setEstadoProgramacion(String estadoProgramacion) {
		this.estadoProgramacion = estadoProgramacion;
	}
		
}
