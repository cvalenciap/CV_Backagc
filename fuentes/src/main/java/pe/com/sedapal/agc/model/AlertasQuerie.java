package pe.com.sedapal.agc.model;

import java.util.Date;

public class AlertasQuerie {
    private Long n_sec_queries;
    private String v_idacti;
    private Long v_id_alerta;
    private String v_query_title_columns;
    private String v_query_td_columns;
    private String v_query_body;
    private Long v_query_uso;
    private String a_v_usucre;
    private String a_v_usumod;
    private Date a_d_feccre;
    private Date a_d_fecmod;
    
	public AlertasQuerie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlertasQuerie(Long n_sec_queries, String v_idacti, Long v_id_alerta, String v_query_title_columns,
			String v_query_td_columns, String v_query_body, Long v_query_uso, String a_v_usucre, String a_v_usumod,
			Date a_d_feccre, Date a_d_fecmod) {
		super();
		this.n_sec_queries = n_sec_queries;
		this.v_idacti = v_idacti;
		this.v_id_alerta = v_id_alerta;
		this.v_query_title_columns = v_query_title_columns;
		this.v_query_td_columns = v_query_td_columns;
		this.v_query_body = v_query_body;
		this.v_query_uso = v_query_uso;
		this.a_v_usucre = a_v_usucre;
		this.a_v_usumod = a_v_usumod;
		this.a_d_feccre = a_d_feccre;
		this.a_d_fecmod = a_d_fecmod;
	}

	public Long getN_sec_queries() {
		return n_sec_queries;
	}

	public void setN_sec_queries(Long n_sec_queries) {
		this.n_sec_queries = n_sec_queries;
	}

	public String getV_idacti() {
		return v_idacti;
	}

	public void setV_idacti(String v_idacti) {
		this.v_idacti = v_idacti;
	}

	public Long getV_id_alerta() {
		return v_id_alerta;
	}

	public void setV_id_alerta(Long v_id_alerta) {
		this.v_id_alerta = v_id_alerta;
	}

	public String getV_query_title_columns() {
		return v_query_title_columns;
	}

	public void setV_query_title_columns(String v_query_title_columns) {
		this.v_query_title_columns = v_query_title_columns;
	}

	public String getV_query_td_columns() {
		return v_query_td_columns;
	}

	public void setV_query_td_columns(String v_query_td_columns) {
		this.v_query_td_columns = v_query_td_columns;
	}

	public String getV_query_body() {
		return v_query_body;
	}

	public void setV_query_body(String v_query_body) {
		this.v_query_body = v_query_body;
	}

	public Long getV_query_uso() {
		return v_query_uso;
	}

	public void setV_query_uso(Long v_query_uso) {
		this.v_query_uso = v_query_uso;
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
    
	
    
    
}
