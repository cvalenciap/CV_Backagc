package pe.com.sedapal.agc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedCaseInsensitiveMap;

import pe.com.sedapal.agc.model.MonitoreoCabecera;
import pe.com.sedapal.agc.model.MonitoreoDetalleTE;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.TomaEstados;

public class GeneraExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(GeneraExcelUtil.class);
	

	@SuppressWarnings("unchecked")
	public File generarExcel( List<Map<String, Object>> listaObjetos, String idActividad) {
		File docExcel = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Monitoreo");
		String[] headers = new String[] {};
		
		switch (idActividad) {
		case "TE":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Lectura", "Medidor", "Incidencia lectura 1", 
					"Incidencia lectura 2", "Incidencia lectura 3", "Medidor observado", "F. Ejecución", "H. Ejecución", "Estado", 
					"Zona", "Cumplimiento" };
			break;
		case "IC":
			headers = new String[] {"Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Medidor", "Orden Servicio", "Tipología Ord. Serv.", "Fecha Visita",
					"Hora Inicio", "Hora Fin", "Servicio", "Lectura", "Estado Medidor", "Incidencia Medidor", "Imposibilidad",
					"Inspección Realizada", "Detalle", "Estado", "Zona", "Cumplimiento"};
			break;
		case "DC":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Nro Carta / Resolución", "Nro Visita", 
					"Tipo de Entrega", "Fecha Notificación", "Hora Notificación", "Incidencia", "Descripción", "Estado", "Zona", "Cumplimiento" };
			break;
		case "DA":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Incidencia", "Descripción", "Tipo Entrega", 
					"Fecha Distribución", "Hora Distribución", "Estado", "Zona", "Cumplimiento" };
			break;
		case "ME":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Serv.", "Tipología de Ord Serv.", "Tipo Actividad", 
					"Codigo Observac.", "Tipo Instalación", "Medidor Instalado", "Medidor Retirado", "Fecha Ejecución", "Hora Ejecución", "Estado",
					"Zona", "Cumplimiento" };
			break;
		case "CR":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Servicio", "Tipología Ord Serv.", "Tipo Actividad", 
					"Codigo Actividad", "Codigo de Observac.", "Lectura Cierre", "F. Ejecución", "H. Ejecución", "Estado", "Zona", "Cumplimiento"
 };
			break;
		case "SO":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Servicio", "Tipología Ord. Servicio",
					"Tipo Actividad", "Cod. de Actividad", "Cod. Observación", "Lectura Cierre", "F. Ejecución", "H. Ejecución", "Estado",
					"Zona", "Cumplimiento" };
			break;

		default:
			break;
		}
		
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
			
		for (int i = 0; i < listaObjetos.size(); ++i) {
			XSSFRow dataRow = sheet.createRow(i + 1);
			LinkedCaseInsensitiveMap<Object> item =  (LinkedCaseInsensitiveMap<Object>) listaObjetos.get(i);
			
			dataRow.createCell(0).setCellValue(i+1);
			dataRow.createCell(1).setCellValue(item.get("NOM_EMP")!=null?item.get("NOM_EMP").toString():item.get("COD_EMP").toString());	
			dataRow.createCell(2).setCellValue(item.get("DES_ACT")!=null?item.get("DES_ACT").toString():"");
			dataRow.createCell(3).setCellValue(item.get("CAN_PRO")!=null?item.get("CAN_PRO").toString():"");
			dataRow.createCell(4).setCellValue(item.get("CAN_EJE")!=null?item.get("CAN_EJE").toString():"");
			dataRow.createCell(5).setCellValue(item.get("CAN_PEN")!=null?item.get("CAN_PEN").toString():"");
			dataRow.createCell(6).setCellValue(item.get("POR_AVA")!=null?item.get("POR_AVA").toString():"");
			dataRow.createCell(7).setCellValue(item.get("SEMAFORO")!=null?item.get("SEMAFORO").toString():"");
			dataRow.createCell(8).setCellValue(item.get("FEC_PRO")!=null?item.get("FEC_PRO").toString():"");
			dataRow.createCell(9).setCellValue(item.get("FEC_INI_EJEC")!=null?item.get("FEC_INI_EJEC").toString():"");
			dataRow.createCell(10).setCellValue(item.get("HOR_INI_EJEC")!=null?item.get("HOR_INI_EJEC").toString():"");
			dataRow.createCell(11).setCellValue(item.get("FEC_FIN_EJEC")!=null?item.get("FEC_FIN_EJEC").toString():"");
			dataRow.createCell(12).setCellValue(item.get("HOR_FIN_EJEC")!=null?item.get("HOR_FIN_EJEC").toString():"");


			dataRow.createCell(13).setCellValue(item.get("DIR")!=null?item.get("DIR").toString():"");
			dataRow.createCell(14).setCellValue(item.get("NIS")!=null?item.get("NIS").toString():"");
			switch (idActividad) {
			case "TE":

				dataRow.createCell(15).setCellValue(item.get("LEC")!=null?item.get("LEC").toString():"");
				dataRow.createCell(16).setCellValue(item.get("MED")!=null?item.get("MED").toString():"");
				dataRow.createCell(17).setCellValue(item.get("INC_LEC_1")!=null?item.get("INC_LEC_1").toString():"");
				dataRow.createCell(18).setCellValue(item.get("INC_LEC_2")!=null?item.get("INC_LEC_2").toString():"");
				dataRow.createCell(19).setCellValue(item.get("INC_LEC_3")!=null?item.get("INC_LEC_3").toString():"");
				dataRow.createCell(20).setCellValue(item.get("MED_OBS")!=null?item.get("MED_OBS").toString():"");
				dataRow.createCell(21).setCellValue(item.get("FEC_EJE")!=null?item.get("FEC_EJE").toString():"");
				dataRow.createCell(22).setCellValue(item.get("HOR_EJE")!=null?item.get("HOR_EJE").toString():"");
				dataRow.createCell(23).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(24).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(25).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
			case "IC":

				dataRow.createCell(15).setCellValue(item.get("MED")!=null?item.get("MED").toString():"");
				dataRow.createCell(16).setCellValue(item.get("OS")!=null?item.get("OS").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TOS")!=null?item.get("TOS").toString():"");
				dataRow.createCell(18).setCellValue(item.get("F_VIS")!=null?item.get("F_VIS").toString():"");
				dataRow.createCell(19).setCellValue(item.get("H_INI")!=null?item.get("H_INI").toString():"");
				dataRow.createCell(20).setCellValue(item.get("H_FIN")!=null?item.get("H_FIN").toString():"");
				dataRow.createCell(21).setCellValue(item.get("SER")!=null?item.get("SER").toString():"");
				dataRow.createCell(22).setCellValue(item.get("LEC")!=null?item.get("LEC").toString():"");
				dataRow.createCell(23).setCellValue(item.get("ESM")!=null?item.get("ESM").toString():"");
				dataRow.createCell(24).setCellValue(item.get("INC")!=null?item.get("INC").toString():"");
				dataRow.createCell(25).setCellValue(item.get("IMP")!=null?item.get("IMP").toString():"");
				dataRow.createCell(26).setCellValue(item.get("INR")!=null?item.get("INR").toString():"");
				dataRow.createCell(27).setCellValue(item.get("DETALLE")!=null?item.get("DETALLE").toString():"");
				dataRow.createCell(28).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(29).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(30).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
			case "DC":
				dataRow.createCell(15).setCellValue(item.get("NRO_CARTA")!=null?item.get("NRO_CARTA").toString():"");
				dataRow.createCell(16).setCellValue(item.get("NRO_VISITA")!=null?item.get("NRO_VISITA").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TIP_ENTREGA")!=null?item.get("TIP_ENTREGA").toString():"");
				dataRow.createCell(18).setCellValue(item.get("FEC_NOTIF")!=null?item.get("FEC_NOTIF").toString():"");
				dataRow.createCell(19).setCellValue(item.get("HOR_NOTIF")!=null?item.get("HOR_NOTIF").toString():"");
				dataRow.createCell(20).setCellValue(item.get("INC")!=null?item.get("INC").toString():"");
				dataRow.createCell(21).setCellValue(item.get("DESC_INC")!=null?item.get("DESC_INC").toString():"");
				dataRow.createCell(22).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(23).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(24).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");

				break;
			case "DA":
				dataRow.createCell(15).setCellValue(item.get("IMP")!=null?item.get("IMP").toString():"");
				dataRow.createCell(16).setCellValue(item.get("DES")!=null?item.get("DES").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TEN")!=null?item.get("TEN").toString():"");
				dataRow.createCell(18).setCellValue(item.get("F_DIS")!=null?item.get("F_DIS").toString():"");
				dataRow.createCell(19).setCellValue(item.get("H_DIS")!=null?item.get("H_DIS").toString():"");
				dataRow.createCell(20).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(21).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(22).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
			case "ME":
				dataRow.createCell(15).setCellValue(item.get("NRO_ORD")!=null?item.get("NRO_ORD").toString():"");
				dataRow.createCell(16).setCellValue(item.get("TIP_ORD")!=null?item.get("TIP_ORD").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TIP_ACT")!=null?item.get("TIP_ACT").toString():"");
				dataRow.createCell(18).setCellValue(item.get("CODIGO_OBSERVACION")!=null?item.get("CODIGO_OBSERVACION").toString():"");
				dataRow.createCell(19).setCellValue(item.get("TIP_INST")!=null?item.get("TIP_INST").toString():"");
				dataRow.createCell(20).setCellValue(item.get("MED_INST")!=null?item.get("MED_INST").toString():"");
				dataRow.createCell(21).setCellValue(item.get("MED_RETI")!=null?item.get("MED_RETI").toString():"");
				dataRow.createCell(22).setCellValue(item.get("FEC_EJE")!=null?item.get("FEC_EJE").toString():"");
				dataRow.createCell(23).setCellValue(item.get("HOR_EJE")!=null?item.get("HOR_EJE").toString():"");
				dataRow.createCell(24).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(25).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(26).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
			case "CR":
				dataRow.createCell(15).setCellValue(item.get("NRO_ORD")!=null?item.get("NRO_ORD").toString():"");
				dataRow.createCell(16).setCellValue(item.get("TIP_ORD")!=null?item.get("TIP_ORD").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TIP_ACT")!=null?item.get("TIP_ACT").toString():"");
				dataRow.createCell(18).setCellValue(item.get("CACT")!=null?item.get("CACT").toString():"");
				dataRow.createCell(19).setCellValue(item.get("OBSE")!=null?item.get("OBSE").toString():"");
				dataRow.createCell(20).setCellValue(item.get("LECT")!=null?item.get("LECT").toString():"");
				dataRow.createCell(21).setCellValue(item.get("FEJE")!=null?item.get("FEJE").toString():"");
				dataRow.createCell(22).setCellValue(item.get("HEJE")!=null?item.get("HEJE").toString():"");
				dataRow.createCell(23).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(24).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(25).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
			case "SO":
				dataRow.createCell(15).setCellValue(item.get("NRO_ORD")!=null?item.get("NRO_ORD").toString():"");
				dataRow.createCell(16).setCellValue(item.get("TIP_ORD")!=null?item.get("TIP_ORD").toString():"");
				dataRow.createCell(17).setCellValue(item.get("TIP_ACT")!=null?item.get("TIP_ACT").toString():"");
				dataRow.createCell(18).setCellValue(item.get("CACT")!=null?item.get("CACT").toString():"");
				dataRow.createCell(19).setCellValue(item.get("OBSE")!=null?item.get("OBSE").toString():"");
				dataRow.createCell(20).setCellValue(item.get("LECT")!=null?item.get("LECT").toString():"");
				dataRow.createCell(21).setCellValue(item.get("FEJE")!=null?item.get("FEJE").toString():"");
				dataRow.createCell(22).setCellValue(item.get("HEJE")!=null?item.get("HEJE").toString():"");
				dataRow.createCell(23).setCellValue(item.get("EST")!=null?item.get("EST").toString():"");
				dataRow.createCell(24).setCellValue(item.get("DESC_ZONA")!=null?item.get("DESC_ZONA").toString():"");
				dataRow.createCell(25).setCellValue(item.get("IND_CUMPLMNTO")!=null?item.get("IND_CUMPLMNTO").toString():"");
				break;
	
			default:
				break;
			}
		}
		
		try{
			docExcel = File.createTempFile("AGC_Monitoreo", ".xlsx");
			FileOutputStream file = new FileOutputStream(docExcel);
			workbook.write(file);
			file.close();
		}catch (IOException e) {
			logger.error("[AGC: MonitoreoServicioImpl - generarExcel()] - "+e.getMessage());
		}						
		return docExcel;
	}

	@SuppressWarnings("unchecked")
	public File generarExcelDetalle(MonitoreoCabecera monitoreo, List<Object> listaObjetos, String idActividad) {
		File docExcel = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Monitoreo");
		String[] headers = new String[] {};
		
		switch (idActividad) {
		case "TE":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Lectura", "Medidor", "Incidencia lectura 1", 
					"Incidencia lectura 2", "Incidencia lectura 3", "Medidor observado", "F. Ejecución", "H. Ejecución", "Estado", 
					"Zona", "Cumplimiento" };
			break;
		case "IC":
			headers = new String[] {"Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Medidor", "Orden Servicio", "Tipología Ord. Serv.", "Fecha Visita",
					"Hora Inicio", "Hora Fin", "Servicio", "Lectura", "Estado Medidor", "Incidencia Medidor", "Imposibilidad",
					"Inspección Realizada", "Detalle", "Estado", "Zona", "Cumplimiento"};
			break;
		case "DC":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Nro Carta / Resolución", "Nro Visita", 
					"Tipo de Entrega", "Fecha Notificación", "Hora Notificación", "Incidencia", "Descripción", "Estado", "Zona", "Cumplimiento" };
			break;
		case "DA":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Incidencia", "Descripción", "Tipo Entrega", 
					"Fecha Distribución", "Hora Distribución", "Estado", "Zona", "Cumplimiento" };
			break;
		case "ME":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Serv.", "Tipología de Ord Serv.", "Tipo Actividad", 
					"Codigo Observac.", "Tipo Instalación", "Medidor Instalado", "Medidor Retirado", "Fecha Ejecución", "Hora Ejecución", "Estado",
					"Zona", "Cumplimiento" };
			break;
		case "CR":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Servicio", "Tipología Ord Serv.", "Tipo Actividad", 
					"Codigo Actividad", "Codigo de Observac.", "Lectura Cierre", "F. Ejecución", "H. Ejecución", "Estado", "Zona", "Cumplimiento"
 };
			break;
		case "SO":
			headers = new String[] { "Código", "Nombre", "Actividad", "Carga Programada", "Carga Ejecutada", 
					"Carga Pendiente", "% Avance", "Semáforo", "Fecha Programación", "F. Ejecución Inicio", "H. Ejecución Inicio", 
					"F. Ejecución Fin", "H. Ejecución Fin", "Dirección", "Suministro", "Orden Servicio", "Tipología Ord. Servicio",
					"Tipo Actividad", "Cod. de Actividad", "Cod. Observación", "Lectura Cierre", "F. Ejecución", "H. Ejecución", "Estado",
					"Zona", "Cumplimiento" };
			break;

		default:
			break;
		}
		
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
			
		for (int i = 0; i < listaObjetos.size(); ++i) {
			XSSFRow dataRow = sheet.createRow(i + 1);
			dataRow.createCell(0).setCellValue(i+1);
			dataRow.createCell(1).setCellValue(monitoreo.getTrabajador().getNombre() != null ?  monitoreo.getTrabajador().getNombre() : monitoreo.getTrabajador().getCodigo().toString());	
			dataRow.createCell(2).setCellValue(monitoreo.getActividad().getDescripcion());
			dataRow.createCell(3).setCellValue(monitoreo.getCargaProgramada());
			dataRow.createCell(4).setCellValue(monitoreo.getCargaEjecutada());
			dataRow.createCell(5).setCellValue(monitoreo.getCargaPendiente());
			dataRow.createCell(6).setCellValue(monitoreo.getAvance());
			dataRow.createCell(7).setCellValue(monitoreo.getSemaforo()==2?"Rojo":(monitoreo.getSemaforo()==3?"Amarillo":"Verde"));
			dataRow.createCell(8).setCellValue(monitoreo.getFechaProgramacion());
			dataRow.createCell(9).setCellValue(monitoreo.getFechaEjecucionInicio());
			dataRow.createCell(10).setCellValue(monitoreo.getHoraEjecucionInicio());
			dataRow.createCell(11).setCellValue(monitoreo.getFechaEjecucionFin());
			dataRow.createCell(12).setCellValue(monitoreo.getHoraEjecucionFin());

			LinkedHashMap<String, Object> item =  (LinkedHashMap<String, Object>) listaObjetos.get(i);

			dataRow.createCell(13).setCellValue(item.get("direccion")!=null?item.get("direccion").toString():"");
			dataRow.createCell(14).setCellValue(item.get("suministro")!=null?item.get("suministro").toString():"");
			switch (idActividad) {
			case "TE":

//				dataRow.createCell(13).setCellValue(item.get("direccion")!=null?item.get("direccion").toString():"");
//				dataRow.createCell(14).setCellValue(item.get("suministro")!=null?item.get("suministro").toString():"");
				dataRow.createCell(15).setCellValue(item.get("lectura")!=null?item.get("lectura").toString():"");
				dataRow.createCell(16).setCellValue(item.get("medidor")!=null?item.get("medidor").toString():"");
				dataRow.createCell(17).setCellValue(item.get("incLectura1")!=null?item.get("incLectura1").toString():"");
				dataRow.createCell(18).setCellValue(item.get("incLectura2")!=null?item.get("incLectura2").toString():"");
				dataRow.createCell(19).setCellValue(item.get("incLectura3")!=null?item.get("incLectura3").toString():"");
				dataRow.createCell(20).setCellValue(item.get("medidorObservado")!=null?item.get("medidorObservado").toString():"");
				dataRow.createCell(21).setCellValue(item.get("fechaEjecucion")!=null?item.get("fechaEjecucion").toString():"");
				dataRow.createCell(22).setCellValue(item.get("horaEjecucion")!=null?item.get("horaEjecucion").toString():"");
				dataRow.createCell(23).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion")).toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(25).setCellValue((((LinkedHashMap<String, Object>)item.get("cumplimiento")))
						!=null?(((LinkedHashMap<String, Object>)item.get("cumplimiento")).get("detalle")).toString():"");
				break;
			case "IC":

//				dataRow.createCell(13).setCellValue(item.get("direccion")!=null?item.get("direccion").toString():"");
//				dataRow.createCell(14).setCellValue(item.get("suministro")!=null?item.get("suministro").toString():"");
				dataRow.createCell(15).setCellValue(item.get("medidor")!=null?item.get("medidor").toString():"");
				dataRow.createCell(16).setCellValue(item.get("ordenServicio")!=null?item.get("ordenServicio").toString():"");
				dataRow.createCell(17).setCellValue(item.get("tipologiaOrdServ")!=null?item.get("tipologiaOrdServ").toString():"");
				dataRow.createCell(18).setCellValue(item.get("fechaVisita")!=null?item.get("fechaVisita").toString():"");
				dataRow.createCell(19).setCellValue(item.get("horaInicio")!=null?item.get("horaInicio").toString():"");
				dataRow.createCell(20).setCellValue(item.get("horaFin")!=null?item.get("horaFin").toString():"");
				dataRow.createCell(21).setCellValue(item.get("servicio")!=null?item.get("servicio").toString():"");
				dataRow.createCell(22).setCellValue(item.get("lectura")!=null?item.get("lectura").toString():"");
				dataRow.createCell(23).setCellValue((((LinkedHashMap<String, Object>)item.get("estadoMedidor")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estadoMedidor")).get("detalle")).toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("incidenciaMedidor")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("incidenciaMedidor")).get("detalle")).toString():"");
				dataRow.createCell(25).setCellValue((((LinkedHashMap<String, Object>)item.get("imposibilidad")).get("descripcionCorta"))
						!=null?(((LinkedHashMap<String, Object>)item.get("imposibilidad")).get("descripcionCorta")).toString():"");
				dataRow.createCell(26).setCellValue(item.get("inspeccionRealizada")!=null?item.get("inspeccionRealizada").toString():"");
				dataRow.createCell(27).setCellValue(item.get("detalle")!=null?item.get("detalle").toString():"");
				dataRow.createCell(28).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("detalle")).toString():"");
				dataRow.createCell(29).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(30).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");
				break;
			case "DC":
//				dataRow.createCell(13).setCellValue(item.get("direccion")!=null?item.get("direccion").toString():"");
//				dataRow.createCell(14).setCellValue(item.get("suministro")!=null?item.get("suministro").toString():"");
				dataRow.createCell(15).setCellValue(item.get("nroCarta")!=null?item.get("nroCarta").toString():"");
				dataRow.createCell(16).setCellValue(item.get("nroVisita")!=null?item.get("nroVisita").toString():"");
				dataRow.createCell(17).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoEntrega")).get("valor"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoEntrega")).get("valor")).toString():"");
				dataRow.createCell(18).setCellValue(item.get("fechaNotificacion")!=null?item.get("fechaNotificacion").toString():"");
				dataRow.createCell(19).setCellValue(item.get("horaNotificacion")!=null?item.get("horaNotificacion").toString():"");
				dataRow.createCell(20).setCellValue((((LinkedHashMap<String, Object>)item.get("incidencia")).get("codigo"))
						!=null?(((LinkedHashMap<String, Object>)item.get("incidencia")).get("codigo")).toString():"");
				dataRow.createCell(21).setCellValue(item.get("descripcion")!=null?item.get("descripcion").toString():"");
				dataRow.createCell(22).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("estado"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("estado")).toString():"");
				dataRow.createCell(23).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");

				break;
			case "DA":
				dataRow.createCell(15).setCellValue((((LinkedHashMap<String, Object>)item.get("incidencia")).get("codigo"))
						!=null?(((LinkedHashMap<String, Object>)item.get("incidencia")).get("codigo")).toString():"");
				dataRow.createCell(16).setCellValue((((LinkedHashMap<String, Object>)item.get("incidencia")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("incidencia")).get("detalle")).toString():"");
				dataRow.createCell(17).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoEntrega")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoEntrega")).get("detalle")).toString():"");
				dataRow.createCell(18).setCellValue(item.get("fechaDistribucion")!=null?item.get("fechaDistribucion").toString():"");
				dataRow.createCell(19).setCellValue(item.get("horaDistribucion")!=null?item.get("horaDistribucion").toString():"");
				dataRow.createCell(20).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion")).toString():"");
				dataRow.createCell(21).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(22).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");
				break;
			case "ME":
				dataRow.createCell(15).setCellValue(item.get("ordenServicio")!=null?item.get("ordenServicio").toString():"");
				dataRow.createCell(16).setCellValue(item.get("tipologia")!=null?item.get("tipologia").toString():"");
				dataRow.createCell(17).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("valor"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("valor")).toString():"");
				dataRow.createCell(18).setCellValue(item.get("codObservacion")!=null?item.get("codObservacion").toString():"");
				dataRow.createCell(19).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoInstalacion")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoInstalacion")).get("descripcion")).toString():"");
				dataRow.createCell(20).setCellValue(item.get("medidorInstalado")!=null?item.get("medidorInstalado").toString():"");
				dataRow.createCell(21).setCellValue(item.get("medidorRetirado")!=null?item.get("medidorRetirado").toString():"");
				dataRow.createCell(22).setCellValue(item.get("fechaEjecucion")!=null?item.get("fechaEjecucion").toString():"");
				dataRow.createCell(23).setCellValue(item.get("horaEjecucion")!=null?item.get("horaEjecucion").toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion")).toString():"");
				dataRow.createCell(25).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(26).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");
				break;
			case "CR":
				dataRow.createCell(15).setCellValue(item.get("ordenServicio")!=null?item.get("ordenServicio").toString():"");
				dataRow.createCell(16).setCellValue(item.get("tipOrdServicio")!=null?item.get("tipOrdServicio").toString():"");
				dataRow.createCell(17).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("codigo"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("codigo")).toString():"");
				dataRow.createCell(18).setCellValue(item.get("codigoActividad")!=null?item.get("codigoActividad").toString():"");
				dataRow.createCell(19).setCellValue(item.get("codObservacion")!=null?item.get("codObservacion").toString():"");
				dataRow.createCell(20).setCellValue(item.get("lecturaCierre")!=null?item.get("lecturaCierre").toString():"");
				dataRow.createCell(21).setCellValue(item.get("fechaEjecucion")!=null?item.get("fechaEjecucion").toString():"");
				dataRow.createCell(22).setCellValue(item.get("horaEjecucion")!=null?item.get("horaEjecucion").toString():"");
				dataRow.createCell(23).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion")).toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(25).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");
				break;
			case "SO":
				dataRow.createCell(15).setCellValue(item.get("ordenServicio")!=null?item.get("ordenServicio").toString():"");
				dataRow.createCell(16).setCellValue(item.get("tipOrdServicio")!=null?item.get("tipOrdServicio").toString():"");
				dataRow.createCell(17).setCellValue((((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("codigo"))
						!=null?(((LinkedHashMap<String, Object>)item.get("tipoActividad")).get("codigo")).toString():"");
				dataRow.createCell(18).setCellValue(item.get("codActividad")!=null?item.get("codActividad").toString():"");
				dataRow.createCell(19).setCellValue(item.get("codObservacion")!=null?item.get("codObservacion").toString():"");
				dataRow.createCell(20).setCellValue(item.get("lecturaCierre")!=null?item.get("lecturaCierre").toString():"");
				dataRow.createCell(21).setCellValue(item.get("fechaEjecucion")!=null?item.get("fechaEjecucion").toString():"");
				dataRow.createCell(22).setCellValue(item.get("horaEjecucion")!=null?item.get("horaEjecucion").toString():"");
				dataRow.createCell(23).setCellValue((((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion"))
						!=null?(((LinkedHashMap<String, Object>)item.get("estado")).get("descripcion")).toString():"");
				dataRow.createCell(24).setCellValue((((LinkedHashMap<String, Object>)item.get("zona")).get("detalle"))
						!=null?(((LinkedHashMap<String, Object>)item.get("zona")).get("detalle")).toString():"");
				dataRow.createCell(25).setCellValue((((LinkedHashMap<String, Object>)item.get("completa")))
						!=null?(((LinkedHashMap<String, Object>)item.get("completa")).get("detalle")).toString():"");
				break;
	
			default:
				break;
			}
		}
		
		try{
			docExcel = File.createTempFile("AGC_Monitoreo", ".xlsx");
			FileOutputStream file = new FileOutputStream(docExcel);
			workbook.write(file);
			file.close();
		}catch (IOException e) {
			logger.error("[AGC: MonitoreoServicioImpl - generarExcel()] - "+e.getMessage());
		}						
		return docExcel;
	}
}
