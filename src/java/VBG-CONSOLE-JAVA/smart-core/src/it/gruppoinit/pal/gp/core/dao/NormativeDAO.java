package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface NormativeDAO extends BaseDAO<Normative, PkId> {

    /**
     * Ritorna la lista delle normative (filtrato per idcomune) e ordinata per il campo normativa
     * 
     */
    public List<Normative> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista delle normative filtrate per idcomune e campo normativa e ordinate per normativa
     * 
     */
    public List<Normative> findByNormativa(String normativa);
}
