package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ParametriesportazioneDAO extends BaseDAO<Parametriesportazione, PkId> {

    public List<Parametriesportazione> findAll(Integer firstResult, Integer maxResult);
}
