package it.gruppoinit.pal.gp.core.exception;

public class ValidazioneSchemaException extends Exception {

    private String xsdFile;
    private String oggettoDellaValidazione;
    private static final long serialVersionUID = -3965629697649674729L;

    public ValidazioneSchemaException() {

	super();
    }

    public ValidazioneSchemaException(String message, Throwable cause) {

	super(message, cause);
    }

    public ValidazioneSchemaException(String message) {

	super(message);
    }

    public ValidazioneSchemaException(Throwable cause) {

	super(cause);
    }

    public String getXsdFile() {

	return xsdFile;
    }

    public String getOggettoDellaValidazione() {

	return oggettoDellaValidazione;
    }

    public ValidazioneSchemaException(String xsdFile, String oggettoDellaValidazione, Throwable cause) {

	super();
	this.xsdFile = xsdFile;
	this.oggettoDellaValidazione = oggettoDellaValidazione;
    }

    @Override
    public String toString() {

	return "ValidazioneSchemaException [xsdFile=" + xsdFile + ", oggettoDellaValidazione=" + oggettoDellaValidazione + "]"
		+ (getCause() != null ? getCause() : "");
    }
}
