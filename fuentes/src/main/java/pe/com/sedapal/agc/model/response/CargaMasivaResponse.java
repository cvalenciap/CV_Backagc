package pe.com.sedapal.agc.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargaMasivaResponse {
	private Estado estado;
	private Integer total;
	private Integer procesados;
	private Integer fallidos;
	private String mensaje;
	private Error error;
	private List<Error> listaErrores;
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getProcesados() {
		return procesados;
	}

	public void setProcesados(Integer procesados) {
		this.procesados = procesados;
	}

	public Integer getFallidos() {
		return fallidos;
	}

	public void setFallidos(Integer fallidos) {
		this.fallidos = fallidos;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<Error> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<Error> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public void setError(int codigo, String mensaje, String mensajeInterno) {
		this.error = new Error(codigo, mensaje, mensajeInterno);
	}

}
