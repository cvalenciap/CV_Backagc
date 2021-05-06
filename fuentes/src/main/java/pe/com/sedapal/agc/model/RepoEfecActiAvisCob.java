package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecActiAvisCob {

	private String v_idcarga;
	private String ciclo;
	private String v_descofic;
	private String periodo; 
	private BigDecimal carga_entrega;
	private BigDecimal entregado;
	private BigDecimal no_entregado;
	private BigDecimal supervisado;
	private BigDecimal porc_entregado;
	private BigDecimal porc_no_entregado;
	private BigDecimal porc_supervisado;
	
	
	public RepoEfecActiAvisCob() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public RepoEfecActiAvisCob(String v_idcarga, String ciclo, String v_descofic, String periodo,
			BigDecimal carga_entrega, BigDecimal entregado, BigDecimal no_entregado, BigDecimal supervisado,
			BigDecimal porc_entregado, BigDecimal porc_no_entregado, BigDecimal porc_supervisado) {
		super();
		this.v_idcarga = v_idcarga;
		this.ciclo = ciclo;
		this.v_descofic = v_descofic;
		this.periodo = periodo;
		this.carga_entrega = carga_entrega;
		this.entregado = entregado;
		this.no_entregado = no_entregado;
		this.supervisado = supervisado;
		this.porc_entregado = porc_entregado;
		this.porc_no_entregado = porc_no_entregado;
		this.porc_supervisado = porc_supervisado;
	}


	public String getV_idcarga() {
		return v_idcarga;
	}
	public void setV_idcarga(String v_idcarga) {
		this.v_idcarga = v_idcarga;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public String getV_descofic() {
		return v_descofic;
	}
	public void setV_descofic(String v_descofic) {
		this.v_descofic = v_descofic;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}
	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}
	public BigDecimal getEntregado() {
		return entregado;
	}
	public void setEntregado(BigDecimal entregado) {
		this.entregado = entregado;
	}
	public BigDecimal getNo_entregado() {
		return no_entregado;
	}
	public void setNo_entregado(BigDecimal no_entregado) {
		this.no_entregado = no_entregado;
	}
	public BigDecimal getSupervisado() {
		return supervisado;
	}
	public void setSupervisado(BigDecimal supervisado) {
		this.supervisado = supervisado;
	}
	public BigDecimal getPorc_entregado() {
		return porc_entregado;
	}
	public void setPorc_entregado(BigDecimal porc_entregado) {
		this.porc_entregado = porc_entregado;
	}
	public BigDecimal getPorc_no_entregado() {
		return porc_no_entregado;
	}
	public void setPorc_no_entregado(BigDecimal porc_no_entregado) {
		this.porc_no_entregado = porc_no_entregado;
	}
	public BigDecimal getPorc_supervisado() {
		return porc_supervisado;
	}
	public void setPorc_supervisado(BigDecimal porc_supervisado) {
		this.porc_supervisado = porc_supervisado;
	}
	
}
