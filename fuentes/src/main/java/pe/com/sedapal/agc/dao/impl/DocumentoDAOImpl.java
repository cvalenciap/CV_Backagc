package pe.com.sedapal.agc.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IDocumentoDAO;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.FileServerResponse;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;
import pe.com.sedapal.agc.util.FileStorageService;

@Repository
public class DocumentoDAOImpl implements IDocumentoDAO {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	private SimpleJdbcCall jdbcCall;
	private Error error;
	private Path fileStorageLocation;

	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;

	@Value("${file.upload-dir}")
	private String pathFinal;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public String invocarProcedimiento() {
		try {
			inicializarDataSource2("agc", "", "PRC_REG_AVI_COB");
			Map<String, Object> resultado = jdbcCall.execute();
			return "Procedimiento 'PRC_CARGAR_TRAMA' ejecutado!";
		} catch (Exception e) {
			logger.error("[AGC: DocumentoDAOImpl - invocarProcedimiento()] - " + e.getMessage());
			return e.getCause().toString();
		}

	}

	@Override
	public Error obtenerError() {
		return this.error;
	}

	private void setAutoCommit(boolean autoCommit) throws SQLException {
		this.jdbcCall.getJdbcTemplate().getDataSource().getConnection().setAutoCommit(autoCommit);
	}

	private void commit() throws SQLException {
		this.jdbcCall.getJdbcTemplate().getDataSource().getConnection().commit();
	}

	private void rollBack() {
		try {
			this.jdbcCall.getJdbcTemplate().getDataSource().getConnection().rollback();
		} catch (SQLException e) {
			capturarMensaje("rollback()", e);
		}
	}

	private void inicializarDataSource(String paquete, String procedimiento) {
		this.jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("AGC");
		this.jdbcCall.withCatalogName(paquete).withProcedureName(procedimiento);
	}

	private void inicializarDataSource2(String esquema, String paquete, String procedimiento) {
		this.jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(esquema);
		this.jdbcCall.withCatalogName(paquete).withProcedureName(procedimiento);
	}

