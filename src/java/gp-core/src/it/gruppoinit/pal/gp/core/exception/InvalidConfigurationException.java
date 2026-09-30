package it.gruppoinit.pal.gp.core.exception;

public class InvalidConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -1336180001770191410L;

    public InvalidConfigurationException() {

	super();
    }

    public InvalidConfigurationException(String message, Throwable cause) {

	super(message, cause);
    }

    public InvalidConfigurationException(String message) {

	super(message);
    }

    public InvalidConfigurationException(Throwable cause) {

	super(cause);
    }
}
