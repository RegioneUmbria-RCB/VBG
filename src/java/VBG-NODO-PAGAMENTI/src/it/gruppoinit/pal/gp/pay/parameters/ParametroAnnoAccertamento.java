package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroAnnoAccertamento extends ParameterBase {

    public ParametroAnnoAccertamento(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroAnnoAccertamento() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "ANNO_ACCERTAMENTO";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
