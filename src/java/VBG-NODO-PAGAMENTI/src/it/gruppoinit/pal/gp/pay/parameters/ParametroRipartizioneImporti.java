package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroRipartizioneImporti extends ParameterBase {

    private static final String NOME_PARAMETRO = "RIPARTIZIONE_CONTI";

    public ParametroRipartizioneImporti(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroRipartizioneImporti() {

	this(NOME_PARAMETRO, "");
    }

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
