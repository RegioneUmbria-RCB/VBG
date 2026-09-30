package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.LivelloServizioDAO;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface LivelloServizioService extends BaseService<LivelloServizio, PkId> {

    /**
     * @see LivelloServizioDAO#findAll(Integer, Integer)
     */
    public List<LivelloServizio> findAll(Integer firstResult, Integer maxResult);

    public List<LivelloServizio> findByDescrizione(String textToSearch);

    public List<LivelloServizio> findByMercatoUso(Integer codiceuso, boolean isSoloAttive);

    public List<LivelloServizio> findServiziDisponibili();

    @DeletableCacheElements
    public void resetObjectCached();
}
