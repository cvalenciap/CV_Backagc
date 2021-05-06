package pe.com.sedapal.agc.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import pe.com.sedapal.agc.model.RegistroAlta;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.response.DarAltaResponse;
import pe.com.sedapal.agc.util.CastUtil;

@Component
public class DarAltaMapper {

	@SuppressWarnings("unchecked")
	public DarAltaResponse obtenerDarAltaResponse(Map<String, Object> map) {
		DarAltaResponse respuesta = new DarAltaResponse();
		respuesta.setEstado(CastUtil.leerValorMapString(map, "O_V_ESTADO").equals("CORRECTO") ? ResultadoCarga.CORRECTO
				: ResultadoCarga.INCORRECTO);
		List<Map<String, Object>> errores = (List<Map<String, Object>>) map.get("C_O_ERRORES");
		List<Map<String, Object>> detalle = (List<Map<String, Object>>) map.get("C_O_DETALLE");
		if (errores != null) {
			errores.forEach(item -> {
				respuesta.getErrores().add(CastUtil.leerValorMapString(item, "COLUMN_VALUE"));
			});
		}

		if (detalle != null) {
			detalle.forEach(item -> {
				RegistroAlta reg = new RegistroAlta();
				reg.setCodEmpleado(CastUtil.leerValorMapInteger(item, "COD_EMP"));
				reg.setDni(CastUtil.leerValorMapString(item, "DNI"));
				reg.setNombres(CastUtil.leerValorMapString(item, "NOMBRES"));
				reg.setCodCargo(CastUtil.leerValorMapString(item, "COD_CARGO"));
				reg.setDescCargo(CastUtil.leerValorMapString(item, "DESC_CARGO"));
				reg.setCodOficina(CastUtil.leerValorMapInteger(item, "COD_OFICINA"));
				reg.setDescOficina(CastUtil.leerValorMapString(item, "DESC_OFICINA"));
				reg.setCodItem(CastUtil.leerValorMapInteger(item, "COD_ITEM"));
				reg.setCodContratista(CastUtil.leerValorMapInteger(item, "COD_CONTRATISTA"));
				reg.setDescContratista(CastUtil.leerValorMapString(item, "DESC_CONTRATISTA"));
				reg.setCodMotAlta(CastUtil.leerValorMapInteger(item, "COD_MOTIVO_ALTA"));
				reg.setIndUsuarioAgc(CastUtil.leerValorMapInteger(item, "IND_USUARIO_AGC"));
				reg.setUsuarioAgc(CastUtil.leerValorMapString(item, "USUARIO_AGC"));
				reg.setUsuarioAlta(CastUtil.leerValorMapString(item, "USUARIO_ALTA"));
				reg.setUsuarioCreacion(CastUtil.leerValorMapString(item, "USUARIO_CREACION"));
				respuesta.getPersonalAlta().add(reg);
			});
		}

		return respuesta;
	}

}
