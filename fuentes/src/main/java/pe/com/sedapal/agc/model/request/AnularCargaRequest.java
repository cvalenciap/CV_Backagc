package pe.com.sedapal.agc.model.request;

public class AnularCargaRequest {
	private String uidCarga;
	private String usuario;
	private String motivo;
	
	public String getUidCarga() {
		return uidCarga;
	}

	public void setUidCarga(String uidCarga) {
		this.uidCarga = uidCarga;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
