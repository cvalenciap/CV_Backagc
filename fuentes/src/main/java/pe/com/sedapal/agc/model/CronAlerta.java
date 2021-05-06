package pe.com.sedapal.agc.model;

public class CronAlerta {
	private Long n_sec_template;
	private String v_cron_tiempo;
	private String v_url;
	private String v_token;
	
	public CronAlerta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CronAlerta(Long n_sec_template, String v_cron_tiempo, String v_url, String v_token) {
		super();
		this.n_sec_template = n_sec_template;
		this.v_cron_tiempo = v_cron_tiempo;
		this.v_url = v_url;
		this.v_token = v_token;
	}

	public Long getN_sec_template() {
		return n_sec_template;
	}

	public void setN_sec_template(Long n_sec_template) {
		this.n_sec_template = n_sec_template;
	}

	public String getV_cron_tiempo() {
		return v_cron_tiempo;
	}

	public void setV_cron_tiempo(String v_cron_tiempo) {
		this.v_cron_tiempo = v_cron_tiempo;
	}

	public String getV_url() {
		return v_url;
	}

	public void setV_url(String v_url) {
		this.v_url = v_url;
	}

	public String getV_token() {
		return v_token;
	}

	public void setV_token(String v_token) {
		this.v_token = v_token;
	}
	
	
	
}
