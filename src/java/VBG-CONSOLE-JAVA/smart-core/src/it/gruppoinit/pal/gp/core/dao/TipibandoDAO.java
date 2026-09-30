package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipibandoDAO extends BaseDAO<Tipibando, PkId> {

    /**
     * @return La lista dei tipibandi che sono attivi
     */
    public List<Tipibando> findActiveTipibando();
}
