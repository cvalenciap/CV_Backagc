package pe.com.sedapal.agc.mapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.CabeceraCargaPersonal;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.TramaPersonal;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.response.CargaPersonalResponse;
import pe.com.sedapal.agc.util.CastUtil;

@Component
public class ResultadoCargaPersonalMapper implements ObjectMapper<CargaPersonalResponse> {

	@SuppressWarnings("unchecked")
	@Override
	public List<CargaPersonalResponse> mapearRespuestaBd(Map<String, Object> map) {
		final List<CargaPersonalResponse> respuesta = new LinkedList<>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) map.get("C_O_RESUMEN_CARGA");
		AtomicInteger contador = new AtomicInteger(1);
		lista.forEach(item -> {
			CargaPersonalResponse response = new CargaPersonalResponse();
			response.setNro(contador.getAndIncrement());
			response.setLoteCarga(CastUtil.leerValorMapString(item, "LOTE_CARGA"));
			response.setNroRegistro(CastUtil.leerValorMapInteger(item, "FILA"));
			response.setFechaDeCarga(CastUtil.leerValorMapString(item, "FEC_CARGA"));
			response.setNumeroDocumento(CastUtil.leerValorMapString(item, "DNI"));
			response.setCodEmpleado1(CastUtil.leerValorMapInteger(item, "COD_EMP_1"));
			response.setCodEmpleado2(CastUtil.leerValorMapInteger(item, "COD_EMP_2"));
			response.setDetalle(Arrays.asList(CastUtil.leerArregloCadena(item, "DETALLE", "\t")));
			if (CastUtil.leerValorMapString(item, "RESULTADO").equals("C")) {
				response.setResultado(ResultadoCarga.CORRECTO);
			} else {
				response.setResultado(ResultadoCarga.INCORRECTO);
			}
			respuesta.add(response);
		});
		return respuesta;
	}

	@Override
	public Integer obtenerTotalregistros(Map<String, Object> map) {
		throw new AgcException("No implemented method");
	}

}
