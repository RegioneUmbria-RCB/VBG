package it.gruppoinit.pal.gp.core.exception;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;

public class BaseValidationException extends RuntimeException {

    private static final long serialVersionUID = -1299772137200392682L;
    private List<InvalidValue> invalidValues = new ArrayList<InvalidValue>();

    public BaseValidationException(Throwable cause) {

	this(null, cause.getMessage(), cause);
    }

    public BaseValidationException(String message) {

	this(null, message, null);
    }

    public BaseValidationException(List<InvalidValue> invalidValues, String message, Throwable cause) {

	this(message, cause);
	if (invalidValues != null) {
	    this.invalidValues = invalidValues;
	}
	if (cause != null) {
	    if (cause instanceof BaseValidationException) {
		this.invalidValues.addAll(((BaseValidationException) cause).getInvalidValues());
	    }
	}
    }

    @SuppressWarnings("unused")
    private BaseValidationException() {

	super();
    }

    private BaseValidationException(String message, Throwable cause) {

	super(message, cause);
    }

    public List<InvalidValue> getInvalidValues() {

	return invalidValues;
    }
}
