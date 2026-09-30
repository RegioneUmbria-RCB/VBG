package it.gruppoinit.pal.gp.core.service.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class BaseValidationException extends RuntimeException {

	private static final long serialVersionUID = -1299772137200392682L;
	private Collection<ConstraintViolation<?>> invalidValues = new ArrayList<ConstraintViolation<?>>();

	public BaseValidationException(Throwable cause) {

		this(null, cause.getMessage(), cause);
	}

	public BaseValidationException(String message) {

		this(null, message, null);
	}

	public BaseValidationException(Collection<ConstraintViolation<?>> invalidValues, String message, Throwable cause) {

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

	public Collection<ConstraintViolation<?>> getInvalidValues() {

		return invalidValues;
	}

	public Set<ExceptionHelper> getListaErrori() {

		StackTraceElement[] stackTraceElements = super.getStackTrace();
		Set<ExceptionHelper> list = new LinkedHashSet<ExceptionHelper>();
		if (stackTraceElements != null) {
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				if (stackTraceElement.getClassName().indexOf("it.gruppoinit.pal.gp") >= 0) {
					ExceptionHelper exceptionHelper = new ExceptionHelper();
					exceptionHelper.setValore(stackTraceElement);
					list.add(exceptionHelper);
				}
			}
		}
		if (this.getCause() != null) {
			if (this.getCause() instanceof EntityValidationException) {
				ExceptionHelper exceptionHelper = new ExceptionHelper();
				exceptionHelper.setNome("Causato da:");
				list.add(exceptionHelper);
				list.addAll(((EntityValidationException) this.getCause()).getListaErrori());
			}
		}
		return list;
	}
}
