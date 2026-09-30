package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroNumeroAccertamento extends ParameterBase {

    public ParametroNumeroAccertamento(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroNumeroAccertamento() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "NUMERO_ACCERTAMENTO";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
