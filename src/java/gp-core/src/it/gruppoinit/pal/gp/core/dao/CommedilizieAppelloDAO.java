package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieAppelloDAO extends BaseDAO<CommedilizieAppello, PkId> {

    /**
     * Lista di appelli filtrati per idcomune
     * 
     */
    public List<CommedilizieAppello> findAll(Integer firstResult, Integer maxResult);
}
