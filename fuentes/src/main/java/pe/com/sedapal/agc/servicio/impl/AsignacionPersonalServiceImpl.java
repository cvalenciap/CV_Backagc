package pe.com.sedapal.agc.servicio.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IAsignacionPersonalDAO;
import pe.com.sedapal.agc.dao.IUsuarioDAO;
import pe.com.sedapal.agc.model.GrupoPersonal;
import pe.com.sedapal.agc.model.OficinaGrupo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.request.GrupoPersonalRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IAsignacionPersonalService;
import pe.com.sedapal.agc.servicio.IUsuarioServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.seguridad.core.bean.TrabajadorBean;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;

@Service
public class AsignacionPersonalServiceImpl implements IAsignacionPersonalService {

	@Autowired
	IAsignacionPersonalDAO daoAsignacionPersonal;
	
	@Autowired
	IUsuarioDAO usuarioDao;

	@Override
	public Error getError() {
		return this.daoAsignacionPersonal.getError();
	}

	@Autowired
	private SeguridadClienteWs seguridadCliente;

	@Override
	public List<OficinaGrupo> listarOficinaGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo) {
		return this.daoAsignacionPersonal.listarOficinaGrupo(codigoEmpresa, codigoOficina, codigoGrupo);
	}

	@Override
	public List<GrupoPersonal> listarPersonalGrupo(Integer codigoEmpresa, Integer codigoOficina, Integer codigoGrupo) {
		return this.daoAsignacionPersonal.listarPersonalGrupo(codigoEmpresa, codigoOficina, codigoGrupo);
	}

	@Override
	public Trabajador obtenerTrabajador(String codigoUsuario, String tipoEmpresa) throws Exception {
		Trabajador trabajador = null;
		if (tipoEmpresa.equalsIgnoreCase(Constantes.TIPO_EMPRESA_SEDAPAL)) {
			TrabajadorBean trabajadorBean = seguridadCliente.obtenerTrabajadorWs(codigoUsuario);
			if (trabajadorBean != null && trabajadorBean.getnCodTrabajador() != null) {
				trabajador = this.daoAsignacionPersonal.obtenerTrabajador(codigoUsuario, tipoEmpresa);
				if (trabajador == null && trabajador.getCodigo() == null) {
					throw new Exception(Constantes.MensajeErrorApi.TRABAJADOR_INTERNO_SIN_PERFIL);
				}
			} else {
				throw new Exception(Constantes.MensajeErrorApi.TRABAJADOR_INTERNO_NO_ENCONTRADO);
			}
		} else if (tipoEmpresa.equalsIgnoreCase(Constantes.TIPO_EMPRESA_CONTRATISTA)) {
			trabajador = usuarioDao.obtenerPersonal(codigoUsuario);
			if (trabajador != null) {
				if (trabajador.getCodigo() != null) {
					trabajador = this.daoAsignacionPersonal.obtenerTrabajador(codigoUsuario, tipoEmpresa);
				} else {
					throw new Exception(Constantes.MensajeErrorApi.TRABAJADOR_EXTERNO_SIN_PERFIL);
				}				
//				if(trabajador == null && trabajador.getCodigo() == null) {
//					throw new Exception(Constantes.MensajeErrorApi.TRABAJADOR_EXTERNO_SIN_PERFIL);
//				}
			} else {
				throw new Exception(Constantes.MensajeErrorApi.TRABAJADOR_EXTERNO_NO_ENCONTRADO);
			}
		}
		return trabajador;
	}

	@Override
	public List<GrupoPersonal> registrarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		return this.daoAsignacionPersonal.registrarGrupoPersonal(requestGrupoPersonal);
	}

	@Override
	public List<GrupoPersonal> eliminarGrupoPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		return this.daoAsignacionPersonal.eliminarGrupoPersonal(requestGrupoPersonal);
	}

	@Override
	public Map<String, Object> validarExistenciaPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		return this.daoAsignacionPersonal.validarExistenciaPersonal(requestGrupoPersonal);
	}

	@Override
	public Map<String, Object> validarEliminarPersonal(GrupoPersonalRequest requestGrupoPersonal) {
		return this.daoAsignacionPersonal.validarEliminarPersonal(requestGrupoPersonal);
	}

}
