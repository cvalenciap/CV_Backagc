package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.ParametroSGC;
import pe.com.sedapal.agc.model.Trabajador;

public class MonitoreoDetalleRequestDC {

	private Empresa contratista;
	private String fechaProgramacion;
	private Trabajador trabajador;
	private Parametro incidencia;
	private Estado estado;
	private Parametro foto;
	private ParametroSGC tipoEntrega;
	private ParametroSGC cumplimiento;
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

	public ParametroSGC getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(ParametroSGC tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public ParametroSGC getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(ParametroSGC cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public Integer getSuministro() {
		return suministro;
	}

	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}

}
