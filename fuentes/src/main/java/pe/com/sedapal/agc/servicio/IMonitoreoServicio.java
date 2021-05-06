package pe.com.sedapal.agc.servicio;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.model.DuracionParametro;
import pe.com.sedapal.agc.model.MonitoreoCabecera;
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
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.request.VisorMonitoreoRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IMonitoreoServicio {

	Map<String, Object> listarAsignacionTrabajo(MonitoreoRequest requestMonitoreo);
	Map<String, Object> listarDetalleAsignacionTrabajo(MonitoreoRequest requestMonitoreo);
	ParametrosCargaBandeja obtenerParametrosBusquedaMonitoreoCab(Integer idPers, Integer idPerfil);
	Map<String, Object> anularRegistroCabecera(Integer idCab, Integer codEmp);
	Map<String, Object> cargaArchivoProgramacion(MultipartFile file, Integer codEmp, String codActividad, Integer codEmpresa, Integer codOficina)throws IOException, Exception;
	ParametrosCargaBandeja obtenerParametrosBusquedaBandejaMonitoreo(Integer idPers, Integer idPerfil);
	ParametrosCargaBandeja obtenerParametrosBusquedaCiclo(Integer idOficina, String idActividad, String idPeriodo);
	Error getError();
	Error getErrorServicio();
	void setErrorServicio(Error errorServicio);
	public DuracionParametro obtenerParametrosDuracion();
	Map<String, Object> listarMonitoreoCabecera(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) throws ParseException;
	Map<String, Object> listarMonitoreoDetalle(String idEmp, String fechaProgramacion, String idActividad);
	Map<String, Object> listarMonitoreoDetalleTE(MonitoreoDetalleRequestTE requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleIC(MonitoreoDetalleRequestIC requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleDA(MonitoreoDetalleRequestDA requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleDC(MonitoreoDetalleRequestDC requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleME(MonitoreoDetalleRequestME requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleCR(MonitoreoDetalleRequestCR requestMonitoreo);
	Map<String, Object> listarMonitoreoDetalleSO(MonitoreoDetalleRequestSO requestMonitoreo);
	File generarArchivoExcelMonitoreo(MonitoreoCabeceraRequest requestMonitoreo, String idActividad);
	File generarArchivoExcelMonitoreoDetalle(MonitoreoCabecera monitoreo, List<Object> listaObjetos, String idActividad);
	Map<String, Object> cargaArchivoProgramacionMasiva(MultipartFile file, Integer codEmp, String codActividad, Integer codEmpresa, String nroCarga, String fecReasignacion)throws IOException, Exception;
	Map<String, Object> listaDetalleReasignaciones(MonitoreoRequest requestMonitoreo);
	Map<String, Object> reasignarTrabajadorManual(ReprogramacionRequest requestReprogramacion);
	String[] obtenerAdjuntosMonitoreoImagen(VisorMonitoreoRequest request);
//	Map<String, Object> reasignarTrabajadorManual(Integer codigoTrabajadorAnt, Integer codigoTrabajadorNuevo);
//	Map<String, Object> reasignarTrabajadorManual(Integer codigoTrabajador);
//	String[] obtenerAdjuntosMonitoreoImagen(VisorDigitalizadoRequest request);
}
