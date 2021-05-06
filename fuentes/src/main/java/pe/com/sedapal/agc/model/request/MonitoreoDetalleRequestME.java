package pe.com.sedapal.agc.model.request;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.ParametroSGC;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Zona;

public class MonitoreoDetalleRequestME {

	private Empresa contratista;
	private String fechaProgramacion;
	private Trabajador trabajador;
	private Integer ordenServicio;
	private Estado estado;
	private Parametro foto;
	private ParametroSGC tipoActividad;
	private Zona zona;
	private String medidorInstalado;
	private String medidorRetirado;
	private String fechaEjecucion;
	private ParametroSGC tipoOrdenServicio;
	private Integer suministro;
	private ParametroSGC codObservacion;
	private ParametroSGC cumplimiento;
	private ParametroSGC tipoCarga;
	private Parametro tipoInstalacion;

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

	public Integer getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(Integer ordenServicio) {
		this.ordenServicio = ordenServicio;
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

	public ParametroSGC getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(ParametroSGC tipoActividad) {
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

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public ParametroSGC getTipoOrdenServicio() {
		return tipoOrdenServicio;
	}

	public void setTipoOrdenServicio(ParametroSGC tipoOrdenServicio) {
		this.tipoOrdenServicio = tipoOrdenServicio;
	}

	public Integer getSuministro() {
		return suministro;
	}

	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}

	public ParametroSGC getCodObservacion() {
		return codObservacion;
	}

	public void setCodObservacion(ParametroSGC codObservacion) {
		this.codObservacion = codObservacion;
	}

	public ParametroSGC getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(ParametroSGC cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public ParametroSGC getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(ParametroSGC tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public Parametro getTipoInstalacion() {
		return tipoInstalacion;
	}

	public void setTipoInstalacion(Parametro tipoInstalacion) {
		this.tipoInstalacion = tipoInstalacion;
	}

}
