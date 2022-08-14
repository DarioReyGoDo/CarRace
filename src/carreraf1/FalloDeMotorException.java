package carreraf1;

public class FalloDeMotorException extends Exception {

	private static final long serialVersionUID = -8778534430271398980L;

	public FalloDeMotorException() {
	}

	public FalloDeMotorException(String message) {
		super(message);
	}

	public FalloDeMotorException(Throwable cause) {
		super(cause);
	}

	public FalloDeMotorException(String message, Throwable cause) {
		super(message, cause);
	}

	public FalloDeMotorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
