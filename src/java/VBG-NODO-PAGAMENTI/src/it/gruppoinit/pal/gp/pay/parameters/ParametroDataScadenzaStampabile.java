package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroDataScadenzaStampabile extends ParameterBase {

    public ParametroDataScadenzaStampabile(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroDataScadenzaStampabile() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "DATA_SCADENZA_STAMPABILE";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
