package pe.com.sedapal.agc.exceptions;

public class FileServerException extends RuntimeException {

	public FileServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileServerException(String message) {
		super(message);
	}
	
}
