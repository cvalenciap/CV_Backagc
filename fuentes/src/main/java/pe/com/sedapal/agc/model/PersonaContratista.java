package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaContratista extends Auditoria {

	private Integer nro;
	private Integer codigoEmpleado;
	private TipoCargo tipoCargo;
	private Cargo cargo;
	private Oficina oficina;
	private Item item;
	private Empresa contratista;
	private String codigoEmpleadoContratista;
	private String fechaIngreso;
	private String numeroDocumento;
	private String nombresCompletos;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String direccion;
	private String fechaNacimiento;
	private String telefonoPersonal;
	private String telefonoAsignado;
	private String correoElectronico;
	private String fechaAlta;
	private String fechaBaja;
	private Parametro motivoBaja;
	private Parametro motivoAlta;
	private String observacionBaja;
	private Estado estadoPersonal;
	private Estado estadoLaboral;
	private String usuario;
	private Archivo archivoFotoPersonal;
	private Archivo archivoCvPersonal;
	private Archivo archivoFotoPersonalAnterior;
	private Archivo archivoCvPersonalAnterior;
	private String indicadorAlta;

	public String getIndicadorAlta() {
		return indicadorAlta;
	}

	public void setIndicadorAlta(String indicadorAlta) {
		this.indicadorAlta = indicadorAlta;
	}

	public String getNombresCompletos() {
		return nombresCompletos;
	}

	public void setNombresCompletos(String nombresCompletos) {
		this.nombresCompletos = nombresCompletos;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Integer getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Integer codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Empresa getContratista() {
		return contratista;
	}

	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}

	public String getCodigoEmpleadoContratista() {
		return codigoEmpleadoContratista;
	}

	public void setCodigoEmpleadoContratista(String codigoEmpleadoContratista) {
		this.codigoEmpleadoContratista = codigoEmpleadoContratista;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefonoPersonal() {
		return telefonoPersonal;
	}

	public void setTelefonoPersonal(String telefonoPersonal) {
		this.telefonoPersonal = telefonoPersonal;
	}

	public String getTelefonoAsignado() {
		return telefonoAsignado;
	}

	public void setTelefonoAsignado(String telefonoAsignado) {
		this.telefonoAsignado = telefonoAsignado;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Parametro getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(Parametro motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public Parametro getMotivoAlta() {
		return motivoAlta;
	}

	public void setMotivoAlta(Parametro motivoAlta) {
		this.motivoAlta = motivoAlta;
	}

	public String getObservacionBaja() {
		return observacionBaja;
	}

	public void setObservacionBaja(String observacionBaja) {
		this.observacionBaja = observacionBaja;
	}

	public Estado getEstadoPersonal() {
		return estadoPersonal;
	}

	public void setEstadoPersonal(Estado estadoPersonal) {
		this.estadoPersonal = estadoPersonal;
	}

	public Estado getEstadoLaboral() {
		return estadoLaboral;
	}

	public void setEstadoLaboral(Estado estadoLaboral) {
		this.estadoLaboral = estadoLaboral;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Archivo getArchivoFotoPersonal() {
		return archivoFotoPersonal;
	}

	public void setArchivoFotoPersonal(Archivo archivoFotoPersonal) {
		this.archivoFotoPersonal = archivoFotoPersonal;
	}

	public Archivo getArchivoCvPersonal() {
		return archivoCvPersonal;
	}

	public void setArchivoCvPersonal(Archivo archivoCvPersonal) {
		this.archivoCvPersonal = archivoCvPersonal;
	}

	public Archivo getArchivoFotoPersonalAnterior() {
		return archivoFotoPersonalAnterior;
	}

	public void setArchivoFotoPersonalAnterior(Archivo archivoFotoPersonalAnterior) {
		this.archivoFotoPersonalAnterior = archivoFotoPersonalAnterior;
	}

	public Archivo getArchivoCvPersonalAnterior() {
		return archivoCvPersonalAnterior;
	}

	public void setArchivoCvPersonalAnterior(Archivo archivoCvPersonalAnterior) {
		this.archivoCvPersonalAnterior = archivoCvPersonalAnterior;
	}

}
