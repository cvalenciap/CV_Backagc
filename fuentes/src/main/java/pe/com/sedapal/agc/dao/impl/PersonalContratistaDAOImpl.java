package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import pe.com.sedapal.agc.dao.IPersonalContratistaDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.DarAltaMapper;
import pe.com.sedapal.agc.mapper.DataCorreoCargaPersonalMapper;
import pe.com.sedapal.agc.mapper.PersonalContratistaMapper;
import pe.com.sedapal.agc.mapper.ResultadoCargaPersonalMapper;
import pe.com.sedapal.agc.model.Archivo;
import pe.com.sedapal.agc.model.CabeceraCargaPersonal;
import pe.com.sedapal.agc.model.DataCorreoCarga;
import pe.com.sedapal.agc.model.PersonaContratista;
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
import pe.com.sedapal.agc.util.AgcExceptionUtil;
import pe.com.sedapal.agc.util.CargaArchivosUtil;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@SuppressWarnings("deprecation")
@Repository
public class PersonalContratistaDAOImpl implements IPersonalContratistaDAO {

	private static final Logger logger = LoggerFactory.getLogger(PersonalContratistaDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Autowired
	private PersonalContratistaMapper mapper;

	@Autowired
	private ResultadoCargaPersonalMapper resultadoCargaMapper;

	@Autowired
	private DataCorreoCargaPersonalMapper dataCorreoMapper;

	@Autowired
	private DarAltaMapper darAltaMapper;

	@Value("${endpoint.file.server}")
	private String apiEndpointFileServer;

	private Error error;
	private Paginacion paginacion;

	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}

	@Override
	public Error getError() {
		return this.error;
	}

