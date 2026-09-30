package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroDatiRiscossione extends ParameterBase {

    public ParametroDatiRiscossione(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroDatiRiscossione() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "DATI_RISCOSSIONE";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
