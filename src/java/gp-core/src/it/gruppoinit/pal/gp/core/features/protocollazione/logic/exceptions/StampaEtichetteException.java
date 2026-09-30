package it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions;

public class StampaEtichetteException extends RuntimeException {

    public StampaEtichetteException(Exception e) {

	super(e);
    }

    public StampaEtichetteException(String message) {

	super(message);
    }

    private static final long serialVersionUID = -3902604022417201448L;
}
