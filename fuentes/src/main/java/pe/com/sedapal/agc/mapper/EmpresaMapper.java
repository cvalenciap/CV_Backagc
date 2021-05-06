package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.util.CastUtil;

public class EmpresaMapper {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> mapRowsEmpresaItem(Map<String, Object> resultados) {		
		Map<String, Object> mapRespuesta = new HashMap<String, Object>();		
		List<Item> listaItemDisponibles = new ArrayList<Item>();
		List<Item> listaEmpresaItem = new ArrayList<Item>();
		Item item;
		List<Map<String, Object>> listaItemDisp = (List<Map<String, Object>>) resultados.get("C_OUT_LIS_ITEM_DISP");
		List<Map<String, Object>> listaEmprItem = (List<Map<String, Object>>) resultados.get("C_OUT_LIS_EMPR_ITEM");
		
		for (Map<String, Object> map : listaItemDisp) {
			item = new Item();
			item.setId(CastUtil.leerValorMapInteger(map, "CODIGO"));
			item.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaItemDisponibles.add(item);		
		}		
		for (Map<String, Object> map : listaEmprItem) {
			item = new Item();
			item.setId(CastUtil.leerValorMapInteger(map, "CODIGO"));
			item.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaEmpresaItem.add(item);
		}		
		mapRespuesta.put("listaItemDisponibles", listaItemDisponibles);
		mapRespuesta.put("listaEmpresaItem", listaEmpresaItem);		
		return mapRespuesta;				
	}
}
