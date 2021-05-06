package pe.com.sedapal.agc.dao;

import java.util.List;

import pe.com.sedapal.agc.model.ProgramaValores;
import pe.com.sedapal.agc.model.Rendimientos;
import pe.com.sedapal.agc.model.RepoCumpActiCierre;
import pe.com.sedapal.agc.model.RepoCumpActiInsp;
import pe.com.sedapal.agc.model.RepoCumpActiNoti;
import pe.com.sedapal.agc.model.RepoCumpActiReapertura;
import pe.com.sedapal.agc.model.RepoCumpActiReci;
import pe.com.sedapal.agc.model.RepoCumpCicloLector;
import pe.com.sedapal.agc.model.RepoEfecActiAvisCob;
import pe.com.sedapal.agc.model.RepoEfecActiTomaEst;
import pe.com.sedapal.agc.model.RepoEfecApertura;
import pe.com.sedapal.agc.model.RepoEfecCierre;
import pe.com.sedapal.agc.model.RepoEfecInspeComer;
import pe.com.sedapal.agc.model.RepoEfecInspeInt;
import pe.com.sedapal.agc.model.RepoEfecLectorTomaEst;
import pe.com.sedapal.agc.model.RepoEfecNotificaciones;
import pe.com.sedapal.agc.model.RepoEfecSostenibilidad;
import pe.com.sedapal.agc.model.RepoInfActiEjec;
import pe.com.sedapal.agc.model.request.ReportesRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IReportesDAO {
	public List<RepoInfActiEjec> obtenerListaRepoInfActiEjec(ReportesRequest request);
	public List<String> obtenerPeriodos();
	Error getError();
//	void cleanError();
	List<String> obtenerUsuarios(ReportesRequest request);	
	List<String> obtenerSubactividades(ReportesRequest request);
	List<ProgramaValores> crearProgramaValores(ProgramaValores request);
	Integer updateProgramaValores(ProgramaValores request);
	List<ProgramaValores> eliminarProgramaValores(ProgramaValores request);
	List<ProgramaValores> listarProgramaValores(ProgramaValores request);
	
	List<Rendimientos> listarRendimientos(Rendimientos request);
	String crearRendimientos(Rendimientos request);
	Integer updateRendimientos(Rendimientos request);
	List<Rendimientos> eliminarRendimientos(Rendimientos request);
	public List<RepoEfecActiTomaEst> obtenerListaRepoEfecActiTomaEst(ReportesRequest request);
	public List<String> obtenerCiclos(String periodo);
	public List<RepoEfecLectorTomaEst> obtenerListaRepoEfecLectorTomaEst(ReportesRequest request);
	public List<RepoEfecNotificaciones> obtenerListaRepoEfecNotificaciones(ReportesRequest request);
	public List<RepoEfecInspeComer> obtenerListaRepoEfecInspeComer(ReportesRequest request);
	public List<RepoEfecActiAvisCob> obtenerListaRepoEfecActiAvisCob(ReportesRequest request);
	public List<RepoEfecInspeInt> obtenerListaRepoEfecInspeInt(ReportesRequest request);
	public List<RepoEfecCierre> obtenerListaRepoEfecCierre(ReportesRequest request);
	public List<RepoEfecApertura> obtenerListaRepoEfecApertura(ReportesRequest request);
	public List<RepoEfecSostenibilidad> obtenerListaRepoEfecSostenibilidad(ReportesRequest request);
	public List<RepoCumpCicloLector> obtenerListaRepoCumpCicloLector(ReportesRequest request);
	public List<RepoCumpActiNoti> obtenerListaRepoCumpActiNoti(ReportesRequest request);
	public List<RepoCumpActiReci> obtenerListaRepoCumpActiReci(ReportesRequest request);
	public List<RepoCumpActiInsp> obtenerListaRepoCumpActiInsp(ReportesRequest request);
	public List<RepoCumpActiCierre> obtenerListaRepoCumpActiCierre(ReportesRequest request);
	public List<RepoCumpActiReapertura> obtenerListaRepoCumpActiReapertura(ReportesRequest request);
	public List<String> obtenerTipoInspe();
	public List<String> obtenerItems(String oficina);
	public List<String> obtenerActividades(Long item);
	public List<String> obtenerSubactividades(Long item, String actividad);
	public List<RepoEfecNotificaciones> obtenerListaRepoCumpNotificaciones(ReportesRequest request);
	public List<String> obtenerFrecAlerta();
}
