package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ForumDAO;
import it.gruppoinit.pal.gp.core.domain.Forum;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface ForumService extends BaseService<Forum, PkId> {

    /**
     * @see ForumDAO#findAll(Integer, Integer)
     */
    public List<Forum> findAll(Integer firstResult, Integer maxResult);
}
