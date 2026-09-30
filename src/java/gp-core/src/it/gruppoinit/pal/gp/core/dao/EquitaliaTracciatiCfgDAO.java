package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface EquitaliaTracciatiCfgDAO extends BaseDAO<EquitaliaTracciatiCfg, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<EquitaliaTracciatiCfg> findAll(Integer firstResult, Integer maxResult);
}
