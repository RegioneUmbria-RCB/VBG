package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AllegatiDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AllegatiDocSoggFirmatariDAO extends BaseDAO<AllegatiDocSoggFirmatari, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AllegatiDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);
}
