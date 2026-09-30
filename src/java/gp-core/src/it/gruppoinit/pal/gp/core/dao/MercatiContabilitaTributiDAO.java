package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiContabilitaTributiDAO extends BaseDAO<MercatiContabilitaTributi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiContabilitaTributi> findAll(Integer firstResult, Integer maxResult);
}
