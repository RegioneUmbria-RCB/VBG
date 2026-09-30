/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocRuoliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class AlberoprocRuoliServiceImpl extends BaseServiceImpl<AlberoprocRuoli, AlberoprocRuoliId> implements AlberoprocRuoliService {

    private AlberoprocRuoliDAO alberoprocRuoliDAO;

    @Autowired
    public void setAlberoprocRuoliDAO(AlberoprocRuoliDAO alberoprocRuoliDAO) {

	this.alberoprocRuoliDAO = alberoprocRuoliDAO;
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ALBEROPROCRUOLI" })
    public void delete(AlberoprocRuoli entity) {

	alberoprocRuoliDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ALBEROPROCRUOLI" })
    public List<AlberoprocRuoli> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocRuoliDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ALBEROPROCRUOLI" })
    public AlberoprocRuoli findById(AlberoprocRuoliId id) {

	return alberoprocRuoliDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ALBEROPROCRUOLI" })
    public void insert(AlberoprocRuoli entity) {

	if (validateEntity(entity)) {
	    alberoprocRuoliDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ALBEROPROCRUOLI" })
    public void update(AlberoprocRuoli entity) {

	if (validateEntity(entity)) {
	    alberoprocRuoliDAO.update(entity);
	}
    }

    @Override
    protected Class<AlberoprocRuoli> getEntityClass() {

	return AlberoprocRuoli.class;
    }
}
