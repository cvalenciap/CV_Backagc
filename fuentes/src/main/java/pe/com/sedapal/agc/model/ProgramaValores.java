package pe.com.sedapal.agc.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProgramaValores {
    private Long n_id_programa_mensual;
    private Long n_id_ofic;
    private Long v_n_id_contrati;
    private String v_idacti;
    private String v_idsubacti_1;
    private String v_idsubacti_2;
    private BigDecimal n_val_prog_mensual;
    private Integer n_id_estado;
    private Date d_periodo;
    private String v_periodo;
    private BigDecimal n_val_prog_total;
    private BigDecimal n_cant_periodos;
    private String a_v_usucre;
    private String a_v_usumod;
    private Date a_d_feccre;
    private Date a_d_fecmod;
    private BigDecimal v_v_mes_inicio;
    private Integer v_v_anio_inicio;
    private Integer v_editable;
	public ProgramaValores() {
		super();
	}
	public ProgramaValores(Long n_id_programa_mensual, Long n_id_ofic, Long v_n_id_contrati, String v_idacti,
			String v_idsubacti_1, String v_idsubacti_2, BigDecimal n_val_prog_mensual, Integer n_id_estado, Date d_periodo, String v_periodo, BigDecimal n_val_prog_total,
			BigDecimal n_cant_periodos, String a_v_usucre, String a_v_usumod, Date a_d_feccre, Date a_d_fecmod,
			BigDecimal v_v_mes_inicio, Integer v_v_anio_inicio, Integer v_editable) {
		super();
		this.n_id_programa_mensual = n_id_programa_mensual;
		this.n_id_ofic = n_id_ofic;
		this.v_n_id_contrati = v_n_id_contrati;
		this.v_idacti = v_idacti;
		this.setV_idsubacti_1(v_idsubacti_1);
		this.setV_idsubacti_2(v_idsubacti_2);
		this.n_val_prog_mensual = n_val_prog_mensual;
		this.n_id_estado = n_id_estado;
		this.d_periodo = d_periodo;
		this.v_periodo = v_periodo;
		this.n_val_prog_total = n_val_prog_total;
		this.n_cant_periodos = n_cant_periodos;
		this.a_v_usucre = a_v_usucre;
		this.a_v_usumod = a_v_usumod;
		this.a_d_feccre = a_d_feccre;
		this.a_d_fecmod = a_d_fecmod;
		this.v_v_mes_inicio = v_v_mes_inicio;
		this.v_v_anio_inicio = v_v_anio_inicio;
		this.v_editable = v_editable;
	}
	public Long getN_id_programa_mensual() {
		return n_id_programa_mensual;
	}
	public void setN_id_programa_mensual(Long n_id_programa_mensual) {
		this.n_id_programa_mensual = n_id_programa_mensual;
	}
	public Long getN_id_ofic() {
		return n_id_ofic;
	}
	public void setN_id_ofic(Long n_id_ofic) {
		this.n_id_ofic = n_id_ofic;
	}
	public Long getV_n_id_contrati() {
		return v_n_id_contrati;
	}
	public void setV_n_id_contrati(Long v_n_id_contrati) {
		this.v_n_id_contrati = v_n_id_contrati;
	}
	public String getV_idacti() {
		return v_idacti;
	}
	public void setV_idacti(String v_idacti) {
		this.v_idacti = v_idacti;
	}
	public BigDecimal getN_val_prog_mensual() {
		return n_val_prog_mensual;
	}
	public void setN_val_prog_mensual(BigDecimal n_val_prog_mensual) {
		this.n_val_prog_mensual = n_val_prog_mensual;
	}
	public Integer getN_id_estado() {
		return n_id_estado;
	}
	public void setN_id_estado(Integer n_id_estado) {
		this.n_id_estado = n_id_estado;
	}
	public Date getD_periodo() {
		return d_periodo;
	}
	public void setD_periodo(Date d_periodo) {
		this.d_periodo = d_periodo;
	}
	public BigDecimal getN_val_prog_total() {
		return n_val_prog_total;
	}
	public void setN_val_prog_total(BigDecimal n_val_prog_total) {
		this.n_val_prog_total = n_val_prog_total;
	}
	public BigDecimal getN_cant_periodos() {
		return n_cant_periodos;
	}
	public void setN_cant_periodos(BigDecimal n_cant_periodos) {
		this.n_cant_periodos = n_cant_periodos;
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
	public String getV_periodo() {
		return v_periodo;
	}
	public void setV_periodo(String v_periodo) {
		this.v_periodo = v_periodo;
	}
	public Integer getV_editable() {
		return v_editable;
	}
	public void setV_editable(Integer v_editable) {
		this.v_editable = v_editable;
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

}
