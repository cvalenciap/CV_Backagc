package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecNotificaciones {
	private String v_idcarga;
	private String v_descofic; 
	private BigDecimal carga_entrega;
	private BigDecimal numero_primera_visita;
	private BigDecimal segunda_visita_entrega_personalizada;
	private BigDecimal segunda_visita_bajo_puerta;
	private BigDecimal otro;
	private BigDecimal pendientes;
	private BigDecimal porc_numero_primera_visita;
	private BigDecimal porc_segunda_visita_entrega_personalizada;
	private BigDecimal porc_segunda_visita_bajo_puerta;
	private BigDecimal porc_otro;
	private BigDecimal porc_pendientes;
    private String femision;
	
	public RepoEfecNotificaciones() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoEfecNotificaciones(String v_idcarga, String v_descofic, BigDecimal carga_entrega,
			BigDecimal numero_primera_visita, BigDecimal segunda_visita_entrega_personalizada,
			BigDecimal segunda_visita_bajo_puerta, BigDecimal otro, BigDecimal pendientes,
			BigDecimal porc_numero_primera_visita, BigDecimal porc_segunda_visita_entrega_personalizada,
			BigDecimal porc_segunda_visita_bajo_puerta, BigDecimal porc_otro, BigDecimal porc_pendientes,
			String femision) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.carga_entrega = carga_entrega;
		this.numero_primera_visita = numero_primera_visita;
		this.segunda_visita_entrega_personalizada = segunda_visita_entrega_personalizada;
		this.segunda_visita_bajo_puerta = segunda_visita_bajo_puerta;
		this.otro = otro;
		this.pendientes = pendientes;
		this.porc_numero_primera_visita = porc_numero_primera_visita;
		this.porc_segunda_visita_entrega_personalizada = porc_segunda_visita_entrega_personalizada;
		this.porc_segunda_visita_bajo_puerta = porc_segunda_visita_bajo_puerta;
		this.porc_otro = porc_otro;
		this.porc_pendientes = porc_pendientes;
		this.femision = femision;
	}
	
	public String getV_idcarga() {
		return v_idcarga;
	}

	public void setV_idcarga(String v_idcarga) {
		this.v_idcarga = v_idcarga;
	}

	public String getV_descofic() {
		return v_descofic;
	}

	public void setV_descofic(String v_descofic) {
		this.v_descofic = v_descofic;
	}

	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}

	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}

	public BigDecimal getNumero_primera_visita() {
		return numero_primera_visita;
	}

	public void setNumero_primera_visita(BigDecimal numero_primera_visita) {
		this.numero_primera_visita = numero_primera_visita;
	}

	public BigDecimal getSegunda_visita_entrega_personalizada() {
		return segunda_visita_entrega_personalizada;
	}

	public void setSegunda_visita_entrega_personalizada(BigDecimal segunda_visita_entrega_personalizada) {
		this.segunda_visita_entrega_personalizada = segunda_visita_entrega_personalizada;
	}

	public BigDecimal getSegunda_visita_bajo_puerta() {
		return segunda_visita_bajo_puerta;
	}

	public void setSegunda_visita_bajo_puerta(BigDecimal segunda_visita_bajo_puerta) {
		this.segunda_visita_bajo_puerta = segunda_visita_bajo_puerta;
	}

	public BigDecimal getOtro() {
		return otro;
	}

	public void setOtro(BigDecimal otro) {
		this.otro = otro;
	}

	public BigDecimal getPendientes() {
		return pendientes;
	}

	public void setPendientes(BigDecimal pendientes) {
		this.pendientes = pendientes;
	}

	public BigDecimal getPorc_numero_primera_visita() {
		return porc_numero_primera_visita;
	}

	public void setPorc_numero_primera_visita(BigDecimal porc_numero_primera_visita) {
		this.porc_numero_primera_visita = porc_numero_primera_visita;
	}

	public BigDecimal getPorc_segunda_visita_entrega_personalizada() {
		return porc_segunda_visita_entrega_personalizada;
	}

	public void setPorc_segunda_visita_entrega_personalizada(BigDecimal porc_segunda_visita_entrega_personalizada) {
		this.porc_segunda_visita_entrega_personalizada = porc_segunda_visita_entrega_personalizada;
	}

	public BigDecimal getPorc_segunda_visita_bajo_puerta() {
		return porc_segunda_visita_bajo_puerta;
	}

	public void setPorc_segunda_visita_bajo_puerta(BigDecimal porc_segunda_visita_bajo_puerta) {
		this.porc_segunda_visita_bajo_puerta = porc_segunda_visita_bajo_puerta;
	}

	public BigDecimal getPorc_otro() {
		return porc_otro;
	}

	public void setPorc_otro(BigDecimal porc_otro) {
		this.porc_otro = porc_otro;
	}

	public BigDecimal getPorc_pendientes() {
		return porc_pendientes;
	}

	public void setPorc_pendientes(BigDecimal porc_pendientes) {
		this.porc_pendientes = porc_pendientes;
	}

	public String getFemision() {
		return femision;
	}

	public void setFemision(String femision) {
		this.femision = femision;
	}



	
}
