package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcCausaliriduzionitDAO extends BaseDAO<CcCausaliriduzionit, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcCausaliriduzionit> findAll(Integer firstResult, Integer maxResult);
}
