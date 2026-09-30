package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.SanAmbiti;

import java.util.List;

/**
 * 
 * @author
 */
public interface SanAmbitiDAO extends BaseDAO<SanAmbiti, Integer> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<SanAmbiti> findAll(Integer firstResult, Integer maxResult);
}
