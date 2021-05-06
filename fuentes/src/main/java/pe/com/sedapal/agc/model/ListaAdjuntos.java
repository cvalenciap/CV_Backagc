package pe.com.sedapal.agc.model;

import java.util.List;

public class ListaAdjuntos {
	private List<Adjunto> listaAdjuntosSedapal;
	private List<Adjunto> listaAdjuntosContratista;

	public ListaAdjuntos() {
		super();
	}
	
	public ListaAdjuntos(List<Adjunto> listaAdjuntosSedapal, List<Adjunto> listaAdjuntosContratista) {
		super();
		this.listaAdjuntosSedapal = listaAdjuntosSedapal;
		this.listaAdjuntosContratista = listaAdjuntosContratista;
	}
	
	public List<Adjunto> getListaAdjuntosSedapal() {
		return listaAdjuntosSedapal;
	}
	
	public void setListaAdjuntosSedapal(List<Adjunto> listaAdjuntosSedapal) {
		this.listaAdjuntosSedapal = listaAdjuntosSedapal;
	}

	public List<Adjunto> getListaAdjuntosContratista() {
		return listaAdjuntosContratista;
	}
	
	public void setListaAdjuntosContratista(List<Adjunto> listaAdjuntosContratista) {
		this.listaAdjuntosContratista = listaAdjuntosContratista;
	}

}
