package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OCausaliriduzionitDAO extends BaseDAO<OCausaliriduzionit, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OCausaliriduzionit> findAll(Integer firstResult, Integer maxResult);
}
