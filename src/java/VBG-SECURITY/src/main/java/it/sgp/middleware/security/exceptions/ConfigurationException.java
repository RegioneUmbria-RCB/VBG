package it.sgp.middleware.security.exceptions;

public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1610195017676189349L;

    public ConfigurationException() {

	super();
    }

    public ConfigurationException(String message, Throwable cause) {

	super(message, cause);
    }

    public ConfigurationException(String message) {

	super(message);
    }

    public ConfigurationException(Throwable cause) {

	super(cause);
    }
}
