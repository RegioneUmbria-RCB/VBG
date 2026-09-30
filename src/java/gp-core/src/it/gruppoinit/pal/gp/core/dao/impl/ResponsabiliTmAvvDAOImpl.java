package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliTmAvvDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvvId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author riccardob
 */
@Repository
public class ResponsabiliTmAvvDAOImpl extends BaseDAOImpl<ResponsabiliTmAvv, ResponsabiliTmAvvId> implements ResponsabiliTmAvvDAO {

    @Override
    public Class<ResponsabiliTmAvv> getEntityClass() {

	return ResponsabiliTmAvv.class;
    }

    @Override
    public List<ResponsabiliTmAvv> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
