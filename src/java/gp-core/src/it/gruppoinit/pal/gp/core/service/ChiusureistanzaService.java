package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ChiusureistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface ChiusureistanzaService extends BaseService<Chiusureistanza, PkId> {

    /**
     * @see ChiusureistanzaDAO#findAll(Integer, Integer)
     */
    public List<Chiusureistanza> findAll(Integer firstResult, Integer maxResult);
}
