package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface ImpiantiDAO extends BaseDAO<Impianti, PkId> {

    /**
     * Restituisce gli Impianti (filtrando per idcomune e software) ordinandole per il campo "impianto" ascendente
     */
    public List<Impianti> findAll(Integer firstResult, Integer maxResult);
}
