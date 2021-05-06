package pe.com.sedapal.agc.model.request;

import java.util.Date;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Trabajador;

public class MonitoreoCabeceraRequest {
	
	private Empresa contratista;
	private Parametro estado;
//	private String actividad;
	private Date fechaProgramacionInicio;
	private Date fechaProgramacionFin;
	private Integer suministro;
	private Integer medidor;
	private String numMedidor;
	private Trabajador operario;
	private Oficina oficina;
	private Parametro periodo;
	private Parametro ciclo;
//	private Parametro actOperativa;
	private Parametro imposibilidad;
	private Parametro tipoActividad;
	private Parametro foto;
	private String ordenServicio;
	private String ordenTrabajo;
	private String cedulaNotificacion;
	private Integer numeroLote;
	private Parametro semaforo;
	private Parametro tipoNotificacion;
	private Integer numLote;
	
	public Parametro getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(Parametro tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public Empresa getContratista() {
		return contratista;
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public Parametro getEstado() {
		return estado;
	}
	public void setEstado(Parametro estado) {
		this.estado = estado;
	}
//	public String getActividad() {
//		return actividad;
//	}
//	public void setActividad(String actividad) {
//		this.actividad = actividad;
//	}
	public Date getFechaProgramacionInicio() {
		return fechaProgramacionInicio;
	}
	public void setFechaProgramacionInicio(Date fechaProgramacionInicio) {
		this.fechaProgramacionInicio = fechaProgramacionInicio;
	}
	public Date getFechaProgramacionFin() {
		return fechaProgramacionFin;
	}
	public void setFechaProgramacionFin(Date fechaProgramacionFin) {
		this.fechaProgramacionFin = fechaProgramacionFin;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public Integer getMedidor() {
		return medidor;
	}
	public void setMedidor(Integer medidor) {
		this.medidor = medidor;
	}
	public String getNumMedidor() {
		return numMedidor;
	}
	public void setNumMedidor(String numMedidor) {
		this.numMedidor = numMedidor;
	}
	public Trabajador getOperario() {
		return operario;
	}
	public void setOperario(Trabajador operario) {
		this.operario = operario;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Parametro getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Parametro periodo) {
		this.periodo = periodo;
	}
	public Parametro getCiclo() {
		return ciclo;
	}
	public void setCiclo(Parametro ciclo) {
		this.ciclo = ciclo;
	}
//	public Parametro getActOperativa() {
//		return actOperativa;
//	}
//	public void setActOperativa(Parametro actOperativa) {
//		this.actOperativa = actOperativa;
//	}
	public Parametro getImposibilidad() {
		return imposibilidad;
	}
	public void setImposibilidad(Parametro imposibilidad) {
		this.imposibilidad = imposibilidad;
	}
	public Parametro getFoto() {
		return foto;
	}
	public void setFoto(Parametro foto) {
		this.foto = foto;
	}
	public String getOrdenServicio() {
		return ordenServicio;
	}
	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}
	public String getOrdenTrabajo() {
		return ordenTrabajo;
	}
	public void setOrdenTrabajo(String ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	public String getCedulaNotificacion() {
		return cedulaNotificacion;
	}
	public void setCedulaNotificacion(String cedulaNotificacion) {
		this.cedulaNotificacion = cedulaNotificacion;
	}
	public Integer getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}
	public Parametro getSemaforo() {
		return semaforo;
	}
	public void setSemaforo(Parametro semaforo) {
		this.semaforo = semaforo;
	}
	public Parametro getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(Parametro tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public Integer getNumLote() {
		return numLote;
	}
	public void setNumLote(Integer numLote) {
		this.numLote = numLote;
	}

}
