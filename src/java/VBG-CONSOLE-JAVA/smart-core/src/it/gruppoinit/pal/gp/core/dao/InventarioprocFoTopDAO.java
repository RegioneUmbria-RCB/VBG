package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author 
 */
public interface InventarioprocFoTopDAO extends BaseDAO<InventarioprocFoTop, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<InventarioprocFoTop> findAll(Integer firstResult, Integer maxResult);
}
