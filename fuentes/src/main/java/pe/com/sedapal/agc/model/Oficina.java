package pe.com.sedapal.agc.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Oficina {

	private Integer codigo;
	private String descripcion;
	private String direccion;
	private String estado;
	private Date fechaCreacion;
	private String usuarioCreacion;
	private Date fechaModificacion;
	private String usuarioModificacion;
	private Integer idEmpresa;

	public Oficina() {
		super();
	}
	
	public Oficina(Integer codigo, String descripcion, String direccion, String estado, Date fechaCreacion,
			String usuarioCreacion, Date fechaModificacion, String usuarioModificacion, Integer idEmpresa) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioModificacion = usuarioModificacion;
		this.idEmpresa = idEmpresa;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	
}
