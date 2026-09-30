package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiTipiinterventoDAO extends BaseDAO<RiTipiintervento, String> {

    public List<RiTipiintervento> findAll(Integer firstResult, Integer maxResult);
}
