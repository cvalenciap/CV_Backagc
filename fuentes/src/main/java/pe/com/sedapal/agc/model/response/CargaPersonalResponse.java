package pe.com.sedapal.agc.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.enums.ResultadoCarga;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargaPersonalResponse {

	private String loteCarga;
	private Integer nro;
	private Integer nroRegistro;
	private String fechaDeCarga;
	private String numeroDocumento;
	private Integer codEmpleado1;
	private Integer codEmpleado2;
	private List<String> detalle;
	private ResultadoCarga resultado;

	public String getLoteCarga() {
		return loteCarga;
	}

	public void setLoteCarga(String loteCarga) {
		this.loteCarga = loteCarga;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Integer getNroRegistro() {
		return nroRegistro;
	}

	public void setNroRegistro(Integer nroRegistro) {
		this.nroRegistro = nroRegistro;
	}

	public String getFechaDeCarga() {
		return fechaDeCarga;
	}

	public void setFechaDeCarga(String fechaDeCarga) {
		this.fechaDeCarga = fechaDeCarga;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Integer getCodEmpleado1() {
		return codEmpleado1;
	}

	public void setCodEmpleado1(Integer codEmpleado1) {
		this.codEmpleado1 = codEmpleado1;
	}

	public Integer getCodEmpleado2() {
		return codEmpleado2;
	}

	public void setCodEmpleado2(Integer codEmpleado2) {
		this.codEmpleado2 = codEmpleado2;
	}

	public List<String> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<String> detalle) {
		this.detalle = detalle;
	}

	public ResultadoCarga getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoCarga resultado) {
		this.resultado = resultado;
	}

}
