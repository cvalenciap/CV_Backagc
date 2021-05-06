package pe.com.sedapal.agc.model;

import java.util.Date;

public class Ciclo {
	private Long n_idciclo;
	private Long n_idofic;
	private String d_Periodo;
	private Long n_ciclo;
	private Long n_id_estado;
	private Long n_child;
    private String a_v_usucre;
    private String a_v_usumod;
    private Date a_d_feccre;
    private Date a_d_fecmod;
    
	public Ciclo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ciclo(Long n_idciclo, Long n_idofic, String d_Periodo, Long n_ciclo, Long n_id_estado, String a_v_usucre,
			String a_v_usumod, Date a_d_feccre, Date a_d_fecmod) {
		super();
		this.n_idciclo = n_idciclo;
		this.n_idofic = n_idofic;
		this.d_Periodo = d_Periodo;
		this.n_ciclo = n_ciclo;
		this.n_id_estado = n_id_estado;
		this.a_v_usucre = a_v_usucre;
		this.a_v_usumod = a_v_usumod;
		this.a_d_feccre = a_d_feccre;
		this.a_d_fecmod = a_d_fecmod;
	}

	public Long getN_idciclo() {
		return n_idciclo;
	}

	public void setN_idciclo(Long n_idciclo) {
		this.n_idciclo = n_idciclo;
	}

	public Long getN_idofic() {
		return n_idofic;
	}

	public void setN_idofic(Long n_idofic) {
		this.n_idofic = n_idofic;
	}

	public String getD_Periodo() {
		return d_Periodo;
	}

	public void setD_Periodo(String d_Periodo) {
		this.d_Periodo = d_Periodo;
	}

	public Long getN_ciclo() {
		return n_ciclo;
	}

	public void setN_ciclo(Long n_ciclo) {
		this.n_ciclo = n_ciclo;
	}

	public Long getN_id_estado() {
		return n_id_estado;
	}

	public void setN_id_estado(Long n_id_estado) {
		this.n_id_estado = n_id_estado;
	}

	public String getA_v_usucre() {
		return a_v_usucre;
	}

	public void setA_v_usucre(String a_v_usucre) {
		this.a_v_usucre = a_v_usucre;
	}

	public String getA_v_usumod() {
		return a_v_usumod;
	}

	public void setA_v_usumod(String a_v_usumod) {
		this.a_v_usumod = a_v_usumod;
	}

	public Date getA_d_feccre() {
		return a_d_feccre;
	}

	public void setA_d_feccre(Date a_d_feccre) {
		this.a_d_feccre = a_d_feccre;
	}

	public Date getA_d_fecmod() {
		return a_d_fecmod;
	}

	public void setA_d_fecmod(Date a_d_fecmod) {
		this.a_d_fecmod = a_d_fecmod;
	}

	public Long getN_child() {
		return n_child;
	}

	public void setN_child(Long n_child) {
		this.n_child = n_child;
	}
	
    

}
