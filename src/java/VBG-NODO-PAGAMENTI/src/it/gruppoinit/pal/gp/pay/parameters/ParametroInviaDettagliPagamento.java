package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroInviaDettagliPagamento extends ParameterBase {

    public ParametroInviaDettagliPagamento(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroInviaDettagliPagamento() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "INVIA_DETTAGLI_PAG";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
