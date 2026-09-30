package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OrariaperturaDAO;
import it.gruppoinit.pal.gp.core.domain.Orariapertura;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface OrariaperturaService extends BaseService<Orariapertura, PkId> {

    /**
     * @see OrariaperturaDAO#findAll(Integer, Integer)
     */
    public List<Orariapertura> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cancella tutti i record di Orariapertura di una testata
     * 
     * @param entity
     */
    public void deleteByOrariaperturatestata(Orariaperturatestata entity);
}
