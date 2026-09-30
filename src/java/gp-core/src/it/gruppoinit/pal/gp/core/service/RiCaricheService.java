package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RiCaricheDAO;
import it.gruppoinit.pal.gp.core.domain.RiCariche;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiCaricheService extends BaseService<RiCariche, String> {

    /**
     * @see RiCaricheDAO#findAll(Integer, Integer)
     */
    public List<RiCariche> findAll(Integer firstResult, Integer maxResult);

    public List<RiCariche> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults);
}
