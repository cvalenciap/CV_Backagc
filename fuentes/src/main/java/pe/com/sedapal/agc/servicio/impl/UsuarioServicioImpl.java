package pe.com.sedapal.agc.servicio.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;	
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.com.sedapal.agc.dao.IUsuarioDAO;
import pe.com.sedapal.agc.model.Formulario;
import pe.com.sedapal.agc.model.Modulo;
import pe.com.sedapal.agc.model.Perfil;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.Usuario;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IUsuarioServicio;

import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.model.request.UsuarioRequest;
import pe.com.sedapal.agc.model.request.ActualizarClaveRequest;

import pe.com.sedapal.seguridad.core.bean.PerfilSistemaBean;
import pe.com.sedapal.seguridad.core.bean.Retorno;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;
import pe.com.sedapal.seguridad.core.bean.SistemaModuloOpcionBean;
import pe.com.sedapal.seguridad.core.bean.TrabajadorBean;

@Service
@ImportResource("classpath:pe/com/sedapal/seguridad/ws/config/applicationContext.xml")
@PropertySource("classpath:application.properties")
public class UsuarioServicioImpl implements IUsuarioServicio {

	private Error error;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Autowired
	private SeguridadClienteWs seguridadCliente;
	
	@Value("${soap.code.system}")
	private Integer uidSistema;
	
	@Value("${server.session.timeout}")
	private String tiempoSesion;
	
	@Autowired
	private IUsuarioDAO dao;
	
