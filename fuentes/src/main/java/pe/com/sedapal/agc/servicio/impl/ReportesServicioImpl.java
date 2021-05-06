package pe.com.sedapal.agc.servicio.impl;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IReportesDAO;
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
import pe.com.sedapal.agc.servicio.IReportesServicio;
import pe.com.sedapal.agc.util.ExportWebUtil;
import pe.com.sedapal.agc.util.UriHelper;

@Service
public class ReportesServicioImpl implements IReportesServicio {

	private String irepoTempExcel;
	private Error error;

	@Value("${app.config.paths.temp}")
	public void setIrepoTempExcel(String irepoTempExcel) {
		this.irepoTempExcel = irepoTempExcel;
	}
	
	@Autowired
	private IReportesDAO dao;

	@Override
	public Error getError() {
		if(this.error == null) {
			return dao.getError();
		} else {
			return this.error;
		}
	}

//	@Override
//	public void cleanError() {
//		if(this.error == null) {
//			dao.cleanError();
//		}
//	}
	
	private String formatosDirectory = "reportes/";
	
	@Value("${app.config.paths.temp}")
	private String tempDirectory;

	 
	private String formatoFecha = "dd/MM/yyyy";
	SimpleDateFormat formateadorFecha = new SimpleDateFormat(formatoFecha);
	
	@Override
	public List<RepoInfActiEjec> obtenerListaRepoInfActiEjec(ReportesRequest request) {
		List<RepoInfActiEjec> lista = new ArrayList<RepoInfActiEjec>();
		lista = dao.obtenerListaRepoInfActiEjec(request);
		return lista;
	}

