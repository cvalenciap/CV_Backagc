package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.util.CastUtil;

public class TipoCargoMapper {

	public static List<TipoCargo> mapRows(Map<String, Object> resultados) {
		List<TipoCargo> listaTipoCargo = new ArrayList<>();
		TipoCargo tipoCargo;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_TIPO_CARGO");
		
		for (Map<String, Object> map : lista) {
			tipoCargo = new TipoCargo();
			tipoCargo.setId(CastUtil.leerValorMapString(map, "ID"));
			tipoCargo.setDescripcion(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaTipoCargo.add(tipoCargo);			
		}		
		return listaTipoCargo;
	}
}
