package pe.com.sedapal.agc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Archivo;
import pe.com.sedapal.agc.model.CabeceraCargaPersonal;
import pe.com.sedapal.agc.model.Cargo;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Estado;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.Oficina;
import pe.com.sedapal.agc.model.Parametro;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.TramaPersonal;
import pe.com.sedapal.agc.model.request.DarAltaRequest;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;

@Component
public class PersonalContratistaMapper implements ObjectMapper<PersonaContratista> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaContratista> mapearRespuestaBd(Map<String, Object> map) {
		final List<PersonaContratista> listaPersonal = new ArrayList<>();
		List<Map<String, Object>> lista = null;
		if (map.get("O_C_LISTA_PERSONA") != null) {
			lista = (List<Map<String, Object>>) map.get("O_C_LISTA_PERSONA");
		} else if (map.get("O_C_RESULTADO") != null) {
			lista = (List<Map<String, Object>>) map.get("O_C_RESULTADO");
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}
		

		lista.forEach(item -> {
			PersonaContratista personal = new PersonaContratista();

			personal.setNro(CastUtil.leerValorMapInteger(item, "RNUM"));

			personal.setCodigoEmpleado(CastUtil.leerValorMapInteger(item, "CODIGOSEDAPAL"));
			personal.setCodigoEmpleadoContratista(CastUtil.leerValorMapString(item, "CODEMPCONTRATISTA"));
			personal.setNumeroDocumento(CastUtil.leerValorMapString(item, "NUMERODOCUMENTO"));
			personal.setNombresCompletos(CastUtil.leerValorMapString(item, "NOMBRECOMPLETO"));
			personal.setNombres(CastUtil.leerValorMapString(item, "NOMBRES"));
			personal.setApellidoPaterno(CastUtil.leerValorMapString(item, "APEPATERNO"));
			personal.setApellidoMaterno(CastUtil.leerValorMapString(item, "APEMATERNO"));
			personal.setDireccion(CastUtil.leerValorMapString(item, "DIRECCION"));
			personal.setFechaNacimiento(CastUtil.leerValorMapString(item, "FECHANACIMIENTO"));
			personal.setTelefonoPersonal(CastUtil.leerValorMapString(item, "TELEFONOPERSONAL"));
			personal.setTelefonoAsignado(CastUtil.leerValorMapString(item, "TELEFONOASIGNADO"));
			personal.setCorreoElectronico(CastUtil.leerValorMapString(item, "CORREO"));
			personal.setFechaIngreso(CastUtil.leerValorMapString(item, "FECHAINGRESO"));
			personal.setFechaAlta(CastUtil.leerValorMapString(item, "FECHAALTA"));			
			personal.setFechaBaja(CastUtil.leerValorMapString(item, "FECHABAJA"));
			personal.setObservacionBaja(CastUtil.leerValorMapString(item, "OBSERVACIONBAJA"));
			personal.setIndicadorAlta(CastUtil.leerValorMapString(item, "INDICADORALTA"));

			Empresa empresa = new Empresa();
			empresa.setCodigo(CastUtil.leerValorMapInteger(item, "CODCONTRATISTA"));
			empresa.setCodigoOpen(CastUtil.leerValorMapInteger(item, "CODCONTRATISTAOPEN"));
			empresa.setDescripcion(CastUtil.leerValorMapString(item, "NOMBRECONTRATISTA"));
			personal.setContratista(empresa);
			
			TipoCargo tipoCargo = new TipoCargo();
			tipoCargo.setId(CastUtil.leerValorMapString(item, "CODTIPOCARGO"));
			tipoCargo.setDescripcion(CastUtil.leerValorMapString(item, "TIPOCARGO"));
			personal.setTipoCargo(tipoCargo);

			Cargo cargo = new Cargo();
			cargo.setId(CastUtil.leerValorMapString(item, "CODCARGO"));
			cargo.setDescripcion(CastUtil.leerValorMapString(item, "CARGO"));
			personal.setCargo(cargo);

			Oficina oficina = new Oficina();
			oficina.setCodigo(CastUtil.leerValorMapInteger(item, "CODIGOOFICINA"));
			oficina.setDescripcion(CastUtil.leerValorMapString(item, "OFICINA"));
			personal.setOficina(oficina);
			
			Item itemObjeto = new Item();
			itemObjeto.setId(CastUtil.leerValorMapInteger(item, "CODITEM"));
			itemObjeto.setDescripcion(CastUtil.leerValorMapString(item, "ITEM"));
			personal.setItem(itemObjeto);
			
			Parametro motivoAlta = new Parametro();
			motivoAlta.setCodigo(CastUtil.leerValorMapInteger(item, "CODMOTIVOALTA"));
			motivoAlta.setDescripcionCorta(CastUtil.leerValorMapString(item, "MOTIVOALTA"));
			personal.setMotivoAlta(motivoAlta);
			
			Parametro motivoBaja = new Parametro();
			motivoBaja.setCodigo(CastUtil.leerValorMapInteger(item, "CODMOTIVOBAJA"));
			motivoBaja.setDescripcionCorta(CastUtil.leerValorMapString(item, "MOTIVOBAJA"));
			personal.setMotivoBaja(motivoBaja);

			Estado estadoSedapal = new Estado();
			estadoSedapal.setId(CastUtil.leerValorMapString(item, "CODIGOESTADOSEDAPAL"));
			estadoSedapal.setDescripcion(CastUtil.leerValorMapString(item, "ESTADOSEDAPAL"));
			personal.setEstadoPersonal(estadoSedapal);

			Estado estadoLaboral = new Estado();
			estadoLaboral.setId(CastUtil.leerValorMapString(item, "CODIGOESTADOLABORAL"));
			estadoLaboral.setDescripcion(CastUtil.leerValorMapString(item, "ESTADOLABORAL"));
			personal.setEstadoLaboral(estadoLaboral);
			
			Archivo archivoFoto = new Archivo();
			archivoFoto.setId(CastUtil.leerValorMapInteger(item, "IDARCHIVOFOTO"));
			archivoFoto.setTipoArchivo(CastUtil.leerValorMapString(item, "TIPOARCHIVOFOTO"));
			archivoFoto.setNombreArchivo(CastUtil.leerValorMapString(item, "NOMBREARCHIVOFOTO"));
			archivoFoto.setRutaArchivo((String)item.get("RUTAARCHIVOFOTO"));
			personal.setArchivoFotoPersonal(archivoFoto);
			
			Archivo archivoCV = new Archivo();
			archivoCV.setId(CastUtil.leerValorMapInteger(item, "IDARCHIVOCV"));
			archivoCV.setTipoArchivo(CastUtil.leerValorMapString(item, "TIPOARCHIVOCV"));
			archivoCV.setNombreArchivo(CastUtil.leerValorMapString(item, "NOMBREARCHIVOCV"));
			archivoCV.setRutaArchivo((String)item.get("RUTAARCHIVOCV"));
			personal.setArchivoCvPersonal(archivoCV);
			
			
			
			personal.setUsuarioCreacion(CastUtil.leerValorMapString(item, "USUARIOCREACION"));

			listaPersonal.add(personal);
		});

