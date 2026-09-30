package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Versione;

public interface VersioneDAO extends BaseDAO<Versione, String> {

    /**
     * recupera tutti i record ordinati per campo versione DESC
     * (nella tabella è presente sempre un solo record)
     */
    public List<Versione> findAll(Integer firstResult, Integer maxResult);
}
