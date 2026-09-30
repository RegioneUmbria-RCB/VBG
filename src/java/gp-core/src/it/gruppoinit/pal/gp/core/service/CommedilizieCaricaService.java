package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieCaricaDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieCaricaService extends BaseService<CommedilizieCarica, PkId> {

    /**
     * @see CommedilizieCaricaDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieCarica> findAll(Integer firstResult, Integer maxResult);
}
