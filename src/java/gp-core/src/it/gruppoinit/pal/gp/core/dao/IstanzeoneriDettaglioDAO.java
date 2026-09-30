package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IstanzeoneriDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzeoneriDettaglioDAO extends BaseDAO<IstanzeoneriDettaglio, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeoneriDettaglio> findAll(Integer firstResult, Integer maxResult);
}
