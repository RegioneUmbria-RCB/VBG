package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface InventarioprocSoggFirmatariDAO extends BaseDAO<InventarioprocSoggFirmatari, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<InventarioprocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);
}
