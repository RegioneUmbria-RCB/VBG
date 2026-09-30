package com.seda.payer.ext.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;
/**
 * @author mmontisano
 */
public enum Messages {
	EMPTY_PARAMETER,
	INVALID_PARAMETER_VALUE,
	ERROR_XML_NODE,
	XML_EXCEPTION,
	HASH_CREATION_ERROR,
	HASH_VERIFY_ERROR,
	TIME_WINDOW_EXPIRED,
	
	INITIALIZATION_SUCCESS,
	METHOD_START_PARAMETER,
	METHOD_START_2_PARAMETERS,
	BUFFER_CREATED,
	TIME_WINDOW_VERIFIED,
	HASH_VERIFIED,
	DATA_BUFFER
    ;
	
    private static ResourceBundle rb;

    public String format( Object... args ) {
        synchronized(Messages.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(Messages.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}