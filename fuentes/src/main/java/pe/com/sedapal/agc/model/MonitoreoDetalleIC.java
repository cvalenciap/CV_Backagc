package pe.com.sedapal.agc.model;

import pe.com.sedapal.agc.model.Parametro;

public class MonitoreoDetalleIC {

	private String direccion;
	private String suministro;
	private String medidor;
	private String ordenServicio;
	private String tipologiaOrdServ;
	private String fechaVisita;
	private String horaInicio;
	private String horaFin;
	private String servicio;
	private String lectura;
	private Parametro estadoMedidor;
	private Parametro incidenciaMedidor;
	private Parametro imposibilidad;
	private String inspeccionRealizada;
	private String detalle;
	private Parametro estado;
	private Zona zona;
	private Parametro completa;
	private Parametro foto;
	private Parametro cumplimiento;
	private String imagen1;
	private String imagen2;
	private String imagen3;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getSuministro() {
		return suministro;
	}
	public void setSuministro(String suministro) {
		this.suministro = suministro;
	}
	public String getMedidor() {
		return medidor;
	}
	public void setMedidor(String medidor) {
		this.medidor = medidor;
	}
	public String getOrdenServicio() {
		return ordenServicio;
	}
	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}
	public String getTipologiaOrdServ() {
		return tipologiaOrdServ;
	}
	public void setTipologiaOrdServ(String tipologiaOrdServ) {
		this.tipologiaOrdServ = tipologiaOrdServ;
	}
	public String getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(String fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getLectura() {
		return lectura;
	}
	public void setLectura(String lectura) {
		this.lectura = lectura;
	}
	public Parametro getEstadoMedidor() {
		return estadoMedidor;
	}
	public void setEstadoMedidor(Parametro estadoMedidor) {
		this.estadoMedidor = estadoMedidor;
	}
	public Parametro getIncidenciaMedidor() {
		return incidenciaMedidor;
	}
	public void setIncidenciaMedidor(Parametro incidenciaMedidor) {
		this.incidenciaMedidor = incidenciaMedidor;
	}
	public Parametro getImposibilidad() {
		return imposibilidad;
	}
	public void setImposibilidad(Parametro imposibilidad) {
		this.imposibilidad = imposibilidad;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Parametro getEstado() {
		return estado;
	}
	public void setEstado(Parametro estado) {
		this.estado = estado;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Parametro getCompleta() {
		return completa;
	}
	public void setCompleta(Parametro completa) {
		this.completa = completa;
	}
	public Parametro getFoto() {
		return foto;
	}
	public void setFoto(Parametro foto) {
		this.foto = foto;
	}
	public String getInspeccionRealizada() {
		return inspeccionRealizada;
	}
	public void setInspeccionRealizada(String inspeccionRealizada) {
		this.inspeccionRealizada = inspeccionRealizada;
	}
	public Parametro getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(Parametro cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public String getImagen1() {
		return imagen1;
	}
	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}
	public String getImagen2() {
		return imagen2;
	}
	public void setImagen2(String imagen2) {
		this.imagen2 = imagen2;
	}
	public String getImagen3() {
		return imagen3;
	}
	public void setImagen3(String imagen3) {
		this.imagen3 = imagen3;
	}
	
}