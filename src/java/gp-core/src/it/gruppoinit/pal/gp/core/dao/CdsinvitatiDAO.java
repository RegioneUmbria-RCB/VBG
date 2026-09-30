package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CdsinvitatiDAO extends BaseDAO<Cdsinvitati, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Cdsinvitati> findAll(Integer firstResult, Integer maxResult);
}
