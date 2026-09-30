package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface DocumentiService extends BaseService<Documenti, PkId> {

    /**
     * @see DocumentiDAO#findAll(Integer, Integer)
     */
    public List<Documenti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Documenti di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Documenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
