package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitoreoDetalleSO {

	private String direccion;
	private Integer suministro;
	private String lecturaCierre;
	private String ordenServicio;
	private String tipOrdServicio;
	private ParametroSGC tipoActividad;
	private String codActividad;
	private String codObservacion;
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

	public String getLecturaCierre() {
		return lecturaCierre;
	}

	public void setLecturaCierre(String lecturaCierre) {
		this.lecturaCierre = lecturaCierre;
	}

	public String getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public String getTipOrdServicio() {
		return tipOrdServicio;
	}

	public void setTipOrdServicio(String tipOrdServicio) {
		this.tipOrdServicio = tipOrdServicio;
	}

	public ParametroSGC getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(ParametroSGC tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getCodActividad() {
		return codActividad;
	}

	public void setCodActividad(String codActividad) {
		this.codActividad = codActividad;
	}

	public String getCodObservacion() {
		return codObservacion;
	}

	public void setCodObservacion(String codObservacion) {
		this.codObservacion = codObservacion;
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
		List<MonitoreoDetalleSO> listaDetalle = new ArrayList<>();

		if (respuestaConsulta.get("C_OUT") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String,Object>>) respuestaConsulta.get("C_OUT");
			if (!listaMaps.isEmpty()) {
				MonitoreoDetalleSO item = null;
				for (Map<String, Object> map : listaMaps) {
					item = new MonitoreoDetalleSO();
					
					item.setSuministro(CastUtil.leerValorMapInteger(map, "NIS"));
					item.setDireccion(CastUtil.leerValorMapStringOriginCase(map, "DIR"));
					item.setOrdenServicio(CastUtil.leerValorMapStringOriginCase(map, "NRO_ORD"));
					item.setTipOrdServicio(CastUtil.leerValorMapStringOriginCase(map, "TIP_ORD"));
					item.setCodActividad(CastUtil.leerValorMapStringOriginCase(map, "CACT"));
					
					ParametroSGC tipoActividadSGC = new ParametroSGC();
					tipoActividadSGC.setCodigo(CastUtil.leerValorMapStringOriginCase(map, "TIP_ACT"));
					item.setTipoActividad(tipoActividadSGC);
					
					item.setCodObservacion(CastUtil.leerValorMapStringOriginCase(map, "OBSE"));
					item.setLecturaCierre(CastUtil.leerValorMapStringOriginCase(map, "LECT"));
					item.setFechaEjecucion(CastUtil.leerValorMapStringOriginCase(map, "FEJE"));
					item.setHoraEjecucion(CastUtil.leerValorMapStringOriginCase(map, "HEJE"));
					
					EstadoAsignacion estado = new EstadoAsignacion();
					estado.setDescripcion(CastUtil.leerValorMapStringOriginCase(map, "EST"));
					item.setEstado(estado);
					
					Zona zona = new Zona();
					zona.setCodigo(CastUtil.leerValorMapInteger(map, "COD_ZONA"));
					zona.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "DESC_ZONA"));
					item.setZona(zona);
					
					ParametroSGC foto = new ParametroSGC();
					foto.setCodigo(CastUtil.leerValorMapString(map, "FLAG_FOTO"));
					item.setFoto(foto);
					
					ParametroSGC completa = new ParametroSGC();
					completa.setDetalle(CastUtil.leerValorMapString(map, "IND_CUMPLMNTO"));
					item.setCompleta(completa);
					
					item.setImagen1(CastUtil.leerValorMapString(map, "IMAGEN_1"));
					item.setImagen2(CastUtil.leerValorMapString(map, "IMAGEN_2"));
					item.setImagen3(CastUtil.leerValorMapString(map, "IMAGEN_3"));
					
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
