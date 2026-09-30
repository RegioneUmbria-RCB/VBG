package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmScaId;

import java.util.List;

/**
 * 
 * @author riccardob
 */
public interface ResponsabiliTmScaDAO extends BaseDAO<ResponsabiliTmSca, ResponsabiliTmScaId> {

    /**
     * @throws NotImplementedException
     * 
     */
    public List<ResponsabiliTmSca> findAll(Integer firstResult, Integer maxResult);
}
