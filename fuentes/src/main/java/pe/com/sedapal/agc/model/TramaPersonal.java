package pe.com.sedapal.agc.model;

public class TramaPersonal {

	private String v_dni;
	private String v_ape_paterno;
	private String v_ape_materno;
	private String v_nombres;
	private String v_fec_nac;
	private String v_direccion;
	private String v_fec_ingreso;
	private String v_cod_cargo;
	private String v_cod_item;
	private String v_cod_oficina;
	private String v_telf_pers;
	private String v_telf_asig;
	private String v_correo;
	private String v_cod_pers_ctrat;
	private String v_cod_empresa;
	private String v_usuario_reg;
	private Integer n_flag_archivos;

	public TramaPersonal() {
	}

	public TramaPersonal(String v_dni, String v_ape_paterno, String v_ape_materno, String v_nombres, String v_fec_nac,
			String v_direccion, String v_fec_ingreso, String v_cod_cargo, String v_cod_item, String v_cod_oficina,
			String v_telf_pers, String v_telf_asig, String v_correo, String v_cod_pers_ctrat, String v_cod_empresa,
			String v_usuario_reg, Integer n_flag_archivos) {
		super();
		this.v_dni = v_dni;
		this.v_ape_paterno = v_ape_paterno;
		this.v_ape_materno = v_ape_materno;
		this.v_nombres = v_nombres;
		this.v_fec_nac = v_fec_nac;
		this.v_direccion = v_direccion;
		this.v_fec_ingreso = v_fec_ingreso;
		this.v_cod_cargo = v_cod_cargo;
		this.v_cod_item = v_cod_item;
		this.v_cod_oficina = v_cod_oficina;
		this.v_telf_pers = v_telf_pers;
		this.v_telf_asig = v_telf_asig;
		this.v_correo = v_correo;
		this.v_cod_pers_ctrat = v_cod_pers_ctrat;
		this.v_cod_empresa = v_cod_empresa;
		this.v_usuario_reg = v_usuario_reg;
		this.n_flag_archivos = n_flag_archivos;
	}

	public Integer getN_flag_archivos() {
		return n_flag_archivos;
	}

	public void setN_flag_archivos(Integer n_flag_archivos) {
		this.n_flag_archivos = n_flag_archivos;
	}

	public String getV_dni() {
		return v_dni;
	}

	public void setV_dni(String v_dni) {
		this.v_dni = v_dni;
	}

	public String getV_ape_paterno() {
		return v_ape_paterno;
	}

	public void setV_ape_paterno(String v_ape_paterno) {
		this.v_ape_paterno = v_ape_paterno;
	}

	public String getV_ape_materno() {
		return v_ape_materno;
	}

	public void setV_ape_materno(String v_ape_materno) {
		this.v_ape_materno = v_ape_materno;
	}

	public String getV_nombres() {
		return v_nombres;
	}

	public void setV_nombres(String v_nombres) {
		this.v_nombres = v_nombres;
	}

	public String getV_fec_nac() {
		return v_fec_nac;
	}

	public void setV_fec_nac(String v_fec_nac) {
		this.v_fec_nac = v_fec_nac;
	}

	public String getV_direccion() {
		return v_direccion;
	}

	public void setV_direccion(String v_direccion) {
		this.v_direccion = v_direccion;
	}

	public String getV_fec_ingreso() {
		return v_fec_ingreso;
	}

	public void setV_fec_ingreso(String v_fec_ingreso) {
		this.v_fec_ingreso = v_fec_ingreso;
	}

	public String getV_cod_cargo() {
		return v_cod_cargo;
	}

	public void setV_cod_cargo(String v_cod_cargo) {
		this.v_cod_cargo = v_cod_cargo;
	}

	public String getV_cod_item() {
		return v_cod_item;
	}

	public void setV_cod_item(String v_cod_item) {
		this.v_cod_item = v_cod_item;
	}

	public String getV_cod_oficina() {
		return v_cod_oficina;
	}

	public void setV_cod_oficina(String v_cod_oficina) {
		this.v_cod_oficina = v_cod_oficina;
	}

	public String getV_telf_pers() {
		return v_telf_pers;
	}

	public void setV_telf_pers(String v_telf_pers) {
		this.v_telf_pers = v_telf_pers;
	}

	public String getV_telf_asig() {
		return v_telf_asig;
	}

	public void setV_telf_asig(String v_telf_asig) {
		this.v_telf_asig = v_telf_asig;
	}

	public String getV_correo() {
		return v_correo;
	}

	public void setV_correo(String v_correo) {
		this.v_correo = v_correo;
	}

	public String getV_cod_pers_ctrat() {
		return v_cod_pers_ctrat;
	}

	public void setV_cod_pers_ctrat(String v_cod_pers_ctrat) {
		this.v_cod_pers_ctrat = v_cod_pers_ctrat;
	}

	public String getV_cod_empresa() {
		return v_cod_empresa;
	}

	public void setV_cod_empresa(String v_cod_empresa) {
		this.v_cod_empresa = v_cod_empresa;
	}

	public String getV_usuario_reg() {
		return v_usuario_reg;
	}

	public void setV_usuario_reg(String v_usuario_reg) {
		this.v_usuario_reg = v_usuario_reg;
	}

}
