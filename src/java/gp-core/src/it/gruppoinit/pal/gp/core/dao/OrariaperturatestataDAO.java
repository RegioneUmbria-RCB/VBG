package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface OrariaperturatestataDAO extends BaseDAO<Orariaperturatestata, PkId> {

    /**
     * Restituisce una lista di Orariaperturatestata filtrata per idcomune e ordinata per tipiorario.todescrizione
     * 
     */
    public List<Orariaperturatestata> findAll(Integer firstResult, Integer maxResult);
}
