/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PeriodicitaDAO;
import it.gruppoinit.pal.gp.core.domain.Periodicita;
import it.gruppoinit.pal.gp.core.service.PeriodicitaService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class PeriodicitaServiceImpl extends BaseServiceImpl<Periodicita, Integer> implements PeriodicitaService {

    private PeriodicitaDAO periodicitaDAO;

    @Autowired
    public void setPeriodicitaDAO(PeriodicitaDAO periodicitaDAO) {

	this.periodicitaDAO = periodicitaDAO;
    }

    @Override
    protected Class<Periodicita> getEntityClass() {

	return Periodicita.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_PERIODICITA" })
    public void delete(Periodicita entity) {

	periodicitaDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_PERIODICITA" })
    public List<Periodicita> findAll(Integer firstResult, Integer maxResult) {

	return periodicitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_PERIODICITA" })
    public Periodicita findById(Integer id) {

	return periodicitaDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_PERIODICITA" })
    public void insert(Periodicita entity) {

	if (validateEntity(entity)) {
	    periodicitaDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_PERIODICITA" })
    public void update(Periodicita entity) {

	if (validateEntity(entity)) {
	    periodicitaDAO.update(entity);
	}
    }
}
