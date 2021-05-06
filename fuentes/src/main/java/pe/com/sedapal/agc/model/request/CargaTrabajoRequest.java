package pe.com.sedapal.agc.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class CargaTrabajoRequest {
	@NotNull(message = "1001-uidCargaTrabajo")
	@Size(max = 12, message = "1002-uidCargaTrabajo,12")
	private String 	uidCargaTrabajo;
	private Integer uidEmpresa;
	private Integer uidOficina;
	private Integer uidGrupo;
	@NotNull(message = "1001-uidActividad")
	private String uidActividad;
	private String 	uidEstado;
	private String 	motivoAnula;
	private Date 	fechaInicio;
	private Date 	fechaFin;
	private boolean cargaFlag;			// Flag (valor: 1 - Carga en línea | 0 - Carga normal)
	@NotNull(message = "1001-actividad")
	private Object	actividad;			// Cuerpo de la actividad enviada (campos que pertenecen a una actividad)
	private String 	usuario;			// Usuario que realiza la operación
	private String  comentario;
	private Integer uidContratista;
	private String accion;
			
	public CargaTrabajoRequest() {}
	public CargaTrabajoRequest(String uidCargaTrabajo,Integer uidEmpresa, Integer uidOficina, Integer uidGrupo, String uidActividad,
			String uidEstado, String motivoAnula, Date fechaInicio, Date fechaFin) {
		super();
		this.uidCargaTrabajo = uidCargaTrabajo;
		this.uidEmpresa = uidEmpresa;
		this.uidOficina = uidOficina;
		this.uidGrupo = uidGrupo;
		this.uidActividad = uidActividad;
		this.uidEstado = uidEstado;
		this.motivoAnula = motivoAnula;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Integer getUidEmpresa() {
		return uidEmpresa;
	}

	public void setUidEmpresa(Integer uidEmpresa) {
		this.uidEmpresa = uidEmpresa;
	}

	public Integer getUidOficina() {
		return uidOficina;
	}

	public void setUidOficina(Integer uidOficina) {
		this.uidOficina = uidOficina;
	}

	public Integer getUidGrupo() {
		return uidGrupo;
	}

	public void setUidGrupo(Integer uidGrupo) {
		this.uidGrupo = uidGrupo;
	}

	public String getUidActividad() {
		return uidActividad;
	}

	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}

	public String getUidEstado() {
		return uidEstado;
	}

	public void setUidEstado(String uidEstado) {
		this.uidEstado = uidEstado;
	}

	public String getMotivoAnula() {
		return motivoAnula;
	}

	public void setMotivoAnula(String motivoAnula) {
		this.motivoAnula = motivoAnula;
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

	public String getUidCargaTrabajo() {
		return uidCargaTrabajo;
	}

	public void setUidCargaTrabajo(String uidCargaTrabajo) {
		this.uidCargaTrabajo = uidCargaTrabajo;
	}

	public boolean isCargaFlag() {
		return cargaFlag;
	}

	public void setCargaFlag(boolean cargaFlag) {
		this.cargaFlag = cargaFlag;
	}

	public Object getActividad() {
		return actividad;
	}

	public void setActividad(Object actividad) {
		this.actividad = actividad;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String toString() {
		return "CargaTrabajoRequest(CargaTrabajo: " + this.getUidCargaTrabajo() + ")";
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Integer getUidContratista() {
		return uidContratista;
	}
	public void setUidContratista(Integer uidContratista) {
		this.uidContratista = uidContratista;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
}
