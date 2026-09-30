package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RiFormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiFormegiuridicheService extends BaseService<RiFormegiuridiche, String> {

    /**
     * @see RiFormegiuridicheDAO#findAll(Integer, Integer)
     */
    public List<RiFormegiuridiche> findAll(Integer firstResult, Integer maxResult);

    public List<RiFormegiuridiche> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults);
}
