package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Trabajador;

public class MonitoreoDetalleRequestDA {

	private Trabajador trabajador;
	private String fechaProgramacion;
	private Empresa contratista;
	private Parametro incidencia;
	private Estado estado;
	private Parametro foto;
	private Integer suministro;
	private Parametro cumplimiento;

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public String getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	public Empresa getContratista() {
		return contratista;
	}

	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}

	public Parametro getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Parametro incidencia) {
		this.incidencia = incidencia;
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

}
