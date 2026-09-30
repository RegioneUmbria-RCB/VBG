package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface TipiaperturaDAO extends BaseDAO<Tipiapertura, PkId> {

    /**
     * Restituisce le tipologie di apertura (filtrando per idcomune e software) ordinandole per il campo descrizione asc
     * 
     */
    public List<Tipiapertura> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param tipiapertura
     * @return ricerca i tipiapertura filtrando per descrizione (Idcomune e software)
     */
    public List<Tipiapertura> findByDescrizione(Tipiapertura tipiapertura);
}
