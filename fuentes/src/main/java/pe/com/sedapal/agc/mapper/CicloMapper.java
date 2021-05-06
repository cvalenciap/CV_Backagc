package pe.com.sedapal.agc.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Ciclo;

public class CicloMapper {
	
	public static List<Ciclo> mapRows(Map<String, Object> resultados){
		List<Ciclo> listaCiclo = new ArrayList<Ciclo>();
		Ciclo ciclo;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");
		for (Map<String, Object> map : lista) {
			ciclo = new Ciclo();
			ciclo.setN_idciclo(((BigDecimal)map.get("n_idciclo")).longValue());
			ciclo.setN_idofic(((BigDecimal)map.get("n_idofic")).longValue());
			ciclo.setD_Periodo((String) map.get("d_periodo"));
			ciclo.setN_ciclo(((BigDecimal)map.get("n_ciclo")).longValue());
			ciclo.setN_child(((BigDecimal)map.get("n_child")).longValue());
			ciclo.setN_id_estado(((BigDecimal)map.get("n_id_estado")).longValue());
			listaCiclo.add(ciclo);			
		}		
		return listaCiclo;
		
	}

}
