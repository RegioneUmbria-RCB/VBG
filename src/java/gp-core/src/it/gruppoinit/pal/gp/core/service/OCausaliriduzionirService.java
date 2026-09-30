package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OCausaliriduzionirDAO;
import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OCausaliriduzionirService extends BaseService<OCausaliriduzionir, PkId> {

    /**
     * @see OCausaliriduzionirDAO#findAll(Integer, Integer)
     */
    public List<OCausaliriduzionir> findAll(Integer firstResult, Integer maxResult);
}
