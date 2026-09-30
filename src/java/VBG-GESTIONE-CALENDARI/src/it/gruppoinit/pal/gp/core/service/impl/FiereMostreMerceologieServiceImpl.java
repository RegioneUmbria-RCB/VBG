package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostreMerceologieDAO;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FiereMostreMerceologieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiereMostreMerceologieServiceImpl extends BaseServiceImpl<FiereMostreMerceologie, PkId> implements FiereMostreMerceologieService {

    private FiereMostreMerceologieDAO fiereMostreMerceologieDAO;

    @Override
    public void insert(FiereMostreMerceologie entity) {

	if (validateEntity(entity)) {
	    fiereMostreMerceologieDAO.insert(entity);
	}
    }

    @Override
    public void update(FiereMostreMerceologie entity) {

	if (validateEntity(entity)) {
	    fiereMostreMerceologieDAO.update(entity);
	}
    }

    @Override
    public void delete(FiereMostreMerceologie entity) {

	if (isDeleteAllowed(entity)) {
	    fiereMostreMerceologieDAO.delete(entity);
	}
    }

    @Override
    public FiereMostreMerceologie findById(PkId id) {

	return fiereMostreMerceologieDAO.findById(id);
    }

    @Override
    protected Class<FiereMostreMerceologie> getEntityClass() {

	return FiereMostreMerceologie.class;
    }

    @Autowired
    public void setFiereMostreMerceologieDAO(FiereMostreMerceologieDAO fiereMostreMerceologieDAO) {

	this.fiereMostreMerceologieDAO = fiereMostreMerceologieDAO;
    }
}
