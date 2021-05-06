package pe.com.sedapal.agc.servicio.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pe.com.sedapal.agc.dao.IPersonalContratistaDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Archivo;
import pe.com.sedapal.agc.model.CabeceraCargaPersonal;
import pe.com.sedapal.agc.model.DataCorreoCarga;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.RegistroAlta;
import pe.com.sedapal.agc.model.ResponseCargaArchivo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.TramaPersonal;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.request.CesarPersonalRequest;
import pe.com.sedapal.agc.model.request.DarAltaRequest;
import pe.com.sedapal.agc.model.request.PersonalContratistaRequest;
import pe.com.sedapal.agc.model.response.CargaPersonalResponse;
import pe.com.sedapal.agc.model.response.CesarPersonalResponse;
import pe.com.sedapal.agc.model.response.CeseMasivoResponse;
import pe.com.sedapal.agc.model.response.DarAltaResponse;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IPersonalContratistaServicio;
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.CargaArchivosUtil;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.UtilAdjunto;

@Service
public class PersonalContratistaServicioImpl implements IPersonalContratistaServicio {

	private static final Logger logger = LoggerFactory.getLogger(PersonalContratistaServicioImpl.class);

	@Autowired
	private IPersonalContratistaDAO personalContratistaDAO;

	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;

	@Value("${app.config.carpeta.personal}")
	private String carpetaArchivosPersonal;

	@Override
	public Paginacion getPaginacion() {
		return personalContratistaDAO.getPaginacion();
	}

	@Override
	public Error getError() {
		return personalContratistaDAO.getError();
	}

	@Override
	public ListaPaginada<PersonaContratista> listarPersonalContratista(PersonalContratistaRequest request,
			Integer pagina, Integer registros) {
		return personalContratistaDAO.listarPersonalContratista(request, pagina, registros);
	}

	@Override
	public void registrarPersonalContratista(PersonaContratista personaContratista) {
		String endPointPersonalFileServer = apiEndpointFileServer + "/personal";
		String nombreFotoFileServer = null;
		String nombreCVFileServer = null;
		try {
			nombreFotoFileServer = guardarArchivoFileServer(personaContratista.getArchivoFotoPersonal(),
					endPointPersonalFileServer);
			personaContratista.getArchivoFotoPersonal().setRutaArchivo("/personal/" + nombreFotoFileServer);

			nombreCVFileServer = guardarArchivoFileServer(personaContratista.getArchivoCvPersonal(),
					endPointPersonalFileServer);
			personaContratista.getArchivoCvPersonal().setRutaArchivo("/personal/" + nombreCVFileServer);

			personalContratistaDAO.registrarPersonalContratista(personaContratista);
		} catch (AgcException e) {
			if (nombreFotoFileServer != null) {
				eliminarArchivoFileServer(nombreFotoFileServer, endPointPersonalFileServer);
			}
			if (nombreCVFileServer != null) {
				eliminarArchivoFileServer(nombreCVFileServer, endPointPersonalFileServer);
			}
			throw e;
		}
	}

