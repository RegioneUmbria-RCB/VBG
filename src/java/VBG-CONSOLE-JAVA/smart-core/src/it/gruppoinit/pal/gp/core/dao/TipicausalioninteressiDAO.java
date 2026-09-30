package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipicausalioninteressiDAO extends BaseDAO<Tipicausalioninteressi, PkId> {

    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult);
}
