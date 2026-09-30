package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloSmistamentiDAO extends BaseDAO<ProtocolloSmistamenti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ProtocolloSmistamenti> findAll(Integer firstResult, Integer maxResult);
}
