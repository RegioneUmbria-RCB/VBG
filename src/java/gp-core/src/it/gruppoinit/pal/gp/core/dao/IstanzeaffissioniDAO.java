package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeaffissioniDAO extends BaseDAO<Istanzeaffissioni, IstanzeaffissioniId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzeaffissioni> findAll(Integer firstResult, Integer maxResult);
}
