package pe.com.sedapal.agc.model;

import pe.com.sedapal.agc.model.Parametro;

public class MonitoreoDetalleTE {

	private String direccion;
	private String suministro;
	private String lectura;
	private String medidor;
	private String incLectura1;
	private String incLectura2;
	private String incLectura3;
	private String medidorObservado;
	private String fechaEjecucion;
	private String horaEjecucion;
	private Estado estado;
	private Zona zona;
	private Parametro cumplimiento;
	private Parametro foto;
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
	public String getLectura() {
		return lectura;
	}
	public void setLectura(String lectura) {
		this.lectura = lectura;
	}
	public String getMedidor() {
		return medidor;
	}
	public void setMedidor(String medidor) {
		this.medidor = medidor;
	}
	public String getIncLectura1() {
		return incLectura1;
	}
	public void setIncLectura1(String incLectura1) {
		this.incLectura1 = incLectura1;
	}
	public String getIncLectura2() {
		return incLectura2;
	}
	public void setIncLectura2(String incLectura2) {
		this.incLectura2 = incLectura2;
	}
	public String getIncLectura3() {
		return incLectura3;
	}
	public void setIncLectura3(String incLectura3) {
		this.incLectura3 = incLectura3;
	}
	public String getMedidorObservado() {
		return medidorObservado;
	}
	public void setMedidorObservado(String medidorObservado) {
		this.medidorObservado = medidorObservado;
	}
	public String getFechaEjecucion() {
		return fechaEjecucion;
	}
	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	public String getHoraEjecucion() {
		return horaEjecucion;
	}
	public void setHoraEjecucion(String horaEjecucion) {
		this.horaEjecucion = horaEjecucion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Parametro getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(Parametro cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public Parametro getFoto() {
		return foto;
	}
	public void setFoto(Parametro foto) {
		this.foto = foto;
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
