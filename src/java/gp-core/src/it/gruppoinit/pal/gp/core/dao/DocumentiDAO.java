package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface DocumentiDAO extends BaseDAO<Documenti, PkId> {

    /**
     * Lista di documenti filtrati per idcomune e ordinati per il campo documento
     * 
     */
    public List<Documenti> findAll(Integer firstResult, Integer maxResult);
}
