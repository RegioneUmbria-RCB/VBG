package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcDettaglisuperficieDAO;
import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcDettaglisuperficieService extends BaseService<CcDettaglisuperficie, PkId> {

    /**
     * @see CcDettaglisuperficieDAO#findAll(Integer, Integer)
     */
    public List<CcDettaglisuperficie> findAll(Integer firstResult, Integer maxResult);
}
