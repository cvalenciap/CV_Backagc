package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.enums.ResultadoCarga;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Archivo extends Auditoria {
	private Integer id;
	private Integer codigoEmpleado;
	private String tipoArchivo;
	private String nombreArchivo;
	private String rutaArchivo;
	private String dataArchivo;
	private ResultadoCarga resultado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getDataArchivo() {
		return dataArchivo;
	}

	public void setDataArchivo(String dataArchivo) {
		this.dataArchivo = dataArchivo;
	}

	public ResultadoCarga getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoCarga resultado) {
		this.resultado = resultado;
	}

}
