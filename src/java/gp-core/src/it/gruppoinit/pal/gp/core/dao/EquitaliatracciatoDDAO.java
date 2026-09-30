package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.EquitaliatracciatoD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author 
 */
public interface EquitaliatracciatoDDAO extends BaseDAO<EquitaliatracciatoD, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<EquitaliatracciatoD> findAll(Integer firstResult, Integer maxResult);
}
