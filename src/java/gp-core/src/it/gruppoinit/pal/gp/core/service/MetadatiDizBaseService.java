package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MetadatiDizBaseDAO;
import it.gruppoinit.pal.gp.core.domain.MetadatiDizBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface MetadatiDizBaseService extends BaseService<MetadatiDizBase, String> {

    /**
     * @see MetadatiDizBaseDAO#findAll(Integer, Integer)
     */
    public List<MetadatiDizBase> findAll(Integer firstResult, Integer maxResult);
}
