package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollCfgConti;
import it.gruppoinit.pal.gp.core.domain.BollCfgContiId;

public interface BollCfgContiService extends BaseService<BollCfgConti, BollCfgContiId> {

    public List<BollCfgConti> findByBollCfgTipo(Integer codiceBollTipo, Integer firstResult, Integer maxResult);
}
