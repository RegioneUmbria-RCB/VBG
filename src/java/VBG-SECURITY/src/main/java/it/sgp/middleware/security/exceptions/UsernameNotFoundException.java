package it.sgp.middleware.security.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8304921376322487924L;

    public UsernameNotFoundException() {

	super();
    }

    public UsernameNotFoundException(String message, Throwable cause) {

	super(message, cause);
    }

    public UsernameNotFoundException(String message) {

	super(message);
    }

    public UsernameNotFoundException(Throwable cause) {

	super(cause);
    }
}
