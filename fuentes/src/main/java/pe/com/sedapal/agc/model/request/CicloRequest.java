package pe.com.sedapal.agc.model.request;

public class CicloRequest {
    private Long n_idciclo;
    private Long n_idofic;
    private String d_periodo;
	private Long n_ciclo;
	private Long n_id_estado;
	private Long n_cant_periodos;
	private String a_v_usucre;
	private String a_v_usumod;
	
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
	public String getD_periodo() {
		return d_periodo;
	}
	public void setD_periodo(String d_periodo) {
		this.d_periodo = d_periodo;
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
	public Long getN_cant_periodos() {
		return n_cant_periodos;
	}
	public void setN_cant_periodos(Long n_cant_periodos) {
		this.n_cant_periodos = n_cant_periodos;
	}	
    
	
	
}
