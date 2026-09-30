package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcDetermtipocalcoloDAO;
import it.gruppoinit.pal.gp.core.domain.CcDetermtipocalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDetermtipocalcoloService extends BaseService<CcDetermtipocalcolo, PkId> {

    /**
     * @see CcDetermtipocalcoloDAO#findAll(Integer, Integer)
     */
    public List<CcDetermtipocalcolo> findAll(Integer firstResult, Integer maxResult);
}
