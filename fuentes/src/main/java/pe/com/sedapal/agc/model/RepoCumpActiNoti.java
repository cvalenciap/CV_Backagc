package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoCumpActiNoti {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_fecha;
	private String ciclo;
	private String cod_lector;
	private String lector;
	private String periodo; 
	private String d_femision;
	private BigDecimal rendimiento;
	private BigDecimal carga_entrega;
	private BigDecimal porc_cumplimiento;
	
	
	public RepoCumpActiNoti() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RepoCumpActiNoti(String v_idcarga, String v_descofic, String v_nombreempr, String v_fecha, String ciclo,
			String cod_lector, String lector, String periodo, String d_femision, BigDecimal rendimiento,
			BigDecimal carga_entrega, BigDecimal porc_cumplimiento) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_fecha = v_fecha;
		this.ciclo = ciclo;
		this.cod_lector = cod_lector;
		this.lector = lector;
		this.periodo = periodo;
		this.d_femision = d_femision;
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


	public String getCod_lector() {
		return cod_lector;
	}


	public void setCod_lector(String cod_lector) {
		this.cod_lector = cod_lector;
	}


	public String getLector() {
		return lector;
	}


	public void setLector(String lector) {
		this.lector = lector;
	}


	public String getPeriodo() {
		return periodo;
	}


	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}


	public String getD_femision() {
		return d_femision;
	}


	public void setD_femision(String d_femision) {
		this.d_femision = d_femision;
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

