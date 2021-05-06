package pe.com.sedapal.agc.servicio;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.ItemOficina;
import pe.com.sedapal.agc.model.request.ItemRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IItemServicio {
	List<Item> listarItemEmpresa(Integer codigoContratista, Integer codigoOficina);
	Paginacion getPaginacion();
	Error getError();
	List<Item> listarItem(ItemRequest itemRequest);
	Integer modificarItem(ItemRequest itemRequest);
	Integer agregarItem(ItemRequest itemRequest);
	Integer eliminarItem(ItemRequest itemRequest);
	Map<String,Object> listarItemOficinaMantenimiento(Integer idItem);
	void registrarItemOficina(List<ItemOficina> listaItemOficina);
}
