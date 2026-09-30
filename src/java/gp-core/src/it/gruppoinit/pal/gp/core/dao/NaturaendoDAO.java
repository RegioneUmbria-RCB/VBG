package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;

import java.util.List;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
public interface NaturaendoDAO extends BaseDAO<Naturaendo, NaturaendoId> {

    /**
     * Restituisce la lista di natura endo (filtrati per idcomune e software) e ordinati per il campo stringa natura
     * 
     * 
     */
    public List<Naturaendo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce la lista di natura endo (filtrati ilike anywhere nel campo natura) e orinati per il campo stringa
     * natura
     * 
     * @param descrizione
     * @return
     */
    public List<Naturaendo> findBydescrizione(String descrizione);

    /**
     * Ritorna il codice natura endo maggiore
     * 
     * @return
     */
    public Integer findMaxCodicenatura();
}
