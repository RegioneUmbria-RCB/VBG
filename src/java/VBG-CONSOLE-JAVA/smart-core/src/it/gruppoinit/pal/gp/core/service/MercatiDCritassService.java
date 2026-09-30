package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiDCritassDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatiDCritassService extends BaseService<MercatiDCritass, PkId> {

    /**
     * @see MercatiDCritassDAO#findAll(Integer, Integer)
     */
    public List<MercatiDCritass> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiDCritass> findByPosteggio(Integer codicePosteggio);
}
