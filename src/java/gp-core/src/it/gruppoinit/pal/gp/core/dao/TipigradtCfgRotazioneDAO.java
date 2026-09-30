package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipigradtCfgRotazioneDAO extends BaseDAO<TipigradtCfgRotazione, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<TipigradtCfgRotazione> findAll(Integer firstResult, Integer maxResult);
}
