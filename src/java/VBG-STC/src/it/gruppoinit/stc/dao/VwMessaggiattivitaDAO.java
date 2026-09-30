package it.gruppoinit.stc.dao;

import java.util.Collection;

import it.gruppoinit.stc.domain.VwMessaggiattivita;

public interface VwMessaggiattivitaDAO extends BaseDAO<VwMessaggiattivita, Integer> {

    public Collection<VwMessaggiattivita> findByFilter(VwMessaggiattivita vwMessaggiattivita, Integer firstResult, Integer maxResult);

    public int countByFilter(VwMessaggiattivita vwMessaggiattivita);
}
