package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface NewsService extends BaseService<News, PkId> {

    /**
     * @see NewsDAO#findByFilter(Set<Software> softwareList)
     */
    public List<News> findByFilter(Set<Software> softwareList);

    /**
     * Torna le ultime n (maxResult) notizie
     * 
     * @param maxResult
     * @return
     */
    public List<News> findLatest(Integer maxResult);

    /**
     * Torna le ultime n (maxResult) notizie in primo piano
     * 
     * @param maxResult
     * @return
     */
    public List<News> findLatestPrimoPiano(Integer maxResult);
}
