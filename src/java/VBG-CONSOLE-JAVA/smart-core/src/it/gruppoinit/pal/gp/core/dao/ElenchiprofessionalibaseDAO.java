package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ElenchiprofessionalibaseDAO extends BaseDAO<Elenchiprofessionalibase, Integer> {

    /**
     * Lista di tutti gli albi professionali
     * 
     */
    public List<Elenchiprofessionalibase> findAll(Integer firstResult, Integer maxResult);
}
