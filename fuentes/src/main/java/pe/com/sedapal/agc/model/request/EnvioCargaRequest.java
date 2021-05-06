package pe.com.sedapal.agc.model.request;

public class EnvioCargaRequest {
	private String uidCarga;
	private String desContratista;	
	private Number uidOficina;
	private Number uidGrupo;
	private String uidActividad;
	private String usuario;

	public String getUidCarga() {
		return uidCarga;
	}

	public void setUidCarga(String uidCarga) {
		this.uidCarga = uidCarga;
	}
	
	public String getDesContratista() {
		return desContratista;
	}

	public void setDesContratista(String desContratista) {
		this.desContratista = desContratista;
	}
	
	public Number getUidOficina() {
		return uidOficina;
	}

	public void setUidOficina(Number uidOficina) {
		this.uidOficina = uidOficina;
	}
	public Number getUidGrupo() {
		return uidGrupo;
	}

	public void setUidGrupo(Number uidGrupo) {
		this.uidGrupo = uidGrupo;
	}
	
	public String getUidActividad() {
		return uidActividad;
	}

	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
