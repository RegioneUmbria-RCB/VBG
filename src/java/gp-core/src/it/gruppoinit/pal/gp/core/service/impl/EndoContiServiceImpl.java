/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EndoContiDAO;
import it.gruppoinit.pal.gp.core.domain.EndoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.EndoContiService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class EndoContiServiceImpl extends BaseServiceImpl<EndoConti, PkId> implements EndoContiService {

    private EndoContiDAO endoContiDAO;

    @Autowired
    public void setEndoContiDAO(EndoContiDAO endoContiDAO) {

	this.endoContiDAO = endoContiDAO;
    }

    @Override
    protected Class<EndoConti> getEntityClass() {

	return EndoConti.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ENDOCONTI" })
    public void delete(EndoConti entity) {

	endoContiDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ENDOCONTI" })
    public List<EndoConti> findAll(Integer firstResult, Integer maxResult) {

	return endoContiDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ENDOCONTI" })
    public EndoConti findById(PkId id) {

	return endoContiDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ENDOCONTI" })
    public void insert(EndoConti entity) {

	if (validateEntity(entity)) {
	    endoContiDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ENDOCONTI" })
    public void update(EndoConti entity) {

	if (validateEntity(entity)) {
	    endoContiDAO.update(entity);
	}
    }
}
