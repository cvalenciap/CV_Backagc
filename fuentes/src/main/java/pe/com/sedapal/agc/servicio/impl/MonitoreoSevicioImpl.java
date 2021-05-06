package pe.com.sedapal.agc.servicio.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.dao.IMonitoreoDAO;
import pe.com.sedapal.agc.model.DuracionParametro;
import pe.com.sedapal.agc.model.MonitoreoCabecera;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.Trabajador;
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
import pe.com.sedapal.agc.servicio.IMonitoreoServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.GeneraExcelUtil;

@Service
public class MonitoreoSevicioImpl implements IMonitoreoServicio {

	@Autowired
	private IMonitoreoDAO dao;	

	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;

	@Value("${app.config.carpeta.monitoreo}")
	private String packageMonitoreo;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	private Error errorServicio;

	@Override
	public Map<String, Object> listarAsignacionTrabajo(MonitoreoRequest requestMonitoreo) {

		return dao.listarAsignacionTrabajo(requestMonitoreo);
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaMonitoreoCab(Integer idPers, Integer idPerfil) {

		return dao.obtenerParametrosBusquedaMonitoreoCab(idPers, idPerfil);
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaCiclo(Integer idOficina, String idActividad,
			String idPeriodo) {

		return dao.obtenerParametrosBusquedaCiclo(idOficina, idActividad, idPeriodo);
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

	@Override
	public Map<String, Object> listarDetalleAsignacionTrabajo(MonitoreoRequest requestMonitoreo) {
		return dao.listarDetalleAsignacionTrabajo(requestMonitoreo);
	}

	@Override
	public Map<String, Object> anularRegistroCabecera(Integer idCab, Integer codEmp) {
		return dao.anularRegistroCabecera(idCab, codEmp);
	}

	@Override
	public DuracionParametro obtenerParametrosDuracion() {
		return dao.obtenerParametrosDuracion();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> cargaArchivoProgramacion(MultipartFile file, Integer codEmp, String codActividad,
			Integer codEmpresa, Integer codOficina) throws IOException, Exception {
		// validar archivo
		Map<String, Object> cargaArchivo = new HashMap<String, Object>();
		try {

			InputStream fileIS = file.getInputStream();
			BufferedReader bufferRPlus = new BufferedReader(new InputStreamReader(fileIS));

			String[] campos = null;
			String linea;

			boolean validacion = false;
			List<Object> listCarga = new ArrayList<Object>();
			linea = bufferRPlus.readLine();
			campos = linea.toLowerCase().split("\t");
			char[] contadorComa;
			Integer contador = 0;
			// String [] arrayBase =
			// {"id","nrocarga","actividad","nroorden","codigopersonal","suministro","fecha
			// asignacion"};
			String[] arrayBase = { "nrocarga", "actividad", "nroorden", "codigopersonal", "nrosuministro",
					"fechaprogramacion" };

			if (Arrays.equals(campos, arrayBase)) {
				validacion = true;
			}
			if (validacion) {
				while ((linea = bufferRPlus.readLine()) != null) {
					if (!linea.equals("")) {
						campos = linea.toLowerCase().split("\t");
						linea = linea.replaceAll("\t", ",");
						contadorComa = linea.toCharArray();
						contador = 0;
						
						for (int i=0; i<contadorComa.length; i++) {
							if(contadorComa[i]==',') {
								contador++;	
							}
						}
						
						if (contador != 5) {
							validacion = false;
							break;
						}
						
						if (validacion) {
							listCarga.add(linea);
						}
					}
				}
				
				if (validacion) {
					cargaArchivo = dao.cargaArchivoProgramacion(listCarga, codEmp, codActividad, codEmpresa, codOficina);
				}else{
					cargaArchivo.put("N_RESP", "0");
					cargaArchivo.put("mensaje", "Error en el formato del archivo.");
				}
				
			} else {
				cargaArchivo.put("N_RESP", "0");
				cargaArchivo.put("mensaje", "Cabecera incorrecta, por favor revisar archivo de carga");
			}
			bufferRPlus.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cargaArchivo;
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaBandejaMonitoreo(Integer idPers, Integer idPerfil) {
		return dao.obtenerParametrosBusquedaBandejaMonitoreo(idPers, idPerfil);
	}

	@Override
	public Map<String, Object> listarMonitoreoCabecera(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) throws ParseException {
		return dao.listarMonitoreoCabecera(requestMonitoreo, idActividad);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalle(String idEmp, String fechaProgramacion, String idActividad) {
		Map<String, Object> retorno = null;

		Trabajador trabajador = new Trabajador();
		trabajador.setCodigo(Integer.parseInt(idEmp));

		switch (idActividad) {
		case "TE":
			MonitoreoDetalleRequestTE requestMonitoreoTE = new MonitoreoDetalleRequestTE();
			requestMonitoreoTE.setTrabajador(trabajador);
			requestMonitoreoTE.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleTE(requestMonitoreoTE);
			break;
		case "IC":
			MonitoreoDetalleRequestIC requestMonitoreoIC = new MonitoreoDetalleRequestIC();
			requestMonitoreoIC.setTrabajador(trabajador);
			requestMonitoreoIC.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleIC(requestMonitoreoIC);
			break;
		case "DA":
			MonitoreoDetalleRequestDA requestDA = new MonitoreoDetalleRequestDA();
			requestDA.setTrabajador(trabajador);
			requestDA.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleDA(requestDA);
			break;
		case "DC":
			MonitoreoDetalleRequestDC requestDC = new MonitoreoDetalleRequestDC();
			requestDC.setTrabajador(trabajador);
			requestDC.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleDC(requestDC);
			break;
		case "ME":
			MonitoreoDetalleRequestME requestME = new MonitoreoDetalleRequestME();
			requestME.setTrabajador(trabajador);
			requestME.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleME(requestME);
			break;
		case "CR":
			MonitoreoDetalleRequestCR requestCR = new MonitoreoDetalleRequestCR();
			requestCR.setTrabajador(trabajador);
			requestCR.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleCR(requestCR);
			break;
		case "SO":
			MonitoreoDetalleRequestSO requestSO = new MonitoreoDetalleRequestSO();
			requestSO.setTrabajador(trabajador);
			requestSO.setFechaProgramacion(fechaProgramacion);
			retorno = dao.listarMonitoreoDetalleSO(requestSO);
			break;
		}
		return retorno;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleTE(MonitoreoDetalleRequestTE requestMonitoreo) {

		return dao.listarMonitoreoDetalleTE(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleIC(MonitoreoDetalleRequestIC requestMonitoreo) {

		return dao.listarMonitoreoDetalleIC(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleDA(MonitoreoDetalleRequestDA requestMonitoreo) {
		return dao.listarMonitoreoDetalleDA(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleDC(MonitoreoDetalleRequestDC requestMonitoreo) {
		return dao.listarMonitoreoDetalleDC(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleME(MonitoreoDetalleRequestME requestMonitoreo) {
		return dao.listarMonitoreoDetalleME(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleCR(MonitoreoDetalleRequestCR requestMonitoreo) {
		return dao.listarMonitoreoDetalleCR(requestMonitoreo);
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleSO(MonitoreoDetalleRequestSO requestMonitoreo) {
		return dao.listarMonitoreoDetalleSO(requestMonitoreo);
	}


	@Override
	public File generarArchivoExcelMonitoreo(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) {
		GeneraExcelUtil excelUtil = new GeneraExcelUtil();
		List<Map<String, Object>> listResultado = new ArrayList<>();
		
		listResultado = dao.generarArchivoExcelMonitoreo(requestMonitoreo, idActividad);
		
		File archivoExcel = excelUtil.generarExcel(listResultado, idActividad);
		
		return archivoExcel; 
	}

	@Override
	public File generarArchivoExcelMonitoreoDetalle(MonitoreoCabecera monitoreo, List<Object> listaObjetos, String idActividad) {
		GeneraExcelUtil excelUtil = new GeneraExcelUtil();
				
		File archivoExcel = excelUtil.generarExcelDetalle(monitoreo, listaObjetos, idActividad);
		
		return archivoExcel; 
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> cargaArchivoProgramacionMasiva(MultipartFile file, Integer codEmp, String codActividad,
			Integer codEmpresa, String nroCarga, String fecReasignacion) throws IOException, Exception {
		// validar archivo
		Map<String, Object> cargaArchivo = new HashMap<String, Object>();
		try {

			InputStream fileIS = file.getInputStream();
			BufferedReader bufferRPlus = new BufferedReader(new InputStreamReader(fileIS));

			String[] campos = null;
			String linea;

			boolean validacion = false;
			List<Object> listCarga = new ArrayList<Object>();
			linea = bufferRPlus.readLine();
			campos = linea.toLowerCase().split("\t");
			// String [] arrayBase =
			// {"id","nrocarga","actividad","nroorden","codigopersonal","suministro","fecha
			// asignacion"};
			String[] arrayBase = { "nrocarga", "actividad", "nroorden", "codigopersonal", "nrosuministro",
					"fechaprogramacion" };

			if (Arrays.equals(campos, arrayBase)) {
				validacion = true;
			}
			if (validacion) {
				while ((linea = bufferRPlus.readLine()) != null) {
					if (!linea.equals("")) {
						campos = linea.toLowerCase().split("\t");

						if (validacion) {
							listCarga.add(linea.replaceAll("\t", ","));
						}
					}
				}
				cargaArchivo = dao.cargaArchivoProgramacionMasiva(listCarga, codEmp, codActividad, codEmpresa, nroCarga, fecReasignacion);
			} else {
				cargaArchivo.put("N_RESP", "0");
				cargaArchivo.put("mensaje", "Cabecera incorrecta, por favor revisar archivo de carga");
			}
			bufferRPlus.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cargaArchivo;
	}

	@Override
	public Map<String, Object> listaDetalleReasignaciones(MonitoreoRequest requestMonitoreo) {
		return dao.listaDetalleReasignaciones(requestMonitoreo);
	}

	@Override
	public Map<String, Object> reasignarTrabajadorManual(ReprogramacionRequest requestReprogramacion) {
		Integer valida;
		Map<String, Object> retorno;
		valida = 0;
		
		if (requestReprogramacion.getTrabajadorAntiguo() != null) {
			if (requestReprogramacion.getTrabajadorAntiguo().getCodigo() != null &&
				requestReprogramacion.getTrabajadorNuevo().getCodigo() != null &&
				requestReprogramacion.getNroCarga() != null &&
				requestReprogramacion.getSuministro() != null) {
						valida = 1;
			}
		}
		
		if (valida == 1) {
			 retorno = dao.reasignarTrabajadorManual(requestReprogramacion);
		}else {
			retorno = new HashMap<String,Object>();
			retorno.put("N_RESP", BigDecimal.valueOf(1));
			retorno.put("V_EJEC", "Información incompleta.");
		}
		return retorno;
		
	}
	
	/*agregado para visor de imagenes - rramirez*/
	@Override
	public String[] obtenerAdjuntosMonitoreoImagen(VisorMonitoreoRequest request) {
		String imagenes[] = null;
		List<String> listaAdjuntos =  new ArrayList<String>();
		this.errorServicio = null;
		
//		Integer resultado = daoLog.registrarAccionLog(request);
//		if(resultado == 1) {	
			try {
				// Obtengo lista de adjuntos	
					listaAdjuntos = listarAdjuntosMonitoreo(request);

				
				if(listaAdjuntos.size() > 0 || listaAdjuntos != null) {
					List<String> listImg = new ArrayList<String>();
					// Bloque para generar archivo PDF con todos los adjuntos
				    boolean existeError = false;
				    
					int cont = 0;
					String nombreImg = "";
					for(String adjunto: listaAdjuntos) {
						String extension = FilenameUtils.getExtension(adjunto).toUpperCase().trim();				
						
						InputStream instream = obtenerArchivoFileServer(adjunto);
						
						if(instream.available() > 0) {
							if(extension.equalsIgnoreCase("JPG")) {
								byte[] buffer = Constantes.toByteArray(instream);
			                	instream.read(buffer, 0, buffer.length);
			                	instream.close();
			                	String imageStr = Base64.encodeBase64String(buffer);
			                	nombreImg = adjunto.substring(0,adjunto.length()-4);
			                	listImg.add(imageStr /*+ "»" + new Date() + "»" + nombreImg*/);
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
//		}							
		return imagenes;
	}
	
	public InputStream obtenerArchivoFileServer(String rutaArchivo) {
		InputStream instream = null;
		final HttpGet request = new HttpGet(apiEndpointFileServer + packageMonitoreo+ "/" + rutaArchivo);
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
	
	private List<String> listarAdjuntosMonitoreo(VisorMonitoreoRequest request){
		List<String> listaAdjuntos = new ArrayList<>();
		if (!request.getImagen1().trim().equals("")) {
			listaAdjuntos.add(request.getImagen1());
		}
		if (!request.getImagen2().trim().equals("")) {
			listaAdjuntos.add(request.getImagen2());
		}
		if (!request.getImagen3().trim().equals("")) {
			listaAdjuntos.add(request.getImagen3());
		}
		
		return listaAdjuntos;
	}
}
