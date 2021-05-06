package pe.com.sedapal.agc.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Adjunto {
	private String  uidCarga;
	private Integer uidRegistro;
	private Integer uidAdjunto;
	private String 	tipoOrigen;
	private String  tipo;
	private String 	ruta;
	private String	nombre;
	private String extension;
	private String estado;
	private String 	usuarioCreacion;
	private String 	usuarioModificacion;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate	fechaCreacion;
	/*agregado para visor de imagenes - rramirez*/
	private String	fechaCreacionConHora;
	/**/
	@DateTimeFormat(iso = ISO.DATE)
	private	LocalDate	fechaModificacion;
	private String fechaCarga;	

	@JsonIgnore
	private Integer totalRegistros;
	
	public Adjunto() {
		super();
	}
	
	public Adjunto(String idCarga, Integer idAdjunto, String tipoOrigen, String tipo, String ruta, String nombre, String extension, 
			String usuarioCreacion, String usuarioModificacion, LocalDate fechaCreacion, LocalDate fechaModificacion) {
		super();
		this.uidCarga = idCarga;
		this.uidAdjunto = idAdjunto;
		this.tipoOrigen = tipoOrigen;
		this.tipo = tipo;
		this.ruta = ruta;
		this.nombre = nombre;
		this.extension = extension;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
	}
	public String getUidCarga() {
		return uidCarga;
	}
	
	public void setUidCarga(String valor) {
		this.uidCarga = valor;
	}
	public Integer getUidAdjunto() {
		return uidAdjunto;
	}

	public void setUidAdjunto(Integer valor) {
		this.uidAdjunto = valor;
	}
	public String getTipoOrigen() {
		return tipoOrigen;
	}

	public void setTipoOrigen(String valor) {
		this.tipoOrigen = valor;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String valor) {
		this.tipo = valor;
	}
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String valor) {
		this.ruta = valor;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Object fechaCreacion) {
		try {
			if (fechaCreacion != null) {
				if (fechaCreacion instanceof String) {
					this.fechaCreacion = LocalDate.parse(((String)fechaCreacion).substring(0,10));
				} else if (fechaCreacion instanceof LocalDate) {
					this.fechaCreacion = (LocalDate)fechaCreacion;
				} else if (fechaCreacion instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaCreacion);
					this.fechaCreacion = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaCreacion = null;
				}
			} 
		} catch (Exception e) {
			this.fechaCreacion = null;
		}
	}
	
	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Object fechaModificacion) {
		try {
			if (fechaModificacion != null) {
				if (fechaModificacion instanceof String) {
					this.fechaModificacion = LocalDate.parse(((String)fechaModificacion).substring(0,10));
				} else if (fechaModificacion instanceof LocalDate) {
					this.fechaModificacion = (LocalDate)fechaModificacion;
				} else if (fechaModificacion instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaModificacion);
					this.fechaModificacion = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				} else {
					this.fechaModificacion = null;
				}
			} 
		} catch (Exception e) {
			this.fechaModificacion = null;
		}
	}
	
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String valor) {
		this.usuarioModificacion = valor;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String valor) {
		this.nombre = valor;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Integer getUidRegistro() {
		return uidRegistro;
	}

	public void setUidRegistro(Integer uidRegistro) {
		this.uidRegistro = uidRegistro;
	}
	
	public String getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	/*agregado para visor de imagenes - rramirez*/
	public String getFechaCreacionConHora() {
		return fechaCreacionConHora;
	}
	public void setFechaCreacionConHora(String fechaCreacionTime) {
		this.fechaCreacionConHora = fechaCreacionTime;
	}
	/**/
}
