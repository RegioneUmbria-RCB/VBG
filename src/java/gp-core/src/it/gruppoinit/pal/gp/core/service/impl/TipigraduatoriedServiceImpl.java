/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatoriedDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoried;
import it.gruppoinit.pal.gp.core.service.TipigraduatoriedService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipigraduatoriedServiceImpl extends BaseServiceImpl<Tipigraduatoried, PkId> implements TipigraduatoriedService {

    private TipigraduatoriedDAO tipigraduatoriedDAO;

    @Autowired
    public void setTipigraduatoriedDAO(TipigraduatoriedDAO tipigraduatoriedDAO) {

	this.tipigraduatoriedDAO = tipigraduatoriedDAO;
    }

    @Override
    protected Class<Tipigraduatoried> getEntityClass() {

	return Tipigraduatoried.class;
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_TIPIGRADUATORIED" })
    @Override
    public void delete(Tipigraduatoried entity) {

	tipigraduatoriedDAO.delete(entity);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_TIPIGRADUATORIED" })
    @Override
    public List<Tipigraduatoried> findAll(Integer firstResult, Integer maxResult) {

	return tipigraduatoriedDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_TIPIGRADUATORIED" })
    @Override
    public Tipigraduatoried findById(PkId id) {

	return tipigraduatoriedDAO.findById(id);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_TIPIGRADUATORIED" })
    @Override
    public void insert(Tipigraduatoried entity) {

	if (validateEntity(entity)) {
	    tipigraduatoriedDAO.insert(entity);
	}
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_TIPIGRADUATORIED" })
    @Override
    public void update(Tipigraduatoried entity) {

	if (validateEntity(entity)) {
	    tipigraduatoriedDAO.update(entity);
	}
    }
}
