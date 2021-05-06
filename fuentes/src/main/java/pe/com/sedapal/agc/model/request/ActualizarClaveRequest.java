package pe.com.sedapal.agc.model.request;

public class ActualizarClaveRequest {

	private String usuario;
	private String claveActual;
	private String nuevaClave;
	private String nuevaClaveR;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClaveActual() {
		return claveActual;
	}

	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}

	public String getNuevaClave() {
		return nuevaClave;
	}

	public void setNuevaClave(String nuevaClave) {
		this.nuevaClave = nuevaClave;
	}

	public String getNuevaClaveR() {
		return nuevaClaveR;
	}

	public void setNuevaClaveR(String nuevaClaveR) {
		this.nuevaClaveR = nuevaClaveR;
	}

}
