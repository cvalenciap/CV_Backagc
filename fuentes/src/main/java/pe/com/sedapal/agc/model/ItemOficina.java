package pe.com.sedapal.agc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOficina extends Auditoria{
	private Item item;
	private	Oficina oficina;
	private Estado estado;
	private Integer cantPersExtOfic;
		
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Integer getCantPersExtOfic() {
		return cantPersExtOfic;
	}
	public void setCantPersExtOfic(Integer cantPersExtOfic) {
		this.cantPersExtOfic = cantPersExtOfic;
	}
}
