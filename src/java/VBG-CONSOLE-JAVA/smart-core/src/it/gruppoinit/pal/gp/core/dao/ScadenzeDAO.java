package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ScadenzeDAO extends BaseDAO<Scadenze, PkId> {

    /**
     * Lista di scadenze filtrate per idcomune e ordinate in modo decrescente per "datascadenza"
     * 
     */
    public List<Scadenze> findAll(Integer firstResult, Integer maxResult);
}
