package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitoreoDetalleDA {

	private String direccion;
	private Integer suministro;
	private ParametroSGC incidencia;
	private ParametroSGC tipoEntrega;
	private String fechaDistribucion;
	private String horaDistribucion;
	private EstadoAsignacion estado;
	private Zona zona;
	private ParametroSGC completa;
	private Parametro foto;
	private String imagen1;
	private String imagen2;
	private String imagen3;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Number getSuministro() {
		return suministro;
	}

	public void setSuministro(Integer suministro) {
		this.suministro = suministro;
	}

	public ParametroSGC getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(ParametroSGC incidencia) {
		this.incidencia = incidencia;
	}

	public ParametroSGC getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(ParametroSGC tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public String getFechaDistribucion() {
		return fechaDistribucion;
	}

	public void setFechaDistribucion(String fechaDistribucion) {
		this.fechaDistribucion = fechaDistribucion;
	}

	public String getHoraDistribucion() {
		return horaDistribucion;
	}

	public void setHoraDistribucion(String horaDistribucion) {
		this.horaDistribucion = horaDistribucion;
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

	public Parametro getFoto() {
		return foto;
	}

	public void setFoto(Parametro foto) {
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
		MonitoreoDetalleDA item = null;
		List<MonitoreoDetalleDA> listaDetalle = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();

		if (respuestaConsulta.get("C_OUT") != null) {
			List<Map<String, Object>> lista = (List<Map<String, Object>>) respuestaConsulta.get("C_OUT");

			if (lista.size() > 0) {
				for (Map<String, Object> map : lista) {
					item = new MonitoreoDetalleDA();

					item.setDireccion(CastUtil.leerValorMapStringOriginCase(map, "DIR"));
					item.setSuministro(CastUtil.leerValorMapInteger(map, "NIS"));

					ParametroSGC incidencia = new ParametroSGC();
					incidencia.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "DES"));
					incidencia.setCodigo(CastUtil.leerValorMapStringOriginCase(map, "IMP"));
					item.setIncidencia(incidencia);

					ParametroSGC tipoEntrega = new ParametroSGC();
					tipoEntrega.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "TEN"));
					item.setTipoEntrega(tipoEntrega);

					item.setFechaDistribucion(CastUtil.leerValorMapStringOriginCase(map, "F_DIS"));
					item.setHoraDistribucion(CastUtil.leerValorMapStringOriginCase(map, "H_DIS"));

					EstadoAsignacion estadoAsignacion = new EstadoAsignacion();
					estadoAsignacion.setDescripcion(CastUtil.leerValorMapStringOriginCase(map, "EST"));
					item.setEstado(estadoAsignacion);

					Zona zona = new Zona();
					
					zona.setCodigo(CastUtil.leerValorMapInteger(map, "COD_ZONA"));
					zona.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "DESC_ZONA"));
					item.setZona(zona);

					Parametro foto = new Parametro();
					foto.setCodigo(CastUtil.leerValorMapInteger(map, "FLAG_FOTO"));
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

		result.put("listarMonitoreoDetalle", listaDetalle);

		return result;
	}

}
