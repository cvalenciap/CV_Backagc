package pe.com.sedapal.agc.model;

import java.util.Date;

public class AlertasTemplate {
    private Long n_sec_template;
	private String v_act_template;
	private String v_nom_template;
	private String v_con_template_ca;
	private Long n_id_sec_queries;
	private String a_v_usucre;
	private Date a_d_feccre;
	private String a_v_usumod;
	private Date a_d_fecmod;
	
	public AlertasTemplate() {
		super();
	}

	public AlertasTemplate(Long n_sec_template, String v_act_template, String v_nom_template, String v_con_template_ca,
			Long n_id_sec_queries, String a_v_usucre, Date a_d_feccre, String a_v_usumod, Date a_d_fecmod) {
		super();
		this.n_sec_template = n_sec_template;
		this.v_act_template = v_act_template;
		this.v_nom_template = v_nom_template;
		this.v_con_template_ca = v_con_template_ca;
		this.n_id_sec_queries = n_id_sec_queries;
		this.a_v_usucre = a_v_usucre;
		this.a_d_feccre = a_d_feccre;
		this.a_v_usumod = a_v_usumod;
		this.a_d_fecmod = a_d_fecmod;
	}

	public Long getN_sec_template() {
		return n_sec_template;
	}

	public void setN_sec_template(Long n_sec_template) {
		this.n_sec_template = n_sec_template;
	}

	public String getV_act_template() {
		return v_act_template;
	}

	public void setV_act_template(String v_act_template) {
		this.v_act_template = v_act_template;
	}

	public String getV_nom_template() {
		return v_nom_template;
	}

	public void setV_nom_template(String v_nom_template) {
		this.v_nom_template = v_nom_template;
	}

	public String getV_con_template_ca() {
		return v_con_template_ca;
	}

	public void setV_con_template_ca(String v_con_template_ca) {
		this.v_con_template_ca = v_con_template_ca;
	}

	public Long getN_id_sec_queries() {
		return n_id_sec_queries;
	}

	public void setN_id_sec_queries(Long n_id_sec_queries) {
		this.n_id_sec_queries = n_id_sec_queries;
	}

	public String getA_v_usucre() {
		return a_v_usucre;
	}

	public void setA_v_usucre(String a_v_usucre) {
		this.a_v_usucre = a_v_usucre;
	}

	public Date getA_d_feccre() {
		return a_d_feccre;
	}

	public void setA_d_feccre(Date a_d_feccre) {
		this.a_d_feccre = a_d_feccre;
	}

	public String getA_v_usumod() {
		return a_v_usumod;
	}

	public void setA_v_usumod(String a_v_usumod) {
		this.a_v_usumod = a_v_usumod;
	}

	public Date getA_d_fecmod() {
		return a_d_fecmod;
	}

	public void setA_d_fecmod(Date a_d_fecmod) {
		this.a_d_fecmod = a_d_fecmod;
	}
	
	
	
}
