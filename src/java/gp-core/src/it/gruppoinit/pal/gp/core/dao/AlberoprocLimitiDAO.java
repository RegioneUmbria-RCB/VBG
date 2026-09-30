package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocLimiti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface AlberoprocLimitiDAO extends BaseDAO<AlberoprocLimiti, PkId> {

    /**
     * Ritorna la lista filtrata per idcomune e software. la lista è ordinata(ASC) per la proprietà nrmaxistanze
     * 
     */
    public List<AlberoprocLimiti> findAll(Integer firstResult, Integer maxResult);
}
