package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroIdUnitaOperativa extends ParameterBase {

    public ParametroIdUnitaOperativa(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroIdUnitaOperativa() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "ID_UNITA_OPERATIVA";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
