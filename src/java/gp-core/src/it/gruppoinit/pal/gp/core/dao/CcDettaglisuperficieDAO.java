package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDettaglisuperficieDAO extends BaseDAO<CcDettaglisuperficie, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcDettaglisuperficie> findAll(Integer firstResult, Integer maxResult);
}
