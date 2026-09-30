package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontributoDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcIcalcoloDcontributoService extends BaseService<CcIcalcoloDcontributo, PkId> {

    /**
     * @see CcIcalcoloDcontributoDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoloDcontributo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Controlla se esitse il record per il CcTipointervento passato
     * 
     * @param entity
     */
    public boolean existRecordByCcTipointervento(CcTipointervento entity);
}
