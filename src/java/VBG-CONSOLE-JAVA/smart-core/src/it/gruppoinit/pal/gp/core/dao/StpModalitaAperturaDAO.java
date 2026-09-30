package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.StpModalitaApertura;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface StpModalitaAperturaDAO extends BaseDAO<StpModalitaApertura, String> {

    public List<StpModalitaApertura> findAll(Integer firstResult, Integer maxResult);
}
