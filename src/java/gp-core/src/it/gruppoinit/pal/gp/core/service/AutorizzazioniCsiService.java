package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniCsiDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AutorizzazioniCsiService extends BaseService<AutorizzazioniCsi, PkId> {

    /**
     * @see AutorizzazioniCsiDAO#findAll(Integer, Integer)
     */
    public List<AutorizzazioniCsi> findAll(Integer firstResult, Integer maxResult);

    /**
     * S
     * 
     * @param idAutorizzazione
     * @return
     */
    public AutorizzazioniCsi findByAutorizzazione(Integer idAutorizzazione);

    public boolean existsRecordsPerEnte();
}
