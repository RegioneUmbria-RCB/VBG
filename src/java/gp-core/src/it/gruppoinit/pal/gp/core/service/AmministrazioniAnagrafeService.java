package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniAnagrafeDAO;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AmministrazioniAnagrafeService extends BaseService<AmministrazioniAnagrafe, PkId> {

    /**
     * @see AmministrazioniAnagrafeDAO#findAll(Integer, Integer)
     */
    public List<AmministrazioniAnagrafe> findAll(Integer firstResult, Integer maxResult);

    public List<AmministrazioniAnagrafe> findAmministrazione(Integer codiceamministrazione);

    public List<AmministrazioniAnagrafe> findByAnagrafe(Integer codiceanagrafe);

    public AmministrazioniAnagrafe findByAnagrafeAndAmministrazione(Integer codiceamministrazione, Integer codiceanagrafe);
}
