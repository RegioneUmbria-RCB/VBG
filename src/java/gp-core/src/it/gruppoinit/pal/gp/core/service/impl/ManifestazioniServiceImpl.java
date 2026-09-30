package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ManifestazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.service.ManifestazioniService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManifestazioniServiceImpl extends BaseServiceImpl<Manifestazioni, Integer> implements ManifestazioniService {

    private ManifestazioniDAO manifestazioniDAO;

    @Autowired
    public void setManifestazioniDAO(ManifestazioniDAO manifestazioniDAO) {

	this.manifestazioniDAO = manifestazioniDAO;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_MANIFESTAZIONI" })
    public void delete(Manifestazioni entity) {

	manifestazioniDAO.delete(entity);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MANIFESTAZIONI" })
    public List<Manifestazioni> findAll(Integer firstResult, Integer maxResult) {

	return manifestazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_MANIFESTAZIONI" })
    public Manifestazioni findById(Integer id) {

	return manifestazioniDAO.findById(id);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_MANIFESTAZIONI" })
    public void insert(Manifestazioni entity) {

	if (validateEntity(entity)) {
	    manifestazioniDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_MANIFESTAZIONI" })
    public void update(Manifestazioni entity) {

	if (validateEntity(entity)) {
	    manifestazioniDAO.update(entity);
	}
    }

    @Override
    protected Class<Manifestazioni> getEntityClass() {

	return Manifestazioni.class;
    }
}
