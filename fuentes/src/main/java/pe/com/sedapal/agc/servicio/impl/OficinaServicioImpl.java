package pe.com.sedapal.agc.servicio.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IOficinaDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.servicio.IOficinaServicio;
import pe.com.sedapal.agc.util.Constantes;

@Service
public class OficinaServicioImpl implements IOficinaServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(OficinaServicioImpl.class);

	@Autowired
	private IOficinaDAO dao;
	private List<Map<String, Object>> listaRespuesta;
	
	public Oficina retornarOficinaLogin(Integer n_idpers) {
		return this.dao.retornarOficinaLogin(n_idpers);
	}
	
	
	@Override
	public Oficina obtenerOficina(Integer codigo) throws ParseException, Exception {
		
		Map<String, Object> l_oficina = new TreeMap<String,Object>();
		BigDecimal bdCast = null;
		Oficina of = new Oficina();
		Oficina r_of = new Oficina();
		String  sOfi_estado;
		Integer iId_empresa;
		
		of.setCodigo(codigo);
		of.setDescripcion("G");
		of.setDireccion("G");
		of.setEstado("A");
		of.setIdEmpresa(0);
			
		l_oficina = this.dao.obtenerOficina(of);
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>)l_oficina.get("C_OUT");
		
		listaRespuesta = new ArrayList<>();
		//La función solo devuelve un objeto.
		for(Map<String, Object> map : lista) {
			r_of.setCodigo(codigo);
			r_of.setDescripcion((String)map.get("descripcion"));
			r_of.setDireccion((String)map.get("direccion"));
			sOfi_estado = (String)map.get("estado");
			r_of.setEstado(sOfi_estado);
			bdCast = (BigDecimal)map.get("idEmpresa");
			iId_empresa = bdCast.intValue();
			r_of.setIdEmpresa(iId_empresa);
		}
		return r_of;
	}

	@Override
	public Map<String, Object> devuelveOficina(Map<String, String> requestParm) throws ParseException, Exception {
		
		String sCod_oficina = requestParm.get("codigo");
		BigDecimal bdCast = new BigDecimal(sCod_oficina);
		Integer iCod_oficina = bdCast.intValue();
		Oficina oficinaRespuesta = obtenerOficina(iCod_oficina);
		Map<String, Object> json_oficina = new TreeMap<String,Object>();
		json_oficina.put("oficina", oficinaRespuesta);
		return json_oficina;
	}

	@Override
	public Map<String, Object> listaOficina(Map<String, String> requestParm) throws ParseException, Exception {

		Oficina of = new Oficina();
		
		String sCodigo = requestParm.get("codigo");
		String sDescripcion = requestParm.get("descripcion");
		String sDireccion = requestParm.get("direccion");
		String sEstado = requestParm.get("estado");
		String sIdEmpresa = requestParm.get("idEmpresa");
		
		of.setCodigo(Integer.parseInt(sCodigo));
		of.setDescripcion(sDescripcion);
		of.setDireccion(sDireccion);
		of.setEstado(sEstado);
		
		of.setIdEmpresa(Integer.parseInt(sIdEmpresa));
		
		return this.dao.obtenerOficina(of);
	}


	@Override
	public List<Map<String, String>> obtenerGruposFuncionales(Integer idOficina, Integer idGrupo) {
		try {
			return dao.obtenerGruposFuncionales(idOficina, idGrupo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD, e);
		}
	}

}
