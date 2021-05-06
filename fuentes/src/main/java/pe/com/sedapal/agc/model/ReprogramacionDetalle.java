package pe.com.sedapal.agc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes.MensajesErrores;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReprogramacionDetalle {

	private Trabajador trabajador;
	private String fechaAsignacion;

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public String getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> mapper(Map<String, Object> respuestaConsulta) throws Exception {
		Map<String, Object> resultado = new HashMap<>();
		List<ReprogramacionDetalle> listaDetalle = new ArrayList<>();

		if (respuestaConsulta.get("C_OUT") != null) {
			List<Map<String, Object>> listaMaps = (List<Map<String,Object>>) respuestaConsulta.get("C_OUT");
			if (!listaMaps.isEmpty()) {
				ReprogramacionDetalle item = null;
				for (Map<String, Object> map : listaMaps) {
					item = new ReprogramacionDetalle();
					
					Trabajador trabajador = new Trabajador();
					
					trabajador.setCodigo(CastUtil.leerValorMapInteger(map, "CODIGO"));
					trabajador.setNombre(CastUtil.leerValorMapString(map, "NOMBRES"));
					trabajador.setDocumento(CastUtil.leerValorMapString(map, "DNI"));
					
					item.setTrabajador(trabajador);
					item.setFechaAsignacion(CastUtil.leerValorMapString(map, "FECHA_ASIGNACION"));

					listaDetalle.add(item);
				}
			}
		} else {
			throw new Exception(MensajesErrores.MAPPER_LISTA_NULL);
		}

		resultado.put("listarReprogramacionDetalle", listaDetalle);
		return resultado;
	}

}
