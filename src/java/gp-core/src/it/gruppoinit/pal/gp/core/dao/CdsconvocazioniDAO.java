package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CdsconvocazioniDAO extends BaseDAO<Cdsconvocazioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Cdsconvocazioni> findAll(Integer firstResult, Integer maxResult);
}
