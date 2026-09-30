package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AnagrafedocumentiDurcDAO extends BaseDAO<AnagrafedocumentiDurc, PkId> {

    /**
     * 
     */
    public List<AnagrafedocumentiDurc> findAll(Integer firstResult, Integer maxResult);
}
