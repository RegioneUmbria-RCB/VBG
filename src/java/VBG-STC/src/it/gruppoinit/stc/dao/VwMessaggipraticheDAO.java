package it.gruppoinit.stc.dao;

import java.util.Collection;

import it.gruppoinit.stc.domain.VwMessaggipratiche;

public interface VwMessaggipraticheDAO extends BaseDAO<VwMessaggipratiche, Integer> {

    public Collection<VwMessaggipratiche> findByFilter(VwMessaggipratiche vwMessaggipratiche, Integer firstResult, Integer maxResult);

    public int countByFilter(VwMessaggipratiche vwMessaggipratiche);
}
