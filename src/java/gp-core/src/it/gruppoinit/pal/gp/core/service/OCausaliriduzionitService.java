package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OCausaliriduzionitService extends BaseService<OCausaliriduzionit, PkId> {

    /**
     * @see OCausaliriduzionitDAO#findAll(Integer, Integer)
     */
    public List<OCausaliriduzionit> findAll(Integer firstResult, Integer maxResult);
}
