package pe.com.sedapal.agc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Estado;
import pe.com.sedapal.agc.model.response.ResponseObject;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constantes {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	public static final Map<Integer, String> MESSAGE_ERROR;
	static {
		MESSAGE_ERROR = new HashMap<>();
		MESSAGE_ERROR.put(1000, "Se realizó correctamente la transacción.");
		MESSAGE_ERROR.put(1001, "El valor del campo [{valor}] no puede estar vacío.");
		MESSAGE_ERROR.put(1002, "El valor del campo [{valor}] no puede superar [{maximo}] carácteres de longitud.");
		MESSAGE_ERROR.put(1004, "El valor del campo [{valor}] debe contener entre [1] a [{maximo}] caracteres.");
		MESSAGE_ERROR.put(1003, "El tipo de Actividad de la Carga de Trabajo no esta definida.");
		MESSAGE_ERROR.put(9001,
				"El objeto [{valor}] devuelto por servicio de seguridad, no se ha procesado correctamente.");
		MESSAGE_ERROR.put(9002, "No se pudo crear el directorio donde se almacenarán los archivos cargados");
		MESSAGE_ERROR.put(9003, "El nombre del archivo [{valor}] contiene una secuencia de ruta no válida");
		MESSAGE_ERROR.put(9004, "No se pudo almacenar el archivo [{valor}], por favor volver a intentar");
		MESSAGE_ERROR.put(9999, "Error en la ejecución de la transacción.");
		MESSAGE_ERROR.put(8000, "El valor del campo [{valor}] no cumple con el formato correcto");
	}

	public static final class MensajeErrorApi {
		public static final String CABECERA = "Aviso";
		public static final String TRABAJADOR_INTERNO_NO_ENCONTRADO = "El usuario no está registrado";
		public static final String TRABAJADOR_INTERNO_SIN_PERFIL = "El usuario no tiene asociado un perfil en el sistema AGC";
		public static final String TRABAJADOR_EXTERNO_NO_ENCONTRADO = "El usuario externo no está registrado";
		public static final String TRABAJADOR_EXTERNO_SIN_PERFIL = "El usuario externo no tiene asociado un perfil en el sistema AGC";
	}

	public static final Map<Integer, String> MESSAGE_CARGA_ARCHIVO_EJECUCION;
	static {
		MESSAGE_CARGA_ARCHIVO_EJECUCION = new HashMap<>();
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1001,
				"La cabecera del Archivo de Ejecución no coincide con el formato definido para la carga. Debe seguir la siguiente estructura:");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1002, "La carga del Archivo de Ejecución se realizó correctamente.");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1003,
				"Se realizó la carga del Archivo de Ejecución. Los siguientes registros no pudieron ser procesados:");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1004, "El fichero Zip debe contener un archivo de texto (.txt)");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1005, "El fichero Zip no debe contener carpeta(s).");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1006, "El fichero Zip no contiene ningún archivo de carga.");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1007, "El fichero Zip debe contener solo un archivo de carga.");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1008, "Se produjo un error durante la carga del Archivo de Ejecución.");
		MESSAGE_CARGA_ARCHIVO_EJECUCION.put(1009, "El Archivo de Ejecución no tiene contenido para el procesamiento.");
	}
	
	public static final Map<Integer, String> MESSAGE_VISOR_PDF;
	static {
		MESSAGE_VISOR_PDF = new HashMap<>();
		MESSAGE_VISOR_PDF.put(1001, "Ocurrió un error al consultar los archivos adjuntos.");
		MESSAGE_VISOR_PDF.put(1002, "Ocurrió un error en el File Server.");
	}	
	
	public static String CORREO_FROM = "agestioncomercial@sedapal.com.pe";
	public static String CORREO_ASUNTO = "Carga de Trabajo Enviada por Sedapal";
	public static String[] cambioTexto = { "{valor}", "{maximo}" };

	public static final String ACT_DIST_AVISO_COBRANZA = "DA";
	public static final String ACT_DIST_COMUNIC = "DC";
	public static final String ACT_INSP_COMERCIAL = "IC";
	public static final String ACT_MEDIDORES = "ME";
	public static final String ACT_TOMA_ESTADO = "TE";

	public static final String PERFIL_ADMINISTRADOR = "1";
	public static final String PERFIL_ANALISTA_INTERNO = "2";
	public static final String PERFIL_RESPONSABLE = "3";
	public static final String PERFIL_ANALISTA_EXTERNO = "4";
	public static final String PERFIL_SUPERVISOR_EXTERNO = "5";

	public static final Integer TIPO_ESTADOS_CONTRATISTA = 5;
	public static final Integer TIPO_ESTADOS_TODOS = 1;

	public static final String TIPO_EMPRESA_SEDAPAL = "S";
	public static final String TIPO_EMPRESA_CONTRATISTA = "C";

	public static class Mensajes {
		public static final String ERROR_PROCESO = "Ocurrio un error durante el procesamiento";
		public static final String ERROR_BD = "Ocurrio un error al realizar la operación en base de datos";
		public static final String ERROR_INSETAR_BD = "Ocurrio un error al insertar registro en la base de datos";
		public static final String ERROR_CONSULTA_BD = "Ocurrio un error al realizar la consulta a base de datos";
		public static final String ERROR_ACTUALIZAR_BD = "Ocurrio un error al actualizar registro en la base de datos";
		public static final String CONSULTA_VACIA = "No se obtuvieron resultados";
		public static final String ERROR_MAPPER = "Error al obtener resultados de consulta";
	}

	public static class CodigoErrores {
		public static final int ERROR_BD = 10;
		public static final int ERROR_SERVICIO = 11;
		public static final int ERROR_CAPTURADO_API = 12;
	}

	public static class CargaMasivaPersonal {
		public static String FORMATO_ARCHIVO_CARGA = ".txt";
		public static String FORMATO_ARCHIVO_FOTO = ".JPG";
		public static String FORMATO_ARCHIVO_CV = ".PDF";

		public static String CABECERAS = "DOC_ID	APE1_EMP	APE2_EMP	NOMB_EMP	D_FECNAC	V_DIREEMP	D_FECINGR	TIP_EMP	N_IDITEM	COD_UNICOM	V_TELFPERS	V_TELFASIG	EMAIL	COD_EMP_CT";
		public static String REGEXP_TRAMA = "^([A-Za-z0-9_\\/?]+\\t){10}([A-Za-z0-9_\\/?]*\\t){3}(\\w*)$";
		public static String REGEXP_CABECERA = "^(DOC_ID)\\t(APE1_EMP)\\t(APE2_EMP)\\t(NOMB_EMP)\\t(D_FECNAC)\\t(V_DIREEMP)\\t(D_FECINGR)\\t(TIP_EMP)\\t(N_IDITEM)\\t(COD_UNICOM)\\t(V_TELFPERS)\\t(V_TELFASIG)\\t(EMAIL)\\t(COD_EMP_CT)$";

		public static String REGEXP_FOTO = "(FOTO)\\_[0-9]+\\_[0-9]{8}\\.(jpg|JPG)";
		public static String REGEXP_CV = "(CV)\\_[0-9]+\\_[0-9]{8}\\.(pdf|PDF)";

		public static String MSE000 = "Contenido de archivo inválido";
		public static String MSE001 = "El archivo no contiene información del personal";
		public static String MSE002 = "El archivo de carga debe tener el formato TXT";
		public static String MSE003 = "Extension de archivo inválido";
		public static String MSE004 = "No se ha seleccionado el archivo foto para el personal con D.N.I: {{DNI}}";
		public static String MSE005 = "No se ha seleccionado el archivo C.V para el personal con D.N.I: {{DNI}}";
		public static String MSE006 = "Error al cargar el archivo {{ARCHIVO}}";
		public static String MSE007 = "Cabecera de archivo inválido";
		public static String MSE008 = "Codificacion de texto incorrecta.\nLa codificación del texto debe ser UTF-8";
	}

	public static class DarAltaPersonal {
		public static final Integer IND_ALTA_ON = 1;
		public static final Integer IND_ALTA_OFF = 2;
	}
	
	public static class Solicitud {
		public static final String TIPO_SOL_CAMB_CARGO = "1";
		public static final String TIPO_SOL_MOV_PERS = "2";
	}

	public static class RegistroSolicitudPersonal {
		public static String MSE000 = "Se ha registrado los datos de la solicitud de cambio de cargo";
		public static String MSE001 = "No podrá registrar una nueva solicitud ya que se encuentra una solicitud anterior pendiente de aprobación";
		public static String MSE002 = "Se ha registrado los datos de la solicitud de movimiento de personal";
		public static String MSE003 = "Se ha aprobado la solicitud de cambio de cargo";
		public static String MSE004 = "Se ha rechazado la solicitud de cambio de cargo";
		public static String MSE005 = "Se ha aprobado la solicitud de movimiento de personal";
		public static String MSE006 = "Se ha rechazado la solicitud de movimiento de personal";
	}

	public static class MantenimientoItems {
		public static String MSE000 = "Se ha guardado los cambios correctamente.";
		public static String MSE001 = "Se presentó un error al registrar Item Oficina.";
	}

	public static class MantenimientoEmpresas {
		public static String MSE000 = "Se ha guardado los cambios correctamente.";
		public static String MSE001 = "Se presentó un error al registrar Empresa Item.";
	}

	public static class validacionCodigoOperadorDetalleCarga {
		public static String MSE001 = "Código de Personal {{valor}} no existe en la información de personal del Contratista.";
		public static String MSE002 = "Código de Personal {{valor}} no esta de ALTA.";
		public static String MSE003 = "Código de Personal {{valor}} no se encuentra dentro de la oficina de personal del Contratista.";
		public static String MSE004 = "La actividad realizada por el Código de Personal {{valor}} no coincide con una de las actividades de su cargo operativo.";
		public static String MSE005 = "La actividad realizada por el Código de Personal {{valor}} no coincide con el suministro y la carga ingresadas.";
	}

	public static final Integer MAX_REGISTROS_DETALLE_CARGA_TRABAJO = 60000;

	public static Error obtenerError(Integer codigoError, String textoRemplazar) {
		String message = MESSAGE_ERROR.get(codigoError);
		if (textoRemplazar != null) {
			String[] valores = textoRemplazar.split(",");
			for (int indice = 0; indice < valores.length; indice++) {
				message = StringUtils.replace(message, cambioTexto[indice], valores[indice]);
			}
			return new Error(codigoError, message);
		} else {
			return new Error(codigoError, message);
		}
	}

	public static Error validarRespuestaOracle(Map<String, Object> ejecucionOracle) {
		Error error = null;
		BigDecimal codigoEjecucion = (BigDecimal) ejecucionOracle.get("N_RESP");
		if (codigoEjecucion.intValue() != 1) {
			String mensaje = (String) ejecucionOracle.get("V_EJEC");
			BigDecimal mensajeInterno = (BigDecimal) ejecucionOracle.get("N_EJEC");
			error = new Error(9999, mensaje, mensajeInterno.toString());
		}
		return error;
	}

	public static File convertMultipartFileToFile(MultipartFile multipartFile) {
		try {
			File file = new File(multipartFile.getOriginalFilename());
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(multipartFile.getBytes());
			fileOutputStream.close();
			return file;
		} catch (IOException ioException) {
			logger.error("[AGC: Constantes - convertMultipartFileToFile()] - " + ioException.getMessage());
			return null;
		}
	}

	public static ResponseObject putAllParameters(Estado estado, Error error, Object resultado) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setEstado(estado);
		responseObject.setError(error);
		responseObject.setResultado(resultado);
		return responseObject;
	}

	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
		file.transferTo(convFile);

		return convFile;
	}
	
