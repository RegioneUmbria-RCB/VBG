package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Titoli;

import java.util.List;

public interface TitoliDAO extends BaseDAO<Titoli, PkId> {

    /**
     * Restituisce le anagrafiche (filtrando per idcomune) ordinandole per il campo titolo
     */
    public List<Titoli> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di Titoli filtrati per il campo titolo tramite l "ilike"
     * 
     */
    public List<Titoli> findByDescrizione(String descrizione);
}
