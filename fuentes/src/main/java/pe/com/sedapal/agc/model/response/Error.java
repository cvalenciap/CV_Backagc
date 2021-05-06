package pe.com.sedapal.agc.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Error {

	private Integer codigo;
	private String mensaje;
	private String mensajeInterno;

	public Error(Integer codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.mensajeInterno = "Error durante el procesamiento";
	}

	public Error(Integer codigo, String mensaje, String mensajeInterno) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.mensajeInterno = mensajeInterno;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeInterno() {
		return mensajeInterno;
	}

	public void setMensajeInterno(String mensajeInterno) {
		this.mensajeInterno = mensajeInterno;
	}

}