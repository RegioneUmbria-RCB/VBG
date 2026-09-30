package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;

public interface MovimentiManager {

    public MovimentiService getMovimentiService();

    /**
     * Esegue l'inserimento del movimento e se il parametro eseguiOperazioniProtocollo è settato a true allora effettua
     * le operazioni di protocollazione e fascicolazione
     * 
     * @param entity
     * @param eseguiOperazioniProtocollo
     */
    public void insert(Movimenti entity, boolean eseguiOperazioniProtocollo);

    /**
     * Viene aggiornato il movimento e vengono controllati i parametri della protocollazione. In particolare se sono
     * presenti i parametri di num e data protocollo e non è presente fkidprotocollo viene fatta una lettura per
     * ricavarlo e viene associato al movimento
     * 
     * @param entity
     */
    public void update(Movimenti entity);
}
