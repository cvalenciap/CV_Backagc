package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoInfActiEjec {
	private BigDecimal item;
	private String v_perfil;
	private BigDecimal pendiente; 
    private BigDecimal n_idOfic;
	private String v_descOfic; 
	private String a_v_usucre; 
	private String v_descacti; 
	private String v_idsubacti;
	private String UM; 
	private BigDecimal prog_Mes; 
	private BigDecimal porc_Avance;
	private BigDecimal prog_Total; 
	private BigDecimal ava_Total;
	private BigDecimal ejecMes;
	private BigDecimal ejecTotal;

	public BigDecimal getEjecMes() {
		return ejecMes;
	}

	public void setEjecMes(BigDecimal ejecMes) {
		this.ejecMes = ejecMes;
	}

	public String getV_perfil() {
		return v_perfil;
	}

	public void setV_perfil(String v_perfil) {
		this.v_perfil = v_perfil;
	}

	public RepoInfActiEjec() {
		super();
	}

	public RepoInfActiEjec(BigDecimal item, BigDecimal pendiente, BigDecimal n_idOfic, 
			String v_descOfic, String a_v_usucre, String v_descacti, String v_idsubacti, String uM, BigDecimal prog_Mes,
			BigDecimal porc_Avance, BigDecimal prog_Total, BigDecimal ava_Total, BigDecimal ejecMes,
			BigDecimal ejecTotal, String v_perfil) {
		super();
		this.item = item;
		this.pendiente = pendiente;
		this.n_idOfic = n_idOfic;
		this.v_descOfic = v_descOfic;
		this.a_v_usucre = a_v_usucre;
		this.v_descacti = v_descacti;
		this.v_idsubacti = v_idsubacti;
		this.UM = uM;
		this.prog_Mes = prog_Mes;
		this.porc_Avance = porc_Avance;
		this.prog_Total = prog_Total;
		this.ava_Total = ava_Total;
		this.ejecMes = ejecMes;
		this.ejecTotal = ejecTotal;
		this.v_perfil = v_perfil;
	}

	public BigDecimal getEjecTotal() {
		return ejecTotal;
	}

	public void setEjecTotal(BigDecimal ejecTotal) {
		this.ejecTotal = ejecTotal;
	}

	public BigDecimal getItem() {
		return item;
	}

	public void setItem(BigDecimal item) {
		this.item = item;
	}

	public BigDecimal getPendiente() {
		return pendiente;
	}

	public void setPendiente(BigDecimal pendiente) {
		this.pendiente = pendiente;
	}

	public BigDecimal getN_idOfic() {
		return n_idOfic;
	}

	public void setN_idOfic(BigDecimal n_idOfic) {
		this.n_idOfic = n_idOfic;
	}

	public String getV_descOfic() {
		return v_descOfic;
	}

	public void setV_descOfic(String v_descOfic) {
		this.v_descOfic = v_descOfic;
	}

	public String getA_v_usucre() {
		return a_v_usucre;
	}

	public void setA_v_usucre(String a_v_usucre) {
		this.a_v_usucre = a_v_usucre;
	}

	public String getV_descacti() {
		return v_descacti;
	}

	public void setV_descacti(String v_descacti) {
		this.v_descacti = v_descacti;
	}

	public String getV_idsubacti() {
		return v_idsubacti;
	}

	public void setV_idsubacti(String v_idsubacti) {
		this.v_idsubacti = v_idsubacti;
	}

	public String getUM() {
		return UM;
	}

	public void setUM(String uM) {
		UM = uM;
	}

	public BigDecimal getProg_Mes() {
		return prog_Mes;
	}

	public void setProg_Mes(BigDecimal prog_Mes) {
		this.prog_Mes = prog_Mes;
	}

	public BigDecimal getPorc_Avance() {
		return porc_Avance;
	}

	public void setPorc_Avance(BigDecimal porc_Avance) {
		this.porc_Avance = porc_Avance;
	}

	public BigDecimal getProg_Total() {
		return prog_Total;
	}

	public void setProg_Total(BigDecimal prog_Total) {
		this.prog_Total = prog_Total;
	}

	public BigDecimal getAva_Total() {
		return ava_Total;
	}

	public void setAva_Total(BigDecimal ava_Total) {
		this.ava_Total = ava_Total;
	}


}
