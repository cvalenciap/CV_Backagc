package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.enums.ResultadoCarga;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCargaArchivo {

	private String nombreArchivo;
	private String url;
	private String mensaje;
	private ResultadoCarga resultado;

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ResultadoCarga getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoCarga resultado) {
		this.resultado = resultado;
	}

}
