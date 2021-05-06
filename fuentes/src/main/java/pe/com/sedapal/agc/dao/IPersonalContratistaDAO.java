package pe.com.sedapal.agc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Archivo;
import pe.com.sedapal.agc.model.CabeceraCargaPersonal;
import pe.com.sedapal.agc.model.DataCorreoCarga;
import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.ResponseCargaArchivo;
import pe.com.sedapal.agc.model.Trabajador;
import pe.com.sedapal.agc.model.TramaPersonal;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;
import pe.com.sedapal.agc.model.request.CesarPersonalRequest;
import pe.com.sedapal.agc.model.request.DarAltaRequest;
import pe.com.sedapal.agc.model.request.PersonalContratistaRequest;
import pe.com.sedapal.agc.model.response.CargaPersonalResponse;
import pe.com.sedapal.agc.model.response.CesarPersonalResponse;
import pe.com.sedapal.agc.model.response.CeseMasivoResponse;
import pe.com.sedapal.agc.model.response.DarAltaResponse;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.model.response.ListaPaginada;
import pe.com.sedapal.agc.model.response.Paginacion;

public interface IPersonalContratistaDAO {
	Paginacion getPaginacion();

	Error getError();

	public ListaPaginada<PersonaContratista> listarPersonalContratista(PersonalContratistaRequest request,
			Integer pagina, Integer registros);

	void registrarPersonalContratista(PersonaContratista personaContratista);

	void actualizarPersonalContratista(PersonaContratista personaContratista);

	Map<String, Object> obtenerParametrosBandeja(Integer idPersonal, Integer idPerfil) throws AgcException;

	public List<CargaPersonalResponse> cargaMasivaPersonal(CabeceraCargaPersonal cabecera,
			List<TramaPersonal> tramasPersonal, Integer idEmpresa) throws AgcException, SQLException;

	public List<DataCorreoCarga> obtenerDataCorreoCargaMasiva(String codLote);

	public void enviarCorreoCargaPersonal(Integer idEmpresa, Integer idOficina, String nomEmpresa, String codLote,
			String bodyMail);

	public ResponseCargaArchivo insertarArchivoPersonal(String dni, Integer codEmpleado1, Integer codEmpleado2,
			Archivo archivoPersonal, Integer idEmpresa, ResponseCargaArchivo response) throws AgcException;

	DarAltaResponse validarAltaPersonal(DarAltaRequest request) throws AgcException, SQLException;

	public void enviarCorreoAltaSedapal(Integer codOficina, Integer mostrarPrimeraSeccion,
			Integer mostrarSegundaSeccion, String listaContratistasPrimeraSeccion,
			String listaContratistasSegundaSeccion, StringBuilder tablaPrimeraSeccion,
			StringBuilder tablaSegundaSeccion);

	public void enviarCorreoAltaContratista(String usuarioCreacion, String contratista, String oficina,
			Integer mostrarPrimeraSeccion, Integer mostrarSegundaSeccion, StringBuilder tablaPrimeraSeccion,
			StringBuilder tablaSegundaSeccion);
	
	public CesarPersonalResponse cesarPersonal(CesarPersonalRequest request);

	ResultadoCarga completarAltaPersonal(Trabajador trabajador, String usuarioAuditoria);

	CeseMasivoResponse ceseMasivoPersonal(Integer idEmpresa, String usuarioPeticion);

}
