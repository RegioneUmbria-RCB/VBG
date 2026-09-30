package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface AlberoprocD2modtattDAO extends BaseDAO<AlberoprocD2modtatt, AlberoprocD2modtattId> {

    
    public List<AlberoprocD2modtatt> findAll(Integer firstResult, Integer maxResult);
}
