package pe.com.sedapal.agc.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Actividad {

	private String codigo;
	private String descripcion;
	private String estado;
	private Date fechaCreacion;
	private String usuarioCreacion;
	private Date fechaModificacion;
	private String usuarioModificacion;

	public Actividad() {
		super();
	}

	public Actividad(String codigo, String descripcion, String estado, Date fechaCreacion, String usuarioCreacion,
			Date fechaModificacion, String usuarioModificacion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioModificacion = usuarioModificacion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@SuppressWarnings("unchecked")
	public static List<Actividad> mapperActividadBD(Map<String, Object> map) {
		List<Actividad> actividades = new ArrayList<Actividad>();
		List<Map<String, Object>> registros = null;
		if (map.get("O_C_RESULTADO") != null) {
			registros = (List<Map<String, Object>>) map.get("O_C_RESULTADO");
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}

		if (registros.size() > 0) {
			for (Map<String, Object> registro : registros) {
				Actividad actividad = new Actividad();
				actividad.setCodigo(CastUtil.leerValorMapString(registro, "V_IDACTI"));
				actividad.setDescripcion(CastUtil.leerValorMapString(registro, "V_DESCACTI"));
				actividad.setEstado(CastUtil.leerValorMapString(registro, "V_ESTAACTI").equals("A") ? "ACTIVO"
						: CastUtil.leerValorMapString(registro, "V_ESTAACTI").equals("I") ? "INACTIVO" : null);
				actividades.add(actividad);
			}
		}

		return actividades;
	}
	
	public static Actividad fromParamMapper(Map<String, Object> map) {
		Actividad actividad = new Actividad();
		actividad.setCodigo(CastUtil.leerValorMapString(map, "V_IDVALO"));
		actividad.setDescripcion(CastUtil.leerValorMapString(map, "V_DESCDETA"));
		return actividad;
	}

}
