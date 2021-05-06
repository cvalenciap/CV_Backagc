package pe.com.sedapal.agc.servicio.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IDistribucionComunicacionesDAO;
import pe.com.sedapal.agc.model.DistribucionComunicaciones;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.Paginacion;
import pe.com.sedapal.agc.servicio.IDistribucionComunicacionesService;

@Service
public class DistribucionComunicacionesServiceImpl implements IDistribucionComunicacionesService {
	
	@Autowired
	private IDistribucionComunicacionesDAO dao;
	
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
	public Map<String, Object> modificarDistComLine(Map<String, String> requestParm) throws ParseException, Exception {

		DistribucionComunicaciones dc = new DistribucionComunicaciones();
		
		dc.setCodigoRegistro(Integer.parseInt(requestParm.get("codigoActividad")));
		dc.setCodigoCarga(requestParm.get("codigoCarga"));
		dc.setNroVisitasNotificador(requestParm.get("nroVisitasNotificador"));
		dc.setHoraVisita1(requestParm.get("horaVisita1"));
		dc.setHoraVisita2(requestParm.get("horaVisita2"));
		dc.setNombreNotificada(requestParm.get("nombreNotificada"));
		dc.setDniNotificada(requestParm.get("dniNotificada"));
		dc.setParentezcoNotificada(requestParm.get("parentezcoNotificada"));
		dc.setObsVisita1(requestParm.get("obsVisita1"));
		dc.setObsVisita2(requestParm.get("obsVisita2"));
		dc.setCodigoNotificadorVisita1(requestParm.get("codigoNotificadorVisita1"));
		dc.setNombreNotificadorVisita1(requestParm.get("nombreNotificadorVisita1"));
		dc.setDniNotificador1(requestParm.get("dniNotificador1"));
		dc.setCodigoNotificadorVisita2(requestParm.get("codigoNotificadorVisita2"));
		dc.setNombreNotificadorVisita2(requestParm.get("nombreNotificadorVisita2"));
		dc.setDniNotificador2(requestParm.get("dniNotificador2"));
		dc.setHoraConNotificacion(requestParm.get("horaConNotificacion"));
		dc.setTipoEntrega(requestParm.get("tipoEntrega"));
		dc.setDificultad(requestParm.get("dificultad"));
		
		String sUsuario = requestParm.get("usuarioModifica");
		return dao.actualizarDistComLine(dc, sUsuario);
	}
	
	@Override
	public List<DistribucionComunicaciones> ListarDetalleDisCom(PageRequest pageRequest, String V_IDCARGA, Long N_IDREG, Integer V_N_CON_ADJ) throws Exception {
		return dao.ListarDetalleDisCom(pageRequest, V_IDCARGA, N_IDREG, V_N_CON_ADJ);
	}

}
