package pe.com.sedapal.agc.model.request;

import java.util.Date;

public class CicloDetalleRequest {
	private Long n_idciclo;
	private Long n_idciclodet;
	private String v_idacti;
	private String v_idaccion;
	private Long n_id_estado;
	private Date d_fechaejec;
    private String a_v_usucre;
    private String a_v_usumod;
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
    
    
}
