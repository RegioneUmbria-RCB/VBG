package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Manifestazioni;

import java.util.List;

public interface ManifestazioniDAO extends BaseDAO<Manifestazioni, Integer> {

    /**
     * Restituisce tutte le manifestazioni
     */
    public List<Manifestazioni> findAll(Integer firstResult, Integer maxResult);
}
