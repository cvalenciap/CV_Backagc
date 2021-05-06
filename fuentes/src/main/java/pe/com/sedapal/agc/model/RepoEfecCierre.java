package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecCierre {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_fecha;
	private BigDecimal carga_entrega;
	private BigDecimal ejecutada;
	private BigDecimal imposibilidad;
	private BigDecimal pendiente;
	private BigDecimal porc_ejecutada;
	private BigDecimal porc_imposibilidad;
	private BigDecimal porc_pendiente;
	

	public RepoEfecCierre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoEfecCierre(String v_idcarga, String v_descofic, String v_nombreempr, String v_fecha,
			BigDecimal carga_entrega, BigDecimal ejecutada, BigDecimal imposibilidad, BigDecimal pendiente,
			BigDecimal porc_ejecutada, BigDecimal porc_imposibilidad, BigDecimal porc_pendiente) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_fecha = v_fecha;
		this.carga_entrega = carga_entrega;
		this.ejecutada = ejecutada;
		this.imposibilidad = imposibilidad;
		this.pendiente = pendiente;
		this.porc_ejecutada = porc_ejecutada;
		this.porc_imposibilidad = porc_imposibilidad;
		this.porc_pendiente = porc_pendiente;
	}


	public String getV_idcarga() {
		return v_idcarga;
	}


	public void setV_idcarga(String v_idcarga) {
		this.v_idcarga = v_idcarga;
	}


	public String getV_descofic() {
		return v_descofic;
	}


	public void setV_descofic(String v_descofic) {
		this.v_descofic = v_descofic;
	}


	public String getV_nombreempr() {
		return v_nombreempr;
	}


	public void setV_nombreempr(String v_nombreempr) {
		this.v_nombreempr = v_nombreempr;
	}


	public String getV_fecha() {
		return v_fecha;
	}


	public void setV_fecha(String v_fecha) {
		this.v_fecha = v_fecha;
	}


	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}


	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}


	public BigDecimal getEjecutada() {
		return ejecutada;
	}


	public void setEjecutada(BigDecimal ejecutada) {
		this.ejecutada = ejecutada;
	}


	public BigDecimal getImposibilidad() {
		return imposibilidad;
	}


	public void setImposibilidad(BigDecimal imposibilidad) {
		this.imposibilidad = imposibilidad;
	}


	public BigDecimal getPendiente() {
		return pendiente;
	}


	public void setPendiente(BigDecimal pendiente) {
		this.pendiente = pendiente;
	}


	public BigDecimal getPorc_ejecutada() {
		return porc_ejecutada;
	}


	public void setPorc_ejecutada(BigDecimal porc_ejecutada) {
		this.porc_ejecutada = porc_ejecutada;
	}


	public BigDecimal getPorc_imposibilidad() {
		return porc_imposibilidad;
	}


	public void setPorc_imposibilidad(BigDecimal porc_imposibilidad) {
		this.porc_imposibilidad = porc_imposibilidad;
	}


	public BigDecimal getPorc_pendiente() {
		return porc_pendiente;
	}


	public void setPorc_pendiente(BigDecimal porc_pendiente) {
		this.porc_pendiente = porc_pendiente;
	}
	
	
	
}