	@Override
	public List<RepoEfecActiTomaEst> obtenerListaRepoEfecActiTomaEst(ReportesRequest request) {
		List<RepoEfecActiTomaEst> lista = new ArrayList<RepoEfecActiTomaEst>();
		lista = dao.obtenerListaRepoEfecActiTomaEst(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecLectorTomaEst> obtenerListaRepoEfecLectorTomaEst(ReportesRequest request) {
		List<RepoEfecLectorTomaEst> lista = new ArrayList<RepoEfecLectorTomaEst>();
		lista = dao.obtenerListaRepoEfecLectorTomaEst(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecNotificaciones> obtenerListaRepoEfecNotificaciones(ReportesRequest request) {
		List<RepoEfecNotificaciones> lista = new ArrayList<RepoEfecNotificaciones>();
		lista = dao.obtenerListaRepoEfecNotificaciones(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecInspeComer> obtenerListaRepoEfecInspeComer(ReportesRequest request) {
		List<RepoEfecInspeComer> lista = new ArrayList<RepoEfecInspeComer>();
		lista = dao.obtenerListaRepoEfecInspeComer(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecActiAvisCob> obtenerListaRepoEfecActiAvisCob(ReportesRequest request) {
		List<RepoEfecActiAvisCob> lista = new ArrayList<RepoEfecActiAvisCob>();
		lista = dao.obtenerListaRepoEfecActiAvisCob(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecInspeInt> obtenerListaRepoEfecInspeInt(ReportesRequest request) {
		List<RepoEfecInspeInt> lista = new ArrayList<RepoEfecInspeInt>();
		lista = dao.obtenerListaRepoEfecInspeInt(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecCierre> obtenerListaRepoEfecCierre(ReportesRequest request) {
		List<RepoEfecCierre> lista = new ArrayList<RepoEfecCierre>();
		lista = dao.obtenerListaRepoEfecCierre(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecApertura> obtenerListaRepoEfecApertura(ReportesRequest request) {
		List<RepoEfecApertura> lista = new ArrayList<RepoEfecApertura>();
		lista = dao.obtenerListaRepoEfecApertura(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecSostenibilidad> obtenerListaRepoEfecSostenibilidad(ReportesRequest request) {
		List<RepoEfecSostenibilidad> lista = new ArrayList<RepoEfecSostenibilidad>();
		lista = dao.obtenerListaRepoEfecSostenibilidad(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpCicloLector> obtenerListaRepoCumpCicloLector(ReportesRequest request) {
		List<RepoCumpCicloLector> lista = new ArrayList<RepoCumpCicloLector>();
		lista = dao.obtenerListaRepoCumpCicloLector(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpActiNoti> obtenerListaRepoCumpActiNoti(ReportesRequest request) {
		List<RepoCumpActiNoti> lista = new ArrayList<RepoCumpActiNoti>();
		lista = dao.obtenerListaRepoCumpActiNoti(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpActiReci> obtenerListaRepoCumpActiReci(ReportesRequest request) {
		List<RepoCumpActiReci> lista = new ArrayList<RepoCumpActiReci>();
		lista = dao.obtenerListaRepoCumpActiReci(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpActiInsp> obtenerListaRepoCumpActiInsp(ReportesRequest request) {
		List<RepoCumpActiInsp> lista = new ArrayList<RepoCumpActiInsp>();
		lista = dao.obtenerListaRepoCumpActiInsp(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpActiCierre> obtenerListaRepoCumpActiCierre(ReportesRequest request) {
		List<RepoCumpActiCierre> lista = new ArrayList<RepoCumpActiCierre>();
		lista = dao.obtenerListaRepoCumpActiCierre(request);
		return lista;
	}
	
	@Override
	public List<RepoCumpActiReapertura> obtenerListaRepoCumpActiReapertura(ReportesRequest request) {
		List<RepoCumpActiReapertura> lista = new ArrayList<RepoCumpActiReapertura>();
		lista = dao.obtenerListaRepoCumpActiReapertura(request);
		return lista;
	}
	
	@Override
	public List<RepoEfecNotificaciones> obtenerListaRepoCumpNotificaciones(ReportesRequest request) {
		List<RepoEfecNotificaciones> lista = new ArrayList<RepoEfecNotificaciones>();
		lista = dao.obtenerListaRepoCumpNotificaciones(request);
		return lista;
	}
	
	
    @Override 
    public String generarReporteInfActiEjec(List<RepoInfActiEjec> detalle) {
		String rutaPDF = "";
		String subtitulo = "";
		
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				Integer countItem = 1;
				for (RepoInfActiEjec item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					subtitulo = item.getV_descacti();
					map.put("item",(countItem.toString()));
					map.put("oficina",item.getV_descOfic());
					map.put("perfil", "ANALISTA EXTERNO");
					map.put("usuario", item.getA_v_usucre());
					map.put("actividad", item.getV_descacti());
					map.put("subactividad", item.getV_idsubacti());
					map.put("um", item.getUM());
					map.put("progmes", item.getProg_Mes().toString());
					map.put("ejecmes", item.getEjecMes().toString());
					map.put("pendmes", item.getPendiente().toString());
					map.put("porcavance", item.getPorc_Avance().toString());
					map.put("progtotal", item.getProg_Total().toString());
					map.put("ejectotal", item.getEjecTotal().toString());
					map.put("avatotal", item.getAva_Total().toString());
					listaParaMap.add(map);	
					countItem++;
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("subtitulo",subtitulo);
				parametrosMap.put("listaDetalleMap", listaParaMap);				
				String archivo = "rptRepoInfActiEjec-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoInfActiEjec.jrxml"),tempDirectory+archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory+archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}

    @Override 
    public String generarReporteInfActiEjecCons(List<RepoInfActiEjec> detalle) {
		String rutaPDF = "";
		String subtitulo = "";
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				Integer countItem = 1;
				for (RepoInfActiEjec item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					subtitulo = item.getV_descacti();
					map.put("item",(countItem.toString()));
					map.put("oficina",item.getV_descOfic());
					map.put("perfil", "ANALISTA EXTERNO");
					map.put("usuario", item.getA_v_usucre());
					map.put("actividad", item.getV_descacti());
					map.put("subactividad", item.getV_idsubacti());
					map.put("um", item.getUM());
					map.put("progmes", item.getProg_Mes().toString());
					map.put("ejecmes", item.getEjecMes().toString());
					map.put("pendmes", item.getPendiente().toString());
					map.put("porcavance", item.getPorc_Avance().toString());
					map.put("progtotal", item.getProg_Total().toString());
					map.put("ejectotal", item.getEjecTotal().toString());
					map.put("avatotal", item.getAva_Total().toString());
					listaParaMap.add(map);
					countItem++;
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("subtitulo",subtitulo);
				parametrosMap.put("listaDetalleMap", listaParaMap);				
				String archivo = "/rptRepoInfActiEjecCons-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoInfActiEjecCons.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    
    @Override 
    public String generarExcel(List<RepoInfActiEjec> detalle) {
    	String rutaXlsx = "";
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, " Informe de Actividades Ejecutadas");
				String[] headers = new String[] { "Item", "Oficina", "Perfil", "Usuario", "Actividad Op.", "Sub Actividad",
						"U/M", "Prog.Mes", "% Ejec.Mes", "Pend.Mes", "% Avance", "Prog.Total", "% Ejec.Total","% Avance total" };

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("Informe de Actividades Ejecutadas ");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(i+1);
					dataRow.createCell(1).setCellValue(detalle.get(i).getV_descOfic());
					dataRow.createCell(2).setCellValue(detalle.get(i).getV_perfil());
					dataRow.createCell(3).setCellValue(detalle.get(i).getA_v_usucre());
					dataRow.createCell(4).setCellValue(detalle.get(i).getV_descacti());
					dataRow.createCell(5).setCellValue(detalle.get(i).getV_idsubacti());
					dataRow.createCell(6).setCellValue(detalle.get(i).getUM());
					dataRow.createCell(7).setCellValue(detalle.get(i).getProg_Mes().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getEjecMes().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPendiente().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getPorc_Avance().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getProg_Total().toString());
					dataRow.createCell(12).setCellValue(detalle.get(i).getEjecTotal().toString());
					dataRow.createCell(13).setCellValue(detalle.get(i).getAva_Total().toString());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("Total de Documentos");
				dataRow.createCell(2).setCellValue(detalle.size());
				rutaXlsx = tempDirectory + "/reporteInformeActividades-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    @Override 
    public String generarRepoEfecActiTomaEst(List<RepoEfecActiTomaEst> detalle) {
		String rutaPDF = "";
		String subtitulo = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_lecturas= new BigDecimal(0);
		BigDecimal total_incidencias= new BigDecimal(0);
		BigDecimal total_supervisadas= new BigDecimal(0);
		BigDecimal porc_total_lecturas= new BigDecimal(0);
		BigDecimal porc_total_incidencias= new BigDecimal(0);
		BigDecimal porc_total_supervisadas= new BigDecimal(0);
		BigDecimal total_ruta= new BigDecimal(0);
		BigDecimal total_con_dispositivo= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecActiTomaEst item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					subtitulo = "SERVICIO DE ACTIVIDADES COMERCIALES Y OPERATIVAS";
					total_carga=total_carga.add(item.getCarga_entrega());
					total_lecturas=total_lecturas.add(item.getLecturas());
					total_incidencias=total_incidencias.add(item.getIncidencias());
					total_supervisadas=total_supervisadas.add(item.getSupervisadas());
					porc_total_lecturas=porc_total_lecturas.add(item.getPorc_lecturas());
					porc_total_incidencias=porc_total_incidencias.add(item.getPorc_incidencias());
					porc_total_supervisadas=porc_total_supervisadas.add(item.getPorc_supervisadas());
					total_ruta=total_ruta.add(item.getCon_hoja_ruta());
					total_con_dispositivo=total_con_dispositivo.add(item.getCon_dispositivo_movil());
					
					map.put("ciclo",item.getCiclo());
					map.put("oficina",item.getV_descofic());
					map.put("contratista", item.getV_nombreempr());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("lecturas", item.getLecturas().toString());
					map.put("incidencias", item.getIncidencias().toString());
					map.put("supervisadas", item.getSupervisadas().toString());
					map.put("porc_lecturas", item.getPorc_lecturas().toString());
					map.put("porc_incidencias", item.getPorc_incidencias().toString());
					map.put("porc_supervisadas", item.getPorc_supervisadas().toString());
					map.put("hoja_ruta", item.getCon_hoja_ruta().toString());
					map.put("con_dispositivo", item.getCon_dispositivo_movil().toString());
					listaParaMap.add(map);									
				}	
			
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("subtitulo",subtitulo);
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_lecturas", total_lecturas.toString());
				parametrosMap.put("total_incidencias", total_incidencias.toString());
				parametrosMap.put("total_supervisadas", total_supervisadas.toString());
				parametrosMap.put("porc_total_lecturas", porc_total_lecturas.toString());
				parametrosMap.put("porc_total_incidencias", porc_total_incidencias.toString());
				parametrosMap.put("porc_total_supervisadas", porc_total_supervisadas.toString());
				parametrosMap.put("total_ruta", total_ruta.toString());
				parametrosMap.put("total_con_dispositivo", total_con_dispositivo.toString());
				parametrosMap.put("listaDetalleMap", listaParaMap);				
				String archivo = "rptRepoEfecActiTomaEst-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecActiTomaEst.jrxml"), tempDirectory + archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecActiTomaEstExcel(List<RepoEfecActiTomaEst> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_lectura = new BigDecimal(0);
    	BigDecimal total_incidencia = new BigDecimal(0);
    	BigDecimal total_supervisada = new BigDecimal(0);
    	BigDecimal total_porc_lectura = new BigDecimal(0);
    	BigDecimal total_porc_incidencia = new BigDecimal(0);
    	BigDecimal total_porc_supervisada = new BigDecimal(0);
    	
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en la actividad");
				String[] headers = new String[] { "Ciclo", "Oficina", "Contratista", "Carga Entregada", "Lecturas", "Incidencias",
						"Supervisadas", "% Lecturas", "% Incidencias", "% Supervisadas", "Hoja de Ruta", "Dispositivo M�vil" };

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LA ACTIVIDAD TOMA DE ESTADO");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCiclo());
					dataRow.createCell(1).setCellValue(detalle.get(i).getV_descofic());
					dataRow.createCell(2).setCellValue(detalle.get(i).getV_nombreempr());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getLecturas().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getIncidencias().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getSupervisadas().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_lecturas().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getPorc_incidencias().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPorc_supervisadas().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getCon_hoja_ruta().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getCon_dispositivo_movil().toString());
					
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					total_lectura=total_lectura.add(detalle.get(i).getLecturas());
					total_incidencia=total_incidencia.add(detalle.get(i).getIncidencias());
					total_supervisada=total_supervisada.add(detalle.get(i).getSupervisadas());
					total_porc_lectura=total_porc_lectura.add(detalle.get(i).getPorc_lecturas());
					total_porc_incidencia=total_porc_incidencia.add(detalle.get(i).getPorc_incidencias());
					total_porc_supervisada=total_porc_supervisada.add(detalle.get(i).getPorc_supervisadas());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_carga.toString());
				dataRow.createCell(4).setCellValue(total_lectura.toString());
				dataRow.createCell(5).setCellValue(total_incidencia.toString());
				dataRow.createCell(6).setCellValue(total_supervisada.toString());
				dataRow.createCell(7).setCellValue(total_porc_lectura.toString());
				dataRow.createCell(8).setCellValue(total_porc_incidencia.toString());
				dataRow.createCell(9).setCellValue(total_porc_supervisada.toString());
				rutaXlsx = tempDirectory + "RepoEfecActiTomaEst-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    

    @Override 
    public String generarRepoEfecLectorTomaEst(List<RepoEfecLectorTomaEst> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_lecturas = new BigDecimal(0);
		BigDecimal total_incidencias = new BigDecimal(0);
		BigDecimal total_supervisadas = new BigDecimal(0);
		BigDecimal porc_total_lecturas = new BigDecimal(0);
		BigDecimal porc_total_incidencias = new BigDecimal(0);
		BigDecimal porc_total_supervisadas = new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecLectorTomaEst item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_lecturas=total_lecturas.add(item.getLecturas());
					total_incidencias=total_incidencias.add(item.getIncidencias());
					total_supervisadas=total_supervisadas.add(item.getSupervisadas());
					porc_total_lecturas=porc_total_lecturas.add(item.getPorc_lecturas());
					porc_total_incidencias=porc_total_incidencias.add(item.getPorc_incidencias());
					porc_total_supervisadas=porc_total_supervisadas.add(item.getPorc_supervisadas());
					
					map.put("oficina",item.getV_descofic());
					map.put("contratista", item.getV_nombreempr());
					map.put("codigo", item.getCod_lector());
					map.put("lector", item.getLector());
					map.put("periodo", item.getPeriodo());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("lecturas", item.getLecturas().toString());
					map.put("incidencias", item.getIncidencias().toString());
					map.put("supervisadas", item.getSupervisadas().toString());
					map.put("porc_lecturas", item.getPorc_lecturas().toString());
					map.put("porc_incidencias", item.getPorc_incidencias().toString());
					map.put("porc_supervisadas", item.getPorc_supervisadas().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_lecturas", total_lecturas.toString());
				parametrosMap.put("total_incidencias", total_incidencias.toString());
				parametrosMap.put("total_supervisadas", total_supervisadas.toString());
				parametrosMap.put("porc_total_lecturas", porc_total_lecturas.toString());
				parametrosMap.put("porc_total_incidencias", porc_total_incidencias.toString());
				parametrosMap.put("porc_total_supervisadas", porc_total_supervisadas.toString());
				String archivo = "rptRepoInfActiEjec-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecLectorTomaEst.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecLectorTomaEstExcel(List<RepoEfecLectorTomaEst> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_lectura = new BigDecimal(0);
    	BigDecimal total_incidencia = new BigDecimal(0);
    	BigDecimal total_supervisada = new BigDecimal(0);
    	BigDecimal total_porc_lectura = new BigDecimal(0);
    	BigDecimal total_porc_incidencia = new BigDecimal(0);
    	BigDecimal total_porc_supervisada = new BigDecimal(0);
    	BigDecimal total_ruta = new BigDecimal(0);
    	BigDecimal total_dispositivo_movil = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad del Lector Toma de Estado");
				String[] headers = new String[] { "Oficina", "Contratista","C�digo","Lector","Periodo","Carga Entregada", "Lecturas", "Incidencias",
						"Supervisadas", "% Lecturas", "% Incidencias", "% Supervisadas", "Hoja de Ruta", "Dispositivo M�vil" };

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD DEL LECTOR TOMA DE ESTADO");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getV_descofic());
					dataRow.createCell(1).setCellValue(detalle.get(i).getV_nombreempr());
					dataRow.createCell(2).setCellValue(detalle.get(i).getCod_lector());
					dataRow.createCell(3).setCellValue(detalle.get(i).getLector());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPeriodo());
					dataRow.createCell(5).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getLecturas().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getIncidencias().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getSupervisadas().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPorc_lecturas().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getPorc_incidencias().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getPorc_supervisadas().toString());
					dataRow.createCell(12).setCellValue(detalle.get(i).getCon_hoja_ruta().toString());
					dataRow.createCell(13).setCellValue(detalle.get(i).getCon_dispositivo_movil().toString());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					total_lectura=total_lectura.add(detalle.get(i).getLecturas());
					total_incidencia=total_incidencia.add(detalle.get(i).getIncidencias());
					total_supervisada=total_supervisada.add(detalle.get(i).getSupervisadas());
					total_porc_lectura=total_porc_lectura.add(detalle.get(i).getPorc_lecturas());
					total_porc_incidencia=total_porc_incidencia.add(detalle.get(i).getPorc_incidencias());
					total_porc_supervisada=total_porc_supervisada.add(detalle.get(i).getPorc_supervisadas());
					total_ruta=total_ruta.add(detalle.get(i).getCon_hoja_ruta());
					total_dispositivo_movil=total_dispositivo_movil.add(detalle.get(i).getCon_dispositivo_movil());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(5).setCellValue(total_carga.toString());
				dataRow.createCell(6).setCellValue(total_lectura.toString());
				dataRow.createCell(7).setCellValue(total_incidencia.toString());
				dataRow.createCell(8).setCellValue(total_supervisada.toString());
				dataRow.createCell(9).setCellValue(total_porc_lectura.toString());
				dataRow.createCell(10).setCellValue(total_porc_incidencia.toString());
				dataRow.createCell(11).setCellValue(total_porc_supervisada.toString());
				dataRow.createCell(12).setCellValue(total_ruta.toString());
				dataRow.createCell(13).setCellValue(total_dispositivo_movil.toString());
				rutaXlsx = tempDirectory + "/RepoEfecActiLector-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecNotificaciones(List<RepoEfecNotificaciones> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_primera = new BigDecimal(0);
		BigDecimal total_segunda_personal = new BigDecimal(0);
		BigDecimal total_segunda_puerta = new BigDecimal(0);
		BigDecimal total_otro = new BigDecimal(0);
		BigDecimal total_pendiente = new BigDecimal(0);
		BigDecimal porc_total_primera = new BigDecimal(0);
		BigDecimal porc_total_segunda_personal = new BigDecimal(0);
		BigDecimal porc_total_segunda_puerta = new BigDecimal(0);
		BigDecimal porc_total_otro = new BigDecimal(0);
		BigDecimal porc_total_pendiente = new BigDecimal(0);
		
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecNotificaciones item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_primera=total_primera.add(item.getNumero_primera_visita());
					total_segunda_personal=total_segunda_personal.add(item.getSegunda_visita_entrega_personalizada());
					total_segunda_puerta=total_segunda_puerta.add(item.getSegunda_visita_bajo_puerta());
					total_otro=total_otro.add(item.getOtro());
					total_pendiente=total_pendiente.add(item.getPendientes());
					porc_total_primera=porc_total_primera.add(item.getPorc_numero_primera_visita());
					porc_total_segunda_personal=porc_total_segunda_personal.add(item.getPorc_segunda_visita_entrega_personalizada());
					porc_total_segunda_puerta=porc_total_segunda_puerta.add(item.getPorc_segunda_visita_bajo_puerta());
					porc_total_otro=porc_total_otro.add(item.getPorc_otro());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendientes());
					
					map.put("fecha",item.getFemision());
					map.put("oficina",item.getV_descofic());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("primera", item.getNumero_primera_visita().toString());
					map.put("segunda_personal", item.getSegunda_visita_entrega_personalizada().toString());
					map.put("segunda_puerta", item.getSegunda_visita_bajo_puerta().toString());
					map.put("otro", item.getOtro().toString());
					map.put("pendiente", item.getPendientes().toString());
					map.put("porc_primera", item.getPorc_numero_primera_visita().toString());
					map.put("porc_segunda_personal", item.getPorc_segunda_visita_entrega_personalizada().toString());
					map.put("porc_segunda_puerta", item.getPorc_segunda_visita_bajo_puerta().toString());
					map.put("porc_otro", item.getPorc_otro().toString());
					map.put("porc_pendiente", item.getPorc_pendientes().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_primera", total_primera.toString());
				parametrosMap.put("total_segunda_personal", total_segunda_personal.toString());
				parametrosMap.put("total_segunda_puerta", total_segunda_puerta.toString());
				parametrosMap.put("total_otro", total_otro.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_primera", porc_total_primera.toString());
				parametrosMap.put("porc_total_segunda_personal", porc_total_segunda_personal.toString());
				parametrosMap.put("porc_total_segunda_puerta", porc_total_segunda_puerta.toString());
				parametrosMap.put("porc_total_otro", porc_total_otro.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				
				String archivo = "rptRepoEfecNotificaciones-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecNotificaciones.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecNotificacionesExcel(List<RepoEfecNotificaciones> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_visita = new BigDecimal(0);
    	BigDecimal total_entrega_personalizada = new BigDecimal(0);
    	BigDecimal total_bajo_puerta = new BigDecimal(0);
    	BigDecimal total_otro = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_visita = new BigDecimal(0);
    	BigDecimal porc_total_entrega_personalizada = new BigDecimal(0);
    	BigDecimal porc_total_bajo_puerta = new BigDecimal(0);
    	BigDecimal porc_total_otro = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en las Notificaciones");
				String[] headers = new String[] { "FECHA EMISI�N", "CARGA ENTREGADA","1RA VISITA","ENTREGA PERSONALIZADA","BAJO PUERTA", "OTRO", "PENDIENTES", "% 1RA VISITA", "% ENTREGA PERSONALIZADA",
						"% BAJO PUERTA ", "% OTRO", "% PENDIENTES"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LAS NOTIFICAIONES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getFemision());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getNumero_primera_visita().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getSegunda_visita_entrega_personalizada().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getSegunda_visita_bajo_puerta().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getOtro().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPendientes().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_numero_primera_visita().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getPorc_segunda_visita_entrega_personalizada().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPorc_segunda_visita_bajo_puerta().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getPorc_otro().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getPorc_pendientes().toString());
					total_visita=total_visita.add(detalle.get(i).getCarga_entrega());
					total_entrega_personalizada=total_entrega_personalizada.add(detalle.get(i).getSegunda_visita_entrega_personalizada());
					total_bajo_puerta=total_bajo_puerta.add(detalle.get(i).getSegunda_visita_bajo_puerta());
					total_otro=total_otro.add(detalle.get(i).getOtro());
					total_pendiente=total_pendiente.add(detalle.get(i).getPendientes());
					porc_total_visita=porc_total_visita.add(detalle.get(i).getPorc_numero_primera_visita());
					porc_total_entrega_personalizada=porc_total_entrega_personalizada.add(detalle.get(i).getPorc_segunda_visita_entrega_personalizada());
					porc_total_bajo_puerta=porc_total_bajo_puerta.add(detalle.get(i).getPorc_segunda_visita_bajo_puerta());
					porc_total_otro=porc_total_otro.add(detalle.get(i).getPorc_otro());
					porc_total_pendiente=porc_total_pendiente.add(detalle.get(i).getPorc_pendientes());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(2).setCellValue(total_visita.toString());
				dataRow.createCell(3).setCellValue(total_entrega_personalizada.toString());
				dataRow.createCell(4).setCellValue(total_bajo_puerta.toString());
				dataRow.createCell(5).setCellValue(total_otro.toString());
				dataRow.createCell(6).setCellValue(total_pendiente.toString());
				dataRow.createCell(7).setCellValue(porc_total_visita.toString());
				dataRow.createCell(8).setCellValue(porc_total_entrega_personalizada.toString());
				dataRow.createCell(9).setCellValue(porc_total_bajo_puerta.toString());
				dataRow.createCell(10).setCellValue(porc_total_otro.toString());
				dataRow.createCell(11).setCellValue(porc_total_pendiente.toString());
				rutaXlsx = tempDirectory + "/RepoEfecNotificaciones-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecInspeComer(List<RepoEfecInspeComer> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_ejecutado = new BigDecimal(0);
		BigDecimal total_imposibilidad = new BigDecimal(0);
		BigDecimal total_pendiente = new BigDecimal(0);
		BigDecimal porc_total_ejecutado = new BigDecimal(0);
		BigDecimal porc_total_imposibilidad = new BigDecimal(0);
		BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecInspeComer item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_ejecutado=total_ejecutado.add(item.getEjecutados());
					total_imposibilidad=total_imposibilidad.add(item.getImposibilidad());
					total_pendiente=total_pendiente.add(item.getPendientes());
					porc_total_ejecutado=porc_total_ejecutado.add(item.getPorc_ejecutados());
					porc_total_imposibilidad=porc_total_imposibilidad.add(item.getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendientes());
					
					map.put("fecha",item.getFemision());
					map.put("oficina",item.getV_descofic());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("ejecutado", item.getEjecutados().toString());
					map.put("imposibilidad", item.getImposibilidad().toString());
					map.put("pendiente", item.getPendientes().toString());
					map.put("porc_ejecutado", item.getPorc_ejecutados().toString());
					map.put("porc_imposibilidad", item.getPorc_imposibilidad().toString());
					map.put("porc_pendiente", item.getPorc_pendientes().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_ejecutado", total_ejecutado.toString());
				parametrosMap.put("total_imposibilidad", total_imposibilidad.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_ejecutado", porc_total_ejecutado.toString());
				parametrosMap.put("porc_total_imposibilidad", porc_total_imposibilidad.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecInspeComer.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecInspeComerExcel(List<RepoEfecInspeComer> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_ejecutada = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_ejecutada = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en la Inspecciones Comerciales");
				String[] headers = new String[] { "FECHA EMISI�N", "CARGA ENTREGADA","EJECUTADAS","IMPOSIBILIDADES","PENDIENTES", "% EJECUTADAS", "% IMPOSIBILIDADES", "% PENDIENTES"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LA INSPECCIONES COMERCIALES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getFemision());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getEjecutados().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getImposibilidad().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPendientes().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getPorc_ejecutados().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPorc_imposibilidad().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_pendientes().toString());
					total_ejecutada=total_ejecutada.add(detalle.get(i).getEjecutados());
					total_imposibilidad=total_imposibilidad.add(detalle.get(i).getImposibilidad());
					total_pendiente=total_pendiente.add(detalle.get(i).getPendientes());
					porc_total_ejecutada=porc_total_ejecutada.add(detalle.get(i).getPorc_ejecutados());
					porc_total_imposibilidad=porc_total_imposibilidad.add(detalle.get(i).getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(detalle.get(i).getPorc_pendientes());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(1).setCellValue(total_ejecutada.toString());
				dataRow.createCell(2).setCellValue(total_imposibilidad.toString());
				dataRow.createCell(3).setCellValue(total_pendiente.toString());
				dataRow.createCell(4).setCellValue(porc_total_ejecutada.toString());
				dataRow.createCell(5).setCellValue(porc_total_imposibilidad.toString());
				dataRow.createCell(6).setCellValue(porc_total_pendiente.toString());
				rutaXlsx = tempDirectory + "/RepoEfecInspeComer-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecActiAvisCob(List<RepoEfecActiAvisCob> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_entregado = new BigDecimal(0);
		BigDecimal total_no_entregado = new BigDecimal(0);
		BigDecimal total_supervisado = new BigDecimal(0);
		BigDecimal porc_total_entregado = new BigDecimal(0);
		BigDecimal porc_total_no_entregado = new BigDecimal(0);
		BigDecimal porc_total_supervisado = new BigDecimal(0);
		
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecActiAvisCob item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_entregado=total_entregado.add(item.getEntregado());
					total_no_entregado=total_no_entregado.add(item.getNo_entregado());
					total_supervisado=total_supervisado.add(item.getSupervisado());
					porc_total_entregado=porc_total_entregado.add(item.getPorc_entregado());
					porc_total_no_entregado=porc_total_no_entregado.add(item.getPorc_no_entregado());
					porc_total_supervisado=porc_total_supervisado.add(item.getPorc_supervisado());
					
					
					map.put("ciclo",item.getCiclo());
					map.put("oficina",item.getV_descofic());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("entregado", item.getEntregado().toString());
					map.put("no_entregado", item.getNo_entregado().toString());
					map.put("supervisado", item.getSupervisado().toString());
					map.put("porc_entregado", item.getPorc_entregado().toString());
					map.put("porc_no_entregado", item.getPorc_no_entregado().toString());
					map.put("porc_supervisado", item.getPorc_supervisado().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_entregado", total_entregado.toString());
				parametrosMap.put("total_no_entregado", total_no_entregado.toString());
				parametrosMap.put("total_supervisado", total_supervisado.toString());
				parametrosMap.put("porc_total_entregado", porc_total_entregado.toString());
				parametrosMap.put("porc_total_no_entregado", porc_total_no_entregado.toString());
				parametrosMap.put("porc_total_supervisado", porc_total_supervisado.toString());
				
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecActiAvisCob.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecActiAvisCobExcel(List<RepoEfecActiAvisCob> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_entregada = new BigDecimal(0);
    	BigDecimal total_no_entregada = new BigDecimal(0);
    	BigDecimal total_supervisada = new BigDecimal(0);
     	BigDecimal porc_total_entregada = new BigDecimal(0);
    	BigDecimal porc_total_no_entregada = new BigDecimal(0);
    	BigDecimal porc_total_supervisada = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en la Actividad Aviso Cobranza");
				String[] headers = new String[] { "CICLO", "CARGA ENTREGADA","ENTREGADA","NO ENTREGADA ","SUPERVISADA", "% ENTREGADA", "% NO ENTREGADA", "% SUPERVISADA"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LA ACTIVIDAD AVISO COBRANZA");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCiclo());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getEntregado().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getNo_entregado().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getSupervisado().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getPorc_entregado().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPorc_no_entregado().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_supervisado().toString());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					total_entregada=total_entregada.add(detalle.get(i).getEntregado());
					total_no_entregada=total_no_entregada.add(detalle.get(i).getNo_entregado());
					total_supervisada=total_supervisada.add(detalle.get(i).getSupervisado());
					porc_total_entregada=porc_total_entregada.add(detalle.get(i).getPorc_entregado());
					porc_total_no_entregada=porc_total_no_entregada.add(detalle.get(i).getPorc_no_entregado());
					porc_total_supervisada=porc_total_supervisada.add(detalle.get(i).getPorc_supervisado());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(1).setCellValue(total_carga.toString());
				dataRow.createCell(2).setCellValue(total_entregada.toString());
				dataRow.createCell(3).setCellValue(total_no_entregada.toString());
				dataRow.createCell(4).setCellValue(total_supervisada.toString());
				dataRow.createCell(5).setCellValue(porc_total_entregada.toString());
				dataRow.createCell(6).setCellValue(porc_total_no_entregada.toString());
				dataRow.createCell(7).setCellValue(porc_total_supervisada.toString());
				rutaXlsx = tempDirectory + "/RepoEfecActiAvisCob-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
				
				
				
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecInspeInt(List<RepoEfecInspeInt> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_con_ingreso = new BigDecimal(0);
    	BigDecimal total_parcial = new BigDecimal(0);
    	BigDecimal total_ausente = new BigDecimal(0);
    	BigDecimal total_oposicion = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_con_ingreso = new BigDecimal(0);
    	BigDecimal porc_total_parcial = new BigDecimal(0);
    	BigDecimal porc_total_ausente = new BigDecimal(0);
    	BigDecimal porc_total_oposicion = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecInspeInt item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_con_ingreso=total_con_ingreso.add(item.getCon_ingreso());
					total_parcial=total_parcial.add(item.getInspe_parcial());
					total_ausente=total_ausente.add(item.getUsuario_ausente());
					total_oposicion=total_oposicion.add(item.getOposicion());
					total_imposibilidad=total_imposibilidad.add(item.getImposibilidad());
					total_pendiente=total_pendiente.add(item.getPendiente());
					porc_total_con_ingreso=porc_total_con_ingreso.add(item.getPorc_con_ingreso());
					porc_total_parcial=porc_total_parcial.add(item.getInspe_parcial());
					porc_total_ausente=porc_total_ausente.add(item.getPorc_usuario_ausente());
					porc_total_oposicion=porc_total_oposicion.add(item.getPorc_oposicion());
					porc_total_imposibilidad=porc_total_imposibilidad.add(item.getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendiente());
					map.put("oficina",item.getV_descofic());
					map.put("fecha",item.getV_fecha());
					map.put("tipo",item.getV_tipo_inspe());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("con_ingreso", item.getCon_ingreso().toString());
					map.put("inspe_parcial", item.getInspe_parcial().toString());
					map.put("usuario_aus", item.getUsuario_ausente().toString());
					map.put("oposicion", item.getOposicion().toString());
					map.put("imposibilidades", item.getImposibilidad().toString());
					map.put("pendiente", item.getPendiente().toString());
					map.put("porc_con_ingreso", item.getPorc_con_ingreso().toString());
					map.put("porc_inspe_parcial", item.getPorc_inspe_parcial().toString());
					map.put("porc_usuario_aus", item.getPorc_usuario_ausente().toString());
					map.put("porc_oposicion", item.getPorc_oposicion().toString());
					map.put("porc_imposibilidades", item.getPorc_imposibilidad().toString());
					map.put("porc_pendiente", item.getPorc_pendiente().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);			
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_con_ingreso", total_con_ingreso.toString());
				parametrosMap.put("total_parcial", total_parcial.toString());
				parametrosMap.put("total_ausente", total_ausente.toString());
				parametrosMap.put("total_oposicion", total_oposicion.toString());
				parametrosMap.put("total_imposibilidad", total_imposibilidad.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_con_ingreso", porc_total_con_ingreso.toString());
				parametrosMap.put("porc_total_parcial", porc_total_parcial.toString());
				parametrosMap.put("porc_total_ausente", porc_total_ausente.toString());
				parametrosMap.put("porc_total_oposicion", porc_total_oposicion.toString());
				parametrosMap.put("porc_total_imposibilidad", porc_total_imposibilidad.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecInspeInt.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecInspeIntExcel(List<RepoEfecInspeInt> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_con_ingreso = new BigDecimal(0);
    	BigDecimal total_parcial = new BigDecimal(0);
    	BigDecimal total_ausente = new BigDecimal(0);
    	BigDecimal total_oposicion = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_con_ingreso = new BigDecimal(0);
    	BigDecimal porc_total_parcial = new BigDecimal(0);
    	BigDecimal porc_total_ausente = new BigDecimal(0);
    	BigDecimal porc_total_oposicion = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en las Inspecciones Internas");
				String[] headers = new String[] { "FECHA EJECUCI�N", "CARGA ENTREGADA","CON INGRESO","INSPECCI�N PARCIAL ","USUARIO AUSENTE", "OPOSICI�N", "IMPOSIBILIDAD", "% CON INGRESO"
						 ,"% INSPECCI�N PARCIAL","% USUARIO AUSENTE","% OPOSICI�N","%IMPOSIBILIDAD"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LAS INSPECCIONES INTERNAS");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getV_fecha());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getCon_ingreso().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getInspe_parcial().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getUsuario_ausente().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getOposicion().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getImposibilidad().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_con_ingreso().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getPorc_inspe_parcial().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPorc_usuario_ausente().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getPorc_oposicion().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getPorc_imposibilidad().toString());
					total_con_ingreso=total_con_ingreso.add(detalle.get(i).getCon_ingreso());
					total_parcial=total_parcial.add(detalle.get(i).getInspe_parcial());
					total_ausente=total_ausente.add(detalle.get(i).getUsuario_ausente());
					total_oposicion=total_oposicion.add(detalle.get(i).getOposicion());
					total_imposibilidad=total_imposibilidad.add(detalle.get(i).getImposibilidad());
					porc_total_con_ingreso=porc_total_con_ingreso.add(detalle.get(i).getPorc_con_ingreso());
					porc_total_parcial=porc_total_parcial.add(detalle.get(i).getInspe_parcial());
					porc_total_ausente=porc_total_ausente.add(detalle.get(i).getPorc_usuario_ausente());
					porc_total_oposicion=porc_total_oposicion.add(detalle.get(i).getPorc_oposicion());
					porc_total_imposibilidad=porc_total_imposibilidad.add(detalle.get(i).getPorc_imposibilidad());
					
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(2).setCellValue(total_con_ingreso.toString());
				dataRow.createCell(3).setCellValue(total_parcial.toString());
				dataRow.createCell(4).setCellValue(total_ausente.toString());
				dataRow.createCell(5).setCellValue(total_oposicion.toString());
				dataRow.createCell(6).setCellValue(total_imposibilidad.toString());
				dataRow.createCell(7).setCellValue(porc_total_con_ingreso.toString());
				dataRow.createCell(8).setCellValue(porc_total_parcial.toString());
				dataRow.createCell(9).setCellValue(porc_total_ausente.toString());
				dataRow.createCell(10).setCellValue(porc_total_oposicion.toString());
				dataRow.createCell(11).setCellValue(porc_total_imposibilidad.toString());
				rutaXlsx = tempDirectory + "/RepoEfecInspeInt-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecCierre(List<RepoEfecCierre> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_ejecutada = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_ejecutada = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecCierre item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_ejecutada=total_ejecutada.add(item.getEjecutada());
					total_imposibilidad=total_imposibilidad.add(item.getImposibilidad());
					total_pendiente=total_pendiente.add(item.getPendiente());
					porc_total_ejecutada=porc_total_ejecutada.add(item.getPorc_ejecutada());
					porc_total_imposibilidad=porc_total_imposibilidad.add(item.getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendiente());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("fecha",item.getV_fecha());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("ejecutada", item.getEjecutada().toString());
					map.put("imposibilidades", item.getImposibilidad().toString());
					map.put("pendiente", item.getPendiente().toString());
					map.put("porc_ejecutada", item.getPorc_ejecutada().toString());
					map.put("porc_imposibilidades", item.getPorc_imposibilidad().toString());
					map.put("porc_pendiente", item.getPorc_pendiente().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_ejecutada", total_ejecutada.toString());
				parametrosMap.put("total_imposibilidad", total_imposibilidad.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_ejecutada", porc_total_ejecutada.toString());
				parametrosMap.put("porc_total_imposibilidad", porc_total_imposibilidad.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecCierre.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecCierreExcel(List<RepoEfecCierre> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_ejecutada = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_ejecutada = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en los Cierres Simples");
				String[] headers = new String[] { "FECHA EMISI�N", "CARGA ENTREGADA","EJECUTADOS", "IMPOSIBILIDAD", "PENDIENTES"
						 ,"% EJECUTADOS","% IMPOSIBILIDAD","% PENDIENTES"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LOS CIERRES SIMPLES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getV_fecha());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getEjecutada().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getImposibilidad().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPendiente().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getPorc_ejecutada().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPorc_imposibilidad().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_pendiente().toString());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					total_ejecutada=total_ejecutada.add(detalle.get(i).getEjecutada());
					total_imposibilidad=total_imposibilidad.add(detalle.get(i).getImposibilidad());
					total_pendiente=total_pendiente.add(detalle.get(i).getPendiente());
					porc_total_ejecutada=porc_total_ejecutada.add(detalle.get(i).getPorc_ejecutada());
					porc_total_imposibilidad=porc_total_imposibilidad.add(detalle.get(i).getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(detalle.get(i).getPorc_pendiente());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(1).setCellValue(total_carga.toString());
				dataRow.createCell(2).setCellValue(total_ejecutada.toString());
				dataRow.createCell(3).setCellValue(total_imposibilidad.toString());
				dataRow.createCell(4).setCellValue(total_pendiente.toString());
				dataRow.createCell(5).setCellValue(porc_total_ejecutada.toString());
				dataRow.createCell(6).setCellValue(porc_total_imposibilidad.toString());
				dataRow.createCell(7).setCellValue(porc_total_pendiente.toString());
				rutaXlsx = tempDirectory + "/RepoEfecInspeComer-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecApertura(List<RepoEfecApertura> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_ejecutada = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_ejecutada = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecApertura item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_ejecutada=total_ejecutada.add(item.getEjecutada());
					total_imposibilidad=total_imposibilidad.add(item.getImposibilidad());
					total_pendiente=total_pendiente.add(item.getPendiente());
					porc_total_ejecutada=porc_total_ejecutada.add(item.getPorc_ejecutada());
					porc_total_imposibilidad=porc_total_imposibilidad.add(item.getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendiente());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("fecha",item.getV_fecha());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("ejecutada", item.getEjecutada().toString());
					map.put("imposibilidades", item.getImposibilidad().toString());
					map.put("pendiente", item.getPendiente().toString());
					map.put("porc_ejecutada", item.getPorc_ejecutada().toString());
					map.put("porc_imposibilidades", item.getPorc_imposibilidad().toString());
					map.put("porc_pendiente", item.getPorc_pendiente().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_ejecutada", total_ejecutada.toString());
				parametrosMap.put("total_imposibilidad", total_imposibilidad.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_ejecutada", porc_total_ejecutada.toString());
				parametrosMap.put("porc_total_imposibilidad", porc_total_imposibilidad.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecApertura.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecAperturaExcel(List<RepoEfecApertura> detalle) {
    	String rutaXlsx = "";
		BigDecimal total_carga = new BigDecimal(0);
    	BigDecimal total_ejecutada = new BigDecimal(0);
    	BigDecimal total_imposibilidad = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_ejecutada = new BigDecimal(0);
    	BigDecimal porc_total_imposibilidad = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en las Reaperturas Simples");
				String[] headers = new String[] { "FECHA EMISI�N", "CARGA ENTREGADA","EJECUTADOS", "IMPOSIBILIDAD", "PENDIENTES"
						 ,"% EJECUTADOS","% IMPOSIBILIDAD","% PENDIENTES"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LAS REAPERTURAS SIMPLES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getV_fecha());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getEjecutada().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getImposibilidad().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPendiente().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getPorc_ejecutada().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPorc_imposibilidad().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_pendiente().toString());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					total_ejecutada=total_ejecutada.add(detalle.get(i).getEjecutada());
					total_imposibilidad=total_imposibilidad.add(detalle.get(i).getImposibilidad());
					total_pendiente=total_pendiente.add(detalle.get(i).getPendiente());
					porc_total_ejecutada=porc_total_ejecutada.add(detalle.get(i).getPorc_ejecutada());
					porc_total_imposibilidad=porc_total_imposibilidad.add(detalle.get(i).getPorc_imposibilidad());
					porc_total_pendiente=porc_total_pendiente.add(detalle.get(i).getPorc_pendiente());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(1).setCellValue(total_carga.toString());
				dataRow.createCell(2).setCellValue(total_ejecutada.toString());
				dataRow.createCell(3).setCellValue(total_imposibilidad.toString());
				dataRow.createCell(4).setCellValue(total_pendiente.toString());
				dataRow.createCell(5).setCellValue(porc_total_ejecutada.toString());
				dataRow.createCell(6).setCellValue(porc_total_imposibilidad.toString());
				dataRow.createCell(7).setCellValue(porc_total_pendiente.toString());
				rutaXlsx = tempDirectory + "/RepoEfecReapertura-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoEfecSostenibilidad(List<RepoEfecSostenibilidad> detalle) {
		String rutaPDF = "";
		BigDecimal total_d01 = new BigDecimal(0);
		BigDecimal total_d02 = new BigDecimal(0);
		BigDecimal total_d03 = new BigDecimal(0);
		BigDecimal total_d04 = new BigDecimal(0);
		BigDecimal total_d05 = new BigDecimal(0);
		BigDecimal total_d06 = new BigDecimal(0);
		BigDecimal total_d07 = new BigDecimal(0);
		BigDecimal total_d08 = new BigDecimal(0);
		BigDecimal total_d09 = new BigDecimal(0);
		BigDecimal total_d10 = new BigDecimal(0);
		
		BigDecimal total_d11 = new BigDecimal(0);
		BigDecimal total_d12 = new BigDecimal(0);
		BigDecimal total_d13 = new BigDecimal(0);
		BigDecimal total_d14 = new BigDecimal(0);
		BigDecimal total_d15 = new BigDecimal(0);
		BigDecimal total_d16 = new BigDecimal(0);
		BigDecimal total_d17 = new BigDecimal(0);
		BigDecimal total_d18 = new BigDecimal(0);
		BigDecimal total_d19 = new BigDecimal(0);
		BigDecimal total_d20 = new BigDecimal(0);
		
		BigDecimal total_d21 = new BigDecimal(0);
		BigDecimal total_d22 = new BigDecimal(0);
		BigDecimal total_d23 = new BigDecimal(0);
		BigDecimal total_d24 = new BigDecimal(0);
		BigDecimal total_d25 = new BigDecimal(0);
		BigDecimal total_d26 = new BigDecimal(0);
		BigDecimal total_d27 = new BigDecimal(0);
		BigDecimal total_d28 = new BigDecimal(0);
		BigDecimal total_d29 = new BigDecimal(0);
		BigDecimal total_d30 = new BigDecimal(0);
		BigDecimal total_d31 = new BigDecimal(0);
		BigDecimal total_dtotales = new BigDecimal(0);
		
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecSostenibilidad item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_d01=total_d01.add(item.getD1());
					total_d02=total_d02.add(item.getD2());
					total_d03=total_d03.add(item.getD3());
					total_d04=total_d04.add(item.getD4());
					total_d05=total_d05.add(item.getD5());
					total_d06=total_d06.add(item.getD6());
					total_d07=total_d07.add(item.getD7());
					total_d08=total_d08.add(item.getD8());
					total_d09=total_d09.add(item.getD9());
					total_d10=total_d10.add(item.getD10());
					
					total_d11=total_d11.add(item.getD11());
					total_d12=total_d12.add(item.getD12());
					total_d13=total_d13.add(item.getD13());
					total_d14=total_d14.add(item.getD14());
					total_d15=total_d15.add(item.getD15());
					total_d16=total_d16.add(item.getD16());
					total_d17=total_d17.add(item.getD17());
					total_d18=total_d18.add(item.getD18());
					total_d19=total_d19.add(item.getD19());
					total_d20=total_d20.add(item.getD20());
					
					total_d21=total_d21.add(item.getD21());
					total_d22=total_d22.add(item.getD22());
					total_d23=total_d23.add(item.getD23());
					total_d24=total_d24.add(item.getD24());
					total_d25=total_d25.add(item.getD25());
					total_d26=total_d26.add(item.getD26());
					total_d27=total_d27.add(item.getD27());
					total_d28=total_d28.add(item.getD28());
					total_d29=total_d29.add(item.getD29());
					total_d30=total_d30.add(item.getD30());
					
					total_d31=total_d31.add(item.getD31());
					total_dtotales=total_dtotales.add(item.getTotal());
					
					
					
					map.put("ordenador",item.getOrdenador());
					map.put("f_emision",item.getF_emision());
					map.put("carga_entregada", item.getCarga_entregada());
					map.put("d01", item.getD1().toString());
					map.put("d02", item.getD2().toString());
					map.put("d03", item.getD3().toString());
					map.put("d04", item.getD4().toString());
					map.put("d05", item.getD5().toString());
					map.put("d06", item.getD6().toString());
					map.put("d07", item.getD7().toString());
					map.put("d08", item.getD8().toString());
					map.put("d09", item.getD9().toString());
					map.put("d10", item.getD10().toString());
					map.put("d11", item.getD11().toString());
					map.put("d12", item.getD12().toString());
					map.put("d13", item.getD13().toString());
					map.put("d14", item.getD14().toString());
					map.put("d15", item.getD15().toString());
					map.put("d16", item.getD16().toString());
					map.put("d17", item.getD17().toString());
					map.put("d18", item.getD18().toString());
					map.put("d19", item.getD19().toString());
					map.put("d20", item.getD20().toString());
					map.put("d21", item.getD21().toString());
					map.put("d22", item.getD22().toString());
					map.put("d23", item.getD23().toString());
					map.put("d24", item.getD24().toString());
					map.put("d25", item.getD25().toString());
					map.put("d26", item.getD26().toString());
					map.put("d27", item.getD27().toString());
					map.put("d28", item.getD28().toString());
					map.put("d29", item.getD29().toString());
					map.put("d30", item.getD30().toString());
					map.put("d31", item.getD31().toString());
					map.put("total", item.getTotal().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_d01", total_d01.toString());
				parametrosMap.put("total_d02", total_d02.toString());
				parametrosMap.put("total_d03", total_d03.toString());
				parametrosMap.put("total_d04", total_d04.toString());
				parametrosMap.put("total_d05", total_d05.toString());
				parametrosMap.put("total_d06", total_d06.toString());
				parametrosMap.put("total_d07", total_d07.toString());
				parametrosMap.put("total_d08", total_d08.toString());
				parametrosMap.put("total_d09", total_d09.toString());
				parametrosMap.put("total_d10", total_d10.toString());
				
				parametrosMap.put("total_d11", total_d11.toString());
				parametrosMap.put("total_d12", total_d12.toString());
				parametrosMap.put("total_d13", total_d13.toString());
				parametrosMap.put("total_d14", total_d14.toString());
				parametrosMap.put("total_d15", total_d15.toString());
				parametrosMap.put("total_d16", total_d16.toString());
				parametrosMap.put("total_d17", total_d17.toString());
				parametrosMap.put("total_d18", total_d18.toString());
				parametrosMap.put("total_d19", total_d19.toString());
				parametrosMap.put("total_d20", total_d20.toString());
				
				parametrosMap.put("total_d21", total_d21.toString());
				parametrosMap.put("total_d22", total_d22.toString());
				parametrosMap.put("total_d23", total_d23.toString());
				parametrosMap.put("total_d24", total_d24.toString());
				parametrosMap.put("total_d25", total_d25.toString());
				parametrosMap.put("total_d26", total_d26.toString());
				parametrosMap.put("total_d27", total_d27.toString());
				parametrosMap.put("total_d28", total_d28.toString());
				parametrosMap.put("total_d29", total_d29.toString());
				parametrosMap.put("total_d30", total_d30.toString());
				
				parametrosMap.put("total_d31", total_d31.toString());
				
				parametrosMap.put("total_dtotales", total_dtotales.toString());
				
				String archivo = "rptRepoEfecSostenibilidad-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecSostenibilidad.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoEfecSostenibilidadExcel(List<RepoEfecSostenibilidad> detalle) {
    	String rutaXlsx = "";
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Efectividad en la Sostenibilidad");
				String[] headers = new String[] { "D01", "D02","D03", "D04", "D05","D06", "D07","D08", "D09", "D10","D11", "D12","D13", "D14", "D15","D16", "D17","D18", "D19", "D20",
						"D21", "D22","D23", "D24", "D25","D26", "D27","D28", "D29", "D30","D31"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LA SOSTENIBILIDAD");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getD1().toString());
					dataRow.createCell(1).setCellValue(detalle.get(i).getD2().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getD3().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getD4().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getD5().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getD6().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getD7().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getD8().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getD9().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getD10().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getD11().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getD12().toString());
					dataRow.createCell(12).setCellValue(detalle.get(i).getD13().toString());
					dataRow.createCell(13).setCellValue(detalle.get(i).getD14().toString());
					dataRow.createCell(14).setCellValue(detalle.get(i).getD15().toString());
					dataRow.createCell(15).setCellValue(detalle.get(i).getD16().toString());
					dataRow.createCell(16).setCellValue(detalle.get(i).getD17().toString());
					dataRow.createCell(17).setCellValue(detalle.get(i).getD18().toString());
					dataRow.createCell(18).setCellValue(detalle.get(i).getD19().toString());
					dataRow.createCell(19).setCellValue(detalle.get(i).getD20().toString());
					dataRow.createCell(20).setCellValue(detalle.get(i).getD21().toString());
					dataRow.createCell(21).setCellValue(detalle.get(i).getD22().toString());
					dataRow.createCell(22).setCellValue(detalle.get(i).getD23().toString());
					dataRow.createCell(23).setCellValue(detalle.get(i).getD24().toString());
					dataRow.createCell(24).setCellValue(detalle.get(i).getD25().toString());
					dataRow.createCell(25).setCellValue(detalle.get(i).getD26().toString());
					dataRow.createCell(26).setCellValue(detalle.get(i).getD27().toString());
					dataRow.createCell(27).setCellValue(detalle.get(i).getD28().toString());
					dataRow.createCell(28).setCellValue(detalle.get(i).getD29().toString());
					dataRow.createCell(29).setCellValue(detalle.get(i).getD30().toString());
                    dataRow.createCell(30).setCellValue(detalle.get(i).getD31().toString());
					
					
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("Total de Documentos");
				dataRow.createCell(2).setCellValue(detalle.size());
				rutaXlsx = tempDirectory + "/RepoEfecInspeComer-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpCicloLector(List<RepoCumpCicloLector> detalle) {
		String rutaPDF = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
      	
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpCicloLector item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_lector());
					map.put("lector",item.getLector());
					map.put("ciclo",item.getCiclo());
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpCicloLector.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpCicloLectorExcel(List<RepoCumpCicloLector> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
      	
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Toma de Estado");
				String[] headers = new String[] { "C�DIGO","LECTOR","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD TOMA DE ESTADO");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_lector());
					dataRow.createCell(1).setCellValue(detalle.get(i).getLector());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
				}
		
				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpCicloLector-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpActiNoti(List<RepoCumpActiNoti> detalle) {
		String rutaPDF = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpActiNoti item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_lector());
					map.put("lector",item.getCod_lector());
					map.put("ciclo","N/A");
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpActiNoti.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpActiNotiExcel(List<RepoCumpActiNoti> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Notificaciones");
				String[] headers = new String[] { "C�DIGO","LECTOR","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD NOTIFICACIONES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_lector());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCod_lector());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
				}
		
				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpActiNoti-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpActiReci(List<RepoCumpActiReci> detalle) {
		String rutaPDF = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpActiReci item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_distribuidor());
					map.put("distribuidor",item.getCod_distribuidor());
					map.put("ciclo","N/A");
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpActiReci.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpActiReciExcel(List<RepoCumpActiReci> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Recibos Diarios");
				String[] headers = new String[] { "C�DIGO","DISTRIBUIDOR","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD RECIBOS DIARIOS");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_distribuidor());
					dataRow.createCell(1).setCellValue(detalle.get(i).getDistribuidor());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
				}
		
				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpActiNoti-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpActiInsp(List<RepoCumpActiInsp> detalle) {
		String rutaPDF = "";
      	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
      	BigDecimal total_re_uso_unico= new BigDecimal(0);
      	BigDecimal total_re_uso_multi= new BigDecimal(0);
      	BigDecimal total_int_uso_unico= new BigDecimal(0);
      	BigDecimal total_int_uso_multi= new BigDecimal(0);
      	BigDecimal total_geofono= new BigDecimal(0);
      	BigDecimal total_ext_anoma= new BigDecimal(0);
      	BigDecimal total_ext_anoma_boros= new BigDecimal(0);
      	BigDecimal total_mant_catas= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpActiInsp item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					total_re_uso_unico=total_re_uso_unico.add(item.getReclamo_unico());
					total_re_uso_multi=total_re_uso_multi.add(item.getReclamo_multi());
					total_int_uso_unico=total_int_uso_unico.add(item.getInternas_unico());
					total_int_uso_multi=total_int_uso_multi.add(item.getInternas_multi());
					total_geofono=total_geofono.add(item.getGeofono());
					total_ext_anoma=total_ext_anoma.add(item.getExterna_anomalia());
					total_ext_anoma_boros=total_ext_anoma_boros.add(item.getExterna_anomalia_boroscopio());
					total_mant_catas=total_mant_catas.add(item.getCatastral());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_inspector());
					map.put("inspector",item.getInspector());
					map.put("fecha",item.getV_fecha());
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					map.put("reclamo_unico", item.getReclamo_unico().toString());
					map.put("reclamo_multi", item.getReclamo_multi().toString());
					map.put("internas_unico", item.getInternas_unico().toString());
					map.put("internas_multi", item.getInternas_multi().toString());
					map.put("geofono", item.getGeofono().toString());
					map.put("externas_anomalia", item.getExterna_anomalia().toString());
					map.put("externas_anomalia_boroscopio", item.getExterna_anomalia_boroscopio().toString());
					map.put("empad", item.getEmpad().toString());
					map.put("catastral", item.getCatastral().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				parametrosMap.put("total_re_uso_unico", total_re_uso_unico.toString());
				parametrosMap.put("total_re_uso_multi", total_re_uso_multi.toString());
				parametrosMap.put("total_int_uso_unico", total_int_uso_unico.toString());
				parametrosMap.put("total_int_uso_multi", total_int_uso_multi.toString());
				parametrosMap.put("total_geofono", total_geofono.toString());
				parametrosMap.put("total_ext_anoma", total_ext_anoma.toString());
				parametrosMap.put("total_ext_anoma_boros", total_ext_anoma_boros.toString());
				parametrosMap.put("total_mant_catas", total_mant_catas.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpActiInsp.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpActiInspExcel(List<RepoCumpActiInsp> detalle) {
    	String rutaXlsx = "";
      	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
      	BigDecimal total_re_uso_unico= new BigDecimal(0);
      	BigDecimal total_re_uso_multi= new BigDecimal(0);
      	BigDecimal total_int_uso_unico= new BigDecimal(0);
      	BigDecimal total_int_uso_multi= new BigDecimal(0);
      	BigDecimal total_geofono= new BigDecimal(0);
      	BigDecimal total_ext_anoma= new BigDecimal(0);
      	BigDecimal total_ext_anoma_boros= new BigDecimal(0);
      	BigDecimal total_mant_catas= new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Inspecciones");
				String[] headers = new String[] { "C�DIGO","INSPECTOR","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO","RECLAMOS USO UNICO","RECLAMOS USO MULTIPLE",
						"INTERNAS USO UNICO","INTERNAS USO MULTIPLE","GEOFONO","EXTERNAS Y/O ANOMAL�A"
						,"EXTERNAS Y/O ANOMAL�A CON BOROSCOPIO","EMPADRONAMIENTO","MANTENIMIENTO CATASTRAL"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD INSPECCIONES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_inspector());
					dataRow.createCell(1).setCellValue(detalle.get(i).getInspector());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getReclamo_unico().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getReclamo_multi().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getInternas_unico().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getInternas_multi().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getGeofono().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getExterna_anomalia().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getExterna_anomalia_boroscopio().toString());
					dataRow.createCell(12).setCellValue(detalle.get(i).getEmpad().toString());
					dataRow.createCell(13).setCellValue(detalle.get(i).getCatastral().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
					total_re_uso_unico=total_re_uso_unico.add(detalle.get(i).getReclamo_unico());
					total_re_uso_multi=total_re_uso_multi.add(detalle.get(i).getReclamo_multi());
					total_int_uso_unico=total_int_uso_unico.add(detalle.get(i).getInternas_unico());
					total_int_uso_multi=total_int_uso_multi.add(detalle.get(i).getInternas_multi());
					total_geofono=total_geofono.add(detalle.get(i).getGeofono());
					total_ext_anoma=total_ext_anoma.add(detalle.get(i).getExterna_anomalia());
					total_ext_anoma_boros=total_ext_anoma_boros.add(detalle.get(i).getExterna_anomalia_boroscopio());
					total_mant_catas=total_mant_catas.add(detalle.get(i).getCatastral());
					
					
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				dataRow.createCell(6).setCellValue(total_re_uso_unico.toString());
				dataRow.createCell(7).setCellValue(total_re_uso_multi.toString());
				dataRow.createCell(8).setCellValue(total_int_uso_unico.toString());
				dataRow.createCell(9).setCellValue(total_int_uso_multi.toString());
				dataRow.createCell(10).setCellValue(total_geofono.toString());
				dataRow.createCell(11).setCellValue(total_ext_anoma.toString());
				dataRow.createCell(12).setCellValue(total_ext_anoma_boros.toString());
				dataRow.createCell(13).setCellValue(total_mant_catas.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpActiInsp-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpActiCierre(List<RepoCumpActiCierre> detalle) {
		String rutaPDF = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpActiCierre item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_operario());
					map.put("inspector",item.getOperario());
					map.put("ciclo","N/A");
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpActiCierre.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpActiCierreExcel(List<RepoCumpActiCierre> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Cierre Simple");
				String[] headers = new String[] { "C�DIGO","OPERARIO","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD CIERRE SIMPLE");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_operario());
					dataRow.createCell(1).setCellValue(detalle.get(i).getOperario());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
				}
		
				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpActiCierre-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpActiReapertura(List<RepoCumpActiReapertura> detalle) {
		String rutaPDF = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoCumpActiReapertura item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_rendimiento=total_rendimiento.add(item.getRendimiento());
					total_carga=total_carga.add(item.getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(item.getPorc_cumplimiento());
					map.put("oficina",item.getV_descofic());
					map.put("contratista",item.getV_nombreempr());
					map.put("codigo",item.getCod_operario());
					map.put("inspector",item.getOperario());
					map.put("ciclo",item.getCiclo());
					map.put("rendimiento", item.getRendimiento().toString());
					map.put("carga_entrega", item.getCarga_entrega().toString());
					map.put("porc_cumplimiento", item.getPorc_cumplimiento().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);	
				parametrosMap.put("total_rendimiento", total_rendimiento.toString());
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("porc_total_cumplimiento", porc_total_cumplimiento.toString());
				String archivo = "rptRepoEfecInspeComer-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoCumpActiReapertura.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpActiReaperturaExcel(List<RepoCumpActiReapertura> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_rendimiento= new BigDecimal(0);
      	BigDecimal total_carga= new BigDecimal(0);
      	BigDecimal porc_total_cumplimiento= new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en los Rendimientos en la Actividad Reapertura Simple");
				String[] headers = new String[] { "C�DIGO","OPERARIO","RENDIMIENTO DIARIO","CARGA ENTREGADA", "% CUMPLIMIENTO"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("CUMPLIMIENTO EN LOS RENDIMIENTOS EN LA ACTIVIDAD REAPERTURA SIMPLE");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getCod_operario());
					dataRow.createCell(1).setCellValue(detalle.get(i).getOperario());
					dataRow.createCell(2).setCellValue(detalle.get(i).getRendimiento().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getPorc_cumplimiento().toString());
					total_rendimiento=total_rendimiento.add(detalle.get(i).getRendimiento());
					total_carga=total_carga.add(detalle.get(i).getCarga_entrega());
					porc_total_cumplimiento=porc_total_cumplimiento.add(detalle.get(i).getPorc_cumplimiento());
				}
		
				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(3).setCellValue(total_rendimiento.toString());
				dataRow.createCell(4).setCellValue(total_carga.toString());
				dataRow.createCell(5).setCellValue(porc_total_cumplimiento.toString());
				rutaXlsx = tempDirectory + "/RepoEfecCumpActiCierre-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    @Override 
    public String generarRepoCumpNotificaciones(List<RepoEfecNotificaciones> detalle) {
		String rutaPDF = "";
		BigDecimal total_carga = new BigDecimal(0);
		BigDecimal total_primera = new BigDecimal(0);
		BigDecimal total_segunda_personal = new BigDecimal(0);
		BigDecimal total_segunda_puerta = new BigDecimal(0);
		BigDecimal total_otro = new BigDecimal(0);
		BigDecimal total_pendiente = new BigDecimal(0);
		BigDecimal porc_total_primera = new BigDecimal(0);
		BigDecimal porc_total_segunda_personal = new BigDecimal(0);
		BigDecimal porc_total_segunda_puerta = new BigDecimal(0);
		BigDecimal porc_total_otro = new BigDecimal(0);
		BigDecimal porc_total_pendiente = new BigDecimal(0);
		
		try {
			if (!detalle.isEmpty()) {
				LocalDateTime fecha = LocalDateTime.now();
				Map<String,Object > parametrosMap = new HashMap<String,Object>();
				List<Map<String, String>> listaParaMap = new ArrayList<Map<String, String>>();
				for (RepoEfecNotificaciones item : detalle) {
					Map<String, String> map = new HashMap<String, String>();
					total_carga=total_carga.add(item.getCarga_entrega());
					total_primera=total_primera.add(item.getNumero_primera_visita());
					total_segunda_personal=total_segunda_personal.add(item.getSegunda_visita_entrega_personalizada());
					total_segunda_puerta=total_segunda_puerta.add(item.getSegunda_visita_bajo_puerta());
					total_otro=total_otro.add(item.getOtro());
					total_pendiente=total_pendiente.add(item.getPendientes());
					porc_total_primera=porc_total_primera.add(item.getPorc_numero_primera_visita());
					porc_total_segunda_personal=porc_total_segunda_personal.add(item.getPorc_segunda_visita_entrega_personalizada());
					porc_total_segunda_puerta=porc_total_segunda_puerta.add(item.getPorc_segunda_visita_bajo_puerta());
					porc_total_otro=porc_total_otro.add(item.getPorc_otro());
					porc_total_pendiente=porc_total_pendiente.add(item.getPorc_pendientes());
					
					map.put("fecha",item.getFemision());
					map.put("oficina",item.getV_descofic());
					map.put("carga_entregada", item.getCarga_entrega().toString());
					map.put("primera", item.getNumero_primera_visita().toString());
					map.put("segunda_personal", item.getSegunda_visita_entrega_personalizada().toString());
					map.put("segunda_puerta", item.getSegunda_visita_bajo_puerta().toString());
					map.put("otro", item.getOtro().toString());
					map.put("pendiente", item.getPendientes().toString());
					map.put("porc_primera", item.getPorc_numero_primera_visita().toString());
					map.put("porc_segunda_personal", item.getPorc_segunda_visita_entrega_personalizada().toString());
					map.put("porc_segunda_puerta", item.getPorc_segunda_visita_bajo_puerta().toString());
					map.put("porc_otro", item.getPorc_otro().toString());
					map.put("porc_pendiente", item.getPorc_pendientes().toString());
					listaParaMap.add(map);									
				}	
					
				parametrosMap.put("logo",UriHelper.getResource(formatosDirectory + "imagen/sedapal-logo.png"));
				parametrosMap.put("listaDetalleMap", listaParaMap);		
				parametrosMap.put("total_carga", total_carga.toString());
				parametrosMap.put("total_primera", total_primera.toString());
				parametrosMap.put("total_segunda_personal", total_segunda_personal.toString());
				parametrosMap.put("total_segunda_puerta", total_segunda_puerta.toString());
				parametrosMap.put("total_otro", total_otro.toString());
				parametrosMap.put("total_pendiente", total_pendiente.toString());
				parametrosMap.put("porc_total_primera", porc_total_primera.toString());
				parametrosMap.put("porc_total_segunda_personal", porc_total_segunda_personal.toString());
				parametrosMap.put("porc_total_segunda_puerta", porc_total_segunda_puerta.toString());
				parametrosMap.put("porc_total_otro", porc_total_otro.toString());
				parametrosMap.put("porc_total_pendiente", porc_total_pendiente.toString());
				
				String archivo = "rptRepoEfecNotificaciones-" + System.currentTimeMillis() + ".pdf";
				ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "rptRepoEfecNotificaciones.jrxml"), tempDirectory + archivo, parametrosMap);
				//ExportWebUtil.exportToPdf( UriHelper.getResource(formatosDirectory + "hoja-envio.jasper"), pdfEndpoint+archivo, parametrosMap);
				rutaPDF = tempDirectory + archivo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rutaPDF = "sin archivo";
		}
		return rutaPDF;
	}
    
    @Override 
    public String generarRepoCumpNotificacionesExcel(List<RepoEfecNotificaciones> detalle) {
    	String rutaXlsx = "";
    	BigDecimal total_visita = new BigDecimal(0);
    	BigDecimal total_entrega_personalizada = new BigDecimal(0);
    	BigDecimal total_bajo_puerta = new BigDecimal(0);
    	BigDecimal total_otro = new BigDecimal(0);
    	BigDecimal total_pendiente = new BigDecimal(0);
    	BigDecimal porc_total_visita = new BigDecimal(0);
    	BigDecimal porc_total_entrega_personalizada = new BigDecimal(0);
    	BigDecimal porc_total_bajo_puerta = new BigDecimal(0);
    	BigDecimal porc_total_otro = new BigDecimal(0);
    	BigDecimal porc_total_pendiente = new BigDecimal(0);
		try {
			if (detalle != null && !detalle.isEmpty()) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, "Cumplimiento en las Notificaciones");
				String[] headers = new String[] { "FECHA EMISI�N", "CARGA ENTREGADA","1RA VISITA","ENTREGA PERSONALIZADA","BAJO PUERTA", "OTRO", "PENDIENTES", "% 1RA VISITA", "% ENTREGA PERSONALIZADA",
						"% BAJO PUERTA ", "% OTRO", "% PENDIENTES"};

				XSSFFont headerFont = workbook.createFont();
				XSSFFont tituloFont = workbook.createFont();
				tituloFont.setBold(true);
				tituloFont.setFontHeightInPoints((short) 16);

				CellStyle tituloCellStyle = workbook.createCellStyle();
				tituloCellStyle.setFont(tituloFont);

				XSSFRow tituloRow = sheet.createRow(0);
				XSSFCell tituloCell = tituloRow.createCell(0);
				tituloCell.setCellStyle(tituloCellStyle);
				tituloCell.setCellValue("EFECTIVIDAD EN LAS NOTIFICAIONES");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);

				CellStyle headerCellStyle = workbook.createCellStyle();

				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				XSSFRow headerRow = sheet.createRow(2);
				for (int i = 0; i < headers.length; ++i) {
					String header = headers[i];
					XSSFCell cell = headerRow.createCell(i);
					cell.setCellStyle(headerCellStyle);
					cell.setCellValue(header);
				}

				for (int i = 0; i < detalle.size(); ++i) {
					XSSFRow dataRow = sheet.createRow(i + 3);
					dataRow.createCell(0).setCellValue(detalle.get(i).getFemision());
					dataRow.createCell(1).setCellValue(detalle.get(i).getCarga_entrega().toString());
					dataRow.createCell(2).setCellValue(detalle.get(i).getNumero_primera_visita().toString());
					dataRow.createCell(3).setCellValue(detalle.get(i).getSegunda_visita_entrega_personalizada().toString());
					dataRow.createCell(4).setCellValue(detalle.get(i).getSegunda_visita_bajo_puerta().toString());
					dataRow.createCell(5).setCellValue(detalle.get(i).getOtro().toString());
					dataRow.createCell(6).setCellValue(detalle.get(i).getPendientes().toString());
					dataRow.createCell(7).setCellValue(detalle.get(i).getPorc_numero_primera_visita().toString());
					dataRow.createCell(8).setCellValue(detalle.get(i).getPorc_segunda_visita_entrega_personalizada().toString());
					dataRow.createCell(9).setCellValue(detalle.get(i).getPorc_segunda_visita_bajo_puerta().toString());
					dataRow.createCell(10).setCellValue(detalle.get(i).getPorc_otro().toString());
					dataRow.createCell(11).setCellValue(detalle.get(i).getPorc_pendientes().toString());
					total_visita=total_visita.add(detalle.get(i).getCarga_entrega());
					total_entrega_personalizada=total_entrega_personalizada.add(detalle.get(i).getSegunda_visita_entrega_personalizada());
					total_bajo_puerta=total_bajo_puerta.add(detalle.get(i).getSegunda_visita_bajo_puerta());
					total_otro=total_otro.add(detalle.get(i).getOtro());
					total_pendiente=total_pendiente.add(detalle.get(i).getPendientes());
					porc_total_visita=porc_total_visita.add(detalle.get(i).getPorc_numero_primera_visita());
					porc_total_entrega_personalizada=porc_total_entrega_personalizada.add(detalle.get(i).getPorc_segunda_visita_entrega_personalizada());
					porc_total_bajo_puerta=porc_total_bajo_puerta.add(detalle.get(i).getPorc_segunda_visita_bajo_puerta());
					porc_total_otro=porc_total_otro.add(detalle.get(i).getPorc_otro());
					porc_total_pendiente=porc_total_pendiente.add(detalle.get(i).getPorc_pendientes());
				}

				XSSFRow dataRow = sheet.createRow(5 + detalle.size());
				dataRow.createCell(0).setCellValue("TOTAL");
				dataRow.createCell(2).setCellValue(total_visita.toString());
				dataRow.createCell(3).setCellValue(total_entrega_personalizada.toString());
				dataRow.createCell(4).setCellValue(total_bajo_puerta.toString());
				dataRow.createCell(5).setCellValue(total_otro.toString());
				dataRow.createCell(6).setCellValue(total_pendiente.toString());
				dataRow.createCell(7).setCellValue(porc_total_visita.toString());
				dataRow.createCell(8).setCellValue(porc_total_entrega_personalizada.toString());
				dataRow.createCell(9).setCellValue(porc_total_bajo_puerta.toString());
				dataRow.createCell(10).setCellValue(porc_total_otro.toString());
				dataRow.createCell(11).setCellValue(porc_total_pendiente.toString());
				rutaXlsx = tempDirectory + "/RepoEfecNotificaciones-" + System.currentTimeMillis() +".xlsx";				
				FileOutputStream file = new FileOutputStream(rutaXlsx);
				workbook.write(file);
				file.close();
			}
		}catch(Exception excepcion){
			excepcion.printStackTrace();
		}
		return rutaXlsx;
    }
    
    
	@Override
	public List<String> obtenerPeriodos() {
		return dao.obtenerPeriodos(); 
	}
	
	@Override
	public List<String> obtenerTipoInspe() {
		return dao.obtenerTipoInspe(); 
	}
	
	@Override
	public List<String> obtenerItems(String oficina) {
		return dao.obtenerItems(oficina); 
	}
	@Override
	public List<String> obtenerActividades(Long item) {
		return dao.obtenerActividades(item); 
	}
	@Override
	public List<String> obtenerSubactividades(Long item, String actividad) {
		return dao.obtenerSubactividades(item, actividad); 
	}
	
	@Override
	public List<String> obtenerCiclos(String periodo) {
		return dao.obtenerCiclos(periodo); 
	}
	
	
	@Override
	public List<String> obtenerUsuarios(ReportesRequest request) {
		return dao.obtenerUsuarios(request);
	}

	@Override
	public List<String> obtenerSubactividades(ReportesRequest request) {
		return dao.obtenerSubactividades(request);
	}

	@Override
	public List<String> obtenerFrecAlerta() {
		return dao.obtenerFrecAlerta(); 
	}
	
    @Override
    public List<ProgramaValores> crearProgramaValores(ProgramaValores request) {
        return dao.crearProgramaValores(request);
    }
    
    @Override
    public List<ProgramaValores> eliminarProgramaValores(ProgramaValores request) {
    	return dao.eliminarProgramaValores(request);
	}
    
    @Override
    public Integer updateProgramaValores(ProgramaValores request) {
    	return dao.updateProgramaValores(request);
    }

    @Override
    public List<ProgramaValores> listarProgramaValores(ProgramaValores request) {
    	return dao.listarProgramaValores(request);
    }

    @Override
    public List<Rendimientos> listarRendimientos(Rendimientos request) {
    	return dao.listarRendimientos(request);
    }
    
    @Override
    public String crearRendimientos(Rendimientos request) {
    	return dao.crearRendimientos(request);
    }

    @Override
    public Integer updateRendimientos(Rendimientos request) {
    	return dao.updateRendimientos(request);
    }

    @Override
    public List<Rendimientos> eliminarRendimientos(Rendimientos request) {
    	return dao.eliminarRendimientos(request);
	}}

