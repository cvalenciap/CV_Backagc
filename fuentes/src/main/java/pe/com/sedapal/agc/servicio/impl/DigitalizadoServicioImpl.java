package pe.com.sedapal.agc.servicio.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import pe.com.sedapal.agc.dao.IDigitalizadoDAO;
import pe.com.sedapal.agc.dao.ILogDigitalizadoDAO;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.Digitalizado;
import pe.com.sedapal.agc.model.DuracionDigitalizados;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.request.DigitalizadoRequest;
import pe.com.sedapal.agc.model.request.VisorDigitalizadoRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IDigitalizadoServicio;
import pe.com.sedapal.agc.util.Constantes;

@Service
public class DigitalizadoServicioImpl implements IDigitalizadoServicio {
		
	@Autowired
	private IDigitalizadoDAO dao;
	
	@Autowired
	private ILogDigitalizadoDAO daoLog;
	
	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Error errorServicio;	
	
	@Override
	public List<Digitalizado> listarDigitalizados(DigitalizadoRequest requestDigitalizado, Integer pagina,
			Integer registros) {		
		return dao.listarDigitalizados(requestDigitalizado, pagina, registros);
	}
	@Override
	public Map<String, Object> listarDigitalizadosPorActividad(DigitalizadoRequest requestDigitalizado, Integer pagina,
			Integer registros) {		
		return dao.listarDigitalizadosPorActividad(requestDigitalizado, pagina, registros);
	}
	
	@Override
	public File generarArchivoExcelDigitalizado(DigitalizadoRequest requestDigitalizado, Integer pagina,
			Integer registros) {
		File archivoExcel = null;	
		List<Digitalizado> listaDigitalizados = new ArrayList<Digitalizado>();
		listaDigitalizados = dao.listarDigitalizados(requestDigitalizado, pagina, registros);
		
		if(listaDigitalizados.size() > 0) {
			archivoExcel = generarExcel(listaDigitalizados);
		}
				
		return archivoExcel;
	}
	
	private File generarExcel(List<Digitalizado> lista) {
		File docExcel = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Digitalizados");
		String[] headers = new String[] { "Item", "Nro. Suministro", "Actividad", "Ord.Serv/Ord.Trab/Num.Cédula", 
				"Tipología Ord.Trab/Ord.Serv", "Nro. Carga", "Fecha Ejecución","Tiene Adjunto" };
		
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
		
		for (int i = 0; i < lista.size(); ++i) {
			XSSFRow dataRow = sheet.createRow(i + 1);
			dataRow.createCell(0).setCellValue(i+1);
			dataRow.createCell(1).setCellValue(lista.get(i).getSuministro());	
			dataRow.createCell(2).setCellValue(lista.get(i).getActividad().getDescripcion());
			dataRow.createCell(3).setCellValue(lista.get(i).getOrdTrabOrdServCedu());
			dataRow.createCell(4).setCellValue(lista.get(i).getTipologia());
			dataRow.createCell(5).setCellValue(lista.get(i).getNumeroCarga());
			dataRow.createCell(6).setCellValue(lista.get(i).getFechaEjecucion());
			//cguerra
			if(lista.get(i).getCantAdj()>0 || lista.get(i).getCantImg()>0) {
				dataRow.createCell(7).setCellValue("SI");
			}else {
				dataRow.createCell(7).setCellValue("NO");	
			}			
			//cguerra
		}
		
		try{
			docExcel = File.createTempFile("AGC_Digitalizados", ".xlsx");
			FileOutputStream file = new FileOutputStream(docExcel);
			workbook.write(file);
			file.close();
		}catch (IOException e) {
			logger.error("[AGC: DigitalizadoServicioImpl - generarExcel()] - "+e.getMessage());
		}						
		return docExcel;
	}
	
	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaDigitalizados(Integer idPers, Integer idPerfil) {		
		return dao.obtenerParametrosBusquedaDigitalizados(idPers, idPerfil);
	}
	
	@Override
	public Integer registrarLogAccion(VisorDigitalizadoRequest requestVisorDigitalizado) {
		Integer resultado = daoLog.registrarAccionLog(requestVisorDigitalizado);
		return resultado;
	}
	
