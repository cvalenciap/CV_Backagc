package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.DataCorreoCarga;
import pe.com.sedapal.agc.util.CastUtil;

@Component
public class DataCorreoCargaPersonalMapper implements ObjectMapper<DataCorreoCarga> {

	@SuppressWarnings("unchecked")
	@Override
	public List<DataCorreoCarga> mapearRespuestaBd(Map<String, Object> map) {
		final List<DataCorreoCarga> respuesta = new ArrayList<>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) map.get("O_C_DATA");
		if (lista != null) {
			lista.forEach(item -> {
				DataCorreoCarga data = new DataCorreoCarga();
				data.setCodCargo(CastUtil.leerValorMapString(item, "COD_CARGO"));
				data.setCargo(CastUtil.leerValorMapString(item, "CARGO"));
				data.setPersonal(CastUtil.leerValorMapString(item, "PERSONAL"));
				respuesta.add(data);
			});
		}
		return respuesta;
	}

	@Override
	public Integer obtenerTotalregistros(Map<String, Object> map) {
		throw new AgcException("No implemented metjod");
	}

}
