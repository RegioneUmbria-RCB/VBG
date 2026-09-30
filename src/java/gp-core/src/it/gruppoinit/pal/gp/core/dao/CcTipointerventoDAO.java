package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTipointerventoDAO extends BaseDAO<CcTipointervento, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcTipointervento> findAll(Integer firstResult, Integer maxResult);
}
