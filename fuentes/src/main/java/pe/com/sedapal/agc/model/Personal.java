package pe.com.sedapal.agc.model;

public class Personal {
	private Integer uidPersonal;
	private String telefono;
	private String mail;
	private String uidUsuario;
	private String estado;
	private String nombres;
	private String apellidos;
	
	public Personal() {
		super();
	}
	
	public Personal(Integer uidPersonal, String telefono, String mail, String uidUsuario, String estado, String nombres, String apellidos) {
		super();
		this.uidPersonal = uidPersonal;
		this.telefono = telefono;
		this.mail = mail;
		this.uidUsuario = uidUsuario;
		this.estado = estado;
		this.nombres = nombres;
		this.apellidos = apellidos;
	}
	public Integer getUidPersonal() {
		return uidPersonal;
	}
	
	public void setUidPersonal(Integer valor) {
		this.uidPersonal = valor;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String valor) {
		this.telefono = valor;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String valor) {
		this.mail = valor;
	}
	
	public String getUidUsuario() {
		return uidUsuario;
	}
	
	public void setUidUsuario(String valor) {
		this.uidUsuario = valor;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String valor) {
		this.estado = valor;
	}
	
	public String getNombres() {
		return nombres;
	}
	
	public void setNombres(String valor) {
		this.nombres = valor;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String valor) {
		this.apellidos = valor;
	}
}
