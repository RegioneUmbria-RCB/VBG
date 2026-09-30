package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MetadatiDizBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface MetadatiDizBaseDAO extends BaseDAO<MetadatiDizBase, String> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MetadatiDizBase> findAll(Integer firstResult, Integer maxResult);
}
