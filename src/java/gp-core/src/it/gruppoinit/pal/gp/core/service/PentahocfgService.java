package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PentahocfgDAO;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;

import java.util.List;

/**
 * 
 * @author
 */
public interface PentahocfgService extends BaseService<Pentahocfg, String> {

    /**
     * @see PentahocfgDAO#findAll(Integer, Integer)
     */
    public List<Pentahocfg> findAll(Integer firstResult, Integer maxResult);
}
