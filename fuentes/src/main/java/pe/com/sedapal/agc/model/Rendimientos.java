package pe.com.sedapal.agc.model;

import java.math.BigDecimal;
import java.util.Date;

public class Rendimientos {
	private Long n_idrendimiento;
	private String v_idacti_seda;
	private String v_idacti_seda_desc;
	private String v_idacti;
	private String v_idacti_desc;
	private String v_ind_clte_espe;
	private String v_idsubacti_1;
	private String v_idsubacti_2;
	private String v_idsubacti_1_1;
	private String v_idsubacti_2_1;
	private Long n_numitem;
	private Integer n_id_estado;
	private String v_idcargo;
	private Long n_numcuad;
	private BigDecimal n_num_val_rend;
	private Long n_idofic;
	private Long n_idcontrati;
	private Date d_periodo;        
	private String a_v_usumod;
	private String a_v_usucre;
	private String a_d_fecmod;
	private String a_d_feccre;
	private BigDecimal v_n_cant_periodos;
	private BigDecimal v_v_mes_inicio;
	private Integer v_v_anio_inicio;
	private Integer v_editable;
	private String v_periodo;
	private Parametro item;
	private String v_uni_medida;
	private Long n_valor_trabajador;
	private Long n_valor_suministro;
	private String v_codigo_ub1;
	private String v_codigo_val2;
	private String v_codigo_val1;
	
	public Rendimientos() {
		super();
	}

	public Rendimientos(Long n_idrendimiento, String v_idacti_seda, String v_idacti_seda_desc, String v_idacti,
			String v_idacti_desc, String v_ind_clte_espe, String v_idsubacti_1, String v_idsubacti_2,
			String v_idsubacti_1_1, String v_idsubacti_2_1, Long n_numitem, Integer n_id_estado, String v_idcargo,
			Long n_numcuad, BigDecimal n_num_val_rend, Long n_idofic, Long n_idcontrati, Date d_periodo,
			String a_v_usumod, String a_v_usucre, String a_d_fecmod, String a_d_feccre, BigDecimal v_n_cant_periodos,
			BigDecimal v_v_mes_inicio, Integer v_v_anio_inicio, Integer v_editable, String v_periodo, Parametro item,
			String v_uni_medida, Long n_valor_trabajador, Long n_valor_suministro,
			String v_codigo_ub1, String v_codigo_val2, String v_codigo_val1) {
		super();
		this.n_idrendimiento = n_idrendimiento;
		this.v_idacti_seda = v_idacti_seda;
		this.v_idacti_seda_desc = v_idacti_seda_desc;
		this.v_idacti = v_idacti;
		this.v_idacti_desc = v_idacti_desc;
		this.v_ind_clte_espe = v_ind_clte_espe;
		this.v_idsubacti_1 = v_idsubacti_1;
		this.v_idsubacti_2 = v_idsubacti_2;
		this.v_idsubacti_1_1 = v_idsubacti_1_1;
		this.v_idsubacti_2_1 = v_idsubacti_2_1;
		this.n_numitem = n_numitem;
		this.n_id_estado = n_id_estado;
		this.v_idcargo = v_idcargo;
		this.n_numcuad = n_numcuad;
		this.n_num_val_rend = n_num_val_rend;
		this.n_idofic = n_idofic;
		this.n_idcontrati = n_idcontrati;
		this.d_periodo = d_periodo;
		this.a_v_usumod = a_v_usumod;
		this.a_v_usucre = a_v_usucre;
		this.a_d_fecmod = a_d_fecmod;
		this.a_d_feccre = a_d_feccre;
		this.v_n_cant_periodos = v_n_cant_periodos;
		this.v_v_mes_inicio = v_v_mes_inicio;
		this.v_v_anio_inicio = v_v_anio_inicio;
		this.v_editable = v_editable;
		this.v_periodo = v_periodo;
		this.item = item;
		this.v_uni_medida = v_uni_medida;
		this.n_valor_trabajador = n_valor_trabajador;
		this.n_valor_suministro = n_valor_suministro;
		this.v_codigo_ub1 = v_codigo_ub1;
		this.v_codigo_val2 = v_codigo_val2;
		this.v_codigo_val1 = v_codigo_val1;
	}

	public Parametro getItem() {
		return item;
	}

	public void setItem(Parametro item) {
		this.item = item;
	}

	public Long getN_idrendimiento() {
		return n_idrendimiento;
	}

	public void setN_idrendimiento(Long n_idrendimiento) {
		this.n_idrendimiento = n_idrendimiento;
	}

	public String getV_idacti_seda() {
		return v_idacti_seda;
	}

	public void setV_idacti_seda(String v_idacti_seda) {
		this.v_idacti_seda = v_idacti_seda;
	}

	public String getV_idacti() {
		return v_idacti;
	}

	public void setV_idacti(String v_idacti) {
		this.v_idacti = v_idacti;
	}

	public String getV_ind_clte_espe() {
		return v_ind_clte_espe;
	}

	public void setV_ind_clte_espe(String v_ind_clte_espe) {
		this.v_ind_clte_espe = v_ind_clte_espe;
	}

	public String getV_idsubacti_1() {
		return v_idsubacti_1;
	}

	public void setV_idsubacti_1(String v_idsubacti_1) {
		this.v_idsubacti_1 = v_idsubacti_1;
	}

	public String getV_idsubacti_2() {
		return v_idsubacti_2;
	}

	public void setV_idsubacti_2(String v_idsubacti_2) {
		this.v_idsubacti_2 = v_idsubacti_2;
	}

