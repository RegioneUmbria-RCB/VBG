package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Onericomportamento;

import java.util.List;

/**
 * 
 * @author
 */
public interface OnericomportamentoDAO extends BaseDAO<Onericomportamento, Integer> {

    /**
     * Restituisce una lista di oneri comportamenti
     * 
     */
    public List<Onericomportamento> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna una lista di oneri comportamento criterio di ricerca<br/>
     * comportamento = ilike %descrizione% ordinati per coDescrizione dalla A alla Z<br/>
     * 
     */
    public List<Onericomportamento> findByDescrizione(String descrizione);
}
