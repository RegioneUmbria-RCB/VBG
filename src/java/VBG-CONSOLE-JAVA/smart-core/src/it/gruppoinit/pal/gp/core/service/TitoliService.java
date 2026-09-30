package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TitoliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Titoli;

import java.util.List;

public interface TitoliService extends BaseService<Titoli, PkId> {

    /**
     * @see TitoliDAO#findAll(Integer firstResult, Integer maxResult)
     */
    public List<Titoli> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see TitoliDAO#findByDescrizione(String descrizione)
     */
    public List<Titoli> findByDescrizione(String descrizione);
}
