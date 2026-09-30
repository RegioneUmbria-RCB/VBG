package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VerticalizzazionibaseDAO extends BaseDAO<Verticalizzazionibase, String> {

    /**
     * Lista di verticalizzazioni base ordinate per modulo asc
     * 
     */
    public List<Verticalizzazionibase> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo che ritorna un booleano :
     * 
     * True: per la vericalizzazione base passata esiste almeno un record nella tabella verticalizzazioni attivo per il
     * software e comune considerati
     * 
     * @param verticalizzazionibase
     * @return
     */
    public boolean isConfigurataPerComuneAndSoftware(Verticalizzazionibase verticalizzazionibase);
}
