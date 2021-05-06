package pe.com.sedapal.agc.servicio.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IItemDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.ItemOficina;
import pe.com.sedapal.agc.model.request.ItemRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IItemServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.MantenimientoItems;

@Service
public class ItemServicioImpl implements IItemServicio {

	private static final Logger logger = LoggerFactory.getLogger(ItemServicioImpl.class);

	@Autowired
	private IItemDAO itemDAO;

	@Override
	public List<Item> listarItemEmpresa(Integer codigoContratista, Integer codigoOficina) {
		return itemDAO.listarItemEmpresa(codigoContratista, codigoOficina);
	}

	@Override
	public Paginacion getPaginacion() {
		return itemDAO.getPaginacion();
	}

	@Override
	public Error getError() {
		return itemDAO.getError();
	}

	@Override
	public List<Item> listarItem(ItemRequest itemRequest) {
		return itemDAO.listarItem(itemRequest);
	}

	@Override
	public Integer modificarItem(ItemRequest itemRequest) {
		return itemDAO.modificarItem(itemRequest);
	}

	@Override
	public Integer agregarItem(ItemRequest itemRequest) {
		return itemDAO.agregarItem(itemRequest);
	}

	@Override
	public Integer eliminarItem(ItemRequest itemRequest) {
		return itemDAO.eliminarItem(itemRequest);
	}

	@Override
	public Map<String, Object> listarItemOficinaMantenimiento(Integer idItem) {
		return itemDAO.listarItemOficinaMantenimiento(idItem);
	}

	@Override
	public void registrarItemOficina(List<ItemOficina> listaItemOficina) {
		try {
			StringBuilder builder = new StringBuilder();
			for (ItemOficina itemOficina : listaItemOficina) {
				builder.append(itemOficina.getItem().getId()).append(",").append(itemOficina.getOficina().getCodigo())
						.append(",").append(itemOficina.getEstado().getId()).append(",")
						.append(itemOficina.getUsuarioCreacion()).append("@");
			}
			builder.deleteCharAt(builder.length() - 1);
			this.itemDAO.registrarItemOficina(builder.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, MantenimientoItems.MSE001));
		}
	}

}
