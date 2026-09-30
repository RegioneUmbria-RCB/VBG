package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CdsDAO extends BaseDAO<Cds, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Cds> findAll(Integer firstResult, Integer maxResult);
}
