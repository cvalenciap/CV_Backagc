package pe.com.sedapal.agc.model;

public class CabeceraCargaPersonal {

	private Integer n_idcarga;
	private String n_lotecarga;
	private String v_fec_carga;
	private String v_nom_archivo;
	private Integer n_cant_registros;
	private Integer n_cant_correctos;
	private Integer n_cant_erroneos;
	private String v_usuario_reg;
	private String v_fec_reg;

	public Integer getN_idcarga() {
		return n_idcarga;
	}

	public void setN_idcarga(Integer n_idcarga) {
		this.n_idcarga = n_idcarga;
	}

	public String getN_lotecarga() {
		return n_lotecarga;
	}

	public void setN_lotecarga(String n_lotecarga) {
		this.n_lotecarga = n_lotecarga;
	}

	public String getV_fec_carga() {
		return v_fec_carga;
	}

	public void setV_fec_carga(String v_fec_carga) {
		this.v_fec_carga = v_fec_carga;
	}

	public String getV_nom_archivo() {
		return v_nom_archivo;
	}

	public void setV_nom_archivo(String v_nom_archivo) {
		this.v_nom_archivo = v_nom_archivo;
	}

	public Integer getN_cant_registros() {
		return n_cant_registros;
	}

	public void setN_cant_registros(Integer n_cant_registros) {
		this.n_cant_registros = n_cant_registros;
	}

	public Integer getN_cant_correctos() {
		return n_cant_correctos;
	}

	public void setN_cant_correctos(Integer n_cant_correctos) {
		this.n_cant_correctos = n_cant_correctos;
	}

	public Integer getN_cant_erroneos() {
		return n_cant_erroneos;
	}

	public void setN_cant_erroneos(Integer n_cant_erroneos) {
		this.n_cant_erroneos = n_cant_erroneos;
	}

	public String getV_usuario_reg() {
		return v_usuario_reg;
	}

	public void setV_usuario_reg(String v_usuario_reg) {
		this.v_usuario_reg = v_usuario_reg;
	}

	public String getV_fec_reg() {
		return v_fec_reg;
	}

	public void setV_fec_reg(String v_fec_reg) {
		this.v_fec_reg = v_fec_reg;
	}

}
