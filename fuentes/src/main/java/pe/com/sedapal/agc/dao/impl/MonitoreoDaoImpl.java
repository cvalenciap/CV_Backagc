package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import pe.com.sedapal.agc.dao.IMonitoreoDAO;
import pe.com.sedapal.agc.mapper.ParametrosMonitoreoMapper;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.DuracionParametro;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.MonitoreoCab;
import pe.com.sedapal.agc.model.MonitoreoCabecera;
import pe.com.sedapal.agc.model.MonitoreoDet;
import pe.com.sedapal.agc.model.MonitoreoDetalleCR;
import pe.com.sedapal.agc.model.MonitoreoDetalleDA;
import pe.com.sedapal.agc.model.MonitoreoDetalleDC;
import pe.com.sedapal.agc.model.MonitoreoDetalleIC;
import pe.com.sedapal.agc.model.MonitoreoDetalleME;
import pe.com.sedapal.agc.model.MonitoreoDetalleSO;
import pe.com.sedapal.agc.model.MonitoreoDetalleTE;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.model.ReprogramacionDetalle;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Zona;
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
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;
import pe.com.sedapal.agc.util.DbConstants;

//@Service
@Repository
public class MonitoreoDaoImpl implements IMonitoreoDAO {

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	private Error error;

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> listarAsignacionTrabajo(MonitoreoRequest requestMonitoreo) {
		Map<String, Object> listaCargaTrab = new HashMap<String, Object>();
		Map<String, Object> out = null;
		Map<String, Object> detalle = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION).withProcedureName(DbConstants.PRC_LIST_ASIG_TRAB)
					.declareParameters(new SqlParameter("V_N_IDEMPR", OracleTypes.NUMBER),
							new SqlParameter("V_C_IDACTI", OracleTypes.VARCHAR),
							new SqlParameter("V_C_ESTADO", OracleTypes.VARCHAR),
							new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_ASIG_INI", OracleTypes.DATE),
							new SqlParameter("V_D_FEC_ASIG_FIN", OracleTypes.DATE),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMPR",
							(requestMonitoreo.getContratista() != null ? requestMonitoreo.getContratista().getCodigo()
									: null))
					.addValue("V_C_IDACTI",
							(requestMonitoreo.getActividad() != null ? requestMonitoreo.getActividad().getCodigo()
									: null))
					.addValue("V_C_ESTADO",
							(requestMonitoreo.getEstado() != null ? requestMonitoreo.getEstado().getId() : null))
					.addValue("V_N_IDOFIC",
							(requestMonitoreo.getOficina() != null ? requestMonitoreo.getOficina().getCodigo() : null))
					.addValue("V_D_FEC_ASIG_INI", requestMonitoreo.getFechaAsignacionInicio())
					.addValue("V_D_FEC_ASIG_FIN", requestMonitoreo.getFechaAsignacionFin());

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaCargaTrab = mapeaMonitoreoCabecera(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDigitalizados()] - " + e.getMessage());
		}
		return listaCargaTrab;
	}

	private Map<String, Object> mapeaMonitoreoCabecera(Map<String, Object> out) throws ParseException {
		MonitoreoCab item;

		Map<String, Object> result = new HashMap<String, Object>();
		List<MonitoreoCab> listaMonitoreoCab = new ArrayList<MonitoreoCab>();

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");

		if (lista.size() > 0) {
			for (Map<String, Object> map : lista) {

				item = new MonitoreoCab();

				Actividad actividad = new Actividad();
				Oficina oficina = new Oficina();
				Empresa contratista = new Empresa();
				Estado estadoAsignacion = new Estado();

				item.setIdCabecera(((BigDecimal) map.get("n_id_cab_asig_trab")).intValue());
				contratista.setCodigo(((BigDecimal) map.get("n_idempr")).intValue());
				contratista.setDescripcion((String) map.get("v_nombrempr"));
				estadoAsignacion.setId("" + ((BigDecimal) map.get("c_estado")).intValue());
				estadoAsignacion.setDescripcion((String) map.get("v_estado"));
				actividad.setCodigo((String) map.get("c_idacti"));
				actividad.setDescripcion((String) map.get("v_descacti"));
				oficina.setCodigo(((BigDecimal) map.get("n_idofic")).intValue());
				oficina.setDescripcion((String) map.get("v_descofic"));
				item.setContratista(contratista);
				item.setEstadoAsignacion(estadoAsignacion);
				item.setActividad(actividad);
				item.setOficina(oficina);
				if (map.get("d_fec_asig") != null && !map.get("d_fec_asig").equals(" ")) {
					Timestamp fecAsig = (Timestamp) map.get("d_fec_asig");
					item.setFechaAsignacion(new SimpleDateFormat("dd/MM/yyyy").format(fecAsig));
				}
				item.setNumeroCarga(((String) map.get("v_idcarga")));

				item.setCantAsignada(((BigDecimal) map.get("n_cantAsignada")).intValue());
				item.setCantProgramada(((BigDecimal) map.get("n_cantProgramada")).intValue());

				listaMonitoreoCab.add(item);
			}
		} /*else {
			listaMonitoreoCab = null;
		}*/

		result.put("listaMonitoreoCab", listaMonitoreoCab);

		return result;
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaMonitoreoCab(Integer idPers, Integer idPerfil) {
		Map<String, Object> out = null;
		ParametrosCargaBandeja parametros = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
				.withProcedureName(DbConstants.PRC_OBT_PARAM_BUSQ_MON_CAB)
				.declareParameters(new SqlOutParameter("C_OUT_EMPR", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_ACTI", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_ESTA", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_TZON", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_OFIC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_ESPR", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SEMA", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDPERS", idPers).addValue("V_N_IDPERFIL",
				idPerfil);

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				parametros = mapearParametrosBusqueda(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaMonitoreoCab()] - " + mensajeInterno);
			}

		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaMonitoreoCab()] - " + e.getMessage());
		}
		return parametros;
	}

	private ParametrosCargaBandeja mapearParametrosBusqueda(Map<String, Object> resultados) {
		ParametrosCargaBandeja params = new ParametrosCargaBandeja();
		List<Map<String, Object>> mapListaEmpresa = (List<Map<String, Object>>) resultados.get("C_OUT_EMPR");
		List<Map<String, Object>> mapListaActividad = (List<Map<String, Object>>) resultados.get("C_OUT_ACTI");
		List<Map<String, Object>> mapListaEstado = (List<Map<String, Object>>) resultados.get("C_OUT_ESTA");
		List<Map<String, Object>> mapListaEstadoDetalle = (List<Map<String, Object>>) resultados.get("C_OUT_ESPR");
		List<Map<String, Object>> mapListaTipoZona = (List<Map<String, Object>>) resultados.get("C_OUT_TZON");
		List<Map<String, Object>> mapListaOficina = (List<Map<String, Object>>) resultados.get("C_OUT_OFIC");
		// Agregados para el monitoreo
		// La lista base es la lista de oficinas ya existente
		// List<Map<String, Object>> mapListaBase = (List<Map<String,
		// Object>>)resultados.get("C_OUT_OFIC");
		List<Map<String, Object>> mapListaPeriodo = (List<Map<String, Object>>) resultados.get("C_OUT_PERI");
		List<Map<String, Object>> mapListaCiclo = (List<Map<String, Object>>) resultados.get("C_OUT_CICLO");
		// La actividad operativa se traslado a un nuevo llamado
		// List<Map<String, Object>> mapListaActOperativa = (List<Map<String,
		// Object>>)resultados.get("C_OUT_OFIC");
		List<Map<String, Object>> mapListaTipoActividad = (List<Map<String, Object>>) resultados.get("C_OUT_TSAC");
		List<Map<String, Object>> mapListaImposibilidad = (List<Map<String, Object>>) resultados.get("C_OUT_IMPO");
		List<Map<String, Object>> mapListaExisteFoto = (List<Map<String, Object>>) resultados.get("C_OUT_FOTO");
		List<Map<String, Object>> mapListaSemaforo = (List<Map<String, Object>>) resultados.get("C_OUT_SEMA");

		List<Empresa> listaContratista = new ArrayList<Empresa>();
		List<Actividad> listaActividad = new ArrayList<Actividad>();
		List<Parametro> listaEstado = new ArrayList<Parametro>();
		List<Parametro> listaEstadoDetalle = new ArrayList<Parametro>();
		List<Parametro> listaTipoZona = new ArrayList<Parametro>();
		List<Oficina> listaOficina = new ArrayList<Oficina>();
		List<Parametro> listaBase = new ArrayList<Parametro>();
		List<Parametro> listaPeriodo = new ArrayList<Parametro>();
		List<Parametro> listaCiclo = new ArrayList<Parametro>();
		List<Parametro> listaActOperativa = new ArrayList<Parametro>();
		List<Parametro> listaTipoActividad = new ArrayList<Parametro>();
		List<Parametro> listaImposibilidad = new ArrayList<Parametro>();
		List<Parametro> listaExisteFoto = new ArrayList<Parametro>();
		List<Parametro> listaSemaforo = new ArrayList<Parametro>();

		Empresa itemEmpresa;
		Actividad itemActividad;
		Parametro itemEstado, itemTipoZona, itemEstadoDetalle, itemBase, itemPeriodo, itemCiclo, itemActOpe,
				itemTipoAct, itemImposibilidad, itemExisteFoto, itemSemaforo;
		Oficina itemOficina;

		if (mapListaEmpresa != null && mapListaEmpresa.size() > 0) {
			for (Map<String, Object> map : mapListaEmpresa) {
				itemEmpresa = new Empresa();
				itemEmpresa.setCodigo(((BigDecimal) map.get("N_IDEMPR")).intValue());
				itemEmpresa.setDescripcion((String) map.get("V_NOMBREMPR"));
				listaContratista.add(itemEmpresa);
			}
		}

		if (mapListaActividad != null && mapListaActividad.size() > 0) {
			for (Map<String, Object> map : mapListaActividad) {
				itemActividad = new Actividad();
				itemActividad.setCodigo((String) map.get("V_IDACTI"));
				itemActividad.setDescripcion((String) map.get("V_DESCACTI"));
				listaActividad.add(itemActividad);
			}
		}

		if (mapListaEstado != null && mapListaEstado.size() > 0) {
			for (Map<String, Object> map : mapListaEstado) {
				itemEstado = new Parametro();
				itemEstado.setCodigo(((BigDecimal) map.get("N_IDPARAMETR")).intValue());
				itemEstado.setDetalle((String) map.get("V_DESCDETA"));
				listaEstado.add(itemEstado);
			}
		}

		if (mapListaEstadoDetalle != null && mapListaEstadoDetalle.size() > 0) {
			for (Map<String, Object> map : mapListaEstadoDetalle) {
				itemEstadoDetalle = new Parametro();
				itemEstadoDetalle.setCodigo(((BigDecimal) map.get("N_IDPARAMETR")).intValue());
				itemEstadoDetalle.setDetalle((String) map.get("V_DESCDETA"));
				listaEstadoDetalle.add(itemEstadoDetalle);
			}
		}

		if (mapListaTipoZona != null && mapListaTipoZona.size() > 0) {
			for (Map<String, Object> map : mapListaTipoZona) {
				itemTipoZona = new Parametro();
				itemTipoZona.setCodigo(((BigDecimal) map.get("N_IDPARAMETR")).intValue());
				itemTipoZona.setDetalle((String) map.get("V_DESCDETA"));
				listaTipoZona.add(itemTipoZona);
			}
		}

		if (mapListaOficina != null && mapListaOficina.size() > 0) {
			for (Map<String, Object> map : mapListaOficina) {
				itemOficina = new Oficina();
				itemOficina.setCodigo(((BigDecimal) map.get("N_IDOFIC")).intValue());
				itemOficina.setDescripcion((String) map.get("V_DESCOFIC"));
				listaOficina.add(itemOficina);
			}
		}

//		for(Map<String, Object> map: mapListaBase) {
//			itemBase = new Parametro();
//			itemBase.setCodigo( ((BigDecimal)map.get("N_IDOFIC")).intValue());
//			itemBase.setDetalle((String)map.get("V_DESCOFIC"));
//			listaBase.add(itemBase);
//		}

		if (mapListaPeriodo != null && mapListaPeriodo.size() > 0) {
			for (Map<String, Object> map : mapListaPeriodo) {
				itemPeriodo = new Parametro();
				itemPeriodo.setDetalle((String) map.get("PERIODO"));
				listaPeriodo.add(itemPeriodo);
			}
		}

		if (mapListaCiclo != null && mapListaCiclo.size() > 0) {
			for (Map<String, Object> map : mapListaCiclo) {
				itemCiclo = new Parametro();
				itemCiclo.setCodigo(((BigDecimal) map.get("N_IDOFIC")).intValue());
				itemCiclo.setDetalle((String) map.get("CICLO_FACT"));
				listaCiclo.add(itemCiclo);
			}
		}

//		for(Map<String, Object> map: mapListaActOperativa) {
//			itemActOpe = new Parametro();
//			itemActOpe.setCodigo( ((BigDecimal)map.get("N_IDOFIC")).intValue());
//			itemActOpe.setDetalle((String)map.get("V_DESCOFIC"));
//			listaActOperativa.add(itemActOpe);
//		}

		if (mapListaTipoActividad != null && mapListaTipoActividad.size() > 0) {
			for (Map<String, Object> map : mapListaTipoActividad) {
				itemTipoAct = new Parametro();
				itemTipoAct.setCodigo(((BigDecimal) map.get("V_IDSACTI")).intValue());
				itemTipoAct.setDetalle((String) map.get("V_DESCSACTI"));
				listaTipoActividad.add(itemTipoAct);
			}
		}

		if (mapListaImposibilidad != null && mapListaImposibilidad.size() > 0) {
			for (Map<String, Object> map : mapListaImposibilidad) {
				itemImposibilidad = new Parametro();
				itemImposibilidad.setCodigo(((BigDecimal) map.get("V_IDIMPO")).intValue());
				itemImposibilidad.setDetalle((String) map.get("V_DESCIMPO"));
				listaImposibilidad.add(itemImposibilidad);
			}
		}

		if (mapListaExisteFoto != null && mapListaExisteFoto.size() > 0) {
			for (Map<String, Object> map : mapListaExisteFoto) {
				itemExisteFoto = new Parametro();
				itemExisteFoto.setCodigo(((BigDecimal) map.get("V_IDFOTO")).intValue());
				itemExisteFoto.setDetalle((String) map.get("V_DESCFOTO"));
				listaExisteFoto.add(itemExisteFoto);
			}
		}

		if (mapListaSemaforo != null && mapListaSemaforo.size() > 0) {
			for (Map<String, Object> map : mapListaSemaforo) {
				itemSemaforo = new Parametro();
				itemSemaforo.setCodigo(((BigDecimal) map.get("N_IDPARAMETR")).intValue());
				itemSemaforo.setDetalle((String) map.get("V_DESCDETA"));
				listaSemaforo.add(itemSemaforo);
			}
		}

		params.setListaEmpresa(listaContratista);
		params.setListaActividad(listaActividad);
		params.setListaEstado(listaEstado);
		params.setListaTipoZona(listaTipoZona);
		params.setListaOficina(listaOficina);
		params.setListaEstadoDetalle(listaEstadoDetalle);
		// params.setListaEstadoDetalle(listaBase);
		params.setListaPeriodo(listaPeriodo);
		params.setListaCiclo(listaCiclo);
		params.setListaActOperativa(listaActOperativa);
		params.setListaTipoActividad(listaTipoActividad);
		params.setListaImposibilidad(listaImposibilidad);
		params.setListaExisteFoto(listaExisteFoto);
		params.setListaSemaforo(listaSemaforo);
		return params;
	}

	private ParametrosCargaBandeja mapearParametrosCiclo(Map<String, Object> resultados) {
		ParametrosCargaBandeja params = new ParametrosCargaBandeja();

		List<Map<String, Object>> mapListaCiclo = (List<Map<String, Object>>) resultados.get("C_OUT");

		List<Parametro> listaCiclo = new ArrayList<Parametro>();

		Parametro itemCiclo;

		if (mapListaCiclo != null && mapListaCiclo.size() > 0) {
			for (Map<String, Object> map : mapListaCiclo) {
				itemCiclo = new Parametro();
				itemCiclo.setCodigo(((BigDecimal) map.get("N_IDCICLO")).intValue());
				itemCiclo.setDetalle("" + ((BigDecimal) map.get("N_CICLO")).intValue());
				listaCiclo.add(itemCiclo);
			}
		}

		params.setListaCiclo(listaCiclo);
		return params;
	}

	@Override
	public Error getError() {
		return this.error;
	}

	@Override
	public Map<String, Object> listarDetalleAsignacionTrabajo(MonitoreoRequest requestMonitoreo) {
		Map<String, Object> listaDetaCargaTrab = new HashMap<String, Object>();
		Map<String, Object> out = null;

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_DET_ASIG_TRAB)
					.declareParameters(new SqlParameter("V_N_ID_CAB_ASIG_TRAB", OracleTypes.NUMBER),
							new SqlParameter("V_N_ID_SEC_ASIG_TRAB", OracleTypes.NUMBER),
							new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_C_IDACT", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_EMP_DET", OracleTypes.VARCHAR),
							new SqlParameter("V_N_NIS", OracleTypes.NUMBER),
							new SqlParameter("V_COD_ZONA", OracleTypes.VARCHAR),
							new SqlParameter("V_EST_PROG", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_ID_CAB_ASIG_TRAB", requestMonitoreo.getIdCabecera())
					.addValue("V_N_ID_SEC_ASIG_TRAB", requestMonitoreo.getIdDetalle())
					.addValue("V_N_IDEMP",
							requestMonitoreo.getContratista() != null ? requestMonitoreo.getContratista().getCodigo()
									: null)
					.addValue("V_C_IDACT",
							(requestMonitoreo.getActividad() != null ? requestMonitoreo.getActividad().getCodigo()
									: null))
					.addValue("V_COD_EMP_DET",
							requestMonitoreo.getTrabajador() != null ? requestMonitoreo.getTrabajador().getCodigo()
									: null)
					.addValue("V_N_NIS", requestMonitoreo.getSuministro())
					.addValue("V_COD_ZONA",
							(requestMonitoreo.getZona() != null ? requestMonitoreo.getZona().getCodigo() : 1))
					.addValue("V_EST_PROG",
							(requestMonitoreo.getEstado() != null
									? Integer.parseInt(requestMonitoreo.getEstado().getId())
									: 1));

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaDetaCargaTrab = mapeaMonitoreoDetalle(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + e.getMessage());
		}
		return listaDetaCargaTrab;
	}

	private Map<String, Object> mapeaMonitoreoDetalle(Map<String, Object> out) throws ParseException {
		MonitoreoDet item;
		Map<String, Object> result = new HashMap<String, Object>();
		List<MonitoreoDet> listaMonitoreoDet = new ArrayList<MonitoreoDet>();

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");

		if (lista.size() > 0) {
			for (Map<String, Object> map : lista) {

				item = new MonitoreoDet();

				Actividad actividad = new Actividad();
				Trabajador trabajador = new Trabajador();
				Zona zona = new Zona();

				Integer cod_emp;
				item.setIdDetalle(((BigDecimal) map.get("ide_reg")).intValue());
				
				if (map.get("cod_emp") != null && !map.get("cod_emp").equals(" ")) {
					cod_emp = Integer.parseInt((String) map.get("cod_emp"));
				} else {
					cod_emp = 0;
				}
				trabajador.setCodigo(cod_emp);
				trabajador.setNombre((String) map.get("nom_emp"));
				actividad.setCodigo((String) map.get("ide_act"));
				actividad.setDescripcion((String) map.get("des_act"));

				item.setActividad(actividad);

				if (map.get("fec_prog") != null && !map.get("fec_prog").equals(" ")) {
					Timestamp fecProg = (Timestamp) map.get("fec_prog");
					item.setFechaProgramacion(new SimpleDateFormat("dd/MM/yyyy").format(fecProg));
				}

				item.setTrabajador(trabajador);
				item.setSuministro(((BigDecimal) map.get("nis_rad")).intValue());
				item.setEstadoProgramacion((String) map.get("est_prog"));

				if (map.get("tpo_zon") != null && !map.get("tpo_zon").equals(" ")) {
					zona.setCodigo(((BigDecimal) map.get("tpo_zon")).intValue());
				} else {
					zona.setCodigo(0);
				}
				zona.setDetalle((String) map.get("des_tzo"));
				item.setZona(zona);

				listaMonitoreoDet.add(item);
			}
		} 
//		else {
//			listaMonitoreoDet = null;
//		}

		result.put("listaMonitoreoDet", listaMonitoreoDet);

		return result;
	}

	@Override
	public Map<String, Object> anularRegistroCabecera(Integer idCab, Integer codEmp) {

		Map<String, Object> out = new HashMap<String, Object>();

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_UPD_CAB_ASIG_TRAB)
					.declareParameters(new SqlParameter("V_N_ID_CAB", OracleTypes.NUMBER),
							new SqlParameter("V_C_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("V_A_V_USUMOD", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_ID_CAB", idCab)
					.addValue("V_C_ESTADO", 6) // Cambiar de estado a anulado
					.addValue("V_C_EMP", codEmp);

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				// return out;
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listaDetalleObtenerValor()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listaDetalleObtenerValor()] - " + e.getMessage());
		}

		return out;
	}

	@Override
	public DuracionParametro obtenerParametrosDuracion() {
		DuracionParametro duracion = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_OBTE_PARAM_DURACION)
					.declareParameters(new SqlOutParameter("N_MESES", OracleTypes.VARCHAR),
							new SqlOutParameter("N_DIAS_RANGO", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			MapSqlParameterSource in = new MapSqlParameterSource();

			Map<String, Object> out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				duracion = new DuracionParametro();
				String mesesConsulta = (String) out.get("N_MESES");
				String diasRango = (String) out.get("N_DIAS_RANGO");
				try {
					duracion.setMesesConsulta(Integer.parseInt(mesesConsulta));
					duracion.setDiasRango(Integer.parseInt(diasRango));

				} catch (NumberFormatException e) {
					duracion = null;
					this.error = new Error(0, "Error",
							"El valor de los parámetros de meses de consulta y rango de días no tiene un valor numérico.");
				}
			} else {
				String mensaje = out.get("N_EJEC").toString();
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
				logger.error("[AGC: DigitalizadoDAOImpl - obtenerDias()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			this.error = new Error(0, "9999", Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - obtenerDiasPeriodo()] - " + e.getMessage());
		}
		return duracion;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> cargaArchivoProgramacion(List<Object> listaCarga, Integer codEmp, String codActividad,
			Integer codEmpresa, Integer codOficina) throws Exception {

		Map<String, Object> out = new HashMap<String, Object>();

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_VALIDAR_TRAMA_PERSONAL_CONTRATISTA)
					.declareParameters(new SqlParameter("TAB_I_LISTA_TRAMA", OracleTypes.ARRAY),
							new SqlParameter("V_USUA_MOD", OracleTypes.NUMBER),
							new SqlParameter("V_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("V_COD_ACT", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_OFI", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			Connection conn = this.jdbcCall.getJdbcTemplate().getDataSource().getConnection()
					.unwrap(OracleConnection.class);
			Array array = ((OracleConnection) conn).createOracleArray("AGC.TYPE_AGC_TAB_TRAMA_PROGRAMACION",
					listaCarga.toArray());
			conn.close();
			MapSqlParameterSource in = new MapSqlParameterSource().addValue("TAB_I_LISTA_TRAMA", array)
					.addValue("V_USUA_MOD", codEmp)
					.addValue("V_COD_EMP", codEmpresa)
					.addValue("V_COD_ACT", codActividad)
					.addValue("V_COD_OFI", codOficina);

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				/*
				 * Integer ejecucion = ((BigDecimal) out.get("N_EJEC")).intValue(); if
				 * (ejecucion == -1){ new Error(resultado, "8888",
				 * Constantes.MESSAGE_ERROR.get(8888)); throw new
				 * Exception("prueba de caida procedmiento"); }
				 */
				return out;
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: MonitoreoDAOImpl - cargaArchivoProgramacion()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: MonitoreoDAOImpl - cargaArchivoProgramacion()] - " + e.getMessage());
			throw new Exception();
		}

		return out;
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaBandejaMonitoreo(Integer idPers, Integer idPerfil) {
		Map<String, Object> out = null;
		ParametrosCargaBandeja parametros = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
				.withProcedureName(DbConstants.PRC_OBT_PARAM_BUSQUEDA_MONITOREO)
				.declareParameters(new SqlParameter("V_N_IDPERS", OracleTypes.NUMBER),
						new SqlParameter("V_N_IDPERFIL", OracleTypes.NUMBER),
						new SqlOutParameter("C_OUT_EMPR", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_OFIC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_PERI", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_CICLO", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_PARAMETROS", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_TIPOS_SGC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_CR", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_DA", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_DC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_IC", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_ME", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_SO", OracleTypes.CURSOR),
						new SqlOutParameter("C_OUT_SA_TE", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDPERS", idPers).addValue("V_N_IDPERFIL",idPerfil);

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				parametros = ParametrosMonitoreoMapper.mapearParametrosBusqueda(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error(
						"[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaBandejaMonitoreo()] - " + mensajeInterno);
			}

		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaBandejaMonitoreo()] - " + e.getMessage());
		}
		return parametros;
	}

	@Override
	public Map<String, Object> listarMonitoreoCabecera(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) throws ParseException {
		Map<String, Object> listaMonitoreoCab = new HashMap<String, Object>();
		Map<String, Object> out = null;
        
		try {

//			int prueba = requestMonitoreo.getPeriodo()!=null?requestMonitoreo.getPeriodo().getCodigo():null;

			//System.out.println(requestMonitoreo.getNumLote().toString());

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_CABECERA)
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_PROG_INI", OracleTypes.DATE),
							new SqlParameter("V_D_FEC_PROG_FIN", OracleTypes.DATE),
							new SqlParameter("V_COD_EMP_DET", OracleTypes.VARCHAR),
							new SqlParameter("V_ESTA_EJEC", OracleTypes.NUMBER),
							new SqlParameter("V_ACTIVIDAD", OracleTypes.VARCHAR),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("V_MEDIDOR", OracleTypes.VARCHAR),
							new SqlParameter("V_ID_OFIC", OracleTypes.NUMBER),
							new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
							new SqlParameter("V_CICLO", OracleTypes.VARCHAR),
							new SqlParameter("V_IND_INC", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_OS", OracleTypes.VARCHAR),
							new SqlParameter("V_NUM_CEDULA", OracleTypes.VARCHAR),
							new SqlParameter("V_NUM_LOTE", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_OT", OracleTypes.VARCHAR),
							new SqlParameter("V_TDOC_NOTI", OracleTypes.NUMBER),
							new SqlParameter("V_SUB_ACT", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMP",
							requestMonitoreo.getContratista() != null ? requestMonitoreo.getContratista().getCodigo()
									: null)
					.addValue("V_D_FEC_PROG_INI", requestMonitoreo.getFechaProgramacionInicio())
					.addValue("V_D_FEC_PROG_FIN", requestMonitoreo.getFechaProgramacionFin())
					.addValue("V_COD_EMP_DET",
							requestMonitoreo.getOperario() != null ? requestMonitoreo.getOperario().getCodigo() : null)
					.addValue("V_ESTA_EJEC",
							requestMonitoreo.getEstado() != null ? requestMonitoreo.getEstado().getCodigo() : 1)
					.addValue("V_ACTIVIDAD", idActividad)
					.addValue("V_NIS", requestMonitoreo.getSuministro())
					.addValue("V_MEDIDOR", requestMonitoreo.getNumMedidor())
					.addValue("V_ID_OFIC",
							requestMonitoreo.getOficina() != null ? requestMonitoreo.getOficina().getCodigo() : null)
					.addValue("V_PERIODO",
							requestMonitoreo.getPeriodo() != null ? requestMonitoreo.getPeriodo().getCodigo() : null)
					.addValue("V_CICLO",
							requestMonitoreo.getCiclo() != null ? requestMonitoreo.getCiclo().getCodigo() : null)
					.addValue("V_IND_INC",
							requestMonitoreo.getImposibilidad() != null
									? requestMonitoreo.getImposibilidad().getCodigo()
									: 1)
					.addValue("V_IND_FOTO",
							requestMonitoreo.getFoto() != null ? requestMonitoreo.getFoto().getCodigo() : 1)
					.addValue("V_NUM_OS", requestMonitoreo.getOrdenServicio())
					.addValue("V_NUM_CEDULA", requestMonitoreo.getCedulaNotificacion())
					.addValue("V_NUM_LOTE", requestMonitoreo.getNumeroLote())
					.addValue("V_NUM_OT", requestMonitoreo.getOrdenTrabajo())
					.addValue("V_TDOC_NOTI", requestMonitoreo.getTipoNotificacion() != null ? requestMonitoreo.getTipoNotificacion().getCodigo() : null)
					.addValue("V_SUB_ACT", requestMonitoreo.getTipoActividad() != null ? requestMonitoreo.getTipoActividad().getId() : null)
					;
			

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			Integer semaforo = (requestMonitoreo.getSemaforo() != null ? requestMonitoreo.getSemaforo().getCodigo()
					: 1);
			if (resultado == 1) {
				listaMonitoreoCab = mapeaCabeceraMonitoreo(out, semaforo);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + e.getMessage());
		}
		return listaMonitoreoCab;
	}

	private Map<String, Object> mapeaCabeceraMonitoreo(Map<String, Object> out, Integer semaforo)
			throws ParseException {
		MonitoreoCabecera item;
		Map<String, Object> result = new HashMap<String, Object>();
		List<MonitoreoCabecera> listaMonitoreoCabecera = new ArrayList<MonitoreoCabecera>();

		Integer cod_emp;
		Integer cant_pend, cant_prog, cant_ejec;
		Double por_avance;
		String porc_avance;

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");

		if (lista.size() > 0) {
			for (Map<String, Object> map : lista) {

				item = new MonitoreoCabecera();

				Actividad actividad = new Actividad();
				Trabajador trabajador = new Trabajador();

				por_avance = 0.0;
				cant_pend = 0;
				cant_prog = 0;
				cant_ejec = 0;

				if (map.get("cod_emp") != null && !map.get("cod_emp").equals(" ")) {
					cod_emp = Integer.parseInt((String) map.get("cod_emp"));
				} else {
					cod_emp = 0;
				}
				item.setIdMonitoreo(cod_emp);
				trabajador.setCodigo(cod_emp);
				trabajador.setNombre((String) map.get("nom_emp"));
				item.setTrabajador(trabajador);

				actividad.setCodigo((String) map.get("ide_act"));
				actividad.setDescripcion((String) map.get("des_act"));
				item.setActividad(actividad);

				item.setCargaProgramada(((BigDecimal) map.get("can_pro")).intValue());
				item.setCargaEjecutada(((BigDecimal) map.get("can_eje")).intValue());
				cant_prog = ((BigDecimal) map.get("can_pro")).intValue();
				cant_ejec = ((BigDecimal) map.get("can_eje")).intValue();
				cant_pend = cant_prog - cant_ejec;
				item.setCargaPendiente(cant_pend);

				por_avance = (cant_prog == 0 ? 0 : cant_ejec.doubleValue() / cant_prog.doubleValue());
				porc_avance = String.format("%.2f", por_avance.floatValue());
				porc_avance = porc_avance + " %";
				item.setAvance(porc_avance);
				item.setFechaProgramacion((String) map.get("fec_pro"));
				item.setFechaEjecucionInicio((String) map.get("fec_ini_ejec"));
				item.setHoraEjecucionInicio((String) map.get("hor_ini_ejec"));
				item.setFechaEjecucionFin((String) map.get("fec_fin_ejec"));
				item.setHoraEjecucionFin((String) map.get("hor_fin_ejec"));
				
				por_avance = por_avance * 100;
				
				if (por_avance < 40) {
					item.setSemaforo(2);
				}
				if (por_avance > 40 && por_avance < 70) {
					item.setSemaforo(3);
				}
				if (por_avance > 70) {
					item.setSemaforo(4);
				}

				switch (semaforo) {

				case 1:
					// Todos
					listaMonitoreoCabecera.add(item);
					break;
				case 2:
					// Rojo
					if (por_avance < 40) {
						listaMonitoreoCabecera.add(item);
					}
					break;
				case 3:
					// Amarillo
					if (por_avance > 40 && por_avance < 70) {
						listaMonitoreoCabecera.add(item);
					}
					break;
				case 4:
					// Verde
					if (por_avance > 70) {
						listaMonitoreoCabecera.add(item);
					}
					break;
				}
				// listaMonitoreoCabecera.add(item);
			}
		} else {
			listaMonitoreoCabecera = new ArrayList<MonitoreoCabecera>();
		}

		result.put("listarMonitoreoCabecera", listaMonitoreoCabecera);

		return result;
	}

	@Override
	public ParametrosCargaBandeja obtenerParametrosBusquedaCiclo(Integer idOficina, String idActividad,
			String idPeriodo) {
		Map<String, Object> out = null;
		ParametrosCargaBandeja parametros = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_MON_CRONOGRAMA).withProcedureName(DbConstants.PRC_LIST_CICLOS)
				.declareParameters(new SqlParameter("V_N_IDOFIC", OracleTypes.NUMBER),
						new SqlParameter("V_C_IDACTI", OracleTypes.VARCHAR),
						new SqlParameter("V_D_PERIODO", OracleTypes.VARCHAR),
						new SqlParameter("V_C_ESTADO", OracleTypes.NUMBER),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource().addValue("V_N_IDOFIC", idOficina)
				.addValue("V_C_IDACTI", idActividad).addValue("V_C_ESTADO", 1).addValue("V_D_PERIODO", idPeriodo);
		;

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();

			if (resultado == 1) {
				parametros = mapearParametrosCiclo(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error(
						"[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaBandejaMonitoreo()] - " + mensajeInterno);
			}

		} catch (Exception e) {
			this.error = new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: MonitoreoDAOImpl - obtenerParametrosBusquedaBandejaMonitoreo()] - " + e.getMessage());
		}
		return parametros;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleTE(MonitoreoDetalleRequestTE requestMonitoreo) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		Integer indCumplimiento = requestMonitoreo.getCumplimiento() !=null ? requestMonitoreo.getCumplimiento().getCodigo() : 1;
		String cumplimiento = null;
		if (indCumplimiento == 1) {
			cumplimiento = null;
		}
		if (indCumplimiento == 2) {
			cumplimiento = "I";
		}
		if (indCumplimiento == 3) {
			cumplimiento = "C";
		}
		
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_TE)
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_INC", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_SUB_INC", OracleTypes.VARCHAR),
							new SqlParameter("V_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_CICLO", OracleTypes.NUMBER),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMP",
							requestMonitoreo.getTrabajador() != null ? requestMonitoreo.getTrabajador().getCodigo()
									: null)
					.addValue("V_D_FEC_PROG", requestMonitoreo.getFechaProgramacion())
					.addValue("V_COD_INC",
							requestMonitoreo.getIncidencia() != null ? requestMonitoreo.getIncidencia().getCodigo()
									: null)
					.addValue("V_COD_SUB_INC",
							requestMonitoreo.getSubIncidencia() != null
									? requestMonitoreo.getSubIncidencia().getCodigo()
									: null)
					.addValue("V_ESTADO",
							requestMonitoreo.getEstado() != null ? requestMonitoreo.getEstado().getId() : 1)
					.addValue("V_IND_FOTO",
							requestMonitoreo.getFoto() != null ? requestMonitoreo.getFoto().getCodigo() : 1)
					.addValue("V_CICLO",
							requestMonitoreo.getCiclo() != null ? requestMonitoreo.getCiclo().getValor() : null)
					.addValue("V_NIS", requestMonitoreo.getSuministro())
					.addValue("I_V_IND_CUMPLMNTO", cumplimiento)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaMonitoreoDet = mapeaDetalleMonitoreoTE(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	private Map<String, Object> mapeaDetalleMonitoreoTE(Map<String, Object> out) throws Exception {
		MonitoreoDetalleTE item;
		Map<String, Object> result = new HashMap<String, Object>();
		List<MonitoreoDetalleTE> listaMonitoreoDetalle = new ArrayList<MonitoreoDetalleTE>();

		if (out.get("C_OUT") != null) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");
			if (lista.size() > 0) {
				for (Map<String, Object> map : lista) {

					item = new MonitoreoDetalleTE();

					Zona zona = new Zona();
					Parametro foto = new Parametro();

					item.setSuministro((String) map.get("nis"));
					item.setDireccion((String) map.get("dir"));
					item.setMedidor((String) map.get("med"));
					item.setLectura((String) map.get("lec"));
					item.setIncLectura1((String) map.get("inc_lec_1"));
					item.setIncLectura2((String) map.get("inc_lec_2"));
					item.setIncLectura3((String) map.get("inc_lec_3"));
					item.setMedidorObservado((String) map.get("med_obs"));
					item.setFechaEjecucion((String) map.get("fec_eje"));
					item.setHoraEjecucion((String) map.get("hor_eje"));

					zona.setCodigo(((BigDecimal) map.get("COD_ZONA")).intValue());
					zona.setDetalle((String) map.get("DESC_ZONA"));
					item.setZona(zona);

					Integer flag_foto = ((BigDecimal) map.get("flag_foto")).intValue();

					foto.setDetalle(flag_foto.toString());
					item.setFoto(foto);
					
					Estado estado = new Estado();
					estado.setDescripcion((String)map.get("est"));
					item.setEstado(estado);
					
					Parametro completa = new Parametro();
					completa.setDetalle(CastUtil.leerValorMapString(map, "IND_CUMPLMNTO"));
					item.setCumplimiento(completa);
					
					item.setImagen1(CastUtil.leerValorMapString(map, "IMAGEN_1"));
					item.setImagen2(CastUtil.leerValorMapString(map, "IMAGEN_2"));
					item.setImagen3(CastUtil.leerValorMapString(map, "IMAGEN_3"));
										
					listaMonitoreoDetalle.add(item);
				}
			}
		} else {
			throw new Exception(Constantes.MensajesErrores.MAPPER_LISTA_NULL);
		}

		result.put("listarMonitoreoDetalle", listaMonitoreoDetalle);

		return result;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleIC(MonitoreoDetalleRequestIC requestMonitoreo) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		Integer indCumplimiento = requestMonitoreo.getCumplimiento() !=null ? requestMonitoreo.getCumplimiento().getCodigo() : 1;
		String cumplimiento = null;
		if (indCumplimiento == 1) {
			cumplimiento = null;
		}
		if (indCumplimiento == 2) {
			cumplimiento = "I";
		}
		if (indCumplimiento == 3) {
			cumplimiento = "C";
		}
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_IC)
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("V_IMPOSIBILIDAD", OracleTypes.NUMBER),
							new SqlParameter("V_EST_SERV", OracleTypes.VARCHAR),
							new SqlParameter("V_EST_MED", OracleTypes.VARCHAR),
							new SqlParameter("V_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_INS", OracleTypes.VARCHAR),
							new SqlParameter("V_RES_INSP", OracleTypes.NUMBER),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlParameter("V_TIP_ZONA", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMP",
							requestMonitoreo.getTrabajador() != null ? requestMonitoreo.getTrabajador().getCodigo()
									: null)
					.addValue("V_D_FEC_PROG", requestMonitoreo.getFechaProgramacion())
					.addValue("V_IMPOSIBILIDAD",
							requestMonitoreo.getIncidencia() != null
									? requestMonitoreo.getIncidencia().getCodigo()
									: null)
					.addValue("V_EST_SERV",
							requestMonitoreo.getEstadoServicio() != null
									? requestMonitoreo.getEstadoServicio().getCodigo()
									: null)
					.addValue("V_EST_MED",
							requestMonitoreo.getEstadoMedidor() != null
									? requestMonitoreo.getEstadoMedidor().getCodigo()
									: null)
					.addValue("V_ESTADO",
							requestMonitoreo.getEstado() != null ? requestMonitoreo.getEstado().getId() : 1)
					.addValue("V_IND_FOTO",
							requestMonitoreo.getFoto() != null ? requestMonitoreo.getFoto().getCodigo() : 1)
					.addValue("V_TIP_INS",
							requestMonitoreo.getTipoInspeccion() != null
									? requestMonitoreo.getTipoInspeccion().getCodigo()
									: null)
					.addValue("V_RES_INSP",
							requestMonitoreo.getResultadoInspeccion() != null
									? requestMonitoreo.getResultadoInspeccion().getCodigo()
									: null)
					.addValue("V_NIS", requestMonitoreo.getSuministro())
					.addValue("I_V_IND_CUMPLMNTO", cumplimiento)
					.addValue("V_TIP_ZONA", requestMonitoreo.getZona() != null ? requestMonitoreo.getZona().getCodigo() : 1)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaMonitoreoDet = mapeaDetalleMonitoreoIC(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajoIC()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajoIC()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> mapeaDetalleMonitoreoIC(Map<String, Object> out) throws Exception {
		MonitoreoDetalleIC item;
		Map<String, Object> result = new HashMap<String, Object>();
		List<MonitoreoDetalleIC> listaMonitoreoDetalle = new ArrayList<MonitoreoDetalleIC>();
		
		if (out.get("C_OUT") != null) {
			List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("C_OUT");

			if (lista.size() > 0) {
				for (Map<String, Object> map : lista) {

					item = new MonitoreoDetalleIC();

					Zona zona = new Zona();
					Parametro estadoMedidor = new Parametro();
					Parametro foto = new Parametro();
					Parametro incidenciaMedidor = new Parametro();
					Parametro imposibilidad = new Parametro();
					Parametro estado = new Parametro();

					Integer nis = ((BigDecimal) map.get("nis")).intValue();
					item.setSuministro(Integer.toString(nis));
					item.setDireccion((String) map.get("dir"));
					item.setMedidor((String) map.get("med"));
					Integer os = ((BigDecimal) map.get("os")).intValue();
					item.setOrdenServicio(Integer.toString(os));
					item.setTipologiaOrdServ((String) map.get("tos"));
					item.setFechaVisita((String) map.get("f_vis"));
					item.setHoraInicio((String) map.get("h_ini"));
					item.setHoraFin((String) map.get("h_fin"));
					item.setServicio((String) map.get("ser"));
					item.setLectura((String) map.get("lec"));

					estadoMedidor.setDetalle((String) map.get("esm"));
					item.setEstadoMedidor(estadoMedidor);

					incidenciaMedidor.setDetalle((String) map.get("inc"));
					item.setIncidenciaMedidor(incidenciaMedidor);

					imposibilidad.setDescripcionCorta((String) map.get("imp"));
					imposibilidad.setDetalle((String) map.get("imp"));
					item.setImposibilidad(imposibilidad);

					item.setInspeccionRealizada((String) map.get("inr"));
					item.setDetalle((String) map.get("detalle"));

					estado.setDetalle((String) map.get("est"));
					item.setEstado(estado);

					Integer t_zona = map.get("COD_ZONA") != null ? ((BigDecimal) map.get("COD_ZONA")).intValue() : 0;
					zona.setCodigo(t_zona);
					zona.setDetalle((String) map.get("DESC_ZONA"));
					item.setZona(zona);

					foto.setCodigo(((BigDecimal) map.get("flag_foto")).intValue());
					item.setFoto(foto);
					
					Parametro completa = new Parametro();
					completa.setDetalle(CastUtil.leerValorMapString(map, "IND_CUMPLMNTO"));
					item.setCompleta(completa);
					
					item.setImagen1(CastUtil.leerValorMapString(map, "IMAGEN_1"));
					item.setImagen2(CastUtil.leerValorMapString(map, "IMAGEN_2"));
					item.setImagen3(CastUtil.leerValorMapString(map, "IMAGEN_3"));

					listaMonitoreoDetalle.add(item);
				}
			}
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		result.put("listarMonitoreoDetalle", listaMonitoreoDetalle);
		return result;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleDA(MonitoreoDetalleRequestDA requestDA) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		Integer indCumplimiento = requestDA.getCumplimiento() !=null ? requestDA.getCumplimiento().getCodigo() : 1;
		String cumplimiento = null;
		if (indCumplimiento == 1) {
			cumplimiento = null;
		}
		if (indCumplimiento == 2) {
			cumplimiento = "I";
		}
		if (indCumplimiento == 3) {
			cumplimiento = "C";
		}
		
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_DA)
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("V_IMPOSIBILIDAD", OracleTypes.VARCHAR),
							new SqlParameter("V_ESTADO", OracleTypes.VARCHAR),
							new SqlParameter("V_IND_FOTO", OracleTypes.VARCHAR),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMP",
							requestDA.getTrabajador() != null ? requestDA.getTrabajador().getCodigo() : null)
					.addValue("V_D_FEC_PROG", requestDA.getFechaProgramacion())
					.addValue("V_IMPOSIBILIDAD",
							requestDA.getIncidencia() != null ? requestDA.getIncidencia().getCodigo()
									: null)
					.addValue("V_ESTADO", requestDA.getEstado() != null ? requestDA.getEstado().getId() : 1)
					.addValue("V_IND_FOTO", requestDA.getFoto() != null ? requestDA.getFoto().getCodigo() : 1)
					.addValue("V_NIS", requestDA.getSuministro() != null ? requestDA.getSuministro() : null)
					.addValue("I_V_IND_CUMPLMNTO", cumplimiento)
					;

			out = this.jdbcCall.execute(in);
			Integer resultadoOperacion = CastUtil.leerValorMapInteger(out, "N_RESP");
			if (resultadoOperacion == 1) {
				listaMonitoreoDet = MonitoreoDetalleDA.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultadoOperacion, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleDA()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleDA()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleDC(MonitoreoDetalleRequestDC requestDC) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		String indCumplimiento = requestDC.getCumplimiento() !=null ? requestDC.getCumplimiento().getCodigo() : "1";
		String cumplimiento = null;
		if (indCumplimiento.equals("1")) {
			cumplimiento = null;
		}
		if (indCumplimiento.equals("2")) {
			cumplimiento = "I";
		}
		if (indCumplimiento.equals("3")) {
			cumplimiento = "C";
		}
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_DC)
					.declareParameters(new SqlParameter("I_V_COD_EMP", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("I_V_IMPOSIBILIDAD", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("I_N_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("I_V_TIP_ENTREGA", OracleTypes.VARCHAR),
							new SqlParameter("I_N_SUMINISTRO", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_RESPUESTA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_V_COD_EMP",
							requestDC.getTrabajador() != null ? requestDC.getTrabajador().getCodigo() : null)
					.addValue("I_V_FEC_PROG", requestDC.getFechaProgramacion())
					.addValue("I_V_IMPOSIBILIDAD",
							requestDC.getIncidencia() != null ? requestDC.getIncidencia().getCodigo()
									: null)
					.addValue("I_N_ESTADO", requestDC.getEstado() != null ? requestDC.getEstado().getId() : 1)
					.addValue("I_N_IND_FOTO", requestDC.getFoto() != null ? requestDC.getFoto().getCodigo() : 1)
					.addValue("I_V_TIP_ENTREGA", requestDC.getTipoEntrega() != null ? requestDC.getTipoEntrega().getDetalle(): null)
					.addValue("I_N_SUMINISTRO", requestDC.getSuministro())
					.addValue("I_V_IND_CUMPLMNTO", cumplimiento)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultado == 1) {
				listaMonitoreoDet = MonitoreoDetalleDC.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("O_V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleDC()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleDC()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleME(MonitoreoDetalleRequestME requestME) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		String indCumplimiento = requestME.getCumplimiento() !=null ? requestME.getCumplimiento().getCodigo() : "1";
		String cumplimiento = null;
		if (indCumplimiento.equals("1")) {
			cumplimiento = null;
		}
		if (indCumplimiento.equals("2")) {
			cumplimiento = "I";
		}
		if (indCumplimiento.equals("3")) {
			cumplimiento = "C";
		}
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_ME)
					.declareParameters(new SqlParameter("I_V_COD_EMP", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("I_V_NUM_OS", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("I_N_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("I_V_TIPO_ACTIV", OracleTypes.VARCHAR),
							new SqlParameter("I_N_ZONA", OracleTypes.NUMBER),
							new SqlParameter("I_V_MEDIDOR_INST", OracleTypes.VARCHAR),
							new SqlParameter("I_V_MEDIDOR_RETI", OracleTypes.VARCHAR),
							new SqlParameter("I_V_FEC_EJEC", OracleTypes.VARCHAR),
							new SqlParameter("I_V_TIPO_OS", OracleTypes.VARCHAR),
							new SqlParameter("I_N_SUMINISTRO", OracleTypes.NUMBER),
							new SqlParameter("I_V_OBSERVACION", OracleTypes.VARCHAR),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlParameter("I_V_TIPO_INST", OracleTypes.VARCHAR),
							new SqlOutParameter("O_C_RESPUESTA", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_V_COD_EMP",
							requestME.getTrabajador() != null ? requestME.getTrabajador().getCodigo() : null)
					.addValue("I_V_FEC_PROG", requestME.getFechaProgramacion())
					.addValue("I_V_NUM_OS", requestME.getOrdenServicio())
					.addValue("I_N_ESTADO", requestME.getEstado() != null ? requestME.getEstado().getId() : 1)
					.addValue("I_N_IND_FOTO", requestME.getFoto() != null ? requestME.getFoto().getCodigo() : 1)
					.addValue("I_V_TIPO_ACTIV",
							requestME.getTipoActividad() != null ? requestME.getTipoActividad().getCodigo() : null)
					.addValue("I_N_ZONA", requestME.getZona() != null ? requestME.getZona().getCodigo() : 1)
					.addValue("I_V_MEDIDOR_INST", requestME.getMedidorInstalado())
					.addValue("I_V_MEDIDOR_RETI", requestME.getMedidorRetirado())
					.addValue("I_V_FEC_EJEC", requestME.getFechaEjecucion())
					.addValue("I_V_TIPO_OS",
							requestME.getTipoCarga() != null ? requestME.getTipoCarga().getCodigo() : null)
					.addValue("I_N_SUMINISTRO", requestME.getSuministro())
					.addValue("I_V_OBSERVACION",
							requestME.getCodObservacion() != null ? requestME.getCodObservacion().getCodigo() : null)
					.addValue("I_V_IND_CUMPLMNTO", cumplimiento)
					.addValue("I_V_TIPO_INST", requestME.getTipoInstalacion() != null ? requestME.getTipoInstalacion().getCodigo() : null)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = CastUtil.leerValorMapInteger(out, "O_N_RESP");
			if (resultado == 1) {
				//listaMonitoreoDet = MonitoreoDetalleDC.mapper(out);
				listaMonitoreoDet = MonitoreoDetalleME.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("" + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("" + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleCR(MonitoreoDetalleRequestCR requestCR) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		String indCumplimiento = requestCR.getCumplimiento() !=null ? requestCR.getCumplimiento().getCodigo() : "1";
		String cumplimiento = null;
		if (indCumplimiento.equals("1")) {
			cumplimiento = null;
		}
		if (indCumplimiento.equals("2")) {
			cumplimiento = "I";
		}
		if (indCumplimiento.equals("3")) {
			cumplimiento = "C";
		}
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_CR).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("V_ORD_SERV", OracleTypes.NUMBER),
							new SqlParameter("V_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_ACT", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_ZONA", OracleTypes.NUMBER),
							new SqlParameter("V_FEC_EJEC", OracleTypes.DATE),
							new SqlParameter("V_TIP_ORDEN", OracleTypes.VARCHAR),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("V_COD_OBS", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("V_N_IDEMP", requestCR.getTrabajador() != null ? requestCR.getTrabajador().getCodigo() : null);
			in.addValue("V_D_FEC_PROG", requestCR.getFechaProgramacion());
			in.addValue("V_ORD_SERV", requestCR.getOrdenServicio());
			in.addValue("V_ESTADO", requestCR.getEstado() != null ? requestCR.getEstado().getId() : 1);
			in.addValue("V_IND_FOTO", requestCR.getFoto() != null ? requestCR.getFoto().getCodigo() : 1);
			in.addValue("V_TIP_ACT", requestCR.getTipoActividad() != null ? requestCR.getTipoActividad().getCodigo() : 1);
			in.addValue("V_TIP_ZONA", requestCR.getZona() != null ? requestCR.getZona().getCodigo() : 1);
			in.addValue("V_FEC_EJEC", requestCR.getFechaEjecucion());
			in.addValue("V_TIP_ORDEN", requestCR.getTipoOrdenServicio() != null ? requestCR.getTipoOrdenServicio().getCodigo() : null);
			in.addValue("V_NIS", requestCR.getSuministro());
			in.addValue("V_COD_OBS", requestCR.getCodObservacion() != null ? requestCR.getCodObservacion().getCodigo():null);
			in.addValue("I_V_IND_CUMPLMNTO", cumplimiento);

			out = this.jdbcCall.execute(in);
			Integer resultado = CastUtil.leerValorMapInteger(out, "N_RESP");
			if (resultado == 1) {
				listaMonitoreoDet = MonitoreoDetalleCR.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleCR()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleCR()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@Override
	public Map<String, Object> listarMonitoreoDetalleSO(MonitoreoDetalleRequestSO requestSO) {
		Map<String, Object> listaMonitoreoDet = new HashMap<String, Object>();
		Map<String, Object> out = null;
		String indCumplimiento = requestSO.getCumplimiento() !=null ? requestSO.getCumplimiento().getCodigo() : "1";
		String cumplimiento = null;
		if (indCumplimiento.equals("1")) {
			cumplimiento = null;
		}
		if (indCumplimiento.equals("2")) {
			cumplimiento = "I";
		}
		if (indCumplimiento.equals("3")) {
			cumplimiento = "C";
		}
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_DETALLE_SO).withoutProcedureColumnMetaDataAccess()
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_PROG", OracleTypes.VARCHAR),
							new SqlParameter("V_ORD_SERV", OracleTypes.NUMBER),
							new SqlParameter("V_ESTADO", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_ACT", OracleTypes.NUMBER),
							new SqlParameter("V_TIP_ZONA", OracleTypes.NUMBER),
							new SqlParameter("V_FEC_EJEC", OracleTypes.DATE),
							new SqlParameter("V_TIP_ORDEN", OracleTypes.NUMBER),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("V_COD_OBS", OracleTypes.NUMBER),
							new SqlParameter("I_V_IND_CUMPLMNTO", OracleTypes.VARCHAR),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("V_N_IDEMP", requestSO.getTrabajador() != null ? requestSO.getTrabajador().getCodigo() : null);
			in.addValue("V_D_FEC_PROG", requestSO.getFechaProgramacion());
			in.addValue("V_ORD_SERV", requestSO.getOrdenServicio());
			in.addValue("V_ESTADO", requestSO.getEstado() != null ? requestSO.getEstado().getId() : 1);
			in.addValue("V_IND_FOTO", requestSO.getFoto() != null ? requestSO.getFoto().getCodigo() : 1);
			in.addValue("V_TIP_ACT", 1);
			in.addValue("V_TIP_ZONA", requestSO.getZona() != null ? requestSO.getZona().getCodigo() : 1);
			in.addValue("V_FEC_EJEC", requestSO.getFechaEjecucion());
			in.addValue("V_TIP_ORDEN",
					requestSO.getTipoOrdenServicio() != null ? Integer.parseInt(requestSO.getTipoOrdenServicio().getCodigo()): 0);
			in.addValue("V_NIS", requestSO.getSuministro());
			in.addValue("V_COD_OBS",
					requestSO.getCodObservacion() != null ? requestSO.getCodObservacion().getCodigo() : null);
			in.addValue("I_V_IND_CUMPLMNTO", cumplimiento);

			out = this.jdbcCall.execute(in);
			Integer resultado = CastUtil.leerValorMapInteger(out, "N_RESP");
			if (resultado == 1) {
				listaMonitoreoDet = MonitoreoDetalleSO.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleSO()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarMonitoreoDetalleSO()] - " + e.getMessage());
		}
		return listaMonitoreoDet;
	}

	@Override
	public List<Map<String, Object>> generarArchivoExcelMonitoreo(MonitoreoCabeceraRequest requestMonitoreo, String idActividad) {
		List<Map<String, Object>> listContenido = new ArrayList<Map<String, Object>>();
		Map<String, Object> out = null;
		try {

			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_EXPORTAR_MONITOREO)
					.declareParameters(new SqlParameter("V_N_IDEMP", OracleTypes.NUMBER),
							new SqlParameter("V_D_FEC_PROG_INI", OracleTypes.DATE),
							new SqlParameter("V_D_FEC_PROG_FIN", OracleTypes.DATE),
							new SqlParameter("V_COD_EMP_DET", OracleTypes.VARCHAR),
							new SqlParameter("V_ESTA_EJEC", OracleTypes.NUMBER),
							new SqlParameter("V_ACTIVIDAD", OracleTypes.VARCHAR),
							new SqlParameter("V_NIS", OracleTypes.NUMBER),
							new SqlParameter("V_MEDIDOR", OracleTypes.VARCHAR),
							new SqlParameter("V_ID_OFIC", OracleTypes.NUMBER),
							new SqlParameter("V_PERIODO", OracleTypes.VARCHAR),
							new SqlParameter("V_CICLO", OracleTypes.VARCHAR),
							new SqlParameter("V_IND_INC", OracleTypes.NUMBER),
							new SqlParameter("V_IND_FOTO", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_OS", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_CEDULA", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_LOTE", OracleTypes.NUMBER),
							new SqlParameter("V_NUM_OT", OracleTypes.NUMBER),
							new SqlParameter("V_TDOC_NOTI", OracleTypes.NUMBER),
							new SqlParameter("V_SUB_ACT", OracleTypes.VARCHAR),							
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_N_IDEMP", requestMonitoreo.getContratista() != null ? requestMonitoreo.getContratista().getCodigo(): null)
					.addValue("V_D_FEC_PROG_INI", requestMonitoreo.getFechaProgramacionInicio())
					.addValue("V_D_FEC_PROG_FIN", requestMonitoreo.getFechaProgramacionFin())
					.addValue("V_COD_EMP_DET", requestMonitoreo.getOperario() != null ? requestMonitoreo.getOperario().getCodigo() : null)
					.addValue("V_ESTA_EJEC", requestMonitoreo.getEstado() != null ? requestMonitoreo.getEstado().getCodigo() : 1)
					.addValue("V_ACTIVIDAD", idActividad)
					.addValue("V_NIS", requestMonitoreo.getSuministro())
					.addValue("V_MEDIDOR", requestMonitoreo.getMedidor())
					.addValue("V_ID_OFIC", requestMonitoreo.getOficina() != null ? requestMonitoreo.getOficina().getCodigo() : null)
					.addValue("V_PERIODO", requestMonitoreo.getPeriodo() != null ? requestMonitoreo.getPeriodo().getCodigo() : null)
					.addValue("V_CICLO", requestMonitoreo.getCiclo() != null ? requestMonitoreo.getCiclo().getCodigo() : null)
					.addValue("V_IND_INC", requestMonitoreo.getImposibilidad() != null ? requestMonitoreo.getImposibilidad().getCodigo() : 1)
					.addValue("V_IND_FOTO", requestMonitoreo.getFoto() != null ? requestMonitoreo.getFoto().getCodigo() : 1)
					.addValue("V_NUM_OS", requestMonitoreo.getOrdenServicio())
					.addValue("V_NUM_CEDULA", requestMonitoreo.getCedulaNotificacion())
					.addValue("V_NUM_LOTE", requestMonitoreo.getNumeroLote())
					.addValue("V_NUM_OT", requestMonitoreo.getOrdenTrabajo())
					.addValue("V_TDOC_NOTI", requestMonitoreo.getTipoNotificacion()) 
					.addValue("V_SUB_ACT", requestMonitoreo.getTipoActividad() != null ? requestMonitoreo.getTipoActividad().getCodigo() : null)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listContenido =  (List<Map<String, Object>>) out.get("C_OUT");
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listarDetalleAsignacionTrabajo()] - " + e.getMessage());
		}
		
		return listContenido;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> cargaArchivoProgramacionMasiva(List<Object> listaCarga, Integer codEmp, String codActividad,
			Integer codEmpresa, String nroCarga, String fecReasignacion) throws Exception {

		Map<String, Object> out = new HashMap<String, Object>();

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_VALIDAR_TRAMA_PERSONAL_REP_CONT)
					.declareParameters(new SqlParameter("TAB_I_LISTA_TRAMA", OracleTypes.ARRAY),
							new SqlParameter("V_USUA_MOD", OracleTypes.NUMBER),
							new SqlParameter("V_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("V_COD_ACT", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FECREPRO", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));

			Connection conn = this.jdbcCall.getJdbcTemplate().getDataSource().getConnection()
					.unwrap(OracleConnection.class);
			Array array = ((OracleConnection) conn).createOracleArray("AGC.TYPE_AGC_TAB_TRAMA_PROGRAMACION",
					listaCarga.toArray());

			MapSqlParameterSource in = new MapSqlParameterSource().addValue("TAB_I_LISTA_TRAMA", array)
					.addValue("V_USUA_MOD", codEmp)
					.addValue("V_COD_EMP", codEmpresa)
					.addValue("V_COD_ACT", codActividad)
					.addValue("V_D_FEC_REP", fecReasignacion)
					;

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				return out;
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: MonitoreoDAOImpl - cargaArchivoProgramacion()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: MonitoreoDAOImpl - cargaArchivoProgramacion()] - " + e.getMessage());
			throw new Exception();
		}

		return out;
	}

	@Override
	public Map<String, Object> listaDetalleReasignaciones(MonitoreoRequest requestMonitoreo) {
		Map<String, Object> listaDetaCargaTrab = new HashMap<String, Object>();
		Map<String, Object> out = null;

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_LIST_MON_REPROGRAMACION)
					.declareParameters(new SqlParameter("V_IDACTI", OracleTypes.VARCHAR),
							new SqlParameter("V_IDEMPR", OracleTypes.NUMBER),
							new SqlParameter("V_COD_EMP", OracleTypes.NUMBER),
							new SqlParameter("V_NOM_EMP", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_EMP_ANT", OracleTypes.NUMBER),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_IDACTI", requestMonitoreo.getActividad() != null ? requestMonitoreo.getActividad().getCodigo():null)
					.addValue("V_IDEMPR", requestMonitoreo.getContratista() != null ? requestMonitoreo.getContratista().getCodigo():null)
					.addValue("V_COD_EMP",requestMonitoreo.getTrabajador() != null ? requestMonitoreo.getTrabajador().getCodigo():null)
					.addValue("V_NOM_EMP",requestMonitoreo.getTrabajador() != null ? requestMonitoreo.getTrabajador().getNombre():null)
					.addValue("V_COD_EMP_ANT",requestMonitoreo.getTrabajadorAnt()!= null ? requestMonitoreo.getTrabajadorAnt().getCodigo():null)
							;

			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				listaDetaCargaTrab = ReprogramacionDetalle.mapper(out);
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - listaDetalleReasignaciones()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - listaDetalleReasignaciones()] - " + e.getMessage());
		}
		return listaDetaCargaTrab;
	}

	@Override
	public Map<String, Object> reasignarTrabajadorManual(ReprogramacionRequest requestReprogramacion) {
		Map<String, Object> listaDetaCargaTrab = new HashMap<String, Object>();
		Map<String, Object> out = null;

		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PROGRAMACION)
					.withProcedureName(DbConstants.PRC_VALIDAR_TRAMA_PERSONAL_REP_MANU)
					.declareParameters(new SqlParameter("V_COD_EMP_DET_ANT", OracleTypes.VARCHAR),
							new SqlParameter("V_COD_EMP_DET_NUEV", OracleTypes.VARCHAR),
							new SqlParameter("V_IDCARGA", OracleTypes.VARCHAR),
							new SqlParameter("V_NIS", OracleTypes.VARCHAR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
			MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("V_COD_EMP_DET_ANT", requestReprogramacion.getTrabajadorAntiguo() != null ? requestReprogramacion.getTrabajadorAntiguo().getCodigo():null)
					.addValue("V_COD_EMP_DET_NUEV", requestReprogramacion.getTrabajadorNuevo() != null ? requestReprogramacion.getTrabajadorNuevo().getCodigo():null)
					.addValue("V_IDCARGA", requestReprogramacion.getNroCarga())
					.addValue("V_NIS", requestReprogramacion.getSuministro());
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			if (resultado == 1) {
				return out;
			} else {
				String mensajeInterno = (String) out.get("V_EJEC");
				new Error(resultado, "9999", Constantes.MESSAGE_ERROR.get(9999));
				logger.error("[AGC: DigitalizadoDAOImpl - reasignarTrabajadorManual()] - " + mensajeInterno);
			}
		} catch (Exception e) {
			new Error(9999, Constantes.MESSAGE_ERROR.get(9999));
			logger.error("[AGC: DigitalizadoDAOImpl - reasignarTrabajadorManual()] - " + e.getMessage());
		}
		return listaDetaCargaTrab;
	}
	

	@Override
	public List<Adjunto> listarAdjuntosMonitoreo(VisorMonitoreoRequest request) {
		Map<String, Object> out = null;
		List<Adjunto> listaAdjuntos = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PCK_AGC_ENVIOENLINEA)
				.withProcedureName(DbConstants.PRC_LIST_ADJU_MONITOREO)
				.declareParameters(
						new SqlParameter("V_V_IDCARG", OracleTypes.VARCHAR),
						new SqlParameter("V_N_IDREG", OracleTypes.INTEGER),
						new SqlParameter("V_C_IDACTI", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT_ADJU", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR));
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_V_IDCARG", request.getNumeroCarga())				
				.addValue("V_N_IDREG", request.getIdCargaDetalle())			
				.addValue("V_C_IDACTI", request.getActividad());
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal) out.get("N_RESP")).intValue();
			
			if (resultado == 1) {
				listaAdjuntos = mapearDocumentosAdjuntos(out, request.getActividad());
			}else {
				String mensajeInterno = (String) out.get("V_EJEC");
				this.error = new Error(resultado, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
				logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosCT()] - "+mensajeInterno);
			}
			
		}catch(Exception e){
			this.error = new Error(0, "9999", Constantes.MESSAGE_VISOR_PDF.get(1001));
			logger.error("[AGC: DigitalizadoDAOImpl - listarAdjuntosDigitalizadosCT()] - "+e.getMessage());
		}
		return listaAdjuntos;			
	}
	
	private List<Adjunto> mapearDocumentosAdjuntos(Map<String, Object> resultados, String tipoActividad){
		List<Adjunto> listaAdjuntos = new ArrayList<Adjunto>();
		Adjunto adjunto = null;
		
		List<Map<String, Object>> mapListaAdjuntos = (List<Map<String, Object>>)resultados.get("C_OUT_ADJU");
		
		for(Map<String, Object> map: mapListaAdjuntos) {
			adjunto = new Adjunto();
			if(tipoActividad.equals("SG")) {
				adjunto.setUidCarga((String)map.get("N_NRO_OT").toString());
			}else {
				adjunto.setUidCarga((String)map.get("V_IDCARGA"));
				adjunto.setUidRegistro(((BigDecimal) map.get("N_IDREG")).intValue());
			}
			adjunto.setUidAdjunto(((BigDecimal) map.get("N_IDARCHI")).intValue());
			adjunto.setNombre((String)map.get("V_NOMBRADJUN"));
			adjunto.setExtension((String)map.get("V_EXTENSION"));
			adjunto.setRuta((String)map.get("V_RUTAADJU"));
			/*agregado para visor de imagenes - rramirez*/
			Timestamp fecCreacion = (Timestamp) map.get("A_D_FECCRE");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String fechaString  = dateFormat.format(fecCreacion);
			adjunto.setFechaCreacionConHora(fechaString);
			/**/
			listaAdjuntos.add(adjunto);
		}		
		return listaAdjuntos;
	}


}
