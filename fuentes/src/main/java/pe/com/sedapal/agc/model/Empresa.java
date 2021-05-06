package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Empresa {
	private Integer codigo;
	private Integer codigoOpen;
	private String descripcion;
	private String direccion;
	private String telefono;
	private String estado;
	private String comentario;
	private String nroRUC;
	private String tipoEmpresa;
	private String numeroContrato;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaInicioVigencia;
	private LocalDate fechaFinVigencia;
	private Integer indCesePersonal;

	public Integer getIndCesePersonal() {
		return indCesePersonal;
	}

	public void setIndCesePersonal(Integer indCesePersonal) {
		this.indCesePersonal = indCesePersonal;
	}

	@JsonIgnore
	private Date fechaCreacion;

	@JsonIgnore
	private String usuarioCreacion;

	@JsonIgnore
	private Date fechaModificacion;

	@JsonIgnore
	private String usuarioModificacion;

	@JsonIgnore
	private Integer rnum;

	@JsonIgnore
	private Integer result_count;

	public Empresa() {
		super();
	}

	public Empresa(Integer codigo, String descripcion, String direccion, String estado, String comentario,
			Integer documento, String nroRUC, String tipoEmpresa, String numeroContrato, LocalDate fechaInicioVigencia,
			LocalDate fechaFinVigencia, Date fechaCreacion, String usuarioCreacion, Date fechaModificacion,
			String usuarioModificacion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.estado = estado;
		this.comentario = comentario;
		this.nroRUC = nroRUC;
		this.tipoEmpresa = tipoEmpresa;
		this.numeroContrato = numeroContrato;
		this.fechaInicioVigencia = fechaInicioVigencia;
		this.fechaFinVigencia = fechaFinVigencia;
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioModificacion = usuarioModificacion;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoOpen() {
		return codigoOpen;
	}

	public void setCodigoOpen(Integer codigoOpen) {
		this.codigoOpen = codigoOpen;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNroRUC() {
		return nroRUC;
	}

	public void setNroRUC(String nroRUC) {
		this.nroRUC = nroRUC;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

	public Integer getResult_count() {
		return result_count;
	}

	public void setResult_count(Integer result_count) {
		this.result_count = result_count;
	}

	public LocalDate getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}
	public void setFechaInicioVigencia(Object fechaInicioVigencia) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]");
		try {
			if (fechaInicioVigencia != null) {
				if (fechaInicioVigencia instanceof String) {
					this.fechaInicioVigencia = LocalDate.parse(((String) fechaInicioVigencia).substring(0, 10),
							formatter);
				} else if (fechaInicioVigencia instanceof LocalDate) {
					this.fechaInicioVigencia = (LocalDate)fechaInicioVigencia;
				} else if (fechaInicioVigencia instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaInicioVigencia);
					this.fechaInicioVigencia = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaInicioVigencia = null;
				}
			} 
		} catch (Exception e) {
			this.fechaInicioVigencia = null;
		}
	}

	public LocalDate getFechaFinVigencia() {
		return fechaFinVigencia;
	}
	public void setFechaFinVigencia(Object fechaFinVigencia) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]");
		try {
			if (fechaFinVigencia != null) {
				if (fechaFinVigencia instanceof String) {
					this.fechaFinVigencia = LocalDate.parse(((String)fechaFinVigencia).substring(0, 10), formatter);
				} else if (fechaFinVigencia instanceof LocalDate) {
					this.fechaFinVigencia = (LocalDate)fechaFinVigencia;
				} else if (fechaFinVigencia instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaFinVigencia);
					this.fechaFinVigencia = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaFinVigencia = null;
				}
			} 
		} catch (Exception e) {
			this.fechaFinVigencia = null;
		}
	}

}
