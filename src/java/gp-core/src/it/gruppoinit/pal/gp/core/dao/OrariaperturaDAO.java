package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Orariapertura;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface OrariaperturaDAO extends BaseDAO<Orariapertura, PkId> {

    /**
     * TODO Restituisce gli orari di apertura (filtrando per idcomune e software)
     * 
     */
    public List<Orariapertura> findAll(Integer firstResult, Integer maxResult);
}