	@Override
	public String obtenerAdjuntosDigitalizados(VisorDigitalizadoRequest request) {
		String rutaArchivoGenerado = null;
		File tempPDF = null;
		List<Adjunto> listaAdjuntos = new ArrayList<Adjunto>();
		Document pdfDocument = null;
		this.errorServicio = null;
		
		Integer resultado = daoLog.registrarAccionLog(request);
		if(resultado == 1) {	
			try {
				// Obtengo lista de adjuntos
				if(request.getActividad().equals("SG")) {			
					listaAdjuntos = dao.listarAdjuntosDigitalizadosSGIO(request);
				}else {				
					listaAdjuntos = dao.listarAdjuntosDigitalizadosCT(request);
				}
				
				if(listaAdjuntos.size() > 0 || listaAdjuntos != null) {		
					// Bloque para generar archivo PDF con todos los adjuntos
					tempPDF = File.createTempFile("AGC-Temp-Pdf-Visor-", ".pdf");
					pdfDocument = new Document();
				    PdfCopy copy = new PdfCopy(pdfDocument, new FileOutputStream(tempPDF.getAbsolutePath()));
				    boolean existeError = false;
				    
				    pdfDocument.open();
					
					for(Adjunto adjunto: listaAdjuntos) {
						String extension = FilenameUtils.getExtension(adjunto.getRuta()).toUpperCase().trim();				
						if(extension.equals("PDF")) {
							InputStream instream = obtenerArchivoFileServer(adjunto.getRuta());
							
							if(instream.available() > 0) {
								//if(extension.equals("PDF")) {
									// Copio todas las paginas del PDF en el nuevo documento PDF
					        		PdfReader reader = new PdfReader(instream);
					        		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					                    copy.addPage(copy.getImportedPage(reader, i));
					                 }
					        		/*Modificado para visor de imagenes - rramirez*/
								/*}else {
									// Creo un nuevo PDF en memoria, pongo la imagen en la primera pagina y luego uso PdfCopy para copiarlo en el PDF principal
									Document imageDocument = new Document();
					                ByteArrayOutputStream imageDocumentOutputStream = new ByteArrayOutputStream();
					                PdfWriter imageDocumentWriter = PdfWriter.getInstance(imageDocument, imageDocumentOutputStream);
					                
					                imageDocument.open();
		
					                if (imageDocument.newPage()) {			                	
					                	byte[] buffer = Constantes.toByteArray(instream);
					                	Image image = Image.getInstance(buffer);
					                	
					                	// Redimencionar imagen
					                	float documentWidth = imageDocument.getPageSize().getWidth() - imageDocument.leftMargin() - imageDocument.rightMargin();
					                	float documentHeight = imageDocument.getPageSize().getHeight() - imageDocument.topMargin() - imageDocument.bottomMargin();
					                	image.scaleToFit(documentWidth, documentHeight);
					                	// Centrar imagen
					                	float x = (PageSize.A4.getWidth() - image.getScaledWidth()) / 2;
					                	float y = (PageSize.A4.getHeight() - image.getScaledHeight()) / 2;
					                	image.setAbsolutePosition(x, y);
					                		                	
					                	imageDocument.add(image);
					                    imageDocument.close();
					                    imageDocumentWriter.close();
		
					                    PdfReader imageDocumentReader = new PdfReader(imageDocumentOutputStream.toByteArray());
					                    copy.addPage(copy.getImportedPage(imageDocumentReader, 1));
					                }							
								}*/
							}else {
								existeError = true;
								// Agrego pagina solo para poder cerrar el objeto Document
								copy.addPage(PageSize.A4, 0);
								break;							
							}
						}
					}
					pdfDocument.close();
					
					// Guardo PDF generado en FileServer
					if(!existeError) {
						String rutaPdfDocument = guardarArchivoFileServer(tempPDF);
						if(rutaPdfDocument != null && !rutaPdfDocument.equals("")) {
							rutaArchivoGenerado = rutaPdfDocument;
						}
					}	
					tempPDF.delete();
			  }				
			} catch(Exception e) {
				tempPDF.delete();
				setErrorServicio(new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999)));
				logger.error("[AGC: DigitalizadoServicioImpl - obtenerAdjuntosDigitalizados()] - "+e.getMessage());
			}
		}							
		return rutaArchivoGenerado;
	}
	
	/*agregado para visor de imagenes - rramirez*/
	@Override
	public String[] obtenerAdjuntosDigitalizadosImagen(VisorDigitalizadoRequest request) {
		String imagenes[] = null;
		List<Adjunto> listaAdjuntos = new ArrayList<Adjunto>();
		this.errorServicio = null;
		
		Integer resultado = daoLog.registrarAccionLog(request);
		if(resultado == 1) {	
			try {
				// Obtengo lista de adjuntos
				if(request.getActividad().equals("SG")) {			
					listaAdjuntos = dao.listarAdjuntosDigitalizadosSGIO(request);
				}else {				
					listaAdjuntos = dao.listarAdjuntosDigitalizadosCT(request);
				}
				
				if(listaAdjuntos.size() > 0 || listaAdjuntos != null) {
//					imagenes = new String[listaAdjuntos.size()];
					List<String> listImg = new ArrayList<String>();
					// Bloque para generar archivo PDF con todos los adjuntos
				    boolean existeError = false;
				    
					int cont = 0;
					String nombreImg = "";
					for(Adjunto adjunto: listaAdjuntos) {
						String extension = FilenameUtils.getExtension(adjunto.getRuta()).toUpperCase().trim();				
						
						InputStream instream = obtenerArchivoFileServer(adjunto.getRuta());
						
						if(instream.available() > 0) {
							if(extension.equals("JPG")) {
								byte[] buffer = Constantes.toByteArray(instream);
			                	instream.read(buffer, 0, buffer.length);
			                	instream.close();
			                	String imageStr = Base64.encodeBase64String(buffer);
//								imagenes[cont] = imageStr + "»" + adjunto.getFechaCreacionConHora();
			                	nombreImg = adjunto.getNombre().substring(0,adjunto.getNombre().length()-4);
			                	listImg.add(imageStr + "»" + adjunto.getFechaCreacionConHora() + "»" + nombreImg);
//								System.out.println(imagenes[cont]);
//								imagenes[cont] = Constantes.RUTA_FILESERVER+adjunto.getRuta()+
//										"»"+adjunto.getFechaCreacion();
								cont++;
							}
						}else {
							existeError = true;
							break;							
						}
					}
					imagenes =new String[listImg.size()];
					listImg.toArray(imagenes);
					System.out.println();
			  }				
			} catch(Exception e) {
				setErrorServicio(new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999)));
				logger.error("[AGC: DigitalizadoServicioImpl - obtenerAdjuntosDigitalizados()] - "+e.getMessage());
			}
		}							
		return imagenes;
	}
	/**/
	
	public InputStream obtenerArchivoFileServer(String rutaArchivo) {
		InputStream instream = null;
		final HttpGet request = new HttpGet(apiEndpointFileServer+rutaArchivo);
		HttpResponse response = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(request);
			instream = response.getEntity().getContent();
			if(instream.available() == 0) {
				setErrorServicio(new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1002)));
			}
		} catch(Exception e) {
			setErrorServicio(new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1002)));
			logger.error("[AGC: DigitalizadoServicioImpl - obtenerArchivoFileServer()] - "+e.getMessage());
		}
		return instream;
	}
	
	public String guardarArchivoFileServer(File file) {
		String urlArchivoGuardado = "";
		try {
			HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", new FileBody(file)).build();
			HttpPut put = new HttpPut(apiEndpointFileServer);
			put.setEntity(httpEntity);
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(put);
			InputStream instream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			JSONObject obJson = new JSONObject(reader.readLine());
			urlArchivoGuardado =  obJson.getString("url");
		} catch (Exception exception) {
			setErrorServicio(new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1002)));
			logger.error("[AGC: DigitalizadoServicioImpl - guardarArchivoFileServer()] - "+exception.getMessage());
		}
		return urlArchivoGuardado;
	}
	
	@Override
	public DuracionDigitalizados obtenerParametrosDuracion(){
		return dao.obtenerParametrosDuracion();
	}

	@Override
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}

	@Override
	public Error getError() {
		return this.dao.getError();
	}	
	
	@Override
	public Error getErrorServicio() {
		return errorServicio;
	}

	@Override
	public void setErrorServicio(Error errorServicio) {
		this.errorServicio = errorServicio;
	}
		
}
