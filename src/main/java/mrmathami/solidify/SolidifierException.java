package mrmathami.solidify;

public class SolidifierException extends Exception {
	private static final long serialVersionUID = 8843653637895566906L;

	public SolidifierException() {
	}

	public SolidifierException(String message) {
		super(message);
	}

	public SolidifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public SolidifierException(Throwable cause) {
		super(cause);
	}
}
