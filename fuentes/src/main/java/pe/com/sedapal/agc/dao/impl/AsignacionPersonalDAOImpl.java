package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IAsignacionPersonalDAO;
import pe.com.sedapal.agc.mapper.AsignacionPersonalMapper;
import pe.com.sedapal.agc.model.GrupoPersonal;
import pe.com.sedapal.agc.model.OficinaGrupo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.request.GrupoPersonalRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class AsignacionPersonalDAOImpl implements IAsignacionPersonalDAO {

	private static final Logger logger = LoggerFactory.getLogger(AsignacionPersonalDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	private Error error;

	@Override
	public Error getError() {
		return this.error;
	}

	@Override
	public List<OficinaGrupo> listarOficinaGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo) {
		this.error = null;
		Map<String, Object> out = null;
		List<OficinaGrupo> listaOficinaGrupo = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_LISTAR_GRUPO_OFICINA)
				.declareParameters(new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUPO", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_IDEMP", codigoEmpresa)
				.addValue("I_N_IDOFIC", codigoOficina).addValue("I_N_IDGRUPO", codigoGrupo);

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				listaOficinaGrupo = AsignacionPersonalMapper.mapearOficinaGrupo(out);
				System.out.println("Total grupos : " + listaOficinaGrupo.size());
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al obtener oficina grupo.", e.getMessage());
		}
		return listaOficinaGrupo;
	}

	@Override
	public List<GrupoPersonal> listarPersonalGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo) {
		this.error = null;
		Map<String, Object> out = null;
		List<GrupoPersonal> listaGrupoPersonal = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_LISTAR_PERSONAL_GRUPO)
				.declareParameters(new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUPO", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_IDEMP", codigoEmpresa)
				.addValue("I_N_IDOFIC", codigoOficina).addValue("I_N_IDGRUPO", codigoGrupo);

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				listaGrupoPersonal = AsignacionPersonalMapper.mapearPersonalGrupo(out);
				System.out.println("Total personal en grupo : " + listaGrupoPersonal.size());
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al obtener personal del grupo.", e.getMessage());
		}
		return listaGrupoPersonal;
	}

	@Override
	public Trabajador obtenerTrabajador(String codigoUsuario, String tipoEmpresa) {
		this.error = null;
		Map<String, Object> out = null;
		Trabajador trabajador = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_OBTENER_TRABAJADOR)
				.declareParameters(new SqlParameter("I_VCODUSUARIO", OracleTypes.VARCHAR),
						new SqlParameter("I_TIPO_EMPRESA", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("I_VCODUSUARIO", codigoUsuario)
				.addValue("I_TIPO_EMPRESA", tipoEmpresa);

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				trabajador = AsignacionPersonalMapper.mapearTrabajador(out);
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al obtener trabajador.", e.getMessage());
		}
		return trabajador;
	}

	@Override
	public List<GrupoPersonal> registrarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		this.error = null;
		Map<String, Object> out = null;
		List<GrupoPersonal> listaGrupoPersonal = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_REGISTRAR_GRUPO_PERSONAL)
				.declareParameters(
						new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDPERS", OracleTypes.NUMBER),
						new SqlParameter("I_USUA_AUD", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_IDEMP", requestGrupoPersonal.getIdEmpresa())
				.addValue("I_N_IDOFIC", requestGrupoPersonal.getIdOficina())
				.addValue("I_N_IDGRUP", requestGrupoPersonal.getIdGrupo())
				.addValue("I_N_IDPERS", requestGrupoPersonal.getIdPersona())
				.addValue("I_USUA_AUD", requestGrupoPersonal.getUsuarioAuditoria());

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				listaGrupoPersonal = AsignacionPersonalMapper.mapearPersonalGrupo(out);
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al registrar personal del grupo.", e.getMessage());
		}
		return listaGrupoPersonal;
	}

	@Override
	public List<GrupoPersonal> eliminarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		this.error = null;
		Map<String, Object> out = null;
		List<GrupoPersonal> listaGrupoPersonal = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_ELIMINAR_GRUPO_PERSONAL)
				.declareParameters(new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDPERS", OracleTypes.NUMBER),
						new SqlParameter("I_USUA_AUD", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_IDEMP", requestGrupoPersonal.getIdEmpresa())
				.addValue("I_N_IDOFIC", requestGrupoPersonal.getIdOficina())
				.addValue("I_N_IDGRUP", requestGrupoPersonal.getIdGrupo())
				.addValue("I_N_IDPERS", requestGrupoPersonal.getIdPersona())
				.addValue("I_USUA_AUD", requestGrupoPersonal.getUsuarioAuditoria());

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				listaGrupoPersonal = AsignacionPersonalMapper.mapearPersonalGrupo(out);
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al registrar personal del grupo.", e.getMessage());
		}
		return listaGrupoPersonal;
	}

	@Override
	public Map<String, Object> validarExistenciaPersonal(GrupoPersonalRequest requestGrupoPersonalRequest) {
		this.error = null;
		Map<String, Object> out = null;
		Map<String, Object> salida = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_VALIDAR_EXISTE_PERSONAL)
				.declareParameters(new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDPERS", OracleTypes.NUMBER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_EXISTE", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("I_N_IDEMP", requestGrupoPersonalRequest.getIdEmpresa())
				.addValue("I_N_IDOFIC", requestGrupoPersonalRequest.getIdOficina())
				.addValue("I_N_IDGRUP", requestGrupoPersonalRequest.getIdGrupo())
				.addValue("I_N_IDPERS", requestGrupoPersonalRequest.getIdPersona());

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				salida = obtenerSalidaValidacion(out);
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}

		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al registrar personal del grupo.", e.getMessage());
		}

		return salida;
	}

	@Override
	public Map<String, Object> validarEliminarPersonal(GrupoPersonalRequest requestGrupoPersonalRequest) {
		this.error = null;
		Map<String, Object> out = null;
		Map<String, Object> salida = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_MANTENIMIENTO_GRUPO_PERSONAL)
				.withProcedureName(DbConstants.PROCEDURE_VALIDAR_ELIMINAR_PERSONAL)
				.declareParameters(new SqlParameter("I_N_IDEMP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDGRUP", OracleTypes.NUMBER),
						new SqlParameter("I_N_IDPERS", OracleTypes.NUMBER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_EXISTE", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("I_N_IDEMP", requestGrupoPersonalRequest.getIdEmpresa())
				.addValue("I_N_IDOFIC", requestGrupoPersonalRequest.getIdOficina())
				.addValue("I_N_IDGRUP", requestGrupoPersonalRequest.getIdGrupo())
				.addValue("I_N_IDPERS", requestGrupoPersonalRequest.getIdPersona());
		

		try {
			out = this.jdbcCall.execute(in);
			int resultado = ((BigDecimal) out.get("O_N_RESP")).intValue();

			if (resultado == 1) {
				salida = obtenerSalidaValidacion(out);
			} else {
				String mensaje = (String) out.get("O_V_EJEC");
				String mensajeInterno = (String) out.get("O_N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}

		} catch (Exception e) {
			String mensaje = (String) out.get("O_V_EJEC");
			String mensajeInterno = (String) out.get("O_N_EJEC");
			this.error = new Error(0, mensaje, mensajeInterno);
			logger.error("Error al registrar personal del grupo.", e.getMessage());
		}

		return salida;
	}

	private Map<String, Object> obtenerSalidaValidacion(Map<String, Object> outProcedure) {
		final Map<String, Object> salida = new LinkedHashMap<>();
		String mensaje = outProcedure.get("O_MENSAJE") != null ? outProcedure.get("O_MENSAJE").toString()
				: null;
		salida.put("mensaje", mensaje);
		salida.put("existe", ((BigDecimal) outProcedure.get("O_EXISTE")).intValue());
		return salida;
	}

}
