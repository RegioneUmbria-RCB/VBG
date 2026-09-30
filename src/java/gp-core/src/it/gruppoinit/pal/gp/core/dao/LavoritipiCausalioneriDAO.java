package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoritipiCausalioneriDAO extends BaseDAO<LavoritipiCausalioneri, PkId> {

    /**
     * torna una lista di tipologie di lavoro di un modulo software ordinate per la proprietà lavoro dalla A alla Z
     * 
     */
    public List<LavoritipiCausalioneri> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * torna una lista di tipologie di lavoro di un modulo software e di una tipologia di lavoro specificata
     * 
     * @param lavoritipi
     * @return
     * @throws IllegalArgumentException
     *             se categoria è nullo o con codice vuoto
     */
    public List<LavoritipiCausalioneri> findByLavoritipi(Lavoritipi lavoritipi);
}
