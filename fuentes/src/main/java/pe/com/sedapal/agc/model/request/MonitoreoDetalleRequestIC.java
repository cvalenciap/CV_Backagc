package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Zona;

public class MonitoreoDetalleRequestIC {
	private Empresa contratista;
	private String fechaProgramacion;
	private Trabajador trabajador;
	private Parametro imposibilidad;
	private Parametro estadoServicio;
	private Parametro estadoMedidor;
	private Estado estado;
	private Parametro foto;
	private Parametro tipoInspeccion;
	private Zona zona;
	private Parametro resultadoInspeccion;
	private Integer suministro;
	private Parametro cumplimiento;
	private Parametro incidencia;
	
	public Empresa getContratista() {
		return contratista;
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public String getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	public Parametro getImposibilidad() {
		return imposibilidad;
	}
	public void setImposibilidad(Parametro imposibilidad) {
		this.imposibilidad = imposibilidad;
	}
	public Parametro getEstadoServicio() {
		return estadoServicio;
	}
	public void setEstadoServicio(Parametro estadoServicio) {
		this.estadoServicio = estadoServicio;
	}
	public Parametro getEstadoMedidor() {
		return estadoMedidor;
	}
	public void setEstadoMedidor(Parametro estadoMedidor) {
		this.estadoMedidor = estadoMedidor;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Parametro getFoto() {
		return foto;
	}
	public void setFoto(Parametro foto) {
		this.foto = foto;
	}
	public Parametro getTipoInspeccion() {
		return tipoInspeccion;
	}
	public void setTipoInspeccion(Parametro tipoInspeccion) {
		this.tipoInspeccion = tipoInspeccion;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Parametro getResultadoInspeccion() {
		return resultadoInspeccion;
	}
	public void setResultadoInspeccion(Parametro resultadoInspeccion) {
		this.resultadoInspeccion = resultadoInspeccion;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public Parametro getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(Parametro cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public Parametro getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(Parametro incidencia) {
		this.incidencia = incidencia;
	}
	
}
