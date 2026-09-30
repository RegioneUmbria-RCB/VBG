package it.gruppoinit.pal.gp.core.service.exception;

import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class VBGRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1959878273570795458L;

    public VBGRuntimeException() {

	super();
    }

    public VBGRuntimeException(String messaggio) {

	super(messaggio);
    }

    public VBGRuntimeException(Logger logger, ApplicationContext context, List<InvalidValue> values) {

	super(buildMessage(logger, context, values));
    }

    private static String buildMessage(Logger logger, ApplicationContext context, List<InvalidValue> values) {

	StringBuilder message = new StringBuilder("");
	for (InvalidValue value : values) {
	    String msg = Utilities.getMessageFromBundle(context, value.getMessage(), new Object[] { "" });
	    logger.error("VBGRuntimeException: invalid value: {}", msg);
	    message.append(msg);
	    message.append('\n');
	}
	return message.toString();
    }
}
