package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Interventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface InterventiDAO extends BaseDAO<Interventi, PkId> {

    /**
     * Restituisce gli Iinterventi (filtrando per idcomune e software) ordinandole per il campo "intervento" ascendente
     */
    public List<Interventi> findAll(Integer firstResult, Integer maxResult);
}
