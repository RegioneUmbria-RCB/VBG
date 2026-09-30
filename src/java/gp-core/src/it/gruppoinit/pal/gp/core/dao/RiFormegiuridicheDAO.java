package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiFormegiuridicheDAO extends BaseDAO<RiFormegiuridiche, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<RiFormegiuridiche> findAll(Integer firstResult, Integer maxResult);
}
