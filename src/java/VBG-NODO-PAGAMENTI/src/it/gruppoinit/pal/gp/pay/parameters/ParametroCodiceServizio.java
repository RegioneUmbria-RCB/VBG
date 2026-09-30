package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroCodiceServizio extends ParameterBase {

    private static final String NOME_PARAMETRO = "CODICE_SERVIZIO";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }

    public ParametroCodiceServizio(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroCodiceServizio() {

	this(NOME_PARAMETRO, "");
    }
}
