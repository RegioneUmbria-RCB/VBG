package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author 
 */
public interface BattitoriCsiDAO extends BaseDAO<BattitoriCsi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BattitoriCsi> findAll(Integer firstResult, Integer maxResult);
}
