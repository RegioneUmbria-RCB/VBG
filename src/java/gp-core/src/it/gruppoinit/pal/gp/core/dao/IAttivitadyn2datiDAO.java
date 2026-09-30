package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitadyn2datiDAO extends BaseDAO<IAttivitadyn2dati, IAttivitadyn2datiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IAttivitadyn2dati> findAll(Integer firstResult, Integer maxResult);
}
