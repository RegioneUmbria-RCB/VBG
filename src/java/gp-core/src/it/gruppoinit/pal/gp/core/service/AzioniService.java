package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AzioniDAO;
import it.gruppoinit.pal.gp.core.domain.Azioni;

import java.util.List;

public interface AzioniService extends BaseService<Azioni, Integer> {

    /**
     * @see AzioniDAO#findAll(Integer, Integer)
     */
    public List<Azioni> findAll(Integer firstResult, Integer maxResult);

    public Azioni findByAzione(String azione);
}
