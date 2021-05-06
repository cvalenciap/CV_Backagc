package pe.com.sedapal.agc.model.enums;

import java.io.Serializable;

public enum Estados implements Serializable  {
	  ANULADO("0"),
	  POR_ENVIAR("1"),
	  ENVIADO("2"),
	  EN_EJECUCION("3"),
	  EJECUTADO("4"),
	  RECEPCIONADO("5"),
	  EJECUTADO_PARCIAL("6"),
	  DEV_SEDAPAL("7"),
	  DEV_CONTRATISTA("8"),
	  RECHAZADO("9");
		
	private String valorEstado;
	
	private Estados (String valor){
		this.valorEstado = valor;
	}
	
	public String getValorEstado() {
		return valorEstado;
	}
}
