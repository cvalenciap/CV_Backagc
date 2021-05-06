package pe.com.sedapal.agc.model;

public class LogDigitalizado {
	private Integer idLog;
	private Integer suministro;
	private Actividad actividad;
	private String ordTrabOrdServCedu;
	private String tipologia;
	private String usuarioAccion;
	private String fechaHoraAccion;
	private String tipoAccion;
	private String tipoArchivo;
	private String ipAccion;
	
	public Integer getIdLog() {
		return idLog;
	}
	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
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
	public String getUsuarioAccion() {
		return usuarioAccion;
	}
	public void setUsuarioAccion(String usuarioAccion) {
		this.usuarioAccion = usuarioAccion;
	}
	public String getFechaHoraAccion() {
		return fechaHoraAccion;
	}
	public void setFechaHoraAccion(String fechaHoraAccion) {
		this.fechaHoraAccion = fechaHoraAccion;
	}
	public String getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	public String getIpAccion() {
		return ipAccion;
	}
	public void setIpAccion(String ipAccion) {
		this.ipAccion = ipAccion;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}		

}
