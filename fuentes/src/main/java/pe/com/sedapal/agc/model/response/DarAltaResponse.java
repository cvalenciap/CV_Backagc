package pe.com.sedapal.agc.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.PersonaContratista;
import pe.com.sedapal.agc.model.RegistroAlta;
import pe.com.sedapal.agc.model.enums.ResultadoCarga;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DarAltaResponse {

	private List<String> errores = new ArrayList<>();
	@JsonIgnore
	private List<RegistroAlta> personalAlta = new ArrayList<>();
	private ResultadoCarga estado;

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public List<RegistroAlta> getPersonalAlta() {
		return personalAlta;
	}

	public void setPersonalAlta(List<RegistroAlta> personalAlta) {
		this.personalAlta = personalAlta;
	}

	public ResultadoCarga getEstado() {
		return estado;
	}

	public void setEstado(ResultadoCarga estado) {
		this.estado = estado;
	}

}
