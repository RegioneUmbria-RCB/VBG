package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniassegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioniassegnazioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniassegnazioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeaffissioniassegnazioniService extends BaseService<Istanzeaffissioniassegnazioni, IstanzeaffissioniassegnazioniId> {

    /**
     * @see IstanzeaffissioniassegnazioniDAO#findAll(Integer, Integer)
     */
    public List<Istanzeaffissioniassegnazioni> findAll(Integer firstResult, Integer maxResult);
}
