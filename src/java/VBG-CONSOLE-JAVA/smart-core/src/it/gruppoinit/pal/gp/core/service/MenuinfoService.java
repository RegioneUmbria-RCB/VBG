package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MenuinfoDAO;
import it.gruppoinit.pal.gp.core.domain.Menuinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MenuinfoService extends BaseService<Menuinfo, PkId> {

    /**
     * @see MenuinfoDAO#findAll(Integer, Integer)
     */
    public List<Menuinfo> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see MenuinfoDAO#findOrdineMax()
     */
    public Integer findOrdineMax();
}
