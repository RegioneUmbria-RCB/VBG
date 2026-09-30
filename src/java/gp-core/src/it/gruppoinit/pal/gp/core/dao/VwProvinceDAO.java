package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwProvince;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwProvinceDAO extends BaseDAO<VwProvince, String> {

    /**
     * Lista di province ordinate per il campo provincia
     * 
     */
    public List<VwProvince> findAll(Integer firstResult, Integer maxResult);
}
