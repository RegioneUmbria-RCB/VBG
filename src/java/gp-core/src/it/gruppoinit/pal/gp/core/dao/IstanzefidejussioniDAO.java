package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.domain.IstanzefidejussioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzefidejussioniDAO extends BaseDAO<Istanzefidejussioni, IstanzefidejussioniId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzefidejussioni> findAll(Integer firstResult, Integer maxResult);
}
