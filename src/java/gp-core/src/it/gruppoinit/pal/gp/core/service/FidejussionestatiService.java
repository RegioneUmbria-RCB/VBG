package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FidejussionestatiDAO;
import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FidejussionestatiService extends BaseService<Fidejussionestati, PkId> {

    /**
     * @see FidejussionestatiDAO#findAll(Integer, Integer)
     */
    public List<Fidejussionestati> findAll(Integer firstResult, Integer maxResult);

    public boolean existRecordsByCurrentSoftware();
}
