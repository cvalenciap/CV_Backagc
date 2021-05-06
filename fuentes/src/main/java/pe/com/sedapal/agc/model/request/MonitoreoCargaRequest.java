package pe.com.sedapal.agc.model.request;

import java.io.File;

import pe.com.sedapal.agc.model.Actividad;
import pe.com.sedapal.agc.model.Empresa;

public class MonitoreoCargaRequest {

	private Empresa contratista;
	private Actividad actividad;
	private String codEmpleado;
	private File archivo;
	//carga
	
	public Empresa getContratista() {
		return contratista;
	}
	public void setContratista(Empresa contratista) {
		this.contratista = contratista;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public String getCodEmpleado() {
		return codEmpleado;
	}
	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}
	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	
	
}
