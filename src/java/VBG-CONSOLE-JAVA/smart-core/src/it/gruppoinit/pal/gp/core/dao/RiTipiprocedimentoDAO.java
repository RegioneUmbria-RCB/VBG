package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiTipiprocedimentoDAO extends BaseDAO<RiTipiprocedimento, String> {

    public List<RiTipiprocedimento> findAll(Integer firstResult, Integer maxResult);
}
