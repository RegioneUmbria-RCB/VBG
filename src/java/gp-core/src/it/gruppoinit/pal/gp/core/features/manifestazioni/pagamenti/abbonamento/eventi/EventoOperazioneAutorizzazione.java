package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.TipoOperazioneBorsAutEnum;

public class EventoOperazioneAutorizzazione implements IEvent{
    
    private Integer idborsellino;
    private String uuidBorsellino;
    private Integer idautorizzazione;
    private TipoOperazioneBorsAutEnum tipoOperazione;
    
    public EventoOperazioneAutorizzazione(Integer idborsellino, String uuidBorsellino, Integer idautorizzazione, TipoOperazioneBorsAutEnum tipoOperazione) {
	this.idborsellino = idborsellino;
	this.uuidBorsellino = uuidBorsellino;
	this.idautorizzazione = idautorizzazione;
	this.tipoOperazione = tipoOperazione;
    }

    
    public Integer getIdborsellino() {
    
        return idborsellino;
    }

    
    public void setIdborsellino(Integer idborsellino) {
    
        this.idborsellino = idborsellino;
    }        
    
    public String getUuidBorsellino() {
    
        return uuidBorsellino;
    }

    
    public void setUuidBorsellino(String uuidBorsellino) {
    
        this.uuidBorsellino = uuidBorsellino;
    }


    public Integer getIdautorizzazione() {
    
        return idautorizzazione;
    }

    
    public void setIdautorizzazione(Integer idautorizzazione) {
    
        this.idautorizzazione = idautorizzazione;
    }

    
    public TipoOperazioneBorsAutEnum getTipoOperazione() {
    
        return tipoOperazione;
    }

    
    public void setTipoOperazione(TipoOperazioneBorsAutEnum tipoOperazione) {
    
        this.tipoOperazione = tipoOperazione;
    }                   
 
    
}
