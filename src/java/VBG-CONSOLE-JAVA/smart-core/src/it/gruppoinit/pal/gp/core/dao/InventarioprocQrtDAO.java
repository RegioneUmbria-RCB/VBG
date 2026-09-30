package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface InventarioprocQrtDAO extends BaseDAO<InventarioprocQrt, PkId> {

    public List<InventarioprocQrt> findAll(Integer firstResult, Integer maxResult);
}
