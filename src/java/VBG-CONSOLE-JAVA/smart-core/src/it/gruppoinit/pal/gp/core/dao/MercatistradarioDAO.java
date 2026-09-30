package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatistradarioDAO extends BaseDAO<Mercatistradario, PkId> {

    /**
     * Restituisce le strade (filtrando per idcomune e software)
     */
    public List<Mercatistradario> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce le strade del mercato (filtrando per idcomune,software,mercato ) ordinandole per il campo descrizione
     * di stradario
     */
    public List<Mercatistradario> findByMercato(Mercati mercati);
}
