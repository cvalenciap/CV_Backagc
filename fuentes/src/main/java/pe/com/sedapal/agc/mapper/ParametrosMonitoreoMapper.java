package pe.com.sedapal.agc.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.ParametroSGC;
import pe.com.sedapal.agc.model.ParametrosCargaBandeja;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;
import pe.com.sedapal.agc.util.Constantes.ParametrosMonitoreo;
import pe.com.sedapal.agc.util.Constantes.ParametrosSGCMonitoreo;

public class ParametrosMonitoreoMapper {

	@SuppressWarnings("unchecked")
	public static ParametrosCargaBandeja mapearParametrosBusqueda(Map<String, Object> respuestaConsulta)
			throws Exception {
		ParametrosCargaBandeja parametros = new ParametrosCargaBandeja();

		if (respuestaConsulta.get("C_OUT_PARAMETROS") != null) {

			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_PARAMETROS");

			Map<Integer, List<Map<String, Object>>> listasParametros = listaMaps.stream()
					.collect(Collectors.groupingBy(m -> Integer.parseInt(String.valueOf(m.get("N_IDTIPOPARA")))));

			List<Actividad> listaActividades = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_ACTIVIDAD).stream()
					.map(Actividad::fromParamMapper).collect(Collectors.toList());

			List<Parametro> listaEstadoImposibilidad = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_EST_IMPOSIBI)
					.stream().map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaEstado = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_EST_EJE).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaExisteFoto = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_FILTRO_FOTO).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaTipoActividad = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_ACTIVIDAD_SED)
					.stream().map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaIncidencia = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_INCIDEN_TE).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaSemaforo = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_SEMAFORO_MON).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaCumplimiento = listasParametros.get(ParametrosMonitoreo.TIPO_IND_CUMPLIMIENTO).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaZona = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_ZONA).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
				
			List<Parametro> listaImposibilidad = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_IMPOSIBILIDAD).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaTipoInspeccion = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_INSPECCIONES).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaEstadoMedidor = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_ESTADO_MED).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaEstadoServicio = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_ESTADO_SUM).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> codObservacion = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_OBSERVACION).stream()
					.map(Parametro::mapper).collect(Collectors.toList());

			List<Parametro> listaTipoInstalacion = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_TIPO_INSTALAC).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaCodObservacion = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_OBSERVA_SGIO).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaTipoNotificacion = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_TIPO_NOTIF).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			List<Parametro> listaAlertaFrecuencia = listasParametros.get(ParametrosMonitoreo.TIPO_PARAM_FREC_ALERTA).stream()
					.map(Parametro::mapper).collect(Collectors.toList());
			
			parametros.setListaActividad(listaActividades);
			parametros.setListaImposibilidad(listaEstadoImposibilidad);
			parametros.setListaEstado(listaEstado);
			parametros.setListaExisteFoto(listaExisteFoto);
			parametros.setListaTipoActividad(listaTipoActividad);
			parametros.setListaIncidencia(listaIncidencia);
			parametros.setListaSemaforo(listaSemaforo);
			parametros.setListaCumplimiento(listaCumplimiento);
			parametros.setListaZona(listaZona);
			parametros.setListaResultadoInspeccion(listaTipoInspeccion);
			parametros.setListaTipoInspeccion(listaTipoInspeccion);
			parametros.setListaEstadoMedidor(listaEstadoMedidor);
			parametros.setListaEstadoServicio(listaEstadoServicio);
			parametros.setCodObservacion(codObservacion);
			parametros.setListaTipoInstalacion(listaTipoInstalacion);
			parametros.setListaCodObservacion(listaCodObservacion);
			parametros.setListaTipoNotificacion(listaTipoNotificacion);
			parametros.setListaAlertaFrecuencia(listaAlertaFrecuencia);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		if (respuestaConsulta.get("C_OUT_EMPR") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_EMPR");
			List<Empresa> listaContratista = new ArrayList<>();
			if (listaMaps.size() > 0) {
				Empresa itemEmpresa = null;
				for (Map<String, Object> map : listaMaps) {
					itemEmpresa = new Empresa();
					itemEmpresa.setCodigo(CastUtil.leerValorMapInteger(map, "N_IDEMPR"));
					itemEmpresa.setDescripcion(CastUtil.leerValorMapString(map, "V_NOMBREMPR"));
					listaContratista.add(itemEmpresa);
				}
			}
			parametros.setListaEmpresa(listaContratista);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		if (respuestaConsulta.get("C_OUT_OFIC") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_OFIC");
			List<Oficina> listaOficina = new ArrayList<>();
			if (listaMaps.size() > 0) {
				Oficina oficina = null;
				for (Map<String, Object> map : listaMaps) {
					oficina = new Oficina();
					oficina.setCodigo(((BigDecimal) map.get("N_IDOFIC")).intValue());
					oficina.setDescripcion((String) map.get("V_DESCOFIC"));
					listaOficina.add(oficina);
				}
			}
			parametros.setListaOficina(listaOficina);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		if (respuestaConsulta.get("C_OUT_PERI") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_PERI");
			List<Parametro> listaPeriodo = new ArrayList<>();
			if (listaMaps.size() > 0) {
				Parametro itemPeriodo = null;
				for (Map<String, Object> map : listaMaps) {
					itemPeriodo = new Parametro();
					itemPeriodo.setDetalle((String) map.get("PERIODO"));
					listaPeriodo.add(itemPeriodo);
				}
			}
			parametros.setListaPeriodo(listaPeriodo);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		if (respuestaConsulta.get("C_OUT_CICLO") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_CICLO");
			List<Parametro> listaCiclo = new ArrayList<>();
			if (listaMaps.size() > 0) {
				Parametro itemCiclo = null;
				for (Map<String, Object> map : listaMaps) {
					itemCiclo = new Parametro();
					itemCiclo.setCodigo(CastUtil.leerValorMapInteger(map, "N_IDOFIC"));
					itemCiclo.setDetalle(CastUtil.leerValorMapString(map, "CICLO_FACT"));
					listaCiclo.add(itemCiclo);
				}
			}
			parametros.setListaCiclo(listaCiclo);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		
		if (respuestaConsulta.get("C_OUT_TIPOS_SGC") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_TIPOS_SGC");
			List<ParametroSGC> listaEntrega = new ArrayList<>();
			List<ParametroSGC> listaTipoCarga = new ArrayList<>();
			List<ParametroSGC> listaTipoOrdServicio = new ArrayList<>();
			List<ParametroSGC> listaTipoOrdServicioSGIO = new ArrayList<>();
			List<ParametroSGC> listaTipoOrdTrabajoSGIO = new ArrayList<>();
			if (listaMaps.size() > 0) {
				
				for (Map<String, Object> map : listaMaps) {
					if (((String)map.get("CLASE")).equals(ParametrosSGCMonitoreo.TIPO_PARAM_ENTREGA)) {
						ParametroSGC itemEntrega = new ParametroSGC();
						itemEntrega.setCodigo(CastUtil.leerValorMapString(map, "TIPO"));
						itemEntrega.setDetalle(CastUtil.leerValorMapString(map, "DESC_TIPO"));
						listaEntrega.add(itemEntrega);
					}
					if (((String)map.get("CLASE")).equals(ParametrosSGCMonitoreo.TIPO_PARAM_CARGA)) {
						ParametroSGC itemCarga = new ParametroSGC();
						itemCarga.setCodigo(CastUtil.leerValorMapString(map, "TIPO"));
						itemCarga.setDetalle(CastUtil.leerValorMapString(map, "DESC_TIPO"));
						listaTipoCarga.add(itemCarga);
					}
					if (((String)map.get("CLASE")).equals(ParametrosSGCMonitoreo.TIPO_SGC_ORDEN_ME)) {
						ParametroSGC itemOrden = new ParametroSGC();
						itemOrden.setCodigo(CastUtil.leerValorMapString(map, "TIPO"));
						itemOrden.setDetalle(CastUtil.leerValorMapString(map, "DESC_TIPO"));
						listaTipoOrdServicio.add(itemOrden);
					}
					if (((String)map.get("CLASE")).equals(ParametrosSGCMonitoreo.TIPO_SGC_ORDEN_SERV )) {
						ParametroSGC itemOrdenServSGIO = new ParametroSGC();
						itemOrdenServSGIO.setCodigo(CastUtil.leerValorMapString(map, "TIPO"));
						itemOrdenServSGIO.setDetalle(CastUtil.leerValorMapString(map, "DESC_TIPO"));
						listaTipoOrdServicioSGIO.add(itemOrdenServSGIO);
					}
					if (((String)map.get("CLASE")).equals(ParametrosSGCMonitoreo.TIPO_SGC_ORDEN_TRAB )) {
						ParametroSGC itemOrdenTrabSGIO = new ParametroSGC();
						itemOrdenTrabSGIO.setCodigo(CastUtil.leerValorMapString(map, "TIPO"));
						itemOrdenTrabSGIO.setDetalle(CastUtil.leerValorMapString(map, "DESC_TIPO"));
						listaTipoOrdTrabajoSGIO.add(itemOrdenTrabSGIO);
					}
				}
			}
			parametros.setListaTipoEntrega(listaEntrega);
			parametros.setListaTipoCarga(listaTipoCarga);
			parametros.setListaTipoOrdServicio(listaTipoOrdServicio);
			parametros.setListaTipoOrdServicioSGIO(listaTipoOrdServicioSGIO);
			parametros.setListaTipoOrdTrabajoSGIO(listaTipoOrdTrabajoSGIO);
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		//Sub Actividades
		if (respuestaConsulta.get("C_OUT_SA_CR") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_CR");
			List<ParametroSGC> listaSubActividadCR = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadCR.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadCR(listaSubActividadCR);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_DA") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_DA");
			List<ParametroSGC> listaSubActividadDA = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadDA.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadDA(listaSubActividadDA);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_DC") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_DC");
			List<ParametroSGC> listaSubActividadDC = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadDC.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadDC(listaSubActividadDC);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_IC") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_IC");
			List<ParametroSGC> listaSubActividadIC = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadIC.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadIC(listaSubActividadIC);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_ME") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_ME");
			List<ParametroSGC> listaSubActividadME = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadME.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadME(listaSubActividadME);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_SO") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_SO");
			List<ParametroSGC> listaSubActividadSO = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadSO.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadDC(listaSubActividadSO);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}
		if (respuestaConsulta.get("C_OUT_SA_TE") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT_SA_TE");
			List<ParametroSGC> listaSubActividadTE = new ArrayList<>();
			if (listaMaps.size() > 0) {
				ParametroSGC itemSubAct = null;
				for (Map<String, Object> map : listaMaps) {
					itemSubAct = new ParametroSGC();
					itemSubAct.setCodigo(CastUtil.leerValorMapString(map, "V_IDSUBACTI"));
					itemSubAct.setDetalle(CastUtil.leerValorMapString(map, "SUB_ACTIVIDAD"));
					itemSubAct.setValor(CastUtil.leerValorMapString(map, "V_IDCHILD"));
					listaSubActividadTE.add(itemSubAct);
				}
			}
			parametros.setListaSubActividadTE(listaSubActividadTE);  
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		return parametros;
	}

}
