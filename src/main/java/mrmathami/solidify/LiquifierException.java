package mrmathami.solidify;

public class LiquifierException extends Exception {
	private static final long serialVersionUID = -4700472448308385105L;

	public LiquifierException() {
	}

	public LiquifierException(String message) {
		super(message);
	}

	public LiquifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public LiquifierException(Throwable cause) {
		super(cause);
	}
}
