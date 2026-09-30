package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MenuDAO;
import it.gruppoinit.pal.gp.core.domain.Menu;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MenuService extends BaseService<Menu, PkId> {

    /**
     * @see MenuDAO#findAll(Integer, Integer)
     */
    public List<Menu> findAll(Integer firstResult, Integer maxResult);
    /**
     * @see MenuDAO#findOrdineMax()
     */
    public Integer findOrdineMax();
}
