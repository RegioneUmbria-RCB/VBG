package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RiTipiprocedimentoDAO;
import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;

import java.util.List;

/**
 * 
 * @author
 */
public interface RiTipiprocedimentoService extends BaseService<RiTipiprocedimento, String> {

    /**
     * @see RiTipiprocedimentoDAO#findAll(Integer, Integer)
     */
    public List<RiTipiprocedimento> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista filtrata per la stringa textToSearch secondo la condizione "startWith" ordinata per il campo
     * "Descrizione" Asc
     * 
     * @param textToSearch
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<RiTipiprocedimento> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);
}
