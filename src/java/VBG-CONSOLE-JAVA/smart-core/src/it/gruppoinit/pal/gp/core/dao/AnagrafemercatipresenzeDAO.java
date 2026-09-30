package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafemercatipresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface AnagrafemercatipresenzeDAO extends BaseDAO<Anagrafemercatipresenze, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Anagrafemercatipresenze> findAll(Integer firstResult, Integer maxResult);
}
