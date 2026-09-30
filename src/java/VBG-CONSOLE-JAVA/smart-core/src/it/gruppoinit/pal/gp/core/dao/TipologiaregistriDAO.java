package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;

import java.util.List;

public interface TipologiaregistriDAO extends BaseDAO<Tipologiaregistri, PkId> {

    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity);

    /**
     * Restituisce le Tipologie di registri (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult);
}
