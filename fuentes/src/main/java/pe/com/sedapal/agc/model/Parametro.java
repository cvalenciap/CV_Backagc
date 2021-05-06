package pe.com.sedapal.agc.model;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.com.sedapal.agc.util.CastUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parametro {
	
	private Integer tipo;
	private Integer codigo;
	private String id;
	private String valor;
	private String descripcionCorta;
	private String detalle;
	private String estado;
	
	public Parametro() {
		super();
	}

	public Parametro(Integer tipo, Integer codigo, String valor, String descripcionCorta, String detalle, String estado) {
		super();
		this.tipo = tipo;
		this.codigo = codigo;
		this.valor = valor;
		this.descripcionCorta = descripcionCorta;
		this.detalle = detalle;
		this.estado = estado;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public static Parametro mapper(Map<String, Object> map) {
		Parametro param = new Parametro();
		param.setCodigo(CastUtil.leerValorMapInteger(map, "N_IDPARAMETR"));
		param.setValor(CastUtil.leerValorMapStringOriginCase(map, "V_IDVALO"));
		param.setDetalle(CastUtil.leerValorMapStringOriginCase(map, "V_DESCDETA"));
		return param;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