//	Inicio MOnitoreo
	public static class MensajesErrores {
		public static final String MAPPER_LISTA_NULL = "Error al recuperar datos de consulta";
	}
	
	public static class ParametrosMonitoreo {
		public static final Integer TIPO_PARAM_ZONA = 25;
		public static final Integer TIPO_PARAM_OBSERVACION = 26;
		public static final Integer TIPO_PARAM_ACTIVIDAD = 27;
		public static final Integer TIPO_PARAM_EST_IMPOSIBI = 31;
		public static final Integer TIPO_PARAM_EST_EJE = 32;
		public static final Integer TIPO_PARAM_FILTRO_FOTO = 33;
		public static final Integer TIPO_PARAM_ACTIVIDAD_SED = 34;
		public static final Integer TIPO_PARAM_INCIDEN_TE = 36;
		public static final Integer TIPO_PARAM_SEMAFORO_MON = 37;
		public static final Integer TIPO_IND_CUMPLIMIENTO = 40;
		public static final Integer TIPO_PARAM_TIPO_INSTALAC = 41;
		public static final Integer TIPO_PARAM_IMPOSIBILIDAD = 46;
		public static final Integer TIPO_PARAM_INSPECCIONES = 45;
		public static final Integer TIPO_PARAM_ESTADO_MED = 44;
		public static final Integer TIPO_PARAM_ESTADO_SUM = 43;
		public static final Integer TIPO_PARAM_OBSERVA_SGIO = 48;
		public static final Integer TIPO_PARAM_TIPO_NOTIF = 42;
		public static final Integer TIPO_PARAM_FREC_ALERTA = 56;
	}
	
	public static class ParametrosSGCMonitoreo {
		public static final String TIPO_PARAM_ENTREGA = "ET";
		public static final String TIPO_SGC_ORDEN_ME = "TO";
		public static final String TIPO_PARAM_CARGA = "TC";
		public static final String TIPO_SGC_ORDEN_SERV = "OS";
		public static final String TIPO_SGC_ORDEN_TRAB = "OT";
	}
