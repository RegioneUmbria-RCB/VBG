package it.gruppoinit.stc.service;

import java.util.Collection;

import it.gruppoinit.stc.domain.VwMessaggipratiche;

public interface VwMessaggipraticheService extends BaseService<VwMessaggipratiche, Integer> {

    public Collection<VwMessaggipratiche> findByFilter(VwMessaggipratiche vwMessaggipratiche, Integer firstResult, Integer maxResult);

    public int countByFilter(VwMessaggipratiche vwMessaggipratiche);
}
