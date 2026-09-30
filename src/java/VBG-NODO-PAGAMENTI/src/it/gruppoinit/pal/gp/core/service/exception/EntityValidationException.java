package it.gruppoinit.pal.gp.core.service.exception;

import java.util.List;

import javax.validation.ConstraintViolation;

public class EntityValidationException extends BaseValidationException {

	private static final long serialVersionUID = -1868263664723254397L;

	public EntityValidationException(List<ConstraintViolation<?>> invalidValues, String message, Throwable cause) {

		super(invalidValues, message, cause);
	}

	public EntityValidationException(Throwable cause) {

		this(null, cause.getMessage(), cause);
	}

	public EntityValidationException(String message) {

		this(null, message, null);
	}
}
