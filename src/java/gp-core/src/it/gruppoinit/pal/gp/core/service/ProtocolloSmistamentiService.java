package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloSmistamentiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloSmistamentiService extends BaseService<ProtocolloSmistamenti, PkId> {

    /**
     * @see ProtocolloSmistamentiDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloSmistamenti> findAll(Integer firstResult, Integer maxResult);

    public List<ProtocolloSmistamenti> findByComuneAndSoftware(String codicecomune, String software);
}
