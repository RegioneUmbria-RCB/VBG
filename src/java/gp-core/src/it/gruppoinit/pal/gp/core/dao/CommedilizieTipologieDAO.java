package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipologieDAO extends BaseDAO<CommedilizieTipologie, PkId> {

    /**
     * torna la lista delle tipologie ordinate per descrizione dalla A alla Z
     * 
     */
    public List<CommedilizieTipologie> findAll(Integer firstResult, Integer maxResult);
}
