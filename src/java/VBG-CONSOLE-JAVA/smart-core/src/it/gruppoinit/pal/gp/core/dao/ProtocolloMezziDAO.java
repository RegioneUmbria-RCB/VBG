package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloMezziDAO extends BaseDAO<ProtocolloMezzi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ProtocolloMezzi> findAll(Integer firstResult, Integer maxResult);
}
