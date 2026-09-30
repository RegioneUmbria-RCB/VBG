package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Equitaliatracciato;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface EquitaliatracciatoDAO extends BaseDAO<Equitaliatracciato, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Equitaliatracciato> findAll(Integer firstResult, Integer maxResult);
}
