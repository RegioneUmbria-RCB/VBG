package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloModalitainvioDAO extends BaseDAO<ProtocolloModalitainvio, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ProtocolloModalitainvio> findAll(Integer firstResult, Integer maxResult);
}
