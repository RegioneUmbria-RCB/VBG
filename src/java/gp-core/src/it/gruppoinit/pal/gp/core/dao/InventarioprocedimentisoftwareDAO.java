package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentisoftwareDAO extends BaseDAO<Inventarioprocedimentisoftware, PkId> {

    /**
     * Ritorna lista di moduli attivi filtrati per idcomune per gli endo procedimenti configurati
     * 
     */
    public List<Inventarioprocedimentisoftware> findAll(Integer firstResult, Integer maxResult);
}
