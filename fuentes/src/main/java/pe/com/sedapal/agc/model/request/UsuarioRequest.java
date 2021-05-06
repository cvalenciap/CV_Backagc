package pe.com.sedapal.agc.model.request;

public class UsuarioRequest {

	private String usuario;
	private String clave;
	private String token;
	private int perfil;
	
	public UsuarioRequest() {
		super();
	}

	public UsuarioRequest(String usuario, String clave, String token, int perfil) {
		super();
		this.usuario = usuario;
		this.clave = clave;
		this.token = token;
		this.perfil = perfil;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
