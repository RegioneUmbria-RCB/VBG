package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlboCategorieDAO;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface AlboCategorieService extends BaseService<AlboCategorie, PkId> {

    /**
     * @see AlboCategorieDAO#findAll(Integer, Integer)
     */
    public List<AlboCategorie> findAll(Integer firstResult, Integer maxResult);
}
