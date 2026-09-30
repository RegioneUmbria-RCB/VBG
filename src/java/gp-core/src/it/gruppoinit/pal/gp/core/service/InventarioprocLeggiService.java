package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocLeggiDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocLeggiService extends BaseService<InventarioprocLeggi, PkId> {

    /**
     * @see InventarioprocLeggiDAO#findAll(Integer, Integer)
     */
    public List<InventarioprocLeggi> findAll(Integer firstResult, Integer maxResult);

    public List<InventarioprocLeggi> findByCodiceinventario(Integer codiceInventario);
}
