package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Azioni;

import java.util.List;

public interface AzioniDAO extends BaseDAO<Azioni, Integer> {

    /**
     * Azioni no ha nè IDCOMUNE nè SOFTWARE. FindALL senza filtri, ordinato per azDescrizione (ASC).
     */
    public List<Azioni> findAll(Integer firstResult, Integer maxResult);
}
