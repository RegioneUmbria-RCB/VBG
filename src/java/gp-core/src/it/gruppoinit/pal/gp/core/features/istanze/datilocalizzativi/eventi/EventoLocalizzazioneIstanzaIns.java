package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoLocalizzazioneIstanzaIns implements IEvent {
    
    private String uuidistanza;

    
   public EventoLocalizzazioneIstanzaIns(String uuidistanza) {
	this.uuidistanza = uuidistanza; //Qui ci passo l'uuidistanza dell'istanza, non istanzestradario, perché ce l'ho
   }

    public String getUuidistanza() {
    
        return uuidistanza;
    }

    public void setUuidistanza(String uuidistanza) {
    
        this.uuidistanza = uuidistanza;
    }
    
 }
