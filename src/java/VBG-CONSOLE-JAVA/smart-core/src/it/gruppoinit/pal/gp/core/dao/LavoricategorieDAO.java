package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoricategorieDAO extends BaseDAO<Lavoricategorie, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Lavoricategorie> findAll(Integer firstResult, Integer maxResult);
}
