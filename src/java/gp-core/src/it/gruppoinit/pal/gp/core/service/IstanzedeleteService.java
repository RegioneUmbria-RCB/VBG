package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzedeleteDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedelete;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzedeleteService extends BaseService<Istanzedelete, PkId> {

    /**
     * @see IstanzedeleteDAO#findAll(Integer, Integer)
     */
    public List<Istanzedelete> findAll(Integer firstResult, Integer maxResult);

    /**
     * popola un oggetto Istanzedelete a partire da un oggetto istanza.
     * 
     * @param entity
     * @return
     */
    public Istanzedelete populateIstanzedelete(Istanze entity);
}
