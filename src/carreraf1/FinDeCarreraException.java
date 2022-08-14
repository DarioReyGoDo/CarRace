package carreraf1;

public class FinDeCarreraException extends Exception {

	private static final long serialVersionUID = -8208939491335929733L;

	public FinDeCarreraException() {
	}

	public FinDeCarreraException(String message) {
		super(message);
	}

	public FinDeCarreraException(Throwable cause) {
		super(cause);
	}

	public FinDeCarreraException(String message, Throwable cause) {
		super(message, cause);
	}

	public FinDeCarreraException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
