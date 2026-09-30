/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiScadenzaDAO;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.service.TipiScadenzaService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipiScadenzaServiceImpl extends BaseServiceImpl<TipiScadenza, Integer> implements TipiScadenzaService {

    private TipiScadenzaDAO tipiScadenzaDAO;

    @Autowired
    public void setTipiScadenzaDAO(TipiScadenzaDAO tipiScadenzaDAO) {

	this.tipiScadenzaDAO = tipiScadenzaDAO;
    }

    @Override
    protected Class<TipiScadenza> getEntityClass() {

	return TipiScadenza.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_TIPISCADENZA" })
    public void delete(TipiScadenza entity) {

	tipiScadenzaDAO.delete(entity);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_TIPISCADENZA" })
    public List<TipiScadenza> findAll(Integer firstResult, Integer maxResult) {

	return tipiScadenzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_TIPISCADENZA" })
    public TipiScadenza findById(Integer id) {

	return tipiScadenzaDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_TIPISCADENZA" })
    public void insert(TipiScadenza entity) {

	if (validateEntity(entity)) {
	    tipiScadenzaDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_TIPISCADENZA" })
    public void update(TipiScadenza entity) {

	if (validateEntity(entity)) {
	    tipiScadenzaDAO.update(entity);
	}
    }
}
