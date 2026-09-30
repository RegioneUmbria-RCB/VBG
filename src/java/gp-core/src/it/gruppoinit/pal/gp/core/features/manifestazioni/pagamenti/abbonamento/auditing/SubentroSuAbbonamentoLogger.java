package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class SubentroSuAbbonamentoLogger extends AbstractAbbonamentoLogger {

    private static final String CONST_ESTREMIAUTORIZZAZIONE = "ESTREMIAUTORIZZAZIONE";
    private static final String CONST_IDAUTORIZZAZIONE = "IDAUTORIZZAZIONE";
    private String estremiAutorizzazione;
    private Integer idAutorizzazione;

    public SubentroSuAbbonamentoLogger(Autorizzazioni autorizzazioneSubentrata, String autore) {

	super(autore);
	this.idAutorizzazione = autorizzazioneSubentrata.getId().getCodice();
	this.estremiAutorizzazione = autorizzazioneSubentrata.getTransientEstremiAut();
	addMessaggio(SubentroSuAbbonamentoLoggerMessaggiEnum.INIZIALE);
    }

    public SubentroSuAbbonamentoLogger addMessaggio(SubentroSuAbbonamentoLoggerMessaggiEnum messaggio) {

	this.messaggio = messaggio.getMessaggio() //
		.replace(CONST_IDAUTORIZZAZIONE, String.valueOf(idAutorizzazione)) //
		.replace(CONST_ESTREMIAUTORIZZAZIONE, String.valueOf(estremiAutorizzazione));
	return this;
    }

    public enum SubentroSuAbbonamentoLoggerMessaggiEnum {

	INIZIALE("Operazioni Subentro su autorizzazione borsellino\nid autorizzazione: " + CONST_IDAUTORIZZAZIONE + "\nESTREMI AUT: " +
		 CONST_ESTREMIAUTORIZZAZIONE + "\n" + DELIMITATORE_LOG),
	AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO("Autorizzazione: " + CONST_IDAUTORIZZAZIONE + "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE +
					      " collegata a nuovo borsellino\n" + DELIMITATORE_LOG);

	private final String messaggio;

	public String getMessaggio() {

	    return messaggio;
	}

	SubentroSuAbbonamentoLoggerMessaggiEnum(String messaggio) {

	    this.messaggio = messaggio;
	}
    }
}
