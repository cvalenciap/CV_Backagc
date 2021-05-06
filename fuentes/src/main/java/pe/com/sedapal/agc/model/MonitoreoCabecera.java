package pe.com.sedapal.agc.model;

public class MonitoreoCabecera {
	  /*
		idMonitoreo: number;
		trabajador: Trabajador;
		actividad: Actividad;
		cargaProgramada: number;
		cargaEjecutada: number;
		cargaPendiente: number;
		fechaProgramacion: string;
		fechaEjecucionInicio: string;
		fechaEjecucionFin: string;
		horaEjecucionInicio: string;
		horaEjecucionFin: string;
		avance:string;
	 */
	private Integer idMonitoreo;
	private Trabajador trabajador;
	private Actividad actividad;
	private Integer cargaProgramada;
	private Integer cargaEjecutada;
	private Integer cargaPendiente;
	private String fechaProgramacion;
	private String fechaEjecucionInicio;
	private String fechaEjecucionFin;
	private String horaEjecucionInicio;
	private String horaEjecucionFin;
	private String avance;
	private Integer semaforo;
	
	public Integer getIdMonitoreo() {
		return idMonitoreo;
	}
	public void setIdMonitoreo(Integer idMonitoreo) {
		this.idMonitoreo = idMonitoreo;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public Integer getCargaProgramada() {
		return cargaProgramada;
	}
	public void setCargaProgramada(Integer cargaProgramada) {
		this.cargaProgramada = cargaProgramada;
	}
	public Integer getCargaEjecutada() {
		return cargaEjecutada;
	}
	public void setCargaEjecutada(Integer cargaEjecutada) {
		this.cargaEjecutada = cargaEjecutada;
	}
	public Integer getCargaPendiente() {
		return cargaPendiente;
	}
	public void setCargaPendiente(Integer cargaPendiente) {
		this.cargaPendiente = cargaPendiente;
	}
	public String getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public String getFechaEjecucionInicio() {
		return fechaEjecucionInicio;
	}
	public void setFechaEjecucionInicio(String fechaEjecucionInicio) {
		this.fechaEjecucionInicio = fechaEjecucionInicio;
	}
	public String getFechaEjecucionFin() {
		return fechaEjecucionFin;
	}
	public void setFechaEjecucionFin(String fechaEjecucionFin) {
		this.fechaEjecucionFin = fechaEjecucionFin;
	}
	public String getHoraEjecucionInicio() {
		return horaEjecucionInicio;
	}
	public void setHoraEjecucionInicio(String horaEjecucionInicio) {
		this.horaEjecucionInicio = horaEjecucionInicio;
	}
	public String getHoraEjecucionFin() {
		return horaEjecucionFin;
	}
	public void setHoraEjecucionFin(String horaEjecucionFin) {
		this.horaEjecucionFin = horaEjecucionFin;
	}
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = avance;
	}
	public Integer getSemaforo() {
		return semaforo;
	}
	public void setSemaforo(Integer semaforo) {
		this.semaforo = semaforo;
	}
		
}
