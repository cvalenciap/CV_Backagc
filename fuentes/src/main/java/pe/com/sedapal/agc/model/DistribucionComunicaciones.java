package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistribucionComunicaciones {
	@NotNull(message = "1001-codigoRegistro")
	@Max(value = 9999999999L, message = "1002-codigoRegistro, 10")
	private Integer codigoRegistro;
	private String codigoCarga;
	private String codigoOficinaComercial;
	private String nroIdentCarga;
	private String fechaEntraga;
	private String nroSuministro;
	private String corrCedulaNotificacion;
	private String fechaEmisionDocumento;
	private String apellidoPatDestinatario;
	private String apellidoMatDestinatario;
	private String nombresDestinatario;
	private String tipoDocumentoDestinatario;
	private String nroDocumento;
	private String razonSocial;
	private String calle;
	private String nro;
	private String piso;
	private String manzana;
	private String lote;
	private String urb;
	private String provincia;
	private String distrito;
	private String nroReclamoOpen;
	private String tipoReclamo;
	private String codigoTipoDocu;
	private String nroDocumentoNotificar;

	@NotNull(message = "1001-nroVisitasNotificador")
	@Size(max = 1, message = "1002-nroVisitasNotificador,1")
	private String nroVisitasNotificador;

	@NotNull(message = "1001-fechaVisita1")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaVisita1;

	@NotNull(message = "1001-horaVisita1")
	@Size(max = 20, message = "1002-horaVisita1,20")
	private String horaVisita1;

	@NotNull(message = "1001-fechaVisita2")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaVisita2;

	@NotNull(message = "1001-horaVisita2")
	@Size(max = 20, message = "1002-horaVisita2,20")
	private String horaVisita2;

	@NotNull(message = "1001-nombreNotificada")
	@Size(max = 50, message = "1002-nombreNotificada,50")
	private String nombreNotificada;

	@NotNull(message = "1001-dniNotificada")
	@Size(max = 8, message = "1002-dniNotificada,8")
	private String dniNotificada;

	@NotNull(message = "1001-parentezcoNotificada")
	@Size(max = 30, message = "1002-parentezcoNotificada,30")
	private String parentezcoNotificada;

	@NotNull(message = "1001-obsVisita1")
	@Size(max = 500, message = "1002-obsVisita1,500")
	private String obsVisita1;

	@NotNull(message = "1001-obsVisita2")
	@Size(max = 500, message = "1002-obsVisita2,500")
	private String obsVisita2;

	@NotNull(message = "1001-codigoNotificadorVisita1")
	@Size(max = 15, message = "1002-codigoNotificadorVisita1,15")
	private String codigoNotificadorVisita1;

	@NotNull(message = "1001-nombreNotificadorVisita1")
	@Size(max = 50, message = "1002-nombreNotificadorVisita1,50")
	private String nombreNotificadorVisita1;

	@NotNull(message = "1001-dniNotificador1")
	@Size(max = 10, message = "1002-dniNotificador1,10")
	private String dniNotificador1;

	@NotNull(message = "1001-codigoNotificadorVisita2")
	@Size(max = 15, message = "1002-codigoNotificadorVisita2,15")
	private String codigoNotificadorVisita2;

	@NotNull(message = "1001-nombreNotificadorVisita2")
	@Size(max = 50, message = "1002-nombreNotificadorVisita2,50")
	private String nombreNotificadorVisita2;

	@NotNull(message = "1001-dniNotificador2")
	@Size(max = 10, message = "1002-dniNotificador2,10")
	private String dniNotificador2;

	@NotNull(message = "1001-fechaConcretaNotificacion")
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaConcretaNotificacion;

	@NotNull(message = "1001-horaConNotificacion")
	@Size(max = 5, message = "1002-horaConNotificacion,5")
	private String horaConNotificacion;

	@NotNull(message = "1001-tipoEntrega")
	@Size(max = 20, message = "1002-tipoEntrega,20")
	private String tipoEntrega;

	@NotNull(message = "1001-dificultad")
	@Size(max = 20, message = "1002-dificultad,20")
	private String dificultad;
	private String grupoFuncional;
	private String codInternoDocuRemitido;
	private String cdsd;
	private String usuario;
	
	public DistribucionComunicaciones() {
		super();
	}
	
	public DistribucionComunicaciones(Integer codigoRegistro, String codigoCarga, String codigoOficinaComercial,
			String nroIdentCarga, String fechaEntraga, String nroSuministro, String corrCedulaNotificacion,
			String fechaEmisionDocumento, String apellidoPatDestinatario, String apellidoMatDestinatario,
			String nombresDestinatario, String tipoDocumentoDestinatario, String nroDocumento, String razonSocial,
			String calle, String nro, String piso, String manzana, String lote, String urb, String provincia, String distrito,
			String nroReclamoOpen, String tipoReclamo, String codigoTipoDocu, String nroDocumentoNotificar,
			String nroVisitasNotificador, Date fechaVisita1, String horaVisita1, Date fechaVisita2,
			String horaVisita2, String nombreNotificada, String dniNotificada, String parentezcoNotificada,
			String obsVisita1, String obsVisita2, String codigoNotificadorVisita1, String nombreNotificadorVisita1,
			String dniNotificador1, String codigoNotificadorVisita2, String nombreNotificadorVisita2,
			String dniNotificador2, Date fechaConcretaNotificacion, String horaConNotificacion, String tipoEntrega,
			String dificultad, String grupoFuncional, String codInternoDocuRemitido, String cdsd, String usuario) {
		super();
		this.codigoRegistro = codigoRegistro;
		this.codigoCarga = codigoCarga;
		this.codigoOficinaComercial = codigoOficinaComercial;
		this.nroIdentCarga = nroIdentCarga;
		this.fechaEntraga = fechaEntraga;
		this.nroSuministro = nroSuministro;
		this.corrCedulaNotificacion = corrCedulaNotificacion;
		this.fechaEmisionDocumento = fechaEmisionDocumento;
		this.apellidoPatDestinatario = apellidoPatDestinatario;
		this.apellidoMatDestinatario = apellidoMatDestinatario;
		this.nombresDestinatario = nombresDestinatario;
		this.tipoDocumentoDestinatario = tipoDocumentoDestinatario;
		this.nroDocumento = nroDocumento;
		this.razonSocial = razonSocial;
		this.calle = calle;
		this.nro = nro;
		this.piso = piso;
		this.manzana = manzana;
		this.lote = lote;
		this.urb = urb;
		this.provincia = provincia;
		this.distrito = distrito;
		this.nroReclamoOpen = nroReclamoOpen;
		this.tipoReclamo = tipoReclamo;
		this.codigoTipoDocu = codigoTipoDocu;
		this.nroDocumentoNotificar = nroDocumentoNotificar;
		this.nroVisitasNotificador = nroVisitasNotificador;
		this.fechaVisita1 = fechaVisita1;
		this.horaVisita1 = horaVisita1;
		this.fechaVisita2 = fechaVisita2;
		this.horaVisita2 = horaVisita2;
		this.nombreNotificada = nombreNotificada;
		this.dniNotificada = dniNotificada;
		this.parentezcoNotificada = parentezcoNotificada;
		this.obsVisita1 = obsVisita1;
		this.obsVisita2 = obsVisita2;
		this.codigoNotificadorVisita1 = codigoNotificadorVisita1;
		this.nombreNotificadorVisita1 = nombreNotificadorVisita1;
		this.dniNotificador1 = dniNotificador1;
		this.codigoNotificadorVisita2 = codigoNotificadorVisita2;
		this.nombreNotificadorVisita2 = nombreNotificadorVisita2;
		this.dniNotificador2 = dniNotificador2;
		this.fechaConcretaNotificacion = fechaConcretaNotificacion;
		this.horaConNotificacion = horaConNotificacion;
		this.tipoEntrega = tipoEntrega;
		this.dificultad = dificultad;
		this.grupoFuncional = grupoFuncional;
		this.codInternoDocuRemitido = codInternoDocuRemitido;
		this.cdsd = cdsd;
		this.usuario = usuario;
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
	public String getCodigoOficinaComercial() {
		return codigoOficinaComercial;
	}
	public void setCodigoOficinaComercial(String codigoOficinaComercial) {
		this.codigoOficinaComercial = codigoOficinaComercial;
	}
	public String getNroIdentCarga() {
		return nroIdentCarga;
	}
	public void setNroIdentCarga(String nroIdentCarga) {
		this.nroIdentCarga = nroIdentCarga;
	}
	public String getFechaEntraga() {
		return fechaEntraga;
	}
	public void setFechaEntraga(String fechaEntraga) {
		this.fechaEntraga = fechaEntraga;
	}
	public String getNroSuministro() {
		return nroSuministro;
	}
	public void setNroSuministro(String nroSuministro) {
		this.nroSuministro = nroSuministro;
	}
	public String getCorrCedulaNotificacion() {
		return corrCedulaNotificacion;
	}
	public void setCorrCedulaNotificacion(String corrCedulaNotificacion) {
		this.corrCedulaNotificacion = corrCedulaNotificacion;
	}
	public String getFechaEmisionDocumento() {
		return fechaEmisionDocumento;
	}
	public void setFechaEmisionDocumento(String fechaEmisionDocumento) {
		this.fechaEmisionDocumento = fechaEmisionDocumento;
	}
	public String getApellidoPatDestinatario() {
		return apellidoPatDestinatario;
	}
	public void setApellidoPatDestinatario(String apellidoPatDestinatario) {
		this.apellidoPatDestinatario = apellidoPatDestinatario;
	}
	public String getApellidoMatDestinatario() {
		return apellidoMatDestinatario;
	}
	public void setApellidoMatDestinatario(String apellidoMatDestinatario) {
		this.apellidoMatDestinatario = apellidoMatDestinatario;
	}
	public String getNombresDestinatario() {
		return nombresDestinatario;
	}
	public void setNombresDestinatario(String nombresDestinatario) {
		this.nombresDestinatario = nombresDestinatario;
	}
	public String getTipoDocumentoDestinatario() {
		return tipoDocumentoDestinatario;
	}
	public void setTipoDocumentoDestinatario(String tipoDocumentoDestinatario) {
		this.tipoDocumentoDestinatario = tipoDocumentoDestinatario;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNro() {
		return nro;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
	
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getUrb() {
		return urb;
	}
	public void setUrb(String urb) {
		this.urb = urb;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getNroReclamoOpen() {
		return nroReclamoOpen;
	}
	public void setNroReclamoOpen(String nroReclamoOpen) {
		this.nroReclamoOpen = nroReclamoOpen;
	}
	public String getTipoReclamo() {
		return tipoReclamo;
	}
	public void setTipoReclamo(String tipoReclamo) {
		this.tipoReclamo = tipoReclamo;
	}
	public String getCodigoTipoDocu() {
		return codigoTipoDocu;
	}
	public void setCodigoTipoDocu(String codigoTipoDocu) {
		this.codigoTipoDocu = codigoTipoDocu;
	}
	public String getNroDocumentoNotificar() {
		return nroDocumentoNotificar;
	}
	public void setNroDocumentoNotificar(String nroDocumentoNotificar) {
		this.nroDocumentoNotificar = nroDocumentoNotificar;
	}
	public String getNroVisitasNotificador() {
		return nroVisitasNotificador;
	}
	public void setNroVisitasNotificador(String nroVisitasNotificador) {
		this.nroVisitasNotificador = nroVisitasNotificador;
	}
	public Date getFechaVisita1() {
		return fechaVisita1;
	}
	public void setFechaVisita1(Date fechaVisita1) {
		this.fechaVisita1 = fechaVisita1;
	}
	public String getHoraVisita1() {
		return horaVisita1;
	}
	public void setHoraVisita1(String horaVisita1) {
		this.horaVisita1 = horaVisita1;
	}
	public Date getFechaVisita2() {
		return fechaVisita2;
	}
	public void setFechaVisita2(Date fechaVisita2) {
		this.fechaVisita2 = fechaVisita2;
	}
	public String getHoraVisita2() {
		return horaVisita2;
	}
	public void setHoraVisita2(String horaVisita2) {
		this.horaVisita2 = horaVisita2;
	}
	public String getNombreNotificada() {
		return nombreNotificada;
	}
	public void setNombreNotificada(String nombreNotificada) {
		this.nombreNotificada = nombreNotificada;
	}
	public String getDniNotificada() {
		return dniNotificada;
	}
	public void setDniNotificada(String dniNotificada) {
		this.dniNotificada = dniNotificada;
	}
	public String getParentezcoNotificada() {
		return parentezcoNotificada;
	}
	public void setParentezcoNotificada(String parentezcoNotificada) {
		this.parentezcoNotificada = parentezcoNotificada;
	}
	public String getObsVisita1() {
		return obsVisita1;
	}
	public void setObsVisita1(String obsVisita1) {
		this.obsVisita1 = obsVisita1;
	}
	public String getObsVisita2() {
		return obsVisita2;
	}
	public void setObsVisita2(String obsVisita2) {
		this.obsVisita2 = obsVisita2;
	}
	public String getCodigoNotificadorVisita1() {
		return codigoNotificadorVisita1;
	}
	public void setCodigoNotificadorVisita1(String codigoNotificadorVisita1) {
		this.codigoNotificadorVisita1 = codigoNotificadorVisita1;
	}
	public String getNombreNotificadorVisita1() {
		return nombreNotificadorVisita1;
	}
	public void setNombreNotificadorVisita1(String nombreNotificadorVisita1) {
		this.nombreNotificadorVisita1 = nombreNotificadorVisita1;
	}
	public String getDniNotificador1() {
		return dniNotificador1;
	}
	public void setDniNotificador1(String dniNotificador1) {
		this.dniNotificador1 = dniNotificador1;
	}
	public String getCodigoNotificadorVisita2() {
		return codigoNotificadorVisita2;
	}
	public void setCodigoNotificadorVisita2(String codigoNotificadorVisita2) {
		this.codigoNotificadorVisita2 = codigoNotificadorVisita2;
	}
	public String getNombreNotificadorVisita2() {
		return nombreNotificadorVisita2;
	}
	public void setNombreNotificadorVisita2(String nombreNotificadorVisita2) {
		this.nombreNotificadorVisita2 = nombreNotificadorVisita2;
	}
	public String getDniNotificador2() {
		return dniNotificador2;
	}
	public void setDniNotificador2(String dniNotificador2) {
		this.dniNotificador2 = dniNotificador2;
	}
	public Date getFechaConcretaNotificacion() {
		return fechaConcretaNotificacion;
	}
	public void setFechaConcretaNotificacion(Date fechaConcretaNotificacion) {
		this.fechaConcretaNotificacion = fechaConcretaNotificacion;
	}
	public String getHoraConNotificacion() {
		return horaConNotificacion;
	}
	public void setHoraConNotificacion(String horaConNotificacion) {
		this.horaConNotificacion = horaConNotificacion;
	}
	public String getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	public String getGrupoFuncional() {
		return grupoFuncional;
	}
	public void setGrupoFuncional(String grupoFuncional) {
		this.grupoFuncional = grupoFuncional;
	}
	public String getCodInternoDocuRemitido() {
		return codInternoDocuRemitido;
	}
	public void setCodInternoDocuRemitido(String codInternoDocuRemitido) {
		this.codInternoDocuRemitido = codInternoDocuRemitido;
	}
	public String getCdsd() {
		return cdsd;
	}
	public void setCdsd(String cdsd) {
		this.cdsd = cdsd;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
}
