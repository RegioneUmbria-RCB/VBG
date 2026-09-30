package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroAggiungiGiorniADataScadenzaAvviso extends ParameterBase {

    public ParametroAggiungiGiorniADataScadenzaAvviso(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroAggiungiGiorniADataScadenzaAvviso() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "AGGIUNGI_GIORNI_A_DATA_SCADENZA_AVVISO";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
