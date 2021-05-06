package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.Solicitud;
import pe.com.sedapal.agc.util.CastUtil;

public class SolicitudMapper {
	
	public static List<Solicitud> mapRowsCambioCargo(Map<String, Object> resultados) {
		List<Solicitud> listaSolicitud = new ArrayList<>();
		Solicitud solicitud;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_SOLICITUDES");
		
		for (Map<String, Object> map : lista) {
			solicitud = new Solicitud();
			solicitud.setIdSolicitud(CastUtil.leerValorMapInteger(map, "NUMERO_SOLICITUD"));
			solicitud.setFechaSolicitud(CastUtil.leerValorMapString(map, "FECHA_SOLICITUD"));
			solicitud.setTipoSolicitud(CastUtil.leerValorMapString(map, "TIPO_SOLICITUD"));
			
			Parametro motivoSolicitud = new Parametro();
			motivoSolicitud.setCodigo(CastUtil.leerValorMapInteger(map, "COD_MTVO_SOLICITUD"));
			motivoSolicitud.setDescripcionCorta(CastUtil.leerValorMapString(map, "DESC_MTVO_SOLICITUD"));
			solicitud.setMotivoSolicitud(motivoSolicitud);
			
			Oficina oficinaActual = new Oficina();
			oficinaActual.setCodigo(CastUtil.leerValorMapInteger(map, "COD_OFICINA_ACTUAL"));
			oficinaActual.setDescripcion(CastUtil.leerValorMapString(map, "DESC_OFICINA_ACTUAL"));
			solicitud.setOficinaActual(oficinaActual);
			
			Oficina oficinaDestino = new Oficina();
			oficinaDestino.setCodigo(CastUtil.leerValorMapInteger(map, "COD_OFICINA_DESTINO"));
			oficinaDestino.setDescripcion(CastUtil.leerValorMapString(map, "DESC_OFICINA_DESTINO"));
			solicitud.setOficinaDestino(oficinaDestino);
			
			Cargo cargoActual = new Cargo();
			cargoActual.setId(CastUtil.leerValorMapString(map, "COD_CARGO_ACTUAL"));
			cargoActual.setDescripcion(CastUtil.leerValorMapString(map, "DESC_CARGO_ACTUAL"));
			solicitud.setCargoActual(cargoActual);
			
			Cargo cargoDestino = new Cargo();
			cargoDestino.setId(CastUtil.leerValorMapString(map, "COD_CARGO_DESTINO"));
			cargoDestino.setDescripcion(CastUtil.leerValorMapString(map, "DESC_CARGO_DESTINO"));
			solicitud.setCargoDestino(cargoDestino);
			
			Estado estadoSolicitud = new Estado();
			estadoSolicitud.setId(CastUtil.leerValorMapString(map, "ESTADO_SOLICITUD"));
			estadoSolicitud.setDescripcion(CastUtil.leerValorMapString(map, "DESC_ESTADO_SOLICITUD"));
			solicitud.setEstadoSolicitud(estadoSolicitud);
			
			solicitud.setDescripcionSolicitud(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaSolicitud.add(solicitud);			
		}		
		return listaSolicitud;
	}
	
	public static List<Solicitud> mapRowsMovimientos(Map<String, Object> resultados) {
		List<Solicitud> listaSolicitud = new ArrayList<>();
		Solicitud solicitud;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_SOLICITUDES");
		
		for (Map<String, Object> map : lista) {
			solicitud = new Solicitud();
			solicitud.setIdSolicitud(CastUtil.leerValorMapInteger(map, "NUMERO_SOLICITUD"));
			solicitud.setFechaSolicitud(CastUtil.leerValorMapString(map, "FECHA_SOLICITUD"));
			solicitud.setTipoSolicitud(CastUtil.leerValorMapString(map, "TIPO_SOLICITUD"));
			
			Parametro motivoSolicitud = new Parametro();
			motivoSolicitud.setCodigo(CastUtil.leerValorMapInteger(map, "COD_MTVO_SOLICITUD"));
			motivoSolicitud.setDescripcionCorta(CastUtil.leerValorMapString(map, "DESC_MTVO_SOLICITUD"));
			solicitud.setMotivoSolicitud(motivoSolicitud);
			
			Oficina oficinaActual = new Oficina();
			oficinaActual.setCodigo(CastUtil.leerValorMapInteger(map, "COD_OFICINA_ACTUAL"));
			oficinaActual.setDescripcion(CastUtil.leerValorMapString(map, "DESC_OFICINA_ACTUAL"));
			solicitud.setOficinaActual(oficinaActual);
			
			Oficina oficinaDestino = new Oficina();
			oficinaDestino.setCodigo(CastUtil.leerValorMapInteger(map, "COD_OFICINA_DESTINO"));
			oficinaDestino.setDescripcion(CastUtil.leerValorMapString(map, "DESC_OFICINA_DESTINO"));
			solicitud.setOficinaDestino(oficinaDestino);
			
			Cargo cargoActual = new Cargo();
			cargoActual.setId(CastUtil.leerValorMapString(map, "COD_CARGO_ACTUAL"));
			cargoActual.setDescripcion(CastUtil.leerValorMapString(map, "DESC_CARGO_ACTUAL"));
			solicitud.setCargoActual(cargoActual);
			
			Cargo cargoDestino = new Cargo();
			cargoDestino.setId(CastUtil.leerValorMapString(map, "COD_CARGO_DESTINO"));
			cargoDestino.setDescripcion(CastUtil.leerValorMapString(map, "DESC_CARGO_DESTINO"));
			solicitud.setCargoDestino(cargoDestino);
			
			Item itemActual = new Item();
			itemActual.setId(CastUtil.leerValorMapInteger(map, "COD_ITEM_ACTUAL"));
			itemActual.setDescripcion(CastUtil.leerValorMapString(map, "DESC_ITEM_ACTUAL"));
			solicitud.setItemActual(itemActual);
			
			Item itemDestino = new Item();
			itemDestino.setId(CastUtil.leerValorMapInteger(map, "COD_ITEM_DESTINO"));
			itemDestino.setDescripcion(CastUtil.leerValorMapString(map, "DESC_ITEM_DESTINO"));
			solicitud.setItemDestino(itemDestino);
			
			Estado estadoSolicitud = new Estado();
			estadoSolicitud.setId(CastUtil.leerValorMapString(map, "ESTADO_SOLICITUD"));
			estadoSolicitud.setDescripcion(CastUtil.leerValorMapString(map, "DESC_ESTADO_SOLICITUD"));
			solicitud.setEstadoSolicitud(estadoSolicitud);
			
			solicitud.setDescripcionSolicitud(CastUtil.leerValorMapString(map, "DESCRIPCION"));
			listaSolicitud.add(solicitud);			
		}		
		return listaSolicitud;
	}
	
	public static List<Item> mapRowsItemEmpresa(Map<String, Object> resultados) {
		List<Item> listaItem = new ArrayList<>();
		Item item;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_ITEM_EMPRESA");
		
		for (Map<String, Object> map : lista) {
			item = new Item();
			item.setId(CastUtil.leerValorMapInteger(map, "COD_IDEM"));
			item.setDescripcion(CastUtil.leerValorMapString(map, "DES_ITEM"));
			listaItem.add(item);
		}		
		return listaItem;
	}
	
	public static List<Oficina> mapRowsOfinaItem(Map<String, Object> resultados) {
		List<Oficina> listaOficina = new ArrayList<>();
		Oficina oficina;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_C_LISTA_OFICINA_ITEM");
		
		for (Map<String, Object> map : lista) {
			oficina = new Oficina();
			oficina.setCodigo(CastUtil.leerValorMapInteger(map, "COD_OFICINA"));
			oficina.setDescripcion(CastUtil.leerValorMapString(map, "DES_OFICINA"));
			listaOficina.add(oficina);
		}		
		return listaOficina;
	}

}
