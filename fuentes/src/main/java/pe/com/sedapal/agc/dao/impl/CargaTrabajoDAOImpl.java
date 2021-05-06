package pe.com.sedapal.agc.dao.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.ICargaTrabajoDAO;
import pe.com.sedapal.agc.dao.IDistribucionAvisoCobbranzaDAO;
import pe.com.sedapal.agc.dao.IDistribucionComunicacionesDAO;
import pe.com.sedapal.agc.dao.IInspeccionesComercialesDAO;
import pe.com.sedapal.agc.dao.IMedidoresDAO;
import pe.com.sedapal.agc.dao.ITomaEstadosDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.CargaTrabajo;
import pe.com.sedapal.agc.model.DistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.DistribucionComunicaciones;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.InspeccionesComerciales;
import pe.com.sedapal.agc.model.LineaFallaCarga;
import pe.com.sedapal.agc.model.Medidores;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.Personal;
import pe.com.sedapal.agc.model.Responsable;
import pe.com.sedapal.agc.model.TamanioAdjuntos;
import pe.com.sedapal.agc.model.TomaEstados;
import pe.com.sedapal.agc.model.enums.CabDistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.enums.CabDistribucionComunicaciones;
import pe.com.sedapal.agc.model.enums.CabInspeccionesComerciales;
import pe.com.sedapal.agc.model.enums.CabMedidores;
import pe.com.sedapal.agc.model.enums.CabTomaEstados;
import pe.com.sedapal.agc.model.request.AnularCargaRequest;
import pe.com.sedapal.agc.model.request.CargaTrabajoRequest;
import pe.com.sedapal.agc.model.request.EnvioCargaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.ICargaTrabajoServicio;
import pe.com.sedapal.agc.util.CabeceraDetalleCarga;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.validacionCodigoOperadorDetalleCarga;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class CargaTrabajoDAOImpl implements ICargaTrabajoDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CargaTrabajoDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	private Error error;
	public Error getError() {
		return this.error;
	}
	
	private Paginacion paginacion;
	public Paginacion getPaginacion() {
		return this.paginacion;
	}	
	
	private List<Adjunto> mapeaAdjunto(Map<String, Object> resultados) {
		Adjunto item;
		List<Adjunto> listaAdjuntos = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("LISTA_ADJUNTOS");
		for (Map<String, Object> map : lista) {
			item = new Adjunto();
			item.setUidCarga((String) map.get("V_IDCARGA"));
			item.setUidAdjunto(((BigDecimal) map.get("N_IDADJUNTO")).intValue());
			item.setTipoOrigen((String) map.get("V_TIPOORIGEN"));
			item.setTipo((String) map.get("V_TIPO"));			
			item.setRuta((String) map.get("V_RUTA"));
			item.setUsuarioModificacion((String) map.get("A_V_USUMOD"));
			item.setUsuarioCreacion((String) map.get("A_V_USUCRE"));
			Timestamp fecha1 = (Timestamp) map.get("A_D_FECMOD");
			item.setFechaModificacion(fecha1.getTime());
			Timestamp fecha2 = (Timestamp) map.get("A_D_FECCRE");
			item.setFechaCreacion(fecha2.getTime());
			item.setFechaCarga(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha2));			
			item.setNombre((String) map.get("V_NOMBRADJUN"));
			item.setExtension((String) map.get("V_EXTENSION"));
			item.setEstado((String) map.get("V_ESTAADJU"));
			listaAdjuntos.add(item);
			if (map.get("RESULT_COUNT") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("RESULT_COUNT")).intValue());
			}
		}
		return listaAdjuntos;
	}
	
	@Override
	public List<Adjunto> obtenerListaAdjuntosSedapal(String V_V_IDCARGA, PageRequest pageSedapal) {
		
		List<Adjunto> listaAdjuntos = new ArrayList<>();
		Map<String, Object> out = null;
		try {
			this.paginacion = new Paginacion();		
			this.paginacion.setPagina(pageSedapal.getPagina());
			this.paginacion.setRegistros(pageSedapal.getRegistros());		
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ENVIO)
					.withProcedureName(DbConstants.PRC_LIS_ADJUNTOS_SEDAPAL)
					.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
										new SqlParameter("N_PAGINA_SEDAPAL", OracleTypes.INTEGER),
										new SqlParameter("N_REGISTROS_SEDAPAL", OracleTypes.INTEGER),
										new SqlOutParameter("LISTA_ADJUNTOS", OracleTypes.CURSOR),
										new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
										new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
										new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", V_V_IDCARGA)
					.addValue("N_PAGINA_SEDAPAL", pageSedapal.getPagina())
					.addValue("N_REGISTROS_SEDAPAL", pageSedapal.getRegistros())
					;
			out = this.jdbcCall.execute(in);
			listaAdjuntos = mapeaAdjunto(out);
		}catch(Exception e) {
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerListaAdjuntosSedapal()] - "+e.getMessage());
		}
		return listaAdjuntos;		
	}

	@Override
	public List<Adjunto> obtenerListaAdjuntosContratista(String V_V_IDCARGA, PageRequest pageContratista) {
		List<Adjunto> listaAdjuntos = new ArrayList<>();
		Map<String, Object> out = null;
		try {
		this.paginacion = new Paginacion();
		this.paginacion.setPagina(pageContratista.getPagina());
		this.paginacion.setRegistros(pageContratista.getRegistros());		
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ENVIO)
					.withProcedureName(DbConstants.PRC_LIS_ADJUNTOS_CONTRATISTA)
					.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
										new SqlParameter("N_PAGINA_CONTRATISTA", OracleTypes.INTEGER),
										new SqlParameter("N_REGISTROS_CONTRATISTA", OracleTypes.INTEGER),
										new SqlOutParameter("LISTA_ADJUNTOS", OracleTypes.CURSOR),
										new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
										new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
										new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", V_V_IDCARGA)
					.addValue("N_PAGINA_CONTRATISTA", pageContratista.getPagina())
					.addValue("N_REGISTROS_CONTRATISTA", pageContratista.getRegistros())
					;
			out = this.jdbcCall.execute(in);
			listaAdjuntos = mapeaAdjunto(out);
		}catch(Exception e) {
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerListaAdjuntosContratista()] - "+e.getMessage());
		}
		return listaAdjuntos;		
	}

	
	@Override
	public List<Adjunto> obtenerListaAdjuntos(String V_V_IDCARGA) {
		List<Adjunto> listaAdjuntos = new ArrayList<>();
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_ADJUNTOS_CARGA)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
									new SqlOutParameter("LISTA_ADJUNTOS", OracleTypes.CURSOR),
									new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
									new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
									new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", V_V_IDCARGA);
		out = this.jdbcCall.execute(in);
		listaAdjuntos = mapeaAdjunto(out);
		return listaAdjuntos;
	}
	
	
	@Override
	public Integer anularCargaTrabajo(String V_V_IDCARGA, String V_A_V_USUMOD, String V_V_MOTIVMOV) {
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ENVIO)
					.withProcedureName(DbConstants.PRC_ANU_CARGA_TRABAJO)
					.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlParameter("V_V_MOTIVMOV", OracleTypes.VARCHAR));

			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", V_V_IDCARGA)
					.addValue("V_A_V_USUMOD", V_A_V_USUMOD)
					.addValue("V_V_MOTIVMOV", V_V_MOTIVMOV);
			
			out = this.jdbcCall.execute(in);
			return ((BigDecimal) out.get("N_RESP")).intValue();
		} catch (Exception e) {
			logger.error("[AGC: CargaTrabajoDAOImpl - anularCargaTrabajo()] - "+e.getMessage());
			return 0;
		}				
	}

	@Override
	public Map<String, Object> obtenerCargas(CargaTrabajo ct, Integer iPagina, Integer iRegistros) throws ParseException {
		
		Integer V_N_TIPO_ESTADO;
		//String V_V_IDUSUCARGA;
		String V_V_IDUSUCARGA =  "G";
		switch (ct.getIdPerfil().toString()) {
			case Constantes.PERFIL_ANALISTA_INTERNO:  
				V_N_TIPO_ESTADO = Constantes.TIPO_ESTADOS_TODOS;
				V_V_IDUSUCARGA ="G";
				break;
			case Constantes.PERFIL_RESPONSABLE:
				V_N_TIPO_ESTADO = Constantes.TIPO_ESTADOS_TODOS;
				V_V_IDUSUCARGA = "G";
				break;
			case Constantes.PERFIL_ANALISTA_EXTERNO:
				V_N_TIPO_ESTADO = Constantes.TIPO_ESTADOS_CONTRATISTA;
				V_V_IDUSUCARGA = "G";
				break;	
			case Constantes.PERFIL_SUPERVISOR_EXTERNO:
                V_N_TIPO_ESTADO = Constantes.TIPO_ESTADOS_CONTRATISTA;
                V_V_IDUSUCARGA = "G";
                break;
			default:
				V_N_TIPO_ESTADO = Constantes.TIPO_ESTADOS_TODOS;
				V_V_IDUSUCARGA = "G";
				break;		
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date f_Aux;
		Date fi,ff;
		
		f_Aux = format.parse(ct.getFechaInicio().toString().substring(8,10) + '/' + ct.getFechaInicio().toString().substring(5,7) + '/' + ct.getFechaInicio().toString().substring(0,4));
		fi = new Date(f_Aux.getTime());

		f_Aux = format.parse(ct.getFechaFin().toString().substring(8,10) + '/' + ct.getFechaFin().toString().substring(5,7) + '/' + ct.getFechaFin().toString().substring(0,4));
		ff = new Date(f_Aux.getTime());
		
		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ENVIO)
					.withProcedureName(DbConstants.PRC_LIS_CARGA_TRABAJO_X_RESP)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_V_IDUSUCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
							new SqlParameter("V_V_IDESTADO", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDCONTRATI", OracleTypes.NUMBER),
							new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FECENVSEDA", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FECINI", OracleTypes.DATE),
							new SqlParameter("V_D_FECFIN", OracleTypes.DATE),
							new SqlParameter("V_N_IDPERS", OracleTypes.INTEGER),
							new SqlParameter("V_N_IDPEFIL", OracleTypes.INTEGER),
							new SqlParameter("V_N_TIPO_ESTADO", OracleTypes.INTEGER),
							new SqlParameter("V_V_DESCCARGA", OracleTypes.VARCHAR),
							new SqlParameter("N_PAGINA", OracleTypes.NUMBER),
							new SqlParameter("N_REGISTROS", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			MapSqlParameterSource paraMap = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", ct.getUidCargaTrabajo())
					.addValue("V_V_IDUSUCARGA", ct.getUidUsuarioC() == null ? "G" : ct.getUidUsuarioC())
					.addValue("V_N_IDOFIC", ct.getUidOficina())
					.addValue("V_V_IDESTADO", ct.getUidEstado())
					.addValue("V_N_IDCONTRATI", ct.getUidContratista())
					.addValue("V_V_IDACTI", ct.getUidActividad())
					.addValue("V_D_FECENVSEDA", ct.getFechaSedapal())
					.addValue("V_D_FECINI", fi)
					.addValue("V_D_FECFIN", ff)
					.addValue("V_N_IDPERS", ct.getIdPers())
					.addValue("V_N_IDPEFIL", ct.getIdPerfil())
					.addValue("V_N_TIPO_ESTADO", V_N_TIPO_ESTADO)
					.addValue("V_V_DESCCARGA", ct.getVdescripcion())
					.addValue("N_PAGINA", iPagina)
					.addValue("N_REGISTROS", iRegistros);
			out = this.jdbcCall.execute(paraMap);			
		} catch (Exception e) {
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerCargas()] - "+e.getMessage());
		}
		return out;
	}
	
	@Override
	public Integer registrarEnvioCargaTrabajo(EnvioCargaRequest carga) {
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_REG_ENVIO_CARGA_TRABAJO)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.NUMBER),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.NUMBER),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_V_IDCARGA", carga.getUidCarga())
				.addValue("V_A_V_USUMOD", carga.getUsuario());

		try {
			out = this.jdbcCall.execute(in);
			resultado = (BigDecimal) out.get("N_RESP");

			if (resultado.intValue() != 1) {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - registrarEnvioCargaTrabajo()] - "+e.getMessage());
		}
		return resultado.intValue();
	}

	@Override
	public Integer guardarAdjunto(Adjunto adj) {
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_REG_ADJUNTO_CARGA)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.NUMBER),
						new SqlParameter("V_V_TIPOORIGEN", OracleTypes.VARCHAR),
						new SqlParameter("V_V_TIPO", OracleTypes.VARCHAR),
						new SqlParameter("V_V_RUTA", OracleTypes.VARCHAR),
						new SqlParameter("V_A_V_USUCRE", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_V_IDCARGA", adj.getUidCarga())
				.addValue("V_V_TIPOORIGEN", adj.getTipoOrigen()).addValue("V_V_TIPO", adj.getTipo())
				.addValue("V_V_RUTA", adj.getRuta()).addValue("V_A_V_USUCRE", adj.getUsuarioCreacion());

		try {
			out = this.jdbcCall.execute(in);
			resultado = (BigDecimal) out.get("N_RESP");

			if (resultado.intValue() != 1) {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - guardarAdjunto()] - "+e.getMessage());
		}
		return resultado.intValue();
	}

	@Override
	public List<Adjunto> obtenerAdjuntos(Adjunto adj) {
		Map<String, Object> out = null;
		List<Adjunto> lista = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_ADJUNTO_CARGA_ENV)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDADJUNTO", OracleTypes.NUMBER),
						new SqlParameter("V_V_TIPOORIGEN", OracleTypes.VARCHAR),
						new SqlParameter("V_V_TIPO", OracleTypes.VARCHAR),
						new SqlParameter("V_V_RUTA", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_V_IDCARGA", adj.getUidCarga())
				.addValue("V_N_IDADJUNTO", adj.getUidAdjunto()).addValue("V_V_TIPOORIGEN", adj.getTipoOrigen())
				.addValue("V_V_TIPO", adj.getTipo()).addValue("V_V_RUTA", adj.getRuta());

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				lista = mapeaAdjunto(out);
			} else {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado, mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerAdjuntos()] - "+e.getMessage());
		}
		return lista;
	}

	@Override
	public Integer anularCarga(AnularCargaRequest ct) {
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_ANU_CARGA_TRABAJO_ENVIO)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.NUMBER),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_V_IDCARGA", ct.getUidCarga())
				.addValue("V_A_V_USUMOD", ct.getUsuario());

		try {
			out = this.jdbcCall.execute(in);
			resultado = (BigDecimal) out.get("N_RESP");

			if (resultado.intValue() != 1) {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			resultado = (BigDecimal) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - anularCarga()] - "+e.getMessage());
		}
		return resultado.intValue();
	}

	@Override
	public List<Personal> obtenerDatosCorreo(Personal datos) {
		Map<String, Object> out = null;
		List<Personal> lista = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_OBT_DATOS_CORREO_ENV)
				.declareParameters(new SqlParameter("V_N_IDPERS", OracleTypes.NUMBER),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDPERS", datos.getUidPersonal());

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				lista = mapeaPersonal(out);
			} else {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado, mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerDatosCorreo()] - "+e.getMessage());
		}
		return lista;
	}

	private List<Personal> mapeaPersonal(Map<String, Object> resultados) {
		Personal item;
		List<Personal> listaPersonal = new ArrayList<>();

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");

		for (Map<String, Object> map : lista) {
			item = new Personal();
			item.setUidPersonal(((Integer) map.get("n_idpers")).intValue());
			item.setTelefono((String) map.get("v_telepers"));
			item.setNombres((String) map.get("v_nombpers"));
			item.setMail((String) map.get("v_mailpers"));
			item.setUidUsuario((String) map.get("v_idusuario"));
			item.setEstado((String) map.get("v_estapers"));
			item.setApellidos((String) map.get("v_apelpers"));
			listaPersonal.add(item);
		}
		return listaPersonal;
	}

	private String obtenerStringEnvio(List<Adjunto> V_V_LISTA_ADJ) {
		Integer i=0;
		String cadena="";
		if(V_V_LISTA_ADJ.size()>0) {
		for(i=0;i<=V_V_LISTA_ADJ.size() -1; i++) {
			cadena=cadena + V_V_LISTA_ADJ.get(i).getNombre() + '@' ;
		}
		cadena=cadena.substring(0,cadena.length() - 1);
		return cadena;
		} else {
			return null;
		}
	}
	
	@Override
	public String obtenerResponsablesEnvio(Responsable datos, String carga, String V_V_IDESTADO, List<Adjunto> V_V_LISTA_ADJ, String V_V_NOMCONTRA) {		
		String listaMail="";
		String listaAdjuntos=this.obtenerStringEnvio(V_V_LISTA_ADJ);
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_RESPONSABLES_ENVIO)
				.declareParameters(new SqlParameter("V_N_IDPERS", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_V_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_V_CARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDESTADO", OracleTypes.VARCHAR),
						new SqlParameter("V_V_LISTA_ADJ", OracleTypes.VARCHAR),
						new SqlParameter("V_V_NOMCONTRA", OracleTypes.VARCHAR),
						new SqlOutParameter("V_LISTA_MAIL", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_N_IDPERS", datos.getUidPersona())				
				.addValue("V_N_IDOFIC", datos.getUidOficina())
				.addValue("V_V_IDACTI", datos.getUidActividad())
				.addValue("V_V_CARGA", carga)
				.addValue("V_V_IDESTADO", V_V_IDESTADO)
				.addValue("V_V_LISTA_ADJ", listaAdjuntos)
				.addValue("V_V_NOMCONTRA", V_V_NOMCONTRA);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				listaMail = (String) out.get("V_LISTA_MAIL");
			} else {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado, mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerResponsablesEnvio()] - "+e.getMessage());
		}
		return listaMail;
	}

	private List<Responsable> mapeaResponsable(Map<String, Object> resultados) {
		Responsable item;
		List<Responsable> listaPersonal = new ArrayList<>();

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");

		for (Map<String, Object> map : lista) {
			item = new Responsable();
			item.setUidPersonal(((Integer) map.get("n_idpers")).intValue());
			item.setUidOficina((Number) map.get("n_idofic"));
			item.setUidGrupo((Number) map.get("n_idgrupo"));
			item.setUidActividad((String) map.get("n_idacti"));
			item.setEstadoResponsable((String) map.get("v_estaresp"));
			item.setUidOficinaRes((Number) map.get("n_ofiidofic"));
			item.setUidGrupoRes((Number) map.get("n_ofiidgrup"));
			item.setTelefono((String) map.get("v_telepers"));
			item.setNombres((String) map.get("v_nombpers"));
			item.setApellidos((String) map.get("v_apelpers"));
			item.setMail((String) map.get("v_mailpers"));
			item.setUidUsuario((String) map.get("v_idusuario"));
			item.setEstado((String) map.get("v_estapers"));
			item.setUidOficina((Number) map.get("n_idofic"));
			item.setTipoEmpresa((Number) map.get("v_tipo_empr"));
			listaPersonal.add(item);

		}
		return listaPersonal;
	}

	@Override
	public List<Object> obtenerListaRegistros(String uidCarga) {
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_REG_ADJUNTO_CARGA)
				.declareParameters(
						new SqlParameter("CODIGO_CARGA", OracleTypes.VARCHAR))
				.returningResultSet("NOMBRE_CURSOR", BeanPropertyRowMapper.newInstance(Object.class));
		
		MapSqlParameterSource in = new MapSqlParameterSource().addValue("CODIGO_CARGA", uidCarga);
		
		List<Object> object = (List<Object>) this.jdbcCall.execute(in).get("NOMBRE_CURSOR");

		return null;
	}
	
	@Override
	public Integer registrarMovCargaTrabajoCab(EnvioCargaRequest carga, String estado) {		
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_REG_MOV_CARGA_TRABAJO_CAB)
				.declareParameters(
						new SqlParameter("V_V_IDCARGA", OracleTypes.NUMBER),
						new SqlParameter("V_V_ESTADO", OracleTypes.VARCHAR),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),						
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARGA", carga.getUidCarga())
				.addValue("V_V_ESTADO", estado)
				.addValue("V_A_V_USUMOD", carga.getUsuario())
				;
		
		try {
			out = this.jdbcCall.execute(in);
			resultado = (BigDecimal)out.get("N_RESP");
			
			if(resultado.intValue() != 1) {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}
		}catch(Exception e){
			resultado = (BigDecimal)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado.intValue(),mensaje,mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - registrarMovCargaTrabajoCab()] - "+e.getMessage());
		}
		return resultado.intValue();
	}
	
	@Override
	public List<CargaTrabajo> obtenerDetalleCarga(String uidCarga) {
		Map<String, Object> out = null;
		List<CargaTrabajo> lista = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_OBT_CARGA_TRABAJO_DESC)
				.declareParameters(new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),						
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_V_IDCARGA", uidCarga);

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				lista = mapeaCarga(out);
			} else {
				String mensaje = (String) out.get("V_EJEC");
				String mensajeInterno = (String) out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("N_RESP");
			String mensaje = (String) out.get("V_EJEC");
			String mensajeInterno = (String) out.get("N_EJEC");
			this.error = new Error(resultado, mensaje, mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerDetalleCarga()] - "+e.getMessage());
		}
		return lista;
	}
	

	private List<CargaTrabajo> mapeaCarga(Map<String, Object> resultados) {
		CargaTrabajo item;
		List<CargaTrabajo> listaCarga = new ArrayList<>();
		BigDecimal bigDecimal;
		String sFecha;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");

		for (Map<String, Object> map : lista) {
			item = new CargaTrabajo();
			item.setUidCargaTrabajo((String) map.get("v_idcarga"));
			item.setMotivoAnula((String) map.get("v_motivoanul"));
			item.setUidUsuarioE((String) map.get("v_idusuejec"));
			item.setUsuarioNombreC((String) map.get("v_idusucarga"));
			bigDecimal = (BigDecimal)map.get("n_idofic");
			item.setUidOficina(bigDecimal.intValue());			
			item.setDescOficina((String) map.get("descOficina"));			
			bigDecimal = (BigDecimal)map.get("n_idgrupo");
			item.setUidGrupo(bigDecimal.intValue());
			item.setUidEstado((String) map.get("v_idestado"));
			item.setEstado((String) map.get("v_descdeta"));
			bigDecimal = (BigDecimal)map.get("n_idcontrati");
			item.setUidContratista(bigDecimal.intValue());
			item.setDescContratista((String) map.get("descContratista"));
			item.setUidActividad((String) map.get("v_idacti"));			
			item.setDescActividad((String) map.get("descActividad"));
			bigDecimal = (BigDecimal)map.get("n_canregejec");
			item.setCantidadEjecutada(bigDecimal.intValue());
			bigDecimal = (BigDecimal)map.get("n_canregcarg");
			item.setCantidadCarga(bigDecimal.intValue());			
			sFecha = new SimpleDateFormat("MM/dd/yyyy").format((Timestamp) map.get("d_fecenvseda"));
			item.setFechaSedapal(sFecha);
			sFecha = new SimpleDateFormat("MM/dd/yyyy").format((Timestamp) map.get("d_fecenvcont"));
			item.setFechaContratista(sFecha);
			sFecha = new SimpleDateFormat("MM/dd/yyyy").format((Timestamp) map.get("d_feccarga"));
			item.setFechaCarga(sFecha);			
			listaCarga.add(item);

		}
		return listaCarga;
	}

	@SuppressWarnings("unchecked")
	private ParametrosCargaBandeja MapearParametros(Map<String, Object> resultados) {
		List<Map<String, Object>> listaEmpresa = (List<Map<String, Object>>) resultados.get("C_OUT_EMPR");
		List<Map<String, Object>> listaOficina = (List<Map<String, Object>>) resultados.get("C_OUT_OFIC");
		List<Map<String, Object>> listaEstado = (List<Map<String, Object>>) resultados.get("C_OUT_ESTA");
		List<Map<String, Object>> listaActividad = (List<Map<String, Object>>) resultados.get("C_OUT_ACTI");
		Integer codGrupo = (Integer) resultados.get("N_CODGRUPO");
		String descGrupo = resultados.get("V_DESCGRUPO").toString();
		Integer codEmpresa = (Integer) resultados.get("N_CODEMPRESA");
		String nombreEmpresa = resultados.get("V_DESCEMPRESA").toString();
		ParametrosCargaBandeja params = new ParametrosCargaBandeja();
		List<Empresa> listaEmp = new ArrayList<Empresa>();
		List<Oficina> listaOfc = new ArrayList<Oficina>();
		List<Parametro> listaPar = new ArrayList<Parametro>();
		List<Actividad> listaAct = new ArrayList<Actividad>();
		Empresa itemEmpresa;
		Oficina itemOficina;
		Parametro itemParametro;
		Actividad itemActividad;
		for(Map<String, Object> map: listaEmpresa) {
			itemEmpresa = new Empresa();
			itemEmpresa.setCodigo(Integer.parseInt(((BigDecimal) map.get("N_IDEMPR")).toString()));
			itemEmpresa.setDescripcion((String) map.get("V_NOMBREMPR"));
			itemEmpresa.setTipoEmpresa((String) map.get("V_TIPOEMPR"));
			itemEmpresa.setEstado(CastUtil.leerValorMapString(map, "V_ESTAEMPR"));
			listaEmp.add(itemEmpresa);
		}
		for(Map<String, Object> map: listaOficina) {
			itemOficina = new Oficina();
			itemOficina.setCodigo(Integer.parseInt(((BigDecimal)map.get("N_IDOFIC")).toString()));
			itemOficina.setDireccion((String)map.get("V_DIREOFIC"));
			itemOficina.setDescripcion((String)map.get("V_DESCOFIC"));
			listaOfc.add(itemOficina);
		}
		for(Map<String, Object> map: listaEstado) {
			itemParametro = new Parametro();
			itemParametro.setCodigo(Integer.parseInt(((BigDecimal)map.get("N_IDTIPOPARA")).toString()));
			itemParametro.setTipo(Integer.parseInt(((BigDecimal)map.get("N_IDPARAMETR")).toString()));
			itemParametro.setValor((String)map.get("V_IDVALO"));
			itemParametro.setDescripcionCorta((String)map.get("V_DESCCORT"));
			itemParametro.setDetalle((String)map.get("V_DESCDETA"));
			listaPar.add(itemParametro);
		}
		if (listaActividad != null && listaActividad.size() > 0) {
			for (Map<String, Object> map : listaActividad) {
				itemActividad = new Actividad();
				itemActividad.setCodigo((String) map.get("V_IDACTI"));
				itemActividad.setDescripcion((String) map.get("V_DESCACTI"));
				listaAct.add(itemActividad);
			}
		}
		params.setListaEmpresa(listaEmp);
		params.setListaEstado(listaPar);
		params.setListaOficina(listaOfc);
		params.setListaActividad(listaAct);
		params.setCodGrupo(codGrupo);
		params.setDescGrupo(descGrupo);
		params.setCodEmpresa(codEmpresa);
		params.setNomEmpresa(nombreEmpresa);
		return params;
	}
	@Override 
	public ParametrosCargaBandeja obtenerParametros(Integer V_N_IDPERS, Integer V_N_IDPERFIL){
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO).withProcedureName(DbConstants.PRC_OBT_PARAMETROS_BANDEJA)
				.declareParameters(new SqlParameter("V_N_IDPERS", OracleTypes.INTEGER),
						new SqlParameter("V_N_IDPERFIL", OracleTypes.INTEGER),
						new SqlOutParameter("C_OUT_EMPR", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_OFIC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_ESTA", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_ACTI", OracleTypes.CURSOR),
						new SqlOutParameter("N_CODGRUPO", OracleTypes.INTEGER),
						new SqlOutParameter("V_DESCGRUPO", OracleTypes.VARCHAR),
						new SqlOutParameter("N_CODEMPRESA", OracleTypes.INTEGER),
						new SqlOutParameter("V_DESCEMPRESA", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDPERS", V_N_IDPERS)
				.addValue("V_N_IDPERFIL", V_N_IDPERFIL);
		
		try {
			out = this.jdbcCall.execute(in);
			resultado = (BigDecimal) out.get("N_RESP");			
			if(resultado.intValue() != 1) {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}else {
				return MapearParametros(out);
			}
		}catch(Exception e){
			resultado = (BigDecimal)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado.intValue(),mensaje,mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerParametros()] - "+e.getMessage());
		}
		return null;
	}

	@Override
	public Integer modificarEstadoCarga(CargaTrabajoRequest ct) {
		BigDecimal resultado = new BigDecimal("0");
		Map<String, Object> out = null;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_REG_MOV_CARGA_TRABAJO_CAB)
				.declareParameters(
						new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
						new SqlParameter("V_V_MOTIVOANUL", OracleTypes.VARCHAR),
						new SqlParameter("V_V_IDESTADO", OracleTypes.VARCHAR),
						new SqlParameter("V_V_COMENTARIO", OracleTypes.VARCHAR),
						new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDCONTRATI", OracleTypes.INTEGER),
						new SqlParameter("V_ACCION", OracleTypes.VARCHAR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),						
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		
	
			
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", ct.getUidCargaTrabajo())
					.addValue("V_V_MOTIVOANUL", ct.getMotivoAnula() == null ? " " : ct.getMotivoAnula())
					.addValue("V_V_IDESTADO", ct.getUidEstado())
					.addValue("V_V_COMENTARIO", ct.getComentario())
					.addValue("V_A_V_USUMOD", ct.getUsuario())
					.addValue("V_N_IDCONTRATI", ct.getUidContratista())
					.addValue("V_ACCION", ct.getAccion());
		try {		
			out = this.jdbcCall.execute(in);
			
			resultado = (BigDecimal)out.get("N_RESP");
			
			
			if(resultado.intValue() == 0) {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado.intValue(), mensaje, mensajeInterno);
			}
		}catch(Exception e){
			resultado = (BigDecimal)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado.intValue(),mensaje,mensajeInterno);
			logger.error("[AGC: CargaTrabajoDAOImpl - modificarEstadoCarga()] - "+e.getMessage());
		}
		return resultado.intValue();
	}
	@Override
	public List<Error> registrarTramaDistribucionAvisoCobranza(DistribucionAvisoCobranza distribucionAvisoCobranza, CargaTrabajoRequest cargaTrabajoRequest) {
		this.error = null;
		List<Error> errors = new ArrayList<Error>();
		errors = validateTypesActivity(distribucionAvisoCobranza, cargaTrabajoRequest);
		if (errors.isEmpty()) {
			try {
				this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
						.withProcedureName(DbConstants.PRC_REGI_DIS_AVI_LINE)
						.declareParameters(
								new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
								new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
								new SqlParameter("V_COD_PERSONA", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_IMP", OracleTypes.VARCHAR),
								new SqlParameter("V_F_DISTRIB", OracleTypes.DATE),
								new SqlParameter("V_HORA_DISTRIB", OracleTypes.NUMBER),
								new SqlParameter("V_TIP_ENT", OracleTypes.VARCHAR),
								new SqlParameter("V_A_V_USUARIO", OracleTypes.VARCHAR),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

				MapSqlParameterSource parametrosOracle = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", distribucionAvisoCobranza.getCodigoCarga())
						.addValue("V_N_IDREG", distribucionAvisoCobranza.getCodigoRegistro())
						.addValue("V_COD_PERSONA", distribucionAvisoCobranza.getCodigoDistribuidor())
						.addValue("V_COD_IMP", distribucionAvisoCobranza.getImposibilidad())
						.addValue("V_F_DISTRIB", distribucionAvisoCobranza.getFechaDistribucion())
						.addValue("V_HORA_DISTRIB", distribucionAvisoCobranza.getHoraDistribucion())
						.addValue("V_TIP_ENT", distribucionAvisoCobranza.getTipoEntrada())
						.addValue("V_A_V_USUARIO", cargaTrabajoRequest.getUsuario());

				Map<String, Object> ejecucionOracle = this.jdbcCall.execute(parametrosOracle);
				this.error = Constantes.validarRespuestaOracle(ejecucionOracle);
			} catch (Exception exception){
				this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
				logger.error("[AGC: CargaTrabajoDAOImpl - registrarTramaDistribucionAvisoCobranza()] - "+exception.getMessage());
			}
		}
		return errors;
	}

	@Override
	public List<Error> registrarTramaDistribucionComunicaciones(DistribucionComunicaciones distribucionComunicaciones, CargaTrabajoRequest cargaTrabajoRequest) {
		this.error = null;
		List<Error> errors = new ArrayList<Error>();
		errors = validateTypesActivity(distribucionComunicaciones, cargaTrabajoRequest);
		if (errors.isEmpty()) {
			try {
				this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
						.withProcedureName(DbConstants.PRC_REGI_DIS_COM_LINE)
						.declareParameters(
								new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
								new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
								new SqlParameter("V_NVISITA", OracleTypes.VARCHAR),
								new SqlParameter("V_FNOTIF1", OracleTypes.DATE),
								new SqlParameter("V_HNOTIF1", OracleTypes.VARCHAR),
								new SqlParameter("V_FNOTIF2", OracleTypes.DATE),
								new SqlParameter("V_HNOTIF2", OracleTypes.VARCHAR),
								new SqlParameter("V_NOMNOTIF", OracleTypes.VARCHAR),
								new SqlParameter("V_DNINOTIF", OracleTypes.VARCHAR),
								new SqlParameter("V_PARENTESCO_N", OracleTypes.VARCHAR),
								new SqlParameter("V_OBS", OracleTypes.VARCHAR),
								new SqlParameter("V_OBS2", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_EMP1", OracleTypes.VARCHAR),
								new SqlParameter("V_NOM_EMP1", OracleTypes.VARCHAR),
								new SqlParameter("V_DNI_EMP1", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_EMP2", OracleTypes.VARCHAR),
								new SqlParameter("V_NOM_EMP2", OracleTypes.VARCHAR),
								new SqlParameter("V_DNI_EMP2", OracleTypes.VARCHAR),
								new SqlParameter("V_FNOTIF", OracleTypes.DATE),
								new SqlParameter("V_HNOTIF", OracleTypes.VARCHAR),
								new SqlParameter("V_TIP_ENTREGA", OracleTypes.VARCHAR),
								new SqlParameter("V_DIFICUL", OracleTypes.VARCHAR),
								new SqlParameter("V_A_V_USUARIO", OracleTypes.VARCHAR),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", distribucionComunicaciones.getCodigoCarga())
						.addValue("V_N_IDREG", distribucionComunicaciones.getCodigoRegistro())
						.addValue("V_NVISITA", distribucionComunicaciones.getNroVisitasNotificador())
						.addValue("V_FNOTIF1", distribucionComunicaciones.getFechaVisita1())
						.addValue("V_HNOTIF1", distribucionComunicaciones.getHoraVisita1())
						.addValue("V_FNOTIF2", distribucionComunicaciones.getFechaVisita2())
						.addValue("V_HNOTIF2", distribucionComunicaciones.getHoraVisita2())
						.addValue("V_NOMNOTIF", distribucionComunicaciones.getNombreNotificada())
						.addValue("V_DNINOTIF", distribucionComunicaciones.getDniNotificada())
						.addValue("V_PARENTESCO_N", distribucionComunicaciones.getParentezcoNotificada())
						.addValue("V_OBS", distribucionComunicaciones.getObsVisita1())
						.addValue("V_OBS2", distribucionComunicaciones.getObsVisita2())
						.addValue("V_COD_EMP1", distribucionComunicaciones.getCodigoNotificadorVisita1())
						.addValue("V_NOM_EMP1", distribucionComunicaciones.getNombreNotificadorVisita1())
						.addValue("V_DNI_EMP1", distribucionComunicaciones.getDniNotificador1())
						.addValue("V_COD_EMP2", distribucionComunicaciones.getCodigoNotificadorVisita2())
						.addValue("V_NOM_EMP2", distribucionComunicaciones.getNombreNotificadorVisita2())
						.addValue("V_DNI_EMP2", distribucionComunicaciones.getDniNotificador2())
						.addValue("V_FNOTIF", distribucionComunicaciones.getFechaConcretaNotificacion())
						.addValue("V_HNOTIF", distribucionComunicaciones.getHoraConNotificacion())
						.addValue("V_TIP_ENTREGA", distribucionComunicaciones.getTipoEntrega())
						.addValue("V_DIFICUL", distribucionComunicaciones.getDificultad())
						.addValue("V_A_V_USUARIO", cargaTrabajoRequest.getUsuario());

				Map<String, Object> ejecucionOracle = this.jdbcCall.execute(mapSqlParameterSource);
				this.error = Constantes.validarRespuestaOracle(ejecucionOracle);
			}
			catch (Exception exception){
				this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
				logger.error("[AGC: CargaTrabajoDAOImpl - registrarTramaDistribucionComunicaciones()] - "+exception.getMessage());
			}
		}
		return errors;
	}

	@Override
	public List<Error> registrarTramaInspeccionesComerciales(InspeccionesComerciales inspeccionesComerciales, CargaTrabajoRequest cargaTrabajoRequest) {
		this.error = null;
		List<Error> errors = new ArrayList<Error>();
		errors = validateTypesActivity(inspeccionesComerciales, cargaTrabajoRequest);
		if (errors.isEmpty()) {
			try {
				this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
						.withProcedureName(DbConstants.PRC_REGI_INS_COM_LINE)
						.declareParameters(
								new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
								new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
								new SqlParameter("V_PTO_MED", OracleTypes.VARCHAR),
								new SqlParameter("V_ACOM", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_EMP", OracleTypes.VARCHAR),
								new SqlParameter("V_FEC_VIS", OracleTypes.VARCHAR),
								new SqlParameter("V_CO_ACCEJ", OracleTypes.VARCHAR),
								new SqlParameter("V_SERV", OracleTypes.VARCHAR),
								new SqlParameter("V_SERV_DT", OracleTypes.VARCHAR),
								new SqlParameter("V_CAJA", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_CAJA", OracleTypes.VARCHAR),
								new SqlParameter("V_CNX_CON", OracleTypes.VARCHAR),
								new SqlParameter("V_CX_C_DT", OracleTypes.VARCHAR),
								new SqlParameter("V_LEC", OracleTypes.VARCHAR),
								new SqlParameter("V_FUN_MED1", OracleTypes.VARCHAR),
								new SqlParameter("V_FUN_MED2", OracleTypes.VARCHAR),
								new SqlParameter("V_FUN_MED3", OracleTypes.VARCHAR),
								new SqlParameter("V_PRECINT", OracleTypes.VARCHAR),
								new SqlParameter("V_FUGA_CAJ", OracleTypes.VARCHAR),
								new SqlParameter("V_N_CONEX", OracleTypes.VARCHAR),
								new SqlParameter("V_MED2", OracleTypes.VARCHAR),
								new SqlParameter("V_MED3", OracleTypes.VARCHAR),
								new SqlParameter("V_NIPLE2", OracleTypes.VARCHAR),
								new SqlParameter("V_NIPLE3", OracleTypes.VARCHAR),
								new SqlParameter("V_VALVULAS", OracleTypes.VARCHAR),
								new SqlParameter("V_DISPOSIT", OracleTypes.VARCHAR),
								new SqlParameter("V_CLANDES", OracleTypes.VARCHAR),
								new SqlParameter("V_NIV_FINC", OracleTypes.VARCHAR),
								new SqlParameter("V_ESTADO", OracleTypes.VARCHAR),
								new SqlParameter("V_N_HABIT", OracleTypes.VARCHAR),
								new SqlParameter("V_UU_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_SOC_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_DOM_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_COM_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_IND_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_OCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_UU_DOCUP", OracleTypes.VARCHAR),
								new SqlParameter("V_SOC_DESC", OracleTypes.VARCHAR),
								new SqlParameter("V_DOM_DESC", OracleTypes.VARCHAR),
								new SqlParameter("V_COM_DESC", OracleTypes.VARCHAR),
								new SqlParameter("V_IND_DESC", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_DESC", OracleTypes.VARCHAR),
								new SqlParameter("V_USO_UNIC", OracleTypes.VARCHAR),
								new SqlParameter("V_CALLE_C", OracleTypes.VARCHAR),
								new SqlParameter("V_NUM_C", OracleTypes.VARCHAR),
								new SqlParameter("V_DUPLI_C", OracleTypes.VARCHAR),
								new SqlParameter("V_MZA_C", OracleTypes.VARCHAR),
								new SqlParameter("V_LOTE_C", OracleTypes.VARCHAR),
								new SqlParameter("V_URB_C", OracleTypes.VARCHAR),
								new SqlParameter("V_REF_C", OracleTypes.VARCHAR),
								new SqlParameter("V_DIST_C", OracleTypes.VARCHAR),
								new SqlParameter("V_ATENDIO", OracleTypes.VARCHAR),
								new SqlParameter("V_PATERNO", OracleTypes.VARCHAR),
								new SqlParameter("V_MATERNO", OracleTypes.VARCHAR),
								new SqlParameter("V_NOMBRE", OracleTypes.VARCHAR),
								new SqlParameter("V_RAZSOC", OracleTypes.VARCHAR),
								new SqlParameter("V_DNI", OracleTypes.VARCHAR),
								new SqlParameter("V_TELEF", OracleTypes.VARCHAR),
								new SqlParameter("V_FAX", OracleTypes.VARCHAR),
								new SqlParameter("V_RUC", OracleTypes.VARCHAR),
								new SqlParameter("V_INOD_B", OracleTypes.VARCHAR),
								new SqlParameter("V_INOD_F", OracleTypes.VARCHAR),
								new SqlParameter("V_INOD_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_LAVA_B", OracleTypes.VARCHAR),
								new SqlParameter("V_LAVA_F", OracleTypes.VARCHAR),
								new SqlParameter("V_LAVA_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_DUCH_B", OracleTypes.VARCHAR),
								new SqlParameter("V_DUCH_F", OracleTypes.VARCHAR),
								new SqlParameter("V_DUCH_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_URIN_B", OracleTypes.VARCHAR),
								new SqlParameter("V_URIN_F", OracleTypes.VARCHAR),
								new SqlParameter("V_URIN_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_GRIFO_B", OracleTypes.VARCHAR),
								new SqlParameter("V_GRIFO_F", OracleTypes.VARCHAR),
								new SqlParameter("V_GRIFO_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_CISTE_B", OracleTypes.VARCHAR),
								new SqlParameter("V_CISTE_F", OracleTypes.VARCHAR),
								new SqlParameter("V_CISTE_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_TANQU_B", OracleTypes.VARCHAR),
								new SqlParameter("V_TANQU_F", OracleTypes.VARCHAR),
								new SqlParameter("V_TANQU_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_PISC_B", OracleTypes.VARCHAR),
								new SqlParameter("V_PISC_F", OracleTypes.VARCHAR),
								new SqlParameter("V_PISC_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_BIDET_B", OracleTypes.VARCHAR),
								new SqlParameter("V_BIDET_F", OracleTypes.VARCHAR),
								new SqlParameter("V_BIDET_CL", OracleTypes.VARCHAR),
								new SqlParameter("V_ABAST", OracleTypes.VARCHAR),
								new SqlParameter("V_PTOME_CA", OracleTypes.VARCHAR),
								new SqlParameter("V_NUM_P", OracleTypes.VARCHAR),
								new SqlParameter("V_DUP_P", OracleTypes.VARCHAR),
								new SqlParameter("V_MZA_P", OracleTypes.VARCHAR),
								new SqlParameter("V_LOTE_P", OracleTypes.VARCHAR),
								new SqlParameter("V_OBSERV", OracleTypes.VARCHAR),
								new SqlParameter("V_OBSERVM1", OracleTypes.VARCHAR),
								new SqlParameter("V_OBSERVM2", OracleTypes.VARCHAR),
								new SqlParameter("V_COTA_SUM_C", OracleTypes.VARCHAR),
								new SqlParameter("V_PISOS_C", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_UBIC_C", OracleTypes.VARCHAR),
								new SqlParameter("V_FTE_CONEXION_C", OracleTypes.VARCHAR),
								new SqlParameter("V_CUP_C", OracleTypes.VARCHAR),
								new SqlParameter("V_COTA_SUM", OracleTypes.VARCHAR),
								new SqlParameter("V_PISOS", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_UBIC", OracleTypes.VARCHAR),
								new SqlParameter("V_FTE_CONEXION", OracleTypes.VARCHAR),
								new SqlParameter("V_CUP", OracleTypes.VARCHAR),
								new SqlParameter("V_DEVUELTO", OracleTypes.VARCHAR),
								new SqlParameter("V_FECHA_D", OracleTypes.VARCHAR),
								new SqlParameter("V_PRIOR", OracleTypes.VARCHAR),
								new SqlParameter("V_ACTUALIZA", OracleTypes.VARCHAR),
								new SqlParameter("V_FECHA_C", OracleTypes.DATE),
								new SqlParameter("V_RESUELTO", OracleTypes.VARCHAR),
								new SqlParameter("V_MULTI", OracleTypes.VARCHAR),
								new SqlParameter("V_PARCIAL", OracleTypes.VARCHAR),
								new SqlParameter("V_FTE_CNX", OracleTypes.VARCHAR),
								new SqlParameter("V_COTA_SUMC", OracleTypes.VARCHAR),
								new SqlParameter("V_PISOSC", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_UBICC", OracleTypes.VARCHAR),
								new SqlParameter("V_FTE_CNXC", OracleTypes.VARCHAR),
								new SqlParameter("V_CUPC", OracleTypes.VARCHAR),
								new SqlParameter("V_HORA_I", OracleTypes.VARCHAR),
								new SqlParameter("V_HORA_F", OracleTypes.VARCHAR),
								new SqlParameter("V_VALV_E_TLSC", OracleTypes.VARCHAR),
								new SqlParameter("V_MAT_VALV_E", OracleTypes.VARCHAR),
								new SqlParameter("V_VALV_S_P", OracleTypes.VARCHAR),
								new SqlParameter("V_MAT_VALV_S", OracleTypes.VARCHAR),
								new SqlParameter("V_EMAIL", OracleTypes.VARCHAR),
								new SqlParameter("V_CEL_CLI_C", OracleTypes.NUMBER),
								new SqlParameter("V_TELF_EMP", OracleTypes.NUMBER),
								new SqlParameter("V_PISO_DPTO_F", OracleTypes.VARCHAR),
								new SqlParameter("V_SECTOR_F", OracleTypes.NUMBER),
								new SqlParameter("V_FTE_FINCA", OracleTypes.VARCHAR),
								new SqlParameter("V_NSE_F", OracleTypes.VARCHAR),
								new SqlParameter("V_OBS_F", OracleTypes.VARCHAR),
								new SqlParameter("V_CORR_CUP", OracleTypes.VARCHAR),
								new SqlParameter("V_PRED_NO_UBIC", OracleTypes.VARCHAR),
								new SqlParameter("V_VAR_ACT_PREDIO", OracleTypes.VARCHAR),
								new SqlParameter("V_INSPEC_REALIZ", OracleTypes.VARCHAR),
								new SqlParameter("V_MOT_NO_ING", OracleTypes.VARCHAR),
								new SqlParameter("V_DIA_CONEX_AGUA", OracleTypes.NUMBER),
								new SqlParameter("V_IMPOSIBILIDAD", OracleTypes.VARCHAR),
								new SqlParameter("V_SEGURO_TAP", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_TAPA", OracleTypes.VARCHAR),
								new SqlParameter("V_INCI_MEDIDOR", OracleTypes.VARCHAR),
								new SqlParameter("V_OBS_ATENDIO", OracleTypes.VARCHAR),
								new SqlParameter("V_CARTOGRAFIA", OracleTypes.VARCHAR),
								new SqlParameter("V_MNTO_CARTOG", OracleTypes.VARCHAR),
								new SqlParameter("V_CORR_CUP_F", OracleTypes.VARCHAR),
								new SqlParameter("V_NUM_MED", OracleTypes.VARCHAR),
								new SqlParameter("V_DIA_MED", OracleTypes.VARCHAR),
								new SqlParameter("V_CNX2_CON", OracleTypes.VARCHAR),
								new SqlParameter("V_CNX3_CON", OracleTypes.VARCHAR),
								new SqlParameter("V_DIA_MED2", OracleTypes.VARCHAR),
								new SqlParameter("V_DIA_MED3", OracleTypes.VARCHAR),
								new SqlParameter("V_LEC_MED2", OracleTypes.NUMBER),
								new SqlParameter("V_LEC_MED3", OracleTypes.NUMBER),
								new SqlParameter("V_COTA_HOR_DES", OracleTypes.NUMBER),
								new SqlParameter("V_COTA_VER_DES", OracleTypes.NUMBER),
								new SqlParameter("V_LONG_CNX_DES", OracleTypes.NUMBER),
								new SqlParameter("V_PROF_CJA_DES", OracleTypes.NUMBER),
								new SqlParameter("V_TIP_SUELO_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_IND_TRAM_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_DIA_CONEX_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_SILO", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_TAPA_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_CAJA_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_EST_TUB_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_TIP_MAT_TAP_D", OracleTypes.VARCHAR),
								new SqlParameter("V_TIP_MAT_CAJ_D", OracleTypes.VARCHAR),
								new SqlParameter("V_TIP_MAT_TUB_D", OracleTypes.VARCHAR),
								new SqlParameter("V_SIS_TRAT_RES", OracleTypes.VARCHAR),
								new SqlParameter("V_TIP_DES_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_CLANDES_DES", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_SUP", OracleTypes.NUMBER),
								new SqlParameter("V_COD_DIGIT", OracleTypes.NUMBER),
								new SqlParameter("V_A_V_USUARIO", OracleTypes.VARCHAR),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", inspeccionesComerciales.getCodigoCarga())
						.addValue("V_N_IDREG", inspeccionesComerciales.getCodigoRegistro())
						.addValue("V_PTO_MED", inspeccionesComerciales.getPtoMedia())
						.addValue("V_ACOM", inspeccionesComerciales.getAcom())
						.addValue("V_COD_EMP", inspeccionesComerciales.getCodEmpleado())
						.addValue("V_FEC_VIS", inspeccionesComerciales.getFechaVisita())
						.addValue("V_CO_ACCEJ", inspeccionesComerciales.getCoAccejec())
						.addValue("V_SERV", inspeccionesComerciales.getServ())
						.addValue("V_SERV_DT", inspeccionesComerciales.getServDT())
						.addValue("V_CAJA", inspeccionesComerciales.getCaja())
						.addValue("V_EST_CAJA", inspeccionesComerciales.getEstadoCaja())
						.addValue("V_CNX_CON", inspeccionesComerciales.getCnxCon())
						.addValue("V_CX_C_DT", inspeccionesComerciales.getCxCDT())
						.addValue("V_LEC", inspeccionesComerciales.getLecturaMedidor())
						.addValue("V_FUN_MED1", inspeccionesComerciales.getEstMedidorEncontrado1())
						.addValue("V_FUN_MED2", inspeccionesComerciales.getEstMedidorEncontrado2())
						.addValue("V_FUN_MED3", inspeccionesComerciales.getEstMedidorEncontrado3())
						.addValue("V_PRECINT", inspeccionesComerciales.getPrescinto())
						.addValue("V_FUGA_CAJ", inspeccionesComerciales.getFugaCaja())
						.addValue("V_N_CONEX", inspeccionesComerciales.getNroConexiones())
						.addValue("V_MED2", inspeccionesComerciales.getMedidorEncontrado2())
						.addValue("V_MED3", inspeccionesComerciales.getMedidorEncontrado3())
						.addValue("V_NIPLE2", inspeccionesComerciales.getNiple2())
						.addValue("V_NIPLE3", inspeccionesComerciales.getNiple3())
						.addValue("V_VALVULAS", inspeccionesComerciales.getValvulas())
						.addValue("V_DISPOSIT", inspeccionesComerciales.getDisposit())
						.addValue("V_CLANDES", inspeccionesComerciales.getClandes())
						.addValue("V_NIV_FINC", inspeccionesComerciales.getNiv_finc())
						.addValue("V_ESTADO", inspeccionesComerciales.getEstado())
						.addValue("V_N_HABIT", inspeccionesComerciales.getN_habit())
						.addValue("V_UU_OCUP", inspeccionesComerciales.getUu_ocup())
						.addValue("V_SOC_OCUP", inspeccionesComerciales.getSoc_ocup())
						.addValue("V_DOM_OCUP", inspeccionesComerciales.getDom_ocup())
						.addValue("V_COM_OCUP", inspeccionesComerciales.getCom_ocup())
						.addValue("V_IND_OCUP", inspeccionesComerciales.getInd_ocup())
						.addValue("V_EST_OCUP", inspeccionesComerciales.getEst_ocup())
						.addValue("V_UU_DOCUP", inspeccionesComerciales.getUu_docup())
						.addValue("V_SOC_DESC", inspeccionesComerciales.getSoc_desc())
						.addValue("V_DOM_DESC", inspeccionesComerciales.getDom_desc())
						.addValue("V_COM_DESC", inspeccionesComerciales.getCom_desc())
						.addValue("V_IND_DESC", inspeccionesComerciales.getInd_desc())
						.addValue("V_EST_DESC", inspeccionesComerciales.getEst_desc())
						.addValue("V_USO_UNIC", inspeccionesComerciales.getUso_unic())
						.addValue("V_CALLE_C", inspeccionesComerciales.getCalle_c())
						.addValue("V_NUM_C", inspeccionesComerciales.getNum_c())
						.addValue("V_DUPLI_C", inspeccionesComerciales.getDupli_c())
						.addValue("V_MZA_C", inspeccionesComerciales.getMza_c())
						.addValue("V_LOTE_C", inspeccionesComerciales.getLote_c())
						.addValue("V_URB_C", inspeccionesComerciales.getUrb_c())
						.addValue("V_REF_C", inspeccionesComerciales.getRef_c())
						.addValue("V_DIST_C", inspeccionesComerciales.getDist_c())
						.addValue("V_ATENDIO", inspeccionesComerciales.getAtendio())
						.addValue("V_PATERNO", inspeccionesComerciales.getPaterno())
						.addValue("V_MATERNO", inspeccionesComerciales.getMaterno())
						.addValue("V_NOMBRE", inspeccionesComerciales.getNombre())
						.addValue("V_RAZSOC", inspeccionesComerciales.getRazsoc())
						.addValue("V_DNI", inspeccionesComerciales.getDni())
						.addValue("V_TELEF", inspeccionesComerciales.getTelef())
						.addValue("V_FAX", inspeccionesComerciales.getFax())
						.addValue("V_RUC", inspeccionesComerciales.getRuc())
						.addValue("V_INOD_B", inspeccionesComerciales.getInod_b())
						.addValue("V_INOD_F", inspeccionesComerciales.getInod_f())
						.addValue("V_INOD_CL", inspeccionesComerciales.getInod_cl())
						.addValue("V_LAVA_B", inspeccionesComerciales.getLava_b())
						.addValue("V_LAVA_F", inspeccionesComerciales.getLava_f())
						.addValue("V_LAVA_CL", inspeccionesComerciales.getLava_cl())
						.addValue("V_DUCH_B", inspeccionesComerciales.getDuch_b())
						.addValue("V_DUCH_F", inspeccionesComerciales.getDuch_f())
						.addValue("V_DUCH_CL", inspeccionesComerciales.getDuch_cl())
						.addValue("V_URIN_B", inspeccionesComerciales.getUrin_b())
						.addValue("V_URIN_F", inspeccionesComerciales.getUrin_f())
						.addValue("V_URIN_CL", inspeccionesComerciales.getUrin_cl())
						.addValue("V_GRIFO_B", inspeccionesComerciales.getGrifo_b())
						.addValue("V_GRIFO_F", inspeccionesComerciales.getGrifo_f())
						.addValue("V_GRIFO_CL", inspeccionesComerciales.getGrifo_cl())
						.addValue("V_CISTE_B", inspeccionesComerciales.getCiste_b())
						.addValue("V_CISTE_F", inspeccionesComerciales.getCiste_f())
						.addValue("V_CISTE_CL", inspeccionesComerciales.getCiste_cl())
						.addValue("V_TANQU_B", inspeccionesComerciales.getTanqu_b())
						.addValue("V_TANQU_F", inspeccionesComerciales.getTanqu_f())
						.addValue("V_TANQU_CL", inspeccionesComerciales.getTanqu_cl())
						.addValue("V_PISC_B", inspeccionesComerciales.getPisc_b())
						.addValue("V_PISC_F", inspeccionesComerciales.getPisc_f())
						.addValue("V_PISC_CL", inspeccionesComerciales.getPisc_cl())
						.addValue("V_BIDET_B", inspeccionesComerciales.getBidet_b())
						.addValue("V_BIDET_F", inspeccionesComerciales.getBidet_f())
						.addValue("V_BIDET_CL", inspeccionesComerciales.getBidet_cl())
						.addValue("V_ABAST", inspeccionesComerciales.getAbast())
						.addValue("V_PTOME_CA", inspeccionesComerciales.getPtome_ca())
						.addValue("V_NUM_P", inspeccionesComerciales.getNum_p())
						.addValue("V_DUP_P", inspeccionesComerciales.getDup_p())
						.addValue("V_MZA_P", inspeccionesComerciales.getMza_p())
						.addValue("V_LOTE_P", inspeccionesComerciales.getLote_p())
						.addValue("V_OBSERV", inspeccionesComerciales.getObserv())
						.addValue("V_OBSERVM1", inspeccionesComerciales.getObservm1())
						.addValue("V_OBSERVM2", inspeccionesComerciales.getObservm2())
						.addValue("V_COTA_SUM_C", inspeccionesComerciales.getCota_sum_c())
						.addValue("V_PISOS_C", inspeccionesComerciales.getPisos_c())
						.addValue("V_COD_UBIC_C", inspeccionesComerciales.getCod_ubic_c())
						.addValue("V_FTE_CONEXION_C", inspeccionesComerciales.getFte_conexion_c())
						.addValue("V_CUP_C", inspeccionesComerciales.getCup_c())
						.addValue("V_COTA_SUM", inspeccionesComerciales.getCota_sum())
						.addValue("V_PISOS", inspeccionesComerciales.getPisos())
						.addValue("V_COD_UBIC", inspeccionesComerciales.getCod_ubic())
						.addValue("V_FTE_CONEXION", inspeccionesComerciales.getFte_conexion())
						.addValue("V_CUP", inspeccionesComerciales.getCup())
						.addValue("V_DEVUELTO", inspeccionesComerciales.getDevuelto())
						.addValue("V_FECHA_D", inspeccionesComerciales.getDate_d())
						.addValue("V_PRIOR", inspeccionesComerciales.getPrior())
						.addValue("V_ACTUALIZA", inspeccionesComerciales.getActualiza())
						.addValue("V_FECHA_C", inspeccionesComerciales.getDate_c())
						.addValue("V_RESUELTO", inspeccionesComerciales.getResuelto())
						.addValue("V_MULTI", inspeccionesComerciales.getMulti())
						.addValue("V_PARCIAL", inspeccionesComerciales.getParcial())
						.addValue("V_FTE_CNX", inspeccionesComerciales.getFte_cnx())
						.addValue("V_COTA_SUMC", inspeccionesComerciales.getCota_sumc())
						.addValue("V_PISOSC", inspeccionesComerciales.getPisosc())
						.addValue("V_COD_UBICC", inspeccionesComerciales.getCod_ubicc())
						.addValue("V_FTE_CNXC", inspeccionesComerciales.getFte_cnxc())
						.addValue("V_CUPC", inspeccionesComerciales.getCupc())
						.addValue("V_HORA_I", inspeccionesComerciales.getHora_i())
						.addValue("V_HORA_F", inspeccionesComerciales.getHora_f())
						.addValue("V_VALV_E_TLSC", inspeccionesComerciales.getValv_e_tlsc())
						.addValue("V_MAT_VALV_E", inspeccionesComerciales.getMat_valv_e())
						.addValue("V_VALV_S_P", inspeccionesComerciales.getValv_s_p())
						.addValue("V_MAT_VALV_S", inspeccionesComerciales.getMat_valv_s())
						.addValue("V_EMAIL", inspeccionesComerciales.getEmail())
						.addValue("V_CEL_CLI_C", inspeccionesComerciales.getCel_cli_c())
						.addValue("V_TELF_EMP", inspeccionesComerciales.getTelf_emp())
						.addValue("V_PISO_DPTO_F", inspeccionesComerciales.getPiso_dpto_f())
						.addValue("V_SECTOR_F", inspeccionesComerciales.getSector_f())
						.addValue("V_FTE_FINCA", inspeccionesComerciales.getFte_finca())
						.addValue("V_NSE_F", inspeccionesComerciales.getNse_f())
						.addValue("V_OBS_F", inspeccionesComerciales.getObs_f())
						.addValue("V_CORR_CUP", inspeccionesComerciales.getCorr_cup())
						.addValue("V_PRED_NO_UBIC", inspeccionesComerciales.getPred_no_ubic())
						.addValue("V_VAR_ACT_PREDIO", inspeccionesComerciales.getVar_act_predio())
						.addValue("V_INSPEC_REALIZ", inspeccionesComerciales.getInspec_realiz())
						.addValue("V_MOT_NO_ING", inspeccionesComerciales.getMot_no_ing())
						.addValue("V_DIA_CONEX_AGUA", inspeccionesComerciales.getDia_conex_agua())
						.addValue("V_IMPOSIBILIDAD", inspeccionesComerciales.getImposibilidad())
						.addValue("V_SEGURO_TAP", inspeccionesComerciales.getSeguro_tap())
						.addValue("V_EST_TAPA", inspeccionesComerciales.getEst_tapa())
						.addValue("V_INCI_MEDIDOR", inspeccionesComerciales.getInci_medidor())
						.addValue("V_OBS_ATENDIO", inspeccionesComerciales.getObs_atendio())
						.addValue("V_CARTOGRAFIA", inspeccionesComerciales.getCartografia())
						.addValue("V_MNTO_CARTOG", inspeccionesComerciales.getMnto_cartog())
						.addValue("V_CORR_CUP_F", inspeccionesComerciales.getCorr_cup_f())
						.addValue("V_NUM_MED", inspeccionesComerciales.getNum_med())
						.addValue("V_DIA_MED", inspeccionesComerciales.getDia_med())
						.addValue("V_CNX2_CON", inspeccionesComerciales.getCnx2_con())
						.addValue("V_CNX3_CON", inspeccionesComerciales.getCnx3_con())
						.addValue("V_DIA_MED2", inspeccionesComerciales.getDia_med2())
						.addValue("V_DIA_MED3", inspeccionesComerciales.getDia_med3())
						.addValue("V_LEC_MED2", inspeccionesComerciales.getLec_med2())
						.addValue("V_LEC_MED3", inspeccionesComerciales.getLec_med3())
						.addValue("V_COTA_HOR_DES", inspeccionesComerciales.getCota_hor_des())
						.addValue("V_COTA_VER_DES", inspeccionesComerciales.getCota_ver_des())
						.addValue("V_LONG_CNX_DES", inspeccionesComerciales.getLong_cnx_des())
						.addValue("V_PROF_CJA_DES", inspeccionesComerciales.getProf_cja_des())
						.addValue("V_TIP_SUELO_DES", inspeccionesComerciales.getTip_suelo_des())
						.addValue("V_IND_TRAM_DES", inspeccionesComerciales.getInd_tram_des())
						.addValue("V_DIA_CONEX_DES", inspeccionesComerciales.getDia_conex_des())
						.addValue("V_SILO", inspeccionesComerciales.getSilo())
						.addValue("V_EST_TAPA_DES", inspeccionesComerciales.getEst_tapa_des())
						.addValue("V_EST_CAJA_DES", inspeccionesComerciales.getEst_caja_des())
						.addValue("V_EST_TUB_DES", inspeccionesComerciales.getEst_tub_des())
						.addValue("V_TIP_MAT_TAP_D", inspeccionesComerciales.getTip_mat_tap_d())
						.addValue("V_TIP_MAT_CAJ_D", inspeccionesComerciales.getTip_mat_caj_d())
						.addValue("V_TIP_MAT_TUB_D", inspeccionesComerciales.getTip_mat_tub_d())
						.addValue("V_SIS_TRAT_RES", inspeccionesComerciales.getSistrat())
						.addValue("V_TIP_DES_DES", inspeccionesComerciales.getTip_des_des())
						.addValue("V_CLANDES_DES", inspeccionesComerciales.getClandes_des())
						.addValue("V_COD_SUP", inspeccionesComerciales.getCod_sup())
						.addValue("V_COD_DIGIT", inspeccionesComerciales.getCod_digit())
						.addValue("V_A_V_USUARIO", cargaTrabajoRequest.getUsuario());

				Map<String, Object> ejecucionOracle = this.jdbcCall.execute(mapSqlParameterSource);
				this.error = Constantes.validarRespuestaOracle(ejecucionOracle);
			}
			catch (Exception exception){
				this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
				logger.error("[AGC: CargaTrabajoDAOImpl - registrarTramaInspeccionesComerciales()] - "+exception.getMessage());
			}
		}
		return errors;
	}

	@Override
	public List<Error> registrarTramaTomaEstados(TomaEstados tomaEstados, CargaTrabajoRequest cargaTrabajoRequest) {
		this.error = null;
		List<Error> errors = new ArrayList<Error>();
		errors = validateTypesActivity(tomaEstados, cargaTrabajoRequest);
		if (errors.isEmpty()) {
			try {
				this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
						.withProcedureName(DbConstants.PRC_REGI_TOM_EST_LINE)
						.declareParameters(
								new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
								new SqlParameter("V_N_IDREG", OracleTypes.VARCHAR),
								new SqlParameter("V_NUM_APA_OBS", OracleTypes.VARCHAR),
								new SqlParameter("V_OBS", OracleTypes.VARCHAR),
								new SqlParameter("V_LECT", OracleTypes.VARCHAR),
								new SqlParameter("V_INC_1", OracleTypes.VARCHAR),
								new SqlParameter("V_DET_1", OracleTypes.VARCHAR),
								new SqlParameter("V_INC_2", OracleTypes.VARCHAR),
								new SqlParameter("V_DET_2", OracleTypes.VARCHAR),
								new SqlParameter("V_INC_3", OracleTypes.VARCHAR),
								new SqlParameter("V_DET_3", OracleTypes.VARCHAR),
								new SqlParameter("V_FECHA", OracleTypes.VARCHAR),
								new SqlParameter("V_HORA", OracleTypes.VARCHAR),
								new SqlParameter("V_COD_LECTOR", OracleTypes.VARCHAR),
								new SqlParameter("V_MEDIO", OracleTypes.VARCHAR),
								new SqlParameter("V_NUM_ACT_INC", OracleTypes.VARCHAR),
								new SqlParameter("V_A_V_USUARIO", OracleTypes.VARCHAR),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", tomaEstados.getCodigoCarga())
						.addValue("V_N_IDREG", tomaEstados.getCodigoRegistro())
						.addValue("V_NUM_APA_OBS", tomaEstados.getNumeroApaObs())
						.addValue("V_OBS", tomaEstados.getOpcional())
						.addValue("V_LECT", tomaEstados.getLecturaMedidor())
						.addValue("V_INC_1", tomaEstados.getPrimerIncidente())
						.addValue("V_DET_1", tomaEstados.getDetallePrimerIncidente())
						.addValue("V_INC_2", tomaEstados.getSegundoIncidente())
						.addValue("V_DET_2", tomaEstados.getDetalleSegundoIncidente())
						.addValue("V_INC_3", tomaEstados.getTercerIncidente())
						.addValue("V_DET_3", tomaEstados.getDetalleTercerIncidente())
						.addValue("V_FECHA", tomaEstados.getFechaLectura())
						.addValue("V_HORA", tomaEstados.getHoraLectura())
						.addValue("V_COD_LECTOR", tomaEstados.getCodigoTomadorEstado())
						.addValue("V_MEDIO", tomaEstados.getMedio())
						.addValue("V_NUM_ACT_INC", tomaEstados.getNumeroActaIncidencia())
						.addValue("V_A_V_USUARIO", cargaTrabajoRequest.getUsuario());

				Map<String, Object> ejecucionOracle = this.jdbcCall.execute(mapSqlParameterSource);
				this.error = Constantes.validarRespuestaOracle(ejecucionOracle);
			}
			catch (Exception exception){
				this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
				logger.error("[AGC: CargaTrabajoDAOImpl - registrarTramaTomaEstados()] - "+exception.getMessage());
			}
		}
		return errors;
	}

	@Override
	public List<Error> registrarTramaMedidores(Medidores medidores, CargaTrabajoRequest cargaTrabajoRequest) {
		this.error = null;
		List<Error> errors = new ArrayList<Error>();
		errors = validateTypesActivity(medidores, cargaTrabajoRequest);
		if (errors.isEmpty()) {
			try {
				this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
						.withProcedureName(DbConstants.PRC_REGI_MED_LINEA)
						.declareParameters(
								new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
								new SqlParameter("V_N_IDREG", OracleTypes.FLOAT),
								new SqlParameter("V_DBESTADO", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNRECEP", OracleTypes.INTEGER),
								new SqlParameter("V_DBNUMORD", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNUMCOM", OracleTypes.VARCHAR),
								new SqlParameter("V_DBFEJEC", OracleTypes.DATE),
								new SqlParameter("V_DBDIST", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNIS", OracleTypes.VARCHAR),
								new SqlParameter("V_DBACT01", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN01", OracleTypes.FLOAT), //Float
								new SqlParameter("V_DBACT02", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN02", OracleTypes.FLOAT), //Float
								new SqlParameter("V_DBACT03", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN03", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT04", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN04", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT05", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN05", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT06", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN06", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT07", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN07", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT08", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN08", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT09", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN09", OracleTypes.NUMBER),
								new SqlParameter("V_DBACT10", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCAN10", OracleTypes.NUMBER),
								new SqlParameter("V_DBLARGO", OracleTypes.FLOAT), //FLoat
								new SqlParameter("V_DBANCHO", OracleTypes.FLOAT), //Float
								new SqlParameter("V_DBESTTAPA", OracleTypes.VARCHAR),
								new SqlParameter("V_DBMARTAP", OracleTypes.VARCHAR),
								new SqlParameter("V_DBVALBRON", OracleTypes.NUMBER),
								new SqlParameter("V_DBVALPVC", OracleTypes.NUMBER),
								new SqlParameter("V_DBTUBPLOM", OracleTypes.FLOAT), //Float
								new SqlParameter("V_DBTIPOACT", OracleTypes.VARCHAR),
								new SqlParameter("V_DBMOTLVTO", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNUMRET", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTRET", OracleTypes.NUMBER),
								new SqlParameter("V_DBTIPRET", OracleTypes.VARCHAR),
								new SqlParameter("V_DBDIAM", OracleTypes.VARCHAR),
								new SqlParameter("V_DBUBIC", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCNXFRA", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCNXFOTRO", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTIPLECT", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNUMINST", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTINST", OracleTypes.NUMBER),
								new SqlParameter("V_DBVERMINST", OracleTypes.VARCHAR),
								new SqlParameter("V_DBDISPSEG", OracleTypes.VARCHAR),
								new SqlParameter("V_DBABONADO", OracleTypes.VARCHAR),
								new SqlParameter("V_DBVALPMED", OracleTypes.NUMBER),
								new SqlParameter("V_DBMATVPM", OracleTypes.VARCHAR),
								new SqlParameter("V_DBVALTELE", OracleTypes.NUMBER),
								new SqlParameter("V_DBMATVTEL", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCOD_OPE", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCOD_TSU", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCOD_SUP", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTUBERIA", OracleTypes.VARCHAR),
								new SqlParameter("V_DBLONG", OracleTypes.FLOAT), //Float
								new SqlParameter("V_DBMATRED", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTIPCNX", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTIPPAV", OracleTypes.VARCHAR),
								new SqlParameter("V_DBFUGAS", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTELFCLI", OracleTypes.VARCHAR),
								new SqlParameter("V_DBSUPERV", OracleTypes.VARCHAR),
								new SqlParameter("V_DBCODOBS", OracleTypes.VARCHAR),
								new SqlParameter("V_DBOBSERV", OracleTypes.VARCHAR),
								new SqlParameter("V_DBDSEGMED", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTUER_VAL", OracleTypes.NUMBER),
								new SqlParameter("V_DBMAT01", OracleTypes.FLOAT), /* Float */
								new SqlParameter("V_DBMAT02", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT03", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT04", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT05", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT06", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT07", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT08", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT09", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT10", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT11", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT12", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT13", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT14", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT15", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT16", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT17", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT18", OracleTypes.FLOAT), /* Float */
								new SqlParameter("V_DBMAT18_D", OracleTypes.VARCHAR),
								new SqlParameter("V_DBMAT19", OracleTypes.FLOAT), /* Float */
								new SqlParameter("V_DBMAT20", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT21", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT22", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT23", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT24", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT25", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT26", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT27", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT28", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT29", OracleTypes.FLOAT),
								new SqlParameter("V_DBMAT30", OracleTypes.FLOAT), /* Float */
								new SqlParameter("V_DBESTCAJA", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTVTEL", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTVPM", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTABRA", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTMED", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTTUBF", OracleTypes.VARCHAR),
								new SqlParameter("V_DBSELLRAT", OracleTypes.VARCHAR),
								new SqlParameter("V_DBESTSOLAD", OracleTypes.VARCHAR),
								new SqlParameter("V_DBTFRET", OracleTypes.NUMBER),
								new SqlParameter("V_DBNUM_DISP", OracleTypes.VARCHAR),
								new SqlParameter("V_DBNUM_TELF", OracleTypes.VARCHAR),
								new SqlParameter("V_DBMOTIACT", OracleTypes.VARCHAR),
								new SqlParameter("V_DBEJEOPER", OracleTypes.VARCHAR),
								new SqlParameter("V_DBEJETECN", OracleTypes.VARCHAR),
								new SqlParameter("V_DBINSPEC", OracleTypes.VARCHAR),
								new SqlParameter("V_DBOFICINA", OracleTypes.VARCHAR),
								new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
						.addValue("V_V_IDCARGA", medidores.getCodigoCarga())
						.addValue("V_N_IDREG", medidores.getCodigoRegistro())
						.addValue("V_DBESTADO", medidores.getEstadoOT())
						.addValue("V_DBNRECEP", medidores.getNroRecepcion())
						.addValue("V_DBNUMORD", medidores.getNroOT())
						.addValue("V_DBNUMCOM", medidores.getNroCompromiso())
						.addValue("V_DBFEJEC", java.sql.Date.valueOf(medidores.getFechaEjecucion()))
						.addValue("V_DBDIST", medidores.getCodigoDistrito())
						.addValue("V_DBNIS", medidores.getNroSuministroCliente())
						.addValue("V_DBACT01", medidores.getCodigoActividadEjecutada1())
						.addValue("V_DBCAN01", medidores.getCantidadEjecutadaActividad1())
						.addValue("V_DBACT02", medidores.getCodigoActividadEjecutada2())
						.addValue("V_DBCAN02", medidores.getCantidadEjecutadaActividad2())
						.addValue("V_DBACT03", medidores.getCodigoActividadEjecutada3())
						.addValue("V_DBCAN03", medidores.getCantidadEjecutadaActividad3())
						.addValue("V_DBACT04", medidores.getCodigoActividadEjecutada4())
						.addValue("V_DBCAN04", medidores.getCantidadEjecutadaActividad4())
						.addValue("V_DBACT05", medidores.getCodigoActividadEjecutada5())
						.addValue("V_DBCAN05", medidores.getCantidadEjecutadaActividad5())
						.addValue("V_DBACT06", medidores.getCodigoActividadEjecutada6())
						.addValue("V_DBCAN06", medidores.getCantidadEjecutadaActividad6())
						.addValue("V_DBACT07", medidores.getCodigoActividadEjecutada7())
						.addValue("V_DBCAN07", medidores.getCantidadEjecutadaActividad7())
						.addValue("V_DBACT08", medidores.getCodigoActividadEjecutada8())
						.addValue("V_DBCAN08", medidores.getCantidadEjecutadaActividad8())
						.addValue("V_DBACT09", medidores.getCodigoActividadEjecutada9())
						.addValue("V_DBCAN09", medidores.getCantidadEjecutadaActividad9())
						.addValue("V_DBACT10", medidores.getCodigoActividadEjecutada10())
						.addValue("V_DBCAN10", medidores.getCantidadEjecutadaActividad10())
						.addValue("V_DBLARGO", medidores.getLargoActiComplEjec())
						.addValue("V_DBANCHO", medidores.getAnchoActividadComplementariaEjecutada())
						.addValue("V_DBESTTAPA", medidores.getEstadoTapa())
						.addValue("V_DBMARTAP", medidores.getMarTap())
						.addValue("V_DBVALBRON", medidores.getValvulasBronce())
						.addValue("V_DBVALPVC", medidores.getValPVC())
						.addValue("V_DBTUBPLOM", medidores.getTuberiaPlomo())
						.addValue("V_DBTIPOACT", medidores.getTipoActividadAMM())
						.addValue("V_DBMOTLVTO", medidores.getMotivoLevantamiento())
						.addValue("V_DBNUMRET", medidores.getNroMedidorRetirado())
						.addValue("V_DBESTRET", medidores.getLectura())
						.addValue("V_DBTIPRET", medidores.getTipoMatePredomina())
						.addValue("V_DBDIAM", medidores.getDbDiametro())
						.addValue("V_DBUBIC", medidores.getUbicacionCajaControl())
						.addValue("V_DBCNXFRA", medidores.getEvaluacionConexionFraudulenta())
						.addValue("V_DBCNXFOTRO", medidores.getOtroTipoConexFraudulenta())
						.addValue("V_DBTIPLECT", medidores.getTipoLecturaMedidorInstalado())
						.addValue("V_DBNUMINST", medidores.getNroMedidorInstalado())
						.addValue("V_DBESTINST", medidores.getLecturaM3())
						.addValue("V_DBVERMINST", medidores.getVerificaMedidorInstalado())
						.addValue("V_DBDISPSEG", medidores.getNroDispositivoSeguridad())
						.addValue("V_DBABONADO", medidores.getNroAbonado())
						.addValue("V_DBVALPMED", medidores.getValvulaPuntoMedicion())
						.addValue("V_DBMATVPM", medidores.getMaterialValvulaPuntoMedicion())
						.addValue("V_DBVALTELE", medidores.getValvulaTelescopia())
						.addValue("V_DBMATVTEL", medidores.getMaterialValvulaTelescopia())
						.addValue("V_DBCOD_OPE", medidores.getDniOperarioOT())
						.addValue("V_DBCOD_TSU", medidores.getDniSupervisorOT())
						.addValue("V_DBCOD_SUP", medidores.getDniSupervisorAsignadoSedapal())
						.addValue("V_DBTUBERIA", medidores.getMaterialTuberiaConexionDomicilia())
						.addValue("V_DBLONG", medidores.getLongitudTubeMatriz())
						.addValue("V_DBMATRED", medidores.getMaterialRed())
						.addValue("V_DBTIPCNX", medidores.getTipoConexion())
						.addValue("V_DBTIPPAV", medidores.getTipoPavimento())
						.addValue("V_DBFUGAS", medidores.getSupervisadaOT())
						.addValue("V_DBTELFCLI", medidores.getFugasInstainternas())
						.addValue("V_DBSUPERV", medidores.getNroTelefonoCliente())
						.addValue("V_DBCODOBS", medidores.getCodigoObservacion())
						.addValue("V_DBOBSERV", medidores.getObsCampo())
						.addValue("V_DBDSEGMED", medidores.getDispoSeguridadMedidor())
						.addValue("V_DBTUER_VAL", medidores.getTuercasValvula())
						.addValue("V_DBMAT01", medidores.getCantAdaptadorPresion())
						.addValue("V_DBMAT02", medidores.getCantidadCodos())
						.addValue("V_DBMAT03", medidores.getCantidadCurvas3())
						.addValue("V_DBMAT04", medidores.getCantidadNiplesRemplazo())
						.addValue("V_DBMAT05", medidores.getCantidadNipleSTD())
						.addValue("V_DBMAT06", medidores.getCantidadPresintosSeguridad())
						.addValue("V_DBMAT07", medidores.getCantidadTransicion())
						.addValue("V_DBMAT08", medidores.getCantidadTuberia())
						.addValue("V_DBMAT09", medidores.getCantidadPolietileno())
						.addValue("V_DBMAT10", medidores.getCantidadValvulaSimple())
						.addValue("V_DBMAT11", medidores.getCantidadFiltroMedidor())
						.addValue("V_DBMAT12", medidores.getCantidadDispositivo())
						.addValue("V_DBMAT13", medidores.getTermoplasticoMT())
						.addValue("V_DBMAT14", medidores.getValvulaTelescopicaPVC())
						.addValue("V_DBMAT15", medidores.getValvulaTelescopicaPmed())
						.addValue("V_DBMAT16", medidores.getDbMat16())
						.addValue("V_DBMAT17", medidores.getDbMat17())
						.addValue("V_DBMAT18", medidores.getDbMat18())
						.addValue("V_DBMAT18_D", medidores.getDbMat18D())
						.addValue("V_DBMAT19", medidores.getDbMat19())
						.addValue("V_DBMAT20", medidores.getDbMat20())
						.addValue("V_DBMAT21", medidores.getDbMat21())
						.addValue("V_DBMAT22", medidores.getDbMat22())
						.addValue("V_DBMAT23", medidores.getDbMat23())
						.addValue("V_DBMAT24", medidores.getDbMat24())
						.addValue("V_DBMAT25", medidores.getDbMat25())
						.addValue("V_DBMAT26", medidores.getDbMat26())
						.addValue("V_DBMAT27", medidores.getDbMat27())
						.addValue("V_DBMAT28", medidores.getDbMat28())
						.addValue("V_DBMAT29", medidores.getDbMat29())
						.addValue("V_DBMAT30", medidores.getDbMat30())
						.addValue("V_DBESTCAJA", medidores.getEstadoCaja())
						.addValue("V_DBESTVTEL", medidores.getEstadoValTeles())
						.addValue("V_DBESTVPM", medidores.getEstadoValPunMedicion())
						.addValue("V_DBESTABRA", medidores.getEstadoValAbrazadera())
						.addValue("V_DBESTMED", medidores.getEstadoMedidor())
						.addValue("V_DBESTTUBF", medidores.getEstadoTubForro())
						.addValue("V_DBSELLRAT", medidores.getSelladoraRatonera())
						.addValue("V_DBESTSOLAD", medidores.getEstadoSoldado())
						.addValue("V_DBTFRET", medidores.getTuboForroRetirado())
						.addValue("V_DBNUM_DISP", medidores.getDbNroDisp())
						.addValue("V_DBNUM_TELF", medidores.getDbNroTelf())
						.addValue("V_DBMOTIACT", medidores.getMotivoAct())
						.addValue("V_DBEJEOPER", medidores.getEjecOperario())
						.addValue("V_DBEJETECN", medidores.getEjecTecnico())
						.addValue("V_DBINSPEC", medidores.getInspec())
						.addValue("V_DBOFICINA", medidores.getCodigoOficina())
						.addValue("V_A_V_USUMOD", cargaTrabajoRequest.getUsuario());

				Map<String, Object> ejecucionOracle = this.jdbcCall.execute(mapSqlParameterSource);
				this.error = Constantes.validarRespuestaOracle(ejecucionOracle);
			}
			catch (Exception exception){
				this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), exception.getCause().toString());
				logger.error("[AGC: CargaTrabajoDAOImpl - registrarTramaMedidores()] - "+exception.getMessage());
			}
		}
		return errors;
	}

	private List<Error> validateTypesActivity(@Valid Object object, CargaTrabajoRequest cargaTrabajoRequest){
		List<Error> errors = new ArrayList<Error>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		switch (cargaTrabajoRequest.getUidActividad().toUpperCase()){
			case Constantes.ACT_DIST_AVISO_COBRANZA:		
				Set<ConstraintViolation<DistribucionAvisoCobranza>> violationsDistribucionAvisoCobranza = validator.validate((DistribucionAvisoCobranza) object);
				if (!violationsDistribucionAvisoCobranza.isEmpty()) {
					for (ConstraintViolation<DistribucionAvisoCobranza> violation: violationsDistribucionAvisoCobranza) {
						String[] parameters = violation.getMessageTemplate().split("-");
						errors.add(getError(parameters));
					}
				}
				break;
			case Constantes.ACT_DIST_COMUNIC:		
				Set<ConstraintViolation<DistribucionComunicaciones>> violationsDistribucionComunicacion = validator.validate((DistribucionComunicaciones) object);
				if (!violationsDistribucionComunicacion.isEmpty()) {
					for (ConstraintViolation<DistribucionComunicaciones> violation: violationsDistribucionComunicacion) {
						String[] parameters = violation.getMessageTemplate().split("-");
						errors.add(getError(parameters));
					}
				}
				break;
			case Constantes.ACT_INSP_COMERCIAL:		
				Set<ConstraintViolation<InspeccionesComerciales>> violationsInspeccionesComerciales = validator.validate((InspeccionesComerciales) object);
				if (!violationsInspeccionesComerciales.isEmpty()) {
					for (ConstraintViolation<InspeccionesComerciales> violation: violationsInspeccionesComerciales) {
						String[] parameters = violation.getMessageTemplate().split("-");
						errors.add(getError(parameters));
					}
				}
				break;
			case Constantes.ACT_MEDIDORES:		
				Set<ConstraintViolation<Medidores>> violationsMedidores = validator.validate((Medidores) object);
				if (!violationsMedidores.isEmpty()) {
					for (ConstraintViolation<Medidores> violation: violationsMedidores) {
						String[] parameters = violation.getMessageTemplate().split("-");
						errors.add(getError(parameters));
					}
				}
				break;
			case Constantes.ACT_TOMA_ESTADO:		
				Set<ConstraintViolation<TomaEstados>> violationsTomaEstados = validator.validate((TomaEstados) object);
				if (!violationsTomaEstados.isEmpty()) {
					for (ConstraintViolation<TomaEstados> violation: violationsTomaEstados) {
						String[] parameters = violation.getMessageTemplate().split("-");
						errors.add(getError(parameters));
					}
				}
				break;
			default:
				errors.add(new Error(9999, "El código del tipo de actividad es incorrecta"));
				break;
		}
		return  errors;
	}
	
	@Autowired
	private IDistribucionComunicacionesDAO daoDC;
	@Autowired
	private IDistribucionAvisoCobbranzaDAO daoDA;
	@Autowired
	private IInspeccionesComercialesDAO daoIC;
	@Autowired
	private IMedidoresDAO daoME;
	@Autowired
	private ITomaEstadosDAO daoTomEst;
	
	@Override
	public File generarTrama(String uidCarga, String uidActividad, String idPerfil, int filtro, String usuario) {	
		this.error = null;
		PageRequest request = new PageRequest();
		request.setPagina(1);
		request.setRegistros(Constantes.MAX_REGISTROS_DETALLE_CARGA_TRABAJO);
		
		File archivoTrama = null;		
		StringBuilder trama = new StringBuilder();								
	
		
		switch(uidActividad) {
		  case Constantes.ACT_DIST_AVISO_COBRANZA:
			  List<DistribucionAvisoCobranza> listaDisAvi = new ArrayList<>();
			  listaDisAvi = daoDA.ListarDetalleAvisoCobranzaTrama(request, uidCarga, filtro, usuario);
			  
			  if(!listaDisAvi.isEmpty()) {
				  if(idPerfil.equals(Constantes.PERFIL_ANALISTA_EXTERNO) || idPerfil.equals(Constantes.PERFIL_SUPERVISOR_EXTERNO)) {					  
					  trama.append(CabDistribucionAvisoCobranza.V_IDCARGA+"\t")    
							  .append(CabDistribucionAvisoCobranza.N_IDREG+"\t")      
							  .append(CabDistribucionAvisoCobranza.COD_UNICOM+"\t")   
							  .append(CabDistribucionAvisoCobranza.SEC_TRABAJO+"\t")  
							  .append(CabDistribucionAvisoCobranza.RUTA+"\t")         
							  .append(CabDistribucionAvisoCobranza.NUM_ITIN+"\t")     
							  .append(CabDistribucionAvisoCobranza.NIS_RAD+"\t")      
							  .append(CabDistribucionAvisoCobranza.NUM_SECUENCIA+"\t")
							  .append(CabDistribucionAvisoCobranza.NUM_APA+"\t")      
							  .append(CabDistribucionAvisoCobranza.NOM_CALLE+"\t")    
							  .append(CabDistribucionAvisoCobranza.NUM_PUERTA+"\t")   
							  .append(CabDistribucionAvisoCobranza.DUPLICADOR+"\t")   
							  .append(CabDistribucionAvisoCobranza.CGV+"\t")          
							  .append(CabDistribucionAvisoCobranza.AOL+"\t")          
							  .append(CabDistribucionAvisoCobranza.CUSP+"\t")         
							  .append(CabDistribucionAvisoCobranza.DISTRITO+"\t")     
							  .append(CabDistribucionAvisoCobranza.LOCALIDAD+"\t")    
							  .append(CabDistribucionAvisoCobranza.REF_DIR+"\t")      
							  .append(CabDistribucionAvisoCobranza.CASO+"\t")         
							  .append(CabDistribucionAvisoCobranza.CICLO+"\t")        
							  .append(CabDistribucionAvisoCobranza.COD_PERSONA+"\t")  
							  .append(CabDistribucionAvisoCobranza.COD_IMP+"\t")      
							  .append(CabDistribucionAvisoCobranza.F_DISTRIB+"\t")    
							  .append(CabDistribucionAvisoCobranza.HORA_DISTRIB+"\t") 
							  .append(CabDistribucionAvisoCobranza.TIP_ENT+"\r\n");					  
					  for(DistribucionAvisoCobranza item : listaDisAvi) {						  
						  trama.append(item.getCodigoCarga()+"\t")
						  		.append(item.getCodigoRegistro()+"\t")
						  		.append(item.getOficinaComercial()+"\t")
						  		.append(item.getSecuenciaTrabajo()+"\t")
						  		.append(item.getRutaEstablecida()+"\t")
						  		.append(item.getCampItinerario()+"\t")
						  		.append(item.getCampSuministro()+"\t")
						  		.append(item.getSecuenciaRecibo()+"\t")
						  		.append(item.getNroMedidor()+"\t")
						  		.append(item.getCalle()+"\t")
						  		.append(item.getNroPuerta()+"\t")
						  		.append(item.getDuplicador()+"\t")
						  		.append(item.getCgv()+"\t")
						  		.append(item.getAol()+"\t")
						  		.append(item.getCusp()+"\t")
						  		.append(item.getRepresentaMunicipio()+"\t")
						  		.append(item.getRepresentaLocalidad()+"\t")
						  		.append(item.getReferenciaDireccion()+"\t")
						  		.append(item.getIndicadorFueraRuta()+"\t")
						  		.append(item.getCiclo()+"\t")
						  		.append(item.getCodigoDistribuidor()+"\t")
						  		.append(item.getImposibilidad()+"\t")
						  		.append((item.getFechaDistribucion() == null ? ' ': item.getFechaDistribucion())+"\t")
						  		.append(item.getHoraDistribucion()+"\t")
						  		.append(item.getTipoEntrada()+"\r\n");
					  }
				  }else {
//					  trama.append(CabDistribucionAvisoCobranza.COD_UNICOM+"\t")   
//						  .append(CabDistribucionAvisoCobranza.SEC_TRABAJO+"\t") 
//						  .append(CabDistribucionAvisoCobranza.RUTA+"\t")         
//						  .append(CabDistribucionAvisoCobranza.NUM_ITIN+"\t")     
//						  .append(CabDistribucionAvisoCobranza.NIS_RAD+"\t")      
//						  .append(CabDistribucionAvisoCobranza.NUM_SECUENCIA+"\t")
//						  .append(CabDistribucionAvisoCobranza.NUM_APA+"\t")      
//						  .append(CabDistribucionAvisoCobranza.NOM_CALLE+"\t")    
//						  .append(CabDistribucionAvisoCobranza.NUM_PUERTA+"\t")   
//						  .append(CabDistribucionAvisoCobranza.DUPLICADOR+"\t")   
//						  .append(CabDistribucionAvisoCobranza.CGV+"\t")          
//						  .append(CabDistribucionAvisoCobranza.AOL+"\t")          
//						  .append(CabDistribucionAvisoCobranza.CUSP+"\t")         
//						  .append(CabDistribucionAvisoCobranza.DISTRITO+"\t")     
//						  .append(CabDistribucionAvisoCobranza.LOCALIDAD+"\t")    
//						  .append(CabDistribucionAvisoCobranza.REF_DIR+"\t")      
//						  .append(CabDistribucionAvisoCobranza.CASO+"\t")         
//						  .append(CabDistribucionAvisoCobranza.CICLO+"\t")        
//						  .append(CabDistribucionAvisoCobranza.COD_PERSONA+"\t")  
//						  .append(CabDistribucionAvisoCobranza.COD_IMP+"\t")      
//						  .append(CabDistribucionAvisoCobranza.F_DISTRIB+"\t")    
//						  .append(CabDistribucionAvisoCobranza.HORA_DISTRIB+"\t") 
//						  .append(CabDistribucionAvisoCobranza.TIP_ENT+"\r\n");					  
					  for(DistribucionAvisoCobranza item : listaDisAvi) {						  
						  	trama.append(item.getOficinaComercial()+"\t")
						  		.append(item.getSecuenciaTrabajo()+"\t")
						  		.append(item.getRutaEstablecida()+"\t")
						  		.append(item.getCampItinerario()+"\t")
						  		.append(item.getCampSuministro()+"\t")
						  		.append(item.getSecuenciaRecibo()+"\t")
						  		.append(item.getNroMedidor()+"\t")
						  		.append(item.getCalle()+"\t")
						  		.append(item.getNroPuerta()+"\t")
						  		.append(item.getDuplicador()+"\t")
						  		.append(item.getCgv()+"\t")
						  		.append(item.getAol()+"\t")
						  		.append(item.getCusp()+"\t")
						  		.append(item.getRepresentaMunicipio()+"\t")
						  		.append(item.getRepresentaLocalidad()+"\t")
						  		.append(item.getReferenciaDireccion()+"\t")
						  		.append(item.getIndicadorFueraRuta()+"\t")
						  		.append(item.getCiclo()+"\t")
						  		.append(item.getCodigoDistribuidor()+"\t")
						  		.append(item.getImposibilidad()+"\t")
						  		.append((item.getFechaDistribucion() == null ? ' ': item.getFechaDistribucion())+"\t")
						  		.append(item.getHoraDistribucion()+"\t")
						  		.append(item.getTipoEntrada()+"\r\n");
					  }
				  }				  				  				  
			  }else {
				  this.error = new Error(0,"Error","No se obtuvo detalle de la carga de trabajo.");
			  }
			  
		    break;
		  case Constantes.ACT_DIST_COMUNIC:
			  List<DistribucionComunicaciones> listaDisCom = new ArrayList<>();
			  listaDisCom = daoDC.ListarDetalleDisComTrama(request, uidCarga, filtro, usuario);
			  SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			  
			  if(!listaDisCom.isEmpty()){
				  if(idPerfil.equals(Constantes.PERFIL_ANALISTA_EXTERNO) || idPerfil.equals(Constantes.PERFIL_SUPERVISOR_EXTERNO)) {
					  trama.append(CabDistribucionComunicaciones.V_IDCARGA+"\t")     
					  .append(CabDistribucionComunicaciones.N_IDREG+"\t")          
					  .append(CabDistribucionComunicaciones.OC+"\t")               
					  .append(CabDistribucionComunicaciones.CARGA+"\t")            
					  .append(CabDistribucionComunicaciones.FENVIO+"\t")           
					  .append(CabDistribucionComunicaciones.NIS+"\t")              
					  .append(CabDistribucionComunicaciones.NCEDULA+"\t")          
					  .append(CabDistribucionComunicaciones.FEMISION+"\t")         
					  .append(CabDistribucionComunicaciones.AP1+"\t")              
					  .append(CabDistribucionComunicaciones.AP2+"\t")              
					  .append(CabDistribucionComunicaciones.NOM+"\t")              
					  .append(CabDistribucionComunicaciones.TIPDNI+"\t")           
					  .append(CabDistribucionComunicaciones.DNI+"\t")              
					  .append(CabDistribucionComunicaciones.RSOCIAL+"\t")          
					  .append(CabDistribucionComunicaciones.CALLE+"\t")            
					  .append(CabDistribucionComunicaciones.NRO+"\t")              
					  .append(CabDistribucionComunicaciones.CGV_CLI+"\t")          
					  .append(CabDistribucionComunicaciones.MZA+"\t")              
					  .append(CabDistribucionComunicaciones.LOTE+"\t")             
					  .append(CabDistribucionComunicaciones.URB+"\t")              
					  .append(CabDistribucionComunicaciones.PROV+"\t")             
					  .append(CabDistribucionComunicaciones.DISTRITO+"\t")         
					  .append(CabDistribucionComunicaciones.NRECLAMO+"\t")         
					  .append(CabDistribucionComunicaciones.TRECLAMO+"\t")         
					  .append(CabDistribucionComunicaciones.TDOC+"\t")             
					  .append(CabDistribucionComunicaciones.NRODOC+"\t")           
					  .append(CabDistribucionComunicaciones.NVISITA+"\t")          
					  .append(CabDistribucionComunicaciones.FNOTIF1+"\t")          
					  .append(CabDistribucionComunicaciones.HNOTIF1+"\t")          
					  .append(CabDistribucionComunicaciones.FNOTIF2+"\t")          
					  .append(CabDistribucionComunicaciones.HNOTIF2+"\t")          
					  .append(CabDistribucionComunicaciones.NOMNOTIF+"\t")         
					  .append(CabDistribucionComunicaciones.DNINOTIF+"\t")         
					  .append(CabDistribucionComunicaciones.PARENTESCO_N+"\t")     
					  .append(CabDistribucionComunicaciones.OBS+"\t")              
					  .append(CabDistribucionComunicaciones.OBS2+"\t")             
					  .append(CabDistribucionComunicaciones.COD_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.NOM_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.DNI_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.COD_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.NOM_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.DNI_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.FNOTIF+"\t")           
					  .append(CabDistribucionComunicaciones.HNOTIF+"\t")           
					  .append(CabDistribucionComunicaciones.TIP_ENTREGA+"\t")      
					  .append(CabDistribucionComunicaciones.DIFICUL+"\t")          
					  .append(CabDistribucionComunicaciones.GF+"\t")               
					  .append(CabDistribucionComunicaciones.TDOCNOT+"\t")          
					  .append(CabDistribucionComunicaciones.CDSD+"\t")            
					  .append(CabDistribucionComunicaciones.USUARIO+"\r\n");					  
					  for(DistribucionComunicaciones item : listaDisCom) {						  
						  trama.append(item.getCodigoCarga()+"\t")
						  	.append(item.getCodigoRegistro()+"\t")
						  	.append(item.getCodigoOficinaComercial()+"\t")
						  	.append(item.getNroIdentCarga()+"\t")
						  	.append(item.getFechaEntraga()+"\t")
						  	.append(item.getNroSuministro()+"\t")
						  	.append(item.getCorrCedulaNotificacion()+"\t")
						  	.append(item.getFechaEmisionDocumento()+"\t")
						  	.append(item.getApellidoPatDestinatario()+"\t")
						  	.append(item.getApellidoMatDestinatario()+"\t")
						  	.append(item.getNombresDestinatario()+"\t")
						  	.append(item.getTipoDocumentoDestinatario()+"\t")
						  	.append(item.getNroDocumento()+"\t")
						  	.append(item.getRazonSocial()+"\t")
						  	.append(item.getCalle()+"\t")
						  	.append(item.getNro()+"\t")
						  	.append(item.getPiso()+"\t")
						  	.append(item.getManzana()+"\t")
						  	.append(item.getLote()+"\t")
						  	.append(item.getUrb()+"\t")
						  	.append(item.getProvincia()+"\t")
						  	.append(item.getDistrito()+"\t")
						  	.append(item.getNroReclamoOpen()+"\t")
						  	.append(item.getTipoReclamo()+"\t")
						  	.append(item.getCodigoTipoDocu()+"\t")
						  	.append(item.getNroDocumentoNotificar()+"\t")
						  	.append(item.getNroVisitasNotificador()+"\t")
						  	.append((item.getFechaVisita1() == null ? ' ' : item.getFechaVisita1())+"\t")
						  	.append(item.getHoraVisita1()+"\t")
						  	.append((item.getFechaVisita2() == null ? ' ' : item.getFechaVisita2())+"\t")
						  	.append(item.getHoraVisita2()+"\t")
						  	.append(item.getNombreNotificada()+"\t")
						  	.append(item.getDniNotificada()+"\t")
						  	.append(item.getParentezcoNotificada()+"\t")
						  	.append(item.getObsVisita1()+"\t")
						  	.append(item.getObsVisita2()+"\t")
						  	.append(item.getCodigoNotificadorVisita1()+"\t")
						  	.append(item.getNombreNotificadorVisita1()+"\t")
						  	.append(item.getDniNotificador1()+"\t")
						  	.append(item.getCodigoNotificadorVisita2()+"\t")
						  	.append(item.getNombreNotificadorVisita2()+"\t")
						  	.append(item.getDniNotificador2()+"\t")
						  	.append((item.getFechaConcretaNotificacion() == null ? ' ': item.getFechaConcretaNotificacion())+"\t")
						  	.append(item.getHoraConNotificacion()+"\t")
						  	.append(item.getTipoEntrega()+"\t")
						  	.append(item.getDificultad()+"\t")
						  	.append(item.getGrupoFuncional()+"\t")
						  	.append(item.getCodInternoDocuRemitido()+"\t")
						  	.append(item.getCdsd()+"\t")
						  	.append(item.getUsuario()+"\r\n");						  							  	
					  }
				  }else {
					  trama.append(CabDistribucionComunicaciones.OC+"\t")               
					  .append(CabDistribucionComunicaciones.CARGA+"\t")            
					  .append(CabDistribucionComunicaciones.FENVIO+"\t")           
					  .append(CabDistribucionComunicaciones.NIS+"\t")              
					  .append(CabDistribucionComunicaciones.NCEDULA+"\t")          
					  .append(CabDistribucionComunicaciones.FEMISION+"\t")         
					  .append(CabDistribucionComunicaciones.AP1+"\t")              
					  .append(CabDistribucionComunicaciones.AP2+"\t")              
					  .append(CabDistribucionComunicaciones.NOM+"\t")              
					  .append(CabDistribucionComunicaciones.TIPDNI+"\t")           
					  .append(CabDistribucionComunicaciones.DNI+"\t")              
					  .append(CabDistribucionComunicaciones.RSOCIAL+"\t")          
					  .append(CabDistribucionComunicaciones.CALLE+"\t")            
					  .append(CabDistribucionComunicaciones.NRO+"\t")              
					  .append(CabDistribucionComunicaciones.CGV_CLI+"\t")          
					  .append(CabDistribucionComunicaciones.MZA+"\t")              
					  .append(CabDistribucionComunicaciones.LOTE+"\t")             
					  .append(CabDistribucionComunicaciones.URB+"\t")              
					  .append(CabDistribucionComunicaciones.PROV+"\t")             
					  .append(CabDistribucionComunicaciones.DISTRITO+"\t")         
					  .append(CabDistribucionComunicaciones.NRECLAMO+"\t")         
					  .append(CabDistribucionComunicaciones.TRECLAMO+"\t")         
					  .append(CabDistribucionComunicaciones.TDOC+"\t")             
					  .append(CabDistribucionComunicaciones.NRODOC+"\t")           
					  .append(CabDistribucionComunicaciones.NVISITA+"\t")          
					  .append(CabDistribucionComunicaciones.FNOTIF1+"\t")          
					  .append(CabDistribucionComunicaciones.HNOTIF1+"\t")          
					  .append(CabDistribucionComunicaciones.FNOTIF2+"\t")          
					  .append(CabDistribucionComunicaciones.HNOTIF2+"\t")          
					  .append(CabDistribucionComunicaciones.NOMNOTIF+"\t")         
					  .append(CabDistribucionComunicaciones.DNINOTIF+"\t")         
					  .append(CabDistribucionComunicaciones.PARENTESCO_N+"\t")     
					  .append(CabDistribucionComunicaciones.OBS+"\t")              
					  .append(CabDistribucionComunicaciones.OBS2+"\t")             
					  .append(CabDistribucionComunicaciones.COD_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.NOM_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.DNI_EMP1+"\t")         
					  .append(CabDistribucionComunicaciones.COD_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.NOM_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.DNI_EMP2+"\t")         
					  .append(CabDistribucionComunicaciones.FNOTIF+"\t")           
					  .append(CabDistribucionComunicaciones.HNOTIF+"\t")           
					  .append(CabDistribucionComunicaciones.TIP_ENTREGA+"\t")      
					  .append(CabDistribucionComunicaciones.DIFICUL+"\t")          
					  .append(CabDistribucionComunicaciones.GF+"\t")               
					  .append(CabDistribucionComunicaciones.TDOCNOT+"\t")          
					  .append(CabDistribucionComunicaciones.CDSD+"\t")            
					  .append(CabDistribucionComunicaciones.USUARIO+"\r\n");					  
					  for(DistribucionComunicaciones item : listaDisCom) {						  
						  trama.append(item.getCodigoOficinaComercial().trim()+"\t")
						  	.append(item.getNroIdentCarga().trim()+"\t")
						  	.append(item.getFechaEntraga().trim()+"\t")
						  	.append(item.getNroSuministro().trim()+"\t")
						  	.append(item.getCorrCedulaNotificacion().trim()+"\t")
						  	.append(item.getFechaEmisionDocumento().trim()+"\t")
						  	.append(item.getApellidoPatDestinatario().trim()+"\t")
						  	.append(item.getApellidoMatDestinatario().trim()+"\t")
						  	.append(item.getNombresDestinatario().trim()+"\t")
						  	.append(item.getTipoDocumentoDestinatario().trim()+"\t")
						  	.append(item.getNroDocumento().trim()+"\t")
						  	.append(item.getRazonSocial().trim()+"\t")
						  	.append(item.getCalle().trim()+"\t")
						  	.append(item.getNro().trim()+"\t")
						  	.append(item.getPiso().trim()+"\t")
						  	.append(item.getManzana().trim()+"\t")
						  	.append(item.getLote().trim()+"\t")
						  	.append(item.getUrb().trim()+"\t")
						  	.append(item.getProvincia().trim()+"\t")
						  	.append(item.getDistrito().trim()+"\t")
						  	.append(item.getNroReclamoOpen().trim()+"\t")
						  	.append(item.getTipoReclamo().trim()+"\t")
						  	.append(item.getCodigoTipoDocu().trim()+"\t")
						  	.append(item.getNroDocumentoNotificar().trim()+"\t")
						  	.append(item.getNroVisitasNotificador().trim()+"\t")
                            .append((item.getFechaVisita1() != null ? (formateador.format(item.getFechaVisita1()).equals("31/12/2999") ? "" : item.getFechaVisita1()): "")+"\t")
						  	.append(item.getHoraVisita1().trim()+"\t")
						  	.append((item.getFechaVisita2() != null ? (formateador.format(item.getFechaVisita2()).equals("31/12/2999") ? "" : item.getFechaVisita2()): "")+"\t")
						  	.append(item.getHoraVisita2().trim()+"\t")
						  	.append(item.getNombreNotificada().trim()+"\t")
						  	.append(item.getDniNotificada().trim()+"\t")
						  	.append(item.getParentezcoNotificada().trim()+"\t")
						  	.append(item.getObsVisita1().trim()+"\t")
						  	.append(item.getObsVisita2().trim()+"\t")
						  	.append(item.getCodigoNotificadorVisita1().trim()+"\t")
						  	.append(item.getNombreNotificadorVisita1().trim()+"\t")
						  	.append(item.getDniNotificador1().trim()+"\t")
						  	.append(item.getCodigoNotificadorVisita2().trim()+"\t")
						  	.append(item.getNombreNotificadorVisita2().trim()+"\t")
						  	.append(item.getDniNotificador2().trim()+"\t")
						  	.append((item.getFechaConcretaNotificacion() != null ? (formateador.format(item.getFechaConcretaNotificacion()).equals("31/12/2999") ? "" : item.getFechaConcretaNotificacion()): "")+"\t")
						  	.append(item.getHoraConNotificacion().trim()+"\t")
						  	.append(item.getTipoEntrega().trim()+"\t")
						  	.append(item.getDificultad().trim()+"\t")
						  	.append(item.getGrupoFuncional().trim()+"\t")
						  	.append(item.getCodInternoDocuRemitido().trim()+"\t")
						  	.append(item.getCdsd().trim()+"\t")
						  	.append(item.getUsuario().trim()+"\r\n");	
				  }
				}
				  
			  }else {
				  this.error = new Error(0,"Error","No se obtuvo detalle de la carga de trabajo.");
			  }
			  			  			  			  			 
		    break;
		  case Constantes.ACT_INSP_COMERCIAL:
			  List<InspeccionesComerciales> listaInsCom = new ArrayList<>();
			  listaInsCom = daoIC.ListarInspeccionesComercialesTrama(request, uidCarga, filtro, usuario);
			  
			  if(!listaInsCom.isEmpty()){				  
				  if(idPerfil.equals(Constantes.PERFIL_ANALISTA_EXTERNO) || idPerfil.equals(Constantes.PERFIL_SUPERVISOR_EXTERNO)) {
					  trama.append(CabInspeccionesComerciales.V_IDCARGA+"\t")
					  .append(CabInspeccionesComerciales.N_IDREG+"\t")
					  .append(CabInspeccionesComerciales.OF_COMER+"\t")
					  .append(CabInspeccionesComerciales.GEN_POR+"\t")
					  .append(CabInspeccionesComerciales.CUS+"\t")
					  .append(CabInspeccionesComerciales.RUTA+"\t")
					  .append(CabInspeccionesComerciales.ITIN+"\t")
					  .append(CabInspeccionesComerciales.TIPSUM+"\t")
					  .append(CabInspeccionesComerciales.FEC_EMIS+"\t")
					  .append(CabInspeccionesComerciales.OBSERVAC+"\t")
					  .append(CabInspeccionesComerciales.NOM_RAZ+"\t")
					  .append(CabInspeccionesComerciales.LE_RUC+"\t")
					  .append(CabInspeccionesComerciales.IAME_FAX+"\t")
					  .append(CabInspeccionesComerciales.COD_ABAS+"\t")
					  .append(CabInspeccionesComerciales.REFER+"\t")
					  .append(CabInspeccionesComerciales.CUA+"\t")
					  .append(CabInspeccionesComerciales.MEDIDOR+"\t")
					  .append(CabInspeccionesComerciales.DIAM+"\t")
					  .append(CabInspeccionesComerciales.ULT_LEC+"\t")
					  .append(CabInspeccionesComerciales.PTO_MED+"\t")
					  .append(CabInspeccionesComerciales.ACOM+"\t")
					  .append(CabInspeccionesComerciales.NIS_RAD+"\t")
					  .append(CabInspeccionesComerciales.NUM_OS+"\t")
					  .append(CabInspeccionesComerciales.TIP_OS+"\t")
					  .append(CabInspeccionesComerciales.FE_RES+"\t")
					  .append(CabInspeccionesComerciales.COD_EMP+"\t")
					  .append(CabInspeccionesComerciales.FEC_VIS+"\t")
					  .append(CabInspeccionesComerciales.CO_ACCEJ+"\t")
					  .append(CabInspeccionesComerciales.SERV+"\t")
					  .append(CabInspeccionesComerciales.SERV_DT+"\t")
					  .append(CabInspeccionesComerciales.CAJA+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA+"\t")
					  .append(CabInspeccionesComerciales.CNX_CON+"\t")
					  .append(CabInspeccionesComerciales.CX_C_DT+"\t")
					  .append(CabInspeccionesComerciales.LEC+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED1+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED2+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED3+"\t")
					  .append(CabInspeccionesComerciales.PRECINT+"\t")
					  .append(CabInspeccionesComerciales.FUGA_CAJ+"\t")
					  .append(CabInspeccionesComerciales.N_CONEX+"\t")
					  .append(CabInspeccionesComerciales.MED2+"\t")
					  .append(CabInspeccionesComerciales.MED3+"\t")
					  .append(CabInspeccionesComerciales.NIPLE2+"\t")
					  .append(CabInspeccionesComerciales.NIPLE3+"\t")
					  .append(CabInspeccionesComerciales.VALVULAS+"\t")
					  .append(CabInspeccionesComerciales.DISPOSIT+"\t")
					  .append(CabInspeccionesComerciales.CLANDES+"\t")
					  .append(CabInspeccionesComerciales.NIV_FINC+"\t")
					  .append(CabInspeccionesComerciales.ESTADO+"\t")
					  .append(CabInspeccionesComerciales.N_HABIT+"\t")
					  .append(CabInspeccionesComerciales.UU_OCUP+"\t")
					  .append(CabInspeccionesComerciales.SOC_OCUP+"\t")
					  .append(CabInspeccionesComerciales.DOM_OCUP+"\t")
					  .append(CabInspeccionesComerciales.COM_OCUP+"\t")
					  .append(CabInspeccionesComerciales.IND_OCUP+"\t")
					  .append(CabInspeccionesComerciales.EST_OCUP+"\t")
					  .append(CabInspeccionesComerciales.UU_DOCUP+"\t")
					  .append(CabInspeccionesComerciales.SOC_DESC+"\t")
					  .append(CabInspeccionesComerciales.DOM_DESC+"\t")
					  .append(CabInspeccionesComerciales.COM_DESC+"\t")
					  .append(CabInspeccionesComerciales.IND_DESC+"\t")
					  .append(CabInspeccionesComerciales.EST_DESC+"\t")
					  .append(CabInspeccionesComerciales.USO_UNIC+"\t")
					  .append(CabInspeccionesComerciales.CALLE+"\t")
					  .append(CabInspeccionesComerciales.NUMERO+"\t")
					  .append(CabInspeccionesComerciales.DUPLICAD+"\t")
					  .append(CabInspeccionesComerciales.MZA+"\t")
					  .append(CabInspeccionesComerciales.LOTE+"\t")
					  .append(CabInspeccionesComerciales.CGV+"\t")
					  .append(CabInspeccionesComerciales.URBANIZA+"\t")
					  .append(CabInspeccionesComerciales.DISTRITO+"\t")
					  .append(CabInspeccionesComerciales.CALLE_C+"\t")
					  .append(CabInspeccionesComerciales.NUM_C+"\t")
					  .append(CabInspeccionesComerciales.DUPLI_C+"\t")
					  .append(CabInspeccionesComerciales.MZA_C+"\t")
					  .append(CabInspeccionesComerciales.LOTE_C+"\t")
					  .append(CabInspeccionesComerciales.URB_C+"\t")
					  .append(CabInspeccionesComerciales.REF_C+"\t")
					  .append(CabInspeccionesComerciales.DIST_C+"\t")
					  .append(CabInspeccionesComerciales.ATENDIO+"\t")
					  .append(CabInspeccionesComerciales.PATERNO+"\t")
					  .append(CabInspeccionesComerciales.MATERNO+"\t")
					  .append(CabInspeccionesComerciales.NOMBRE+"\t")
					  .append(CabInspeccionesComerciales.RAZSOC+"\t")
					  .append(CabInspeccionesComerciales.DNI+"\t")
					  .append(CabInspeccionesComerciales.TELEF+"\t")
					  .append(CabInspeccionesComerciales.FAX+"\t")
					  .append(CabInspeccionesComerciales.RUC+"\t")
					  .append(CabInspeccionesComerciales.INOD_B+"\t")
					  .append(CabInspeccionesComerciales.INOD_F+"\t")
					  .append(CabInspeccionesComerciales.INOD_CL+"\t")
					  .append(CabInspeccionesComerciales.LAVA_B+"\t")
					  .append(CabInspeccionesComerciales.LAVA_F+"\t")
					  .append(CabInspeccionesComerciales.LAVA_CL+"\t")
					  .append(CabInspeccionesComerciales.DUCH_B+"\t")
					  .append(CabInspeccionesComerciales.DUCH_F+"\t")
					  .append(CabInspeccionesComerciales.DUCH_CL+"\t")
					  .append(CabInspeccionesComerciales.URIN_B+"\t")
					  .append(CabInspeccionesComerciales.URIN_F+"\t")
					  .append(CabInspeccionesComerciales.URIN_CL+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_B+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_F+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_CL+"\t")
					  .append(CabInspeccionesComerciales.CISTE_B+"\t")
					  .append(CabInspeccionesComerciales.CISTE_F+"\t")
					  .append(CabInspeccionesComerciales.CISTE_CL+"\t")
					  .append(CabInspeccionesComerciales.TANQU_B+"\t")
					  .append(CabInspeccionesComerciales.TANQU_F+"\t")
					  .append(CabInspeccionesComerciales.TANQU_CL+"\t")
					  .append(CabInspeccionesComerciales.PISC_B+"\t")
					  .append(CabInspeccionesComerciales.PISC_F+"\t")
					  .append(CabInspeccionesComerciales.PISC_CL+"\t")
					  .append(CabInspeccionesComerciales.BIDET_B+"\t")
					  .append(CabInspeccionesComerciales.BIDET_F+"\t")
					  .append(CabInspeccionesComerciales.BIDET_CL+"\t")
					  .append(CabInspeccionesComerciales.ABAST+"\t")
					  .append(CabInspeccionesComerciales.PTOME_CA+"\t")
					  .append(CabInspeccionesComerciales.NUM_P+"\t")
					  .append(CabInspeccionesComerciales.DUP_P+"\t")
					  .append(CabInspeccionesComerciales.MZA_P+"\t")
					  .append(CabInspeccionesComerciales.LOTE_P+"\t")
					  .append(CabInspeccionesComerciales.OBSERV+"\t")
					  .append(CabInspeccionesComerciales.OBSERVM1+"\t")
					  .append(CabInspeccionesComerciales.OBSERVM2+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUM_C+"\t")
					  .append(CabInspeccionesComerciales.PISOS_C+"\t")
					  .append(CabInspeccionesComerciales.COD_UBIC_C+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEXION_C+"\t")
					  .append(CabInspeccionesComerciales.CUP_C+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUM+"\t")
					  .append(CabInspeccionesComerciales.PISOS+"\t")
					  .append(CabInspeccionesComerciales.COD_UBIC+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEXION+"\t")
					  .append(CabInspeccionesComerciales.CUP+"\t")
					  .append(CabInspeccionesComerciales.DEVUELTO+"\t")
					  .append(CabInspeccionesComerciales.FECHA_D+"\t")
					  .append(CabInspeccionesComerciales.PRIOR_1+"\t")
					  .append(CabInspeccionesComerciales.ACTUALIZA+"\t")
					  .append(CabInspeccionesComerciales.FECHA_C+"\t")
					  .append(CabInspeccionesComerciales.RESUELTO+"\t")
					  .append(CabInspeccionesComerciales.MULTI+"\t")
					  .append(CabInspeccionesComerciales.PARCIAL+"\t")
					  .append(CabInspeccionesComerciales.FTE_CNX+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUMC+"\t")
					  .append(CabInspeccionesComerciales.PISOSC+"\t")
					  .append(CabInspeccionesComerciales.COD_UBICC+"\t")
					  .append(CabInspeccionesComerciales.FTE_CNXC+"\t")
					  .append(CabInspeccionesComerciales.CUPC+"\t")
					  .append(CabInspeccionesComerciales.HORA_I+"\t")
					  .append(CabInspeccionesComerciales.HORA_F+"\t")
					  .append(CabInspeccionesComerciales.VALV_E_TLSC+"\t")
					  .append(CabInspeccionesComerciales.MAT_VALV_E+"\t")
					  .append(CabInspeccionesComerciales.VALV_S_P+"\t")
					  .append(CabInspeccionesComerciales.MAT_VALV_S+"\t")
					  .append(CabInspeccionesComerciales.SECTOR+"\t")
					  .append(CabInspeccionesComerciales.CUP_PRED+"\t")
					  .append(CabInspeccionesComerciales.AOL+"\t")
					  .append(CabInspeccionesComerciales.CICLO+"\t")
					  .append(CabInspeccionesComerciales.NRECLAMO+"\t")
					  .append(CabInspeccionesComerciales.CELULAR_CLI+"\t")
					  .append(CabInspeccionesComerciales.FAX_CLI+"\t")
					  .append(CabInspeccionesComerciales.NIV_PREDIO+"\t")
					  .append(CabInspeccionesComerciales.NSE_PRE+"\t")
					  .append(CabInspeccionesComerciales.SOC_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.DOM_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.COM_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.IND_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.SOC_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.DOM_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.COM_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.IND_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_SUM+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEX+"\t")
					  .append(CabInspeccionesComerciales.UBIC_PRED+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX+"\t")
					  .append(CabInspeccionesComerciales.COTA_CONEX+"\t")
					  .append(CabInspeccionesComerciales.DISP_SEG+"\t")
					  .append(CabInspeccionesComerciales.TIP_LEC+"\t")
					  .append(CabInspeccionesComerciales.HOR_ABASC+"\t")
					  .append(CabInspeccionesComerciales.NIA+"\t")
					  .append(CabInspeccionesComerciales.DIA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.FTE_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.UBIC_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TIP_SUELO_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TIP_DESCAR_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.COTAH_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.COTAV_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.LONG_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.PROFUND_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TR_GRASAS+"\t")
					  .append(CabInspeccionesComerciales.SISTRAT+"\t")
					  .append(CabInspeccionesComerciales.MAT_TAPA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.MAT_CAJA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.MAT_TUB_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_TUB_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EMAIL+"\t")
					  .append(CabInspeccionesComerciales.CEL_CLI_C+"\t")
					  .append(CabInspeccionesComerciales.TELF_EMP+"\t")
					  .append(CabInspeccionesComerciales.PISO_DPTO_F+"\t")
					  .append(CabInspeccionesComerciales.SECTOR_F+"\t")
					  .append(CabInspeccionesComerciales.FTE_FINCA+"\t")
					  .append(CabInspeccionesComerciales.NSE_F+"\t")
					  .append(CabInspeccionesComerciales.OBS_F+"\t")
					  .append(CabInspeccionesComerciales.CORR_CUP+"\t")
					  .append(CabInspeccionesComerciales.PRED_NO_UBIC+"\t")
					  .append(CabInspeccionesComerciales.VAR_ACT_PREDIO+"\t")
					  .append(CabInspeccionesComerciales.INSPEC_REALIZ+"\t")
					  .append(CabInspeccionesComerciales.MOT_NO_ING+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX_AGUA+"\t")
					  .append(CabInspeccionesComerciales.IMPOSIBILIDAD+"\t")
					  .append(CabInspeccionesComerciales.SEGURO_TAP+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA+"\t")
					  .append(CabInspeccionesComerciales.INCI_MEDIDOR+"\t")
					  .append(CabInspeccionesComerciales.ACCESO_INMUEB+"\t")
					  .append(CabInspeccionesComerciales.OBS_ATENDIO+"\t")
					  .append(CabInspeccionesComerciales.CARTOGRAFIA+"\t")
					  .append(CabInspeccionesComerciales.MNTO_CARTOG+"\t")
					  .append(CabInspeccionesComerciales.CORR_CUP_F+"\t")
					  .append(CabInspeccionesComerciales.NUM_MED+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED+"\t")
					  .append(CabInspeccionesComerciales.CNX2_CON+"\t")
					  .append(CabInspeccionesComerciales.CNX3_CON+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED2+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED3+"\t")
					  .append(CabInspeccionesComerciales.LEC_MED2+"\t")
					  .append(CabInspeccionesComerciales.LEC_MED3+"\t")
					  .append(CabInspeccionesComerciales.COTA_HOR_DES+"\t")
					  .append(CabInspeccionesComerciales.COTA_VER_DES+"\t")
					  .append(CabInspeccionesComerciales.LONG_CNX_DES+"\t")
					  .append(CabInspeccionesComerciales.PROF_CJA_DES+"\t")
					  .append(CabInspeccionesComerciales.TIP_SUELO_DES+"\t")
					  .append(CabInspeccionesComerciales.IND_TRAM_DES+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX_DES+"\t")
					  .append(CabInspeccionesComerciales.SILO+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA_DES+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA_DES+"\t")
					  .append(CabInspeccionesComerciales.EST_TUB_DES+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_TAP_D+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_CAJ_D+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_TUB_D+"\t")
					  .append(CabInspeccionesComerciales.SIS_TRAT_RES+"\t")
					  .append(CabInspeccionesComerciales.TIP_DES_DES+"\t")
					  .append(CabInspeccionesComerciales.CLANDES_DES+"\t")
					  .append(CabInspeccionesComerciales.COD_SUP+"\t")
					  .append(CabInspeccionesComerciales.COD_DIGIT+"\r\n");
					  for(InspeccionesComerciales item : listaInsCom) {
						  trama.append(item.getCodigoCarga()+"\t")
						  .append(item.getCodigoRegistro()+"\t")
						  .append(item.getOficinaComercial()+"\t")
						  .append(item.getGeneroUsuario()+"\t")
						  .append(item.getCus()+"\t")
						  .append(item.getRuta()+"\t")
						  .append(item.getItin()+"\t")
						  .append(item.getTipsum()+"\t")
						  .append(item.getFechaEmision()+"\t")
						  .append(item.getObservacion()+"\t")
						  .append(item.getNombreRazon()+"\t")
						  .append(item.getLeRuc()+"\t")
						  .append(item.getTelefonoFax()+"\t")
						  .append(item.getCodAbastecimiento()+"\t")
						  .append(item.getReferenciaDireccion()+"\t")
						  .append(item.getCua()+"\t")
						  .append(item.getMedidor()+"\t")
						  .append(item.getDiametroMedidor()+"\t")
						  .append(item.getUltLectura()+"\t")
						  .append(item.getPtoMedia()+"\t")
						  .append(item.getAcom()+"\t")
						  .append(item.getNisRad()+"\t")
						  .append(item.getNumOS()+"\t")
						  .append(item.getTipOS()+"\t")
						  .append(item.getFechaResolucion()+"\t")
						  .append(item.getCodEmpleado()+"\t")
						  .append(item.getFechaVisita()+"\t")
						  .append(item.getCoAccejec()+"\t")
						  .append(item.getServ()+"\t")
						  .append(item.getServDT()+"\t")
						  .append(item.getCaja()+"\t")
						  .append(item.getEstadoCaja()+"\t")
						  .append(item.getCnxCon()+"\t")
						  .append(item.getCxCDT()+"\t")
						  .append(item.getLecturaMedidor()+"\t")
						  .append(item.getEstMedidorEncontrado1()+"\t")
						  .append(item.getEstMedidorEncontrado2()+"\t")
						  .append(item.getEstMedidorEncontrado3()+"\t")
						  .append(item.getPrescinto()+"\t")
						  .append(item.getFugaCaja()+"\t")
						  .append(item.getNroConexiones()+"\t")
						  .append(item.getMedidorEncontrado2()+"\t")
						  .append(item.getMedidorEncontrado3()+"\t")
						  .append(item.getNiple2()+"\t")
						  .append(item.getNiple3()+"\t")
						  .append(item.getValvulas()+"\t")
						  .append(item.getDisposit()+"\t")
						  .append(item.getClandes()+"\t")
						  .append(item.getNiv_finc()+"\t")
						  .append(item.getEstado()+"\t")
						  .append(item.getN_habit()+"\t")
						  .append(item.getUu_ocup()+"\t")
						  .append(item.getSoc_ocup()+"\t")
						  .append(item.getDom_ocup()+"\t")
						  .append(item.getCom_ocup()+"\t")
						  .append(item.getInd_ocup()+"\t")
						  .append(item.getEst_ocup()+"\t")
						  .append(item.getUu_docup()+"\t")
						  .append(item.getSoc_desc()+"\t")
						  .append(item.getDom_desc()+"\t")
						  .append(item.getCom_desc()+"\t")
						  .append(item.getInd_desc()+"\t")
						  .append(item.getEst_desc()+"\t")
						  .append(item.getUso_unic()+"\t")
						  .append(item.getCalle()+"\t")
						  .append(item.getNumero()+"\t")
						  .append(item.getDuplicad()+"\t")
						  .append(item.getMza()+"\t")
						  .append(item.getLote()+"\t")
						  .append(item.getCgv()+"\t")
						  .append(item.getUrbaniza()+"\t")
						  .append(item.getDistrito()+"\t")
						  .append(item.getCalle_c()+"\t")
						  .append(item.getNum_c()+"\t")
						  .append(item.getDupli_c()+"\t")
						  .append(item.getMza_c()+"\t")
						  .append(item.getLote_c()+"\t")
						  .append(item.getUrb_c()+"\t")
						  .append(item.getRef_c()+"\t")
						  .append(item.getDist_c()+"\t")
						  .append(item.getAtendio()+"\t")
						  .append(item.getPaterno()+"\t")
						  .append(item.getMaterno()+"\t")
						  .append(item.getNombre()+"\t")
						  .append(item.getRazsoc()+"\t")
						  .append(item.getDni()+"\t")
						  .append(item.getTelef()+"\t")
						  .append(item.getFax()+"\t")
						  .append(item.getRuc()+"\t")
						  .append(item.getInod_b()+"\t")
						  .append(item.getInod_f()+"\t")
						  .append(item.getInod_cl()+"\t")
						  .append(item.getLava_b()+"\t")
						  .append(item.getLava_f()+"\t")
						  .append(item.getLava_cl()+"\t")
						  .append(item.getDuch_b()+"\t")
						  .append(item.getDuch_f()+"\t")
						  .append(item.getDuch_cl()+"\t")
						  .append(item.getUrin_b()+"\t")
						  .append(item.getUrin_f()+"\t")
						  .append(item.getUrin_cl()+"\t")
						  .append(item.getGrifo_b()+"\t")
						  .append(item.getGrifo_f()+"\t")
						  .append(item.getGrifo_cl()+"\t")
						  .append(item.getCiste_b()+"\t")
						  .append(item.getCiste_f()+"\t")
						  .append(item.getCiste_cl()+"\t")
						  .append(item.getTanqu_b()+"\t")
						  .append(item.getTanqu_f()+"\t")
						  .append(item.getTanqu_cl()+"\t")
						  .append(item.getPisc_b()+"\t")
						  .append(item.getPisc_f()+"\t")
						  .append(item.getPisc_cl()+"\t")
						  .append(item.getBidet_b()+"\t")
						  .append(item.getBidet_f()+"\t")
						  .append(item.getBidet_cl()+"\t")
						  .append(item.getAbast()+"\t")
						  .append(item.getPtome_ca()+"\t")
						  .append(item.getNum_p()+"\t")
						  .append(item.getDup_p()+"\t")
						  .append(item.getMza_p()+"\t")
						  .append(item.getLote_p()+"\t")
						  .append(item.getObserv()+"\t")
						  .append(item.getObservm1()+"\t")
						  .append(item.getObservm2()+"\t")
						  .append(item.getCota_sum_c()+"\t")
						  .append(item.getPisos_c()+"\t")
						  .append(item.getCod_ubic_c()+"\t")
						  .append(item.getFte_conexion_c()+"\t")
						  .append(item.getCup_c()+"\t")
						  .append(item.getCota_sum()+"\t")
						  .append(item.getPisos()+"\t")
						  .append(item.getCod_ubic()+"\t")
						  .append(item.getFte_conexion()+"\t")
						  .append(item.getCup()+"\t")
						  .append(item.getDevuelto()+"\t")
						  .append(item.getDate_d()+"\t")
						  .append(item.getPrior()+"\t")
						  .append(item.getActualiza()+"\t")
						  .append((item.getDate_c() == null ? ' ': item.getDate_c())+"\t")
						  .append(item.getResuelto()+"\t")
						  .append(item.getMulti()+"\t")
						  .append(item.getParcial()+"\t")
						  .append(item.getFte_cnx()+"\t")
						  .append(item.getCota_sumc()+"\t")
						  .append(item.getPisosc()+"\t")
						  .append(item.getCod_ubicc()+"\t")
						  .append(item.getFte_cnxc()+"\t")
						  .append(item.getCupc()+"\t")
						  .append(item.getHora_i()+"\t")
						  .append(item.getHora_f()+"\t")
						  .append(item.getValv_e_tlsc()+"\t")
						  .append(item.getMat_valv_e()+"\t")
						  .append(item.getValv_s_p()+"\t")
						  .append(item.getMat_valv_s()+"\t")
						  .append(item.getSector()+"\t")
						  .append(item.getCup_pred()+"\t")
						  .append(item.getAol()+"\t")
						  .append(item.getCiclo()+"\t")
						  .append(item.getNreclamo()+"\t")
						  .append(String.format(Locale.US,"%.0f", item.getCelular_cli())+"\t")
						  .append(item.getFax_cli()+"\t")
						  .append(item.getNiv_predio()+"\t")
						  .append(item.getNse_pre()+"\t")
						  .append(item.getSoc_ocup_cat()+"\t")
						  .append(item.getDom_ocup_cat()+"\t")
						  .append(item.getCom_ocup_cat()+"\t")
						  .append(item.getInd_ocup_cat()+"\t")
						  .append(item.getEst_ocup_cat()+"\t")
						  .append(item.getSoc_desoc_cat()+"\t")
						  .append(item.getDom_desoc_cat()+"\t")
						  .append(item.getCom_desoc_cat()+"\t")
						  .append(item.getInd_desoc_cat()+"\t")
						  .append(item.getEst_desoc_cat()+"\t")
						  .append(item.getEst_sum()+"\t")
						  .append(item.getFte_conex()+"\t")
						  .append(item.getUbic_pred()+"\t")
						  .append(item.getDia_conex()+"\t")
						  .append(item.getCota_conex()+"\t")
						  .append(item.getDisp_seg()+"\t")
						  .append(item.getTip_lec()+"\t")
						  .append(item.getHor_abasc()+"\t")
						  .append(item.getNia()+"\t")
						  .append(item.getDia_alcan()+"\t")
						  .append(item.getFte_alcan()+"\t")
						  .append(item.getUbic_alcan()+"\t")
						  .append(item.getTip_suelo_alca()+"\t")
						  .append(item.getTip_descar_alc()+"\t")
						  .append(item.getCotah_alcan()+"\t")
						  .append(item.getCotav_alcan()+"\t")
						  .append(item.getLong_alcan()+"\t")
						  .append(item.getProfund_alcan()+"\t")
						  .append(item.getTr_grasas()+"\t")
						  .append(item.getSistrat()+"\t")
						  .append(item.getMat_tapa_alcan()+"\t")
						  .append(item.getEst_tapa_alcan()+"\t")
						  .append(item.getMat_caja_alcan()+"\t")
						  .append(item.getEst_caja_alcan()+"\t")
						  .append(item.getMat_tub_alcan()+"\t")
						  .append(item.getEst_tub_alcan()+"\t")
						  .append(item.getEmail()+"\t")
						  .append(item.getCel_cli_c()+"\t")
						  .append(item.getTelf_emp()+"\t")
						  .append(item.getPiso_dpto_f()+"\t")
						  .append(item.getSector_f()+"\t")
						  .append(item.getFte_finca()+"\t")
						  .append(item.getNse_f()+"\t")
						  .append(item.getObs_f()+"\t")
						  .append(item.getCorr_cup()+"\t")
						  .append(item.getPred_no_ubic()+"\t")
						  .append(item.getVar_act_predio()+"\t")
						  .append(item.getInspec_realiz()+"\t")
						  .append(item.getMot_no_ing()+"\t")
						  .append(item.getDia_conex_agua()+"\t")
						  .append(item.getImposibilidad()+"\t")
						  .append(item.getSeguro_tap()+"\t")
						  .append(item.getEst_tapa()+"\t")
						  .append(item.getInci_medidor()+"\t")
						  .append(item.getAcceso_inmueb()+"\t")
						  .append(item.getObs_atendio()+"\t")
						  .append(item.getCartografia()+"\t")
						  .append(item.getMnto_cartog()+"\t")
						  .append(item.getCorr_cup_f()+"\t")
						  .append(item.getNum_med()+"\t")
						  .append(item.getDia_med()+"\t")
						  .append(item.getCnx2_con()+"\t")
						  .append(item.getCnx3_con()+"\t")
						  .append(item.getDia_med2()+"\t")
						  .append(item.getDia_med3()+"\t")
						  .append(item.getLec_med2()+"\t")
						  .append(item.getLec_med3()+"\t")
						  .append(item.getCota_hor_des()+"\t")
						  .append(item.getCota_ver_des()+"\t")
						  .append(item.getLong_cnx_des()+"\t")
						  .append(item.getProf_cja_des()+"\t")
						  .append(item.getTip_suelo_des()+"\t")
						  .append(item.getInd_tram_des()+"\t")
						  .append(item.getDia_conex_des()+"\t")
						  .append(item.getSilo()+"\t")
						  .append(item.getEst_tapa_des()+"\t")
						  .append(item.getEst_caja_des()+"\t")
						  .append(item.getEst_tub_des()+"\t")
						  .append(item.getTip_mat_tap_d()+"\t")
						  .append(item.getTip_mat_caj_d()+"\t")
						  .append(item.getTip_mat_tub_d()+"\t")
						  .append(item.getSis_trat_res()+"\t")
						  .append(item.getTip_des_des()+"\t")
						  .append(item.getClandes_des()+"\t")
						  .append(item.getCod_sup()+"\t")
						  .append(item.getCod_digit()+"\r\n");
					  }
				  }else {					  
					  /*
					  trama.append(CabInspeccionesComerciales.OF_COMER+"\t")
					  .append(CabInspeccionesComerciales.GEN_POR+"\t")
					  .append(CabInspeccionesComerciales.CUS+"\t")
					  .append(CabInspeccionesComerciales.RUTA+"\t")
					  .append(CabInspeccionesComerciales.ITIN+"\t")
					  .append(CabInspeccionesComerciales.TIPSUM+"\t")
					  .append(CabInspeccionesComerciales.FEC_EMIS+"\t")
					  .append(CabInspeccionesComerciales.OBSERVAC+"\t")
					  .append(CabInspeccionesComerciales.NOM_RAZ+"\t")
					  .append(CabInspeccionesComerciales.LE_RUC+"\t")
					  .append(CabInspeccionesComerciales.IAME_FAX+"\t")
					  .append(CabInspeccionesComerciales.COD_ABAS+"\t")
					  .append(CabInspeccionesComerciales.REFER+"\t")
					  .append(CabInspeccionesComerciales.CUA+"\t")
					  .append(CabInspeccionesComerciales.MEDIDOR+"\t")
					  .append(CabInspeccionesComerciales.DIAM+"\t")
					  .append(CabInspeccionesComerciales.ULT_LEC+"\t")
					  .append(CabInspeccionesComerciales.PTO_MED+"\t")
					  .append(CabInspeccionesComerciales.ACOM+"\t")
					  .append(CabInspeccionesComerciales.NIS_RAD+"\t")
					  .append(CabInspeccionesComerciales.NUM_OS+"\t")
					  .append(CabInspeccionesComerciales.TIP_OS+"\t")
					  .append(CabInspeccionesComerciales.FE_RES+"\t")
					  .append(CabInspeccionesComerciales.COD_EMP+"\t")
					  .append(CabInspeccionesComerciales.FEC_VIS+"\t")
					  .append(CabInspeccionesComerciales.CO_ACCEJ+"\t")
					  .append(CabInspeccionesComerciales.SERV+"\t")
					  .append(CabInspeccionesComerciales.SERV_DT+"\t")
					  .append(CabInspeccionesComerciales.CAJA+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA+"\t")
					  .append(CabInspeccionesComerciales.CNX_CON+"\t")
					  .append(CabInspeccionesComerciales.CX_C_DT+"\t")
					  .append(CabInspeccionesComerciales.LEC+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED1+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED2+"\t")
					  .append(CabInspeccionesComerciales.FUN_MED3+"\t")
					  .append(CabInspeccionesComerciales.PRECINT+"\t")
					  .append(CabInspeccionesComerciales.FUGA_CAJ+"\t")
					  .append(CabInspeccionesComerciales.N_CONEX+"\t")
					  .append(CabInspeccionesComerciales.MED2+"\t")
					  .append(CabInspeccionesComerciales.MED3+"\t")
					  .append(CabInspeccionesComerciales.NIPLE2+"\t")
					  .append(CabInspeccionesComerciales.NIPLE3+"\t")
					  .append(CabInspeccionesComerciales.VALVULAS+"\t")
					  .append(CabInspeccionesComerciales.DISPOSIT+"\t")
					  .append(CabInspeccionesComerciales.CLANDES+"\t")
					  .append(CabInspeccionesComerciales.NIV_FINC+"\t")
					  .append(CabInspeccionesComerciales.ESTADO+"\t")
					  .append(CabInspeccionesComerciales.N_HABIT+"\t")
					  .append(CabInspeccionesComerciales.UU_OCUP+"\t")
					  .append(CabInspeccionesComerciales.SOC_OCUP+"\t")
					  .append(CabInspeccionesComerciales.DOM_OCUP+"\t")
					  .append(CabInspeccionesComerciales.COM_OCUP+"\t")
					  .append(CabInspeccionesComerciales.IND_OCUP+"\t")
					  .append(CabInspeccionesComerciales.EST_OCUP+"\t")
					  .append(CabInspeccionesComerciales.UU_DOCUP+"\t")
					  .append(CabInspeccionesComerciales.SOC_DESC+"\t")
					  .append(CabInspeccionesComerciales.DOM_DESC+"\t")
					  .append(CabInspeccionesComerciales.COM_DESC+"\t")
					  .append(CabInspeccionesComerciales.IND_DESC+"\t")
					  .append(CabInspeccionesComerciales.EST_DESC+"\t")
					  .append(CabInspeccionesComerciales.USO_UNIC+"\t")
					  .append(CabInspeccionesComerciales.CALLE+"\t")
					  .append(CabInspeccionesComerciales.NUMERO+"\t")
					  .append(CabInspeccionesComerciales.DUPLICAD+"\t")
					  .append(CabInspeccionesComerciales.MZA+"\t")
					  .append(CabInspeccionesComerciales.LOTE+"\t")
					  .append(CabInspeccionesComerciales.CGV+"\t")
					  .append(CabInspeccionesComerciales.URBANIZA+"\t")
					  .append(CabInspeccionesComerciales.DISTRITO+"\t")
					  .append(CabInspeccionesComerciales.CALLE_C+"\t")
					  .append(CabInspeccionesComerciales.NUM_C+"\t")
					  .append(CabInspeccionesComerciales.DUPLI_C+"\t")
					  .append(CabInspeccionesComerciales.MZA_C+"\t")
					  .append(CabInspeccionesComerciales.LOTE_C+"\t")
					  .append(CabInspeccionesComerciales.URB_C+"\t")
					  .append(CabInspeccionesComerciales.REF_C+"\t")
					  .append(CabInspeccionesComerciales.DIST_C+"\t")
					  .append(CabInspeccionesComerciales.ATENDIO+"\t")
					  .append(CabInspeccionesComerciales.PATERNO+"\t")
					  .append(CabInspeccionesComerciales.MATERNO+"\t")
					  .append(CabInspeccionesComerciales.NOMBRE+"\t")
					  .append(CabInspeccionesComerciales.RAZSOC+"\t")
					  .append(CabInspeccionesComerciales.DNI+"\t")
					  .append(CabInspeccionesComerciales.TELEF+"\t")
					  .append(CabInspeccionesComerciales.FAX+"\t")
					  .append(CabInspeccionesComerciales.RUC+"\t")
					  .append(CabInspeccionesComerciales.INOD_B+"\t")
					  .append(CabInspeccionesComerciales.INOD_F+"\t")
					  .append(CabInspeccionesComerciales.INOD_CL+"\t")
					  .append(CabInspeccionesComerciales.LAVA_B+"\t")
					  .append(CabInspeccionesComerciales.LAVA_F+"\t")
					  .append(CabInspeccionesComerciales.LAVA_CL+"\t")
					  .append(CabInspeccionesComerciales.DUCH_B+"\t")
					  .append(CabInspeccionesComerciales.DUCH_F+"\t")
					  .append(CabInspeccionesComerciales.DUCH_CL+"\t")
					  .append(CabInspeccionesComerciales.URIN_B+"\t")
					  .append(CabInspeccionesComerciales.URIN_F+"\t")
					  .append(CabInspeccionesComerciales.URIN_CL+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_B+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_F+"\t")
					  .append(CabInspeccionesComerciales.GRIFO_CL+"\t")
					  .append(CabInspeccionesComerciales.CISTE_B+"\t")
					  .append(CabInspeccionesComerciales.CISTE_F+"\t")
					  .append(CabInspeccionesComerciales.CISTE_CL+"\t")
					  .append(CabInspeccionesComerciales.TANQU_B+"\t")
					  .append(CabInspeccionesComerciales.TANQU_F+"\t")
					  .append(CabInspeccionesComerciales.TANQU_CL+"\t")
					  .append(CabInspeccionesComerciales.PISC_B+"\t")
					  .append(CabInspeccionesComerciales.PISC_F+"\t")
					  .append(CabInspeccionesComerciales.PISC_CL+"\t")
					  .append(CabInspeccionesComerciales.BIDET_B+"\t")
					  .append(CabInspeccionesComerciales.BIDET_F+"\t")
					  .append(CabInspeccionesComerciales.BIDET_CL+"\t")
					  .append(CabInspeccionesComerciales.ABAST+"\t")
					  .append(CabInspeccionesComerciales.PTOME_CA+"\t")
					  .append(CabInspeccionesComerciales.NUM_P+"\t")
					  .append(CabInspeccionesComerciales.DUP_P+"\t")
					  .append(CabInspeccionesComerciales.MZA_P+"\t")
					  .append(CabInspeccionesComerciales.LOTE_P+"\t")
					  .append(CabInspeccionesComerciales.OBSERV+"\t")
					  .append(CabInspeccionesComerciales.OBSERVM1+"\t")
					  .append(CabInspeccionesComerciales.OBSERVM2+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUM_C+"\t")
					  .append(CabInspeccionesComerciales.PISOS_C+"\t")
					  .append(CabInspeccionesComerciales.COD_UBIC_C+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEXION_C+"\t")
					  .append(CabInspeccionesComerciales.CUP_C+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUM+"\t")
					  .append(CabInspeccionesComerciales.PISOS+"\t")
					  .append(CabInspeccionesComerciales.COD_UBIC+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEXION+"\t")
					  .append(CabInspeccionesComerciales.CUP+"\t")
					  .append(CabInspeccionesComerciales.DEVUELTO+"\t")
					  .append(CabInspeccionesComerciales.FECHA_D+"\t")
					  .append(CabInspeccionesComerciales.PRIOR_1+"\t")
					  .append(CabInspeccionesComerciales.ACTUALIZA+"\t")
					  .append(CabInspeccionesComerciales.FECHA_C+"\t")
					  .append(CabInspeccionesComerciales.RESUELTO+"\t")
					  .append(CabInspeccionesComerciales.MULTI+"\t")
					  .append(CabInspeccionesComerciales.PARCIAL+"\t")
					  .append(CabInspeccionesComerciales.FTE_CNX+"\t")
					  .append(CabInspeccionesComerciales.COTA_SUMC+"\t")
					  .append(CabInspeccionesComerciales.PISOSC+"\t")
					  .append(CabInspeccionesComerciales.COD_UBICC+"\t")
					  .append(CabInspeccionesComerciales.FTE_CNXC+"\t")
					  .append(CabInspeccionesComerciales.CUPC+"\t")
					  .append(CabInspeccionesComerciales.HORA_I+"\t")
					  .append(CabInspeccionesComerciales.HORA_F+"\t")
					  .append(CabInspeccionesComerciales.VALV_E_TLSC+"\t")
					  .append(CabInspeccionesComerciales.MAT_VALV_E+"\t")
					  .append(CabInspeccionesComerciales.VALV_S_P+"\t")
					  .append(CabInspeccionesComerciales.MAT_VALV_S+"\t")
					  .append(CabInspeccionesComerciales.SECTOR+"\t")
					  .append(CabInspeccionesComerciales.CUP_PRED+"\t")
					  .append(CabInspeccionesComerciales.AOL+"\t")
					  .append(CabInspeccionesComerciales.CICLO+"\t")
					  .append(CabInspeccionesComerciales.NRECLAMO+"\t")
					  .append(CabInspeccionesComerciales.CELULAR_CLI+"\t")
					  .append(CabInspeccionesComerciales.FAX_CLI+"\t")
					  .append(CabInspeccionesComerciales.NIV_PREDIO+"\t")
					  .append(CabInspeccionesComerciales.NSE_PRE+"\t")
					  .append(CabInspeccionesComerciales.SOC_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.DOM_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.COM_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.IND_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_OCUP_CAT+"\t")
					  .append(CabInspeccionesComerciales.SOC_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.DOM_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.COM_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.IND_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_DESOC_CAT+"\t")
					  .append(CabInspeccionesComerciales.EST_SUM+"\t")
					  .append(CabInspeccionesComerciales.FTE_CONEX+"\t")
					  .append(CabInspeccionesComerciales.UBIC_PRED+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX+"\t")
					  .append(CabInspeccionesComerciales.COTA_CONEX+"\t")
					  .append(CabInspeccionesComerciales.DISP_SEG+"\t")
					  .append(CabInspeccionesComerciales.TIP_LEC+"\t")
					  .append(CabInspeccionesComerciales.HOR_ABASC+"\t")
					  .append(CabInspeccionesComerciales.NIA+"\t")
					  .append(CabInspeccionesComerciales.DIA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.FTE_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.UBIC_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TIP_SUELO_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TIP_DESCAR_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.COTAH_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.COTAV_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.LONG_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.PROFUND_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.TR_GRASAS+"\t")
					  .append(CabInspeccionesComerciales.SISTRAT+"\t")
					  .append(CabInspeccionesComerciales.MAT_TAPA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.MAT_CAJA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.MAT_TUB_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EST_TUB_ALCAN+"\t")
					  .append(CabInspeccionesComerciales.EMAIL+"\t")
					  .append(CabInspeccionesComerciales.CEL_CLI_C+"\t")
					  .append(CabInspeccionesComerciales.TELF_EMP+"\t")
					  .append(CabInspeccionesComerciales.PISO_DPTO_F+"\t")
					  .append(CabInspeccionesComerciales.SECTOR_F+"\t")
					  .append(CabInspeccionesComerciales.FTE_FINCA+"\t")
					  .append(CabInspeccionesComerciales.NSE_F+"\t")
					  .append(CabInspeccionesComerciales.OBS_F+"\t")
					  .append(CabInspeccionesComerciales.CORR_CUP+"\t")
					  .append(CabInspeccionesComerciales.PRED_NO_UBIC+"\t")
					  .append(CabInspeccionesComerciales.VAR_ACT_PREDIO+"\t")
					  .append(CabInspeccionesComerciales.INSPEC_REALIZ+"\t")
					  .append(CabInspeccionesComerciales.MOT_NO_ING+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX_AGUA+"\t")
					  .append(CabInspeccionesComerciales.IMPOSIBILIDAD+"\t")
					  .append(CabInspeccionesComerciales.SEGURO_TAP+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA+"\t")
					  .append(CabInspeccionesComerciales.INCI_MEDIDOR+"\t")
					  .append(CabInspeccionesComerciales.ACCESO_INMUEB+"\t")
					  .append(CabInspeccionesComerciales.OBS_ATENDIO+"\t")
					  .append(CabInspeccionesComerciales.CARTOGRAFIA+"\t")
					  .append(CabInspeccionesComerciales.MNTO_CARTOG+"\t")
					  .append(CabInspeccionesComerciales.CORR_CUP_F+"\t")
					  .append(CabInspeccionesComerciales.NUM_MED+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED+"\t")
					  .append(CabInspeccionesComerciales.CNX2_CON+"\t")
					  .append(CabInspeccionesComerciales.CNX3_CON+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED2+"\t")
					  .append(CabInspeccionesComerciales.DIA_MED3+"\t")
					  .append(CabInspeccionesComerciales.LEC_MED2+"\t")
					  .append(CabInspeccionesComerciales.LEC_MED3+"\t")
					  .append(CabInspeccionesComerciales.COTA_HOR_DES+"\t")
					  .append(CabInspeccionesComerciales.COTA_VER_DES+"\t")
					  .append(CabInspeccionesComerciales.LONG_CNX_DES+"\t")
					  .append(CabInspeccionesComerciales.PROF_CJA_DES+"\t")
					  .append(CabInspeccionesComerciales.TIP_SUELO_DES+"\t")
					  .append(CabInspeccionesComerciales.IND_TRAM_DES+"\t")
					  .append(CabInspeccionesComerciales.DIA_CONEX_DES+"\t")
					  .append(CabInspeccionesComerciales.SILO+"\t")
					  .append(CabInspeccionesComerciales.EST_TAPA_DES+"\t")
					  .append(CabInspeccionesComerciales.EST_CAJA_DES+"\t")
					  .append(CabInspeccionesComerciales.EST_TUB_DES+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_TAP_D+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_CAJ_D+"\t")
					  .append(CabInspeccionesComerciales.TIP_MAT_TUB_D+"\t")
					  .append(CabInspeccionesComerciales.SIS_TRAT_RES+"\t")
					  .append(CabInspeccionesComerciales.TIP_DES_DES+"\t")
					  .append(CabInspeccionesComerciales.CLANDES_DES+"\t")
					  .append(CabInspeccionesComerciales.COD_SUP+"\t")
					  .append(CabInspeccionesComerciales.COD_DIGIT+"\r\n");					  
					  */
					  for(InspeccionesComerciales item : listaInsCom) {						  
						// Se eliminan los espacios por defecto para tipo dato String
						  trama.append(item.getOficinaComercial().trim()+"\t")
						  .append(item.getGeneroUsuario().trim()+"\t")
						  .append(item.getCus().trim()+"\t")
						  .append(item.getRuta().trim()+"\t")
						  .append(item.getItin().trim()+"\t")
						  .append(item.getTipsum().trim()+"\t")
						  .append(item.getFechaEmision().trim()+"\t")
						  .append(item.getObservacion().trim()+"\t")
						  .append(item.getNombreRazon().trim()+"\t")
						  .append(item.getLeRuc().trim()+"\t")
						  .append(item.getTelefonoFax().trim()+"\t")
						  .append(item.getCodAbastecimiento().trim()+"\t")
						  .append(item.getReferenciaDireccion().trim()+"\t")
						  .append(item.getCua().trim()+"\t")
						  .append(item.getMedidor().trim()+"\t")
						  .append(item.getDiametroMedidor().trim()+"\t")
						  .append(item.getUltLectura().trim()+"\t")
						  .append(item.getPtoMedia().trim()+"\t")
						  .append(item.getAcom().trim()+"\t")
						  .append(item.getNisRad()+"\t")
						  .append(item.getNumOS()+"\t")
						  .append(item.getTipOS().trim()+"\t")
						  .append(item.getFechaResolucion().trim()+"\t")
						  .append(item.getCodEmpleado().trim()+"\t")
						  .append(item.getFechaVisita().trim()+"\t")
						  .append(item.getCoAccejec().trim()+"\t")
						  .append(item.getServ().trim()+"\t")
						  .append(item.getServDT().trim()+"\t")
						  .append(item.getCaja().trim()+"\t")
						  .append(item.getEstadoCaja().trim()+"\t")
						  .append(item.getCnxCon().trim()+"\t")
						  .append(item.getCxCDT().trim()+"\t")
						  .append(item.getLecturaMedidor().trim()+"\t")
						  .append(item.getEstMedidorEncontrado1().trim()+"\t")
						  .append(item.getEstMedidorEncontrado2().trim()+"\t")
						  .append(item.getEstMedidorEncontrado3().trim()+"\t")
						  .append(item.getPrescinto().trim()+"\t")
						  .append(item.getFugaCaja().trim()+"\t")
						  .append(item.getNroConexiones().trim()+"\t")
						  .append(item.getMedidorEncontrado2().trim()+"\t")
						  .append(item.getMedidorEncontrado3().trim()+"\t")
						  .append(item.getNiple2().trim()+"\t")
						  .append(item.getNiple3().trim()+"\t")
						  .append(item.getValvulas().trim()+"\t")
						  .append(item.getDisposit().trim()+"\t")
						  .append(item.getClandes().trim()+"\t")
						  .append(item.getNiv_finc().trim()+"\t")
						  .append(item.getEstado().trim()+"\t")
						  .append(item.getN_habit().trim()+"\t")
						  .append(item.getUu_ocup().trim()+"\t")
						  .append(item.getSoc_ocup().trim()+"\t")
						  .append(item.getDom_ocup().trim()+"\t")
						  .append(item.getCom_ocup().trim()+"\t")
						  .append(item.getInd_ocup().trim()+"\t")
						  .append(item.getEst_ocup().trim()+"\t")
						  .append(item.getUu_docup().trim()+"\t")
						  .append(item.getSoc_desc().trim()+"\t")
						  .append(item.getDom_desc().trim()+"\t")
						  .append(item.getCom_desc().trim()+"\t")
						  .append(item.getInd_desc().trim()+"\t")
						  .append(item.getEst_desc().trim()+"\t")
						  .append(item.getUso_unic().trim()+"\t")
						  .append(item.getCalle().trim()+"\t")
						  .append(item.getNumero().trim()+"\t")
						  .append(item.getDuplicad().trim()+"\t")
						  .append(item.getMza().trim()+"\t")
						  .append(item.getLote().trim()+"\t")
						  .append(item.getCgv().trim()+"\t")
						  .append(item.getUrbaniza().trim()+"\t")
						  .append(item.getDistrito().trim()+"\t")
						  .append(item.getCalle_c().trim()+"\t")
						  .append(item.getNum_c().trim()+"\t")
						  .append(item.getDupli_c().trim()+"\t")
						  .append(item.getMza_c().trim()+"\t")
						  .append(item.getLote_c().trim()+"\t")
						  .append(item.getUrb_c().trim()+"\t")
						  .append(item.getRef_c().trim()+"\t")
						  .append(item.getDist_c().trim()+"\t")
						  .append(item.getAtendio().trim()+"\t")
						  .append(item.getPaterno().trim()+"\t")
						  .append(item.getMaterno().trim()+"\t")
						  .append(item.getNombre().trim()+"\t")
						  .append(item.getRazsoc().trim()+"\t")
						  .append(item.getDni().trim()+"\t")
						  .append(item.getTelef().trim()+"\t")
						  .append(item.getFax().trim()+"\t")
						  .append(item.getRuc().trim()+"\t")
						  .append(item.getInod_b().trim()+"\t")
						  .append(item.getInod_f().trim()+"\t")
						  .append(item.getInod_cl().trim()+"\t")
						  .append(item.getLava_b().trim()+"\t")
						  .append(item.getLava_f().trim()+"\t")
						  .append(item.getLava_cl().trim()+"\t")
						  .append(item.getDuch_b().trim()+"\t")
						  .append(item.getDuch_f().trim()+"\t")
						  .append(item.getDuch_cl().trim()+"\t")
						  .append(item.getUrin_b().trim()+"\t")
						  .append(item.getUrin_f().trim()+"\t")
						  .append(item.getUrin_cl().trim()+"\t")
						  .append(item.getGrifo_b().trim()+"\t")
						  .append(item.getGrifo_f().trim()+"\t")
						  .append(item.getGrifo_cl().trim()+"\t")
						  .append(item.getCiste_b().trim()+"\t")
						  .append(item.getCiste_f().trim()+"\t")
						  .append(item.getCiste_cl().trim()+"\t")
						  .append(item.getTanqu_b().trim()+"\t")
						  .append(item.getTanqu_f().trim()+"\t")
						  .append(item.getTanqu_cl().trim()+"\t")
						  .append(item.getPisc_b().trim()+"\t")
						  .append(item.getPisc_f().trim()+"\t")
						  .append(item.getPisc_cl().trim()+"\t")
						  .append(item.getBidet_b().trim()+"\t")
						  .append(item.getBidet_f().trim()+"\t")
						  .append(item.getBidet_cl().trim()+"\t")
						  .append(item.getAbast().trim()+"\t")
						  .append(item.getPtome_ca().trim()+"\t")
						  .append(item.getNum_p().trim()+"\t")
						  .append(item.getDup_p().trim()+"\t")
						  .append(item.getMza_p().trim()+"\t")
						  .append(item.getLote_p().trim()+"\t")
						  .append(item.getObserv().trim()+"\t")
						  .append(item.getObservm1().trim()+"\t")
						  .append(item.getObservm2().trim()+"\t")
						  .append(item.getCota_sum_c().trim()+"\t")
						  .append(item.getPisos_c().trim()+"\t")
						  .append(item.getCod_ubic_c().trim()+"\t")
						  .append(item.getFte_conexion_c().trim()+"\t")
						  .append(item.getCup_c().trim()+"\t")
						  .append(item.getCota_sum().trim()+"\t")
						  .append(item.getPisos().trim()+"\t")
						  .append(item.getCod_ubic().trim()+"\t")
						  .append(item.getFte_conexion().trim()+"\t")
						  .append(item.getCup().trim()+"\t")
						  .append(item.getDevuelto().trim()+"\t")
						  .append(item.getDate_d().trim()+"\t")
						  .append(item.getPrior().trim()+"\t")
						  .append(item.getActualiza().trim()+"\t")
						  .append((item.getDate_c() == null ? ' ': item.getDate_c())+"\t")
						  .append(item.getResuelto().trim()+"\t")
						  .append(item.getMulti().trim()+"\t")
						  .append(item.getParcial().trim()+"\t")
						  .append(item.getFte_cnx().trim()+"\t")
						  .append(item.getCota_sumc().trim()+"\t")
						  .append(item.getPisosc().trim()+"\t")
						  .append(item.getCod_ubicc().trim()+"\t")
						  .append(item.getFte_cnxc().trim()+"\t")
						  .append(item.getCupc().trim()+"\t")
						  .append(item.getHora_i().trim()+"\t")
						  .append(item.getHora_f().trim()+"\t")
						  .append(item.getValv_e_tlsc().trim()+"\t")
						  .append(item.getMat_valv_e().trim()+"\t")
						  .append(item.getValv_s_p().trim()+"\t")
						  .append(item.getMat_valv_s().trim()+"\t")
						  .append(item.getSector()+"\t")
						  .append(item.getCup_pred().trim()+"\t")
						  .append(item.getAol()+"\t")
						  .append(item.getCiclo().trim()+"\t")
						  .append(item.getNreclamo().trim()+"\t")
						  .append(String.format(Locale.US,"%.0f", item.getCelular_cli())+"\t")
						  .append(item.getFax_cli()+"\t")
						  .append(item.getNiv_predio()+"\t")
						  .append(item.getNse_pre().trim()+"\t")
						  .append(item.getSoc_ocup_cat()+"\t")
						  .append(item.getDom_ocup_cat()+"\t")
						  .append(item.getCom_ocup_cat()+"\t")
						  .append(item.getInd_ocup_cat()+"\t")
						  .append(item.getEst_ocup_cat()+"\t")
						  .append(item.getSoc_desoc_cat()+"\t")
						  .append(item.getDom_desoc_cat()+"\t")
						  .append(item.getCom_desoc_cat()+"\t")
						  .append(item.getInd_desoc_cat()+"\t")
						  .append(item.getEst_desoc_cat()+"\t")
						  .append(item.getEst_sum().trim()+"\t")
						  .append(item.getFte_conex().trim()+"\t")
						  .append(item.getUbic_pred().trim()+"\t")
						  .append(item.getDia_conex()+"\t")
						  .append(item.getCota_conex()+"\t")
						  .append(item.getDisp_seg().trim()+"\t")
						  .append(item.getTip_lec().trim()+"\t")
						  .append(item.getHor_abasc().trim()+"\t")
						  .append(item.getNia()+"\t")
						  .append(item.getDia_alcan().trim()+"\t")
						  .append(item.getFte_alcan().trim()+"\t")
						  .append(item.getUbic_alcan().trim()+"\t")
						  .append(item.getTip_suelo_alca().trim()+"\t")
						  .append(item.getTip_descar_alc().trim()+"\t")
						  .append(item.getCotah_alcan()+"\t")
						  .append(item.getCotav_alcan()+"\t")
						  .append(item.getLong_alcan()+"\t")
						  .append(item.getProfund_alcan()+"\t")
						  .append(item.getTr_grasas()+"\t")
						  .append(item.getSistrat().trim()+"\t")
						  .append(item.getMat_tapa_alcan().trim()+"\t")
						  .append(item.getEst_tapa_alcan().trim()+"\t")
						  .append(item.getMat_caja_alcan().trim()+"\t")
						  .append(item.getEst_caja_alcan().trim()+"\t")
						  .append(item.getMat_tub_alcan().trim()+"\t")
						  .append(item.getEst_tub_alcan().trim()+"\t")
						  .append(item.getEmail().trim()+"\t")
						  .append(item.getCel_cli_c()+"\t")
						  .append(item.getTelf_emp()+"\t")
						  .append(item.getPiso_dpto_f().trim()+"\t")
						  .append(item.getSector_f()+"\t")
						  .append(item.getFte_finca().trim()+"\t")
						  .append(item.getNse_f().trim()+"\t")
						  .append(item.getObs_f().trim()+"\t")
						  .append(item.getCorr_cup().trim()+"\t")
						  .append(item.getPred_no_ubic().trim()+"\t")
						  .append(item.getVar_act_predio().trim()+"\t")
						  .append(item.getInspec_realiz().trim()+"\t")
						  .append(item.getMot_no_ing().trim()+"\t")
						  .append(item.getDia_conex_agua()+"\t")
						  .append(item.getImposibilidad().trim()+"\t")
						  .append(item.getSeguro_tap().trim()+"\t")
						  .append(item.getEst_tapa().trim()+"\t")
						  .append(item.getInci_medidor().trim()+"\t")
						  .append(item.getAcceso_inmueb().trim()+"\t")
						  .append(item.getObs_atendio().trim()+"\t")
						  .append(item.getCartografia().trim()+"\t")
						  .append(item.getMnto_cartog().trim()+"\t")
						  .append(item.getCorr_cup_f().trim()+"\t")
						  .append(item.getNum_med().trim()+"\t")
						  .append(item.getDia_med().trim()+"\t")
						  .append(item.getCnx2_con().trim()+"\t")
						  .append(item.getCnx3_con().trim()+"\t")
						  .append(item.getDia_med2().trim()+"\t")
						  .append(item.getDia_med3().trim()+"\t")
						  .append(item.getLec_med2()+"\t")
						  .append(item.getLec_med3()+"\t")
						  .append(item.getCota_hor_des()+"\t")
						  .append(item.getCota_ver_des()+"\t")
						  .append(item.getLong_cnx_des()+"\t")
						  .append(item.getProf_cja_des()+"\t")
						  .append(item.getTip_suelo_des().trim()+"\t")
						  .append(item.getInd_tram_des().trim()+"\t")
						  .append(item.getDia_conex_des().trim()+"\t")
						  .append(item.getSilo().trim()+"\t")
						  .append(item.getEst_tapa_des().trim()+"\t")
						  .append(item.getEst_caja_des().trim()+"\t")
						  .append(item.getEst_tub_des().trim()+"\t")
						  .append(item.getTip_mat_tap_d().trim()+"\t")
						  .append(item.getTip_mat_caj_d().trim()+"\t")
						  .append(item.getTip_mat_tub_d().trim()+"\t")
						  .append(item.getSis_trat_res().trim()+"\t")
						  .append(item.getTip_des_des().trim()+"\t")
						  .append(item.getClandes_des().trim()+"\t")
						  .append(item.getCod_sup()+"\t")
						  .append(item.getCod_digit()+"\r\n");
					  }
				  }				  
			  }else {
				  this.error = new Error(0,"Error","No se obtuvo detalle de la carga de trabajo.");
			  }
			    break;
		  case Constantes.ACT_MEDIDORES:
			  List<Medidores> listaMe = new ArrayList<>();
			  listaMe = daoME.ListarDetalleMedidoresTrama(request, uidCarga, filtro, usuario);
			  
			  if(!listaMe.isEmpty()){
				  if(idPerfil.equals(Constantes.PERFIL_ANALISTA_EXTERNO) || idPerfil.equals(Constantes.PERFIL_SUPERVISOR_EXTERNO)) {
					  trama.append(CabMedidores.V_IDCARGA+"\t")
					  .append(CabMedidores.N_IDREG+"\t")
					  .append(CabMedidores.NIS_RAD+"\t")
					  .append(CabMedidores.CLIENTE+"\t")
					  .append(CabMedidores.NUM_OS+"\t")
					  .append(CabMedidores.NOM_CALLE+"\t")
					  .append(CabMedidores.NUM_PUERTA+"\t")
					  .append(CabMedidores.DUPLICADOR+"\t")
					  .append(CabMedidores.CGV+"\t")
					  .append(CabMedidores.MZ+"\t")
					  .append(CabMedidores.LOTE+"\t")
					  .append(CabMedidores.LOCALIDAD+"\t")
					  .append(CabMedidores.DISTRITO+"\t")
					  .append(CabMedidores.CUS+"\t")
					  .append(CabMedidores.REF_DIR+"\t")
					  .append(CabMedidores.MED_ACTUAL+"\t")
					  .append(CabMedidores.DIAMETRO+"\t")
					  .append(CabMedidores.ACCION+"\t")
					  .append(CabMedidores.COMENT_OS+"\t")
					  .append(CabMedidores.TEXTO+"\t")
					  .append(CabMedidores.TPI+"\t")
					  .append(CabMedidores.F_RES+"\t")
					  .append(CabMedidores.F_INSP+"\t")
					  .append(CabMedidores.TIP_OS+"\t")
					  .append(CabMedidores.HORA+"\r\n");					  
					  for(Medidores item : listaMe) {
						  trama.append(item.getCodigoCarga()+"\t")
						  .append(item.getCodigoRegistro()+"\t")
						  .append(item.getNroSuministro()+"\t")
						  .append(item.getNombreCliente()+"\t")
						  .append(item.getNumeroOrdenServicio()+"\t")
						  .append(item.getNombreCalle()+"\t")
						  .append(item.getNroPuerta()+"\t")
						  .append(item.getDuplicador()+"\t")
						  .append(item.getCgv()+"\t")
						  .append(item.getMz()+"\t")
						  .append(item.getLote()+"\t")
						  .append(item.getLocalidad()+"\t")
						  .append(item.getDistrito()+"\t")
						  .append(item.getCus()+"\t")
						  .append(item.getDireccionReferencia()+"\t")
						  .append(item.getMedidorActual()+"\t")
						  .append(item.getDiametro()+"\t")
						  .append(item.getAccion()+"\t")
						  .append(item.getComentario()+"\t")
						  .append(item.getTexto()+"\t")
						  .append(item.getTpi()+"\t")
						  .append(item.getFechaResolucion()+"\t")
						  .append(item.getFechaInspeccion()+"\t")
						  .append(item.getTipoOrdenServicio()+"\t")
						  .append(item.getHoraInspeccion()+"\r\n");
					  }
				  }else {
					  /*trama.append(CabMedidores.DBESTADO+"\t")
					  .append(CabMedidores.DBNRECEP+"\t")
					  .append(CabMedidores.DBNUMORD+"\t")
					  .append(CabMedidores.DBNUMCOM+"\t")
					  .append(CabMedidores.DBFEJEC+"\t")
					  .append(CabMedidores.DBDIST+"\t")
					  .append(CabMedidores.DBNIS+"\t")
					  .append(CabMedidores.DBACT01+"\t")
					  .append(CabMedidores.DBCAN01+"\t")
					  .append(CabMedidores.DBACT02+"\t")
					  .append(CabMedidores.DBCAN02+"\t")
					  .append(CabMedidores.DBACT03+"\t")
					  .append(CabMedidores.DBCAN03+"\t")
					  .append(CabMedidores.DBACT04+"\t")
					  .append(CabMedidores.DBCAN04+"\t")
					  .append(CabMedidores.DBACT05+"\t")
					  .append(CabMedidores.DBCAN05+"\t")
					  .append(CabMedidores.DBACT06+"\t")
					  .append(CabMedidores.DBCAN06+"\t")
					  .append(CabMedidores.DBACT07+"\t")
					  .append(CabMedidores.DBCAN07+"\t")
					  .append(CabMedidores.DBACT08+"\t")
					  .append(CabMedidores.DBCAN08+"\t")
					  .append(CabMedidores.DBACT09+"\t")
					  .append(CabMedidores.DBCAN09+"\t")
					  .append(CabMedidores.DBACT10+"\t")
					  .append(CabMedidores.DBCAN10+"\t")
					  .append(CabMedidores.DBLARGO+"\t")
					  .append(CabMedidores.DBANCHO+"\t")
					  .append(CabMedidores.DBESTTAPA+"\t")
					  .append(CabMedidores.DBMARTAP+"\t")
					  .append(CabMedidores.DBVALBRON+"\t")
					  .append(CabMedidores.DBVALPVC+"\t")
					  .append(CabMedidores.DBTUBPLOM+"\t")
					  .append(CabMedidores.DBTIPOACT+"\t")
					  .append(CabMedidores.DBMOTLVTO+"\t")
					  .append(CabMedidores.DBNUMRET+"\t")
					  .append(CabMedidores.DBESTRET+"\t")
					  .append(CabMedidores.DBTIPRET+"\t")
					  .append(CabMedidores.DBDIAM+"\t")
					  .append(CabMedidores.DBUBIC+"\t")
					  .append(CabMedidores.DBCNXFRA+"\t")
					  .append(CabMedidores.DBCNXFOTRO+"\t")
					  .append(CabMedidores.DBTIPLECT+"\t")
					  .append(CabMedidores.DBNUMINST+"\t")
					  .append(CabMedidores.DBESTINST+"\t")
					  .append(CabMedidores.DBVERMINST+"\t")
					  .append(CabMedidores.DBDISPSEG+"\t")
					  .append(CabMedidores.DBABONADO+"\t")
					  .append(CabMedidores.DBVALPMED+"\t")
					  .append(CabMedidores.DBMATVPM+"\t")
					  .append(CabMedidores.DBVALTELE+"\t")
					  .append(CabMedidores.DBMATVTEL+"\t")
					  .append(CabMedidores.DBCOD_OPE+"\t")
					  .append(CabMedidores.DBCOD_TSU+"\t")
					  .append(CabMedidores.DBCOD_SUP+"\t")
					  .append(CabMedidores.DBTUBERIA+"\t")
					  .append(CabMedidores.DBLONG+"\t")
					  .append(CabMedidores.DBMATRED+"\t")
					  .append(CabMedidores.DBTIPCNX+"\t")
					  .append(CabMedidores.DBTIPPAV+"\t")
					  .append(CabMedidores.DBFUGAS+"\t")
					  .append(CabMedidores.DBTELFCLI+"\t")
					  .append(CabMedidores.DBSUPERV+"\t")
					  .append(CabMedidores.DBCODOBS+"\t")
					  .append(CabMedidores.DBOBSERV+"\t")
					  .append(CabMedidores.DBDSEGMED+"\t")
					  .append(CabMedidores.DBTUER_VAL+"\t")
					  .append(CabMedidores.DBMAT01+"\t")
					  .append(CabMedidores.DBMAT02+"\t")
					  .append(CabMedidores.DBMAT03+"\t")
					  .append(CabMedidores.DBMAT04+"\t")
					  .append(CabMedidores.DBMAT05+"\t")
					  .append(CabMedidores.DBMAT06+"\t")
					  .append(CabMedidores.DBMAT07+"\t")
					  .append(CabMedidores.DBMAT08+"\t")
					  .append(CabMedidores.DBMAT09+"\t")
					  .append(CabMedidores.DBMAT10+"\t")
					  .append(CabMedidores.DBMAT11+"\t")
					  .append(CabMedidores.DBMAT12+"\t")
					  .append(CabMedidores.DBMAT13+"\t")
					  .append(CabMedidores.DBMAT14+"\t")
					  .append(CabMedidores.DBMAT15+"\t")
					  .append(CabMedidores.DBMAT16+"\t")
					  .append(CabMedidores.DBMAT17+"\t")
					  .append(CabMedidores.DBMAT18+"\t")
					  .append(CabMedidores.DBMAT18_D+"\t")
					  .append(CabMedidores.DBMAT19+"\t")
					  .append(CabMedidores.DBMAT20+"\t")
					  .append(CabMedidores.DBMAT21+"\t")
					  .append(CabMedidores.DBMAT22+"\t")
					  .append(CabMedidores.DBMAT23+"\t")
					  .append(CabMedidores.DBMAT24+"\t")
					  .append(CabMedidores.DBMAT25+"\t")
					  .append(CabMedidores.DBMAT26+"\t")
					  .append(CabMedidores.DBMAT27+"\t")
					  .append(CabMedidores.DBMAT28+"\t")
					  .append(CabMedidores.DBMAT29+"\t")
					  .append(CabMedidores.DBMAT30+"\t")
					  .append(CabMedidores.DBESTCAJA+"\t")
					  .append(CabMedidores.DBESTVTEL+"\t")
					  .append(CabMedidores.DBESTVPM+"\t")
					  .append(CabMedidores.DBESTABRA+"\t")
					  .append(CabMedidores.DBESTMED+"\t")
					  .append(CabMedidores.DBESTTUBF+"\t")
					  .append(CabMedidores.DBSELLRAT+"\t")
					  .append(CabMedidores.DBESTSOLAD+"\t")
					  .append(CabMedidores.DBTFRET+"\t")
					  .append(CabMedidores.DBNUM_DISP+"\t")
					  .append(CabMedidores.DBNUM_TELF+"\t")
					  .append(CabMedidores.DBMOTIACT+"\t")
					  .append(CabMedidores.DBEJEOPER+"\t")
					  .append(CabMedidores.DBEJETECN+"\t")
					  .append(CabMedidores.DBINSPEC+"\t")
					  .append(CabMedidores.DBOFICINA+"\r\n");*/					  
					  for(Medidores item : listaMe) {						  
						  trama.append(!item.getEstadoOT().equals(" ")? item.getEstadoOT()+"\t" : "\t")
						  .append(item.getNroRecepcion()+"\t")
						  .append(!item.getNroOT().equals(" ")? item.getNroOT()+"\t" : "\t")
						  .append(!item.getNroCompromiso().equals(" ")? item.getNroCompromiso()+"\t" : "\t")
						  .append(item.getFechaEjecucion()+"\t")
						  .append(!item.getCodigoDistrito().equals(" ")? item.getCodigoDistrito()+"\t" : "\t")
						  .append(!item.getNroSuministroCliente().equals(" ")? item.getNroSuministroCliente()+"\t" : "\t")
						  .append(!item.getCodigoActividadEjecutada1().equals(" ")? item.getCodigoActividadEjecutada1()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad1()+"\t")
						  .append(!item.getCodigoActividadEjecutada2().equals(" ")? item.getCodigoActividadEjecutada2()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad2()+"\t")
						  .append(!item.getCodigoActividadEjecutada3().equals(" ")? item.getCodigoActividadEjecutada3()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad3()+"\t")
						  .append(!item.getCodigoActividadEjecutada4().equals(" ")? item.getCodigoActividadEjecutada4()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad4()+"\t")
						  .append(!item.getCodigoActividadEjecutada5().equals(" ")? item.getCodigoActividadEjecutada5()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad5()+"\t")
						  .append(!item.getCodigoActividadEjecutada6().equals(" ")? item.getCodigoActividadEjecutada6()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad6()+"\t")
						  .append(!item.getCodigoActividadEjecutada7().equals(" ")? item.getCodigoActividadEjecutada7()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad7()+"\t")
						  .append(!item.getCodigoActividadEjecutada8().equals(" ")? item.getCodigoActividadEjecutada8()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad8()+"\t")
						  .append(!item.getCodigoActividadEjecutada9().equals(" ")? item.getCodigoActividadEjecutada9()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad9()+"\t")
						  .append(!item.getCodigoActividadEjecutada10().equals(" ")? item.getCodigoActividadEjecutada10()+"\t" : "\t")
						  .append(item.getCantidadEjecutadaActividad10()+"\t")
						  .append(item.getLargoActiComplEjec()+"\t")
						  .append(item.getAnchoActividadComplementariaEjecutada()+"\t")
						  .append(!item.getEstadoTapa().equals(" ")? item.getEstadoTapa()+"\t" : "\t")
						  .append(!item.getMarTap().equals(" ")? item.getMarTap()+"\t" : "\t")
						  .append(item.getValvulasBronce()+"\t")
						  .append(item.getValPVC()+"\t")
						  .append(item.getTuberiaPlomo()+"\t")
						  .append(!item.getTipoActividadAMM().equals(" ")? item.getTipoActividadAMM()+"\t" : "\t")
						  .append(!item.getMotivoLevantamiento().equals(" ")? item.getMotivoLevantamiento()+"\t" : "\t")
						  .append(!item.getNroMedidorRetirado().equals(" ")? item.getNroMedidorRetirado	()+"\t" : "\t")
						  .append(item.getLectura()+"\t")
						  .append(!item.getTipoMatePredomina().equals(" ")? item.getTipoMatePredomina()+"\t" : "\t")
						  .append(!item.getDbDiametro().equals(" ")? item.getDbDiametro()+"\t" : "\t")
						  .append(!item.getUbicacionCajaControl().equals(" ")? item.getUbicacionCajaControl()+"\t" : "\t")
						  .append(!item.getEvaluacionConexionFraudulenta().equals(" ")? item.getEvaluacionConexionFraudulenta()+"\t" : "\t")
						  .append(!item.getOtroTipoConexFraudulenta().equals(" ")? item.getOtroTipoConexFraudulenta()+"\t" : "\t")
						  .append(!item.getTipoLecturaMedidorInstalado().equals(" ")? item.getTipoLecturaMedidorInstalado()+"\t" : "\t")
						  .append(!item.getNroMedidorInstalado().equals(" ")? item.getNroMedidorInstalado()+"\t" : "\t")
						  .append(item.getLecturaM3()+"\t")
						  .append(!item.getVerificaMedidorInstalado().equals(" ")? item.getVerificaMedidorInstalado()+"\t" : "\t")
						  .append(!item.getNroDispositivoSeguridad().equals(" ")? item.getNroDispositivoSeguridad()+"\t" : "\t")
						  .append(!item.getNroAbonado().equals(" ")? item.getNroAbonado()+"\t" : "\t")
						  .append(item.getValvulaPuntoMedicion()+"\t")
						  .append(!item.getMaterialValvulaPuntoMedicion().equals(" ")? item.getMaterialValvulaPuntoMedicion()+"\t" : "\t")
						  .append(item.getValvulaTelescopia()+"\t")
						  .append(!item.getMaterialValvulaTelescopia().equals(" ")? item.getMaterialValvulaTelescopia()+"\t" : "\t")
						  .append(!item.getDniOperarioOT().equals(" ")? item.getDniOperarioOT()+"\t" : "\t")
						  .append(!item.getDniSupervisorOT().equals(" ")? item.getDniSupervisorOT()+"\t" : "\t")
						  .append(!item.getDniSupervisorAsignadoSedapal().equals(" ")? item.getDniSupervisorAsignadoSedapal()+"\t" : "\t")
						  .append(!item.getMaterialTuberiaConexionDomicilia().equals(" ")? item.getMaterialTuberiaConexionDomicilia()+"\t" : "\t")
						  .append(item.getLongitudTubeMatriz()+"\t")
						  .append(!item.getMaterialRed().equals(" ")? item.getMaterialRed()+"\t" : "\t")
						  .append(!item.getTipoConexion().equals(" ")? item.getTipoConexion()+"\t" : "\t")
						  .append(!item.getTipoPavimento().equals(" ")? item.getTipoPavimento()+"\t" : "\t")						  
						  .append(!item.getFugasInstainternas().equals(" ")? item.getFugasInstainternas()+"\t" : "\t")
						  .append(!item.getNroTelefonoCliente().equals(" ")? item.getNroTelefonoCliente()+"\t" : "\t")
						  .append(!item.getSupervisadaOT().equals(" ")? item.getSupervisadaOT()+"\t" : "\t")						  
						  .append(!item.getCodigoObservacion().equals(" ")? item.getCodigoObservacion()+"\t" : "\t")						  
						  .append(!item.getObsCampo().equals(" ")? item.getObsCampo()+"\t" : "\t")
						  .append(!item.getDispoSeguridadMedidor().equals(" ")? item.getDispoSeguridadMedidor()+"\t" : "\t")
						  .append(item.getTuercasValvula()+"\t")
						  .append(item.getCantAdaptadorPresion()+"\t")
						  .append(item.getCantidadCodos()+"\t")
						  .append(item.getCantidadCurvas3()+"\t")
						  .append(item.getCantidadNiplesRemplazo()+"\t")
						  .append(item.getCantidadNipleSTD()+"\t")
						  .append(item.getCantidadPresintosSeguridad()+"\t")
						  .append(item.getCantidadTransicion()+"\t")
						  .append(item.getCantidadTuberia()+"\t")
						  .append(item.getCantidadPolietileno()+"\t")
						  .append(item.getCantidadValvulaSimple()+"\t")
						  .append(item.getCantidadFiltroMedidor()+"\t")
						  .append(item.getCantidadDispositivo()+"\t")
						  .append(item.getTermoplasticoMT()+"\t")
						  .append(item.getValvulaTelescopicaPVC()+"\t")
						  .append(item.getValvulaTelescopicaPmed()+"\t")
						  .append(item.getDbMat16()+"\t")
						  .append(item.getDbMat17()+"\t")
						  .append(item.getDbMat18()+"\t")
						  .append(!item.getDbMat18D().equals(" ")? item.getDbMat18D()+"\t" : "\t")
						  .append(item.getDbMat19()+"\t")
						  .append(item.getDbMat20()+"\t")
						  .append(item.getDbMat21()+"\t")
						  .append(item.getDbMat22()+"\t")
						  .append(item.getDbMat23()+"\t")
						  .append(item.getDbMat24()+"\t")
						  .append(item.getDbMat25()+"\t")
						  .append(item.getDbMat26()+"\t")
						  .append(item.getDbMat27()+"\t")
						  .append(item.getDbMat28()+"\t")
						  .append(item.getDbMat29()+"\t")
						  .append(item.getDbMat30()+"\t")
						  .append(!item.getEstadoCaja().equals(" ")? item.getEstadoCaja()+"\t" : "\t")
						  .append(!item.getEstadoValTeles().equals(" ")? item.getEstadoValTeles()+"\t" : "\t")
						  .append(!item.getEstadoValPunMedicion().equals(" ")? item.getEstadoValPunMedicion()+"\t" : "\t")
						  .append(!item.getEstadoValAbrazadera().equals(" ")? item.getEstadoValAbrazadera()+"\t" : "\t")
						  .append(!item.getEstadoMedidor().equals(" ")? item.getEstadoMedidor()+"\t" : "\t")
						  .append(!item.getEstadoTubForro().equals(" ")? item.getEstadoTubForro()+"\t" : "\t")
						  .append(!item.getSelladoraRatonera().equals(" ")? item.getSelladoraRatonera()+"\t" : "\t")
						  .append(!item.getEstadoSoldado().equals(" ")? item.getEstadoSoldado()+"\t" : "\t")
						  .append(item.getTuboForroRetirado()+"\t")
						  .append(!item.getDbNroDisp().equals(" ")? item.getDbNroDisp()+"\t" : "\t")
						  .append(!item.getDbNroTelf().equals(" ")? item.getDbNroTelf()+"\t" : "\t")
						  .append(!item.getMotivoAct().equals(" ")? item.getMotivoAct()+"\t" : "\t")
						  .append(!item.getEjecOperario().equals(" ")? item.getEjecOperario()+"\t" : "\t")
						  .append(!item.getEjecTecnico().equals(" ")? item.getEjecTecnico()+"\t" : "\t")
						  .append(!item.getInspec().equals(" ")? item.getInspec()+"\t" : "\t")
						  .append(!item.getCodigoOficina().equals(" ")? item.getCodigoOficina()+"\r\n" : "\r\n");
					  }					  
				  }				  
			  }else {
				  this.error = new Error(0,"Error","No se obtuvo detalle de la carga de trabajo.");
			  }
			    break;
		  case Constantes.ACT_TOMA_ESTADO:
			  List<TomaEstados> listaTomaEst = new ArrayList<>();
			  listaTomaEst = daoTomEst.ListarTomaEstadosTrama(request, uidCarga, filtro, usuario);
			  
			  if(!listaTomaEst.isEmpty()){
				  if(idPerfil.equals(Constantes.PERFIL_ANALISTA_EXTERNO) || idPerfil.equals(Constantes.PERFIL_SUPERVISOR_EXTERNO)) {					  
					  trama.append(CabTomaEstados.V_IDCARGA+"\t")
					          .append(CabTomaEstados.N_IDREG+"\t")
					  		  .append(CabTomaEstados.COD_UNICOM+"\t")
							  .append(CabTomaEstados.ANNO+"\t")          
							  .append(CabTomaEstados.MES+"\t")           
							  .append(CabTomaEstados.CICLO_FACT+"\t")
							  .append(CabTomaEstados.RUTA+"\t")     
							  .append(CabTomaEstados.NUM_ITIN+"\t")
							  .append(CabTomaEstados.AOL+"\t")       
							  .append(CabTomaEstados.NOM_RAZ_SOCIAL+"\t")
							  .append(CabTomaEstados.NOM_LOCAL+"\t") 
							  .append(CabTomaEstados.NOM_CALLE+"\t")      
							  .append(CabTomaEstados.COMPL_DIREC+"\t")    
							  .append(CabTomaEstados.COD_MUNIC+"\t")    
							  .append(CabTomaEstados.MZ_LOT+"\t")      
							  .append(CabTomaEstados.CGV+"\t")            
							  .append(CabTomaEstados.ACC_PREDIO+"\t")
							  .append(CabTomaEstados.ACC_CAJA+"\t")     
							  .append(CabTomaEstados.NUM_APA+"\t")       
							  .append(CabTomaEstados.LECT_ANT+"\t")       
							  .append(CabTomaEstados.NUM_RUE+"\t")       
							  .append(CabTomaEstados.COD_TAR+"\t")        
							  .append(CabTomaEstados.NIS_RAD+"\t")        
							  .append(CabTomaEstados.COD_CNAE+"\t")       
							  .append(CabTomaEstados.NUM_APA_OBS+"\t")    
							  .append(CabTomaEstados.OBS+"\t")    
							  .append(CabTomaEstados.LECT+"\t")           
							  .append(CabTomaEstados.INC_1+"\t")          
							  .append(CabTomaEstados.DET_1+"\t")          
							  .append(CabTomaEstados.INC_2+"\t")          
							  .append(CabTomaEstados.DET_2+"\t")          
							  .append(CabTomaEstados.INC_3+"\t")          
							  .append(CabTomaEstados.DET_3+"\t")          
							  .append(CabTomaEstados.FECHA+"\t")          
							  .append(CabTomaEstados.HORA+"\t")          
							  .append(CabTomaEstados.COD_LECTOR+"\t")
							  .append(CabTomaEstados.MEDIO+"\t")     
							  .append(CabTomaEstados.CSMO_ACTUAL+"\t")    
							  .append(CabTomaEstados.DES_INC_1+"\t")     
							  .append(CabTomaEstados.DES_INC_2+"\t")      
							  .append(CabTomaEstados.DES_INC_3+"\t")      
							  .append(CabTomaEstados.DES_DET1+"\t")      
							  .append(CabTomaEstados.DES_DET2+"\t")       
							  .append(CabTomaEstados.DES_DET3+"\t")       
							  .append(CabTomaEstados.LECT_ORI+"\t")       
							  .append(CabTomaEstados.FECHA_ORI+"\t")      
							  .append(CabTomaEstados.HORA_ORI+"\t")      
							  .append(CabTomaEstados.TIP_LECTURA+"\t")
							  .append(CabTomaEstados.CUP+"\t")    
							  .append(CabTomaEstados.SECTOR+"\t")         
							  .append(CabTomaEstados.CSMO_PROM+"\r\n");
					  for(TomaEstados item : listaTomaEst) {
						  trama.append(item.getCodigoCarga()+"\t")
					  		.append(item.getCodigoRegistro()+"\t")
					  		.append(item.getCodigoOficinaComercial()+"\t")
					  		.append(item.getAnno()+"\t")
					  		.append(item.getMes()+"\t")
					  		.append(item.getCicloComercial()+"\t")
					  		.append(item.getRuta()+"\t")
					  		.append(item.getNumeroIntenerario()+"\t")
					  		.append(item.getAOL()+"\t")
					  		.append(item.getNombreRazonSocial()+"\t")
					  		.append(item.getNombreUrbanizacion()+"\t")
					  		.append(item.getNombreCalle()+"\t")
					  		.append(item.getComplementoDireccion()+"\t")
					  		.append(item.getNumeroPuerta()+"\t")
					  		.append(item.getManzanaLote()+"\t")
					  		.append(item.getCGV()+"\t")
					  		.append(item.getAccesoPredio()+"\t")
					  		.append(item.getAccesoPuntoMedida()+"\t")
					  		.append(item.getNumeroApa()+"\t")
					  		.append(item.getLecturaAnterior()+"\t")
					  		.append(item.getNumeroRuedasMedidor()+"\t")
					  		.append(item.getCodigoTarifa()+"\t")
					  		.append(item.getSuministro()+"\t")
					  		.append(item.getCodigoCNAE()+"\t")
					  		.append(item.getNumeroApaObs()+"\t")
					  		.append(item.getObservacion()+"\t")					  		
					  		.append(item.getLecturaMedidor()+"\t")
					  		.append(item.getPrimerIncidente()+"\t")
					  		.append(item.getDetallePrimerIncidente()+"\t")
					  		.append(item.getSegundoIncidente()+"\t")
					  		.append(item.getDetalleSegundoIncidente()+"\t")
					  		.append(item.getTercerIncidente()+"\t")
					  		.append(item.getDetalleTercerIncidente()+"\t")					  		
					  		.append(item.getFechaLectura()+"\t")
					  		.append(item.getHoraLectura()+"\t")
					  		.append(item.getCodigoTomadorEstado()+"\t")
					  		.append(item.getMedio()+"\t")
					  		.append(item.getCsmoActual()+"\t")
					  		.append(item.getDesIncidente1()+"\t")
					  		.append(item.getDesIncidente2()+"\t")
					  		.append(item.getDesIncidente3()+"\t")
					  		.append(item.getDesDetalle1()+"\t")
					  		.append(item.getDesDetalle2()+"\t")
					  		.append(item.getDesDetalle3()+"\t")
					  		.append(item.getLecturaOriginal()+"\t")
					  		.append(item.getFechaOrigen()+"\t")
					  		.append(item.getHoraOrigen()+"\t")
					  		.append(item.getTipoLectura()+"\t")
					  		.append(item.getCup()+"\t")
					  		.append(item.getSector()+"\t")
					  		.append(item.getCsmoPromedioCalculado()+"\r\n");
					  }
				  }else {
//					  trama.append(CabTomaEstados.COD_UNICOM+"\t")
//							  .append(CabTomaEstados.ANNO+"\t")          
//							  .append(CabTomaEstados.MES+"\t")           
//							  .append(CabTomaEstados.CICLO_FACT+"\t")
//							  .append(CabTomaEstados.RUTA+"\t")     
//							  .append(CabTomaEstados.NUM_ITIN+"\t")
//							  .append(CabTomaEstados.AOL+"\t")       
//							  .append(CabTomaEstados.NOM_RAZ_SOCIAL+"\t")
//							  .append(CabTomaEstados.NOM_LOCAL+"\t") 
//							  .append(CabTomaEstados.NOM_CALLE+"\t")      
//							  .append(CabTomaEstados.COMPL_DIREC+"\t")    
//							  .append(CabTomaEstados.COD_MUNIC+"\t")    
//							  .append(CabTomaEstados.MZ_LOT+"\t")      
//							  .append(CabTomaEstados.CGV+"\t")            
//							  .append(CabTomaEstados.ACC_PREDIO+"\t")
//							  .append(CabTomaEstados.ACC_CAJA+"\t")     
//							  .append(CabTomaEstados.NUM_APA+"\t")       
//							  .append(CabTomaEstados.LECT_ANT+"\t")       
//							  .append(CabTomaEstados.NUM_RUE+"\t")       
//							  .append(CabTomaEstados.COD_TAR+"\t")        
//							  .append(CabTomaEstados.NIS_RAD+"\t")        
//							  .append(CabTomaEstados.COD_CNAE+"\t")       
//							  .append(CabTomaEstados.NUM_APA_OBS+"\t")    
//							  .append(CabTomaEstados.OBS+"\t")    
//							  .append(CabTomaEstados.LECT+"\t")           
//							  .append(CabTomaEstados.INC_1+"\t")          
//							  .append(CabTomaEstados.DET_1+"\t")          
//							  .append(CabTomaEstados.INC_2+"\t")          
//							  .append(CabTomaEstados.DET_2+"\t")          
//							  .append(CabTomaEstados.INC_3+"\t")          
//							  .append(CabTomaEstados.DET_3+"\t")          
//							  .append(CabTomaEstados.FECHA+"\t")          
//							  .append(CabTomaEstados.HORA+"\t")          
//							  .append(CabTomaEstados.COD_LECTOR+"\t")
//							  .append(CabTomaEstados.MEDIO+"\t")    							  							  
//							  .append(CabTomaEstados.TIP_LECTURA+"\t")							  
//							  .append(CabTomaEstados.NUM_ACT_INC+"\r\n");		
					  for(TomaEstados item : listaTomaEst) {						  
						  // Se eliminan los espacios por defecto para tipo dato String
						  trama.append(item.getCodigoOficinaComercial().trim()+"\t")
					  		.append(item.getAnno().trim()+"\t")
					  		.append(item.getMes().trim()+"\t")
					  		.append(item.getCicloComercial().trim()+"\t")
					  		.append(item.getRuta().trim()+"\t")
					  		.append(item.getNumeroIntenerario().trim()+"\t")
					  		.append(item.getAOL().trim()+"\t")
					  		.append(item.getNombreRazonSocial().trim()+"\t")
					  		.append(item.getNombreUrbanizacion().trim()+"\t")
					  		.append(item.getNombreCalle().trim()+"\t")
					  		.append(item.getComplementoDireccion().trim()+"\t")
					  		.append(item.getNumeroPuerta().trim()+"\t")
					  		.append(item.getManzanaLote().trim()+"\t")
					  		.append(item.getCGV().trim()+"\t")
					  		.append(item.getAccesoPredio().trim()+"\t")
					  		.append(item.getAccesoPuntoMedida().trim()+"\t")
					  		.append(item.getNumeroApa().trim()+"\t")
					  		.append(item.getLecturaAnterior().trim()+"\t")
					  		.append(item.getNumeroRuedasMedidor().trim()+"\t")
					  		.append(item.getCodigoTarifa().trim()+"\t")
					  		.append(item.getSuministro().trim()+"\t")
					  		.append(item.getCodigoCNAE().trim()+"\t")
					  		.append(item.getNumeroApaObs().trim()+"\t")
					  		.append(item.getObservacion().trim()+"\t")					  		
					  		.append(item.getLecturaMedidor().trim()+"\t")
					  		.append(item.getPrimerIncidente().trim()+"\t")
					  		.append(item.getDetallePrimerIncidente().trim()+"\t")
					  		.append(item.getSegundoIncidente().trim()+"\t")
					  		.append(item.getDetalleSegundoIncidente().trim()+"\t")
					  		.append(item.getTercerIncidente().trim()+"\t")
					  		.append(item.getDetalleTercerIncidente().trim()+"\t")					  		
					  		.append(item.getFechaLectura().trim()+"\t")
					  		.append(item.getHoraLectura().trim()+"\t")
					  		.append(item.getCodigoTomadorEstado().trim()+"\t")
					  		.append(item.getMedio().trim()+"\t")					  							  		
					  		.append(item.getTipoLectura().trim()+"\t")
					  		.append(item.getNumeroActaIncidencia().trim()+"\r\n");
					  }
				  }				  
			  }else {
				  this.error = new Error(0,"Error","No se obtuvo detalle de la carga de trabajo.");
			  }
			    break;
		  default:
			  this.error = new Error(0,"Error","Actividad de la carga de trabajo no definida.");			  			 			  			  			 
		}
		if(trama.length() > 0) {			
			try {
				archivoTrama = File.createTempFile(uidCarga, ".txt");			
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTrama))){
		              bw.write(trama.toString());
		          } catch (IOException e) {
		        	  logger.error("[AGC: CargaTrabajoDAOImpl - generarTrama()] - "+e.getMessage());
		          }										  				 
				archivoTrama.deleteOnExit();				
		    } catch(IOException e) {			
		     	 logger.error("[AGC: CargaTrabajoDAOImpl - generarTrama()] - "+e.getMessage());
		    }	
		}		
		return archivoTrama;	
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer cargarArchivoEjecucion(MultipartHttpServletRequest request, String uidActividad,
			String uidCargaTrabajo, String usuario, Integer codOficExt) {
		this.error = null;
	   // Obtengo del request el zip y sus propiedades
	   Iterator<String> itr = request.getFileNames();		
	   MultipartFile file = request.getFile(itr.next());
	   String fileType = request.getParameter("fileType");
	   String fileName = file.getOriginalFilename();
	  	
	  try {
		  // Creo archivo temporal para el zip 
		  File zipTemp = File.createTempFile("AGI-Temp-", fileName);
		  zipTemp.deleteOnExit();
		  BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(zipTemp));
		  stream.write(file.getBytes());
		  stream.close();
		  // Creo instancia del zip para su lectura
		  ZipFile zf = new ZipFile(zipTemp.getPath());			  
		  Enumeration entries = zf.entries();
		  if(zf.size() == 1) {
			  while (entries.hasMoreElements()) {
				  ZipEntry ze = (ZipEntry) entries.nextElement();
				  if(!ze.isDirectory()) {
					  if(ze.getName().toLowerCase().endsWith(".txt")) {
						  List<String> contenidoArchivo = new ArrayList<String>();
						  if (ze.getSize() > 0) {							 							  
							  BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
							  String line;
							  while ((line = br.readLine()) != null) {
								  contenidoArchivo.add(line);
							  }						  
							  br.close();			

								List<LineaFallaCarga> listaFallas = null;
								switch (uidActividad) {
								case Constantes.ACT_DIST_AVISO_COBRANZA:
									listaFallas = validarCargaDistAvisoCobranza(contenidoArchivo, uidCargaTrabajo,
											uidActividad, codOficExt);
									if (this.error != null) {
										return 0;
									}
									insertarDetalleDistAvisoCobranza(contenidoArchivo, listaFallas);																																					
									if(this.error != null) {
										return 0;
									}									
									actualizarDetalleDistAvisoCobranza(usuario, uidCargaTrabajo);	
									if(this.error != null) {
										return 0;
									}
									break;
								case Constantes.ACT_DIST_COMUNIC:
									listaFallas = validarCargaDistComunicaciones(contenidoArchivo, uidCargaTrabajo,
											uidActividad, codOficExt);
									if (this.error != null) {
										return 0;
									}
									insertarDetalleDistComunicaciones(contenidoArchivo, listaFallas);																																					
									if(this.error != null) {
										return 0;
									}									
									actualizarDetalleDistComunicaciones(usuario, uidCargaTrabajo);	
									if(this.error != null) {
										return 0;
									}																
									break;
								case Constantes.ACT_INSP_COMERCIAL:
									listaFallas = validarCargaInspComercial(contenidoArchivo, uidCargaTrabajo,
											uidActividad, codOficExt);
									if (this.error != null) {
										return 0;
									}
									insertarDetalleInspComercial(contenidoArchivo, listaFallas);																																					
									if(this.error != null) {
										return 0;
									}	
									
									actualizarDetalleInspComercial(usuario, uidCargaTrabajo);	
									if(this.error != null) {
										return 0;
									}
									
									break;
								case Constantes.ACT_MEDIDORES:
									listaFallas = validarCargaMedidores(contenidoArchivo, uidCargaTrabajo,
											uidActividad, codOficExt);
									if (this.error != null) {
										return 0;
									}
									insertarDetalleMedidores(contenidoArchivo, listaFallas);																																					
									if(this.error != null) {
										return 0;
									}									
									actualizarDetalleMedidores(usuario, uidCargaTrabajo);	
									if(this.error != null) {
										return 0;
									}
									break;
								case Constantes.ACT_TOMA_ESTADO:
									listaFallas = validarCargaTomEstado(contenidoArchivo, uidCargaTrabajo,
											uidActividad, codOficExt);
									if (this.error != null) {
										return 0;
									}
									insertarDetalleTomEstado(contenidoArchivo, listaFallas);																																					
									if(this.error != null) {
										return 0;
									}									
									actualizarDetalleTomEstado(usuario, uidCargaTrabajo);	
									if(this.error != null) {
										return 0;
									}
									break;
								default:
									this.error = new Error(0, "Error", Constantes.MESSAGE_ERROR.get(1003));
									return 0;
							}
						  
							if(listaFallas != null && listaFallas.size() > 0) {
							  StringBuilder lineasFalla = new StringBuilder();
							  lineasFalla.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1003)+"\r\n");
							  listaFallas.forEach(i -> lineasFalla.append(i.getErrores()+"\r\n"));
							  this.error = new Error(0, "Error", lineasFalla.toString());
						    }							  
						  }else {
							  this.error = new Error(0,"Error",Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
							  return 0;
						  }
					  }else {
						  this.error = new Error(0,"Error",Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1004));
						  return 0;
					  }
				  }else {
					  this.error = new Error(0,"Error",Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1005));
					  return 0;
				  }
			  }
		  }else if(zf.size() == 0) {
			  this.error = new Error(0,"Error",Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1006));
			  return 0;
		  }else {
			  this.error = new Error(0,"Error",Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1007));
			  return 0;
		  }		
		  zf.close();
		  if (zipTemp.exists()) {
			  zipTemp.delete();
		  }
	  }catch (IOException e) {
		  this.error = new Error(0,"Error",Constantes.MESSAGE_ERROR.get(9999));
		  logger.error("[AGC: CargaTrabajoDAOImpl - cargarArchivoEjecucion()] - "+e.getMessage());
	  }				
		return 1;
	}	
	
	@Autowired
	private ICargaTrabajoServicio cargaTrabajo;

	public List<LineaFallaCarga> validaCamposActividad(List<CargaTrabajoRequest> listaCargaTrabajoRequest,
			String actividad, Integer codOficExt) {
		String busca = "";
		ObjectMapper objectMapper = new ObjectMapper();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		List<LineaFallaCarga> listaFallas = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		final ObjectMapper mapper = new ObjectMapper();
		int numeroLinea = 0;
		try {
		switch (actividad){
			case Constantes.ACT_DIST_AVISO_COBRANZA:	
				for(CargaTrabajoRequest objRequest: listaCargaTrabajoRequest) {	
					numeroLinea++;
					String mensaje = "";					
					String registryActiviy = objectMapper.writeValueAsString(objRequest.getActividad());
					Map<String, Object> nuevoMapa = mapper.readValue(registryActiviy, HashMap.class);					
					Class<?> clase = DistribucionAvisoCobranza.class;
					// Valida campos numericos y fechas
					for(Map.Entry<String, Object> elemento : nuevoMapa.entrySet()) {						
						Field f = clase.getDeclaredField(elemento.getKey());						
						if(f.getType()==java.lang.Integer.class) {
							try {
								Integer r =Integer.parseInt(elemento.getValue().toString());				
							} catch (Exception e ) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}							
						}else if(f.getType()==java.sql.Date.class) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								java.util.Date d = sdf.parse(elemento.getValue().toString());
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), "31/12/2999");
							}
						}else if(f.getType()==java.lang.Double.class) {
							try {
								double d = Double.parseDouble(elemento.getValue().toString());  
							} catch(Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}
						}
					}			
					// Valida campos string
					String nuevaLinea = objectMapper.writeValueAsString(nuevoMapa);
					DistribucionAvisoCobranza valueObject = objectMapper.readValue(nuevaLinea, DistribucionAvisoCobranza.class);
					Set<ConstraintViolation<DistribucionAvisoCobranza>> violationsObject = validator.validate((DistribucionAvisoCobranza) valueObject);
					if (!violationsObject.isEmpty()) {									
						for (ConstraintViolation<DistribucionAvisoCobranza> violation: violationsObject) {
							mensaje = mensaje + violation.getPropertyPath().toString()+", ";
						}
					} else {
						// Valido codigo del operario
						if (!valueObject.getCodigoDistribuidor().isEmpty()
								&& valueObject.getCodigoDistribuidor() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(Integer.parseInt(valueObject.getCodigoDistribuidor()),
										codOficExt, actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Inicio
							try {
								validarOperadorDetCarga(valueObject.getCodigoDistribuidor(),valueObject.getCodigoCarga(),valueObject.getCampSuministro());
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Fin
						}
					}
					if(mensaje != "") {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(numeroLinea);
						lineaFalla.setErrores("Linea [" + numeroLinea + "] Error de formato en los campos [" + mensaje.substring(0,mensaje.length()-2)  + "]");
						listaFallas.add(lineaFalla);
					}										
				}									
				break;
			case Constantes.ACT_DIST_COMUNIC:
				for(CargaTrabajoRequest objRequest: listaCargaTrabajoRequest) {	
					numeroLinea++;
					String mensaje = "";					
					String registryActiviy = objectMapper.writeValueAsString(objRequest.getActividad());
					Map<String, Object> nuevoMapa = mapper.readValue(registryActiviy, HashMap.class);					
					Class<?> clase = DistribucionComunicaciones.class;
					// Valida campos numericos y fechas
					for(Map.Entry<String, Object> elemento : nuevoMapa.entrySet()) {						
						Field f = clase.getDeclaredField(elemento.getKey());						
						if(f.getType()==java.lang.Integer.class) {
							try {
								Integer r =Integer.parseInt(elemento.getValue().toString());				
							} catch (Exception e ) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}							
						}else if(f.getType()==java.sql.Date.class) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								java.util.Date d = sdf.parse(elemento.getValue().toString());
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), "31/12/2999");
							}
						}else if(f.getType()==java.lang.Double.class) {
							try {
								double d = Double.parseDouble(elemento.getValue().toString());  
							} catch(Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}
						}
					}			
					// Valida campos string
					String nuevaLinea = objectMapper.writeValueAsString(nuevoMapa);
					DistribucionComunicaciones valueObject = objectMapper.readValue(nuevaLinea, DistribucionComunicaciones.class);
					Set<ConstraintViolation<DistribucionComunicaciones>> violationsObject = validator.validate((DistribucionComunicaciones) valueObject);
					if (!violationsObject.isEmpty()) {									
						for (ConstraintViolation<DistribucionComunicaciones> violation: violationsObject) {
							mensaje = mensaje + violation.getPropertyPath().toString()+", ";
						}
					} else {
						// Valido codigo del operario
						if (!valueObject.getCodigoNotificadorVisita1().isEmpty()
								&& valueObject.getCodigoNotificadorVisita1() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(
										Integer.parseInt(valueObject.getCodigoNotificadorVisita1()), codOficExt,
										actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] "
										+ StringUtils.replace(e.getMessage(), "Personal", "Personal [COD_EMP1]"));

								listaFallas.add(lineaFalla);
							}
							//AVargas Inicio
							try {
								validarOperadorDetCarga(valueObject.getCodigoNotificadorVisita1(),valueObject.getCodigoCarga(),Integer.parseInt(valueObject.getNroSuministro()));
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Fin
						}
						if (!valueObject.getCodigoNotificadorVisita2().isEmpty()
								&& valueObject.getCodigoNotificadorVisita2() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(
										Integer.parseInt(valueObject.getCodigoNotificadorVisita2()), codOficExt,
										actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] "
										+ StringUtils.replace(e.getMessage(), "Personal", "Personal [COD_EMP2]"));
								listaFallas.add(lineaFalla);
							}
						}
					}
					if(mensaje != "") {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(numeroLinea);
						lineaFalla.setErrores("Linea [" + numeroLinea + "] Error de formato en los campos [" + mensaje.substring(0,mensaje.length()-2)  + "]");
						listaFallas.add(lineaFalla);
					}										
				}													
				break;
			case Constantes.ACT_INSP_COMERCIAL:				
				for(CargaTrabajoRequest objRequest: listaCargaTrabajoRequest) {	
					numeroLinea++;
					String mensaje = "";					
					String registryActiviy = objectMapper.writeValueAsString(objRequest.getActividad());
					Map<String, Object> nuevoMapa = mapper.readValue(registryActiviy, HashMap.class);					
					Class<?> clase = InspeccionesComerciales.class;
					// Valida campos numericos y fechas
					for(Map.Entry<String, Object> elemento : nuevoMapa.entrySet()) {						
						Field f = clase.getDeclaredField(elemento.getKey());						
						if(f.getType()==java.lang.Integer.class) {
							try {
								Integer r =Integer.parseInt(elemento.getValue().toString());				
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}							
						}else if(f.getType()==java.sql.Date.class) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								java.util.Date d = sdf.parse(elemento.getValue().toString());
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), "31/12/2999");
							}
						}else if(f.getType()==java.lang.Double.class) {							
							try {
								busca = elemento.getValue().toString(); 
								if(busca.indexOf(".")==0) {
									double d = Double.parseDouble(elemento.getValue().toString() + "0.00");
								} else {
									double d = Double.parseDouble(elemento.getValue().toString());
								}
							} catch(Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}
						}
					}			
					// Valida campos string
					String nuevaLinea = objectMapper.writeValueAsString(nuevoMapa);
					InspeccionesComerciales valueObject = objectMapper.readValue(nuevaLinea, InspeccionesComerciales.class);
					Set<ConstraintViolation<InspeccionesComerciales>> violationsObject = validator.validate((InspeccionesComerciales) valueObject);
					if (!violationsObject.isEmpty()) {									
						for (ConstraintViolation<InspeccionesComerciales> violation: violationsObject) {
							mensaje = mensaje + violation.getPropertyPath().toString()+", ";
						}
					} else {
						// Valido codigo del operario
						if (!valueObject.getCodEmpleado().isEmpty() && valueObject.getCodEmpleado() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(Integer.parseInt(valueObject.getCodEmpleado()),
										codOficExt, actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Inicio
							try {
								validarOperadorDetCarga(valueObject.getCodEmpleado(),valueObject.getCodigoCarga(),valueObject.getNisRad());
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Fin
						}
					}
					if(mensaje != "") {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(numeroLinea);
						lineaFalla.setErrores("Linea [" + numeroLinea + "] Error de formato en los campos [" + mensaje.substring(0,mensaje.length()-2)  + "]");
						listaFallas.add(lineaFalla);
					}										
				}
				break;
			case Constantes.ACT_MEDIDORES:
				for(CargaTrabajoRequest objRequest: listaCargaTrabajoRequest) {	
					numeroLinea++;
					String mensaje = "";					
					String registryActiviy = objectMapper.writeValueAsString(objRequest.getActividad());
					Map<String, Object> nuevoMapa = mapper.readValue(registryActiviy, HashMap.class);					
					Class<?> clase = Medidores.class;
					// Valida campos numericos y fechas
					for(Map.Entry<String, Object> elemento : nuevoMapa.entrySet()) {						
						Field f = clase.getDeclaredField(elemento.getKey());						
						if(f.getType()==java.lang.Integer.class) {
							try {
								Integer r =Integer.parseInt(elemento.getValue().toString());				
							} catch (Exception e ) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}							
						}else if(f.getType()==java.sql.Date.class) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								java.util.Date d = sdf.parse(elemento.getValue().toString());
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), "31/12/2999");
							}
						}else if(f.getType()==java.lang.Double.class) {
							try {
								double d = Double.parseDouble(elemento.getValue().toString());  
							} catch(Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}
						}
					}			
					// Valida campos string
					String nuevaLinea = objectMapper.writeValueAsString(nuevoMapa);
					Medidores valueObject = objectMapper.readValue(nuevaLinea, Medidores.class);
					Set<ConstraintViolation<Medidores>> violationsObject = validator.validate((Medidores) valueObject);
					if (!violationsObject.isEmpty()) {									
						for (ConstraintViolation<Medidores> violation: violationsObject) {
							mensaje = mensaje + violation.getPropertyPath().toString()+", ";
						}
					} else {
						// Valido codigo del operario
						if (!valueObject.getDniOperarioOT().isEmpty() && valueObject.getDniOperarioOT() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(Integer.parseInt(valueObject.getDniOperarioOT()),
										codOficExt, actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							
							//AVargas Inicio
							try {
								validarOperadorDetCarga(valueObject.getDniOperarioOT(),valueObject.getCodigoCarga(),Integer.parseInt(valueObject.getNroSuministroCliente()));
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Fin
						}
					}
					if(mensaje != "") {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(numeroLinea);
						lineaFalla.setErrores("Linea [" + numeroLinea + "] Error de formato en los campos [" + mensaje.substring(0,mensaje.length()-2)  + "]");
						listaFallas.add(lineaFalla);
					}										
				}
				break;
			case Constantes.ACT_TOMA_ESTADO:
				for(CargaTrabajoRequest objRequest: listaCargaTrabajoRequest) {	
					numeroLinea++;
					String mensaje = "";					
					String registryActiviy = objectMapper.writeValueAsString(objRequest.getActividad());
					Map<String, Object> nuevoMapa = mapper.readValue(registryActiviy, HashMap.class);					
					Class<?> clase = TomaEstados.class;
					// Valida campos numericos y fechas
					for(Map.Entry<String, Object> elemento : nuevoMapa.entrySet()) {						
						Field f = clase.getDeclaredField(elemento.getKey());						
						if(f.getType()==java.lang.Integer.class) {
							try {
								Integer r =Integer.parseInt(elemento.getValue().toString());				
							} catch (Exception e ) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}							
						}else if(f.getType()==java.sql.Date.class) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								java.util.Date d = sdf.parse(elemento.getValue().toString());
							} catch (Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), "31/12/2999");
							}
						}else if(f.getType()==java.lang.Double.class) {
							try {
								double d = Double.parseDouble(elemento.getValue().toString());  
							} catch(Exception e) {
								mensaje = mensaje + elemento.getKey() + ", ";
								nuevoMapa.put(elemento.getKey(), 0);
							}
						}
					}			
					// Valida campos string
					String nuevaLinea = objectMapper.writeValueAsString(nuevoMapa);
					TomaEstados valueObject = objectMapper.readValue(nuevaLinea, TomaEstados.class);
					Set<ConstraintViolation<TomaEstados>> violationsObject = validator.validate((TomaEstados) valueObject);
					if (!violationsObject.isEmpty()) {									
						for (ConstraintViolation<TomaEstados> violation: violationsObject) {
							mensaje = mensaje + violation.getPropertyPath().toString()+", ";
						}
					} else {
						// Valido codigo del operario
						if (!valueObject.getCodigoTomadorEstado().isEmpty()
								&& valueObject.getCodigoTomadorEstado() != " ") {
							try {
								validarCodigoOperadorDetalleCarga(
										Integer.parseInt(valueObject.getCodigoTomadorEstado()), codOficExt, actividad);
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							
							//AVargas Inicio
							try {
								validarOperadorDetCarga(valueObject.getCodigoTomadorEstado(), valueObject.getCodigoCarga(), Integer.parseInt(valueObject.getSuministro()));
							} catch (AgcException e) {
								lineaFalla = new LineaFallaCarga();
								lineaFalla.setLinea(numeroLinea);
								lineaFalla.setErrores("Linea [" + numeroLinea + "] " + e.getMessage());
								listaFallas.add(lineaFalla);
							}
							//AVargas Fin
						}
					}
					if(mensaje != "") {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(numeroLinea);
						lineaFalla.setErrores("Linea [" + numeroLinea + "] Error de formato en los campos [" + mensaje.substring(0,mensaje.length()-2)  + "]");
						listaFallas.add(lineaFalla);
					}										
				}
				break;	
			}
		 }catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999), e.getCause().getMessage());
			logger.error("[AGC: CargaTrabajoDAOImpl - validaCamposActividad()] - "+e.getMessage());
		 }
		return listaFallas;
	}

	public List<LineaFallaCarga> validarCargaDistAvisoCobranza(List<String> contenidoArchivo, String idCargaTrabajo,
			String idActividad, Integer codOficExt) {
		List<LineaFallaCarga> listaFallas = null;
		final ObjectMapper mapper = new ObjectMapper();
		List<CargaTrabajoRequest> lista = new ArrayList<CargaTrabajoRequest>();
		CargaTrabajoRequest cargaTrabajoRequest = null;
		Object actividad = null;
		Map<String, Object> map = null;
		int contador = 0;	
		List<LineaFallaCarga> listaRegistrosErroneos = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		try {
			Map<Integer, String> posicionCampo = new LinkedHashMap<>();
			for (Map.Entry<String, Map<Integer, String>> entry : CabeceraDetalleCarga.CAB_CARGA_DIS_AVIS.entrySet()) {
				posicionCampo.putAll(entry.getValue());
			}
			
			for(String line: contenidoArchivo) {				
				contador++;				
				String[] split = line.split("	");				
				// Valido cabecera				
				if(contador == 1) {
					int numeroCabecera = 0;
					boolean existeErrorCab = false;
					StringBuilder logCabecera = new StringBuilder();
					for(String i: split) {	
						numeroCabecera++;
						if(!CabeceraDetalleCarga.CAB_CARGA_DIS_AVIS.containsKey(i)
								|| CabeceraDetalleCarga.CAB_CARGA_DIS_AVIS.get(i).get(numeroCabecera) == null) {								
							existeErrorCab = true;
						}																			
					}
					if(numeroCabecera != CabeceraDetalleCarga.CAB_CARGA_DIS_AVIS.size()) {
						existeErrorCab = true;
					}
					if(existeErrorCab) {
						logCabecera.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1001)+"\r\n");
						CabeceraDetalleCarga.CAB_CARGA_DIS_AVIS.forEach((k,v) -> logCabecera.append(k+"\t"));
						this.error = new Error(0, "Error", logCabecera.toString());							
						return null;
					}							
				}else {
					if(split[0].equals(idCargaTrabajo)) {
						cargaTrabajoRequest = new CargaTrabajoRequest();																
						map = new HashMap<>();												
						map.put(posicionCampo.get(1), split[0]);
						map.put(posicionCampo.get(2), split[1]);
						map.put(posicionCampo.get(3), split[2]);
						map.put(posicionCampo.get(4), split[3]);
						map.put(posicionCampo.get(5), split[4]);
						map.put(posicionCampo.get(6), split[5]);
						map.put(posicionCampo.get(7), split[6]);
						actividad = mapper.convertValue(map, Object.class);						
						cargaTrabajoRequest.setActividad(actividad);					
						lista.add(cargaTrabajoRequest);		
					}else {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(contador-1);
						lineaFalla.setErrores("Linea [" + (contador-1) + "] Código de la Carga de Trabajo erróneo.");
						listaRegistrosErroneos.add(lineaFalla);
					}
				}
			}
			listaFallas = validaCamposActividad(lista, idActividad, codOficExt);

			if (!listaRegistrosErroneos.isEmpty() || listaRegistrosErroneos != null) {
				if (!listaFallas.isEmpty() || listaFallas != null) {
					for (LineaFallaCarga e : listaRegistrosErroneos) {
						listaFallas.add(e);
					}
				}else {
					listaFallas = listaRegistrosErroneos;
				}
			}
			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - validarCargaDistAvisoCobranza()] - "+e.getMessage());
		}
		return listaFallas;
	}			
	
