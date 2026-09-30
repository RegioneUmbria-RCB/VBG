package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AllegatiDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.domain.AllegatiDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AllegatiDocSoggFirmatariService extends BaseService<AllegatiDocSoggFirmatari, PkId> {

    /**
     * @see AllegatiDocSoggFirmatariDAO#findAll(Integer, Integer)
     */
    public List<AllegatiDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);

    public List<AllegatiDocSoggFirmatari> findByDocumento(Integer codiceallegato, String idcomunerecord, Integer object, Integer object2);
}
