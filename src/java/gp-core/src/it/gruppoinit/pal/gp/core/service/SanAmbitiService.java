package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SanAmbitiDAO;
import it.gruppoinit.pal.gp.core.domain.SanAmbiti;

import java.util.List;

/**
 * 
 * @author
 */
public interface SanAmbitiService extends BaseService<SanAmbiti, Integer> {

    /**
     * @see SanAmbitiDAO#findAll(Integer, Integer)
     */
    public List<SanAmbiti> findAll(Integer firstResult, Integer maxResult);
}
