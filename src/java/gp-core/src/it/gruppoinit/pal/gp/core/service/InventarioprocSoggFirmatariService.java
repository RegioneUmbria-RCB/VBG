package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface InventarioprocSoggFirmatariService extends BaseService<InventarioprocSoggFirmatari, PkId> {

    /**
     * @see InventarioprocSoggFirmatariDAO#findAll(Integer, Integer)
     */
    public List<InventarioprocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);
    
    public List<InventarioprocSoggFirmatari> findByDocumento(Integer codicedocumento, Integer firstResult, Integer maxResult);
}
