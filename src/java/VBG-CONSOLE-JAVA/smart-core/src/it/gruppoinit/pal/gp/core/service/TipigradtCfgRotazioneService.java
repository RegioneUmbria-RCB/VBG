package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipigradtCfgRotazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipigradtCfgRotazioneService extends BaseService<TipigradtCfgRotazione, PkId> {

    /**
     * @see TipigradtCfgRotazioneDAO#findAll(Integer, Integer)
     */
    public List<TipigradtCfgRotazione> findAll(Integer firstResult, Integer maxResult);

    /**
     * ritorna la lista di TipigradtCfgRotazione filtrate per TipigraduatorieT
     * 
     * @param codice
     * @return
     */
    public List<TipigradtCfgRotazione> findByTipigraduatoriet(Integer codice);
}
