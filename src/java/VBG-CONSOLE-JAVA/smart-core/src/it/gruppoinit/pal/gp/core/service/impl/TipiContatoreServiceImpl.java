package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiContatoreDAO;
import it.gruppoinit.pal.gp.core.domain.TipiContatore;
import it.gruppoinit.pal.gp.core.service.TipiContatoreService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiContatoreServiceImpl extends BaseServiceImpl<TipiContatore, Integer> implements TipiContatoreService {

    private TipiContatoreDAO tipiContatoreDAO;

    @Autowired
    public void setTipiContatoreDAO(TipiContatoreDAO tipiContatoreDAO) {

	this.tipiContatoreDAO = tipiContatoreDAO;
    }

    @Override
    protected Class<TipiContatore> getEntityClass() {

	return TipiContatore.class;
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_TIPICONTATORE" })
    @Override
    public void delete(TipiContatore entity) {

	tipiContatoreDAO.delete(entity);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_TIPICONTATORE" })
    @Override
    public List<TipiContatore> findAll(Integer firstResult, Integer maxResult) {

	return tipiContatoreDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_TIPICONTATORE" })
    @Override
    public TipiContatore findById(Integer id) {

	return tipiContatoreDAO.findById(id);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_TIPICONTATORE" })
    @Override
    public void insert(TipiContatore entity) {

	if (validateEntity(entity)) {
	    tipiContatoreDAO.insert(entity);
	}
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_TIPICONTATORE" })
    @Override
    public void update(TipiContatore entity) {

	if (validateEntity(entity)) {
	    tipiContatoreDAO.update(entity);
	}
    }
}
