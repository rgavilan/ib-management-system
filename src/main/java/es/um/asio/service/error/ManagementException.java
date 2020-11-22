package es.um.asio.service.error;

public class ManagementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3586521635144353568L;

	public ManagementException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagementException(String message) {
		super(message);
	}

	public ManagementException() {
	}

}
