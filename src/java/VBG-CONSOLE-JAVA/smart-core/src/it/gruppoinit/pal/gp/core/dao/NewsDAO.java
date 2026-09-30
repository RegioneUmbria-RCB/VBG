package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface NewsDAO extends BaseDAO<News, PkId> {

    /**
     * 
     * @param softwareList
     * @return Una lista di News filtrata tramite la lista di Software abilitati per quel Responsabile
     */
    public List<News> findByFilter(Set<Software> softwareList);
}
