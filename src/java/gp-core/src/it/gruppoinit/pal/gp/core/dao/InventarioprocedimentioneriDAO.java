package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface InventarioprocedimentioneriDAO extends BaseDAO<Inventarioprocedimentioneri, PkId> {

    /**
     * List di oneri degli inventario procedimenti (endo procediemnti) filtrati per software e id comune
     * 
     */
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult);
}
