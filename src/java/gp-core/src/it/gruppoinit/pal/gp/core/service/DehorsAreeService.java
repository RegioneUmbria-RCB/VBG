package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DehorsAreeDAO;
import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsAreeService extends BaseService<DehorsAree, PkId> {

    /**
     * @see DehorsAreeDAO#findAll(Integer, Integer)
     */
    public List<DehorsAree> findAll(Integer firstResult, Integer maxResult);

    public  List<DehorsAree> findByArea(Integer codicearea);
}
