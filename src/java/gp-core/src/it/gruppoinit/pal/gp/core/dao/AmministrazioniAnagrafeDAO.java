package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AmministrazioniAnagrafeDAO extends BaseDAO<AmministrazioniAnagrafe, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AmministrazioniAnagrafe> findAll(Integer firstResult, Integer maxResult);
}
