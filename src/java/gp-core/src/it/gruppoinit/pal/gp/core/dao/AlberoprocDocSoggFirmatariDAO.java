package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocDocSoggFirmatariDAO extends BaseDAO<AlberoprocDocSoggFirmatari, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AlberoprocDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult);

    public Integer countSoggettiInUsoForDocuments(List<Integer> alberoprocids, Integer tipisoggetto);
}
