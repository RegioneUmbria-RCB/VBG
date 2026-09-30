package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Info;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface InfoDAO extends BaseDAO<Info, PkId> {

    /**
     * Restituisce la lista di informazioni del Comune filtrata per Idcomune e ordinata per ordine ASC
     * 
     */
    public List<Info> findAll(Integer firstResult, Integer maxResult);
}
