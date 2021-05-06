package pe.com.sedapal.agc.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.CicloDetalle;

public class CicloDetalleMapper {

	
	public static List<CicloDetalle> mapRows(Map<String, Object> resultados){
		List<CicloDetalle> listaCicloDetalle = new ArrayList<CicloDetalle>();
		CicloDetalle cicloDetalle;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");
		for (Map<String, Object> map : lista) {
			cicloDetalle = new CicloDetalle();
			cicloDetalle.setN_idciclo(((BigDecimal)map.get("n_idciclo")).longValue());
			cicloDetalle.setN_idciclodet(((BigDecimal)map.get("n_idciclodet")).longValue());
			cicloDetalle.setV_idacti((String) map.get("v_idacti"));
			cicloDetalle.setV_idaccion((String) map.get("v_idaccion"));
			cicloDetalle.setN_id_estado(((BigDecimal)map.get("n_id_estado")).longValue());
			cicloDetalle.setD_fechaejec((Date) (map.get("d_fechaejec")));
			listaCicloDetalle.add(cicloDetalle);			
		}		
		return listaCicloDetalle;
		
	}
	
}
