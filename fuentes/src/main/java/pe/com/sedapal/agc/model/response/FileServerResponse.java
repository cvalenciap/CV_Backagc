package pe.com.sedapal.agc.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileServerResponse implements Serializable {

	private static final long serialVersionUID = 2598781037594226799L;

	private String mensaje;
	private String ubicacion;
	private String nombre;
	private String extension;
	private String nombreReal;
	private String url;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
