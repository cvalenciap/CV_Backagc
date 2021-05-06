package pe.com.sedapal.agc.model.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.util.CastUtil;

@JsonInclude(Include.NON_NULL)
public class CesarPersonalResponse {

	private ResultadoCarga resultado;
	private String mensaje;

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
	
	public static CesarPersonalResponse mapper(Map<String, Object> map) {
		CesarPersonalResponse response = new CesarPersonalResponse();
		response.setResultado(ResultadoCarga.INCORRECTO);
		if (map != null) {
			response.setMensaje(CastUtil.leerValorMapString(map, "O_V_DETALLE"));
			if (CastUtil.leerValorMapString(map, "O_V_RESULTADO").equals("INCORRECTO")) {				
				response.setResultado(ResultadoCarga.INCORRECTO);
			} else if (CastUtil.leerValorMapString(map, "O_V_RESULTADO").equals("CORRECTO")) {
				response.setResultado(ResultadoCarga.CORRECTO);
			}
		}
		return response;
	}

}
