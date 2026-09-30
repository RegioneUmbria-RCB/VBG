package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LeggiDAO;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LeggiService extends BaseService<Leggi, PkId> {

    /**
     * @see LeggiDAO#findAllWithOrder(Integer, Integer)
     */
    public List<Leggi> findAllWithOrder(String campo);
}
