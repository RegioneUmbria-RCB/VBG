package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroInviaSoloPosizioniDiSoggettiConMail extends ParameterBase {

    public ParametroInviaSoloPosizioniDiSoggettiConMail(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroInviaSoloPosizioniDiSoggettiConMail() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "INVIA_SOLO_POSIZIONI_DI_SOGGETTI_CON_MAIL";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
