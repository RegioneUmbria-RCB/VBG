package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumenticatDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface AlberoprocDocumenticatService extends BaseService<AlberoprocDocumenticat, PkId> {

    /**
     * @see AlberoprocDocumenticatDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocDocumenticat> findAll(Integer firstResult, Integer maxResult);
}
