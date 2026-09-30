package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DehorsCfgDAO;
import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsCfgService extends BaseService<DehorsCfg, PkId> {

    /**
     * @see DehorsCfgDAO#findAll(Integer, Integer)
     */
    public List<DehorsCfg> findAll(Integer firstResult, Integer maxResult);

    public boolean isExistRecord();

    public DehorsCfg findByTipologiaregistro(Integer codiceRegistro);
}