	@Override
	public Usuario autentificarCredenciales(UsuarioRequest usuarioRequest) {
		this.error = null;
		Usuario usuario = new Usuario();
		String campo = "";
		Retorno retorno = null;
		
		if (validarUsuarioRequest(usuarioRequest)) {

			String strUsuario = usuarioRequest.getUsuario();
			String strClave   = usuarioRequest.getClave();
			
			retorno = seguridadCliente.autenticacionUsuarioActWs(strUsuario, uidSistema, strClave);
			
			try {
				if (retorno.getCodigo().equals("0") || retorno.getCodigo().equals("2")) {
					this.error = new Error(1003, retorno.getMensaje(),
							retorno.getTipo());
				} else if(retorno.getCodigo().equals("1")) {
					if (retorno.getFlagCambiarClave()==null || retorno.getFlagCambiarClave().equals("1")) { //Cambio de Clave
						this.error = new Error(1003, retorno.getMensaje(),
								retorno.getTipo());
					}else {
					usuario.setUsuario(retorno.getUsuario().toString());
					usuario.setToken(retorno.getToken());
					usuario.setPerfiles(obtenerPerfilesAct(retorno.getPerfilesAct()));
					usuario.setNumPerfilesActivos(retorno.getNumPerfilesAct());
					usuario.setTiempoSesion(this.tiempoSesion);
					}
				}
			} catch (Exception e) {
				campo = SeguridadClienteWs.class.getSimpleName();
				this.error = new Error(9001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9001), "{valor}", campo),
						e.getClass().toString());
				logger.error("[AGC: UsuarioServicioImpl - autentificarCredenciales()] - "+e.getMessage());
			}
			
		}
		
		return usuario;
	}
	
	@Override
	public Usuario obtenerPerfilesModulos(UsuarioRequest usuarioRequest) {
		this.error = null;
		Usuario usuario = new Usuario();
		String campo = "";
		List<SistemaModuloOpcionBean> listaMenus = null;
		List<String> listaPermisos = null;
		List<Perfil> listaPerfil   = null;
		List<Modulo> listaModulos   = null;
		
		String strUsuario       = usuarioRequest.getUsuario();
		int nPerfilSeleccionado = usuarioRequest.getPerfil();
		
		listaMenus    = seguridadCliente.obtenerMenuActWs(strUsuario, uidSistema, nPerfilSeleccionado);
		listaPermisos = seguridadCliente.obtenerPermisosActWs(strUsuario, uidSistema, nPerfilSeleccionado);
		
		try {
			usuario.setDescripcionSistema(listaMenus.get(0).getSistemaNombre());
			usuario.setUidSistema(listaMenus.get(0).getCodSistema());
			
			listaPerfil  = obtenerPerfiles(listaMenus);
			listaModulos = listaPerfil.get(0).getModulos();
			
			usuario.setModulos(listaModulos);
			usuario.setPermisos(listaPermisos);
			
		} catch (Exception e) {
			campo = SeguridadClienteWs.class.getSimpleName();
			this.error = new Error(9001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9001), "{valor}", campo),
					e.getClass().toString());
			logger.error("[AGC: UsuarioServicioImpl - obtenerPerfilesModulos()] - "+e.getMessage());
		}
	   return usuario;
	}
	

	@Override
	public String actualizarCredenciales(ActualizarClaveRequest actualizarClaveRequest) {
		this.error = null;
		String respuesta = "", campo = "";
		Retorno retorno = null;
		
		if (validarActualizarClaveRequest(actualizarClaveRequest)) {

			String strUsuario = actualizarClaveRequest.getUsuario();
			String strClaveActual   = actualizarClaveRequest.getClaveActual();
			String strNuevaClave   = actualizarClaveRequest.getNuevaClave();
			String strNuevaClaveR   = actualizarClaveRequest.getNuevaClaveR();
		
			retorno = seguridadCliente.actualizarClaveWs(strUsuario, strClaveActual, strNuevaClave, strNuevaClaveR);
				
			try {
				if (retorno.getCodigo().equals("0")) {
					this.error = new Error(1003, retorno.getMensaje(),
							retorno.getTipo());
				} else if(retorno.getCodigo().equals("1")) {
					respuesta = retorno.getMensaje().toString();
				}
			} catch (Exception e) {
				campo = SeguridadClienteWs.class.getSimpleName();
				this.error = new Error(9001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9001), "{valor}", campo),
						e.getClass().toString());
				logger.error("[AGC: UsuarioServicioImpl - actualizarCredenciales()] - "+e.getMessage());
			}
		}

		return respuesta;
	}
	
	@Override
	public String recuperarCredenciales(String usuario) {
		this.error = null;
		String respuesta = new String();
		Retorno retorno = null;

		if (!usuario.equals(""))
			retorno = seguridadCliente.olvidarClaveWs(usuario);
		else
			this.error = new Error(1001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(1001), "{valor}", "usuario"));

		if (!retorno.equals(null))
			respuesta = retorno.getMensaje().toString();

		return respuesta;
	}

	@Override
	public Trabajador obtenerTrabajador(String usuario) {
		this.error = null;
		Trabajador trabajador = new Trabajador();
		TrabajadorBean trabajadorBean = new TrabajadorBean();
		String campo = "";
		
		try {
				
				trabajadorBean = seguridadCliente.obtenerTrabajadorWs(usuario);
				
				if (trabajadorBean!=null) {
					trabajador.setCodigo(trabajadorBean.getnCodTrabajador());
					trabajador.setNombre(obtenerNombresTrabajador(trabajadorBean));
					trabajador.setEstado(trabajadorBean.getEstadoTrabajador());
					trabajador.setFicha(trabajadorBean.getnFicha());
				}
				
		 } catch (Exception e) {
				campo = SeguridadClienteWs.class.getSimpleName();
				this.error = new Error(9001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(9001), "{valor}", campo),
						e.getClass().toString());
				logger.error("[AGC: UsuarioServicioImpl - obtenerTrabajador()] - "+e.getMessage());
		 }
			
		return trabajador;
	}
	
	@Override
	public String logoutToken(String token) {
		this.error = null;
		String respuesta = new String();
		Retorno retorno = null;

		if (!token.equals(""))
			retorno = seguridadCliente.logoutWs(token);
		else
			this.error = new Error(1001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(1001), "{valor}", "token"));

		if (!retorno.equals(null))
			respuesta = retorno.getMensaje().toString();

		return respuesta;
	}

	@Override
	public Error getError() {
		return this.error;
	}

	private boolean validarActualizarClaveRequest(ActualizarClaveRequest actualizarRequest) {
		boolean valor = true;
		String campo = "";

		if (actualizarRequest.getUsuario().equals("")) {
			valor = false;
			campo = "usuario";
		}
		if (actualizarRequest.getClaveActual().equals("")) {
			valor = false;
			campo = "claveActual";
		}

		if (actualizarRequest.getNuevaClave().equals("")) {
			valor = false;
			campo = "nuevaClave";
		}

		if (actualizarRequest.getNuevaClaveR().equals("")) {
			valor = false;
			campo = "nuevaClaveR";
		}

		if (!valor)
			this.error = new Error(1001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(1001), "{valor}", campo));

		return valor;
	}

	private String obtenerNombresTrabajador(TrabajadorBean trabajadorBean) {
		StringBuilder builder = new StringBuilder();
		builder.append(trabajadorBean.getvApePaterno() != null ? trabajadorBean.getvApePaterno() : "")
		.append(trabajadorBean.getvApePaterno() != null && trabajadorBean.getvApeMaterno() != null ? " " :
			trabajadorBean.getvApePaterno() == null ? "" : ", ")
		.append(trabajadorBean.getvApeMaterno() != null ? trabajadorBean.getvApeMaterno() : "")
		.append(trabajadorBean.getvApeMaterno() != null ? ", " : "")
		.append(trabajadorBean.getvNombres() != null ? trabajadorBean.getvNombres() : "");
		return builder.toString();
	}

	private boolean validarUsuarioRequest(UsuarioRequest usuarioRequest) {
		boolean valor = true;
		String campo = "";

		if (usuarioRequest.getUsuario().equals("")) {
			valor = false;
			campo = "usuario";
		}
		if (usuarioRequest.getClave().equals("")) {
			valor = false;
			campo = "clave";
		}

		if (!valor)
			this.error = new Error(1001, StringUtils.replace(Constantes.MESSAGE_ERROR.get(1001), "{valor}", campo));

		return valor;
	}

	

	private List<Perfil> obtenerPerfiles(List<SistemaModuloOpcionBean> menus) {
		Integer codigoPerfilAux = 0, codigoModuloAux = 0, index = 0;
		List<Integer> iCambioModulo = new ArrayList<Integer>();
		List<Integer> iCambioPerfil = new ArrayList<Integer>();
		List<Perfil> perfiles = new ArrayList<Perfil>();
		List<Modulo> modulos = new ArrayList<Modulo>();
		List<Formulario> formularios = new ArrayList<Formulario>();

		// Se realiza el ordenamiento por código de módulo
		Collections.sort(menus, (SistemaModuloOpcionBean s1, SistemaModuloOpcionBean s2) -> s1.getCodModulo()
				.compareTo(s2.getCodModulo()));

		// Se realiza el ordenamiento por código de perfil
		Collections.sort(menus, (SistemaModuloOpcionBean s1, SistemaModuloOpcionBean s2) -> s1.getCodPerfil()
				.compareTo(s2.getCodPerfil()));

		for (SistemaModuloOpcionBean menu : menus) {
			codigoModuloAux = menu.getCodModulo();
			codigoPerfilAux = menu.getCodPerfil();
			if (perfiles.isEmpty())
				perfiles.add(new Perfil(menu.getCodPerfil(), menu.getPerfilNombre()));

			if (modulos.isEmpty())
				modulos.add(new Modulo(menu.getCodModulo(), menu.getModuloNombre()));

			if (!codigoModuloAux.equals(modulos.get(modulos.size() - 1).getCodigo())
					|| !codigoPerfilAux.equals(perfiles.get(perfiles.size() - 1).getCodigo())) {
				modulos.add(new Modulo(menu.getCodModulo(), menu.getModuloNombre()));
				iCambioModulo.add(index);
			}

			if (!codigoPerfilAux.equals(perfiles.get(perfiles.size() - 1).getCodigo())) {
				perfiles.add(new Perfil(menu.getCodPerfil(), menu.getPerfilNombre()));
				iCambioPerfil.add(iCambioModulo.size());
			}

			formularios.add(cargarFormulario(menu));
			index++;
		}

		asignarFormularios(modulos, formularios, iCambioModulo);
		asignarModulos(perfiles, modulos, iCambioPerfil);

		return perfiles;
	}

	private List<Perfil> asignarModulos(List<Perfil> perfiles, List<Modulo> modulos, List<Integer> iCambioPerfil) {
		Integer index = 0, indexPerfil = 0;
		List<List<Modulo>> arrayListaModulos = new ArrayList<List<Modulo>>();

		if (iCambioPerfil.isEmpty()) {
			perfiles.get(indexPerfil).setModulos(modulos);
		} else {
			List<Modulo> listaInicial = new ArrayList<Modulo>();
			arrayListaModulos.add(listaInicial);
			for (Modulo modulo : modulos) {
				if (!iCambioPerfil.contains(index)) {
					arrayListaModulos.get(indexPerfil).add(modulo);
				} else {
					perfiles.get(indexPerfil).setModulos(arrayListaModulos.get(indexPerfil));
					List<Modulo> listaAuxiliar = new ArrayList<Modulo>();
					arrayListaModulos.add(listaAuxiliar);
					indexPerfil++;
					arrayListaModulos.get(indexPerfil).add(modulo);
				}

				index++;
			}

			perfiles.get(indexPerfil).setModulos(arrayListaModulos.get(indexPerfil));
		}
		return perfiles;
	}

	private List<Modulo> asignarFormularios(List<Modulo> modulos, List<Formulario> formularios,
			List<Integer> iCambioModulo) {
		Integer index = 0, indexModulo = 0;
		List<List<Formulario>> arrayListaFormularios = new ArrayList<List<Formulario>>();

		if (iCambioModulo.isEmpty()) {
			modulos.get(indexModulo).setFormularios(formularios);
		} else {
			List<Formulario> listaInicial = new ArrayList<Formulario>();
			arrayListaFormularios.add(listaInicial);
			for (Formulario formulario : formularios) {
				if (!iCambioModulo.contains(index)) {
					arrayListaFormularios.get(indexModulo).add(formulario);
				} else {
					modulos.get(indexModulo).setFormularios(arrayListaFormularios.get(indexModulo));
					List<Formulario> listaAuxiliar = new ArrayList<Formulario>();
					arrayListaFormularios.add(listaAuxiliar);
					indexModulo++;
					arrayListaFormularios.get(indexModulo).add(formulario);
				}
				index++;
			}
			modulos.get(indexModulo).setFormularios(arrayListaFormularios.get(indexModulo));
		}

		return modulos;
	}

	private Formulario cargarFormulario(SistemaModuloOpcionBean menu) {
		Formulario formulario = new Formulario();
		formulario.setCodigo(menu.getCodFormulario());
		formulario.setCodigoPadre(menu.getCodFormularioPadre());
		formulario.setDescripcion(menu.getDescripcion());
		formulario.setEstado(menu.getEstado());
		formulario.setDescripcionEstado(menu.getEstadoNombre());
		formulario.setNivel(menu.getNivelFormulario());
		formulario.setOrden(menu.getOrdenFormulario());
		formulario.setUrl(menu.getUrlFormulario());
		return formulario;
	}
	
	private List<Perfil> obtenerPerfilesAct(List<PerfilSistemaBean> perfiles) {
		Integer codigoPerfil = 0;
		String  descripcionPerfil = null;
		List<Perfil> listaPerfiles = new ArrayList<Perfil>();
	
		for (PerfilSistemaBean perfil : perfiles) {
			codigoPerfil = perfil.getCodPerfil();
			descripcionPerfil = perfil.getDescripcion();
			listaPerfiles.add(new Perfil(codigoPerfil, descripcionPerfil));
		}
		
		return listaPerfiles;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}			

	@Override
	public Trabajador obtenerPersonal(String usuario) {		
		return this.dao.obtenerPersonal(usuario);
	}
	
	public Error getErrorUsuario() {
		return this.dao.getError();
	}
   
}
