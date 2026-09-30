package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiElabpresenzeDAO extends BaseDAO<MercatiElabpresenze, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiElabpresenze> findAll(Integer firstResult, Integer maxResult);
}
