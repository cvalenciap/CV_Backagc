package pe.com.sedapal.agc.model.request;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Zona;

public class MonitoreoRequest {
//	private Integer numCargaTrabajo;
	private Integer idCabecera;
	private Integer idDetalle;
	private Trabajador trabajador;
	private Integer suministro;
	private Empresa contratista;
//	private String desEmpr;
	private Estado estado;
//	private String desEstado;
	private Actividad actividad;
//	private String desActi;
	private Date fechaAsignacionFin;
	private String fechaAsignacionDetalle;
	private Date fechaAsignacionDetalleDate;
	private Date fechaAsignacionInicio;
//	private Date fechaEjecucion;
//	private String sFechaEjec;
//	private Date fechaAsig;
	
	private Zona zona;
	private Oficina oficina;
	private Trabajador trabajadorAnt;
//	private String desTipoZona;
//	private String desIdCarga;
//	private Integer numIdOfic;
//	private String desOfic;
//	private Integer numCantEjec;
//	private Integer numCantPend;
//	private String desUser;
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
//	public Integer getNumCargaTrabajo() {
//		return numCargaTrabajo;
//	}
//	public void setNumCargaTrabajo(Integer numCargaTrabajo) {
//		this.numCargaTrabajo = numCargaTrabajo;
//	}

//	public String getDesEmpr() {
//		return desEmpr;
//	}
//	public void setDesEmpr(String desEmpr) {
//		this.desEmpr = desEmpr;
//	}

//	public String getDesEstado() {
//		return desEstado;
//	}
//	public void setDesEstado(String desEstado) {
//		this.desEstado = desEstado;
//	}

//	public String getDesActi() {
//		return desActi;
//	}
//	public void setDesActi(String desActi) {
//		this.desActi = desActi;
//	}

//	public String getsFechaEjec() {
//		return sFechaEjec;
//	}
//	public void setsFechaEjec(String sFechaEjec) throws ParseException {
//		this.sFechaEjec = sFechaEjec;
//		this.fechaEjec = (Date) formato.parse(sFechaEjec);
//	}
//	public Date getFechaAsig() {
//		return fechaAsig;
//	}
//	public void setFechaAsig(Date fechaAsig) {
//		this.fechaAsig = fechaAsig;
//	}
	
	public Empresa getContratista() {
		return contratista;
	}
	public String getFechaAsignacionDetalle() {
		return fechaAsignacionDetalle;
	}
	public void setFechaAsignacionDetalle(String fechaAsignacionDetalle) throws ParseException {
		this.fechaAsignacionDetalle = fechaAsignacionDetalle;
		this.fechaAsignacionDetalleDate = (Date) formato.parse(fechaAsignacionDetalle);
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public Date getFechaAsignacionFin() {
		return fechaAsignacionFin;
	}
	public void setFechaAsignacionFin(Date fechaAsignacionFin) {
		this.fechaAsignacionFin = fechaAsignacionFin;
	}
	public Date getFechaAsignacionInicio() {
		return fechaAsignacionInicio;
	}
//	public Date getFechaEjecucion() {
//		return fechaEjecucion;
//	}
//	public void setFechaEjecucion(Date fechaEjecucion) {
//		this.fechaEjecucion = fechaEjecucion;
//	}
	public void setFechaAsignacionInicio(Date fechaAsignacionInicio) {
		this.fechaAsignacionInicio = fechaAsignacionInicio;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public SimpleDateFormat getFormato() {
		return formato;
	}
	public void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Integer getIdCabecera() {
		return idCabecera;
	}
	public void setIdCab(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}
	public Integer getIdDetalle() {
		return idDetalle;
	}
	public void setIdDet(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	public Integer getSuministro() {
		return suministro;
	}
	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}
	public Date getFechaAsignacionDetalleDate() {
		return fechaAsignacionDetalleDate;
	}
	public void setFechaAsignacionDetalleDate(Date fechaAsignacionDetalleDate) {
		this.fechaAsignacionDetalleDate = fechaAsignacionDetalleDate;
	}

//	public String getDesTipoZona() {
//		return desTipoZona;
//	}
//	public void setDesTipoZona(String desTipoZona) {
//		this.desTipoZona = desTipoZona;
//	}
//	public String getDesIdCarga() {
//		return desIdCarga;
//	}
//	public void setDesIdCarga(String desIdCarga) {
//		this.desIdCarga = desIdCarga;
//	}
//	public Integer getNumIdOfic() {
//		return numIdOfic;
//	}
//	public void setNumIdOfic(Integer numIdOfic) {
//		this.numIdOfic = numIdOfic;
//	}
//	public String getDesOfic() {
//		return desOfic;
//	}
//	public void setDesOfic(String desOfic) {
//		this.desOfic = desOfic;
//	}
//	public Integer getNumCantEjec() {
//		return numCantEjec;
//	}
//	public void setNumCantEjec(Integer numCantEjec) {
//		this.numCantEjec = numCantEjec;
//	}
//	public Integer getNumCantPend() {
//		return numCantPend;
//	}
//	public void setNumCantPend(Integer numCantPend) {
//		this.numCantPend = numCantPend;
//	}
//	public String getDesUser() {
//		return desUser;
//	}
//	public void setDesUser(String desUser) {
//		this.desUser = desUser;
//	}
	public void setIdCabecera(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}
	public Trabajador getTrabajadorAnt() {
		return trabajadorAnt;
	}
	public void setTrabajadorAnt(Trabajador trabajadorAnt) {
		this.trabajadorAnt = trabajadorAnt;
	}
	
	
}
