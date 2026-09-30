package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegateId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwIstanzecollegateDAO extends BaseDAO<VwIstanzecollegate, VwIstanzecollegateId> {

    /**
     * Lista di record VwIstanzecollegate filtrate per idcomune
     * 
     */
    public List<VwIstanzecollegate> findAll(Integer firstResult, Integer maxResult);
}
