package pe.com.sedapal.agc.model;

public class CargaMasivaPersonal extends Auditoria {

	private Integer idCarga;
	private String loteCarga;
	private String fechaCarga;
	private String nombreArchivoCarga;
	private Integer cantidadRegistros;
	private Integer cantidadRegistrosIncorrectos;
	private Integer cantidadRegistrosCorrectos;

	public Integer getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(Integer idCarga) {
		this.idCarga = idCarga;
	}

	public String getLoteCarga() {
		return loteCarga;
	}

	public void setLoteCarga(String loteCarga) {
		this.loteCarga = loteCarga;
	}

	public String getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getNombreArchivoCarga() {
		return nombreArchivoCarga;
	}

	public void setNombreArchivoCarga(String nombreArchivoCarga) {
		this.nombreArchivoCarga = nombreArchivoCarga;
	}

	public Integer getCantidadRegistros() {
		return cantidadRegistros;
	}

	public void setCantidadRegistros(Integer cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	public Integer getCantidadRegistrosIncorrectos() {
		return cantidadRegistrosIncorrectos;
	}

	public void setCantidadRegistrosIncorrectos(Integer cantidadRegistrosIncorrectos) {
		this.cantidadRegistrosIncorrectos = cantidadRegistrosIncorrectos;
	}

	public Integer getCantidadRegistrosCorrectos() {
		return cantidadRegistrosCorrectos;
	}

	public void setCantidadRegistrosCorrectos(Integer cantidadRegistrosCorrectos) {
		this.cantidadRegistrosCorrectos = cantidadRegistrosCorrectos;
	}

}