	@Override
	public ListaPaginada<PersonaContratista> listarPersonalContratista(PersonalContratistaRequest request,
			Integer pagina, Integer registros) {
		ListaPaginada<PersonaContratista> listaPersonal = new ListaPaginada<>();
		try {
			Map<String, Object> respuestaConsulta = null;

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_LISTAR_PERSONAL_CONTRATISTA)
					.declareParameters(new SqlParameter("I_V_NUM_DNI", OracleTypes.VARCHAR),
							new SqlParameter("I_N_COD_SED", OracleTypes.NUMBER),
							new SqlParameter("I_V_NOMB", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_PAT", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_MAT", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ID_EMPR", OracleTypes.NUMBER),
							new SqlParameter("I_V_COD_CARG", OracleTypes.VARCHAR),
							new SqlParameter("I_V_COD_OFIC", OracleTypes.NUMBER),
							new SqlParameter("I_V_PER_INGR", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTA_LAB", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTA_SED", OracleTypes.VARCHAR),
							new SqlParameter("I_N_COD_MOT_BAJA", OracleTypes.VARCHAR),
							new SqlParameter("I_D_FEC_BAJA", OracleTypes.VARCHAR),
							new SqlParameter("I_N_TIPO_SOLI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_ESTA_SOLI", OracleTypes.VARCHAR),
							new SqlParameter("I_N_IDPERS", OracleTypes.NUMBER),
							new SqlParameter("I_N_IDPERFIL", OracleTypes.NUMBER),
							new SqlParameter("I_V_CODLOTE", OracleTypes.VARCHAR),
							new SqlParameter("I_N_PAGINA", OracleTypes.VARCHAR),
							new SqlParameter("I_N_REGISTROS", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_LISTA_PERSONA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource parametros = new MapSqlParameterSource()
					.addValue("I_V_NUM_DNI", request.getNumeroDocumento())
					.addValue("I_N_COD_SED", request.getCodigoEmpleado()).addValue("I_V_NOMB", request.getNombres())
					.addValue("I_V_AP_PAT", request.getApellidoPaterno())
					.addValue("I_V_AP_MAT", request.getApellidoMaterno())
					.addValue("I_N_ID_EMPR", request.getIdEmpresa()).addValue("I_V_COD_CARG", request.getCodigoCargo())
					.addValue("I_V_COD_OFIC", request.getCodigoOficina())
					.addValue("I_V_PER_INGR", request.getPeriodoIngreso())
					.addValue("I_V_ESTA_LAB", request.getEstadoLaboral())
					.addValue("I_V_ESTA_SED", request.getEstadoSedapal())
					.addValue("I_N_COD_MOT_BAJA", request.getCodMotivoCese())
					.addValue("I_D_FEC_BAJA", request.getFechaCese())
					.addValue("I_N_TIPO_SOLI", request.getTipoSolicitud())
					.addValue("I_V_ESTA_SOLI", request.getEstadoSolicitud())
					.addValue("I_N_IDPERS", request.getIdPersonal()).addValue("I_N_IDPERFIL", request.getIdPerfil())
					.addValue("I_V_CODLOTE", request.getCodLote()).addValue("I_N_PAGINA", pagina)
					.addValue("I_N_REGISTROS", registros);

			respuestaConsulta = this.jdbcCall.execute(parametros);
			Integer resultado = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");
			if (resultado == 1) {
				List<PersonaContratista> lista = mapper.mapearRespuestaBd(respuestaConsulta);
				if (!lista.isEmpty()) {
					listaPersonal.setLista(lista);
					listaPersonal.setPagina(pagina);
					listaPersonal.setRegistros(registros);
					listaPersonal.setTotalRegistros(mapper.obtenerTotalregistros(respuestaConsulta));
				} else {
					listaPersonal.setLista(lista);
					listaPersonal.setPagina(pagina);
					listaPersonal.setRegistros(registros);
					listaPersonal.setTotalRegistros(0);
				}
			} else {
				Integer codigoErrorBD = ((BigDecimal) respuestaConsulta.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaConsulta.get("O_V_EJEC");
				logger.error("Error al obtener Cargas de Trabajo.", mensajeErrorBD);
				throw new AgcException(Constantes.Mensajes.ERROR_BD,
						new Error(codigoErrorBD, mensajeErrorBD, mensajeErrorBD));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD,
					new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage(), e.getMessage()));
		}
		return listaPersonal;
	}

	@Override
	public void registrarPersonalContratista(PersonaContratista personaContratista) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		Integer codigoError = 0;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
				.withProcedureName(DbConstants.PRC_INSERTAR_PERSONA)
				.declareParameters(new SqlParameter("I_V_NUM_DNI", OracleTypes.VARCHAR),
						new SqlParameter("I_V_NOMB", OracleTypes.VARCHAR),
						new SqlParameter("I_V_AP_PAT", OracleTypes.VARCHAR),
						new SqlParameter("I_V_AP_MAT", OracleTypes.VARCHAR),
						new SqlParameter("I_V_DIRE_EMP", OracleTypes.VARCHAR),
						new SqlParameter("I_D_FEC_NAC", OracleTypes.DATE),
						new SqlParameter("I_N_ID_EMPR", OracleTypes.NUMBER),
						new SqlParameter("I_V_COD_CARG", OracleTypes.VARCHAR),
						new SqlParameter("I_N_ID_ITEM", OracleTypes.NUMBER),
						new SqlParameter("I_N_COD_OFIC", OracleTypes.NUMBER),
						new SqlParameter("I_V_EMAIL", OracleTypes.VARCHAR),
						new SqlParameter("I_D_FEC_INGR", OracleTypes.DATE),
						new SqlParameter("I_V_TELF_PERS", OracleTypes.VARCHAR),
						new SqlParameter("I_V_TELF_ASIG", OracleTypes.VARCHAR),
						new SqlParameter("I_V_COD_CONTR_PERS", OracleTypes.VARCHAR),
						new SqlParameter("I_N_PESO_EMP", OracleTypes.NUMBER),
						new SqlParameter("I_V_EST_EMP", OracleTypes.VARCHAR),
						new SqlParameter("I_V_ESTA_LAB", OracleTypes.VARCHAR),
						new SqlParameter("I_V_TIPO_IMAGEN", OracleTypes.VARCHAR),
						new SqlParameter("I_V_NOMB_IMAGEN", OracleTypes.VARCHAR),
						new SqlParameter("I_V_RUTA_IMAGEN", OracleTypes.VARCHAR),
						new SqlParameter("I_V_TIPO_ARCHIVO", OracleTypes.VARCHAR),
						new SqlParameter("I_V_NOMB_ARCHIVO", OracleTypes.VARCHAR),
						new SqlParameter("I_V_RUTA_ARCHIVO", OracleTypes.VARCHAR),
						new SqlParameter("I_A_USU_CRE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_V_NUM_DNI", personaContratista.getNumeroDocumento())
					.addValue("I_V_NOMB", personaContratista.getNombres())
					.addValue("I_V_AP_PAT", personaContratista.getApellidoPaterno())
					.addValue("I_V_AP_MAT", personaContratista.getApellidoMaterno())
					.addValue("I_V_DIRE_EMP", personaContratista.getDireccion())
					.addValue("I_D_FEC_NAC", formatter.parse(personaContratista.getFechaNacimiento()))
					.addValue("I_N_ID_EMPR", personaContratista.getContratista().getCodigo())
					.addValue("I_V_COD_CARG", personaContratista.getCargo().getId())
					.addValue("I_N_ID_ITEM", personaContratista.getItem().getId())
					.addValue("I_N_COD_OFIC", personaContratista.getOficina().getCodigo())
					.addValue("I_V_EMAIL", personaContratista.getCorreoElectronico())
					.addValue("I_D_FEC_INGR", formatter.parse(personaContratista.getFechaIngreso()))
					.addValue("I_V_TELF_PERS", personaContratista.getTelefonoPersonal())
					.addValue("I_V_TELF_ASIG", personaContratista.getTelefonoAsignado())
					.addValue("I_V_COD_CONTR_PERS", personaContratista.getCodigoEmpleadoContratista())
					.addValue("I_N_PESO_EMP", 8).addValue("I_V_EST_EMP", personaContratista.getEstadoPersonal().getId())
					.addValue("I_V_ESTA_LAB", personaContratista.getEstadoLaboral().getId())
					.addValue("I_V_TIPO_IMAGEN", personaContratista.getArchivoFotoPersonal().getTipoArchivo())
					.addValue("I_V_NOMB_IMAGEN", personaContratista.getArchivoFotoPersonal().getNombreArchivo())
					.addValue("I_V_RUTA_IMAGEN", personaContratista.getArchivoFotoPersonal().getRutaArchivo())
					.addValue("I_V_TIPO_ARCHIVO", personaContratista.getArchivoCvPersonal().getTipoArchivo())
					.addValue("I_V_NOMB_ARCHIVO", personaContratista.getArchivoCvPersonal().getNombreArchivo())
					.addValue("I_V_RUTA_ARCHIVO", personaContratista.getArchivoCvPersonal().getRutaArchivo())
					.addValue("I_A_USU_CRE", personaContratista.getUsuarioCreacion());

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			codigoError = CastUtil.leerValorMapInteger(out, "O_N_EJEC");
			if (resultadoEjecucion != 1 || codigoError != 0) {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			if (codigoError == 10000 || codigoError == 20000 || codigoError == 30000) {
				throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage()));
			} else {
				logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, "Error al registrar Personal Contratista."));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, "Error al registrar Personal Contratista."));
		}
	}

	@Override
	public void actualizarPersonalContratista(PersonaContratista personaContratista) {
		Map<String, Object> respuestaConsulta = null;
		Integer resultadoEjecucion = 0;
		Integer codigoError = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_ACTUALIZAR_PERSONA).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_COD_SED", OracleTypes.NUMBER),
							new SqlParameter("I_V_NOMB", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_PAT", OracleTypes.VARCHAR),
							new SqlParameter("I_V_AP_MAT", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DIRE_EMP", OracleTypes.VARCHAR),
							new SqlParameter("I_D_FEC_NAC", OracleTypes.DATE),
							new SqlParameter("I_V_EMAIL", OracleTypes.VARCHAR),
							new SqlParameter("I_D_FEC_INGR", OracleTypes.DATE),
							new SqlParameter("I_V_TELF_PERS", OracleTypes.VARCHAR),
							new SqlParameter("I_V_TELF_ASIG", OracleTypes.VARCHAR),
							new SqlParameter("I_V_COD_CONTR_PERS", OracleTypes.VARCHAR),
							new SqlParameter("I_N_PESO_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_ID_IMAGEN", OracleTypes.NUMBER),
							new SqlParameter("I_V_RUTA_IMAGEN", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ID_ARCHIVO", OracleTypes.NUMBER),
							new SqlParameter("I_V_RUTA_ARCHIVO", OracleTypes.VARCHAR),
							new SqlParameter("I_A_USU_MOD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_COD_SED", personaContratista.getCodigoEmpleado())
					.addValue("I_V_NOMB", personaContratista.getNombres())
					.addValue("I_V_AP_PAT", personaContratista.getApellidoPaterno())
					.addValue("I_V_AP_MAT", personaContratista.getApellidoMaterno())
					.addValue("I_V_DIRE_EMP", personaContratista.getDireccion())
					.addValue("I_D_FEC_NAC", formatter.parse(personaContratista.getFechaNacimiento()))
					.addValue("I_V_EMAIL", personaContratista.getCorreoElectronico())
					.addValue("I_D_FEC_INGR", formatter.parse(personaContratista.getFechaIngreso()))
					.addValue("I_V_TELF_PERS", personaContratista.getTelefonoPersonal())
					.addValue("I_V_TELF_ASIG", personaContratista.getTelefonoAsignado())
					.addValue("I_V_COD_CONTR_PERS", personaContratista.getCodigoEmpleadoContratista())
					.addValue("I_N_PESO_EMP", 8)
					.addValue("I_N_ID_IMAGEN",
							personaContratista.getArchivoFotoPersonalAnterior() != null
									? personaContratista.getArchivoFotoPersonalAnterior().getId()
									: 0)
					.addValue("I_V_RUTA_IMAGEN",
							personaContratista.getArchivoFotoPersonal() != null
									? personaContratista.getArchivoFotoPersonal().getRutaArchivo()
									: "")
					.addValue("I_N_ID_ARCHIVO",
							personaContratista.getArchivoCvPersonalAnterior() != null
									? personaContratista.getArchivoCvPersonalAnterior().getId()
									: 0)
					.addValue("I_V_RUTA_ARCHIVO",
							personaContratista.getArchivoCvPersonal() != null
									? personaContratista.getArchivoCvPersonal().getRutaArchivo()
									: "")
					.addValue("I_A_USU_MOD", personaContratista.getUsuarioModificacion());

			respuestaConsulta = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");
			codigoError = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_EJEC");
			if (resultadoEjecucion != 1 || codigoError != 0) {
				Integer codigoErrorBD = ((BigDecimal) respuestaConsulta.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaConsulta.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			if (codigoError == 30000) {
				throw new AgcException(e.getMessage(), new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage()));
			} else {
				logger.error(Constantes.Mensajes.ERROR_INSETAR_BD, e);
				throw new AgcException(e.getMessage(),
						new Error(Constantes.CodigoErrores.ERROR_BD, "Error al registrar Personal Contratista."));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, "Error al actualizar Personal Contratista."));
		}
	}

	@Override
	public Map<String, Object> obtenerParametrosBandeja(Integer idPersonal, Integer idPerfil) throws AgcException {
		Map<String, Object> parametros = new LinkedHashMap<>();
		try {
			Map<String, Object> respuestaConsulta = null;

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_OBT_PARAMETROS_BANDEJA_PERSONAL)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("V_N_IDPERS", OracleTypes.INTEGER),
							new SqlParameter("V_N_IDPERFIL", OracleTypes.INTEGER),
							new SqlOutParameter("C_LISTA_CARGO", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_OFICINA", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_EST_LABORAL", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_EST_PERSONAL", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_MOT_CESE", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_EST_SOLICITUD", OracleTypes.CURSOR),
							new SqlOutParameter("C_LISTA_MOT_ALTA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("V_N_IDPERS", idPersonal)
					.addValue("V_N_IDPERFIL", idPerfil);

			respuestaConsulta = this.jdbcCall.execute(params);
			Integer resultado = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");

			if (resultado == 1) {
				parametros.put("cargos", respuestaConsulta.get("C_LISTA_CARGO"));
				parametros.put("oficinas", respuestaConsulta.get("C_LISTA_OFICINA"));
				parametros.put("estadoLaboral", respuestaConsulta.get("C_LISTA_EST_LABORAL"));
				parametros.put("estadoPersonal", respuestaConsulta.get("C_LISTA_EST_PERSONAL"));
				parametros.put("motivosCese", respuestaConsulta.get("C_LISTA_MOT_CESE"));
				parametros.put("estadoSolicitud", respuestaConsulta.get("C_LISTA_EST_SOLICITUD"));
				parametros.put("motivosAlta", respuestaConsulta.get("C_LISTA_MOT_ALTA"));
			} else {
				logger.error("Error al obtener parametros de bandeja personal.",
						CastUtil.leerValorMapString(respuestaConsulta, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaConsulta);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD,
					new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage(), e.getMessage()));
		}
		return parametros;
	}

	@Override
	public List<CargaPersonalResponse> cargaMasivaPersonal(CabeceraCargaPersonal cabecera,
			List<TramaPersonal> tramasPersonal, Integer idEmpresa) throws AgcException, SQLException {
		List<CargaPersonalResponse> listaResultados = new ArrayList<>();
		Connection conn = null;
		try {
			conn = this.jdbc.getDataSource().getConnection();
			Map<String, Object> respuestaConsulta = null;

			StructDescriptor structCabecera = StructDescriptor.createDescriptor("AGC.TYPE_AGC_OBJ_CAB_CARGA_PERS",
					conn.getMetaData().getConnection());
			StructDescriptor structTramaPersonal = StructDescriptor.createDescriptor("AGC.TYPE_AGC_OBJ_TRAMA_PERSONAL",
					conn.getMetaData().getConnection());
			ArrayDescriptor structLista = ArrayDescriptor.createDescriptor("AGC.TYPE_AGC_TAB_TRAMA_PERSONAL",
					conn.getMetaData().getConnection());

//			Objeto Cabecera
			Object cabeceraDB = new STRUCT(structCabecera, conn.getMetaData().getConnection(),
					mapper.obtenerObjetoCabeceraDB(cabecera));

//			Objetos trama
			List<Object> listaTramaPersonal = new ArrayList<>();
			for (int i = 0; i < tramasPersonal.size(); i++) {
				listaTramaPersonal.add(new STRUCT(structTramaPersonal, conn.getMetaData().getConnection(),
						mapper.obtenerObjetoTramaPersonalDB(tramasPersonal.get(i))));
			}

//			ARRAy Oracle
			ARRAY arrayDBTramas = new ARRAY(structLista, conn.getMetaData().getConnection(),
					listaTramaPersonal.toArray());

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_VALIDAR_TRAMA_PERSONAL_CONTRATISTA)
					.withoutProcedureColumnMetaDataAccess().declareParameters(
							new SqlParameter("TAB_I_LISTA_TRAMA", OracleTypes.ARRAY, "AGC.TYPE_AGC_TAB_TRAMA_PERSONAL"),
							new SqlParameter("OBJ_I_CAB_CARGA", OracleTypes.STRUCT, "AGC.TYPE_AGC_OBJ_CAB_CARGA_PERS"),
							new SqlParameter("N_ID_EMPRESA", OracleTypes.NUMBER),
							new SqlOutParameter("C_O_RESUMEN_CARGA", OracleTypes.CURSOR),
							new SqlOutParameter("V_O_DETALLE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("TAB_I_LISTA_TRAMA", arrayDBTramas)
					.addValue("OBJ_I_CAB_CARGA", cabeceraDB).addValue("N_ID_EMPRESA", idEmpresa);

			respuestaConsulta = this.jdbcCall.execute(params);
			Integer resultado = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");

			if (resultado == 1) {
				listaResultados = resultadoCargaMapper.mapearRespuestaBd(respuestaConsulta);
			} else {
				Integer codigoErrorBD = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_EJEC");
				String mensajeErrorBD = CastUtil.leerValorMapString(respuestaConsulta, "O_V_EJEC");
				throw new AgcException(Constantes.Mensajes.ERROR_BD,
						new Error(codigoErrorBD, Constantes.Mensajes.ERROR_BD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		} finally {
			if (conn != null) {
				conn.close();
				this.jdbc.getDataSource().getConnection().close();
			}
		}
		return listaResultados;
	}

	@Override
	public List<DataCorreoCarga> obtenerDataCorreoCargaMasiva(String codLote) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		List<DataCorreoCarga> data = new ArrayList<>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_OBTENER_DATA_CORREO_CARGA_MASIVA_PERSONAL)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_COD_LOTE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_DATA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_V_COD_LOTE", codLote);

			respuesta = this.jdbcCall.execute(params);
			Integer resultado = CastUtil.leerValorMapInteger(respuesta, "O_N_RESP");
			if (resultado == 1) {
				data = dataCorreoMapper.mapearRespuestaBd(respuesta);
			} else {
				logger.info(String.format("Error al obtener data del lote %s", codLote),
						CastUtil.leerValorMapString(respuesta, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuesta);
			}

		} catch (AgcException e) {
			throw e;
		} catch (Exception e) {
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return data;
	}

	@Override
	public void enviarCorreoCargaPersonal(Integer idEmpresa, Integer idOficina, String nomEmpresa, String codLote,
			String bodyMail) {
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_ENVIAR_CORREO_CARGA_PERSONAL)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("N_ID_OFIC", OracleTypes.NUMBER),
							new SqlParameter("V_NOM_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_NUM_LOTE", OracleTypes.VARCHAR),
							new SqlParameter("V_CONT_BODY", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("N_IDEMP", idEmpresa)
					.addValue("N_ID_OFIC", idOficina).addValue("V_NOM_EMPR", nomEmpresa).addValue("V_NUM_LOTE", codLote)
					.addValue("V_CONT_BODY", bodyMail);

			this.jdbcCall.execute(params);

		} catch (Exception e) {
			logger.error("Error en envio de correos carga masiva personal", e.getStackTrace().toString());
		}
	}

	@Override
	public ResponseCargaArchivo insertarArchivoPersonal(String dni, Integer codEmpleado1, Integer codEmpleado2,
			Archivo archivoPersonal, Integer idEmpresa, ResponseCargaArchivo response) {
		try {
			Map<String, Object> respuestaOperacion = new LinkedHashMap<>();

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_INSERTAR_ARCHIVO_PERSONAL).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_COD_EMP_1", OracleTypes.NUMBER),
							new SqlParameter("I_N_COD_EMP_2", OracleTypes.NUMBER),
							new SqlParameter("I_N_COD_CONTRA", OracleTypes.NUMBER),
							new SqlParameter("I_V_TIPOARCH", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NOMBARCH", OracleTypes.VARCHAR),
							new SqlParameter("I_V_RUTAARCH", OracleTypes.VARCHAR),
							new SqlParameter("I_A_USU_CRE", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_N_COD_EMP_1", codEmpleado1)
					.addValue("I_N_COD_EMP_2", codEmpleado2).addValue("I_N_COD_CONTRA", idEmpresa)
					.addValue("I_V_TIPOARCH", archivoPersonal.getTipoArchivo())
					.addValue("I_V_NOMBARCH", archivoPersonal.getNombreArchivo())
					.addValue("I_V_RUTAARCH", archivoPersonal.getRutaArchivo())
					.addValue("I_A_USU_CRE", archivoPersonal.getUsuarioCreacion());

			respuestaOperacion = this.jdbcCall.execute(params);
			response.setResultado(ResultadoCarga.CORRECTO);
			/*
			 * Integer estado = CastUtil.leerValorMapInteger(respuestaOperacion,
			 * "O_N_RESP"); if (estado == 1) {
			 * response.setResultado(ResultadoCarga.CORRECTO); } else { logger.error(
			 * String.format("Error al insertar archivo %s del personal con dni: %s",
			 * archivoPersonal.getNombreArchivo(), dni),
			 * CastUtil.leerValorMapString(respuestaOperacion, "O_V_EJEC"));
			 * response.setResultado(ResultadoCarga.INCORRECTO); response.setMensaje(String.
			 * format("Error al insertar archivo %s del personal con dni: %s - %s",
			 * archivoPersonal.getNombreArchivo(), dni,
			 * CastUtil.leerValorMapString(respuestaOperacion, "O_V_EJEC"))); }
			 */
		} catch (Exception e) {
			response.setResultado(ResultadoCarga.INCORRECTO);
			response.setMensaje(String.format("Error al insertar archivo %s del personal con codigo: %s - %s",
					archivoPersonal.getNombreArchivo(), archivoPersonal.getCodigoEmpleado(), e.toString()));
			try {
				CargaArchivosUtil.eliminarArchivoFileServer(archivoPersonal.getRutaArchivo(), apiEndpointFileServer);
			} catch (Exception ex) {
				logger.error("Error al eliminar archivo en el FileServer.", ex.getMessage(), ex.getCause());

			}
			logger.error(e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public DarAltaResponse validarAltaPersonal(DarAltaRequest request) throws AgcException, SQLException {
		DarAltaResponse resultadoAlta = new DarAltaResponse();
		try (Connection conn = this.jdbc.getDataSource().getConnection();) {
			Map<String, Object> respuestaConsulta = null;

			StructDescriptor structRegistroAlta = StructDescriptor.createDescriptor("AGC.TYPE_AGC_OBJ_REG_ALTA",
					conn.getMetaData().getConnection());
			ArrayDescriptor structListaRegistrosAlta = ArrayDescriptor.createDescriptor("AGC.TYPE_AGC_TAB_REG_DAR_ALTA",
					conn.getMetaData().getConnection());

//			Objetos registro alta
			List<Object> listaRegistrosAlta = new ArrayList<>();
			for (int i = 0; i < request.getListaPersonal().size(); i++) {
				listaRegistrosAlta.add(new STRUCT(structRegistroAlta, conn.getMetaData().getConnection(),
						mapper.obtenerRegistroAltaDb(request.getListaPersonal().get(i), request)));
			}

//			ARRAy Oracle
			ARRAY arrayDBRegistrosAlta = new ARRAY(structListaRegistrosAlta, conn.getMetaData().getConnection(),
					listaRegistrosAlta.toArray());

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_VALIDAR_ALTA_PERSONAL_CONTRATISTA)
					.withoutProcedureColumnMetaDataAccess().declareParameters(
							new SqlParameter("TAB_REG_ALTA", OracleTypes.ARRAY, "AGC.TYPE_AGC_TAB_REG_DAR_ALTA"),
							new SqlOutParameter("C_O_DETALLE", OracleTypes.CURSOR),
							new SqlOutParameter("C_O_ERRORES", OracleTypes.CURSOR),
							new SqlOutParameter("O_V_ESTADO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("TAB_REG_ALTA", arrayDBRegistrosAlta);

			respuestaConsulta = this.jdbcCall.execute(params);
			Integer resultado = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");

			if (resultado == 1) {
				resultadoAlta = darAltaMapper.obtenerDarAltaResponse(respuestaConsulta);
			} else {
				logger.info("Error al dar alta a personal", CastUtil.leerValorMapString(respuestaConsulta, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaConsulta);
			}
		} catch (AgcException e) {
			logger.error(e.toString());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return resultadoAlta;
	}

	@Override
	public void enviarCorreoAltaSedapal(Integer codOficina, Integer mostrarPrimeraSeccion,
			Integer mostrarSegundaSeccion, String listaContratistasPrimeraSeccion,
			String listaContratistasSegundaSeccion, StringBuilder tablaPrimeraSeccion,
			StringBuilder tablaSegundaSeccion) {
		Map<String, Object> respuestaBd = null;
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_ENVIAR_CORREO_ALTA_SEDAPAL)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_IDOFICINA", OracleTypes.NUMBER),
							new SqlParameter("I_B_PRIM_SECCION", OracleTypes.NUMBER),
							new SqlParameter("I_B_SEGN_SECCION", OracleTypes.NUMBER),
							new SqlParameter("I_V_LISTA_EMPR_1", OracleTypes.VARCHAR),
							new SqlParameter("I_V_LISTA_EMPR_2", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DATA_PRINCIPAL", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DATA_SECUNDARIO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_N_IDOFICINA", codOficina)
					.addValue("I_B_PRIM_SECCION", mostrarPrimeraSeccion)
					.addValue("I_B_SEGN_SECCION", mostrarSegundaSeccion)
					.addValue("I_V_LISTA_EMPR_1", listaContratistasPrimeraSeccion)
					.addValue("I_V_LISTA_EMPR_2", listaContratistasSegundaSeccion)
					.addValue("I_V_DATA_PRINCIPAL", tablaPrimeraSeccion != null ? tablaPrimeraSeccion.toString() : null)
					.addValue("I_V_DATA_SECUNDARIO",
							tablaSegundaSeccion != null ? tablaSegundaSeccion.toString() : null);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");
			if (resultadoOperacion == 1) {
				logger.info("Correo de notificacion de alta a sedapal enviado");
			} else {
				logger.info("[{}]: Error al enviar correo. Cod: {} - Msg: {}", "enviarCorreoAltaSedapal",
						CastUtil.leerValorMapInteger(respuestaBd, "O_N_EJEC"),
						CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al enviar correo de alta a sedapal", e);
		}
	}

	@Override
	public void enviarCorreoAltaContratista(String usuarioCreacion, String contratista, String oficina,
			Integer mostrarPrimeraSeccion, Integer mostrarSegundaSeccion, StringBuilder tablaPrimeraSeccion,
			StringBuilder tablaSegundaSeccion) {
		Map<String, Object> respuestaBd = null;
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_ENVIAR_CORREO_ALTA_CONTRATISTA)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_V_USUARIO_CREACION", OracleTypes.VARCHAR),
							new SqlParameter("I_B_PRIM_SECCION", OracleTypes.NUMBER),
							new SqlParameter("I_B_SEGN_SECCION", OracleTypes.NUMBER),
							new SqlParameter("I_V_NOM_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DESC_OFIC", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DATA_PRINCIPAL", OracleTypes.VARCHAR),
							new SqlParameter("I_V_DATA_SECUNDARIO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_V_USUARIO_CREACION", usuarioCreacion)
					.addValue("I_B_PRIM_SECCION", mostrarPrimeraSeccion)
					.addValue("I_B_SEGN_SECCION", mostrarSegundaSeccion).addValue("I_V_NOM_EMPR", contratista)
					.addValue("I_V_DESC_OFIC", oficina)
					.addValue("I_V_DATA_PRINCIPAL", tablaPrimeraSeccion != null ? tablaPrimeraSeccion.toString() : null)
					.addValue("I_V_DATA_SECUNDARIO",
							tablaSegundaSeccion != null ? tablaSegundaSeccion.toString() : null);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");
			if (resultadoOperacion == 1) {
				logger.info("Correo de notificacion de alta a contratista enviado");
			} else {
				logger.info("[{}]: Error al enviar correo. Cod: {} - Msg: {}", "enviarCorreoAltaContratista",
						CastUtil.leerValorMapInteger(respuestaBd, "O_N_EJEC"),
						CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
			}

		} catch (Exception e) {
			logger.error("Error al enviar correo de alta a contratista", e);
		}
	}

	@Override
	public ResultadoCarga completarAltaPersonal(Trabajador trabajador, String usuarioAuditoria) {
		Map<String, Object> respuestaBd = new HashMap<>();
		ResultadoCarga resultadoAlta = ResultadoCarga.INCORRECTO;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_COMPLETAR_ALTA).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_ID_PERS", OracleTypes.NUMBER),
							new SqlParameter("I_V_PASS_TEMP", OracleTypes.VARCHAR),
							new SqlParameter("I_V_PASS_ENCRYP", OracleTypes.VARCHAR),
							new SqlParameter("I_V_USUA_AUD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_N_ID_PERS", trabajador.getCodigo())
					.addValue("I_V_PASS_TEMP", trabajador.getPasswordTemporal())
					.addValue("I_V_PASS_ENCRYP", encoder.encode(trabajador.getPasswordTemporal()))
					.addValue("I_V_USUA_AUD", usuarioAuditoria);

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOp = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOp == 1) {
				resultadoAlta = ResultadoCarga.CORRECTO;
			} else {
				logger.info("Error al dar alta a personal", CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBd);
			}

		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return resultadoAlta;
	}

	@Override
	public CesarPersonalResponse cesarPersonal(CesarPersonalRequest request) {
		CesarPersonalResponse cesarPersonalResponse = new CesarPersonalResponse();
		cesarPersonalResponse.setResultado(ResultadoCarga.INCORRECTO);
		Map<String, Object> respuestaBd = new HashMap<>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_CESAR_PERSONAL).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_CODEMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_CODMOTCESE", OracleTypes.NUMBER),
							new SqlParameter("I_V_OBSERVACION", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_CESE", OracleTypes.VARCHAR),
							new SqlParameter("I_V_USUARIO_MOD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_V_RESULTADO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_V_DETALLE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_N_CODEMP", request.getCodigoEmpleado())
					.addValue("I_N_CODMOTCESE", request.getCodMotCese())
					.addValue("I_V_OBSERVACION", request.getObservacion())
					.addValue("I_V_FEC_CESE", request.getFechaCese())
					.addValue("I_V_USUARIO_MOD", request.getUsuarioMod());

			respuestaBd = this.jdbcCall.execute(params);
			Integer resultadoOp = CastUtil.leerValorMapInteger(respuestaBd, "O_N_RESP");

			if (resultadoOp == 1) {
				cesarPersonalResponse = CesarPersonalResponse.mapper(respuestaBd);
			} else {
				logger.info("Error al cesar personal", CastUtil.leerValorMapString(respuestaBd, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBd);
			}
		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return cesarPersonalResponse;
	}

	@Override
	public CeseMasivoResponse ceseMasivoPersonal(Integer idEmpresa, String usuarioPeticion) {
		CeseMasivoResponse ceseMasivoResponse = new CeseMasivoResponse();
		ceseMasivoResponse.setResultado(ResultadoCarga.INCORRECTO);
		Map<String, Object> respuestaBD = new HashMap<>();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_CESE_MASIVO_PERSONAL).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("I_N_COD_EMPRESA", OracleTypes.NUMBER),
							new SqlParameter("A_V_USUARIO_MOD", OracleTypes.VARCHAR),
							new SqlOutParameter("O_V_RESULTADO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_V_MSG_CESE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_DETALLE", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource params = new MapSqlParameterSource().addValue("I_N_COD_EMPRESA", idEmpresa)
					.addValue("A_V_USUARIO_MOD", usuarioPeticion);

			respuestaBD = this.jdbcCall.execute(params);
			Integer resultadoOp = CastUtil.leerValorMapInteger(respuestaBD, "O_N_RESP");

			if (resultadoOp == 1) {
				ceseMasivoResponse = CeseMasivoResponse.mapper(respuestaBD);
			} else {
				logger.info("Error en cese masivo", CastUtil.leerValorMapString(respuestaBD, "O_V_EJEC"));
				AgcExceptionUtil.errorOperacionBD(respuestaBD);
			}

		} catch (AgcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw AgcExceptionUtil.lanzarAgcException(e, Constantes.CodigoErrores.ERROR_BD);
		}
		return ceseMasivoResponse;
	}

}
