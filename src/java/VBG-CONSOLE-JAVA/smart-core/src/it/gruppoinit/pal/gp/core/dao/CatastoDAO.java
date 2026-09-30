package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Catasto;

import java.util.List;

/**
 * 
 * @author
 */
public interface CatastoDAO extends BaseDAO<Catasto, String> {

    /**
     * Torna tutti i record della tabella
     * 
     */
    public List<Catasto> findAll(Integer firstResult, Integer maxResult);
}
