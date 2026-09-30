package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Lotti;
import it.gruppoinit.pal.gp.core.domain.LottiId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LottiService extends BaseService<Lotti, LottiId> {

    /**
     * 
     * @see LottiDAO#findByAree(Aree);
     */
    public List<Lotti> findByAree(Aree aree);
}
