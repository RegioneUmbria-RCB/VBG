package it.gruppoinit.pal.gp.core.service.exception;

import java.util.List;

import javax.validation.ConstraintViolation;

public class BusinessValidationException extends BaseValidationException {

	private static final long serialVersionUID = 5185623765795441512L;

	public BusinessValidationException(List<ConstraintViolation<?>> invalidValues, String message, Throwable cause) {

		super(invalidValues, message, cause);
	}

	public BusinessValidationException(Throwable cause) {

		this(null, cause.getMessage(), cause);
	}

	public BusinessValidationException(String message) {

		this(null, message, null);
	}
}
