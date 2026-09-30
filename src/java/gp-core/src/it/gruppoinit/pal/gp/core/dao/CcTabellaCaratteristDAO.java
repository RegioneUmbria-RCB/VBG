package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcTabellaCaratterist;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTabellaCaratteristDAO extends BaseDAO<CcTabellaCaratterist, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcTabellaCaratterist> findAll(Integer firstResult, Integer maxResult);
}
