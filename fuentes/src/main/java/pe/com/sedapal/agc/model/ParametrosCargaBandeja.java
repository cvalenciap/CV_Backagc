package pe.com.sedapal.agc.model;

import java.util.List;

public class ParametrosCargaBandeja {
	private List<Empresa> listaEmpresa;
	private List<Oficina> listaOficina;
	private List<Parametro> listaEstado;
	private List<Parametro>  listaEstadoDetalle;
	private List<Actividad> listaActividad;
	/*Avargas-AGC-Programacion:Inicio */
	private List<Parametro>  listaTipoZona;
	private List<Parametro>  listaBase;
	private List<Parametro>  listaPeriodo;
	private List<Parametro>  listaCiclo;
	private List<Parametro>  listaActOperativa;
	private List<Parametro>  listaTipoActividad;
	private List<Parametro>  listaIncidencia;
	private List<Parametro>  listaImposibilidad;
	private List<Parametro>  listaExisteFoto;
	private List<Parametro>  listaSemaforo;
	
	private List<Parametro>  listaEstadoServicio;
	private List<Parametro>  listaEstadoMedidor;
	private List<Parametro>  listaTipoInspeccion;
	private List<Parametro>  listaResultadoInspeccion;
	
	private List<Parametro>  listaCumplimiento;
	private List<Parametro>  listaZona;
	private List<ParametroSGC>  listaTipoEntrega;
	
	private List<Parametro>  listaTipoInstalacion;
	private List<Parametro>  codObservacion;
	private List<Parametro>  listaCodObservacion;
	private List<Parametro>  listaTipoNotificacion;
	private List<ParametroSGC>  listaTipoCarga;
	private List<ParametroSGC>  listaTipoOrdServicio;
	private List<ParametroSGC>  listaTipoOrdServicioSGIO;
	private List<ParametroSGC>  listaTipoOrdTrabajoSGIO;
	
