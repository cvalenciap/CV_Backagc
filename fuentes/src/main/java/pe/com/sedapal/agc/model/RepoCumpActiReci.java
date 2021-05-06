package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoCumpActiReci {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_fecha;
	private String ciclo;
	private String cod_distribuidor;
	private String distribuidor;
	private String periodo; 
	private BigDecimal rendimiento;
	private BigDecimal carga_entrega;
	private BigDecimal porc_cumplimiento;
	
	
	public RepoCumpActiReci() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoCumpActiReci(String v_idcarga, String v_descofic, String v_nombreempr, String v_fecha, String ciclo,
			String cod_distribuidor, String distribuidor, String periodo, BigDecimal rendimiento,
			BigDecimal carga_entrega, BigDecimal porc_cumplimiento) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_fecha = v_fecha;
		this.ciclo = ciclo;
		this.cod_distribuidor = cod_distribuidor;
		this.distribuidor = distribuidor;
		this.periodo = periodo;
		this.rendimiento = rendimiento;
		this.carga_entrega = carga_entrega;
		this.porc_cumplimiento = porc_cumplimiento;
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


	public String getCiclo() {
		return ciclo;
	}


	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}


	public String getCod_distribuidor() {
		return cod_distribuidor;
	}


	public void setCod_distribuidor(String cod_distribuidor) {
		this.cod_distribuidor = cod_distribuidor;
	}


	public String getDistribuidor() {
		return distribuidor;
	}


	public void setDistribuidor(String distribuidor) {
		this.distribuidor = distribuidor;
	}


	public String getPeriodo() {
		return periodo;
	}


	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}


	public BigDecimal getRendimiento() {
		return rendimiento;
	}


	public void setRendimiento(BigDecimal rendimiento) {
		this.rendimiento = rendimiento;
	}


	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}


	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}


	public BigDecimal getPorc_cumplimiento() {
		return porc_cumplimiento;
	}


	public void setPorc_cumplimiento(BigDecimal porc_cumplimiento) {
		this.porc_cumplimiento = porc_cumplimiento;
	}
	
}

