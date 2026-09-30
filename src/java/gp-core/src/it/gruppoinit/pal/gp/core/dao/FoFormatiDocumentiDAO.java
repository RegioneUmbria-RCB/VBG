package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoFormatiDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoFormatiDocumentiDAO extends BaseDAO<FoFormatiDocumenti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<FoFormatiDocumenti> findAll(Integer firstResult, Integer maxResult);
}
