package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitoreoDetalleME {

	private String direccion;
	private Integer suministro;
	private String ordenServicio;
	private String tipologia;
	private ParametroSGC tipoActividad;
	private String codObservacion;
	private ParametroSGC tipoInstalacion;
	private String medidorInstalado;
	private String medidorRetirado;
	private String fechaEjecucion;
	private String horaEjecucion;
	private EstadoAsignacion estado;
	private Zona zona;
	private ParametroSGC completa;
	private ParametroSGC foto;
	private String imagen1;
	private String imagen2;
	private String imagen3;
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getSuministro() {
		return suministro;
	}

	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}

	public String getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public ParametroSGC getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(ParametroSGC tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getCodObservacion() {
		return codObservacion;
	}

	public void setCodObservacion(String codObservacion) {
		this.codObservacion = codObservacion;
	}

	public ParametroSGC getTipoInstalacion() {
		return tipoInstalacion;
	}

	public void setTipoInstalacion(ParametroSGC tipoInstalacion) {
		this.tipoInstalacion = tipoInstalacion;
	}

	public String getMedidorInstalado() {
		return medidorInstalado;
	}

	public void setMedidorInstalado(String medidorInstalado) {
		this.medidorInstalado = medidorInstalado;
	}

	public String getMedidorRetirado() {
		return medidorRetirado;
	}

	public void setMedidorRetirado(String medidorRetirado) {
		this.medidorRetirado = medidorRetirado;
	}

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getHoraEjecucion() {
		return horaEjecucion;
	}

	public void setHoraEjecucion(String horaEjecucion) {
		this.horaEjecucion = horaEjecucion;
	}

	public EstadoAsignacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoAsignacion estado) {
		this.estado = estado;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public ParametroSGC getCompleta() {
		return completa;
	}

	public void setCompleta(ParametroSGC completa) {
		this.completa = completa;
	}

	public ParametroSGC getFoto() {
		return foto;
	}

	public void setFoto(ParametroSGC foto) {
		this.foto = foto;
	}

	public String getImagen1() {
		return imagen1;
	}
	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}
	public String getImagen2() {
		return imagen2;
	}
	public void setImagen2(String imagen2) {
		this.imagen2 = imagen2;
	}
	public String getImagen3() {
		return imagen3;
	}
	public void setImagen3(String imagen3) {
		this.imagen3 = imagen3;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> mapper(Map<String, Object> respuestaConsulta) throws Exception {
		Map<String, Object> resultado = new HashMap<>();
		List<MonitoreoDetalleME> listaDetalle = new ArrayList<>();

		if (respuestaConsulta.get("O_C_RESPUESTA") != null) {
			List<Map<String, Object>> maps = (List<Map<String, Object>>) respuestaConsulta.get("O_C_RESPUESTA");

			if (!maps.isEmpty()) {
				MonitoreoDetalleME item = null;
				for (Map<String, Object> map : maps) {
					item = new MonitoreoDetalleME();

					item.setDireccion(CastUtil.leerValorMapStringOriginCase(map, "DIRECCION"));
					item.setSuministro(CastUtil.leerValorMapInteger(map, "SUMINISTRO"));
					item.setOrdenServicio(CastUtil.leerValorMapStringOriginCase(map, "ORDEN_SERVICIO"));
					item.setTipologia(CastUtil.leerValorMapStringOriginCase(map, "TIPOLOGIA_OS"));

					ParametroSGC tipoActividad = new ParametroSGC();
					tipoActividad.setValor(CastUtil.leerValorMapStringOriginCase(map, "TIPO_ACTIVIDAD"));
					item.setTipoActividad(tipoActividad);

					item.setCodObservacion(CastUtil.leerValorMapStringOriginCase(map, "CODIGO_OBSERVACION"));

					ParametroSGC tipoInstalacion = new ParametroSGC();
					tipoInstalacion.setValor(CastUtil.leerValorMapStringOriginCase(map, "TIPO_INSTALACION"));
					item.setTipoInstalacion(tipoInstalacion);
					
					item.setMedidorInstalado(CastUtil.leerValorMapStringOriginCase(map, "MEDIDOR_INSTALADO"));
					item.setMedidorRetirado(CastUtil.leerValorMapStringOriginCase(map, "MEDIDOR_RETIRADO"));
					item.setFechaEjecucion(CastUtil.leerValorMapStringOriginCase(map, "FECHA_EJECUCION"));
					item.setHoraEjecucion(CastUtil.leerValorMapStringOriginCase(map, "HORA_EJECUCION"));
					
					EstadoAsignacion estadoAsigancion = new EstadoAsignacion();
					estadoAsigancion.setDescripcion(CastUtil.leerValorMapStringOriginCase(map, "ESTADO"));
					item.setEstado(estadoAsigancion);
					
					Zona zona = new Zona();
					zona.setCodigo(CastUtil.leerValorMapInteger(map, "COD_ZONA") );
					zona.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "DESC_ZONA"));
					item.setZona(zona);
					
					ParametroSGC foto = new ParametroSGC();
					foto.setCodigo(CastUtil.leerValorMapStringOriginCase(map, "FLAG_FOTOS"));
					item.setFoto(foto);
					
					ParametroSGC completa = new ParametroSGC();
					completa.setDetalle(CastUtil.leerValorMapString(map, "IND_CUMPLMNTO"));
					item.setCompleta(completa);
					
					item.setImagen1(CastUtil.leerValorMapString(map, "IMAGEN_1"));
					item.setImagen2(CastUtil.leerValorMapString(map, "IMAGEN_2"));
					item.setImagen2(CastUtil.leerValorMapString(map, "IMAGEN_3"));
					
					listaDetalle.add(item);
				}
			}

		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		resultado.put("listarMonitoreoDetalle", listaDetalle);
		return resultado;
	}

}
