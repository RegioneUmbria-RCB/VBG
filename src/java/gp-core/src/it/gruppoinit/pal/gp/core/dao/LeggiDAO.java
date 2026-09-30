package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LeggiDAO extends BaseDAO<Leggi, PkId> {

    /**
     * Estrae tutte le leggi in ordine alfabetico
     * 
     * @param campo
     *            del bean Leggi rispetto al quale ordinare
     * 
     * @return lista di oggetti Leggi ordinati
     */
    public List<Leggi> findAllWithOrder(String campo);
}
