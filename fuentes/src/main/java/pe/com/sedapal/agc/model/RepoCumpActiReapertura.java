package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoCumpActiReapertura {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_fecha;
	private String ciclo;
	private String cod_operario;
	private String operario;
	private String periodo; 
	private BigDecimal rendimiento;
	private BigDecimal carga_entrega;
	private BigDecimal porc_cumplimiento;
	
	
	public RepoCumpActiReapertura() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RepoCumpActiReapertura(String v_idcarga, String v_descofic, String v_nombreempr, String v_fecha, String ciclo,
			String cod_operario, String operario, String periodo, BigDecimal rendimiento, BigDecimal carga_entrega,
			BigDecimal porc_cumplimiento) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_fecha = v_fecha;
		this.ciclo = ciclo;
		this.cod_operario = cod_operario;
		this.operario = operario;
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


	public String getCod_operario() {
		return cod_operario;
	}


	public void setCod_operario(String cod_operario) {
		this.cod_operario = cod_operario;
	}


	public String getOperario() {
		return operario;
	}


	public void setOperario(String operario) {
		this.operario = operario;
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

