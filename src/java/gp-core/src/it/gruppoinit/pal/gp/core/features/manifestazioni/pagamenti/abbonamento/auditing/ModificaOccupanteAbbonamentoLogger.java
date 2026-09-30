package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class ModificaOccupanteAbbonamentoLogger extends AbstractAbbonamentoLogger {

    private static final String CONST_ESTREMIAUTORIZZAZIONE = "ESTREMIAUTORIZZAZIONE";
    private static final String CONST_IDAUTORIZZAZIONE = "IDAUTORIZZAZIONE";
    private String estremiAutorizzazione;
    private Integer idAutorizzazione;

    public ModificaOccupanteAbbonamentoLogger(Autorizzazioni autorizzazione, String autore) {

	super(autore);
	this.idAutorizzazione = autorizzazione.getId().getCodice();
	this.estremiAutorizzazione = autorizzazione.getTransientEstremiAut();
	addMessaggio(ModificaOccupanteAbbonamentoLoggerEnum.INIZIALE);
    }

    public ModificaOccupanteAbbonamentoLogger addMessaggio(ModificaOccupanteAbbonamentoLoggerEnum messaggio) {

	this.messaggio = messaggio.getMessaggio() //
		.replace(CONST_IDAUTORIZZAZIONE, String.valueOf(idAutorizzazione)) //
		.replace(CONST_ESTREMIAUTORIZZAZIONE, String.valueOf(estremiAutorizzazione));
	return this;
    }

    public enum ModificaOccupanteAbbonamentoLoggerEnum {

	INIZIALE("Operazioni modifica occupante su autorizzazione collegata a borsellino\nid autorizzazione: " + CONST_IDAUTORIZZAZIONE +
		 "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE + "\n" + DELIMITATORE_LOG),
	AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO("Autorizzazione: " + CONST_IDAUTORIZZAZIONE + "\nESTREMI AUT: " + CONST_ESTREMIAUTORIZZAZIONE +
					      " collegata a nuovo borsellino per modifica occupante\n" + DELIMITATORE_LOG);

	private final String messaggio;

	public String getMessaggio() {

	    return messaggio;
	}

	ModificaOccupanteAbbonamentoLoggerEnum(String messaggio) {

	    this.messaggio = messaggio;
	}
    }
}