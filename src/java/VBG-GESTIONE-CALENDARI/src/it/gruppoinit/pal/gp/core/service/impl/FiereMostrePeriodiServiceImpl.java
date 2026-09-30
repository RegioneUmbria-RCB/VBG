package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostrePeriodiDAO;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FiereMostrePeriodiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiereMostrePeriodiServiceImpl extends BaseServiceImpl<FiereMostrePeriodi, PkId> implements FiereMostrePeriodiService {

    private FiereMostrePeriodiDAO fiereMostrePeriodiDAO;

    @Override
    public void insert(FiereMostrePeriodi entity) {

	if (validateEntity(entity)) {
	    fiereMostrePeriodiDAO.insert(entity);
	}
    }

    @Override
    public void update(FiereMostrePeriodi entity) {

	if (validateEntity(entity)) {
	    fiereMostrePeriodiDAO.update(entity);
	}
    }

    @Override
    public void delete(FiereMostrePeriodi entity) {

	if (isDeleteAllowed(entity)) {
	    fiereMostrePeriodiDAO.delete(entity);
	}
    }

    @Override
    public FiereMostrePeriodi findById(PkId id) {

	return fiereMostrePeriodiDAO.findById(id);
    }

    @Override
    public void evict(FiereMostrePeriodi entity) {

	fiereMostrePeriodiDAO.evict(entity);
    }

    @Override
    protected Class<FiereMostrePeriodi> getEntityClass() {

	return FiereMostrePeriodi.class;
    }

    @Autowired
    public void setFiereMostrePeriodiDAO(FiereMostrePeriodiDAO fiereMostrePeriodiDAO) {

	this.fiereMostrePeriodiDAO = fiereMostrePeriodiDAO;
    }
}
