package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatiDCritassDAO extends BaseDAO<MercatiDCritass, PkId> {

    public List<MercatiDCritass> findAll(Integer firstResult, Integer maxResult);
}
