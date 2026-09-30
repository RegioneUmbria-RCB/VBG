package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface InventarioprocedimentioneriService extends BaseService<Inventarioprocedimentioneri, PkId> {

    public List<Inventarioprocedimentioneri> findByCodiceInventarioAndCausale(Integer codiceInventario, Integer codiceCausale);

    public List<Inventarioprocedimentioneri> findByCodiceInventario(Integer codiceInventario, boolean escludiCausaliDisabilitate);
}
