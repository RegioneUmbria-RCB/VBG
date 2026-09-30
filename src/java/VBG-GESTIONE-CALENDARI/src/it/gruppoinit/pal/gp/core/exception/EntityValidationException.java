package it.gruppoinit.pal.gp.core.exception;

import java.util.List;

import org.hibernate.validator.InvalidValue;

public class EntityValidationException extends BaseValidationException {

    private static final long serialVersionUID = -1868263664723254397L;

    public EntityValidationException(List<InvalidValue> invalidValues, String message, Throwable cause) {

	super(invalidValues, message, cause);
    }

    public EntityValidationException(Throwable cause) {

	this(null, cause.getMessage(), cause);
    }

    public EntityValidationException(String message) {

	this(null, message, null);
    }
}
