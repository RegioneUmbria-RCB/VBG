package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcClassisuperficiDAO extends BaseDAO<CcClassisuperfici, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcClassisuperfici> findAll(Integer firstResult, Integer maxResult);
}
