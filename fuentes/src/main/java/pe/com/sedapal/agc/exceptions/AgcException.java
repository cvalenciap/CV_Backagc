package pe.com.sedapal.agc.exceptions;

import pe.com.sedapal.agc.model.response.Error;

public class AgcException extends RuntimeException {
	
	private static final long serialVersionUID = 6608856425697781852L;
	
	Error error;

	public AgcException(String message, Throwable cause) {
		super(message, cause);
	}

	public AgcException(String message) {
		super(message);
	}
	
	public AgcException(String message, Error error) {
		super(message);
		this.error = error;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	
	
}
