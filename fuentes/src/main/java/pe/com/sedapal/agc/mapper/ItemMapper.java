package pe.com.sedapal.agc.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.ItemOficina;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.util.CastUtil;

public class ItemMapper {
	
	public static List<Item> mapRows(Map<String, Object> resultados) {
		List<Item> listaItems = new ArrayList<>();
		Item item;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_ITEMS_EMPRESA");
		
		for (Map<String, Object> map : lista) {
			item = new Item();
			item.setId(CastUtil.leerValorMapInteger(map, "ID"));
			item.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			item.setEstado(CastUtil.leerValorMapString(map, "ESTADO"));
			listaItems.add(item);			
		}		
		return listaItems;
	}
	
	public static List<Item> mapRowsItems(Map<String, Object> resultados) {
		List<Item> listaItems = new ArrayList<Item>();
		Item item;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LIST_ITEM");
		
		for (Map<String, Object> map : lista) {
			item = new Item();
			item.setId(((BigDecimal)map.get("ID")).intValue());
			item.setDescripcion((String) map.get("DESCRIPCION"));
			item.setEstado((String) map.get("ESTADO"));
			listaItems.add(item);			
		}		
		return listaItems;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> mapRowsItemOficina(Map<String, Object> resultados) {
		
		Map<String, Object> mapRespuesta = new HashMap<String, Object>();
		
		List<Oficina> listaOficinasDisponibles = new ArrayList<Oficina>();
		List<ItemOficina> listaItemOficinas = new ArrayList<ItemOficina>();
		Oficina oficina;
		ItemOficina itemOficina;

		List<Map<String, Object>> listaOfiDisp = (List<Map<String, Object>>) resultados.get("C_OUT_LIS_OFIC_DISP");
		List<Map<String, Object>> listaItemOfi = (List<Map<String, Object>>) resultados.get("C_OUT_LIS_OFIC_ITEM");
		
		for (Map<String, Object> map : listaOfiDisp) {
			oficina = new Oficina();
			oficina.setCodigo(CastUtil.leerValorMapInteger(map, "CODIGO"));
			oficina.setEstado(CastUtil.leerValorMapString(map, "ESTADO"));
			oficina.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaOficinasDisponibles.add(oficina);		
		}
		
		for (Map<String, Object> map : listaItemOfi) {
			itemOficina = new ItemOficina();
			Item item = new Item();
			item.setId(CastUtil.leerValorMapInteger(map, "CODIGO_ITEM"));
			itemOficina.setItem(item);
			
			Oficina office = new Oficina();
			office.setCodigo(CastUtil.leerValorMapInteger(map, "CODIGO_OFICINA"));
			office.setEstado(CastUtil.leerValorMapString(map, "ESTADO"));
			office.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION_OFICINA"));
			itemOficina.setOficina(office);		
			itemOficina.setCantPersExtOfic(CastUtil.leerValorMapInteger(map, "CANT_PERS_EXT_OFIC"));
			listaItemOficinas.add(itemOficina);			
		}
		
		mapRespuesta.put("listaOficinasDisponibles", listaOficinasDisponibles);
		mapRespuesta.put("listaItemOficinas", listaItemOficinas);
		
		return mapRespuesta;				
	}
}