	@Override
	public void actualizarPersonalContratista(PersonaContratista personaContratista) {
		String endPointPersonalFileServer = apiEndpointFileServer + "/personal";
		String nombreFotoFileServer = null;
		String nombreCVFileServer = null;
		try {
			if (personaContratista.getArchivoFotoPersonal() != null) {
				nombreFotoFileServer = guardarArchivoFileServer(personaContratista.getArchivoFotoPersonal(),
						endPointPersonalFileServer);
				personaContratista.getArchivoFotoPersonal().setRutaArchivo("/personal/" + nombreFotoFileServer);
			}
			if (personaContratista.getArchivoCvPersonal() != null) {
				nombreCVFileServer = guardarArchivoFileServer(personaContratista.getArchivoCvPersonal(),
						endPointPersonalFileServer);
				personaContratista.getArchivoCvPersonal().setRutaArchivo("/personal/" + nombreCVFileServer);
			}
			personalContratistaDAO.actualizarPersonalContratista(personaContratista);
			/* Elimino la foto/cv que se ha actualizado */
			if (personaContratista.getArchivoFotoPersonalAnterior() != null && nombreFotoFileServer != null) {
				int iniIndex = personaContratista.getArchivoFotoPersonalAnterior().getRutaArchivo().lastIndexOf("/");
				int finIndex = personaContratista.getArchivoFotoPersonalAnterior().getRutaArchivo().length();
				String nombreFotoFS = personaContratista.getArchivoFotoPersonalAnterior().getRutaArchivo()
						.substring(iniIndex, finIndex);
				eliminarArchivoFileServer(nombreFotoFS, endPointPersonalFileServer);
			}
			if (personaContratista.getArchivoCvPersonalAnterior() != null && nombreCVFileServer != null) {
				int iniIndex = personaContratista.getArchivoCvPersonalAnterior().getRutaArchivo().lastIndexOf("/");
				int finIndex = personaContratista.getArchivoCvPersonalAnterior().getRutaArchivo().length();
				String nombreCVFS = personaContratista.getArchivoCvPersonalAnterior().getRutaArchivo()
						.substring(iniIndex, finIndex);
				eliminarArchivoFileServer(nombreCVFS, endPointPersonalFileServer);
			}
		} catch (AgcException e) {
			if (nombreFotoFileServer != null) {
				eliminarArchivoFileServer(nombreFotoFileServer, endPointPersonalFileServer);
			}
			if (nombreCVFileServer != null) {
				eliminarArchivoFileServer(nombreCVFileServer, endPointPersonalFileServer);
			}
			throw e;
		}
	}

	@Override
	public Map<String, Object> obtenerParametroBandejaPersonal(Integer idPersonal, Integer idPerfil)
			throws AgcException {
		return personalContratistaDAO.obtenerParametrosBandeja(idPersonal, idPerfil);
	}

