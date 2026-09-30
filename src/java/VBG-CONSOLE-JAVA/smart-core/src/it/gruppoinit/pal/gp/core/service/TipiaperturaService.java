package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiaperturaDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface TipiaperturaService extends BaseService<Tipiapertura, PkId> {

    /**
     * @see TipiaperturaDAO#findAll(Integer, Integer)
     */
    public List<Tipiapertura> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param tipiapertura
     * @return ricerca i tipiapertura filtrando per descrizione
     */
    public List<Tipiapertura> findByDescrizione(Tipiapertura tipiapertura);
}