	@Override
	public boolean validateFilesOnline(MultipartFile[] files, String usuario) {
		try {
			boolean validation = true;
			Map<String, Object> executeOracle = null;
			prepareDataSourceUploadFilesOnline();
			for (MultipartFile file : files) {
				String[] originalFileName = file.getOriginalFilename().split("-");
				SqlParameterSource parametersOracle = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", originalFileName[0])
						.addValue("V_N_IDREG",
								Integer.parseInt(
										originalFileName[1].substring(0, originalFileName[1].lastIndexOf('.'))))
						.addValue("V_N_IDARCHI", file.getOriginalFilename().hashCode())
						.addValue("V_A_V_USUCRE", usuario);

				executeOracle = jdbcCall.execute(parametersOracle);

				if (executeOracle.get("N_RESP").toString().equals("0")) {
					String message = executeOracle.get("V_EJEC").toString();
					this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), message);
					logger.info("No se cargo el archivo: " + file.getOriginalFilename() + " | " + message);
					validation = false;
				} else {
					copiarArchivos(file);
				}
			}
			return validation;
		} catch (Exception exception) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: DocumentoDAOImpl - validateFilesOnline()] - " + exception.getMessage());
			return false;
		}
	}

	private void prepareDataSourceUploadFilesOnline() {
		this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
		this.jdbcCall.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
				.withProcedureName(DbConstants.PRC_REG_ADJUNTO_DET_LINEA);
		this.jdbcCall.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
				new SqlParameter("V_N_IDREG", OracleTypes.NUMBER), new SqlParameter("V_N_IDARCHI", OracleTypes.NUMBER),
				new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR));
	}

	@Override
	public boolean saveAttachFileDataBase(Adjunto adjunto) {
		this.error = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
			this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
					.withProcedureName(DbConstants.PRC_REGI_ADJU_CARGA_TRAB);
			this.jdbcCall.declareParameters(new SqlParameter("I_VIDCARGA", OracleTypes.VARCHAR),
					new SqlParameter("I_VTIPOORIGEN", OracleTypes.VARCHAR),
					new SqlParameter("I_VNOMBRADJUN", OracleTypes.VARCHAR),
					new SqlParameter("I_VEXTENSION", OracleTypes.VARCHAR),
					new SqlParameter("I_VRUTA", OracleTypes.VARCHAR), new SqlParameter("USUARIO", OracleTypes.VARCHAR));

			SqlParameterSource parameterOracle = new MapSqlParameterSource()
					.addValue("I_VIDCARGA", adjunto.getUidCarga()).addValue("I_VTIPOORIGEN", adjunto.getTipoOrigen())
					.addValue("I_VNOMBRADJUN", adjunto.getNombre()).addValue("I_VEXTENSION", adjunto.getExtension())
					.addValue("I_VRUTA", adjunto.getRuta()).addValue("USUARIO", adjunto.getUsuarioCreacion());

			Map<String, Object> executeOracleSave = this.jdbcCall.execute(parameterOracle);
			this.error = Constantes.validarRespuestaOracle(executeOracleSave);
			if (this.error != null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			eliminarArchivoFileServer(adjunto.getRuta());
			this.error = Constantes.obtenerError(9999, null);
			this.error.setMensajeInterno(exception.getCause().toString());
			logger.error("[AGC: DocumentoDAOImpl - saveAttachFileDataBase()] - " + exception.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteAttachFileDataBase(Adjunto adjunto) {
		this.error = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
		this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
				.withProcedureName(DbConstants.PRC_ELIM_ADJU_CARGA_TRAB);
		this.jdbcCall.declareParameters(new SqlParameter("I_VIDCARGA", OracleTypes.VARCHAR),
				new SqlParameter("I_NIDADJUNTO", OracleTypes.NUMBER), new SqlParameter("USUARIO", OracleTypes.VARCHAR));

		SqlParameterSource parameterOracle = new MapSqlParameterSource().addValue("I_VIDCARGA", adjunto.getUidCarga())
				.addValue("I_NIDADJUNTO", adjunto.getUidAdjunto()).addValue("USUARIO", adjunto.getUsuarioCreacion());

		try {
			Map<String, Object> executeOracleDelete = this.jdbcCall.execute(parameterOracle);
			this.error = Constantes.validarRespuestaOracle(executeOracleDelete);
			if (this.error != null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			this.error = Constantes.obtenerError(9999, null);
			this.error.setMensajeInterno(exception.getCause().toString());
			logger.error("[AGC: DocumentoDAOImpl - deleteAttachFileDataBase()] - " + exception.getMessage());
			return false;
		}
	}

	@Override
	public List<Error> guardarRegistrosAdjuntosDetalle(List<FileServerResponse> archivosSubidos, String usuario,
			String uidActividad) throws SQLException {
		List<Error> errors = new ArrayList<>();
		for(FileServerResponse datosAdjunto : archivosSubidos) {
			try {
				this.error = null;
				this.jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
				this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS);
				this.jdbcCall.withProcedureName(DbConstants.PRC_REGI_ADJU_CARGA_TRAB_DET);
				this.jdbcCall.declareParameters(new SqlParameter("V_I_NOMBRE_ARCHIVO", OracleTypes.VARCHAR),
						new SqlParameter("V_I_RUTA_FILESERVER", OracleTypes.VARCHAR),
						new SqlParameter("V_I_USUARIO", OracleTypes.VARCHAR),
						new SqlParameter("V_IDACTI", OracleTypes.VARCHAR),
						new SqlOutParameter("V_RUTA_ADJU_DUPLI", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
				SqlParameterSource input = new MapSqlParameterSource()
						.addValue("V_I_NOMBRE_ARCHIVO", datosAdjunto.getNombreReal())
						.addValue("V_I_RUTA_FILESERVER", getPathUploadFile(datosAdjunto.getUrl()))
						.addValue("V_I_USUARIO", usuario).addValue("V_IDACTI", uidActividad);

				Map<String, Object> resultado = jdbcCall.execute(input);
				if (resultado.get("N_RESP").toString().equals("0")) {
					eliminarArchivoFileServer(getPathUploadFile(datosAdjunto.getUrl()));
					Integer codigo = Integer.parseInt(resultado.get("N_EJEC").toString());
					String mensajeOracle = resultado.get("V_EJEC").toString();
					String mensaje = Constantes.CargaArchivos.devolverMensajeErrorGuardarRegistro(datosAdjunto.getNombreReal());
					String mensajeInterno = mensajeOracle.contains(Constantes.CargaArchivos.ORACLE_NO_DATA_FOUND)
							? Constantes.CargaArchivos.MENSAJE_ERROR_ITEM_CARGA : mensajeOracle;
//					String messageError = String.format(
//							"No se cargo el archivo [%s], no esta asociado a ninguna carga de trabajo",
//							datosAdjunto.getNombreReal());
					error = new Error(codigo, mensaje, mensajeInterno);
					errors.add(error);
					logger.error(mensaje);
					logger.error(mensajeInterno);
				} else {
					String rutaArchivoDuplicado = resultado.get("V_RUTA_ADJU_DUPLI") != null
							? resultado.get("V_RUTA_ADJU_DUPLI").toString()
							: null;
					if (rutaArchivoDuplicado != null) {
						eliminarArchivoFileServer(rutaArchivoDuplicado);
					}
				}
			} catch (Exception e) {
				eliminarArchivoFileServer(getPathUploadFile(datosAdjunto.getUrl()));
				errors.add(new Error(1, e.getMessage(), Constantes.CargaArchivos.MENSAJE_ERROR_BD));
				error = new Error(9999, e.getCause().toString());
				logger.error(String.format(
						"AGC: DocumentoDAOImpl - guardarRegistroAdjuntosDetalle()] : Error al crear registro de archivo: [%s]",
						datosAdjunto.getNombreReal()));
			}
		}
		return errors;
	}

	public String copiarArchivos(MultipartFile archivo) {
		String urlSaveFile = "";
		File file = null;
		try {
			file = Constantes.convert(archivo);
			if (file != null) {
				HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", new FileBody(file)).build();
				HttpPut put = new HttpPut(apiEndpointFileServer);
				put.setEntity(httpEntity);
				HttpClient client = HttpClientBuilder.create().build();
				HttpResponse response = client.execute(put);
				InputStream instream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				JSONObject obJson = new JSONObject(reader.readLine());
				urlSaveFile = obJson.getString("url");
			}
		} catch (Exception exception) {
			logger.error("[AGC: DocumentoDAOImpl - copiarArchivos()] - " + exception.getMessage());
		} finally {
			if (file != null && file.exists()) {
				file.delete();
			}
		}
		return urlSaveFile;
	}

	private void eliminarRutasDuplicadas(List<String> rutasDuplicadas) {
		if (!rutasDuplicadas.isEmpty()) {
			rutasDuplicadas.forEach(ruta -> eliminarArchivoFileServer(ruta));
		}
	}

	private void eliminarArchivosAnteError(List<FileServerResponse> datosArchivosSubidos) {
		datosArchivosSubidos.forEach(archivo -> eliminarArchivoFileServer(getPathUploadFile(archivo.getUrl())));
	}

	public void eliminarArchivoFileServer(String rutaArchivoDuplicado) {
		final HttpDelete delete = new HttpDelete(apiEndpointFileServer + rutaArchivoDuplicado);
		HttpResponse response = null;
		try {
			delete.addHeader("Content-Type", "application/json");
			HttpClient httpclient = HttpClientBuilder.create().build();
			response = httpclient.execute(delete);
		} catch (Exception exception) {
			logger.error("[AGC: DocumentoDAOImpl - eliminarArchivoFileServer()] - " + exception.getMessage());
		}
	}

	private void updateAttchFileDetails(String fileName, String url, String user, String codeActivity) {
		this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
		this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
				.withProcedureName(DbConstants.PRC_UPDA_ADJU_CARGA_TRAB_DET);
		this.jdbcCall.declareParameters(new SqlParameter("NOMBRE", OracleTypes.VARCHAR),
				new SqlParameter("RUTA", OracleTypes.VARCHAR), new SqlParameter("USUARIO", OracleTypes.VARCHAR),
				new SqlParameter("V_IDACTI", OracleTypes.VARCHAR));

		SqlParameterSource parametersOracle = new MapSqlParameterSource().addValue("NOMBRE", fileName)
				.addValue("RUTA", getPathUploadFile(url)).addValue("USUARIO", user).addValue("V_IDACTI", codeActivity);

		Map<String, Object> executeOracle = this.jdbcCall.execute(parametersOracle);
	}

	private String getPathUploadFile(String url) {
		String returnPath = "/";
		returnPath = url.replace(apiEndpointFileServer, "");
		return returnPath;
	}

	@Override
	public List<Adjunto> obtenerAdjuntosPorCargaRegistro(String uidCarga, Integer uidRegistro,
			PageRequest pageRequest) {
		List<Adjunto> adjuntos = new ArrayList<Adjunto>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
			this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
					.withProcedureName(DbConstants.PRC_LIST_ADJU_CARGA_TRAB_DET);
			this.jdbcCall.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
					new SqlParameter("V_N_IDREG", OracleTypes.NUMBER), new SqlParameter("N_PAGINA", OracleTypes.NUMBER),
					new SqlParameter("N_REGISTROS", OracleTypes.NUMBER),
					new SqlOutParameter("C_OUT", OracleTypes.CURSOR), new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
					new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
					new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			SqlParameterSource parametersOracle = new MapSqlParameterSource().addValue("V_V_IDCARGA", uidCarga)
					.addValue("V_N_IDREG", uidRegistro).addValue("N_PAGINA", pageRequest.getPagina())
					.addValue("N_REGISTROS", pageRequest.getRegistros());

			Map<String, Object> executionOracle = this.jdbcCall.execute(parametersOracle);
			this.error = Constantes.validarRespuestaOracle(executionOracle);
			if (this.error == null) {
				adjuntos = mapearAdjuntosPorCargaRegistro(executionOracle);
			}
		} catch (Exception exception) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
			logger.error("[AGC: DocumentoDAOImpl - obtenerAdjuntosPorCargaRegistro()] - " + exception.getMessage());
		}

		return adjuntos;
	}

	private List<Adjunto> mapearAdjuntosPorCargaRegistro(Map<String, Object> resultados) {

		Adjunto adjunto;
		List<Adjunto> listaAdjuntos = new ArrayList<Adjunto>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");

		for (Map<String, Object> map : lista) {
			adjunto = new Adjunto();
			adjunto.setUidCarga((String) map.get("uidCarga"));
			adjunto.setUidAdjunto(((BigDecimal) map.get("uidAdjunto")).intValue());
			adjunto.setNombre((String) map.get("nombre"));
			adjunto.setExtension((String) map.get("extension"));
			adjunto.setRuta((String) map.get("ruta"));
			adjunto.setUsuarioCreacion((String) map.get("usuarioCreacion"));
			Timestamp fechaCreacion = (Timestamp) map.get("fechaCreacion");
			adjunto.setFechaCreacion(fechaCreacion.getTime());
			adjunto.setFechaCarga(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fechaCreacion));
			adjunto.setTotalRegistros(((BigDecimal) map.get("totalRegistros")).intValue());
			listaAdjuntos.add(adjunto);
		}

		return listaAdjuntos;
	}

	@Override
	public boolean eliminarAdjuntoPorCargaRegistro(Adjunto adjunto) {
		this.error = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withSchemaName(DbConstants.DBSCHEMA);
		this.jdbcCall.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
				.withProcedureName(DbConstants.PRC_ELIM_ADJU_CARGA_TRAB_DET);
		this.jdbcCall.declareParameters(new SqlParameter("I_VIDCARGA", OracleTypes.VARCHAR),
				new SqlParameter("I_NIDREG", OracleTypes.NUMBER), new SqlParameter("I_NIDARCHI", OracleTypes.VARCHAR));
		SqlParameterSource parameterOracle = new MapSqlParameterSource().addValue("I_VIDCARGA", adjunto.getUidCarga())
				.addValue("I_NIDREG", adjunto.getUidRegistro()).addValue("I_NIDARCHI", adjunto.getUidAdjunto());

		try {
			Map<String, Object> executeOracleDelete = this.jdbcCall.execute(parameterOracle);
			this.error = Constantes.validarRespuestaOracle(executeOracleDelete);
			if (this.error != null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			this.error = Constantes.obtenerError(9999, null);
			this.error.setMensajeInterno(exception.getCause().toString());
			logger.error("[AGC: DocumentoDAOImpl - eliminarAdjuntoPorCargaRegistro()] - " + exception.getMessage());
			return false;
		}
	}

	private void capturarMensaje(String metodo, Exception ex) {
		this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), ex.getCause().toString());
		logger.error(String.format("[AGC: DocumentoServicioImpl - %s] - ", metodo) + ex.getMessage());
	}

	private static void trazarLog(String metodo, String mensaje) {
		logger.error(String.format("[AGC: DocumentoServicioImpl - %s] - %s", metodo, mensaje));
	}
}