	private String guardarArchivoFileServer(Archivo archivo, String endPointFileServer) {
		String nombreRandomFileServer = null;
		try {
//			if (CargaArchivosUtil.verificarEstadoFileServer(endPointFileServer)) {
				File tempFile = UtilAdjunto.base64ToFile(archivo);
				if (tempFile.exists()) {
					FileServerResponse fsr = CargaArchivosUtil.enviarArchivoToFileServer(tempFile, endPointFileServer);
					nombreRandomFileServer = fsr.getNombre();
					tempFile.delete();
				}
//			} else {
//				throw new AgcException(Constantes.CargaArchivos.MENSAJE_ERROR_FILESERVER,
//						new Error(Constantes.CodigoErrores.ERROR_SERVICIO, Constantes.MESSAGE_ERROR.get(9999)));
//			}
		} catch (Exception e) {
			throw new AgcException(Constantes.MESSAGE_ERROR.get(9999),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, Constantes.MESSAGE_ERROR.get(9999)));
		}
		return nombreRandomFileServer;
	}

	@Override
	public List<CargaPersonalResponse> procesarCargaPersonal(List<TramaPersonal> dataPersonal, Integer idEmpresa,
			String usuarioCarga, String nomContratista, String nombreArchivo) throws Exception {
		List<CargaPersonalResponse> listaResultados = new LinkedList<>();
		File tempFile = null;
		try {
			CabeceraCargaPersonal cabecera = new CabeceraCargaPersonal();
			cabecera.setV_nom_archivo(UtilAdjunto.obtenerNombreArchivoFromPath(nombreArchivo));
			cabecera.setN_cant_registros(dataPersonal.size());
			cabecera.setV_usuario_reg(usuarioCarga);
			listaResultados = personalContratistaDAO.cargaMasivaPersonal(cabecera, dataPersonal, idEmpresa);
		} catch (AgcException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_SERVICIO);
		} finally {
			CargaArchivosUtil.eliminarArchivoTemporal(tempFile);
		}
		return listaResultados;
	}

	@Override
	public List<TramaPersonal> recuperarDataArchivo(MultipartFile archivo, Integer idEmpresa, String usuarioCarga)
			throws Exception {
		final List<TramaPersonal> lista = new ArrayList<>();
		File tempFile = null;
		try {
			tempFile = CargaArchivosUtil.crearArchivoCargaTemporal(archivo);

			if (!CargaArchivosUtil.esFormatoUTF8(Files.readAllBytes(Paths.get(tempFile.getCanonicalPath())))) {
				Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO, Constantes.CargaMasivaPersonal.MSE008,
						Constantes.CargaMasivaPersonal.MSE008);
				throw new AgcException(Constantes.CargaMasivaPersonal.MSE008, error);
			} else {
				List<String> tramas = Files.readAllLines(Paths.get(tempFile.getCanonicalPath()));
				if (tramas.isEmpty()) {
					Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
							Constantes.CargaMasivaPersonal.MSE001, Constantes.CargaMasivaPersonal.MSE001);
					throw new AgcException(Constantes.CargaMasivaPersonal.MSE001, error);
				} else if (!validarCabeceras(tramas.get(0))) {
					Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
							Constantes.CargaMasivaPersonal.MSE007, Constantes.CargaMasivaPersonal.MSE007);
					throw new AgcException(Constantes.CargaMasivaPersonal.MSE007, error);
				} else if (tramas.size() == 1) {
					Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
							Constantes.CargaMasivaPersonal.MSE001, Constantes.CargaMasivaPersonal.MSE001);
					throw new AgcException(Constantes.CargaMasivaPersonal.MSE001, error);
				} else {
					for (int i = 0; i < tramas.size(); i++) {
						if (i > 0) {
							try {
								TramaPersonal dataPersonal = null;
								List<String> fila = Arrays.asList(tramas.get(i).split("\t"));
								dataPersonal = new TramaPersonal();
								dataPersonal
										.setV_dni(fila.get(0) != null ? fila.get(0).trim().toUpperCase() : null);
								dataPersonal.setV_ape_paterno(
										fila.get(1) != null ? fila.get(1).trim().toUpperCase() : null);
								dataPersonal.setV_ape_materno(
										fila.get(2) != null ? fila.get(2).trim().toUpperCase() : null);
								dataPersonal.setV_nombres(
										fila.get(3) != null ? fila.get(3).trim().toUpperCase() : null);
								dataPersonal.setV_fec_nac(
										fila.get(4) != null ? fila.get(4).trim().toUpperCase() : null);
								dataPersonal.setV_direccion(
										fila.get(5) != null ? fila.get(5).trim().toUpperCase() : null);
								dataPersonal.setV_fec_ingreso(
										fila.get(6) != null ? fila.get(6).trim().toUpperCase() : null);
								dataPersonal.setV_cod_cargo(
										fila.get(7) != null ? fila.get(7).trim().toUpperCase() : null);
								dataPersonal.setV_cod_item(
										fila.get(8) != null ? fila.get(8).trim().toUpperCase() : null);
								dataPersonal.setV_cod_oficina(
										fila.get(9) != null ? fila.get(9).trim().toUpperCase() : null);
								dataPersonal.setV_telf_pers(
										fila.get(10) != null ? fila.get(10).trim().toUpperCase() : null);
								dataPersonal.setV_telf_asig(
										fila.get(11) != null ? fila.get(11).trim().toUpperCase() : null);
								dataPersonal.setV_correo(
										fila.get(12) != null ? fila.get(12).trim().toUpperCase() : null);
								dataPersonal.setV_cod_pers_ctrat(fila.size() == 14
										? fila.get(13) != null ? fila.get(13).trim().toUpperCase() : null
										: null);
								dataPersonal.setV_cod_empresa(String.valueOf(idEmpresa).trim().toUpperCase());
								dataPersonal.setV_usuario_reg(usuarioCarga.toUpperCase());
								lista.add(dataPersonal);
							} catch (Exception e) {
								Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
										Constantes.CargaMasivaPersonal.MSE000, e.toString());
								throw new AgcException(Constantes.CargaMasivaPersonal.MSE000, error);
							}
						}
					}
				}
			}
		} catch (AgcException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
//			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_SERVICIO);
			Error error = new Error(Constantes.CodigoErrores.ERROR_SERVICIO,
					Constantes.CargaMasivaPersonal.MSE006.replace("{{ARCHIVO}}", archivo.getOriginalFilename()),
					e.toString());
			throw new AgcException(
					Constantes.CargaMasivaPersonal.MSE006.replace("{{ARCHIVO}}", archivo.getOriginalFilename()), error);
		} finally {
			CargaArchivosUtil.eliminarArchivoTemporal(tempFile);
		}
		return lista;
	}

	private boolean validarCabeceras(String tramaCabecera) {
		boolean flag = true;
		if (tramaCabecera.startsWith("\uFEFF")) {
			tramaCabecera = tramaCabecera.substring(1, tramaCabecera.length());
		}
		
		List<String> cabeceras = Arrays.asList(tramaCabecera.trim().split("\t"));
		List<String> cabecerasParam = Arrays.asList(Constantes.CargaMasivaPersonal.CABECERAS.trim().split("\t"));
		
		if (cabeceras.size() > cabecerasParam.size() || cabeceras.size() < cabecerasParam.size()) {
			return false;
		}

		for (int i = 0; i < cabecerasParam.size(); i++) {
			String cab1 = cabecerasParam.get(i).trim();
			String cab2 = cabeceras.get(i);			
			int compare = cab1.compareTo(cab2);
			if (compare > 0 || compare < 0) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	@Override
	@Async
	public void enviarCorreoCargaMasivaPersonal(String usuarioCarga, String loteCarga, Integer idEmpresa,
			Integer idOficina, String nomContratista) {
		try {
			List<DataCorreoCarga> data = personalContratistaDAO.obtenerDataCorreoCargaMasiva(loteCarga);
			if (data != null) {
				if (!data.isEmpty()) {
					String body = construirBodyCorreo(agruparPersonalPorCargo(data));
					personalContratistaDAO.enviarCorreoCargaPersonal(idEmpresa, idOficina, nomContratista, loteCarga,
							body);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	public Map<String, List<DataCorreoCarga>> agruparPersonalPorCargo(List<DataCorreoCarga> personalPendiente) {
		return personalPendiente.stream().collect(Collectors.groupingBy(DataCorreoCarga::getCargo));
	}

	@Override
	public String construirBodyCorreo(Map<String, List<DataCorreoCarga>> data) {
		String salto = "<br>";
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, List<DataCorreoCarga>> entrada : data.entrySet()) {
			builder.append(String.format("<p>Con el cargo <strong>%s</strong></p> ", entrada.getKey()));
			builder.append(salto);
			builder.append("<ol> ");
			for (DataCorreoCarga item : entrada.getValue()) {
				builder.append(String.format("<li>%s</li>", item.getPersonal()));
			}
			builder.append("</ol> ");
			builder.append(salto);
		}
		return builder.toString();
	}

	@Override
	public List<ResponseCargaArchivo> enviarArchivosFileServer(MultipartFile archivoFoto, MultipartFile archivoCv,
			String dni, Integer codEmpleado1, Integer codEmpleado2, Integer idEmpresa, String usuario)
			throws AgcException {
		List<ResponseCargaArchivo> resultados = new ArrayList<>();
		try {
			if (archivoFoto != null && archivoCv != null) {
				resultados.add(subirArchivo(archivoFoto, Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_FOTO, dni,
						codEmpleado1, codEmpleado2, idEmpresa, usuario).get());
				resultados.add(subirArchivo(archivoCv, Constantes.CargaMasivaPersonal.FORMATO_ARCHIVO_CV, dni,
						codEmpleado1, codEmpleado2, idEmpresa, usuario).get());
			} else if (archivoFoto == null) {
				ResponseCargaArchivo response = new ResponseCargaArchivo();
				response.setResultado(ResultadoCarga.INCORRECTO);
				response.setMensaje(Constantes.CargaMasivaPersonal.MSE004.replace("{{DNI}}", dni));
				resultados.add(response);
			} else if (archivoCv == null) {
				ResponseCargaArchivo response = new ResponseCargaArchivo();
				response.setResultado(ResultadoCarga.INCORRECTO);
				response.setMensaje(Constantes.CargaMasivaPersonal.MSE005.replace("{{DNI}}", dni));
				resultados.add(response);
			}
		} catch (Exception e) {
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_SERVICIO);
		}
		return resultados;
	}

	@Override
	public DarAltaResponse darAltaPersonal(DarAltaRequest request) throws AgcException, SQLException {
		return personalContratistaDAO.validarAltaPersonal(request);
	}

	@Override
	@Async
	public Future<ResponseCargaArchivo> subirArchivo(MultipartFile file, String extension, String dni,
			Integer codEmpleado1, Integer codEmpleado2, Integer idEmpresa, String usuario)
			throws AgcException, Exception {
		File tempFile = null;
		String rutaArchivo = "";
		ResponseCargaArchivo response = new ResponseCargaArchivo();
		response.setResultado(ResultadoCarga.INCORRECTO);
		response.setNombreArchivo(file.getOriginalFilename());
		try {
			tempFile = CargaArchivosUtil.crearArchivoTemporal(file, extension);
			if (tempFile.exists()) {
				FileServerResponse fsr = CargaArchivosUtil.enviarArchivoToFileServer(tempFile,
						this.apiEndpointFileServer + carpetaArchivosPersonal);
				rutaArchivo = fsr.getNombre();
				response.setUrl(carpetaArchivosPersonal + "/" + rutaArchivo);

				Archivo archivo = new Archivo();
				archivo.setNombreArchivo(file.getOriginalFilename());
				archivo.setRutaArchivo(carpetaArchivosPersonal + "/" + rutaArchivo);
				archivo.setTipoArchivo(extension.replace(".", ""));
				archivo.setUsuarioCreacion(usuario);

				response = personalContratistaDAO.insertarArchivoPersonal(dni, codEmpleado1, codEmpleado2, archivo,
						idEmpresa, response);
				if (response.getResultado().equals(ResultadoCarga.INCORRECTO)) {
					CargaArchivosUtil.eliminarArchivoFileServer(rutaArchivo,
							apiEndpointFileServer + carpetaArchivosPersonal);
				}

			}
			return new AsyncResult<ResponseCargaArchivo>(response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setResultado(ResultadoCarga.INCORRECTO);
			response.setMensaje(
					Constantes.CargaMasivaPersonal.MSE006.replace("{{ARCHIVO}}", file.getOriginalFilename()));
			return new AsyncResult<ResponseCargaArchivo>(response);
		} finally {
			if (tempFile != null) {
				CargaArchivosUtil.eliminarArchivoTemporal(tempFile);
			}
		}
	}

	private void eliminarArchivoFileServer(String rutaArchivo, String apiEndpointFileServer) {
		try {
			CargaArchivosUtil.eliminarArchivoFileServer(rutaArchivo, apiEndpointFileServer);
		} catch (Exception e) {
			logger.error("Error al eliminar archivo en el FileServer.", e.getMessage(), e.getCause());
		}
	}

	@Override
	public void enviarCorreosAlta(DarAltaResponse resultadoAlta) {
		enviarCorreoAltaSedapal(resultadoAlta);
		enviarCorreoAltaContratista(resultadoAlta);
	}

	@Async
	private void enviarCorreoAltaSedapal(DarAltaResponse resultadoAlta) {
		try {
			Integer codOficina = 0;
			Integer mostrarPrimeraSeccion = 0;
			Integer mostrarSegundaSeccion = 0;
			String listaContratistasPrimeraSeccion = "";
			String listaContratistasSegundaSeccion = "";
			StringBuilder tablaPrimeraSeccion = null;
			StringBuilder tablaSegundaSeccion = null;

			Map<Integer, List<RegistroAlta>> dataCorreos = resultadoAlta.getPersonalAlta().stream()
					.collect(Collectors.groupingBy(RegistroAlta::getIndUsuarioAgc));

			List<RegistroAlta> registrosAltaFinalizado = dataCorreos
					.get(Constantes.DarAltaPersonal.IND_ALTA_OFF) != null
							? dataCorreos.get(Constantes.DarAltaPersonal.IND_ALTA_OFF)
							: new ArrayList<>();

			List<RegistroAlta> registrosAltaParcial = dataCorreos.get(Constantes.DarAltaPersonal.IND_ALTA_ON) != null
					? dataCorreos.get(Constantes.DarAltaPersonal.IND_ALTA_ON)
					: new ArrayList<>();

			if (registrosAltaFinalizado != null) {
				if (!registrosAltaFinalizado.isEmpty()) {
					Map<String, List<RegistroAlta>> dataPorEmpresa = registrosAltaFinalizado.stream()
							.collect(Collectors.groupingBy(RegistroAlta::getDescContratista));
					Set<String> contratistas = dataPorEmpresa.keySet();
					listaContratistasPrimeraSeccion = StringUtils.collectionToCommaDelimitedString(contratistas);
					codOficina = registrosAltaFinalizado.get(0).getCodOficina();
					tablaPrimeraSeccion = getPrimeraTablaNotificacionAlta(registrosAltaFinalizado);
					mostrarPrimeraSeccion = 1;
				}
			}

			if (registrosAltaParcial != null) {
				if (!registrosAltaParcial.isEmpty()) {
					Map<String, List<RegistroAlta>> dataPorEmpresa = registrosAltaParcial.stream()
							.collect(Collectors.groupingBy(RegistroAlta::getDescContratista));
					Set<String> contratistas = dataPorEmpresa.keySet();
					listaContratistasSegundaSeccion = StringUtils.collectionToCommaDelimitedString(contratistas);
					codOficina = registrosAltaParcial.get(0).getCodOficina();
					tablaSegundaSeccion = getSegundaTablaNotificacionAlta(registrosAltaParcial);
					mostrarSegundaSeccion = 1;
				}
			}

			if (registrosAltaParcial != null || registrosAltaFinalizado != null) {
				if (!registrosAltaParcial.isEmpty() || !registrosAltaFinalizado.isEmpty()) {
					personalContratistaDAO.enviarCorreoAltaSedapal(codOficina, mostrarPrimeraSeccion,
							mostrarSegundaSeccion, listaContratistasPrimeraSeccion, listaContratistasSegundaSeccion,
							tablaPrimeraSeccion, tablaSegundaSeccion);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al generar correo de alta a sedapal", e);
		}
	}

	private StringBuilder getPrimeraTablaNotificacionAlta(List<RegistroAlta> registrosAlta) {
		StringBuilder builder = new StringBuilder();
		String style = "text-align: center; vertical-align: middle; font-size: 12px;"
				+ " color: black; border-style: solid; border-color: #000000;"
				+ " border-width: 1px; border-collapse: collapse; text-align: center; vertical-align: middle;";

		for (RegistroAlta reg : registrosAlta) {
			builder.append("<tr>");
			builder.append(String.format("<td style=\"%s\">%s</td>", style, String.valueOf(reg.getCodEmpleado())));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getNombres()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDni()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescCargo()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescOficina()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescContratista()));
			builder.append("</tr>");
		}
		return builder;
	}

	private StringBuilder getSegundaTablaNotificacionAlta(List<RegistroAlta> registrosAlta) {
		StringBuilder builder = new StringBuilder();
		String style = "text-align: center; vertical-align: middle; font-size: 12px;"
				+ " color: black; border: 1px solid #000000;"
				+ " text-align: center; border-collapse: collapse; vertical-align: middle;";
		for (RegistroAlta reg : registrosAlta) {
			builder.append("<tr>");
			builder.append(String.format("<td style=\"%s\">%s</td>", style, String.valueOf(reg.getCodEmpleado())));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getNombres()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDni()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescCargo()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescOficina()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getDescContratista()));
			builder.append(String.format("<td style=\"%s\">%s</td>", style, reg.getUsuarioAgc()));
			builder.append("</tr>");
		}
		return builder;
	}

	@Async
	private void enviarCorreoAltaContratista(DarAltaResponse resultadoAlta) {
		try {
			Map<String, List<RegistroAlta>> registrosPorUsuario = resultadoAlta.getPersonalAlta().stream()
					.collect(Collectors.groupingBy(RegistroAlta::getUsuarioCreacion));

			for (Map.Entry<String, List<RegistroAlta>> registros : registrosPorUsuario.entrySet()) {
				generarCorreoAltaContratista(registros.getKey(), registros.getValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al generar correo de alta a contratistas", e);
		}
	}

	private void generarCorreoAltaContratista(String usuario, List<RegistroAlta> registrosAlta) {
		String contratista = "";
		String oficina = "";
		Integer mostrarPrimeraSeccion = 0;
		Integer mostrarSegundaSeccion = 0;
		StringBuilder tablaPrimeraSeccion = null;
		StringBuilder tablaSegundaSeccion = null;

		contratista = registrosAlta.get(0).getDescContratista();
		oficina = registrosAlta.get(0).getDescOficina();

		List<RegistroAlta> registrosAltaFinalizado = registrosAlta.stream()
				.filter(r -> r.getIndUsuarioAgc() == Constantes.DarAltaPersonal.IND_ALTA_OFF)
				.collect(Collectors.toList());

		List<RegistroAlta> registrosAltaParcial = registrosAlta.stream()
				.filter(r -> r.getIndUsuarioAgc() == Constantes.DarAltaPersonal.IND_ALTA_ON)
				.collect(Collectors.toList());

		if (registrosAltaFinalizado != null) {
			if (!registrosAltaFinalizado.isEmpty()) {
				tablaPrimeraSeccion = getPrimeraTablaNotificacionAlta(registrosAltaFinalizado);
				mostrarPrimeraSeccion = 1;
			}
		}

		if (registrosAltaParcial != null) {
			if (!registrosAltaParcial.isEmpty()) {
				tablaSegundaSeccion = getSegundaTablaNotificacionAlta(registrosAltaParcial);
				mostrarSegundaSeccion = 1;
			}
		}

		if (registrosAltaFinalizado != null || registrosAltaParcial != null) {
			if (!registrosAltaFinalizado.isEmpty() || !registrosAltaParcial.isEmpty()) {
				personalContratistaDAO.enviarCorreoAltaContratista(usuario, contratista, oficina, mostrarPrimeraSeccion,
						mostrarSegundaSeccion, tablaPrimeraSeccion, tablaSegundaSeccion);
			}
		}

	}

	@Override
	public ResultadoCarga completarAltaPersonal(Trabajador trabajador, String usuarioAuditoria) {
		trabajador.setPasswordTemporal(generadorPasswordTemporal());
		return personalContratistaDAO.completarAltaPersonal(trabajador, usuarioAuditoria);
	}

	private String generadorPasswordTemporal() {
		String valores = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

		String pw = "";
		for (int i = 0; i < 9; i++) {
			int index = (int) (new SecureRandom().nextDouble() * valores.length());
			pw = pw + valores.substring(index, index + 1);
		}
		return pw;
	}

	@Override
	public CesarPersonalResponse cesarPersonal(CesarPersonalRequest request) throws AgcException, SQLException {
		return personalContratistaDAO.cesarPersonal(request);
	}

	@Override
	public CeseMasivoResponse ceseMasivoPersonal(Integer idEmpresa, String usuarioPeticion) {
		return personalContratistaDAO.ceseMasivoPersonal(idEmpresa, usuarioPeticion);
	}

}
