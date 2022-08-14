package carreraf1;

public class AccidenteException extends Exception {

	private static final long serialVersionUID = -8658871079892729767L;

	public AccidenteException() {
	}

	public AccidenteException(String message) {
		super(message);
	}

	public AccidenteException(Throwable cause) {
		super(cause);
	}

	public AccidenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccidenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
