package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoFormatiDocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.FoFormatiDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoFormatiDocumentiService extends BaseService<FoFormatiDocumenti, PkId> {

    /**
     * @see FoFormatiDocumentiDAO#findAll(Integer, Integer)
     */
    public List<FoFormatiDocumenti> findAll(Integer firstResult, Integer maxResult);
}
