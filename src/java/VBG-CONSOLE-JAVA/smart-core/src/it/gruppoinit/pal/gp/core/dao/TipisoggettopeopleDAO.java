package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;
import it.gruppoinit.pal.gp.core.domain.TipisoggettopeopleId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TipisoggettopeopleDAO extends BaseDAO<Tipisoggettopeople, TipisoggettopeopleId> {

    /**
     * Ritorna la lista di tipo soggetto people (filtrando per idcomune e software) e ordinate per tiporapprpeople
     * 
     */
    public List<Tipisoggettopeople> findAll(Integer firstResult, Integer maxResult);
}
