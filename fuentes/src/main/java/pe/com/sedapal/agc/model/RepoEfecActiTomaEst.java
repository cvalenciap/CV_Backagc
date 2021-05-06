package pe.com.sedapal.agc.model;

import java.math.BigDecimal;

public class RepoEfecActiTomaEst {
	private String v_idcarga;
	private String ciclo;
	private String v_descofic; 
    private String v_nombreempr;
	private String periodo; 
	private BigDecimal carga_entrega;
	private BigDecimal lecturas;
	private BigDecimal incidencias;
	private BigDecimal supervisadas;
	private BigDecimal porc_lecturas;
	private BigDecimal porc_incidencias;
	private BigDecimal porc_supervisadas;
	private BigDecimal con_dispositivo_movil;
	private BigDecimal con_hoja_ruta;

	public RepoEfecActiTomaEst(String v_idcarga, String ciclo, String v_descofic, String v_nombreempr, String periodo,
			BigDecimal carga_entrega, BigDecimal lecturas, BigDecimal incidencias, BigDecimal supervisadas,
			BigDecimal porc_lecturas, BigDecimal porc_incidencias, BigDecimal porc_supervisadas,
			BigDecimal con_dispositivo_movil, BigDecimal con_hoja_ruta) {
		super();
		this.v_idcarga = v_idcarga;
		this.ciclo = ciclo;
		this.v_descofic = v_descofic;
		this.v_nombreempr = v_nombreempr;
		this.periodo = periodo;
		this.carga_entrega = carga_entrega;
		this.lecturas = lecturas;
		this.incidencias = incidencias;
		this.supervisadas = supervisadas;
		this.porc_lecturas = porc_lecturas;
		this.porc_incidencias = porc_incidencias;
		this.porc_supervisadas = porc_supervisadas;
		this.con_dispositivo_movil = con_dispositivo_movil;
		this.con_hoja_ruta = con_hoja_ruta;
	}

	public RepoEfecActiTomaEst() {
		super();
	}

	public String getV_idcarga() {
		return v_idcarga;
	}

	public void setV_idcarga(String v_idcarga) {
		this.v_idcarga = v_idcarga;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
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

	public BigDecimal getCarga_entrega() {
		return carga_entrega;
	}

	public void setCarga_entrega(BigDecimal carga_entrega) {
		this.carga_entrega = carga_entrega;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getLecturas() {
		return lecturas;
	}

	public void setLecturas(BigDecimal lecturas) {
		this.lecturas = lecturas;
	}

	public BigDecimal getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(BigDecimal incidencias) {
		this.incidencias = incidencias;
	}

	public BigDecimal getSupervisadas() {
		return supervisadas;
	}

	public void setSupervisadas(BigDecimal supervisadas) {
		this.supervisadas = supervisadas;
	}

	public BigDecimal getPorc_incidencias() {
		return porc_incidencias;
	}

	public void setPorc_incidencias(BigDecimal porc_incidencias) {
		this.porc_incidencias = porc_incidencias;
	}

	public BigDecimal getPorc_lecturas() {
		return porc_lecturas;
	}

	public void setPorc_lecturas(BigDecimal porc_lecturas) {
		this.porc_lecturas = porc_lecturas;
	}

	public BigDecimal getPorc_supervisadas() {
		return porc_supervisadas;
	}

	public void setPorc_supervisadas(BigDecimal porc_supervisadas) {
		this.porc_supervisadas = porc_supervisadas;
	}

	public BigDecimal getCon_dispositivo_movil() {
		return con_dispositivo_movil;
	}

	public void setCon_dispositivo_movil(BigDecimal con_dispositivo_movil) {
		this.con_dispositivo_movil = con_dispositivo_movil;
	}

	public BigDecimal getCon_hoja_ruta() {
		return con_hoja_ruta;
	}

	public void setCon_hoja_ruta(BigDecimal con_hoja_ruta) {
		this.con_hoja_ruta = con_hoja_ruta;
	}


}
