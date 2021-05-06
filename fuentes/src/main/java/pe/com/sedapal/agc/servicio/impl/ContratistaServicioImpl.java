package pe.com.sedapal.agc.servicio.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IContratistaDAO;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IContratistaServicio;

@Service
public class ContratistaServicioImpl implements IContratistaServicio {

	@Autowired
	private IContratistaDAO dao;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date f_Aux;
	Date f_cast;
	
	@Override
	public Map<String, Object> listaContratista(Map<String, String> requestParm) throws ParseException, Exception {

		Empresa empresa = new Empresa();
		String sCodigo = requestParm.get("codigo");	
		String sDescripcion = requestParm.get("descripcion");
		String sDireccion = requestParm.get("direccion");
		String sEstado = requestParm.get("estado");
		String sComentario = requestParm.get("comentario");	
		String sNroRUC = requestParm.get("nroRUC");
		String sTipoEmpresa = requestParm.get("tipoEmpresa");
		String sNumeroContrato = requestParm.get("numeroContrato");
		String sFechaInicioVigencia = requestParm.get("fechaInicioVigencia");
		String sFechaFinVigencia = requestParm.get("fechaFinVigencia");
		
		empresa.setCodigo(Integer.parseInt(sCodigo));
		empresa.setDescripcion(sDescripcion);
		empresa.setDireccion(sDireccion);
		
		empresa.setEstado(sEstado);
		empresa.setComentario(sComentario);
		empresa.setNroRUC(sNroRUC);
		empresa.setTipoEmpresa(sTipoEmpresa);
		empresa.setNumeroContrato(sNumeroContrato);
		
		f_Aux = format.parse(sFechaInicioVigencia);
		f_cast = new Date(f_Aux.getTime());
		empresa.setFechaInicioVigencia(f_cast);
		
		f_Aux = format.parse(sFechaFinVigencia);
		f_cast = new Date(f_Aux.getTime());
		empresa.setFechaFinVigencia(f_cast);
		
		return this.dao.listaContratista(empresa);
	}

	@Override
	public List<Empresa> listaContratistaPersonal(Long idPers) throws Exception {
		return this.dao.listaContratistaPersonal(idPers);
	}

	@Override
	public Error getError() {
		return this.dao.getError();
	}

}
