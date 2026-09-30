/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BandiinputDAO;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BandiinputService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class BandiinputServiceImpl extends BaseServiceImpl<Bandiinput, PkId> implements BandiinputService {

    private BandiinputDAO bandiinputDAO;

    @Autowired
    public void setBandiinputDAO(BandiinputDAO bandiinputDAO) {

	this.bandiinputDAO = bandiinputDAO;
    }

    @Override
    protected Class<Bandiinput> getEntityClass() {

	return Bandiinput.class;
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_BANDIINPUT" })
    @Override
    public void delete(Bandiinput entity) {

	bandiinputDAO.delete(entity);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_BANDIINPUT" })
    @Override
    public List<Bandiinput> findAll(Integer firstResult, Integer maxResult) {

	return bandiinputDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_BANDIINPUT" })
    @Override
    public Bandiinput findById(PkId id) {

	return bandiinputDAO.findById(id);
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_BANDIINPUT" })
    @Override
    public void insert(Bandiinput entity) {

	if (validateEntity(entity)) {
	    bandiinputDAO.insert(entity);
	}
    }

    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_BANDIINPUT" })
    @Override
    public void update(Bandiinput entity) {

	if (validateEntity(entity)) {
	    bandiinputDAO.update(entity);
	}
    }
}
