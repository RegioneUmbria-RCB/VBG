package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollCfgTipo;

/**
 * 
 * @author
 */
public interface BollCfgTipoDAO extends BaseDAO<BollCfgTipo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollCfgTipo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle tipologie abilitate per il responsabile ed il software corrente
     * 
     * @param codiceResponsabile
     * @return
     */
    public List<CreazioneBollCfgTipo> findByResponsabile(Integer codiceResponsabile);
}
