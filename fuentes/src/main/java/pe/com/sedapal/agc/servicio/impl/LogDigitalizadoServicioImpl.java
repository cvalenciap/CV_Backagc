package pe.com.sedapal.agc.servicio.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.ILogDigitalizadoDAO;
import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.LogDigitalizado;
import pe.com.sedapal.agc.model.request.DigitalizadoLogRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.ILogDigitalizadoServicio;

@Service
public class LogDigitalizadoServicioImpl implements ILogDigitalizadoServicio{
	
	@Autowired
	private ILogDigitalizadoDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Override
	public List<LogDigitalizado> listarLogDigitalizados(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina,
			Integer registros) {		
		return dao.listarLogDigitalizados(requestLogDigitalizado, pagina, registros);
	}
	
	@Override
	public File generarArchivoExcelLogDigitalizado(DigitalizadoLogRequest requestLogDigitalizado, Integer pagina,
			Integer registros) {
		File archivoExcel = null;	
		List<LogDigitalizado> listaLogDigitalizados = new ArrayList<LogDigitalizado>();
		listaLogDigitalizados = dao.listarLogDigitalizados(requestLogDigitalizado, pagina, registros);
		
		if(listaLogDigitalizados.size() > 0) {
			archivoExcel = generarExcel(listaLogDigitalizados);
		}
				
		return archivoExcel;
	}
	
	private File generarExcel(List<LogDigitalizado> lista) {
		File docExcel = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Log-Digitalizados");
        /*Avargas Inicio: se agrego el campo Tipo de Archivo*/
        String[] headers = new String[] { "Item", "Nro. Suministro", "Actividad", "Ord.Serv/Ord.Trab/Num.Cédula", 
                                        "Tipología Ord.Trab/Ord.Serv", "Tipo de Archivo JPG/PDF","Fecha de Acción", "IP", "Usuario", "Tipo de Acción" };
        /*Avargas Fin*/
//
//		String[] headers = new String[] { "Item", "Nro. Suministro", "Actividad", "Ord.Serv/Ord.Trab/Num.Cédula", 
//				"Tipología Ord.Trab/Ord.Serv","Fecha de Acción", "IP", "Usuario", "Tipo de Acción" };
		
		XSSFFont headerFont = workbook.createFont();
		XSSFFont tituloFont = workbook.createFont();
		tituloFont.setBold(true);
		tituloFont.setFontHeightInPoints((short) 16);
		
		CellStyle tituloCellStyle = workbook.createCellStyle();
		tituloCellStyle.setFont(tituloFont);
		
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		XSSFRow headerRow = sheet.createRow(0);
		
		for (int i = 0; i < headers.length; ++i) {
			String header = headers[i];
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue(header);
		}
		
		/*for (int i = 0; i < lista.size(); ++i) {
			XSSFRow dataRow = sheet.createRow(i + 1);
			dataRow.createCell(0).setCellValue(i+1);
			dataRow.createCell(1).setCellValue(lista.get(i).getSuministro());	
			dataRow.createCell(2).setCellValue(lista.get(i).getActividad().getDescripcion());
			dataRow.createCell(3).setCellValue(lista.get(i).getOrdTrabOrdServCedu());
			dataRow.createCell(4).setCellValue(lista.get(i).getTipologia());
			dataRow.createCell(5).setCellValue(lista.get(i).getFechaHoraAccion());
			dataRow.createCell(6).setCellValue(lista.get(i).getIpAccion());
			dataRow.createCell(7).setCellValue(lista.get(i).getUsuarioAccion());
			dataRow.createCell(8).setCellValue(lista.get(i).getTipoAccion().equals("V") ? "VISUALIZAR" : lista.get(i).getTipoAccion().equals("D") ? "DESCARGAR" : lista.get(i).getTipoAccion().equals("I") ? "IMPRIMIR" : lista.get(i).getTipoAccion());
		}*/

        /*Avargas Inicio: Se agrego el campo Tipo de Archivo*/
        for (int i = 0; i < lista.size(); ++i) {
            XSSFRow dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(i+1);
            dataRow.createCell(1).setCellValue(lista.get(i).getSuministro());              
            dataRow.createCell(2).setCellValue(lista.get(i).getActividad().getDescripcion());
            dataRow.createCell(3).setCellValue(lista.get(i).getOrdTrabOrdServCedu());
            dataRow.createCell(4).setCellValue(lista.get(i).getTipologia());
            dataRow.createCell(5).setCellValue(lista.get(i).getTipoArchivo());
            dataRow.createCell(6).setCellValue(lista.get(i).getFechaHoraAccion());
            dataRow.createCell(7).setCellValue(lista.get(i).getIpAccion());
            dataRow.createCell(8).setCellValue(lista.get(i).getUsuarioAccion());
            dataRow.createCell(9).setCellValue(lista.get(i).getTipoAccion().equals("V") ? "VISUALIZAR" : lista.get(i).getTipoAccion().equals("D") ? "DESCARGAR" : lista.get(i).getTipoAccion().equals("I") ? "IMPRIMIR" : lista.get(i).getTipoAccion());
        }
        /*Avargas Fin*/
		
		try{
			docExcel = File.createTempFile("AGC_Log_Digitalizados", ".xlsx");
			FileOutputStream file = new FileOutputStream(docExcel);
			workbook.write(file);
			file.close();
		}catch (IOException e) {
			logger.error("[AGC: LogDigitalizadoServicioImpl - generarExcel()] - "+e.getMessage());
		}						
		return docExcel;
	}

	@Override
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}

	@Override
	public Error getError() {
		return this.dao.getError();
	}	

}
