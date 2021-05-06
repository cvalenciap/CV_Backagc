package pe.com.sedapal.agc.model;

import java.util.Date;

public class CicloDetalle {
	private Long n_idciclo;
	private Long n_idciclodet;
	private String v_idacti;
	private String v_idaccion;
	private Long n_id_estado;
	private Date d_fechaejec;
    private String a_v_usucre;
    private String a_v_usumod;
    private Date a_d_feccre;
    private Date a_d_fecmod;
    
	public CicloDetalle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CicloDetalle(Long n_idciclo, Long n_idciclodet, String v_idacti, String v_idaccion, Long n_id_estado,
			Date d_fechaejec, String a_v_usucre, String a_v_usumod, Date a_d_feccre, Date a_d_fecmod) {
		super();
		this.n_idciclo = n_idciclo;
		this.n_idciclodet = n_idciclodet;
		this.v_idacti = v_idacti;
		this.v_idaccion = v_idaccion;
		this.n_id_estado = n_id_estado;
		this.d_fechaejec = d_fechaejec;
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

	public Long getN_idciclodet() {
		return n_idciclodet;
	}

	public void setN_idciclodet(Long n_idciclodet) {
		this.n_idciclodet = n_idciclodet;
	}

	public String getV_idacti() {
		return v_idacti;
	}

	public void setV_idacti(String v_idacti) {
		this.v_idacti = v_idacti;
	}

	public String getV_idaccion() {
		return v_idaccion;
	}

	public void setV_idaccion(String v_idaccion) {
		this.v_idaccion = v_idaccion;
	}

	public Long getN_id_estado() {
		return n_id_estado;
	}

	public void setN_id_estado(Long n_id_estado) {
		this.n_id_estado = n_id_estado;
	}

	public Date getD_fechaejec() {
		return d_fechaejec;
	}

	public void setD_fechaejec(Date d_fechaejec) {
		this.d_fechaejec = d_fechaejec;
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
