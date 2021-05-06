package pe.com.sedapal.agc.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.util.CastUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CeseMasivoResponse {

	private ResultadoCarga resultado;
	private String mensaje;
	private List<String> detalle = new ArrayList<String>();

	public ResultadoCarga getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoCarga resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<String> detalle) {
		this.detalle = detalle;
	}
	
	@SuppressWarnings("unchecked")
	public static CeseMasivoResponse mapper(Map<String, Object> map) {
		CeseMasivoResponse response = new CeseMasivoResponse();
		if (CastUtil.leerValorMapString(map, "O_V_RESULTADO").equals("CORRECTO")) {
			response.setResultado(ResultadoCarga.CORRECTO);
		} else if (CastUtil.leerValorMapString(map, "O_V_RESULTADO").equals("INCORRECTO")) {
			response.setResultado(ResultadoCarga.INCORRECTO);
		}
		response.setMensaje(CastUtil.leerValorMapString(map, "O_V_MSG_CESE"));
		List<Map<String, Object>> detalle = (List<Map<String, Object>>) map.get("O_C_DETALLE");
		if (detalle != null) {
			detalle.forEach(d -> {
				response.detalle.add(CastUtil.leerValorMapString(d, "COLUMN_VALUE"));
			});
		}
		return response;
	}

}
