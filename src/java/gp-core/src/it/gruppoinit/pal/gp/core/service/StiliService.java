package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.StiliDAO;
import it.gruppoinit.pal.gp.core.domain.Stili;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface StiliService extends BaseService<Stili, Integer> {

    /**
     * @see StiliDAO#findAll(Integer, Integer)
     */
    public List<Stili> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca lo stile filtrando per descrizione
     * 
     * @param valore
     * @return
     */
    public Stili findByNome(String nome);
}
