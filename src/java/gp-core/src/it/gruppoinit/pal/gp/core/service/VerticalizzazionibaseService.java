package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazionibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VerticalizzazionibaseService extends BaseService<Verticalizzazionibase, String> {

    /**
     * @see VerticalizzazionibaseDAO#findAll(Integer, Integer)
     */
    public List<Verticalizzazionibase> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see VerticalizzazionibaseDAO#isConfigurataPerComuneAndSoftware(Verticalizzazionibase verticalizzazionibase)
     */
    public Boolean isConfigurataPerComuneAndSoftware(Verticalizzazionibase verticalizzazionibase);

    /**
     * Ritorna tutte le verticalizzazioni base ordinate per modulo, per ogni verticalizzazione base controlla che è
     * configurata e setta il flag booleano "flagConfigurata". Lo setta true se: Esiste ed è collegato ad esso almeno
     * una verticalizzazione atttiva per il comune a cui ci stiamo riferendo
     * 
     * @return
     */
    public List<Verticalizzazionibase> findAllAndCheckVerticalizzazionibaseconfigurate();
}
