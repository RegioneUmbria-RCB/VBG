package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.EquitaliaTracciatiCfgDAO;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface EquitaliaTracciatiCfgService extends BaseService<EquitaliaTracciatiCfg, PkId> {

    /**
     * @see EquitaliaTracciatiCfgDAO#findAll(Integer, Integer)
     */
    public List<EquitaliaTracciatiCfg> findAll(Integer firstResult, Integer maxResult);

    public EquitaliaTracciatiCfg findBySoftware();
}
