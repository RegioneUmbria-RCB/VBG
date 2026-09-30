package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatistradarioDAO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatistradarioService extends BaseService<Mercatistradario, PkId> {

    /**
     * @see MercatistradarioDAO#findAll(Integer, Integer)
     */
    public List<Mercatistradario> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see MercatistradarioDAO#findByMercato(Mercati mercati)
     */
    public List<Mercatistradario> findByMercato(Mercati mercati);
}
