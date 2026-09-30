package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface IstanzefrontofficeDAO extends BaseDAO<Istanzefrontoffice, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzefrontoffice> findAll(Integer firstResult, Integer maxResult);
}