private void insertarDetalleDistAvisoCobranza(List<String> contenidoArchivo, List<LineaFallaCarga> listaFallas) {
	List<Integer> lineasErroneas = new ArrayList<Integer>();
	if(listaFallas != null) {
		listaFallas.forEach(i -> lineasErroneas.add(i.getLinea()));
	}	
	try {				
		int numeroLinea = 0;
		StringBuilder builder = new StringBuilder();			
		for(String line: contenidoArchivo) {
			numeroLinea++;
			if(numeroLinea > 1 && !lineasErroneas.contains(numeroLinea-1)) {				
				builder.append(line+"\r");
			}						
		}
		
		if(builder.length() > 0) {		
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_INSERT_TEMP_DET_DIS_AVIS)
					.declareParameters(
							new SqlParameter("V_CLOB", OracleTypes.CLOB),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
						.addValue("V_CLOB", builder.toString());
			  
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
		  
			if(resultado == 0) {
			String mensaje = out.get("N_EJEC").toString();
			String mensajeInterno = (String)out.get("V_EJEC");					
			this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleDistAvisoCobranza()] - "+mensaje+":"+mensajeInterno);
			}
		}else {
			if(listaFallas.size() == 0 || listaFallas.isEmpty()) {
				this.error = new Error(0, "9999", Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
			}			
		}
	}catch(Exception e) {
		this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
		logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleDistAvisoCobranza()] - "+e.getMessage());
	}		
}
	
	public void actualizarDetalleDistAvisoCobranza(String usuario, String uidCargaTrabajo) {	
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_ACTUALIZA_DET_DIS_AVIS)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", uidCargaTrabajo)
					.addValue("V_A_V_USUMOD", usuario);
			
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleDistAvisoCobranza()] - "+mensaje+":"+mensajeInterno);
			}			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleDistAvisoCobranza()] - "+e.getMessage());
		}
	}

	public List<LineaFallaCarga> validarCargaDistComunicaciones(List<String> contenidoArchivo, String idCargaTrabajo,
			String idActividad, Integer codOficExt) {
		List<LineaFallaCarga> listaFallas = null;
		final ObjectMapper mapper = new ObjectMapper();
		List<CargaTrabajoRequest> lista = new ArrayList<CargaTrabajoRequest>();
		CargaTrabajoRequest cargaTrabajoRequest = null;
		Object actividad = null;
		Map<String, Object> map = null;
		int contador = 0;		
		List<LineaFallaCarga> listaRegistrosErroneos = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		try {
			Map<Integer, String> posicionCampo = new LinkedHashMap<>();
			for (Map.Entry<String, Map<Integer, String>> entry : CabeceraDetalleCarga.CAB_CARGA_DIS_COMU.entrySet()) {
				posicionCampo.putAll(entry.getValue());
			}
			
			for(String line: contenidoArchivo) {				
				contador++;		
				String[] split = line.split("	");				
				// Valido cabecera
				if(contador == 1) {
					int numeroCabecera = 0;					
					boolean existeErrorCab = false;
					StringBuilder logCabecera = new StringBuilder();
					for(String i: split) {	
						numeroCabecera++;
						if(!CabeceraDetalleCarga.CAB_CARGA_DIS_COMU.containsKey(i)
								|| CabeceraDetalleCarga.CAB_CARGA_DIS_COMU.get(i).get(numeroCabecera) == null) {								
							existeErrorCab = true;
						}																			
					}
					if(numeroCabecera != CabeceraDetalleCarga.CAB_CARGA_DIS_COMU.size()) {
						existeErrorCab = true;
					}
					if(existeErrorCab) {
						logCabecera.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1001)+"\r\n");
						CabeceraDetalleCarga.CAB_CARGA_DIS_COMU.forEach((k,v) -> logCabecera.append(k+"\t"));
						this.error = new Error(0, "Error", logCabecera.toString());							
						return null;
					}							
				}else {
					if(split[0].equals(idCargaTrabajo)) {
						cargaTrabajoRequest = new CargaTrabajoRequest();																
						map = new HashMap<>();												
						map.put(posicionCampo.get(1), split[0]);
						map.put(posicionCampo.get(2), split[1]);
						map.put(posicionCampo.get(3), split[2]);
						map.put(posicionCampo.get(4), split[3]);
						map.put(posicionCampo.get(5), split[4]);
						map.put(posicionCampo.get(6), split[5]);
						map.put(posicionCampo.get(7), split[6]);
						map.put(posicionCampo.get(8), split[7]);
						map.put(posicionCampo.get(9), split[8]);
						map.put(posicionCampo.get(10), split[9]);
						map.put(posicionCampo.get(11), split[10]);
						map.put(posicionCampo.get(12), split[11]);
						map.put(posicionCampo.get(13), split[12]);
						map.put(posicionCampo.get(14), split[13]);
						map.put(posicionCampo.get(15), split[14]);
						map.put(posicionCampo.get(16), split[15]);
						map.put(posicionCampo.get(17), split[16]);
						map.put(posicionCampo.get(18), split[17]);
						map.put(posicionCampo.get(19), split[18]);
						map.put(posicionCampo.get(20), split[19]);
						map.put(posicionCampo.get(21), split[20]);
						map.put(posicionCampo.get(22), split[21]);
	
						actividad = mapper.convertValue(map, Object.class);						
						cargaTrabajoRequest.setActividad(actividad);					
						lista.add(cargaTrabajoRequest);		
					}else {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(contador-1);
						lineaFalla.setErrores("Linea [" + (contador-1) + "] Código de la Carga de Trabajo erróneo.");
						listaRegistrosErroneos.add(lineaFalla);
					}
				}
			}
			listaFallas = validaCamposActividad(lista, idActividad, codOficExt);

			if (!listaRegistrosErroneos.isEmpty() || listaRegistrosErroneos != null) {
				if (!listaFallas.isEmpty() || listaFallas != null) {
					for (LineaFallaCarga e : listaRegistrosErroneos) {
						listaFallas.add(e);
					}
				}else {
					listaFallas = listaRegistrosErroneos;
				}
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - validarCargaDistComunicaciones()] - "+e.getMessage());
		}
		return listaFallas;
	}	
	
	private void insertarDetalleDistComunicaciones(List<String> contenidoArchivo, List<LineaFallaCarga> listaFallas) {
		List<Integer> lineasErroneas = new ArrayList<Integer>();
		if(listaFallas != null) {
			listaFallas.forEach(i -> lineasErroneas.add(i.getLinea()));
		}	
		try {				
			int numeroLinea = 0;
			StringBuilder builder = new StringBuilder();			
			for(String line: contenidoArchivo) {
				numeroLinea++;
				if(numeroLinea > 1 && !lineasErroneas.contains(numeroLinea-1)) {				
					builder.append(line+"\r");	
				}						
			}
			
			if(builder.length() > 0) {		
				this.jdbcCall = new SimpleJdbcCall(this.jdbc)
						.withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PACKAGE_EJECUCION)
						.withProcedureName(DbConstants.PRC_INSERT_TEMP_DET_DIS_COMU)
						.declareParameters(
								new SqlParameter("V_CLOB", OracleTypes.CLOB),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
				
				MapSqlParameterSource in = new MapSqlParameterSource()
							.addValue("V_CLOB", builder.toString());
				  
				Map<String, Object> out = this.jdbcCall.execute(in);
				Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			  
				if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");						
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleDistComunicaciones()] - "+mensaje+":"+mensajeInterno);
				}
			}else {
				if(listaFallas.size() == 0 || listaFallas.isEmpty()) {
					this.error = new Error(0, "9999", Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
				}			
			}									
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleDistComunicaciones()] - "+e.getMessage());
		}		
	}
	
	public void actualizarDetalleDistComunicaciones(String usuario, String uidCargaTrabajo) {	
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_ACTUALIZA_DET_DIS_COMU)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", uidCargaTrabajo)
					.addValue("V_A_V_USUMOD", usuario);
			
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleDistComunicaciones()] - "+mensaje+":"+mensajeInterno);
			}			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleDistComunicaciones()] - "+e.getMessage());
		}
	}

	public List<LineaFallaCarga> validarCargaInspComercial(List<String> contenidoArchivo, String idCargaTrabajo,
			String idActividad, Integer codOficExt) {
		List<LineaFallaCarga> listaFallas = null;
		final ObjectMapper mapper = new ObjectMapper();
		List<CargaTrabajoRequest> lista = new ArrayList<CargaTrabajoRequest>();
		CargaTrabajoRequest cargaTrabajoRequest = null;
		Object actividad = null;
		Map<String, Object> map = null;
		int contador = 0;		
		List<LineaFallaCarga> listaRegistrosErroneos = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		try {
			Map<Integer, String> posicionCampo = new LinkedHashMap<>();
			for (Map.Entry<String, Map<Integer, String>> entry : CabeceraDetalleCarga.CAB_CARGA_INS_COME.entrySet()) {
				posicionCampo.putAll(entry.getValue());
			}
			
			for(String line: contenidoArchivo) {				
				contador++;				
				String[] split = line.split("	");				
				// Valido cabecera
				if(contador == 1) {
					int numeroCabecera = 0;
					boolean existeErrorCab = false;
					StringBuilder logCabecera = new StringBuilder();
					for(String i: split) {	
						numeroCabecera++;
						if(!CabeceraDetalleCarga.CAB_CARGA_INS_COME.containsKey(i)
								|| CabeceraDetalleCarga.CAB_CARGA_INS_COME.get(i).get(numeroCabecera) == null) {								
							existeErrorCab = true;
						}																			
					}
					if(numeroCabecera != CabeceraDetalleCarga.CAB_CARGA_INS_COME.size()) {
						existeErrorCab = true;
					}
					if(existeErrorCab) {
						logCabecera.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1001)+"\r\n");
						CabeceraDetalleCarga.CAB_CARGA_INS_COME.forEach((k,v) -> logCabecera.append(k+"\t"));
						this.error = new Error(0, "Error", logCabecera.toString());							
						return null;
					}							
				}else {
					if(split[0].equals(idCargaTrabajo)) {
						cargaTrabajoRequest = new CargaTrabajoRequest();																
						map = new HashMap<>();												
						map.put(posicionCampo.get(1), split[0]);
						map.put(posicionCampo.get(2), split[1]);
						map.put(posicionCampo.get(3), split[2]);
						map.put(posicionCampo.get(4), split[3]);
						map.put(posicionCampo.get(5), split[4]);
						map.put(posicionCampo.get(6), split[5]);
						map.put(posicionCampo.get(7), split[6]);
						map.put(posicionCampo.get(8), split[7]);
						map.put(posicionCampo.get(9), split[8]);
						map.put(posicionCampo.get(10), split[9]);
						map.put(posicionCampo.get(11), split[10]);
						map.put(posicionCampo.get(12), split[11]);
						map.put(posicionCampo.get(13), split[12]);
						map.put(posicionCampo.get(14), split[13]);
						map.put(posicionCampo.get(15), split[14]);
						map.put(posicionCampo.get(16), split[15]);
						map.put(posicionCampo.get(17), split[16]);
						map.put(posicionCampo.get(18), split[17]);
						map.put(posicionCampo.get(19), split[18]);
						map.put(posicionCampo.get(20), split[19]);
						map.put(posicionCampo.get(21), split[20]);
						map.put(posicionCampo.get(22), split[21]);
						map.put(posicionCampo.get(23), split[22]);
						map.put(posicionCampo.get(24), split[23]);
						map.put(posicionCampo.get(25), split[24]);
						map.put(posicionCampo.get(26), split[25]);
						map.put(posicionCampo.get(27), split[26]);
						map.put(posicionCampo.get(28), split[27]);
						map.put(posicionCampo.get(29), split[28]);
						map.put(posicionCampo.get(30), split[29]);
						map.put(posicionCampo.get(31), split[30]);
						map.put(posicionCampo.get(32), split[31]);
						map.put(posicionCampo.get(33), split[32]);
						map.put(posicionCampo.get(34), split[33]);
						map.put(posicionCampo.get(35), split[34]);
						map.put(posicionCampo.get(36), split[35]);
						map.put(posicionCampo.get(37), split[36]);
						map.put(posicionCampo.get(38), split[37]);
						map.put(posicionCampo.get(39), split[38]);
						map.put(posicionCampo.get(40), split[39]);
						map.put(posicionCampo.get(41), split[40]);
						map.put(posicionCampo.get(42), split[41]);
						map.put(posicionCampo.get(43), split[42]);
						map.put(posicionCampo.get(44), split[43]);
						map.put(posicionCampo.get(45), split[44]);
						map.put(posicionCampo.get(46), split[45]);
						map.put(posicionCampo.get(47), split[46]);
						map.put(posicionCampo.get(48), split[47]);
						map.put(posicionCampo.get(49), split[48]);
						map.put(posicionCampo.get(50), split[49]);
						map.put(posicionCampo.get(51), split[50]);
						map.put(posicionCampo.get(52), split[51]);
						map.put(posicionCampo.get(53), split[52]);
						map.put(posicionCampo.get(54), split[53]);
						map.put(posicionCampo.get(55), split[54]);
						map.put(posicionCampo.get(56), split[55]);
						map.put(posicionCampo.get(57), split[56]);
						map.put(posicionCampo.get(58), split[57]);
						map.put(posicionCampo.get(59), split[58]);
						map.put(posicionCampo.get(60), split[59]);
						map.put(posicionCampo.get(61), split[60]);
						map.put(posicionCampo.get(62), split[61]);
						map.put(posicionCampo.get(63), split[62]);
						map.put(posicionCampo.get(64), split[63]);
						map.put(posicionCampo.get(65), split[64]);
						map.put(posicionCampo.get(66), split[65]);
						map.put(posicionCampo.get(67), split[66]);
						map.put(posicionCampo.get(68), split[67]);
						map.put(posicionCampo.get(69), split[68]);
						map.put(posicionCampo.get(70), split[69]);
						map.put(posicionCampo.get(71), split[70]);
						map.put(posicionCampo.get(72), split[71]);
						map.put(posicionCampo.get(73), split[72]);
						map.put(posicionCampo.get(74), split[73]);
						map.put(posicionCampo.get(75), split[74]);
						map.put(posicionCampo.get(76), split[75]);
						map.put(posicionCampo.get(77), split[76]);
						map.put(posicionCampo.get(78), split[77]);
						map.put(posicionCampo.get(79), split[78]);
						map.put(posicionCampo.get(80), split[79]);
						map.put(posicionCampo.get(81), split[80]);
						map.put(posicionCampo.get(82), split[81]);
						map.put(posicionCampo.get(83), split[82]);
						map.put(posicionCampo.get(84), split[83]);
						map.put(posicionCampo.get(85), split[84]);
						map.put(posicionCampo.get(86), split[85]);
						map.put(posicionCampo.get(87), split[86]);
						map.put(posicionCampo.get(88), split[87]);
						map.put(posicionCampo.get(89), split[88]);
						map.put(posicionCampo.get(90), split[89]);
						map.put(posicionCampo.get(91), split[90]);
						map.put(posicionCampo.get(92), split[91]);
						map.put(posicionCampo.get(93), split[92]);
						map.put(posicionCampo.get(94), split[93]);
						map.put(posicionCampo.get(95), split[94]);
						map.put(posicionCampo.get(96), split[95]);
						map.put(posicionCampo.get(97), split[96]);
						map.put(posicionCampo.get(98), split[97]);
						map.put(posicionCampo.get(99), split[98]);
						map.put(posicionCampo.get(100), split[99]);
						map.put(posicionCampo.get(101), split[100]);
						map.put(posicionCampo.get(102), split[101]);
						map.put(posicionCampo.get(103), split[102]);
						map.put(posicionCampo.get(104), split[103]);
						map.put(posicionCampo.get(105), split[104]);
						map.put(posicionCampo.get(106), split[105]);
						map.put(posicionCampo.get(107), split[106]);
						map.put(posicionCampo.get(108), split[107]);
						map.put(posicionCampo.get(109), split[108]);
						map.put(posicionCampo.get(110), split[109]);
						map.put(posicionCampo.get(111), split[110]);
						map.put(posicionCampo.get(112), split[111]);
						map.put(posicionCampo.get(113), split[112]);
						map.put(posicionCampo.get(114), split[113]);
						map.put(posicionCampo.get(115), split[114]);
						map.put(posicionCampo.get(116), split[115]);
						map.put(posicionCampo.get(117), split[116]);
						map.put(posicionCampo.get(118), split[117]);
						map.put(posicionCampo.get(119), split[118]);
						map.put(posicionCampo.get(120), split[119]);
						map.put(posicionCampo.get(121), split[120]);
						map.put(posicionCampo.get(122), split[121]);
						map.put(posicionCampo.get(123), split[122]);
						map.put(posicionCampo.get(124), split[123]);
						map.put(posicionCampo.get(125), split[124]);
						map.put(posicionCampo.get(126), split[125]);
						map.put(posicionCampo.get(127), split[126]);
						map.put(posicionCampo.get(128), split[127]);
						map.put(posicionCampo.get(129), split[128]);
						map.put(posicionCampo.get(130), split[129]);
						map.put(posicionCampo.get(131), split[130]);
						map.put(posicionCampo.get(132), split[131]);
						map.put(posicionCampo.get(133), split[132]);
						map.put(posicionCampo.get(134), split[133]);
						map.put(posicionCampo.get(135), split[134]);
						map.put(posicionCampo.get(136), split[135]);
						map.put(posicionCampo.get(137), split[136]);
						map.put(posicionCampo.get(138), split[137]);
						map.put(posicionCampo.get(139), split[138]);
						map.put(posicionCampo.get(140), split[139]);
						map.put(posicionCampo.get(141), split[140]);
						map.put(posicionCampo.get(142), split[141]);
						map.put(posicionCampo.get(143), split[142]);
						map.put(posicionCampo.get(144), split[143]);
						map.put(posicionCampo.get(145), split[144]);
						map.put(posicionCampo.get(146), split[145]);
						map.put(posicionCampo.get(147), split[146]);
						map.put(posicionCampo.get(148), split[147]);
						map.put(posicionCampo.get(149), split[148]);
						map.put(posicionCampo.get(150), split[149]);
						map.put(posicionCampo.get(151), split[150]);
						map.put(posicionCampo.get(152), split[151]);
						map.put(posicionCampo.get(153), split[152]);
						map.put(posicionCampo.get(154), split[153]);
						map.put(posicionCampo.get(155), split[154]);
						map.put(posicionCampo.get(156), split[155]);
						map.put(posicionCampo.get(157), split[156]);
						map.put(posicionCampo.get(158), split[157]);
						map.put(posicionCampo.get(159), split[158]);
						map.put(posicionCampo.get(160), split[159]);
						map.put(posicionCampo.get(161), split[160]);
						map.put(posicionCampo.get(162), split[161]);
						map.put(posicionCampo.get(163), split[162]);
						map.put(posicionCampo.get(164), split[163]);
						map.put(posicionCampo.get(165), split[164]);
						map.put(posicionCampo.get(166), split[165]);
						map.put(posicionCampo.get(167), split[166]);
						map.put(posicionCampo.get(168), split[167]);
						map.put(posicionCampo.get(169), split[168]);
						map.put(posicionCampo.get(170), split[169]);
						map.put(posicionCampo.get(171), split[170]);
						map.put(posicionCampo.get(172), split[171]);
						map.put(posicionCampo.get(173), split[172]);
						map.put(posicionCampo.get(174), split[173]);
						map.put(posicionCampo.get(175), split[174]);
	
						actividad = mapper.convertValue(map, Object.class);						
						cargaTrabajoRequest.setActividad(actividad);					
						lista.add(cargaTrabajoRequest);		
					}else {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(contador-1);
						lineaFalla.setErrores("Linea [" + (contador-1) + "] Código de la Carga de Trabajo erróneo.");
						listaRegistrosErroneos.add(lineaFalla);
					}
				}
			}
			listaFallas = validaCamposActividad(lista, idActividad, codOficExt);

			if (!listaRegistrosErroneos.isEmpty() || listaRegistrosErroneos != null) {
				if (!listaFallas.isEmpty() || listaFallas != null) {
					for (LineaFallaCarga e : listaRegistrosErroneos) {
						listaFallas.add(e);
					}
				}else {
					listaFallas = listaRegistrosErroneos;
				}
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - validarCargaInspComercial()] - "+e.getMessage());
		}
		return listaFallas;
	}	
	
	private void insertarDetalleInspComercial(List<String> contenidoArchivo, List<LineaFallaCarga> listaFallas) {
		List<Integer> lineasErroneas = new ArrayList<Integer>();
		if(listaFallas != null) {
			listaFallas.forEach(i -> lineasErroneas.add(i.getLinea()));
		}	
		try {				
			int numeroLinea = 0;
			StringBuilder builder = new StringBuilder();			
			for(String line: contenidoArchivo) {
				numeroLinea++;
				if(numeroLinea > 1 && !lineasErroneas.contains(numeroLinea-1)) {				
					builder.append(line+"\r");	
				}						
			}
			
			if(builder.length() > 0) {		
				this.jdbcCall = new SimpleJdbcCall(this.jdbc)
						.withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PACKAGE_EJECUCION)
						.withProcedureName(DbConstants.PRC_INSERT_TEMP_DET_INS_COME)
						.declareParameters(
								new SqlParameter("V_CLOB", OracleTypes.CLOB),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
				
				MapSqlParameterSource in = new MapSqlParameterSource()
							.addValue("V_CLOB", builder.toString());
				  
				Map<String, Object> out = this.jdbcCall.execute(in);
				Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
				if(resultado == 0) {
					String mensaje = out.get("N_EJEC").toString();
					String mensajeInterno = (String)out.get("V_EJEC");						
					this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
					logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleInspComercial()] - "+mensaje+":"+mensajeInterno);
				}
			}else {
				if(listaFallas.size() == 0 || listaFallas.isEmpty()) {
					this.error = new Error(0, "9999", Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
				}			
			}									
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleInspComercial()] - "+e.getMessage());
		}		
	}
	
	public void actualizarDetalleInspComercial(String usuario, String uidCargaTrabajo) {	
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_ACTUALIZA_DET_INS_COME)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", uidCargaTrabajo)
					.addValue("V_A_V_USUMOD", usuario);
			
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleInspComercial()] - "+mensaje+":"+mensajeInterno);
			}			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleInspComercial()] - "+e.getMessage());
		}
	}

	public List<LineaFallaCarga> validarCargaMedidores(List<String> contenidoArchivo, String idCargaTrabajo,
			String idActividad, Integer codOficExt) {
		List<LineaFallaCarga> listaFallas = null;
		final ObjectMapper mapper = new ObjectMapper();
		List<CargaTrabajoRequest> lista = new ArrayList<CargaTrabajoRequest>();
		CargaTrabajoRequest cargaTrabajoRequest = null;
		Object actividad = null;
		Map<String, Object> map = null;
		int contador = 0;		
		List<LineaFallaCarga> listaRegistrosErroneos = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		try {
			Map<Integer, String> posicionCampo = new LinkedHashMap<>();
			for (Map.Entry<String, Map<Integer, String>> entry : CabeceraDetalleCarga.CAB_CARGA_MEDIDORE.entrySet()) {
				posicionCampo.putAll(entry.getValue());
			}
			
			for(String line: contenidoArchivo) {				
				contador++;				
				String[] split = line.split("	");				
				// Valido cabecera
				if(contador == 1) {
					int numeroCabecera = 0;
					boolean existeErrorCab = false;
					StringBuilder logCabecera = new StringBuilder();
					for(String i: split) {	
						numeroCabecera++;
						if(!CabeceraDetalleCarga.CAB_CARGA_MEDIDORE.containsKey(i)
								|| CabeceraDetalleCarga.CAB_CARGA_MEDIDORE.get(i).get(numeroCabecera) == null) {								
							existeErrorCab = true;
						}																			
					}
					if(numeroCabecera != CabeceraDetalleCarga.CAB_CARGA_MEDIDORE.size()) {
						existeErrorCab = true;
					}
					if(existeErrorCab) {
						logCabecera.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1001)+"\r\n");
						CabeceraDetalleCarga.CAB_CARGA_MEDIDORE.forEach((k,v) -> logCabecera.append(k+"\t"));
						this.error = new Error(0, "Error", logCabecera.toString());							
						return null;
					}							
				}else {
					if(split[0].equals(idCargaTrabajo)) {
						cargaTrabajoRequest = new CargaTrabajoRequest();																
						map = new HashMap<>();												
						map.put(posicionCampo.get(1), split[0]);
						map.put(posicionCampo.get(2), split[1]);
						map.put(posicionCampo.get(3), split[2]);
						map.put(posicionCampo.get(4), split[3]);
						map.put(posicionCampo.get(5), split[4]);
						map.put(posicionCampo.get(6), split[5]);
						map.put(posicionCampo.get(7), split[6]);
						map.put(posicionCampo.get(8), split[7]);
						map.put(posicionCampo.get(9), split[8]);
						map.put(posicionCampo.get(10), split[9]);
						map.put(posicionCampo.get(11), split[10]);
						map.put(posicionCampo.get(12), split[11]);
						map.put(posicionCampo.get(13), split[12]);
						map.put(posicionCampo.get(14), split[13]);
						map.put(posicionCampo.get(15), split[14]);
						map.put(posicionCampo.get(16), split[15]);
						map.put(posicionCampo.get(17), split[16]);
						map.put(posicionCampo.get(18), split[17]);
						map.put(posicionCampo.get(19), split[18]);
						map.put(posicionCampo.get(20), split[19]);
						map.put(posicionCampo.get(21), split[20]);
						map.put(posicionCampo.get(22), split[21]);
						map.put(posicionCampo.get(23), split[22]);
						map.put(posicionCampo.get(24), split[23]);
						map.put(posicionCampo.get(25), split[24]);
						map.put(posicionCampo.get(26), split[25]);
						map.put(posicionCampo.get(27), split[26]);
						map.put(posicionCampo.get(28), split[27]);
						map.put(posicionCampo.get(29), split[28]);
						map.put(posicionCampo.get(30), split[29]);
						map.put(posicionCampo.get(31), split[30]);
						map.put(posicionCampo.get(32), split[31]);
						map.put(posicionCampo.get(33), split[32]);
						map.put(posicionCampo.get(34), split[33]);
						map.put(posicionCampo.get(35), split[34]);
						map.put(posicionCampo.get(36), split[35]);
						map.put(posicionCampo.get(37), split[36]);
						map.put(posicionCampo.get(38), split[37]);
						map.put(posicionCampo.get(39), split[38]);
						map.put(posicionCampo.get(40), split[39]);
						map.put(posicionCampo.get(41), split[40]);
						map.put(posicionCampo.get(42), split[41]);
						map.put(posicionCampo.get(43), split[42]);
						map.put(posicionCampo.get(44), split[43]);
						map.put(posicionCampo.get(45), split[44]);
						map.put(posicionCampo.get(46), split[45]);
						map.put(posicionCampo.get(47), split[46]);
						map.put(posicionCampo.get(48), split[47]);
						map.put(posicionCampo.get(49), split[48]);
						map.put(posicionCampo.get(50), split[49]);
						map.put(posicionCampo.get(51), split[50]);
						map.put(posicionCampo.get(52), split[51]);
						map.put(posicionCampo.get(53), split[52]);
						map.put(posicionCampo.get(54), split[53]);
						map.put(posicionCampo.get(55), split[54]);
						map.put(posicionCampo.get(56), split[55]);
						map.put(posicionCampo.get(57), split[56]);
						map.put(posicionCampo.get(58), split[57]);
						map.put(posicionCampo.get(59), split[58]);
						map.put(posicionCampo.get(60), split[59]);
						map.put(posicionCampo.get(61), split[60]);
						map.put(posicionCampo.get(62), split[61]);
						map.put(posicionCampo.get(63), split[62]);
						map.put(posicionCampo.get(64), split[63]);
						map.put(posicionCampo.get(65), split[64]);
						map.put(posicionCampo.get(66), split[65]);
						map.put(posicionCampo.get(67), split[66]);
						map.put(posicionCampo.get(68), split[67]);
						map.put(posicionCampo.get(69), split[68]);
						map.put(posicionCampo.get(70), split[69]);
						map.put(posicionCampo.get(71), split[70]);
						map.put(posicionCampo.get(72), split[71]);
						map.put(posicionCampo.get(73), split[72]);
						map.put(posicionCampo.get(74), split[73]);
						map.put(posicionCampo.get(75), split[74]);
						map.put(posicionCampo.get(76), split[75]);
						map.put(posicionCampo.get(77), split[76]);
						map.put(posicionCampo.get(78), split[77]);
						map.put(posicionCampo.get(79), split[78]);
						map.put(posicionCampo.get(80), split[79]);
						map.put(posicionCampo.get(81), split[80]);
						map.put(posicionCampo.get(82), split[81]);
						map.put(posicionCampo.get(83), split[82]);
						map.put(posicionCampo.get(84), split[83]);
						map.put(posicionCampo.get(85), split[84]);
						map.put(posicionCampo.get(86), split[85]);
						map.put(posicionCampo.get(87), split[86]);
						map.put(posicionCampo.get(88), split[87]);
						map.put(posicionCampo.get(89), split[88]);
						map.put(posicionCampo.get(90), split[89]);
						map.put(posicionCampo.get(91), split[90]);
						map.put(posicionCampo.get(92), split[91]);
						map.put(posicionCampo.get(93), split[92]);
						map.put(posicionCampo.get(94), split[93]);
						map.put(posicionCampo.get(95), split[94]);
						map.put(posicionCampo.get(96), split[95]);
						map.put(posicionCampo.get(97), split[96]);
						map.put(posicionCampo.get(98), split[97]);
						map.put(posicionCampo.get(99), split[98]);
						map.put(posicionCampo.get(100), split[99]);
						map.put(posicionCampo.get(101), split[100]);
						map.put(posicionCampo.get(102), split[101]);
						map.put(posicionCampo.get(103), split[102]);
						map.put(posicionCampo.get(104), split[103]);
						map.put(posicionCampo.get(105), split[104]);
						map.put(posicionCampo.get(106), split[105]);
						map.put(posicionCampo.get(107), split[106]);
						map.put(posicionCampo.get(108), split[107]);
						map.put(posicionCampo.get(109), split[108]);
						map.put(posicionCampo.get(110), split[109]);
						map.put(posicionCampo.get(111), split[110]);
						map.put(posicionCampo.get(112), split[111]);
						map.put(posicionCampo.get(113), split[112]);
						map.put(posicionCampo.get(114), split[113]);
						map.put(posicionCampo.get(115), split[114]);
						map.put(posicionCampo.get(116), split[115]);
						map.put(posicionCampo.get(117), split[116]);					
	
						actividad = mapper.convertValue(map, Object.class);						
						cargaTrabajoRequest.setActividad(actividad);					
						lista.add(cargaTrabajoRequest);		
					}else {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(contador-1);
						lineaFalla.setErrores("Linea [" + (contador-1) + "] Código de la Carga de Trabajo erróneo.");
						listaRegistrosErroneos.add(lineaFalla);
					}
				}
			}
			listaFallas = validaCamposActividad(lista, idActividad, codOficExt);

			if (!listaRegistrosErroneos.isEmpty() || listaRegistrosErroneos != null) {
				if (!listaFallas.isEmpty() || listaFallas != null) {
					for (LineaFallaCarga e : listaRegistrosErroneos) {
						listaFallas.add(e);
					}
				}else {
					listaFallas = listaRegistrosErroneos;
				}
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - validarCargaMedidores()] - "+e.getMessage());
		}
		return listaFallas;
	}	
	
	private void insertarDetalleMedidores(List<String> contenidoArchivo, List<LineaFallaCarga> listaFallas) {
		List<Integer> lineasErroneas = new ArrayList<Integer>();
		if(listaFallas != null) {
			listaFallas.forEach(i -> lineasErroneas.add(i.getLinea()));
		}	
		try {				
			int numeroLinea = 0;
			StringBuilder builder = new StringBuilder();			
			for(String line: contenidoArchivo) {
				numeroLinea++;
				if(numeroLinea > 1 && !lineasErroneas.contains(numeroLinea-1)) {				
					builder.append(line+"\r");	
				}						
			}
			
			if(builder.length() > 0) {		
				this.jdbcCall = new SimpleJdbcCall(this.jdbc)
						.withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PACKAGE_EJECUCION)
						.withProcedureName(DbConstants.PRC_INSERT_TEMP_DET_MEDIDORE)
						.declareParameters(
								new SqlParameter("V_CLOB", OracleTypes.CLOB),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
				
				MapSqlParameterSource in = new MapSqlParameterSource()
							.addValue("V_CLOB", builder.toString());
				  
				Map<String, Object> out = this.jdbcCall.execute(in);
				Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			  
				if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");						
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleMedidores()] - "+mensaje+":"+mensajeInterno);
				}
			}else {
				if(listaFallas.size() == 0 || listaFallas.isEmpty()) {
					this.error = new Error(0, "9999", Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
				}			
			}									
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleMedidores()] - "+e.getMessage());
		}		
	}
	
	public void actualizarDetalleMedidores(String usuario, String uidCargaTrabajo) {	
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_ACTUALIZA_DET_MEDIDORE)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", uidCargaTrabajo)
					.addValue("V_A_V_USUMOD", usuario);
			
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleMedidores()] - "+mensaje+":"+mensajeInterno);
			}			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleMedidores()] - "+e.getMessage());
		}
	}

	public List<LineaFallaCarga> validarCargaTomEstado(List<String> contenidoArchivo, String idCargaTrabajo,
			String idActividad, Integer codOficExt) {
		List<LineaFallaCarga> listaFallas = null;
		final ObjectMapper mapper = new ObjectMapper();
		List<CargaTrabajoRequest> lista = new ArrayList<CargaTrabajoRequest>();
		CargaTrabajoRequest cargaTrabajoRequest = null;
		Object actividad = null;
		Map<String, Object> map = null;
		int contador = 0;		
		List<LineaFallaCarga> listaRegistrosErroneos = new ArrayList<LineaFallaCarga>();
		LineaFallaCarga lineaFalla = null;
		try {
			Map<Integer, String> posicionCampo = new LinkedHashMap<>();
			for (Map.Entry<String, Map<Integer, String>> entry : CabeceraDetalleCarga.CAB_CARGA_TOM_ESTA.entrySet()) {
				posicionCampo.putAll(entry.getValue());
			}
			
			for(String line: contenidoArchivo) {				
				contador++;				
				String[] split = line.split("	");				
				// Valido cabecera
								
				if(contador == 1) {
					int numeroCabecera = 0;
					boolean existeErrorCab = false;
					StringBuilder logCabecera = new StringBuilder();
					for(String i: split) {	
						numeroCabecera++;
						if(!CabeceraDetalleCarga.CAB_CARGA_TOM_ESTA.containsKey(i)
								|| CabeceraDetalleCarga.CAB_CARGA_TOM_ESTA.get(i).get(numeroCabecera) == null) {								
							existeErrorCab = true;
						}																			
					}
					if(numeroCabecera != CabeceraDetalleCarga.CAB_CARGA_TOM_ESTA.size()) {
						existeErrorCab = true;
					}
					if(existeErrorCab) {
						logCabecera.append(Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1001)+"\r\n");
						CabeceraDetalleCarga.CAB_CARGA_TOM_ESTA.forEach((k,v) -> logCabecera.append(k+"\t"));
						this.error = new Error(0, "Error", logCabecera.toString());							
						return null;
					}							
				}else {
					if(split[0].equals(idCargaTrabajo)) {
						cargaTrabajoRequest = new CargaTrabajoRequest();																
						map = new HashMap<>();												
						map.put(posicionCampo.get(1), split[0]);
						map.put(posicionCampo.get(2), split[1]);
						map.put(posicionCampo.get(3), split[2]);
						map.put(posicionCampo.get(4), split[3]);
						map.put(posicionCampo.get(5), split[4]);
						map.put(posicionCampo.get(6), split[5]);
						map.put(posicionCampo.get(7), split[6]);
						map.put(posicionCampo.get(8), split[7]);
						map.put(posicionCampo.get(9), split[8]);
						map.put(posicionCampo.get(10), split[9]);
						map.put(posicionCampo.get(11), split[10]);
						map.put(posicionCampo.get(12), split[11]);
						map.put(posicionCampo.get(13), split[12]);
						map.put(posicionCampo.get(14), split[13]);
						map.put(posicionCampo.get(15), split[14]);
						map.put(posicionCampo.get(16), split[15]);										
	
						actividad = mapper.convertValue(map, Object.class);						
						cargaTrabajoRequest.setActividad(actividad);					
						lista.add(cargaTrabajoRequest);		
					}else {
						lineaFalla = new LineaFallaCarga();
						lineaFalla.setLinea(contador-1);
						lineaFalla.setErrores("Linea [" + (contador-1) + "] Código de la Carga de Trabajo erróneo.");
						listaRegistrosErroneos.add(lineaFalla);
					}
				}
			}
			listaFallas = validaCamposActividad(lista, idActividad, codOficExt);

			if (!listaRegistrosErroneos.isEmpty() || listaRegistrosErroneos != null) {
				if (!listaFallas.isEmpty() || listaFallas != null) {
					for (LineaFallaCarga e : listaRegistrosErroneos) {
						listaFallas.add(e);
					}
				}else {
					listaFallas = listaRegistrosErroneos;
				}
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - validarCargaTomEstado()] - "+e.getMessage());
		}
		return listaFallas;
	}	
	
	private void insertarDetalleTomEstado(List<String> contenidoArchivo, List<LineaFallaCarga> listaFallas) {
		List<Integer> lineasErroneas = new ArrayList<Integer>();
		if(listaFallas != null) {
			listaFallas.forEach(i -> lineasErroneas.add(i.getLinea()));
		}	
		try {				
			int numeroLinea = 0;
			StringBuilder builder = new StringBuilder();			
			for(String line: contenidoArchivo) {
				numeroLinea++;
				if(numeroLinea > 1 && !lineasErroneas.contains(numeroLinea-1)) {				
					builder.append(line+"\r");
				}						
			}
			
			if(builder.length() > 0) {		
				this.jdbcCall = new SimpleJdbcCall(this.jdbc)
						.withSchemaName(DbConstants.DBSCHEMA)
						.withCatalogName(DbConstants.PACKAGE_EJECUCION)
						.withProcedureName(DbConstants.PRC_INSERT_TEMP_DET_TOM_ESTA)
						.declareParameters(
								new SqlParameter("V_CLOB", OracleTypes.CLOB),
								new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
								new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
								new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
				
				MapSqlParameterSource in = new MapSqlParameterSource()
							.addValue("V_CLOB", builder.toString());
				  
				Map<String, Object> out = this.jdbcCall.execute(in);
				Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			  
				if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");						
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleTomEstado()] - "+mensaje+":"+mensajeInterno);
				}
			}else {
				if(listaFallas.size() == 0 || listaFallas.isEmpty()) {
					this.error = new Error(0, "9999", Constantes.MESSAGE_CARGA_ARCHIVO_EJECUCION.get(1009));
				}			
			}									
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - insertarDetalleTomEstado()] - "+e.getMessage());
		}		
	}
	
	public void actualizarDetalleTomEstado(String usuario, String uidCargaTrabajo) {	
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_ACTUALIZA_DET_TOM_ESTA)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", uidCargaTrabajo)
					.addValue("V_A_V_USUMOD", usuario);
			
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 0) {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleTomEstado()] - "+mensaje+":"+mensajeInterno);
			}			
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - actualizarDetalleTomEstado()] - "+e.getMessage());
		}
	}

	private Error getError(String[] parameters){
		Error error = null;
		if (parameters.length == 2) {
			error = Constantes.obtenerError(Integer.parseInt(parameters[0]), parameters[1]);
		}
		return error;
	}

	@Override
	public Adjunto buscarAdjuntoDetalleCarga(String idCarga, Integer idRegistro, String nombreArchivo) {
		this.error = null;
		Adjunto adjunto = new Adjunto();
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
					.withProcedureName(DbConstants.PRC_BUSQ_ADJU_CARGA_TRAB_DET)
					.declareParameters(
							new SqlParameter("V_V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDREG", OracleTypes.NUMBER),
							new SqlParameter("V_V_NOMBRE_ADJU", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_V_IDCARGA", idCarga)
					.addValue("V_N_IDREG", idRegistro)
					.addValue("V_V_NOMBRE_ADJU", nombreArchivo);
						
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");
				for(Map<String, Object> map : lista) {
					adjunto.setUidCarga((String)map.get("V_IDCARGA"));
					adjunto.setUidRegistro(((BigDecimal)map.get("N_IDREG")).intValue());
					adjunto.setUidAdjunto(((BigDecimal)map.get("N_IDARCHI")).intValue());
					adjunto.setNombre((String)map.get("V_NOMBRADJUN"));
					adjunto.setExtension((String)map.get("V_EXTENSION"));
					adjunto.setRuta((String)map.get("V_RUTAADJU"));
				}				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");				
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - buscarAdjuntoDetalleCarga()] - "+e.getMessage());
		}
		return adjunto;
	}
	
	@Override
	public TamanioAdjuntos obtenerSize() {
		TamanioAdjuntos sizeAdjunto = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_CARGA_ADJUNTOS)
					.withProcedureName(DbConstants.PRC_SIZE_ADJUNTOS)
					.declareParameters(
							new SqlOutParameter("N_MAXSIZEPDF", OracleTypes.VARCHAR),
							new SqlOutParameter("N_MAXSIZEJPG", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			
			MapSqlParameterSource in = new MapSqlParameterSource();
						
			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			if(resultado == 1) {
				sizeAdjunto = new TamanioAdjuntos();
				String sizeMaxPDF = (String) out.get("N_SIZEMAXPDF");
				String sizeMaxJPG = (String) out.get("N_SIZEMAXJPG");
				try {
					sizeAdjunto.setSizeMaxPDF(Double.parseDouble(sizeMaxPDF));
					sizeAdjunto.setSizeMaxJPG(Double.parseDouble(sizeMaxJPG));
				} catch (NumberFormatException e) {
					sizeAdjunto = null;
					this.error = new Error(0,"Error", "El valor de los parámetros Tamaño Maximo Archivo PDF/JPG no tienen un valor numérico.");
				}				
			}else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String)out.get("V_EJEC");					
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: CargaTrabajoDAOImpl - obtenerSize()] - "+mensajeInterno);
			}
		}catch(Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: CargaTrabajoDAOImpl - obtenerSize()] - "+e.getMessage());
		}
		return sizeAdjunto;		
	}

	public void validarCodigoOperadorDetalleCarga(Integer codEmpl, Integer codOficExt, String uidActividad) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_VALIDA_CODIGO_OPERADOR_DET_CARGA)
					.declareParameters(new SqlParameter("I_N_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("I_N_COD_OFIC_EXT", OracleTypes.NUMBER),
							new SqlParameter("I_V_ID_ACTI", OracleTypes.VARCHAR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource().addValue("I_N_COD_EMP", codEmpl)
					.addValue("I_N_COD_OFIC_EXT", codOficExt).addValue("I_V_ID_ACTI", uidActividad);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			if (e.getError().getCodigo() == -20001) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE001, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20002) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE002, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20003) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE003, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20004) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE004, "{{valor}}",
						codEmpl.toString()));
			} else {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public void validarOperadorDetCarga(String codEmpl, String idCarga, Integer idSuministro) {
		Map<String, Object> out = null;
		Integer resultadoEjecucion = 0;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_EJECUCION)
					.withProcedureName(DbConstants.PRC_VALIDA_OPERADOR_DET_CARGA)
					.declareParameters(new SqlParameter("I_V_COD_EMP", OracleTypes.VARCHAR),
							new SqlParameter("I_V_CARGA", OracleTypes.VARCHAR),
							new SqlParameter("I_N_NIS", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource().addValue("I_V_COD_EMP", codEmpl)
					.addValue("I_V_CARGA", idCarga)
					.addValue("I_N_NIS", idSuministro);

			out = this.jdbcCall.execute(in);
			resultadoEjecucion = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultadoEjecucion == 0) {
				Integer codigoErrorBD = ((BigDecimal) out.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) out.get("O_V_EJEC");
				throw new AgcException(mensajeErrorBD, new Error(codigoErrorBD, mensajeErrorBD));
			}
		} catch (AgcException e) {
			if (e.getError().getCodigo() == -20001) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE001, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20002) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE002, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20003) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE003, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20004) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE004, "{{valor}}",
						codEmpl.toString()));
			} else if (e.getError().getCodigo() == -20005) {
				throw new AgcException(StringUtils.replace(validacionCodigoOperadorDetalleCarga.MSE005, "{{valor}}",
						codEmpl.toString()));	
			} else {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}



}


