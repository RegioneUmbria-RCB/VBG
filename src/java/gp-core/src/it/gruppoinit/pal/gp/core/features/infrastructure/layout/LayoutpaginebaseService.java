package it.gruppoinit.pal.gp.core.features.infrastructure.layout;

import it.gruppoinit.pal.gp.core.domain.Layoutpaginebase;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

/**
 * 
 * @author
 */
public interface LayoutpaginebaseService extends BaseService<Layoutpaginebase, String> {

    /**
     * @see LayoutpaginebaseDAO#findAll(Integer, Integer)
     */
    public List<Layoutpaginebase> findAll(Integer firstResult, Integer maxResult);
}
