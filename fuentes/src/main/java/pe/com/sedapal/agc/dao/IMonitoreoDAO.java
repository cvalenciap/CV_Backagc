package pe.com.sedapal.agc.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.DuracionParametro;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.request.MonitoreoCabeceraRequest;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestCR;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestDA;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestDC;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestIC;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestME;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestSO;
import pe.com.sedapal.agc.model.request.MonitoreoDetalleRequestTE;
import pe.com.sedapal.agc.model.request.MonitoreoRequest;
import pe.com.sedapal.agc.model.request.ReprogramacionRequest;
import pe.com.sedapal.agc.model.request.VisorMonitoreoRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IMonitoreoDAO {

	Map<String, Object> listarAsignacionTrabajo(MonitoreoRequest requestMonitoreo);
	Map<String, Object> listarDetalleAsignacionTrabajo(MonitoreoRequest requestMonitoreo);
	ParametrosCargaBandeja obtenerParametrosBusquedaMonitoreoCab(Integer idPers, Integer idPerfil);
	Map<String, Object> anularRegistroCabecera(Integer idCab, Integer codEmp);
	Map<String, Object> cargaArchivoProgramacion(List<Object> listaCarga, Integer codEmp, String codActividad, Integer codEmpresa, Integer codOficina) throws Exception;
	ParametrosCargaBandeja obtenerParametrosBusquedaBandejaMonitoreo(Integer idPers, Integer idPerfil);
	ParametrosCargaBandeja obtenerParametrosBusquedaCiclo(Integer idOficina, String idActividad, String idPeriodo);
	Error getError();
	public DuracionParametro obtenerParametrosDuracion();
	public Map<String, Object> listarMonitoreoCabecera(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) throws ParseException;
	public Map<String, Object> listarMonitoreoDetalleTE(MonitoreoDetalleRequestTE requestMonitoreo);
	public Map<String, Object> listarMonitoreoDetalleIC(MonitoreoDetalleRequestIC requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleDA(MonitoreoDetalleRequestDA requestDA);
	Map<String, Object> listarMonitoreoDetalleDC(MonitoreoDetalleRequestDC requestDC);
	Map<String, Object> listarMonitoreoDetalleME(MonitoreoDetalleRequestME requestME);
	Map<String, Object> listarMonitoreoDetalleCR(MonitoreoDetalleRequestCR requestCR);
	Map<String, Object> listarMonitoreoDetalleSO(MonitoreoDetalleRequestSO requestSO);
	List<Map<String, Object>> generarArchivoExcelMonitoreo(MonitoreoCabeceraRequest requestMonitoreo, String idActividad);
	Map<String, Object> cargaArchivoProgramacionMasiva(List<Object> listaCarga, Integer codEmp, String codActividad, Integer codEmpresa, String nroCarga, String fecReasignacion) throws Exception;
	Map<String, Object> listaDetalleReasignaciones(MonitoreoRequest requestMonitoreo);
	public Map<String, Object> reasignarTrabajadorManual(ReprogramacionRequest requestReprogramacion);
//	public Map<String, Object> reasignarTrabajadorManual(Integer codigoTrabajadorAnt, Integer codigoTrabajadorNuevo);
//	Map<String, Object> reaSIGNARTRABAJADORMANUAL(INTEGER CODIGOTRABAJADOR);
	List<Adjunto> listarAdjuntosMonitoreo(VisorMonitoreoRequest request);
	//List<Adjunto> listarAdjuntosMonitoreo(VisorDigitalizadoRequest request);
}
