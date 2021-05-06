package pe.com.sedapal.agc.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.Instant;

public class CargaTrabajo {
	private String uidCargaTrabajo;
	private Integer uidContratista;
	private String descContratista;
	private Integer uidOficina;
	private String descOficina;
	private Integer uidGrupo;
	private String uidActividad;
	private String descActividad;
	private String uidEstado;
	private String estado;
	private String fechaCarga;
	private String fechaSedapal;
	private String fechaContratista;
	private Integer cantidadCarga;
	private Integer cantidadEjecutada;
	private String uidUsuarioC;
	private String usuarioNombreC;
	private String uidUsuarioE;
	private String usuarioNombreE;
	private String motivoAnula;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaInicio;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaFin;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private String comentario;
	private Integer idPers;
	private Integer idPerfil;
	private Integer vnTipoEstado;
	private String vdescripcion;

	public CargaTrabajo() {
		super();
	}

	public CargaTrabajo(String uidCargaTrabajo, Integer uidContratista, String descContratista, Integer uidOficina,
			String descOficina, Integer uidGrupo, String uidActividad, String descActividad, String uidEstado, String estado,
			String fechaCarga, String fechaSedapal, String fechaContratista, Integer cantidadCarga, Integer cantidadEjecutada,
			String uidUsuarioC, String usuarioNombreC, String uidUsuarioE, String usuarioNombreE,
			String motivoAnula, LocalDate fechaInicio, LocalDate fechaFin, Integer idPers, Integer idPerfil, Integer vnTipoEstado,
			String vdescripcion) {
		super();
		this.uidCargaTrabajo = uidCargaTrabajo;
		this.uidContratista = uidContratista;
		this.descContratista = descContratista;
		this.uidOficina = uidOficina;
		this.descOficina = descOficina;
		this.uidGrupo = uidGrupo;
		this.uidActividad = uidActividad;
		this.descActividad = descActividad;
		this.uidEstado = uidEstado;
		this.estado = estado;
		this.fechaCarga = fechaCarga;
		this.fechaSedapal = fechaSedapal;
		this.fechaContratista = fechaContratista;
		this.cantidadCarga = cantidadCarga;
		this.cantidadEjecutada = cantidadEjecutada;
		this.uidUsuarioC = uidUsuarioC;
		this.usuarioNombreC = usuarioNombreC;
		this.uidUsuarioE = uidUsuarioE;
		this.usuarioNombreE = usuarioNombreE;
		this.motivoAnula = motivoAnula;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.idPers = idPers;
		this.idPerfil = idPerfil;
		this.vnTipoEstado = vnTipoEstado;
		this.vdescripcion = vdescripcion;
	}
	
	public String getVdescripcion() {
		return vdescripcion;
	}
	
	public void setVdescripcion(String vdescripcion) {
		this.vdescripcion = vdescripcion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
	
	public Integer getVnTipoEstado() {
		return vnTipoEstado;
	}
	
	public void setVnTipoEstado(Integer vnTipoEstado) {
		this.vnTipoEstado = vnTipoEstado;
	}
	
	public Integer getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Integer getIdPers() {
		return idPers;
	}
	
	public void setIdPers(Integer idPers) {
		this.idPers = idPers;
	}
	
	public String getUidCargaTrabajo() {
		return uidCargaTrabajo;
	}

	public void setUidCargaTrabajo(String uidCargaTrabajo) {
		this.uidCargaTrabajo = uidCargaTrabajo;
	}

	public Integer getUidContratista() {
		return uidContratista;
	}

	public void setUidContratista(Integer uidContratista) {
		this.uidContratista = uidContratista;
	}

	public String getDescContratista() {
		return descContratista;
	}

	public void setDescContratista(String descContratista) {
		this.descContratista = descContratista;
	}

	public Integer getUidOficina() {
		return uidOficina;
	}

	public void setUidOficina(Integer uidOficina) {
		this.uidOficina = uidOficina;
	}

	public String getDescOficina() {
		return descOficina;
	}

	public void setDescOficina(String descOficina) {
		this.descOficina = descOficina;
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

	public String getDescActividad() {
		return descActividad;
	}

	public void setDescActividad(String descActividad) {
		this.descActividad = descActividad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getFechaSedapal() {
		return fechaSedapal;
	}

	public void setFechaSedapal(String fechaSedapal) {
		this.fechaSedapal = fechaSedapal;
	}

	public String getFechaContratista() {
		return fechaContratista;
	}

	public void setFechaContratista(String fechaContratista) {
		this.fechaContratista = fechaContratista;
	}

	public Integer getCantidadCarga() {
		return cantidadCarga;
	}

	public void setCantidadCarga(Integer cantidadCarga) {
		this.cantidadCarga = cantidadCarga;
	}

	public Integer getCantidadEjecutada() {
		return cantidadEjecutada;
	}

	public void setCantidadEjecutada(Integer cantidadEjecutada) {
		this.cantidadEjecutada = cantidadEjecutada;
	}

	public String getUidUsuarioC() {
		return uidUsuarioC;
	}

	public void setUidUsuarioC(String uidUsuarioC) {
		this.uidUsuarioC = uidUsuarioC;
	}

	public String getUsuarioNombreC() {
		return usuarioNombreC;
	}

	public void setUsuarioNombreC(String usuarioNombreC) {
		this.usuarioNombreC = usuarioNombreC;
	}

	public String getUidUsuarioE() {
		return uidUsuarioE;
	}

	public void setUidUsuarioE(String uidUsuarioE) {
		this.uidUsuarioE = uidUsuarioE;
	}

	public String getUsuarioNombreE() {
		return usuarioNombreE;
	}

	public void setUsuarioNombreE(String usuarioNombreE) {
		this.usuarioNombreE = usuarioNombreE;
	}

	public String getMotivoAnula() {
		return motivoAnula;
	}

	public void setMotivoAnula(String motivoAnula) {
		this.motivoAnula = motivoAnula;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Object fechaInicio) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]");
		try {
			if (fechaInicio != null) {
				if (fechaInicio instanceof String) {
					this.fechaInicio = LocalDate.parse(((String)fechaInicio).substring(0, 10), formatter);
				} else if (fechaInicio instanceof LocalDate) {
					this.fechaInicio = (LocalDate)fechaInicio;
				} else if (fechaInicio instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaInicio);
					this.fechaInicio = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaInicio = null;
				}
			} 
		} catch (Exception e) {
			this.fechaInicio = null;
		}
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Object fechaFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]");
		try {
			if (fechaFin != null) {
				if (fechaFin instanceof String) {
					this.fechaFin = LocalDate.parse(((String)fechaFin).substring(0, 10), formatter);
				} else if (fechaFin instanceof LocalDate) {
					this.fechaFin = (LocalDate)fechaFin;
				} else if (fechaFin instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaFin);
					this.fechaFin = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaFin = null;
				}
			} 
		} catch (Exception e) {
			this.fechaFin = null;
		}
	}

	public String getUidEstado() {
		return uidEstado;
	}

	public void setUidEstado(String uidEstado) {
		this.uidEstado = uidEstado;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}	

}
