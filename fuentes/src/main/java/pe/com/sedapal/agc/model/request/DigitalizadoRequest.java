package pe.com.sedapal.agc.model.request;

import java.sql.Date;

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Oficina;

public class DigitalizadoRequest {
	private Integer suministro;
	private String numeroCarga;
	private Actividad actividad;
	private String ordenServicio;
	private String ordenTrabajo;
	private String numeroCedula;
	private String numeroReclamo;
	private Oficina oficina;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer digitalizado;
	
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public String getNumeroCarga() {
		return numeroCarga;
	}
	public void setNumeroCarga(String numeroCarga) {
		this.numeroCarga = numeroCarga;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
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
	public String getNumeroCedula() {
		return numeroCedula;
	}
	public void setNumeroCedula(String numeroCedula) {
		this.numeroCedula = numeroCedula;
	}
	public String getNumeroReclamo() {
		return numeroReclamo;
	}
	public void setNumeroReclamo(String numeroReclamo) {
		this.numeroReclamo = numeroReclamo;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Integer getDigitalizado() {
		return digitalizado;
	}
	public void setDigitalizado(Integer digitalizado) {
		this.digitalizado = digitalizado;
	}
		
}
