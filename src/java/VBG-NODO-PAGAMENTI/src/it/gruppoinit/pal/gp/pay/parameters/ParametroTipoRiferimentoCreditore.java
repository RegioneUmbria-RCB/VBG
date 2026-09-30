package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroTipoRiferimentoCreditore extends ParameterBase {

    public ParametroTipoRiferimentoCreditore(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroTipoRiferimentoCreditore() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "TIPO_RIFERIMENTO_CREDITORE";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
