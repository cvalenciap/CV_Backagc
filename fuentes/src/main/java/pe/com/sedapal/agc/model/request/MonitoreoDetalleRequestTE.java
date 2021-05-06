package pe.com.sedapal.agc.model.request;

import java.sql.Date;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Zona;

public class MonitoreoDetalleRequestTE {
	private Empresa contratista;
	private String fechaProgramacion;
	private Trabajador trabajador;
	private Parametro incidencia;
	private Parametro subIncidencia;
	private Estado estado;
	private Parametro foto;
	private Parametro ciclo;
	private Parametro cumplimiento;
	private Parametro imposibiliad;
	private Parametro estadoServicio;
	private Parametro tipoInspeccion;
	private Parametro resultadoInspeccion;
	private Parametro tipoEntrega;
	private Date fechaEjecucion;
	private String ordenServicio;
	private Parametro tipoActividad;
	private Zona zona;
	private String medidorInstalado;
	private String medidorRetirado;
	private Parametro tipoOrdenServicio;
	private Parametro codObservacion;
	private Integer suministro;
	
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
	public Parametro getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(Parametro incidencia) {
		this.incidencia = incidencia;
	}
	public Parametro getSubIncidencia() {
		return subIncidencia;
	}
	public void setSubIncidencia(Parametro subIncidencia) {
		this.subIncidencia = subIncidencia;
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
	public Parametro getCiclo() {
		return ciclo;
	}
	public void setCiclo(Parametro ciclo) {
		this.ciclo = ciclo;
	}
	public Parametro getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(Parametro cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public Parametro getImposibiliad() {
		return imposibiliad;
	}
	public void setImposibiliad(Parametro imposibiliad) {
		this.imposibiliad = imposibiliad;
	}
	public Parametro getEstadoServicio() {
		return estadoServicio;
	}
	public void setEstadoServicio(Parametro estadoServicio) {
		this.estadoServicio = estadoServicio;
	}
	public Parametro getTipoInspeccion() {
		return tipoInspeccion;
	}
	public void setTipoInspeccion(Parametro tipoInspeccion) {
		this.tipoInspeccion = tipoInspeccion;
	}
	public Parametro getResultadoInspeccion() {
		return resultadoInspeccion;
	}
	public void setResultadoInspeccion(Parametro resultadoInspeccion) {
		this.resultadoInspeccion = resultadoInspeccion;
	}
	public Parametro getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(Parametro tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	public String getOrdenServicio() {
		return ordenServicio;
	}
	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}
	public Parametro getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(Parametro tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public String getMedidorInstalado() {
		return medidorInstalado;
	}
	public void setMedidorInstalado(String medidorInstalado) {
		this.medidorInstalado = medidorInstalado;
	}
	public String getMedidorRetirado() {
		return medidorRetirado;
	}
	public void setMedidorRetirado(String medidorRetirado) {
		this.medidorRetirado = medidorRetirado;
	}
	public Parametro getTipoOrdenServicio() {
		return tipoOrdenServicio;
	}
	public void setTipoOrdenServicio(Parametro tipoOrdenServicio) {
		this.tipoOrdenServicio = tipoOrdenServicio;
	}
	public Parametro getCodObservacion() {
		return codObservacion;
	}
	public void setCodObservacion(Parametro codObservacion) {
		this.codObservacion = codObservacion;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}

}
