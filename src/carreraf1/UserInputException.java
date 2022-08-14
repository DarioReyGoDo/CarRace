package carreraf1;

public class UserInputException extends Exception {

	private static final long serialVersionUID = -8482426523079339182L;

	public UserInputException() {
	}

	public UserInputException(String message) {
		super(message);
	}

	public UserInputException(Throwable cause) {
		super(cause);
	}

	public UserInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
