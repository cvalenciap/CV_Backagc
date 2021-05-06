package pe.com.sedapal.agc.dao;

import java.util.Map;

import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IParametroDAO {
	public Error getError();
	
	Map<String,Object> listarTipoParametro(Parametro tipoParametro, PageRequest pageRequest);
	Map<String,Object> obtenerTipoParametro(Parametro tipoParametro);
	Integer registrarTipoParametro(Parametro tipoParametro, String usuario);
	Integer modificarTipoParametro(Parametro tipoParametro,String Usuario);
	Integer eliminarTipoParametro(Parametro tipoParametro, String usuario);
	
	Map<String,Object> listarParametros(Parametro parametro, PageRequest pageRequest);
	Integer registrarParametro(Parametro parametro, String usuario);
	Integer modificarParametro(Parametro parametro,String Usuario);
	Integer eliminarParametro(Parametro parametro, String usuario);
	Map<String,Object> obtenerParametro(Parametro parametro);
}
