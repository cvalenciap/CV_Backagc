package pe.com.sedapal.agc.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalContratistaRequest {

	private String numeroDocumento;
	private Integer codigoEmpleado;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer idEmpresa;
	private String codigoCargo;
	private String codigoOficina;
	private String periodoIngreso;
	private String estadoLaboral;
	private String estadoSedapal;
	private Integer codMotivoCese;
	private String fechaCese;
	private Integer tipoSolicitud;
	private String estadoSolicitud;
	private Integer idPersonal;
	private Integer idPerfil;
	private String codLote;

	public Integer getIdPersonal() {
		return idPersonal;
	}

	public void setIdPersonal(Integer idPersonal) {
		this.idPersonal = idPersonal;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getCodLote() {
		return codLote;
	}

	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getCodigoOficina() {
		return codigoOficina;
	}

	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public String getPeriodoIngreso() {
		return periodoIngreso;
	}

	public void setPeriodoIngreso(String periodoIngreso) {
		this.periodoIngreso = periodoIngreso;
	}

	public String getEstadoLaboral() {
		return estadoLaboral;
	}

	public void setEstadoLaboral(String estadoLaboral) {
		this.estadoLaboral = estadoLaboral;
	}

	public String getEstadoSedapal() {
		return estadoSedapal;
	}

	public void setEstadoSedapal(String estadoSedapal) {
		this.estadoSedapal = estadoSedapal;
	}

	public Integer getCodMotivoCese() {
		return codMotivoCese;
	}

	public void setCodMotivoCese(Integer codMotivoCese) {
		this.codMotivoCese = codMotivoCese;
	}

	public String getFechaCese() {
		return fechaCese;
	}

	public void setFechaCese(String fechaCese) {
		this.fechaCese = fechaCese;
	}

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

}
