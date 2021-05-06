package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Movimiento;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Solicitud;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;

@Component
public class MovimientoMapper implements ObjectMapper<Movimiento> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimiento> mapearRespuestaBd(Map<String, Object> map) {
		final List<Movimiento> listaMovimientos = new ArrayList<>();
		List<Map<String, Object>> lista = null;
		if (map.get("O_C_RESULTADO") != null) {
			lista = (List<Map<String, Object>>) map.get("O_C_RESULTADO");
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}
		
		lista.forEach(item -> {
			Movimiento movimiento = new Movimiento();
			
			movimiento.setNro(CastUtil.leerValorMapInteger(item, "RNUM"));
			movimiento.setIdMovimiento(CastUtil.leerValorMapInteger(item, "IDMOVIMIENTO"));
			movimiento.setCodigoEmpleado(CastUtil.leerValorMapInteger(item, "CODIGOSEDAPAL"));
			movimiento.setNumeroDocumento(CastUtil.leerValorMapString(item, "NUMERODOCUMENTO"));
			movimiento.setNombreEmpleado(CastUtil.leerValorMapStringOriginCase(item, "NOMBRECOMPLETO"));
			
			Empresa empresa = new Empresa();
			empresa.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "NOMBRECONTRATISTA"));
			movimiento.setEmpresa(empresa);
			
			Solicitud solicitud = new Solicitud();
			solicitud.setDescripcionSolicitud(CastUtil.leerValorMapStringOriginCase(item, "MOTIVOSOLICITUD"));
			movimiento.setSolicitud(solicitud);
			
			Cargo cargoOrigen = new Cargo();
			cargoOrigen.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "CARGOORIGEN"));
			movimiento.setCargoActual(cargoOrigen);
			
			Oficina oficinaOrigen = new Oficina();
			oficinaOrigen.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "OFICINAORIGEN"));
			movimiento.setOficinaActual(oficinaOrigen);
			
			Item itemOrigen = new Item();
			itemOrigen.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "ITEMORIGEN"));
			movimiento.setItemActual(itemOrigen);
			
			Cargo cargoDestino = new Cargo();
			cargoDestino.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "CARGODESTINO"));
			movimiento.setCargoDestino(cargoDestino);
			
			Oficina oficinaDestino = new Oficina();
			oficinaDestino.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "OFICINADESTINO"));
			movimiento.setOficinaDestino(oficinaDestino);
			
			Item itemDestino = new Item();
			itemDestino.setDescripcion(CastUtil.leerValorMapStringOriginCase(item, "ITEMDESTINO"));
			movimiento.setItemDestino(itemDestino);
			
			movimiento.setFechaAltaCargo(CastUtil.leerValorMapString(item, "FECHAALTA"));
			movimiento.setFechaBajaCargo(CastUtil.leerValorMapString(item, "FECHABAJA"));
			
			listaMovimientos.add(movimiento);
		});
		
		return listaMovimientos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer obtenerTotalregistros(Map<String, Object> map) {
		if (map.get("O_C_RESULTADO") != null) {
			return CastUtil.leerValorMapInteger(((List<Map<String, Object>>) map.get("O_C_RESULTADO")).get(0),
					"totalRegistros");
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}
	}
	
	

}
