package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;

import java.util.List;

public interface TipiareeDAO extends BaseDAO<Tipiaree, PkId> {

    List<Tipiaree> findByFilter(Tipiaree entity);

    List<Tipiaree> findAll(Integer firstResult, Integer maxResult);
}
