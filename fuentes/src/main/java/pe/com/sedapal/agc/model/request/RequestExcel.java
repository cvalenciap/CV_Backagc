package pe.com.sedapal.agc.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import pe.com.sedapal.agc.model.MonitoreoCabecera;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestExcel {

	MonitoreoCabecera monitoreo;
	List<Object> listaObjetos;
	public MonitoreoCabecera getMonitoreo() {
		return monitoreo;
	}
	public void setMonitoreo(MonitoreoCabecera monitoreo) {
		this.monitoreo = monitoreo;
	}
	public List<Object> getListaObjetos() {
		return listaObjetos;
	}
	public void setListaObjetos(List<Object> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}
	
	
}
