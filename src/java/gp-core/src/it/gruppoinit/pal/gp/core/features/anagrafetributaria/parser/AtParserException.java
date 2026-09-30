package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.io.IOException;

public class AtParserException extends RuntimeException {

    public AtParserException(String messaggio) {

	super(messaggio);
    }

    public AtParserException(String string, IOException e) {

	super(string, e);
    }

    public AtParserException(Exception e) {

	super(e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4666780229607365648L;
}
