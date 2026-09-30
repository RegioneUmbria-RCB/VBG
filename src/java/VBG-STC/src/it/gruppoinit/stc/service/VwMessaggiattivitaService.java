package it.gruppoinit.stc.service;

import java.util.Collection;

import it.gruppoinit.stc.domain.VwMessaggiattivita;

public interface VwMessaggiattivitaService extends BaseService<VwMessaggiattivita, Integer> {

    public Collection<VwMessaggiattivita> findByFilter(VwMessaggiattivita vwMessaggiattivita, Integer firstResult, Integer maxResult);

    public int countByFilter(VwMessaggiattivita vwMessaggiattivita);
}
