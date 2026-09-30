package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiFormuleCalcoloDAO extends BaseDAO<MercatiFormuleCalcolo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiFormuleCalcolo> findAll(Integer firstResult, Integer maxResult);
}
