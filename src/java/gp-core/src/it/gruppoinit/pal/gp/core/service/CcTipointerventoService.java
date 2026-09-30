package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcTipointerventoDAO;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcTipointerventoService extends BaseService<CcTipointervento, PkId> {

    /**
     * @see CcTipointerventoDAO#findAll(Integer, Integer)
     */
    public List<CcTipointervento> findAll(Integer firstResult, Integer maxResult);
}
