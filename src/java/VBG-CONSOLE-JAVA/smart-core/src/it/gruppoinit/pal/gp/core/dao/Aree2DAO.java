package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface Aree2DAO extends BaseDAO<Aree2, PkId> {

    /**
     * Torna la lista delle aree2 per idcomune e software correnti ordinate per denominazione dalla A alla Z
     * 
     */
    public List<Aree2> findAll(Integer firstResult, Integer maxResult);
}
