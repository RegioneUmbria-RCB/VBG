/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EndoCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.EndoCausaliService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class EndoCausaliServiceImpl extends BaseServiceImpl<EndoCausali, PkId> implements EndoCausaliService {

    private EndoCausaliDAO endoCausaliDAO;

    @Autowired
    public void setEndoCausaliDAO(EndoCausaliDAO endoCausaliDAO) {

	this.endoCausaliDAO = endoCausaliDAO;
    }

    @Override
    protected Class<EndoCausali> getEntityClass() {

	return EndoCausali.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ENDOCAUSALI" })
    public void delete(EndoCausali entity) {

	endoCausaliDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ENDOCAUSALI" })
    public List<EndoCausali> findAll(Integer firstResult, Integer maxResult) {

	return endoCausaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ENDOCAUSALI" })
    public EndoCausali findById(PkId id) {

	return endoCausaliDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ENDOCAUSALI" })
    public void insert(EndoCausali entity) {

	if (validateEntity(entity)) {
	    endoCausaliDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ENDOCAUSALI" })
    public void update(EndoCausali entity) {

	if (validateEntity(entity)) {
	    endoCausaliDAO.update(entity);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ENDOCAUSALI" })
    @Override
    public List<EndoCausali> findByInventarioprocedimenti(EndoCausali entity) {

	return endoCausaliDAO.findByInventarioprocedimenti(entity);
    }
}
