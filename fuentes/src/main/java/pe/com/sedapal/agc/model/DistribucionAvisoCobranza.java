package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.lang.annotation.Repeatable;
import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistribucionAvisoCobranza {

	@NotNull(message = "1001-codigoRegistro")
	@Max(value = 9999999999L, message = "1002-codigoRegistro, 10")
	private Integer codigoRegistro;
	private String codigoCarga;
	private Integer oficinaComercial;
	private Double secuenciaTrabajo;
	private Integer rutaEstablecida;
	private Integer campItinerario;
	private Integer campSuministro;
	private Integer secuenciaRecibo;
	private String nroMedidor;
	private String calle;
	private Integer nroPuerta;
	private String duplicador;
	private String cgv;
	private Integer aol;
	private String cusp;
	private String representaMunicipio;
	private String representaLocalidad;
	private String referenciaDireccion;
	private String indicadorFueraRuta;
	private Integer ciclo;

	@NotNull(message = "1001-codigoDistribuidor")
	@Size(max = 8,min=1, message = "1004-codigoDistribuidor,8")	
	private String codigoDistribuidor;
	private String codigoIncidecia;

	@NotNull(message = "1001-fechaDistribucion")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaDistribucion;

	@NotNull(message = "1001-horaDistribucion")
	@Max(value = 9999, message = "1002-codigoRegistro, 4")
	private Integer horaDistribucion;

	@NotNull(message = "1001-tipoEntrada")
	@Size(max = 4,min=1, message = "1004-tipoEntrada,4")
	private String tipoEntrada;

	@NotNull(message = "1001-imposibilidad")
	@Size(max = 8,min=1, message = "1004-imposibilidad,8")	
	private String imposibilidad;
	
	public DistribucionAvisoCobranza() {
		super();
	}
	
	public DistribucionAvisoCobranza(Integer codigoRegistro, String codigoCarga, Integer oficinaComercial,
									 Double secuenciaTrabajo, Integer rutaEstablecida, Integer campItinerario, Integer campSuministro,
									 Integer secuenciaRecibo, String nroMedidor, String calle, Integer nroPuerta, String duplicador, String cgv,
									 Integer aol, String cusp, String representaMunicipio, String representaLocalidad,
									 String referenciaDireccion, String indicadorFueraRuta, Integer ciclo, String codigoDistribuidor,
									 String codigoIncidecia, Date fechaDistribucion, Integer horaDistribucion, String tipoEntrada, String imposibilidad) {
		super();
		this.codigoRegistro = codigoRegistro;
		this.codigoCarga = codigoCarga;
		this.oficinaComercial = oficinaComercial;
		this.secuenciaTrabajo = secuenciaTrabajo;
		this.rutaEstablecida = rutaEstablecida;
		this.campItinerario = campItinerario;
		this.campSuministro = campSuministro;
		this.secuenciaRecibo = secuenciaRecibo;
		this.nroMedidor = nroMedidor;
		this.calle = calle;
		this.nroPuerta = nroPuerta;
		this.duplicador = duplicador;
		this.cgv = cgv;
		this.aol = aol;
		this.cusp = cusp;
		this.representaMunicipio = representaMunicipio;
		this.representaLocalidad = representaLocalidad;
		this.referenciaDireccion = referenciaDireccion;
		this.indicadorFueraRuta = indicadorFueraRuta;
		this.ciclo = ciclo;
		this.codigoDistribuidor = codigoDistribuidor;
		this.codigoIncidecia = codigoIncidecia;
		this.fechaDistribucion = fechaDistribucion;
		this.horaDistribucion = horaDistribucion;
		this.tipoEntrada = tipoEntrada;
		this.imposibilidad = imposibilidad;
	}
	
	public Integer getCodigoRegistro() {
		return codigoRegistro;
	}
	public void setCodigoRegistro(Integer codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
	}
	public String getCodigoCarga() {
		return codigoCarga;
	}
	public void setCodigoCarga(String codigoCarga) {
		this.codigoCarga = codigoCarga;
	}
	public Integer getOficinaComercial() {
		return oficinaComercial;
	}
	public void setOficinaComercial(Integer oficinaComercial) {
		this.oficinaComercial = oficinaComercial;
	}
	public Double getSecuenciaTrabajo() {
		return secuenciaTrabajo;
	}
	public void setSecuenciaTrabajo(Double secuenciaTrabajo) {
		this.secuenciaTrabajo = secuenciaTrabajo;
	}
	public Integer getRutaEstablecida() {
		return rutaEstablecida;
	}
	public void setRutaEstablecida(Integer rutaEstablecida) {
		this.rutaEstablecida = rutaEstablecida;
	}
	public Integer getCampItinerario() {
		return campItinerario;
	}
	public void setCampItinerario(Integer campItinerario) {
		this.campItinerario = campItinerario;
	}
	public Integer getCampSuministro() {
		return campSuministro;
	}
	public void setCampSuministro(Integer campSuministro) {
		this.campSuministro = campSuministro;
	}
	public Integer getSecuenciaRecibo() {
		return secuenciaRecibo;
	}
	public void setSecuenciaRecibo(Integer secuenciaRecibo) {
		this.secuenciaRecibo = secuenciaRecibo;
	}
	public String getNroMedidor() {
		return nroMedidor;
	}
	public void setNroMedidor(String nroMedidor) {
		this.nroMedidor = nroMedidor;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Integer getNroPuerta() {
		return nroPuerta;
	}
	public void setNroPuerta(Integer nroPuerta) {
		this.nroPuerta = nroPuerta;
	}
	public String getDuplicador() {
		return duplicador;
	}
	public void setDuplicador(String duplicador) {
		this.duplicador = duplicador;
	}
	public String getCgv() {
		return cgv;
	}
	public void setCgv(String cgv) {
		this.cgv = cgv;
	}
	public Integer getAol() {
		return aol;
	}
	public void setAol(Integer aol) {
		this.aol = aol;
	}
	public String getCusp() {
		return cusp;
	}
	public void setCusp(String cusp) {
		this.cusp = cusp;
	}
	public String getRepresentaMunicipio() {
		return representaMunicipio;
	}
	public void setRepresentaMunicipio(String representaMunicipio) {
		this.representaMunicipio = representaMunicipio;
	}
	public String getRepresentaLocalidad() {
		return representaLocalidad;
	}
	public void setRepresentaLocalidad(String representaLocalidad) {
		this.representaLocalidad = representaLocalidad;
	}
	public String getReferenciaDireccion() {
		return referenciaDireccion;
	}
	public void setReferenciaDireccion(String referenciaDireccion) {
		this.referenciaDireccion = referenciaDireccion;
	}
	public String getIndicadorFueraRuta() {
		return indicadorFueraRuta;
	}
	public void setIndicadorFueraRuta(String indicadorFueraRuta) {
		this.indicadorFueraRuta = indicadorFueraRuta;
	}
	public Integer getCiclo() {
		return ciclo;
	}
	public void setCiclo(Integer ciclo) {
		this.ciclo = ciclo;
	}
	public String getCodigoDistribuidor() {
		return codigoDistribuidor;
	}
	public void setCodigoDistribuidor(String codigoDistribuidor) {
		this.codigoDistribuidor = codigoDistribuidor;
	}
	public String getCodigoIncidecia() {
		return codigoIncidecia;
	}
	public void setCodigoIncidecia(String codigoIncidecia) {
		this.codigoIncidecia = codigoIncidecia;
	}
	public Date getFechaDistribucion() {
		return fechaDistribucion;
	}
	public void setFechaDistribucion(Date fechaDistribucion) {
		this.fechaDistribucion = fechaDistribucion;
	}
	public Integer getHoraDistribucion() {
		return horaDistribucion;
	}
	public void setHoraDistribucion(Integer horaDistribucion) {
		this.horaDistribucion = horaDistribucion;
	}
	public String getTipoEntrada() {
		return tipoEntrada;
	}
	public void setTipoEntrada(String tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}
	public String getImposibilidad() {
		return imposibilidad;
	}
	public void setImposibilidad(String imposibilidad) {
		this.imposibilidad = imposibilidad;
	}
	

}
