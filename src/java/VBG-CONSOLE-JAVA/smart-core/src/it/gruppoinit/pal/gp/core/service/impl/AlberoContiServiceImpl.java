/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoContiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoContiService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class AlberoContiServiceImpl extends BaseServiceImpl<AlberoConti, PkId> implements AlberoContiService {

    private AlberoContiDAO alberoContiDAO;

    @Autowired
    public void setAlberoContiDAO(AlberoContiDAO alberoContiDAO) {

	this.alberoContiDAO = alberoContiDAO;
    }

    @Override
    protected Class<AlberoConti> getEntityClass() {

	return AlberoConti.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ALBEROCONTI" })
    public void delete(AlberoConti entity) {

	alberoContiDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ALBEROCONTI" })
    public List<AlberoConti> findAll(Integer firstResult, Integer maxResult) {

	return alberoContiDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ALBEROCONTI" })
    public AlberoConti findById(PkId id) {

	return alberoContiDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ALBEROCONTI" })
    public void insert(AlberoConti entity) {

	if (validateEntity(entity)) {
	    alberoContiDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ALBEROCONTI" })
    public void update(AlberoConti entity) {

	if (validateEntity(entity)) {
	    alberoContiDAO.update(entity);
	}
    }
}
