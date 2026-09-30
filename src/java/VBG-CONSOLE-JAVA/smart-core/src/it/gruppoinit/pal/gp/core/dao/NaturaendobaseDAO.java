package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Naturaendobase;

import java.util.List;

public interface NaturaendobaseDAO extends BaseDAO<Naturaendobase, Integer> {

    /**
     * Restituisce la lista di natura endo (filtrati per idcomune e software) e ordinati per il campo stringa natura
     * 
     * 
     */
    public List<Naturaendobase> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce la lista di natura endo (filtrati ilike anywhere nel campo natura) e orinati per il campo stringa
     * natura
     * 
     * @param descrizione
     * @return
     */
    public List<Naturaendobase> findBydescrizione(String descrizione);

    /**
     * Ritorna il codice natura endo maggiore
     * 
     * @return
     */
    public Integer findMaxCodicenatura();
}