//	Fin monitoreo

	public static class CargaArchivos {
		public static final Integer LISTA_VALIDA = 1;
		public static final Integer LISTA_ERRORES = 0;
		public static final String ADJUNTO_CABECERA = "CAB";
		public static final String ADJUNTO_DETALLE = "DET";
		public static final String FORMATO_PDF = ".PDF";
		public static final String FORMATO_JPG = ".JPG";
		public static final String REG_EXP_ITEM_CARGA_TRABAJO = "[0-9]{4}(DA|DC|IC|ME|SG|TE)[0-9]{9}\\-[0-9]+\\_\\w+\\.(pdf|jpg|PDF|JPG)";
		public static final String ERROR_FORMATO = "Se hallaron archivos con formato no permitido, los formatos permitidos son: PDF y JPG";
		public static final String ERROR_VALIDACION_ARCHIVOS = "Uno o más archivos no cumple los requisitos de tamaño y/o nombre de archivo";
		public static final String ERROR_NOMBRE_ARCHIVOS = "El nombre de los archivos deber tener la siguiente nomenclatura: "
				+ "[Código de la carga]-[número del ítem del detalle]_[texto descriptivo].[Extensión pdf o jpg]";
		public static final String MENSAJE_OK_FILESERVER = "El archivo fue cargado correctamente";
		public static final String MENSAJE_ERROR_TAMANIOS = "Uno o más archivos superan el tamaño límite permitido en el FileServer";
		public static final String MENSAJE_ERROR_FILESERVER = "Ocurrio un error en el repositorio de archivos FileServer, comunicarse con el Administrador de Aplicaciones";
		public static final String MENSAJE_ERROR_ELIMINAR_FILESERVER = "Ocurrió un error al eliminar archivos en el FileServer";
		public static final String MENSAJE_ERROR_BD = "Ocurrió un error al registrar datos de adjuntos en la base de datos";
		public static final String MENSAJE_ERROR_ITEM_CARGA = "Existen adjuntos que no están vinculados a ninguna carga de trabajo";
		public static final String ORACLE_NO_DATA_FOUND = "ORA-01403";
		public static final String MENSAJE_RESPONSE_OK = "Los archivos se subieron correctamente";
		public static final String MENSAJE_RESPOSE_ERROR = "Se presentaron algunos errores durante la operación";
		
		public static String devolverMensajeErrorEliminarArchivo(String nombreArchivo) {
			return String.format("Error al eliminar el archivo [%s]", nombreArchivo);
		}

		public static String devolverMensajeErrorArchivo(String nombreArchivo) {
			return String.format("Error al subir el archivo: [%s]", nombreArchivo);
		}
		
		public static String devolverMensajeErrorGuardarRegistro(String nombreArchivo) {
			return String.format("Error al guardar registro del adjunto: [%s]", nombreArchivo);
		}


		public static String devolverMensajeErrorTamanioArchivo(String nombreArchivo, Double tamanioMax) {
			return String.format("El tamaño del archivo [%s] no debe exceder de %s MB", nombreArchivo, tamanioMax);
		}

		public static String devolverMensajeErrorNomenclatura(String nombreArchivo) {
			return String.format("Error en archivo %s - El nombre de los archivos debe tener la siguiente nomenclatura:"
					+ " [Código de la carga]-[número del ítem del detalle]_[texto descriptivo].[Extensión pdf o jpg]",
					nombreArchivo);
		}
	}

	public static class recuperaArchivos {
		public static final String MENSAJE_ERROR_FILESERVER = "Ocurrio un error al obtener el archivo del file server";
	}

	public static byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}
	
	public static boolean matchString(String regexp, String cadena) {
		Pattern patron = Pattern.compile(regexp);
		Matcher matcher = patron.matcher(cadena);
		return matcher.matches();
	}

}
