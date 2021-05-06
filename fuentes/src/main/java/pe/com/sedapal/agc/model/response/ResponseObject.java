package pe.com.sedapal.agc.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseObject {

	private Estado estado;

	private Paginacion paginacion;

	private Error error;

	private Object resultado;
	
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Paginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public void setError(Integer codigo, String mensaje, String mensajeInterno) {
		this.error = new Error(codigo, mensaje, mensajeInterno);
	}

	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

}