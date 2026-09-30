package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavorooffroDAO extends BaseDAO<Bachecalavorooffro, PkId> {

    /**
     * 
     * @return Una lista di offerte di lavoro ordinate per data di scadenza desc
     */
    public List<Bachecalavorooffro> findAll(Integer firstResult, Integer maxResult);
}
