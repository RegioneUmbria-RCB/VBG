package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Forum;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface ForumDAO extends BaseDAO<Forum, PkId> {

    /**
     * Restituisce la lista di forum filtrata per Idcomune e ordinata per descrizione
     * 
     */
    public List<Forum> findAll(Integer firstResult, Integer maxResult);
}
