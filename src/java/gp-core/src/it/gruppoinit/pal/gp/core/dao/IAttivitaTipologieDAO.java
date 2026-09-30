package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitaTipologieDAO extends BaseDAO<IAttivitaTipologie, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IAttivitaTipologie> findAll(Integer firstResult, Integer maxResult);
}
