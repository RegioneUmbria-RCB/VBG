package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConcessionitipiDAO;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcessionitipiServiceImpl extends BaseServiceImpl<Concessionitipi, String> implements ConcessionitipiService {

    private ConcessionitipiDAO concessionitipiDAO;

    @Autowired
    public void setConcessionitipiDAO(ConcessionitipiDAO concessionitipiDAO) {

	this.concessionitipiDAO = concessionitipiDAO;
    }

    @Override
    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_CONCESSIONITIPI" })
    public void delete(Concessionitipi entity) {

	concessionitipiDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_CONCESSIONITIPI" })
    public List<Concessionitipi> findAll(Integer firstResult, Integer maxResult) {

	return concessionitipiDAO.findAll(null, null);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_CONCESSIONITIPI" })
    public Concessionitipi findById(String id) {

	return concessionitipiDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_CONCESSIONITIPI" })
    public void insert(Concessionitipi entity) {

	if (validateEntity(entity)) {
	    concessionitipiDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_CONCESSIONITIPI" })
    public void update(Concessionitipi entity) {

	if (validateEntity(entity)) {
	    concessionitipiDAO.update(entity);
	}
    }

    @Override
    protected Class<Concessionitipi> getEntityClass() {

	return Concessionitipi.class;
    }

    @Override
    public List<Concessionitipi> findByDescrizione(Concessionitipi entity) {

	return concessionitipiDAO.findByDescrizione(entity);
    }
}
