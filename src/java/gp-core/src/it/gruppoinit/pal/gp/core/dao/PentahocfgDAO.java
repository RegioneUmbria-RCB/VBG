package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Pentahocfg;

import java.util.List;

/**
 * 
 * @author
 */
public interface PentahocfgDAO extends BaseDAO<Pentahocfg, String> {

    public List<Pentahocfg> findAll(Integer firstResult, Integer maxResult);
}