	public Long getN_numitem() {
		return n_numitem;
	}

	public void setN_numitem(Long n_numitem) {
		this.n_numitem = n_numitem;
	}

	public String getV_idcargo() {
		return v_idcargo;
	}

	public void setV_idcargo(String v_idcargo) {
		this.v_idcargo = v_idcargo;
	}

	public Long getN_numcuad() {
		return n_numcuad;
	}

	public void setN_numcuad(Long n_numcuad) {
		this.n_numcuad = n_numcuad;
	}

	public BigDecimal getN_num_val_rend() {
		return n_num_val_rend;
	}

	public void setN_num_val_rend(BigDecimal n_num_val_rend) {
		this.n_num_val_rend = n_num_val_rend;
	}

	public Integer getN_id_estado() {
		return n_id_estado;
	}

	public void setN_id_estado(Integer n_id_estado) {
		this.n_id_estado = n_id_estado;
	}

	public Long getN_idofic() {
		return n_idofic;
	}

	public void setN_idofic(Long n_idofic) {
		this.n_idofic = n_idofic;
	}

	public Long getN_idcontrati() {
		return n_idcontrati;
	}

	public void setN_idcontrati(Long n_idcontrati) {
		this.n_idcontrati = n_idcontrati;
	}

	public Date getD_periodo() {
		return d_periodo;
	}

	public void setD_periodo(Date d_periodo) {
		this.d_periodo = d_periodo;
	}

	public String getA_v_usumod() {
		return a_v_usumod;
	}

	public void setA_v_usumod(String a_v_usumod) {
		this.a_v_usumod = a_v_usumod;
	}

	public String getA_v_usucre() {
		return a_v_usucre;
	}

	public void setA_v_usucre(String a_v_usucre) {
		this.a_v_usucre = a_v_usucre;
	}

	public String getA_d_fecmod() {
		return a_d_fecmod;
	}

	public void setA_d_fecmod(String a_d_fecmod) {
		this.a_d_fecmod = a_d_fecmod;
	}

	public String getA_d_feccre() {
		return a_d_feccre;
	}

	public void setA_d_feccre(String a_d_feccre) {
		this.a_d_feccre = a_d_feccre;
	}

	public BigDecimal getV_v_mes_inicio() {
		return v_v_mes_inicio;
	}

	public void setV_v_mes_inicio(BigDecimal v_v_mes_inicio) {
		this.v_v_mes_inicio = v_v_mes_inicio;
	}

	public Integer getV_v_anio_inicio() {
		return v_v_anio_inicio;
	}

	public void setV_v_anio_inicio(Integer v_v_anio_inicio) {
		this.v_v_anio_inicio = v_v_anio_inicio;
	}

	public Integer getV_editable() {
		return v_editable;
	}

	public void setV_editable(Integer v_editable) {
		this.v_editable = v_editable;
	}

	public BigDecimal getV_n_cant_periodos() {
		return v_n_cant_periodos;
	}

	public void setV_n_cant_periodos(BigDecimal v_n_cant_periodos) {
		this.v_n_cant_periodos = v_n_cant_periodos;
	}

	public String getV_periodo() {
		return v_periodo;
	}

	public void setV_periodo(String v_periodo) {
		this.v_periodo = v_periodo;
	}

	public String getV_idacti_seda_desc() {
		return v_idacti_seda_desc;
	}

	public void setV_idacti_seda_desc(String v_idacti_seda_desc) {
		this.v_idacti_seda_desc = v_idacti_seda_desc;
	}

	public String getV_idacti_desc() {
		return v_idacti_desc;
	}

	public void setV_idacti_desc(String v_idacti_desc) {
		this.v_idacti_desc = v_idacti_desc;
	}

	public String getV_idsubacti_1_1() {
		return v_idsubacti_1_1;
	}

	public void setV_idsubacti_1_1(String v_idsubacti_1_1) {
		this.v_idsubacti_1_1 = v_idsubacti_1_1;
	}

	public String getV_idsubacti_2_1() {
		return v_idsubacti_2_1;
	}

	public void setV_idsubacti_2_1(String v_idsubacti_2_1) {
		this.v_idsubacti_2_1 = v_idsubacti_2_1;
	}

	public String getV_uni_medida() {
		return v_uni_medida;
	}

	public void setV_uni_medida(String v_uni_medida) {
		this.v_uni_medida = v_uni_medida;
	}

	public Long getN_valor_trabajador() {
		return n_valor_trabajador;
	}

	public void setN_valor_trabajador(Long n_valor_trabajador) {
		this.n_valor_trabajador = n_valor_trabajador;
	}

	public Long getN_valor_suministro() {
		return n_valor_suministro;
	}

	public void setN_valor_suministro(Long n_valor_suministro) {
		this.n_valor_suministro = n_valor_suministro;
	}

	public String getV_codigo_ub1() {
		return v_codigo_ub1;
	}

	public void setV_codigo_ub1(String v_codigo_ub1) {
		this.v_codigo_ub1 = v_codigo_ub1;
	}

	public String getV_codigo_val2() {
		return v_codigo_val2;
	}

	public void setV_codigo_val2(String v_codigo_val2) {
		this.v_codigo_val2 = v_codigo_val2;
	}

	public String getV_codigo_val1() {
		return v_codigo_val1;
	}

	public void setV_codigo_val1(String v_codigo_val1) {
		this.v_codigo_val1 = v_codigo_val1;
	}
	
}
