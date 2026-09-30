package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BollCfgRuoliDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BollCfgRuoliService extends BaseService<BollCfgRuoli, BollCfgRuoliId> {

    /**
     * @see BollCfgRuoliDAO#findAll(Integer, Integer)
     */
    public List<BollCfgRuoli> findAll(Integer firstResult, Integer maxResult);

    /** 
     * Metodo per la ricerca di tutti i ruoli associati alla BollcfgTipo
     * */
    public List<BollCfgRuoli> findByBollcfgTipo(Integer codiceBollcfgTipo, Integer firstResult, Integer maxResult);
}
