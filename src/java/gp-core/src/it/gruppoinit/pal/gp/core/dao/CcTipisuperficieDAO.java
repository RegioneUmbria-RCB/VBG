package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcTipisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTipisuperficieDAO extends BaseDAO<CcTipisuperficie, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcTipisuperficie> findAll(Integer firstResult, Integer maxResult);
}
