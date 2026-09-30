package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcBasetipocalcoloDAO extends BaseDAO<CcBasetipocalcolo, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcBasetipocalcolo> findAll(Integer firstResult, Integer maxResult);
}
