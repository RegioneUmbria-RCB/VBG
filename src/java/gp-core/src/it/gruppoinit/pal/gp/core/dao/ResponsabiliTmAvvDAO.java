package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvvId;

import java.util.List;

/**
 * 
 * @author riccardob
 */
public interface ResponsabiliTmAvvDAO extends BaseDAO<ResponsabiliTmAvv, ResponsabiliTmAvvId> {

    /**
     * @throws NotImplementedException
     * 
     */
    public List<ResponsabiliTmAvv> findAll(Integer firstResult, Integer maxResult);
}
