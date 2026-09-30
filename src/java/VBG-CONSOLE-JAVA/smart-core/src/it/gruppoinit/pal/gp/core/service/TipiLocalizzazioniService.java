package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiLocalizzazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipiLocalizzazioniService extends BaseService<TipiLocalizzazioni, PkId> {

    /**
     * @see TipiLocalizzazioniDAO#findAll(Integer, Integer)
     */
    public List<TipiLocalizzazioni> findAll(Integer firstResult, Integer maxResult);

    public List<TipiLocalizzazioni> findByDescrizione(String descrizione, Integer firstResult, Integer maxResults);
}
