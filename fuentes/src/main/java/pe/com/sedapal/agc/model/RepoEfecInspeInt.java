package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecInspeInt {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_tipo_inspe;
	private String v_fecha;
	private BigDecimal carga_entrega;
	private BigDecimal con_ingreso;
	private BigDecimal inspe_parcial;
	private BigDecimal usuario_ausente;
	private BigDecimal oposicion;
	private BigDecimal imposibilidad;
	private BigDecimal pendiente;
	private BigDecimal porc_con_ingreso;
	private BigDecimal porc_inspe_parcial;
	private BigDecimal porc_usuario_ausente;
	private BigDecimal porc_oposicion;
	private BigDecimal porc_imposibilidad;
	private BigDecimal porc_pendiente;
	

	public RepoEfecInspeInt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoEfecInspeInt(String v_idcarga, String v_descofic, String v_nombreempr, String v_tipo_inspe,
			String v_fecha, BigDecimal carga_entrega, BigDecimal con_ingreso, BigDecimal inspe_parcial,
			BigDecimal usuario_ausente, BigDecimal oposicion, BigDecimal imposibilidad, BigDecimal pendiente,
			BigDecimal porc_con_ingreso, BigDecimal porc_inspe_parcial, BigDecimal porc_usuario_ausente,
			BigDecimal porc_oposicion, BigDecimal porc_imposibilidad, BigDecimal porc_pendiente) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_tipo_inspe = v_tipo_inspe;
		this.v_fecha = v_fecha;
		this.carga_entrega = carga_entrega;
		this.con_ingreso = con_ingreso;
		this.inspe_parcial = inspe_parcial;
		this.usuario_ausente = usuario_ausente;
		this.oposicion = oposicion;
		this.imposibilidad = imposibilidad;
		this.pendiente = pendiente;
		this.porc_con_ingreso = porc_con_ingreso;
		this.porc_inspe_parcial = porc_inspe_parcial;
		this.porc_usuario_ausente = porc_usuario_ausente;
		this.porc_oposicion = porc_oposicion;
		this.porc_imposibilidad = porc_imposibilidad;
		this.porc_pendiente = porc_pendiente;
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


	public String getV_nombreempr() {
		return v_nombreempr;
	}


	public void setV_nombreempr(String v_nombreempr) {
		this.v_nombreempr = v_nombreempr;
	}


	public String getV_tipo_inspe() {
		return v_tipo_inspe;
	}


	public void setV_tipo_inspe(String v_tipo_inspe) {
		this.v_tipo_inspe = v_tipo_inspe;
	}


	public String getV_fecha() {
		return v_fecha;
	}


	public void setV_fecha(String v_fecha) {
		this.v_fecha = v_fecha;
	}


	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}


	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}


	public BigDecimal getCon_ingreso() {
		return con_ingreso;
	}


	public void setCon_ingreso(BigDecimal con_ingreso) {
		this.con_ingreso = con_ingreso;
	}


	public BigDecimal getInspe_parcial() {
		return inspe_parcial;
	}


	public void setInspe_parcial(BigDecimal inspe_parcial) {
		this.inspe_parcial = inspe_parcial;
	}


	public BigDecimal getUsuario_ausente() {
		return usuario_ausente;
	}


	public void setUsuario_ausente(BigDecimal usuario_ausente) {
		this.usuario_ausente = usuario_ausente;
	}


	public BigDecimal getOposicion() {
		return oposicion;
	}


	public void setOposicion(BigDecimal oposicion) {
		this.oposicion = oposicion;
	}


	public BigDecimal getImposibilidad() {
		return imposibilidad;
	}


	public void setImposibilidad(BigDecimal imposibilidad) {
		this.imposibilidad = imposibilidad;
	}


	public BigDecimal getPendiente() {
		return pendiente;
	}


	public void setPendiente(BigDecimal pendiente) {
		this.pendiente = pendiente;
	}


	public BigDecimal getPorc_con_ingreso() {
		return porc_con_ingreso;
	}


	public void setPorc_con_ingreso(BigDecimal porc_con_ingreso) {
		this.porc_con_ingreso = porc_con_ingreso;
	}


	public BigDecimal getPorc_inspe_parcial() {
		return porc_inspe_parcial;
	}


	public void setPorc_inspe_parcial(BigDecimal porc_inspe_parcial) {
		this.porc_inspe_parcial = porc_inspe_parcial;
	}


	public BigDecimal getPorc_usuario_ausente() {
		return porc_usuario_ausente;
	}


	public void setPorc_usuario_ausente(BigDecimal porc_usuario_ausente) {
		this.porc_usuario_ausente = porc_usuario_ausente;
	}


	public BigDecimal getPorc_oposicion() {
		return porc_oposicion;
	}


	public void setPorc_oposicion(BigDecimal porc_oposicion) {
		this.porc_oposicion = porc_oposicion;
	}


	public BigDecimal getPorc_imposibilidad() {
		return porc_imposibilidad;
	}


	public void setPorc_imposibilidad(BigDecimal porc_imposibilidad) {
		this.porc_imposibilidad = porc_imposibilidad;
	}


	public BigDecimal getPorc_pendiente() {
		return porc_pendiente;
	}


	public void setPorc_pendiente(BigDecimal porc_pendiente) {
		this.porc_pendiente = porc_pendiente;
	}

	
}
