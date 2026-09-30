package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiareeDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;

import java.util.List;

public interface TipiareeService extends BaseService<Tipiaree, PkId> {

    public List<Tipiaree> findByFilter(Tipiaree entity);

    /**
     * @see TipiareeDAO#findAll(Integer, Integer)
     */
    public List<Tipiaree> findAll(Integer firstResult, Integer maxResult);
}