	private List<ParametroSGC> listaSubActividadCR;
	private List<ParametroSGC> listaSubActividadDA;
	private List<ParametroSGC> listaSubActividadDC;
	private List<ParametroSGC> listaSubActividadIC;
	private List<ParametroSGC> listaSubActividadME;
	private List<ParametroSGC> listaSubActividadSO;
	private List<ParametroSGC> listaSubActividadTE;
	/*Avargas-AGC-Monitoreo:Fin*/
	private Integer codGrupo;
	private String descGrupo;
	private Integer codEmpresa;
	private String	nomEmpresa;
	/*Alerta tipo frecuencias - ralvi*/
	private  List<Parametro> listaAlertaFrecuencia;

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}
	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}
	public List<Oficina> getListaOficina() {
		return listaOficina;
	}
	public void setListaOficina(List<Oficina> listaOficina) {
		this.listaOficina = listaOficina;
	}
	public List<Parametro> getListaEstado() {
		return listaEstado;
	}
	public void setListaEstado(List<Parametro> listaEstado) {
		this.listaEstado = listaEstado;
	}
	public List<Actividad> getListaActividad() {
		return listaActividad;
	}
	public void setListaActividad(List<Actividad> listaActividad) {
		this.listaActividad = listaActividad;
	}
	/*Avargas-AGC-Monitoreo:Inicio*/
	public List<Parametro> getListaTipoZona() {
		return listaTipoZona;
	}
	public void setListaTipoZona(List<Parametro> listaTipoZona) {
		this.listaTipoZona = listaTipoZona;
	}
	public List<Parametro> getListaEstadoDetalle() {
		return listaEstadoDetalle;
	}
	public void setListaEstadoDetalle(List<Parametro> listaEstadoDetalle) {
		this.listaEstadoDetalle = listaEstadoDetalle;
	}

	public List<Parametro> getListaBase() {
		return listaBase;
	}
	public void setListaBase(List<Parametro> listaBase) {
		this.listaBase = listaBase;
	}
	public List<Parametro> getListaPeriodo() {
		return listaPeriodo;
	}
	public void setListaPeriodo(List<Parametro> listaPeriodo) {
		this.listaPeriodo = listaPeriodo;
	}
	public List<Parametro> getListaCiclo() {
		return listaCiclo;
	}
	public void setListaCiclo(List<Parametro> listaCiclo) {
		this.listaCiclo = listaCiclo;
	}
	public List<Parametro> getListaActOperativa() {
		return listaActOperativa;
	}
	public void setListaActOperativa(List<Parametro> listaActOperativa) {
		this.listaActOperativa = listaActOperativa;
	}
	public List<Parametro> getListaTipoActividad() {
		return listaTipoActividad;
	}
	public void setListaTipoActividad(List<Parametro> listaTipoActividad) {
		this.listaTipoActividad = listaTipoActividad;
	}
	public List<Parametro> getListaImposibilidad() {
		return listaImposibilidad;
	}
	public void setListaImposibilidad(List<Parametro> listaImposibilidad) {
		this.listaImposibilidad = listaImposibilidad;
	}
	public List<Parametro> getListaExisteFoto() {
		return listaExisteFoto;
	}
	public void setListaExisteFoto(List<Parametro> listaExisteFoto) {
		this.listaExisteFoto = listaExisteFoto;
	}
	public List<Parametro> getListaSemaforo() {
		return listaSemaforo;
	}
	public void setListaSemaforo(List<Parametro> listaSemaforo) {
		this.listaSemaforo = listaSemaforo;
	}
	public List<Parametro> getListaEstadoServicio() {
		return listaEstadoServicio;
	}
	public void setListaEstadoServicio(List<Parametro> listaEstadoServicio) {
		this.listaEstadoServicio = listaEstadoServicio;
	}
	public List<Parametro> getListaEstadoMedidor() {
		return listaEstadoMedidor;
	}
	public void setListaEstadoMedidor(List<Parametro> listaEstadoMedidor) {
		this.listaEstadoMedidor = listaEstadoMedidor;
	}
	public List<Parametro> getListaTipoInspeccion() {
		return listaTipoInspeccion;
	}
	public void setListaTipoInspeccion(List<Parametro> listaTipoInspeccion) {
		this.listaTipoInspeccion = listaTipoInspeccion;
	}
	public List<Parametro> getListaResultadoInspeccion() {
		return listaResultadoInspeccion;
	}
	public void setListaResultadoInspeccion(List<Parametro> listaResultadoInspeccion) {
		this.listaResultadoInspeccion = listaResultadoInspeccion;
	}
	public List<Parametro> getListaIncidencia() {
		return listaIncidencia;
	}
	public void setListaIncidencia(List<Parametro> listaIncidencia) {
		this.listaIncidencia = listaIncidencia;
	}
	public List<Parametro> getListaCumplimiento() {
		return listaCumplimiento;
	}
	public void setListaCumplimiento(List<Parametro> listaCumplimiento) {
		this.listaCumplimiento = listaCumplimiento;
	}
	public List<Parametro> getListaZona() {
		return listaZona;
	}
	public void setListaZona(List<Parametro> listaZona) {
		this.listaZona = listaZona;
	}
	public List<ParametroSGC> getListaTipoEntrega() {
		return listaTipoEntrega;
	}
	public void setListaTipoEntrega(List<ParametroSGC> listaTipoEntrega) {
		this.listaTipoEntrega = listaTipoEntrega;
	}
	public List<Parametro> getListaTipoInstalacion() {
		return listaTipoInstalacion;
	}
	public void setListaTipoInstalacion(List<Parametro> listaTipoInstalacion) {
		this.listaTipoInstalacion = listaTipoInstalacion;
	}
	public List<Parametro> getCodObservacion() {
		return codObservacion;
	}
	public void setCodObservacion(List<Parametro> codObservacion) {
		this.codObservacion = codObservacion;
	}
	public List<ParametroSGC> getListaTipoCarga() {
		return listaTipoCarga;
	}
	public void setListaTipoCarga(List<ParametroSGC> listaTipoCarga) {
		this.listaTipoCarga = listaTipoCarga;
	}
	public List<ParametroSGC> getListaTipoOrdServicio() {
		return listaTipoOrdServicio;
	}
	public void setListaTipoOrdServicio(List<ParametroSGC> listaTipoOrdServicio) {
		this.listaTipoOrdServicio = listaTipoOrdServicio;
	}
	public List<ParametroSGC> getListaTipoOrdServicioSGIO() {
		return listaTipoOrdServicioSGIO;
	}
	public void setListaTipoOrdServicioSGIO(List<ParametroSGC> listaTipoOrdServicioSGIO) {
		this.listaTipoOrdServicioSGIO = listaTipoOrdServicioSGIO;
	}
	public List<Parametro> getListaCodObservacion() {
		return listaCodObservacion;
	}
	public void setListaCodObservacion(List<Parametro> listaCodObservacion) {
		this.listaCodObservacion = listaCodObservacion;
	}
	public List<ParametroSGC> getListaTipoOrdTrabajoSGIO() {
		return listaTipoOrdTrabajoSGIO;
	}
	public void setListaTipoOrdTrabajoSGIO(List<ParametroSGC> listaTipoOrdTrabajoSGIO) {
		this.listaTipoOrdTrabajoSGIO = listaTipoOrdTrabajoSGIO;
	}
	/*Avargas-AGC-Monitoreo:Fin*/
	public Integer getCodGrupo() {
		return codGrupo;
	}

	public void setCodGrupo(Integer codGrupo) {
		this.codGrupo = codGrupo;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public Integer getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Integer codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getNomEmpresa() {
		return nomEmpresa;
	}

	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}
	public List<Parametro> getListaTipoNotificacion() {
		return listaTipoNotificacion;
	}
	public void setListaTipoNotificacion(List<Parametro> listaTipoNotificacion) {
		this.listaTipoNotificacion = listaTipoNotificacion;
	}
	public List<ParametroSGC> getListaSubActividadCR() {
		return listaSubActividadCR;
	}
	public void setListaSubActividadCR(List<ParametroSGC> listaSubActividadCR) {
		this.listaSubActividadCR = listaSubActividadCR;
	}
	public List<ParametroSGC> getListaSubActividadDA() {
		return listaSubActividadDA;
	}
	public void setListaSubActividadDA(List<ParametroSGC> listaSubActividadDA) {
		this.listaSubActividadDA = listaSubActividadDA;
	}
	public List<ParametroSGC> getListaSubActividadDC() {
		return listaSubActividadDC;
	}
	public void setListaSubActividadDC(List<ParametroSGC> listaSubActividadDC) {
		this.listaSubActividadDC = listaSubActividadDC;
	}
	public List<ParametroSGC> getListaSubActividadIC() {
		return listaSubActividadIC;
	}
	public void setListaSubActividadIC(List<ParametroSGC> listaSubActividadIC) {
		this.listaSubActividadIC = listaSubActividadIC;
	}
	public List<ParametroSGC> getListaSubActividadME() {
		return listaSubActividadME;
	}
	public void setListaSubActividadME(List<ParametroSGC> listaSubActividadME) {
		this.listaSubActividadME = listaSubActividadME;
	}
	public List<ParametroSGC> getListaSubActividadSO() {
		return listaSubActividadSO;
	}
	public void setListaSubActividadSO(List<ParametroSGC> listaSubActividadSO) {
		this.listaSubActividadSO = listaSubActividadSO;
	}
	public List<ParametroSGC> getListaSubActividadTE() {
		return listaSubActividadTE;
	}
	public void setListaSubActividadTE(List<ParametroSGC> listaSubActividadTE) {
		this.listaSubActividadTE = listaSubActividadTE;
	}
	public List<Parametro> getListaAlertaFrecuencia() {
		return listaAlertaFrecuencia;
	}
	public void setListaAlertaFrecuencia(List<Parametro> listaAlertaFrecuencia) {
		this.listaAlertaFrecuencia = listaAlertaFrecuencia;
	}
}
