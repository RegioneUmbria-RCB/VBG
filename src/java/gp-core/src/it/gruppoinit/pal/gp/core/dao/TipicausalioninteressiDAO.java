package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;

/**
 * 
 * @author
 */
public interface TipicausalioninteressiDAO extends BaseDAO<Tipicausalioninteressi, PkId> {

    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult);

    public List<Tipicausalioninteressi> findByIdEGGpassati(Integer id, Integer ggPassati);
}
