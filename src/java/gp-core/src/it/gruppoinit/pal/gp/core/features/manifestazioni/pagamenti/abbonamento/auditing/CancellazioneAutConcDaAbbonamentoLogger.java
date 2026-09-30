package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class CancellazioneAutConcDaAbbonamentoLogger extends AbstractAbbonamentoLogger {

    private static final String CONST_ESTREMIAUTORIZZAZIONE = "ESTREMIAUTORIZZAZIONE";
    private static final String CONST_IDAUTORIZZAZIONE = "IDAUTORIZZAZIONE";
    private static final String CONST_UIDBORSELLINO = "UIDBORSELLINO";
    private String estremiAutorizzazione;
    private Integer idAutorizzazione;

    public CancellazioneAutConcDaAbbonamentoLogger(Autorizzazioni autorizzazione, String autore) {

	super(autore);
	this.idAutorizzazione = autorizzazione.getId().getCodice();
	this.estremiAutorizzazione = autorizzazione.getTransientEstremiAut();
	addMessaggio(CancellazioneAutConcDaAbbonamentoLoggerEnum.INIZIALE);
    }

    public CancellazioneAutConcDaAbbonamentoLogger addMessaggio(CancellazioneAutConcDaAbbonamentoLoggerEnum messaggio) {

	this.messaggio = messaggio.getMessaggio() //
		.replace(CONST_IDAUTORIZZAZIONE, String.valueOf(idAutorizzazione)) //
		.replace(CONST_ESTREMIAUTORIZZAZIONE, String.valueOf(estremiAutorizzazione));
	return this;
    }

    public CancellazioneAutConcDaAbbonamentoLogger addMessaggio(CancellazioneAutConcDaAbbonamentoLoggerEnum messaggio, String guidBorsellino) {

	this.messaggio = messaggio.getMessaggio() //
		.replace(CONST_IDAUTORIZZAZIONE, String.valueOf(idAutorizzazione)) //
		.replace(CONST_ESTREMIAUTORIZZAZIONE, String.valueOf(estremiAutorizzazione)) //
		.replace(CONST_UIDBORSELLINO, guidBorsellino);
	return this;
    }

    public enum CancellazioneAutConcDaAbbonamentoLoggerEnum {

	INIZIALE("Operazioni cancellazione autorizzazione/concessione collegata a borsellino\nid autorizzazione: " + CONST_IDAUTORIZZAZIONE +
		 "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE + "\n" + DELIMITATORE_LOG),
	AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO("Autorizzazione: " + CONST_IDAUTORIZZAZIONE + "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE +
					      " collegata a nuovo borsellino per cancellazione\n" + DELIMITATORE_LOG), //
	AUTORIZZAZIONE_RIMOSSA_DA_BORSELLINO("Autorizzazione: " + CONST_IDAUTORIZZAZIONE + "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE +
					     " rimossa da borsellino " + CONST_UIDBORSELLINO + " per cancellazione\n" + DELIMITATORE_LOG);

	private final String messaggio;

	public String getMessaggio() {

	    return messaggio;
	}

	CancellazioneAutConcDaAbbonamentoLoggerEnum(String messaggio) {

	    this.messaggio = messaggio;
	}
    }
}