		return listaPersonal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer obtenerTotalregistros(Map<String, Object> map) {
		if (map.get("O_C_LISTA_PERSONA") != null) {
			return CastUtil.leerValorMapInteger(((List<Map<String, Object>>) map.get("O_C_LISTA_PERSONA")).get(0),
					"totalRegistros");
		} else if (map.get("O_C_RESULTADO") != null) {
			return CastUtil.leerValorMapInteger(((List<Map<String, Object>>) map.get("O_C_RESULTADO")).get(0),
					"totalRegistros");
		} else {
			throw new AgcException(Constantes.Mensajes.ERROR_MAPPER);
		}
	}
	
	public Object[] obtenerObjetoCabeceraDB(CabeceraCargaPersonal cabecera) {
		Object[] objCabeceraDb = new Object[CabeceraCargaPersonal.class.getDeclaredFields().length];
		objCabeceraDb[3] = cabecera.getV_nom_archivo();
		objCabeceraDb[4] = cabecera.getN_cant_registros();
		objCabeceraDb[7] = cabecera.getV_usuario_reg();
		return objCabeceraDb;
	}
	
	public Object[] obtenerObjetoTramaPersonalDB(TramaPersonal trama) {
		Object[] objCabecera = new Object[TramaPersonal.class.getDeclaredFields().length];
		objCabecera[0] = trama.getV_dni();
		objCabecera[1] = trama.getV_ape_paterno();
		objCabecera[2] = trama.getV_ape_materno();
		objCabecera[3] = trama.getV_nombres();
		objCabecera[4] = trama.getV_fec_nac();
		objCabecera[5] = trama.getV_direccion();
		objCabecera[6] = trama.getV_fec_ingreso();
		objCabecera[7] = trama.getV_cod_cargo();
		objCabecera[8] = trama.getV_cod_item();
		objCabecera[9] = trama.getV_cod_oficina();
		objCabecera[10] = trama.getV_telf_pers();
		objCabecera[11] = trama.getV_telf_asig();
		objCabecera[12] = trama.getV_correo();
		objCabecera[13] = trama.getV_cod_pers_ctrat();
		objCabecera[14] = trama.getV_cod_empresa();
		objCabecera[15] = trama.getV_usuario_reg();
		objCabecera[16] = trama.getN_flag_archivos();
		return objCabecera;
	}
	
	public Object[] obtenerRegistroAltaDb(PersonaContratista personal, DarAltaRequest request) {
		Object[] objetoBd = new Object[15];
		objetoBd[0] = personal.getCodigoEmpleado();
		objetoBd[1] = personal.getNumeroDocumento();
		objetoBd[2] = personal.getNombresCompletos();
		objetoBd[3] = personal.getCargo().getId();
		objetoBd[4] = personal.getCargo().getDescripcion();
		objetoBd[5] = personal.getOficina().getCodigo();
		objetoBd[6] = personal.getOficina().getDescripcion();
		objetoBd[7] = personal.getItem().getId();
		objetoBd[8] = personal.getContratista().getCodigo();
		objetoBd[9] = personal.getContratista().getDescripcion();
		objetoBd[10] = personal.getMotivoAlta().getCodigo();
		objetoBd[11] = null;
		objetoBd[12] = null;
		objetoBd[13] = request.getUsuarioAlta();
		objetoBd[14] = personal.getUsuarioCreacion();
		return objetoBd;
	}

}
