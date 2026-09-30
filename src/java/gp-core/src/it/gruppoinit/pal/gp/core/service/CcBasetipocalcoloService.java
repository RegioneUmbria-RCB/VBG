package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcBasetipocalcoloDAO;
import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcBasetipocalcoloService extends BaseService<CcBasetipocalcolo, String> {

    /**
     * @see CcBasetipocalcoloDAO#findAll(Integer, Integer)
     */
    public List<CcBasetipocalcolo> findAll(Integer firstResult, Integer maxResult);
}
