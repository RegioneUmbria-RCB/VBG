package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.domain.OValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OValiditacoefficientiService extends BaseService<OValiditacoefficienti, PkId> {

    /**
     * @see OValiditacoefficientiDAO#findAll(Integer, Integer)
     */
    public List<OValiditacoefficienti> findAll(Integer firstResult, Integer maxResult);

    public boolean existRecordByCurrentSoftware();
}
