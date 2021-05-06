package pe.com.sedapal.agc.model;

public class MonitoreoCab {
	private Integer idCabecera;
	private Actividad actividad;
	private Oficina oficina;
	private Estado estadoAsignacion;
	private Empresa contratista;
	private String fechaAsignacion;
	private String numeroCarga;
	private Integer cantAsignada;
	private Integer cantProgramada;
	
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Estado getEstadoAsignacion() {
		return estadoAsignacion;
	}
	public void setEstadoAsignacion(Estado estadoAsignacion) {
		this.estadoAsignacion = estadoAsignacion;
	}
	public Empresa getContratista() {
		return contratista;
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public String getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public String getNumeroCarga() {
		return numeroCarga;
	}
	public void setNumeroCarga(String numeroCarga) {
		this.numeroCarga = numeroCarga;
	}
	public Integer getIdCabecera() {
		return idCabecera;
	}
	public void setIdCabecera(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}
	public Integer getCantAsignada() {
		return cantAsignada;
	}
	public void setCantAsignada(Integer cantAsignada) {
		this.cantAsignada = cantAsignada;
	}
	public Integer getCantProgramada() {
		return cantProgramada;
	}
	public void setCantProgramada(Integer cantProgramada) {
		this.cantProgramada = cantProgramada;
	}
	
}
