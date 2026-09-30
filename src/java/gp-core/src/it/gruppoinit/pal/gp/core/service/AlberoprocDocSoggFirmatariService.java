package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocDocSoggFirmatariService extends BaseService<AlberoprocDocSoggFirmatari, PkId> {

    /**
     * @see AlberoprocDocSoggFirmatariDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);

    public List<AlberoprocDocSoggFirmatari> findByDocumento(Integer codicedocumento, Integer firstResult, Integer maxResult);

    public Integer countSoggettiInUsoForDocuments(List<Integer> alberoprocids, Integer tipisoggetto);
}
