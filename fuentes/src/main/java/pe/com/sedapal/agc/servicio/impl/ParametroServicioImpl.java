package pe.com.sedapal.agc.servicio.impl;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IParametroDAO;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.servicio.IParametroServicio;
import pe.com.sedapal.agc.model.response.Error;

@Service
public class ParametroServicioImpl implements IParametroServicio {

	@Autowired
	IParametroDAO dao;
	
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public Map<String, Object> listaParametro(Map<String, String> requestParm) throws ParseException, Exception {
		
		Parametro parametro = new Parametro();
		
		String sIdTipoParam = requestParm.get("tipo");
		String sIdParam = requestParm.get("codigo");
		String sEstaDesc = requestParm.get("estado");
		String sDescDeta = requestParm.get("detalle");
		String sDescCort = requestParm.get("descripcionCorta");
		String sIdValor = requestParm.get("valor");
		
		parametro.setTipo(Integer.parseInt(sIdTipoParam));
		parametro.setCodigo(Integer.parseInt(sIdParam));
		parametro.setEstado(sEstaDesc);
		parametro.setDetalle(sDescDeta);
		parametro.setDescripcionCorta(sDescCort);
		parametro.setValor(sIdValor);
		
		return this.dao.obtenerParametro(parametro);

	}

	@Override
	public Map<String, Object> listarTipoParametro(Map<String, String> requestParm, PageRequest pageRequest) {
		
		Parametro tipoParametro = new Parametro();
		
		String sIdParam = requestParm.get("codigo");
		String sEstaDesc = requestParm.get("estado");
		String sDescDeta = requestParm.get("detalle");
		
		tipoParametro.setCodigo(Integer.parseInt(sIdParam));
		tipoParametro.setEstado(sEstaDesc);
		tipoParametro.setDetalle(sDescDeta);
		return this.dao.listarTipoParametro(tipoParametro, pageRequest);
	}

	@Override
	public Integer registrarTipoParametro(Map<String, String> requestParm) {
		String usuario = requestParm.get("usuario");
		Parametro tipoParametro = new Parametro();
		String sDescDeta = requestParm.get("detalle");
		tipoParametro.setDetalle(sDescDeta);
		return this.dao.registrarTipoParametro(tipoParametro,usuario);
	}

	@Override
	public Integer modificarTipoParametro(Map<String, String> requestParm) {
		String usuario = requestParm.get("usuario");
		Parametro tipoParametro = new Parametro();
		String sDescDeta = requestParm.get("detalle");
		String sEstaDesc = requestParm.get("estado");
		String sIdParam = requestParm.get("codigo");
		tipoParametro.setCodigo(Integer.parseInt(sIdParam));
		tipoParametro.setDetalle(sDescDeta);
		tipoParametro.setEstado(sEstaDesc);
		return this.dao.modificarTipoParametro(tipoParametro,usuario);
	}

	@Override
	public Integer eliminarTipoParametro(Integer codigo, String usuario) {
		Parametro tipoParametro = new Parametro();
		tipoParametro.setCodigo(codigo);
		return this.dao.eliminarTipoParametro(tipoParametro,usuario);
	}
	@Override
	public Map<String,Object> obtenerTipoParametro(Map<String, String> requestParm){
		Parametro tipoParametro = new Parametro();
		String sIdParam = requestParm.get("codigo");
		tipoParametro.setCodigo(Integer.parseInt(sIdParam));
		return this.dao.obtenerTipoParametro(tipoParametro);
	}

	@Override
	public Map<String, Object> listarParametros(Map<String, String> requestParm, PageRequest pageRequest) {

		Parametro parametro = new Parametro();
		
		String sIdTipoParam = requestParm.get("tipo");
		String sIdParam = requestParm.get("codigo");
		String sEstaDesc = requestParm.get("estado");
		String sDescDeta = requestParm.get("detalle");
		String sDescCort = requestParm.get("descripcionCorta");
		String sIdValor = requestParm.get("valor");
		
		parametro.setTipo(Integer.parseInt(sIdTipoParam));
		parametro.setCodigo(Integer.parseInt(sIdParam));
		parametro.setEstado(sEstaDesc);
		parametro.setDetalle(sDescDeta);
		parametro.setDescripcionCorta(sDescCort);
		parametro.setValor(sIdValor);
		
		return this.dao.listarParametros(parametro, pageRequest);

	}
	@Override
	public Integer registrarParametro(Map<String, String> requestParm) {
		String usuario = requestParm.get("usuario");
		Parametro parametro = new Parametro();
		String sDescDeta = requestParm.get("detalle");
		String sIdTipoParam = requestParm.get("tipo");
		
		String sEstaDesc = requestParm.get("estado");
		String sDescCort = requestParm.get("descripcionCorta");
		String sIdValor = requestParm.get("valor");
		parametro.setTipo(Integer.parseInt(sIdTipoParam));
		
		parametro.setDetalle(sDescDeta);
		parametro.setEstado(sEstaDesc);
		parametro.setDescripcionCorta(sDescCort);
		parametro.setValor(sIdValor);
		return this.dao.registrarParametro(parametro,usuario);
	}

	@Override
	public Integer modificarParametro(Map<String, String> requestParm) {
		String usuario = requestParm.get("usuario");
		Parametro parametro = new Parametro();
		String sDescDeta = requestParm.get("detalle");
		String sIdTipoParam = requestParm.get("tipo");
		String sIdParam = requestParm.get("codigo");
		String sEstaDesc = requestParm.get("estado");
		String sDescCort = requestParm.get("descripcionCorta");
		String sIdValor = requestParm.get("valor");
		parametro.setTipo(Integer.parseInt(sIdTipoParam));
		parametro.setCodigo(Integer.parseInt(sIdParam));
		parametro.setDetalle(sDescDeta);
		parametro.setEstado(sEstaDesc);
		parametro.setDescripcionCorta(sDescCort);
		parametro.setValor(sIdValor);
		return this.dao.modificarParametro(parametro,usuario);
	}

	@Override
	public Integer eliminarParametro(Integer codigo,Integer tipoCodigo, String usuario) {
		Parametro parametro = new Parametro();
		parametro.setCodigo(codigo);
		parametro.setTipo(tipoCodigo);
		return this.dao.eliminarParametro(parametro,usuario);
	}
	@Override
	public Map<String,Object> obtenerParametro(Map<String, String> requestParm){
		Parametro parametro = new Parametro();
		String sIdTipoParam = requestParm.get("tipo");
		String sIdParam = requestParm.get("codigo");
		parametro.setTipo(Integer.parseInt(sIdTipoParam));
		parametro.setCodigo(Integer.parseInt(sIdParam));
		return this.dao.obtenerParametro(parametro);
	}

}
