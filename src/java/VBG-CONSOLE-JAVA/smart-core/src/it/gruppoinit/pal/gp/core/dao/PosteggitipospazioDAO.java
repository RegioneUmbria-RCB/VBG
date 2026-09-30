package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;

import java.util.List;

public interface PosteggitipospazioDAO extends BaseDAO<Posteggitipospazio, PkId> {

    /**
     * Restituisce una lista di tipologie spazio posteggio filtrando per il software attivo ed ordinando la lista per la
     * descrizione
     */
    public List<Posteggitipospazio> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param posteggitipospazio
     * @return una lista di tipi spazio
     */
    public List<Posteggitipospazio> findByTipoSpazio(Posteggitipospazio posteggitipospazio);
}
