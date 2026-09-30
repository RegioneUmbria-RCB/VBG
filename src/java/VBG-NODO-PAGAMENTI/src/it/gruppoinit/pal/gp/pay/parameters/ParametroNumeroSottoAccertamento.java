package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroNumeroSottoAccertamento extends ParameterBase {

    public ParametroNumeroSottoAccertamento(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroNumeroSottoAccertamento() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "NUMERO_SOTTO_ACCERTAMENTO";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
