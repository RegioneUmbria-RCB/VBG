package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface Cdsinvitati2DAO extends BaseDAO<Cdsinvitati2, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Cdsinvitati2> findAll(Integer firstResult, Integer maxResult);
}
