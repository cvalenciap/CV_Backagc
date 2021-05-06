package pe.com.sedapal.agc.model;

import java.util.Date;

public class Alerta {
    private Long v_id_alerta;
    private String v_description;
    private Long v_estado;
    private String a_v_usucre;
    private String a_v_usumod;
    private Date a_d_feccre;
    private Date a_d_fecmod;
    
	public Alerta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Alerta(Long v_id_alerta, String v_description, Long v_estado, String a_v_usucre, String a_v_usumod,
			Date a_d_feccre, Date a_d_fecmod) {
		super();
		this.v_id_alerta = v_id_alerta;
		this.v_description = v_description;
		this.v_estado = v_estado;
		this.a_v_usucre = a_v_usucre;
		this.a_v_usumod = a_v_usumod;
		this.a_d_feccre = a_d_feccre;
		this.a_d_fecmod = a_d_fecmod;
	}

	public Long getV_id_alerta() {
		return v_id_alerta;
	}

	public void setV_id_alerta(Long v_id_alerta) {
		this.v_id_alerta = v_id_alerta;
	}

	public String getV_description() {
		return v_description;
	}

	public void setV_description(String v_description) {
		this.v_description = v_description;
	}

	public Long getV_estado() {
		return v_estado;
	}

	public void setV_estado(Long v_estado) {
		this.v_estado = v_estado;
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
