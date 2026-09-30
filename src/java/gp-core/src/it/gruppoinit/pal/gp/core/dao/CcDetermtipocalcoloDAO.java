package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcDetermtipocalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDetermtipocalcoloDAO extends BaseDAO<CcDetermtipocalcolo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcDetermtipocalcolo> findAll(Integer firstResult, Integer maxResult);
}
