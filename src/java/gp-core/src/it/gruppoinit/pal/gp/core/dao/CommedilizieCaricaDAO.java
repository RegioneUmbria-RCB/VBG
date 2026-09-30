package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieCaricaDAO extends BaseDAO<CommedilizieCarica, PkId> {

    /**
     * Torna la lista dei dati ordinati per ordinamento dal più piccolo al più grande
     * 
     */
    public List<CommedilizieCarica> findAll(Integer firstResult, Integer maxResult);
}
