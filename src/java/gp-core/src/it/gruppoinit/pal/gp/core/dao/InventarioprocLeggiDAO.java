package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocLeggiDAO extends BaseDAO<InventarioprocLeggi, PkId> {

    public List<InventarioprocLeggi> findAll(Integer firstResult, Integer maxResult);
}
