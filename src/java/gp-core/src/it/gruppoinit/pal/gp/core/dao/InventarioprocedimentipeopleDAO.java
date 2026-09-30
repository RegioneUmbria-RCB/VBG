package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentipeopleDAO extends BaseDAO<Inventarioprocedimentipeople, PkId> {

    /**
     * Lista di inventario procedimenti people filtrati per idcomune
     * 
     */
    public List<Inventarioprocedimentipeople> findAll(Integer firstResult, Integer maxResult);
}
