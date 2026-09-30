package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RiTipiinterventoDAO;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiTipiinterventoService extends BaseService<RiTipiintervento, String> {

    /**
     * @see RiTipiinterventoDAO#findAll(Integer, Integer)
     */
    public List<RiTipiintervento> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista filtrata per la stringa textToSearch secondo la condizione "startWith" ordinata per il campo
     * "Descrizione" Asc
     * 
     * @param textToSearch
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<RiTipiintervento> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);
}
