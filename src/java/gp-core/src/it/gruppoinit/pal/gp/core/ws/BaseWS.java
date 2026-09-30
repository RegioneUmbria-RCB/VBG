package it.gruppoinit.pal.gp.core.ws;

import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;

import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.dao.DataAccessException;

public class BaseWS extends BaseEnvironment {

    protected String getRootCause(Exception e) {

	StringBuffer rootCause = new StringBuffer(e.getMessage());
	if (e instanceof DataAccessException) {
	    DataAccessException dae = (DataAccessException) e;
	    Throwable t = dae.getRootCause();
	    if (t != null) {
		rootCause.append(" ").append(t.getMessage());
	    }
	} else if (e instanceof BaseValidationException) {
	    List<InvalidValue> validationMessages = ((BaseValidationException) e).getInvalidValues();
	    if (validationMessages != null) {
		for (InvalidValue invalidValue : validationMessages) {
		    rootCause.append(invalidValue).append(",");
		}
	    }
	}
	return rootCause.toString();
    }
}
