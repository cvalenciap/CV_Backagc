package pe.com.sedapal.agc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;


@SuppressWarnings("deprecation")
public class ExportWebUtil {

	private static final Logger LOGGER = Logger.getLogger(ExportWebUtil.class);
	
	private static final int BYTES_DOWNLOAD = 1024;
	private static final String MEDIATYPE_UTF8 = "UTF-8";
	private static final String CONTENTTYPE_TEXT = "text/plain";
	private static final String EXTENSION_TEXT = ".txt";
	private static final String CONTENTTYPE_PDF = "application/pdf";
	private static final String EXTENSION_PDF = ".pdf";
	private static final String CONTENTTYPE_XLS = "application/vnd.ms-excel";
	private static final String EXTENSION_XLS = ".xls";

	public static void exportToTxt(HttpServletResponse response, String nombreArchivo, String contenido) {
		try {
			response.setContentType(CONTENTTYPE_TEXT);
			response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + EXTENSION_TEXT);

			OutputStream os = response.getOutputStream();

			InputStream input = new ByteArrayInputStream(contenido.getBytes(MEDIATYPE_UTF8));

			int read = 0;
			byte[] bytes = new byte[BYTES_DOWNLOAD];

			while ((read = input.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			os.flush();
			os.close();
		} catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
	}
	
	public static void exportToPdf(HttpServletResponse response, String rutaJasper, String nombreArchivo, Map<String, Object> params){
		try {
			JRDataSource dsLista = new JREmptyDataSource();
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, params, dsLista);
			if(jasperPrint!=null){
				response.setContentType(CONTENTTYPE_PDF);
	            response.addHeader("Content-disposition", "attachment; filename=" + nombreArchivo + EXTENSION_PDF);
	            ServletOutputStream servletStream = response.getOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, servletStream);
			}			
		}catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
	}
	
	public static void exportToPdf(HttpServletResponse response, String rutaJasper, String nombreArchivo, Map<String, Object> params, List<?> listBeans){
		try {
			JRBeanCollectionDataSource dsLista = new JRBeanCollectionDataSource(listBeans);
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, params, dsLista);
			if(jasperPrint!=null){
				response.setContentType(CONTENTTYPE_PDF);
	            response.addHeader("Content-disposition", "attachment; filename=" + nombreArchivo + EXTENSION_PDF);
	            ServletOutputStream servletStream = response.getOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, servletStream);
			}			
		}catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
	}
	
	@SuppressWarnings("resource")
	public static void exportPdfCreado(HttpServletResponse response, String ruta,String nombreArchivo){
		try {
			   File file = new File(ruta+nombreArchivo);				 
		       FileInputStream fis = new FileInputStream(file);		        
		       ByteArrayOutputStream bos = new ByteArrayOutputStream();
		       byte[] buf = new byte[1024];
		       for (int readNum; (readNum = fis.read(buf)) != -1;) {
		                bos.write(buf, 0, readNum);
		       }
		       /*Obtener la imagen en bytes*/
		        byte[] bytesReport = bos.toByteArray();

				response.setContentType(CONTENTTYPE_PDF);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + nombreArchivo );

				response.setContentLength(bytesReport.length);
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write(bytesReport, 0, bytesReport.length);
				servletOutputStream.flush();
				servletOutputStream.close();			
		} catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
	}
	
	public static void exportToExcel(HttpServletResponse response, List<BPdf> ltaJaspers,String nombreArchivo) {
		List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
		JRDataSource datasourceSinData = null; 
		JRBeanCollectionDataSource datasourceConData = null;
		byte[] reporteBytes = null;
		try {
			for (int i = 0; i < ltaJaspers.size(); i++) {
				JasperPrint paginaJasper = null ;
				if(ltaJaspers.get(i).getListaDataSource() != null){
					datasourceConData = new JRBeanCollectionDataSource(ltaJaspers.get(i).getListaDataSource());
					JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
					JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
					jrPropertiesUtil.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
					paginaJasper = JasperFillManager.fillReport(ltaJaspers.get(i).getRutaJasper(), ltaJaspers.get(i).getParametro(),datasourceConData);					
				}else{
					datasourceSinData = new JREmptyDataSource();
					JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
					JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
					jrPropertiesUtil.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
					paginaJasper = JasperFillManager.fillReport(ltaJaspers.get(i).getRutaJasper(), ltaJaspers.get(i).getParametro(),datasourceSinData);
				}
				jasperPrints.add(paginaJasper);
			}			
			if (jasperPrints != null) { 
				JRXlsExporter exporter = new JRXlsExporter ();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
				exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, MEDIATYPE_UTF8);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out); 				
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
				exporter.exportReport();
                reporteBytes = out.toByteArray();
                response.setContentType(CONTENTTYPE_XLS);
    			response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + EXTENSION_XLS);
    			response.setCharacterEncoding("UTF-8");
    			response.setContentLength(reporteBytes.length);    
    			ServletOutputStream servletOutputStream = response.getOutputStream();
    			servletOutputStream.write(reporteBytes, 0, reporteBytes.length);
    			servletOutputStream.flush();
    			servletOutputStream.close();
			}
		}
		catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
		
	} 
	
	public static void exportarVariasPaginasPdf(HttpServletResponse response, List<BPdf> ltaJaspers,String nombreArchivo){
		List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
		JRDataSource datasourceSinData = null; 
		JRBeanCollectionDataSource datasourceConData = null;
		byte[] reporteBytes = null;
		try {
			for (int i = 0; i < ltaJaspers.size(); i++) {
				JasperPrint paginaJasper = null ;
				if(ltaJaspers.get(i).getListaDataSource() != null){
					datasourceConData = new JRBeanCollectionDataSource(ltaJaspers.get(i).getListaDataSource());
					JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
					JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
					jrPropertiesUtil.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
					paginaJasper = JasperFillManager.fillReport(ltaJaspers.get(i).getRutaJasper(), ltaJaspers.get(i).getParametro(),datasourceConData);					
				}else{
					datasourceSinData = new JREmptyDataSource();
					JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
					JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
					jrPropertiesUtil.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
					paginaJasper = JasperFillManager.fillReport(ltaJaspers.get(i).getRutaJasper(), ltaJaspers.get(i).getParametro(),datasourceSinData);
				}
				jasperPrints.add(paginaJasper);
			}
			if (jasperPrints != null) { 
				JRPdfExporter exporter = new JRPdfExporter();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, MEDIATYPE_UTF8);
//                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, IDENTITY);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);     
                exporter.exportReport();
                reporteBytes = out.toByteArray();
                response.setContentType(CONTENTTYPE_PDF);
    			response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + EXTENSION_PDF);
    			response.setCharacterEncoding("UTF-8");
    			response.setContentLength(reporteBytes.length);  
    			ServletOutputStream servletOutputStream = response.getOutputStream();
    			servletOutputStream.write(reporteBytes, 0, reporteBytes.length);
    			servletOutputStream.flush();
    			servletOutputStream.close();
			}
		}
		catch (Exception e) {
//			String[] error = MensajeExceptionUtil.obtenerMensajeError(e);
//			LOGGER.error(error[1], e);
		}
	}
	
	
	public static void exportToPdf( String rutaJasper,String direccionCompleta, Map<String, Object> params){
		//rutaJasper = rutaJasper.replace("/", "\\\\");
		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(rutaJasper);
		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {			
			JRDataSource dsLista = new JREmptyDataSource();
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dsLista);
			if(jasperPrint!=null){				
	            JasperExportManager.exportReportToPdfFile(jasperPrint,direccionCompleta);
			}			
		}catch (JRException e) {
		}
	}
	

	
}
