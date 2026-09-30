package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAutorizzazioneInserita implements IEvent {

    private Integer idAutorizzazione;
    private String codiceComuneRiferimento;
    private String codiceFirmatario;

    public EventoAutorizzazioneInserita(Integer idAutorizzazione, String codiceComuneRiferimento, String codiceFirmatario) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException("L'id dell'autorizzazione inserita non può essere nullo");
	}
	this.idAutorizzazione = idAutorizzazione;
	this.codiceComuneRiferimento = codiceComuneRiferimento;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public String getCodiceComuneRiferimento() {

	return codiceComuneRiferimento;
    }

    public String getCodiceFirmatario() {

	return codiceFirmatario;
    }
}
