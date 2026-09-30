package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliTmScaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmScaId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author riccardob
 */
@Repository
public class ResponsabiliTmScaDAOImpl extends BaseDAOImpl<ResponsabiliTmSca, ResponsabiliTmScaId> implements ResponsabiliTmScaDAO {

    @Override
    public Class<ResponsabiliTmSca> getEntityClass() {

	return ResponsabiliTmSca.class;
    }

    @Override
    public List<ResponsabiliTmSca> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
