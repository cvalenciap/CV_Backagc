package pe.com.sedapal.agc.model.request;


public class PageRequest {
	private Integer pagina;
	private Integer registros;
	private String orden;

	public PageRequest(Integer pagina, Integer registros)
	{
		this.pagina = pagina;
		this.registros = registros;
	}
	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getRegistros() {
		return registros;
	}
	public void setRegistros(Integer registros) {
		this.registros = registros;
	}
	
	public PageRequest() {

		this.pagina = 1;
		this.registros = 1;
		this.orden="DESCRIPCION";
	}
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
}
