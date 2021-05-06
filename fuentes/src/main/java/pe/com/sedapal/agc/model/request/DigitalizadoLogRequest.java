package pe.com.sedapal.agc.model.request;

import java.sql.Date;

public class DigitalizadoLogRequest {
	private Integer suministro;
	private String usuario;
	private String tipoAccion;
	private String tipoArchivo;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
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
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
		
}
