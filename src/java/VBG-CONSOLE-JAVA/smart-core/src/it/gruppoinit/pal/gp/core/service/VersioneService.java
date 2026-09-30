package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.VersioneDAO;
import it.gruppoinit.pal.gp.core.domain.Versione;

public interface VersioneService extends BaseService<Versione, String> {

    /**
     * @see VersioneDAO#findAll(Integer, Integer)
     */
    public List<Versione> findAll(Integer firstResult, Integer maxResult);
}
