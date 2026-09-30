package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoLocalizzazioneIstanzaUpd implements IEvent {
    
    private String uuidistanza;

    
   public EventoLocalizzazioneIstanzaUpd(String uuidistanza) {
	this.uuidistanza = uuidistanza;
    }


    public String getUuidistanza() {
    
        return uuidistanza;
    }

    public void setUuidistanza(String uuidistanza) {
    
        this.uuidistanza = uuidistanza;
    }
    
 }
