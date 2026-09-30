package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroDataScadenza extends ParameterBase {

    public ParametroDataScadenza(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroDataScadenza() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "DATA_SCADENZA";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
