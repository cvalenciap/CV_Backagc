package pe.com.sedapal.agc.model.request;

public class VisorMonitoreoRequest {
	private Integer suministro;
	private String numeroCarga;
	private Integer idCargaDetalle;
	private String ordTrabOrdServCedu;
	private String tipologia;
	private String tipoArchivo;
	private String numeroOT;
	private String actividad; 
	private String accion;
	private String usuario; 
	private String ip;
	private String imagen1;
	private String imagen2;
	private String imagen3;
	
	public String getOrdTrabOrdServCedu() {
		return ordTrabOrdServCedu;
	}
	public void setOrdTrabOrdServCedu(String ordTrabOrdServCedu) {
		this.ordTrabOrdServCedu = ordTrabOrdServCedu;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
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
	public Integer getIdCargaDetalle() {
		return idCargaDetalle;
	}
	public void setIdCargaDetalle(Integer idCargaDetalle) {
		this.idCargaDetalle = idCargaDetalle;
	}
	public String getNumeroOT() {
		return numeroOT;
	}
	public void setNumeroOT(String numeroOT) {
		this.numeroOT = numeroOT;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
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
