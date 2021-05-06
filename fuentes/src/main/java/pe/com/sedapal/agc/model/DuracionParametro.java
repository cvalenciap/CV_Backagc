package pe.com.sedapal.agc.model;

public class DuracionParametro {
  private Integer mesesConsulta;
  private Integer diasRango;
  
  
  public DuracionParametro() {
	  super();
	}
  
	public Integer getMesesConsulta() {
		return mesesConsulta;
	}
	public void setMesesConsulta(Integer mesesConsulta) {
		this.mesesConsulta = mesesConsulta;
	}
	public Integer getDiasRango() {
		return diasRango;
	}
	public void setDiasRango(Integer diasRango) {
		this.diasRango = diasRango;
	}
}
