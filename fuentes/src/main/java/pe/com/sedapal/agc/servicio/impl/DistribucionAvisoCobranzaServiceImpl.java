package pe.com.sedapal.agc.servicio.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IDistribucionAvisoCobbranzaDAO;
import pe.com.sedapal.agc.model.DistribucionAvisoCobranza;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IDistribucionAvisoCobranzaService;

@Service
public class DistribucionAvisoCobranzaServiceImpl implements IDistribucionAvisoCobranzaService {

	@Autowired
	private IDistribucionAvisoCobbranzaDAO dao;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date f_Aux;
	Date f_cast;

	@Override
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}
	
	@Override
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public Map<String, Object> modificarDistAvisCobLine(Map<String, String> requestParm)
			throws ParseException, Exception {

		DistribucionAvisoCobranza dac = new DistribucionAvisoCobranza();
		
		String sCodigoActivida = requestParm.get("codigoActivida");
		String sCodigoCarga = requestParm.get("codigoCarga"); 
		String sCiclo = requestParm.get("ciclo");
		String sCodigoDistribuidor = requestParm.get("codigoDistribuidor");
		String sCodigoIncidecia = requestParm.get("codigoIncidecia");
		String sFechaDistribucion = requestParm.get("fechaDistribucion");
		String sHoraDistribucion = requestParm.get("horaDistribucion");
		String sTipoEntrada = requestParm.get("tipoEntrada");
		
		dac.setCodigoRegistro(Integer.parseInt(sCodigoActivida));
		dac.setCodigoCarga(sCodigoCarga);
		dac.setCiclo(Integer.parseInt(sCiclo));
		dac.setCodigoDistribuidor(sCodigoDistribuidor);
		dac.setCodigoIncidecia(sCodigoIncidecia);
		
		f_Aux = format.parse(sFechaDistribucion);
		f_cast = new Date(f_Aux.getTime());
		dac.setFechaDistribucion(f_cast);
		
		dac.setHoraDistribucion(Integer.parseInt(sHoraDistribucion));
		dac.setTipoEntrada(sTipoEntrada);

		String sUsuario = requestParm.get("usuarioModifica");
		
		return dao.actualizarDistAvisCobLine(dac, sUsuario);

	}
	
	@Override
	public List<DistribucionAvisoCobranza> ListarDetalleAvisoCobranza(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception {
		return this.dao.ListarDetalleAvisoCobranza(pageRequest, V_IDCARGA, N_IDREG, V_N_CON_ADJ);

	}

}
