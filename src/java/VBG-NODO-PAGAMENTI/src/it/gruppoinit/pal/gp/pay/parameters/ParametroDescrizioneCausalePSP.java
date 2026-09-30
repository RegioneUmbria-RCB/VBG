package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroDescrizioneCausalePSP extends ParameterBase {

    public ParametroDescrizioneCausalePSP(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroDescrizioneCausalePSP() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "DESCRIZIONE_CAUSALE_PSP";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
