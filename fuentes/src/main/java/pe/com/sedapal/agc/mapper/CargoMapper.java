package pe.com.sedapal.agc.mapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;

@Component
public class CargoMapper {
	
	public static List<Cargo> mapRows(Map<String, Object> resultados) {
		List<Cargo> listaCargo = new ArrayList<>();
		Cargo cargo;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_CARGOS");
		
		for (Map<String, Object> map : lista) {
			cargo = new Cargo();
			cargo.setId(CastUtil.leerValorMapString(map, "ID"));
			cargo.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));						
			listaCargo.add(cargo);			
		}		
		return listaCargo;
	}
	
	@SuppressWarnings("unchecked")
	public ListaPaginada<Cargo> mapperConsultaCargosOpen(Map<String, Object> resultados) {
		ListaPaginada<Cargo> paginaCargos = new ListaPaginada<>();
		List<Cargo> listaCargos = new ArrayList<>();
		if (resultados.get("O_C_RESULTADO") != null) {			
			List<Map<String, Object>> respuestaConsulta = (List<Map<String, Object>>) resultados.get("O_C_RESULTADO");
			
			Map<String, List<Map<String, Object>>> cargosOpen = respuestaConsulta.stream()
					.collect(Collectors.groupingBy(m -> m.get("CODIGOCARGO").toString()));
			
			for (Map.Entry<String, List<Map<String, Object>>> listaMap : cargosOpen.entrySet()) {

				Map<String, Object> cargoBd = listaMap.getValue().get(0);
				
				Cargo cargoItem = new Cargo();
				cargoItem.setCodigo(CastUtil.leerValorMapString(cargoBd, "CODIGOCARGO"));
				cargoItem.setDescripcionCargo(CastUtil.leerValorMapString(cargoBd, "DESCCARGO"));
				
				Estado estadoCargo = new Estado();
				estadoCargo.setId(CastUtil.leerValorMapString(cargoBd, "CODESTADO"));
				estadoCargo.setDescripcion(CastUtil.leerValorMapString(cargoBd, "DESCESTADO"));
				cargoItem.setEstadoCargo(estadoCargo);
				
				TipoCargo tipoCargo = new TipoCargo();
				tipoCargo.setId(CastUtil.leerValorMapString(cargoBd, "CODTIPO"));
				tipoCargo.setDescripcion(CastUtil.leerValorMapString(cargoBd, "DESCTIPO"));
				cargoItem.setTipoCargo(tipoCargo);				
				
				
				if (listaMap.getValue().size() > 1) {
					for(Map<String, Object> registro : listaMap.getValue()) {
						Actividad actividad = new Actividad();
						actividad.setCodigo(CastUtil.leerValorMapString(registro, "IDACTIVIDAD"));
						actividad.setDescripcion(CastUtil.leerValorMapString(registro, "DESCACTIVIDAD"));
						actividad.setEstado(CastUtil.leerValorMapString(registro, "ESTADOACTIVIDAD"));
						cargoItem.getActividades().add(actividad);
					}
				}
				
				listaCargos.add(cargoItem);
			}
			
			paginaCargos.setLista(listaCargos);
			paginaCargos.setPagina(1);
			paginaCargos.setRegistros(listaCargos.size());
			paginaCargos.setTotalRegistros(listaCargos.size());
			
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}
		return paginaCargos;
	}
	
}
