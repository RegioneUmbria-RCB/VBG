package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

public enum RabbitTopicEnum {

    BACKEND_COMUNICAZIONI_UTENTE_NUOVA("backend.comunicazioni-utente.nuova", "/rest/notifica/comunicazioni-utente/nuova", true, "backend.comunicazioni-utente.nuova"),
    BACKEND_PRATICHE_NUOVA("backend.pratiche.nuova", "/rest/notifica/pratiche/nuova", true, "backend.pratiche.nuova"),
    BACKEND_PRATICHE_ELIMINATA("backend.pratiche.eliminata", "/rest/notifica/pratiche/eliminata", false, "backend.pratiche.eliminata"),
    BACKEND_PRATICHE_CAMBIO_STATO("backend.pratiche.aggiornata", "/rest/notifica/pratiche/cambio-stato", true, "backend.pratiche.cambio-stato"),
    BACKEND_PRATICHE_DESTINATARI_AGGIORNATI("backend.pratiche.destinatari-aggiornati", "/rest/notifica/pratiche/destinatari-aggiornati", false, "backend.pratiche.destinatari-aggiornati"),
    BACKEND_SCADENZE_NUOVA("backend.scadenze.*", "/rest/notifica/scadenze/nuova", false, "backend.scadenze.nuova"),
    BACKEND_SCADENZE_AGGIORNATA("backend.scadenze.*", "/rest/notifica/scadenze/aggiornata", false, "backend.scadenze.aggiornata"),
    BACKEND_SCADENZE_ELIMINATA("backend.scadenze.*", "/rest/notifica/scadenze/eliminata", false, "backend.scadenze.eliminata"),
    BACKEND_SCADENZE_EVASA("backend.scadenze.*", "/rest/notifica/scadenze/evasa", false, "backend.scadenze.evasa")
    // AL MOMENTO QUESTO EVENTO VIENE RILANCIATO DA BACKEND-RABBIT-MQ A SEGUITO DI NOTIFICA NUOVA POSIZIONE DEBITORIA  
    // ,BACKEND_POSIZIONIDEBITORIE_DESTINATARI_PENDENZA_AGGIORNATI("backend.posizioni-debitorie.destinatari-pendenza-aggiornati", "", false, "backend.posizioni-debitorie.destinatari-pendenza-aggiornati")
    ;

    private String value;
    private String uri;
    private boolean visualizzaMailTipo;
    public String nomeTopicInMessaggi;

    private RabbitTopicEnum(String value, String uri, boolean visualizzaMailTipo, String nomeTopicInMessaggi) {

	this.value = value;
	this.uri = uri;
	this.visualizzaMailTipo = visualizzaMailTipo;
	this.nomeTopicInMessaggi = nomeTopicInMessaggi;
    }

    public String getValue() {

	return value;
    }

    public boolean isVisualizzaMailTipo() {

	return visualizzaMailTipo;
    }

    public void setValue(String value) {

	this.value = value;
    }

    public String getNomeTopicInMessaggi() {

	return nomeTopicInMessaggi;
    }

    public static List<String> findListaTopicForClient() {

	List<String> ret = new ArrayList<String>();
	ret.add(BACKEND_COMUNICAZIONI_UTENTE_NUOVA.getValue());
	ret.add(BACKEND_PRATICHE_NUOVA.getValue());
	ret.add(BACKEND_PRATICHE_ELIMINATA.getValue());
	ret.add(BACKEND_PRATICHE_CAMBIO_STATO.getValue());
	ret.add(BACKEND_PRATICHE_DESTINATARI_AGGIORNATI.getValue());
	ret.add(BACKEND_SCADENZE_NUOVA.getValue());
	return ret;
    }

    public boolean visualizzaMailTipo(String valore) {

	for (RabbitTopicEnum b : RabbitTopicEnum.values()) {
	    if (b.value.equalsIgnoreCase(valore)) {
		return b.visualizzaMailTipo;
	    }
	}
	throw new IllegalArgumentException("Valore [" + valore + "] non riconosciuto per l'enumerazione RabbitTopicEnum");
    }

    public String getUri() {

	return uri;
    }

    public static RabbitTopicEnum fromName(String v) {

	for (RabbitTopicEnum b : RabbitTopicEnum.values()) {
	    if (b.value.equalsIgnoreCase(v)) {
		return b;
	    }
	}
	return null;
    }

    @Override
    public String toString() {

	return this.getValue();
    }
}
