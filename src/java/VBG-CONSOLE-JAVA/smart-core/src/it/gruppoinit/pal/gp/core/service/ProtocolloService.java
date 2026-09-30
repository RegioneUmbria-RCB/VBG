package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Protocollo;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface ProtocolloService extends BaseService<Protocollo, PkId> {

    /**
     * @see ProtocolloDAO#findAll(Integer, Integer)
     */
    public List<Protocollo> findAll(Integer firstResult, Integer maxResult);
}
