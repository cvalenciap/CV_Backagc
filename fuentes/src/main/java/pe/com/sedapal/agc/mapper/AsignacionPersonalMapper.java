package pe.com.sedapal.agc.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Grupo;
import pe.com.sedapal.agc.model.GrupoPersonal;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.OficinaGrupo;
import pe.com.sedapal.agc.model.Perfil;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.seguridad.core.bean.TrabajadorBean;

public class AsignacionPersonalMapper {

	public static List<OficinaGrupo> mapearOficinaGrupo(Map<String, Object> resultados) {
		OficinaGrupo oficinaGrupo;
		List<OficinaGrupo> listaOficinaGrupo = new ArrayList<>();

		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");

		for (Map<String, Object> map : lista) {
			oficinaGrupo = new OficinaGrupo();
			Empresa empresa = new Empresa();
			empresa.setCodigo(((BigDecimal) map.get("IDEMPR")).intValue());
			empresa.setDescripcion((String) map.get("NOMBREMPR"));
			empresa.setTipoEmpresa((String) map.get("TIPOEMPR"));
			oficinaGrupo.setEmpresa(empresa);
			Oficina oficina = new Oficina();
			oficina.setCodigo(((BigDecimal) map.get("IDOFIC")).intValue());
			oficina.setDescripcion((String) map.get("DESCOFIC"));
			oficinaGrupo.setOficina(oficina);
			Grupo grupo = new Grupo();
			grupo.setIdGrupo(((BigDecimal) map.get("IDGRUP")).intValue());
			grupo.setDescGrupo((String) map.get("DESCGRUP"));
			oficinaGrupo.setGrupo(grupo);
			listaOficinaGrupo.add(oficinaGrupo);
		}
		return listaOficinaGrupo;
	}

	public static List<GrupoPersonal> mapearPersonalGrupo(Map<String, Object> resultados) {
		GrupoPersonal grupoPersonal;
		List<GrupoPersonal> listaGrupoPersonal = new ArrayList<>();

		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");
		for (Map<String, Object> map : lista) {
			grupoPersonal = new GrupoPersonal();
			grupoPersonal.setTrabajador(obtenerDatosTrabajador(map));
			listaGrupoPersonal.add(grupoPersonal);
		}
		return listaGrupoPersonal;
	}

	private static String obtenerNombresTrabajador(String nombre, String apeMaterno, String apePaterno) {
		StringBuilder builder = new StringBuilder();
		builder.append(apePaterno != null ? apePaterno : "")
				.append(apePaterno != null && apeMaterno != null ? " " : apePaterno == null ? "" : ", ")
				.append(apeMaterno != null ? apeMaterno : "").append(apeMaterno != null ? ", " : "")
				.append(nombre != null ? nombre : "");
		return builder.toString();
	}

	public static Trabajador mapearTrabajador(Map<String, Object> resultados) {
		Trabajador trabajador = new Trabajador();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");
		for (Map<String, Object> map : lista) {
			trabajador = obtenerDatosTrabajador(map);
		}
		return trabajador;
	}

	private static Trabajador obtenerDatosTrabajador(Map<String, Object> map) {
		String nombres = CastUtil.leerValorMapString(map, "NOMBRES");
		String apePaterno = CastUtil.leerValorMapString(map, "APEPATERNO");
		String apeMaterno = CastUtil.leerValorMapString(map, "APEMATERNO");

		Trabajador trabajador = new Trabajador();
		Perfil perfil = new Perfil();
		trabajador.setCodigo(map.get("IDPERS") != null ? ((BigDecimal) map.get("IDPERS")).intValue()
				: map.get("CODUSUARIO") != null ? ((BigDecimal) map.get("CODTRABAJADOR")).intValue() : null);
		trabajador.setFicha(map.get("FICHA") != null ? ((BigDecimal) map.get("FICHA")).intValue() : null);
		trabajador.setNombre(obtenerNombresTrabajador(nombres, apeMaterno, apePaterno));
		trabajador.setCodUsuario(map.get("CODUSUARIO") != null ? (String) map.get("CODUSUARIO") : null);
		trabajador.setDirElectronica(map.get("DIRELECTRONICA") != null ? (String) map.get("DIRELECTRONICA") : null);
		
		trabajador.setFlagCompletarAlta(CastUtil.leerValorMapInteger(map, "FLAGCOMPLETARALTA"));
		trabajador.setUsuarioAgc(CastUtil.leerValorMapString(map, "USUARIOAGC"));

		perfil.setCodigo(map.get("CODPERFIL") != null ? ((BigDecimal) map.get("CODPERFIL")).intValue() : null);
		perfil.setDescripcion(map.get("PERFIL") != null ? (String) map.get("PERFIL") : null);
		trabajador.setPerfil(perfil);
		trabajador.setEstado(map.get("ESTADO") != null ? (String) map.get("ESTADO") : null);
		return trabajador;
	}
}
