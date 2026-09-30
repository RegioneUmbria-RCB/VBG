package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public enum StatoPosizioneDebitoriaEnum {

    DA_PAGARE("Da pagare"), COMPILATO("Compilato"), IN_ATTESA("In attesa"), FALLITO("Fallito"), SUCCESSO("Successo"), ANNULLATO(
	    "Annullato"), TRANSAZIONE_INIZIALIZZATA("Transazione inizializzata"), TRANSAZIONE_AVVIATA("Transazione avviata"), TRANSAZIONE_ERRORE(
		    "Transazione errore"), INVALIDATO_DALL_ENTE("Invalidato dall'ente"), PAGAMENTO_REVOCATO("Pagamento revocato");

    private String descrizioneStato;
    private static Map<String, StatoPosizioneDebitoriaEnum> map = new HashMap<>();

    private StatoPosizioneDebitoriaEnum(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
    }

    static {
	for (StatoPosizioneDebitoriaEnum stato : StatoPosizioneDebitoriaEnum.values()) {
	    map.put(stato.descrizioneStato, stato);
	}
    }

    public String value() {

	return name();
    }

    public static StatoPosizioneDebitoriaEnum fromDescrizione(String stato) {

	return map.get(stato);
    }

    public StatoPagamentoType toStatoPagamentoType() {

	switch (this) {
	case DA_PAGARE:
	case COMPILATO:
	case IN_ATTESA:
	case TRANSAZIONE_AVVIATA:
	case TRANSAZIONE_INIZIALIZZATA:
	case FALLITO:
	case ANNULLATO:
	case TRANSAZIONE_ERRORE:
	    return StatoPagamentoType.ATTIVATO_IN_PSP;
	case SUCCESSO:
	    return StatoPagamentoType.NOTIFICATO_DA_PSP;
	case INVALIDATO_DALL_ENTE:
	case PAGAMENTO_REVOCATO:
	    return StatoPagamentoType.ANNULLATO;
	}
	throw new IllegalArgumentException("Stato Pagamento non valido " + this);
    }
}
