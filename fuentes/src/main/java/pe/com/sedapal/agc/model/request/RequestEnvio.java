package pe.com.sedapal.agc.model.request;

import java.util.List;

import pe.com.sedapal.agc.model.Adjunto;
import pe.com.sedapal.agc.model.Responsable;

public class RequestEnvio {
	private Responsable responsable;
	private List<Adjunto> listaAdjuntos;
	
	public RequestEnvio() {
		super();
	}
	
	public RequestEnvio(Responsable responsable, List<Adjunto> listaAdjuntos) {
		super();
		this.responsable = responsable;
		this.listaAdjuntos = listaAdjuntos;
	}
	
	public Responsable getResponsable() {
		return responsable;
	}
	
	public void setReponsable(Responsable responsable) {
		this.responsable = responsable;
	}
	
	public List<Adjunto> getListaAdjuntos() {
		return listaAdjuntos;
	}
	
	public void setListaAdjuntos(List<Adjunto> listaAdjuntos) {
		this.listaAdjuntos = listaAdjuntos;
	}

}
