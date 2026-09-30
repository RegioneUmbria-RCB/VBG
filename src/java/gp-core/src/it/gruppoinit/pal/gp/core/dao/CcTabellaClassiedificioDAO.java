package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTabellaClassiedificioDAO extends BaseDAO<CcTabellaClassiedificio, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcTabellaClassiedificio> findAll(Integer firstResult, Integer maxResult);
    
    /**
     * restituisce la lista delle classi edifici ordinata in base ai campi DA e A crescenti
     * @return
     */
    public List<CcTabellaClassiedificio> listByIntervallo();
}
