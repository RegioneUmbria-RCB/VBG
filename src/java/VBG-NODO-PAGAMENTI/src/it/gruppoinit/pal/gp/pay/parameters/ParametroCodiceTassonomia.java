package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroCodiceTassonomia extends ParameterBase {

    public ParametroCodiceTassonomia(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroCodiceTassonomia() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "CODICE_TASSONOMIA";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
