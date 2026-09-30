package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OggettiStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface OggettiStoricoDAO extends BaseDAO<OggettiStorico, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OggettiStorico> findAll(Integer firstResult, Integer maxResult);
}
