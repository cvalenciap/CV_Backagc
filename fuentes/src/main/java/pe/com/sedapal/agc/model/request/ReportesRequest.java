package pe.com.sedapal.agc.model.request;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportesRequest {
	private Long n_idofic;
    private Long n_idcontrati;
    private String v_idacti;
    private String v_subacti;
    private String v_perfil;
    private String v_periodo;
    private String v_usuario;
    private String lstUsuarios;
    private String v_ciclo;
    private BigDecimal V_N_VAL_PROG_TOTAL;
	private BigDecimal V_N_VAL_PROG_MENSUAL;
	private Integer N_CANT_PERIODOS;
	private String v_periodo_inicio;
	private String v_periodo_final;
	private String v_codigo_lector;
	private String v_tipo_inspe;
	private Long v_n_item;
	private Date d_femision;
	private Long v_n_idempr;
	

	public ReportesRequest() {
		super();
	}


	public ReportesRequest(Long n_idofic, Long n_idcontrati, String v_idacti, String v_subacti, String v_perfil,
			String v_periodo, String v_usuario, String lstUsuarios, String v_ciclo, BigDecimal v_N_VAL_PROG_TOTAL,
			BigDecimal v_N_VAL_PROG_MENSUAL, Integer n_CANT_PERIODOS, String v_periodo_inicio, String v_periodo_final,
			String v_codigo_lector, String v_tipo_inspe, Long v_n_item, Date d_femision, Long v_n_idempr) {
		super();
		this.n_idofic = n_idofic;
		this.n_idcontrati = n_idcontrati;
		this.v_idacti = v_idacti;
		this.v_subacti = v_subacti;
		this.v_perfil = v_perfil;
		this.v_periodo = v_periodo;
		this.v_usuario = v_usuario;
		this.lstUsuarios = lstUsuarios;
		this.v_ciclo = v_ciclo;
		V_N_VAL_PROG_TOTAL = v_N_VAL_PROG_TOTAL;
		V_N_VAL_PROG_MENSUAL = v_N_VAL_PROG_MENSUAL;
		N_CANT_PERIODOS = n_CANT_PERIODOS;
		this.v_periodo_inicio = v_periodo_inicio;
		this.v_periodo_final = v_periodo_final;
		this.v_codigo_lector = v_codigo_lector;
		this.v_tipo_inspe = v_tipo_inspe;
		this.v_n_item = v_n_item;
		this.d_femision = d_femision;
		this.v_n_idempr = v_n_idempr;
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


	public String getV_idacti() {
		return v_idacti;
	}


	public void setV_idacti(String v_idacti) {
		this.v_idacti = v_idacti;
	}


	public String getV_subacti() {
		return v_subacti;
	}


	public void setV_subacti(String v_subacti) {
		this.v_subacti = v_subacti;
	}


	public String getV_perfil() {
		return v_perfil;
	}


	public void setV_perfil(String v_perfil) {
		this.v_perfil = v_perfil;
	}


	public String getV_periodo() {
		return v_periodo;
	}


	public void setV_periodo(String v_periodo) {
		this.v_periodo = v_periodo;
	}


	public String getV_usuario() {
		return v_usuario;
	}


	public void setV_usuario(String v_usuario) {
		this.v_usuario = v_usuario;
	}


	public String getLstUsuarios() {
		return lstUsuarios;
	}


	public void setLstUsuarios(String lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}


	public String getV_ciclo() {
		return v_ciclo;
	}


	public void setV_ciclo(String v_ciclo) {
		this.v_ciclo = v_ciclo;
	}


	public BigDecimal getV_N_VAL_PROG_TOTAL() {
		return V_N_VAL_PROG_TOTAL;
	}


	public void setV_N_VAL_PROG_TOTAL(BigDecimal v_N_VAL_PROG_TOTAL) {
		V_N_VAL_PROG_TOTAL = v_N_VAL_PROG_TOTAL;
	}


	public BigDecimal getV_N_VAL_PROG_MENSUAL() {
		return V_N_VAL_PROG_MENSUAL;
	}


	public void setV_N_VAL_PROG_MENSUAL(BigDecimal v_N_VAL_PROG_MENSUAL) {
		V_N_VAL_PROG_MENSUAL = v_N_VAL_PROG_MENSUAL;
	}


	public Integer getN_CANT_PERIODOS() {
		return N_CANT_PERIODOS;
	}


	public void setN_CANT_PERIODOS(Integer n_CANT_PERIODOS) {
		N_CANT_PERIODOS = n_CANT_PERIODOS;
	}


	public String getV_periodo_inicio() {
		return v_periodo_inicio;
	}


	public void setV_periodo_inicio(String v_periodo_inicio) {
		this.v_periodo_inicio = v_periodo_inicio;
	}


	public String getV_periodo_final() {
		return v_periodo_final;
	}


	public void setV_periodo_final(String v_periodo_final) {
		this.v_periodo_final = v_periodo_final;
	}


	public String getV_codigo_lector() {
		return v_codigo_lector;
	}


	public void setV_codigo_lector(String v_codigo_lector) {
		this.v_codigo_lector = v_codigo_lector;
	}


	public String getV_tipo_inspe() {
		return v_tipo_inspe;
	}


	public void setV_tipo_inspe(String v_tipo_inspe) {
		this.v_tipo_inspe = v_tipo_inspe;
	}


	public Long getV_n_item() {
		return v_n_item;
	}


	public void setV_n_item(Long v_n_item) {
		this.v_n_item = v_n_item;
	}


	public String getD_femision(Date d_femision) {
		//Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(d_femision);  
		System.out.println(strDate);
		return strDate;
	}


	public void setD_femision(Date d_femision) {
		this.d_femision = d_femision;
	}
	
	public String getD_femision() {
		return getD_femision(d_femision);
	}


	public Long getV_n_idempr() {
		return v_n_idempr;
	}


	public void setV_n_idempr(Long v_n_idempr) {
		this.v_n_idempr = v_n_idempr;
	}

	
	
	
}
