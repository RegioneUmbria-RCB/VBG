package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OCausaliriduzionirDAO extends BaseDAO<OCausaliriduzionir, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OCausaliriduzionir> findAll(Integer firstResult, Integer maxResult);
}
