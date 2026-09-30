package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AlberoprocArendoDAO extends BaseDAO<AlberoprocArendo, PkId> {

    /**
     * Restituisce la lista di tutti gli AlberoprocArendo filtrati per idcomune
     * 
     */
    public List<AlberoprocArendo> findAll(Integer firstResult, Integer maxResult);
}
