package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoCumpActiInsp {

	private String v_idcarga;
	private String v_descofic;
	private String v_nombreempr;
	private String v_fecha;
	private String cod_inspector;
	private String inspector;
	private BigDecimal rendimiento;
	private BigDecimal carga_entrega;
	private BigDecimal porc_cumplimiento;
	private BigDecimal reclamo_unico;
	private BigDecimal reclamo_multi;
	private BigDecimal internas_unico;
	private BigDecimal internas_multi;
	private BigDecimal geofono;
	private BigDecimal externa_anomalia;
	private BigDecimal externa_anomalia_boroscopio;
	private BigDecimal empad;
	private BigDecimal catastral;
	
	public RepoCumpActiInsp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepoCumpActiInsp(String v_idcarga, String v_descofic, String v_nombreempr, String v_fecha,
			String cod_inspector, String inspector, BigDecimal rendimiento, BigDecimal carga_entrega,
			BigDecimal porc_cumplimiento, BigDecimal reclamo_unico, BigDecimal reclamo_multi, BigDecimal internas_unico,
			BigDecimal internas_multi, BigDecimal geofono, BigDecimal externa_anomalia,
			BigDecimal externa_anomalia_boroscopio, BigDecimal empad, BigDecimal catastral) {
		super();
		this.v_idcarga = v_idcarga;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.v_fecha = v_fecha;
		this.cod_inspector = cod_inspector;
		this.inspector = inspector;
		this.rendimiento = rendimiento;
		this.carga_entrega = carga_entrega;
		this.porc_cumplimiento = porc_cumplimiento;
		this.reclamo_unico = reclamo_unico;
		this.reclamo_multi = reclamo_multi;
		this.internas_unico = internas_unico;
		this.internas_multi = internas_multi;
		this.geofono = geofono;
		this.externa_anomalia = externa_anomalia;
		this.externa_anomalia_boroscopio = externa_anomalia_boroscopio;
		this.empad = empad;
		this.catastral = catastral;
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

	public String getV_fecha() {
		return v_fecha;
	}

	public void setV_fecha(String v_fecha) {
		this.v_fecha = v_fecha;
	}

	public String getCod_inspector() {
		return cod_inspector;
	}

	public void setCod_inspector(String cod_inspector) {
		this.cod_inspector = cod_inspector;
	}

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public BigDecimal getRendimiento() {
		return rendimiento;
	}

	public void setRendimiento(BigDecimal rendimiento) {
		this.rendimiento = rendimiento;
	}

	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}

	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}

	public BigDecimal getPorc_cumplimiento() {
		return porc_cumplimiento;
	}

	public void setPorc_cumplimiento(BigDecimal porc_cumplimiento) {
		this.porc_cumplimiento = porc_cumplimiento;
	}

	public BigDecimal getReclamo_unico() {
		return reclamo_unico;
	}

	public void setReclamo_unico(BigDecimal reclamo_unico) {
		this.reclamo_unico = reclamo_unico;
	}

	public BigDecimal getReclamo_multi() {
		return reclamo_multi;
	}

	public void setReclamo_multi(BigDecimal reclamo_multi) {
		this.reclamo_multi = reclamo_multi;
	}

	public BigDecimal getInternas_unico() {
		return internas_unico;
	}

	public void setInternas_unico(BigDecimal internas_unico) {
		this.internas_unico = internas_unico;
	}

	public BigDecimal getInternas_multi() {
		return internas_multi;
	}

	public void setInternas_multi(BigDecimal internas_multi) {
		this.internas_multi = internas_multi;
	}

	public BigDecimal getGeofono() {
		return geofono;
	}

	public void setGeofono(BigDecimal geofono) {
		this.geofono = geofono;
	}

	public BigDecimal getExterna_anomalia() {
		return externa_anomalia;
	}

	public void setExterna_anomalia(BigDecimal externa_anomalia) {
		this.externa_anomalia = externa_anomalia;
	}

	public BigDecimal getExterna_anomalia_boroscopio() {
		return externa_anomalia_boroscopio;
	}

	public void setExterna_anomalia_boroscopio(BigDecimal externa_anomalia_boroscopio) {
		this.externa_anomalia_boroscopio = externa_anomalia_boroscopio;
	}

	public BigDecimal getEmpad() {
		return empad;
	}

	public void setEmpad(BigDecimal empad) {
		this.empad = empad;
	}

	public BigDecimal getCatastral() {
		return catastral;
	}

	public void setCatastral(BigDecimal catastral) {
		this.catastral = catastral;
	}
	
	
}

