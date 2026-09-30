package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeaffissioniService extends BaseService<Istanzeaffissioni, IstanzeaffissioniId> {

    /**
     * @see IstanzeaffissioniDAO#findAll(Integer, Integer)
     */
    public List<Istanzeaffissioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera i record di Istanzeaffissioni diltrando per istanza
     * 
     * @param codice
     * @return
     */
    public List<Istanzeaffissioni> findByIstanze(Integer codice);
}
