package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioniassegnazioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniassegnazioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeaffissioniassegnazioniDAO extends BaseDAO<Istanzeaffissioniassegnazioni, IstanzeaffissioniassegnazioniId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzeaffissioniassegnazioni> findAll(Integer firstResult, Integer maxResult);
}
