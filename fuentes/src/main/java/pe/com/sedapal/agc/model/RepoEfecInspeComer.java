package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecInspeComer {
	private String v_idcarga;
	private String femision;
	private String v_descofic; 
	private String tip_os;
	private BigDecimal carga_entrega;
	private BigDecimal ejecutados;
	private BigDecimal imposibilidad;
	private BigDecimal pendientes;
	private BigDecimal porc_ejecutados;
	private BigDecimal porc_imposibilidad;
	private BigDecimal porc_pendientes;
	
	public RepoEfecInspeComer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoEfecInspeComer(String v_idcarga, String femision, String v_descofic, String tip_os,
			BigDecimal carga_entrega, BigDecimal ejecutados, BigDecimal imposibilidad, BigDecimal pendientes,
			BigDecimal porc_ejecutados, BigDecimal porc_imposibilidad, BigDecimal porc_pendientes) {
		super();
		this.v_idcarga = v_idcarga;
		this.femision = femision;
		this.v_descofic = v_descofic;
		this.tip_os = tip_os;
		this.carga_entrega = carga_entrega;
		this.ejecutados = ejecutados;
		this.imposibilidad = imposibilidad;
		this.pendientes = pendientes;
		this.porc_ejecutados = porc_ejecutados;
		this.porc_imposibilidad = porc_imposibilidad;
		this.porc_pendientes = porc_pendientes;
	}



	public String getV_idcarga() {
		return v_idcarga;
	}

	public void setV_idcarga(String v_idcarga) {
		this.v_idcarga = v_idcarga;
	}

	public String getFemision() {
		return femision;
	}

	public void setFemision(String femision) {
		this.femision = femision;
	}

	public String getV_descofic() {
		return v_descofic;
	}

	public void setV_descofic(String v_descofic) {
		this.v_descofic = v_descofic;
	}

	public String getTip_os() {
		return tip_os;
	}

	public void setTip_os(String tip_os) {
		this.tip_os = tip_os;
	}

	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}

	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}

	public BigDecimal getEjecutados() {
		return ejecutados;
	}

	public void setEjecutados(BigDecimal ejecutados) {
		this.ejecutados = ejecutados;
	}

	public BigDecimal getImposibilidad() {
		return imposibilidad;
	}

	public void setImposibilidad(BigDecimal imposibilidad) {
		this.imposibilidad = imposibilidad;
	}

	public BigDecimal getPendientes() {
		return pendientes;
	}

	public void setPendientes(BigDecimal pendientes) {
		this.pendientes = pendientes;
	}

	public BigDecimal getPorc_ejecutados() {
		return porc_ejecutados;
	}

	public void setPorc_ejecutados(BigDecimal porc_ejecutados) {
		this.porc_ejecutados = porc_ejecutados;
	}

	public BigDecimal getPorc_imposibilidad() {
		return porc_imposibilidad;
	}

	public void setPorc_imposibilidad(BigDecimal porc_imposibilidad) {
		this.porc_imposibilidad = porc_imposibilidad;
	}

	public BigDecimal getPorc_pendientes() {
		return porc_pendientes;
	}

	public void setPorc_pendientes(BigDecimal porc_pendientes) {
		this.porc_pendientes = porc_pendientes;
	}

	

}
