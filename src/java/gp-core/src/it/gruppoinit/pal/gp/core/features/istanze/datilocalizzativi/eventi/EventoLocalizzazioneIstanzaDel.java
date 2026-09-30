package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoLocalizzazioneIstanzaDel implements IEvent {
    
    private String uuidistanza;
    private boolean cancellazioneIstanzaInCorso;

    public EventoLocalizzazioneIstanzaDel(String uuidistanza, boolean cancellazioneIstanzaInCorso) {

	super();
	this.uuidistanza = uuidistanza;
	this.cancellazioneIstanzaInCorso = cancellazioneIstanzaInCorso;
    }

    public String getUuidistanza() {
    
        return uuidistanza;
    }

    public void setUuidistanza(String uuidistanza) {
    
        this.uuidistanza = uuidistanza;
    }


    public boolean isCancellazioneIstanzaInCorso() {
    
        return cancellazioneIstanzaInCorso;
    }

    public void setCancellazioneIstanzaInCorso(boolean cancellazioneIstanzaInCorso) {
    
        this.cancellazioneIstanzaInCorso = cancellazioneIstanzaInCorso;
    }
    
}
