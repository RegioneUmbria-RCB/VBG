package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellitId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface TipiprocedureDyn2modellitDAO extends BaseDAO<TipiprocedureDyn2modellit, TipiprocedureDyn2modellitId> {

    /**
     * Restituisce la lista di tutte le TipiprocedureDyn2modellit filtrate per idcomune
     * 
     */
    public List<TipiprocedureDyn2modellit> findAll(Integer firstResult, Integer maxResult);
}
