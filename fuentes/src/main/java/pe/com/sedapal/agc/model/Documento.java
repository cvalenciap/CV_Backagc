package pe.com.sedapal.agc.model;

public class Documento {
	private Integer codigo;
	private String codigoCarga;
	private String codigoDetalle;
	private String nombre;
	private String ruta;
	private String tipo;
	private String usuario;

	public Documento(String nombre, String ruta, String tipo) {
		super();
		this.nombre = nombre;
		this.ruta = ruta;
		this.tipo = tipo;
	}
	
	public Documento(String nombre, String ruta, String tipo, String usuario) {
		super();
		this.nombre = nombre;
		this.ruta = ruta;
		this.tipo = tipo;
		this.usuario = usuario;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCarga() {
		return codigoCarga;
	}

	public void setCodigoCarga(String codigoCarga) {
		this.codigoCarga = codigoCarga;
	}

	public String getCodigoDetalle() {
		return codigoDetalle;
	}

	public void setCodigoDetalle(String codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
