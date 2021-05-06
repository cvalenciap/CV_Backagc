package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;

public class MonitoreoDetalleDC {
	
	private Number suministro;
	private String direccion;
	private String nroCarta;
	private String nroVisita;
	private ParametroSGC tipoEntrega;
	private String fechaNotificacion;
	private String horaNotificacion;
	private ParametroSGC incidencia;
	private String descripcion;
	private Parametro estado;
	private Zona zona;
	private Parametro completa;
	private Parametro foto;
	private String imagen1;
	private String imagen2;
	private String imagen3;
	
	public Number getSuministro() {
		return suministro;
	}
	public void setSuministro(Number suministro) {
		this.suministro = suministro;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getNroCarta() {
		return nroCarta;
	}
	public void setNroCarta(String nroCarta) {
		this.nroCarta = nroCarta;
	}
	public String getNroVisita() {
		return nroVisita;
	}
	public void setNroVisita(String nroVisita) {
		this.nroVisita = nroVisita;
	}
	public ParametroSGC getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(ParametroSGC tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getHoraNotificacion() {
		return horaNotificacion;
	}
	public void setHoraNotificacion(String horaNotificacion) {
		this.horaNotificacion = horaNotificacion;
	}
	public ParametroSGC getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(ParametroSGC incidencia) {
		this.incidencia = incidencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Parametro getEstado() {
		return estado;
	}
	public void setEstado(Parametro estado) {
		this.estado = estado;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Parametro getCompleta() {
		return completa;
	}
	public void setCompleta(Parametro completa) {
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
		MonitoreoDetalleDC item = null;
		List<MonitoreoDetalleDC> listaDetalle = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		
		if (respuestaConsulta.get("O_C_RESPUESTA") != null) {
			List<Map<String, Object>> lista = (List<Map<String, Object>>) respuestaConsulta.get("O_C_RESPUESTA");
			
			if (lista.size() > 0) {
				for (Map<String, Object> map : lista) {
					item = new MonitoreoDetalleDC();
					
					item.setDireccion(CastUtil.leerValorMapStringOriginCase(map, "DIRECCION"));
					item.setSuministro(CastUtil.leerValorMapInteger(map, "SUMINISTRO"));
					
					item.setNroCarta(CastUtil.leerValorMapStringOriginCase(map, "NUMERO_CARTA_RESOL"));
					item.setNroVisita(CastUtil.leerValorMapStringOriginCase(map, "NUMERO_VISITA"));
					
					ParametroSGC incidencia = new ParametroSGC();
					incidencia.setValor(CastUtil.leerValorMapStringOriginCase(map, "DESCRIPCION"));
					incidencia.setCodigo(CastUtil.leerValorMapStringOriginCase(map, "INCIDENCIA"));
					item.setIncidencia(incidencia);
					
					ParametroSGC tipoEntrega = new ParametroSGC();
					tipoEntrega.setValor(CastUtil.leerValorMapStringOriginCase(map, "TIPO_ENTREGA"));
					item.setTipoEntrega(tipoEntrega);
					
					item.setFechaNotificacion(CastUtil.leerValorMapStringOriginCase(map, "FECHA_NOTIF"));
					item.setHoraNotificacion(CastUtil.leerValorMapStringOriginCase(map, "HORA_NOTIF"));
					
					Parametro estado = new Parametro();
					estado.setEstado(CastUtil.leerValorMapStringOriginCase(map, "ESTADO"));
					item.setEstado(estado);
					
					Zona zona = new Zona();
					zona.setCodigo(CastUtil.leerValorMapInteger(map, "COD_ZONA"));
					zona.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "DESC_ZONA"));
					item.setZona(zona);
					
					Parametro foto = new Parametro();
					foto.setCodigo(CastUtil.leerValorMapInteger(map, "FLAG_FOTOS"));
					item.setFoto(foto);
					
					Parametro completa = new Parametro();
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
