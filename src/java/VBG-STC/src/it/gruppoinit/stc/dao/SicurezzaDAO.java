package it.gruppoinit.stc.dao;

import it.gruppoinit.stc.domain.Sicurezza;

public interface SicurezzaDAO extends BaseDAO<Sicurezza, Integer> {
    /**
     * Ricerca tramite token
     * @param token
     * @return
     */
    public Sicurezza findByToken(String token);
}
