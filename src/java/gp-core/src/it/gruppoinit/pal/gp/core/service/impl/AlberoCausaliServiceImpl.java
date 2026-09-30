/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.AlberoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoCausaliService;
import it.gruppoinit.pal.gp.core.service.AlberoContiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class AlberoCausaliServiceImpl extends BaseServiceImpl<AlberoCausali, PkId> implements AlberoCausaliService {

    private AlberoCausaliDAO alberoCausaliDAO;
    private AlberoContiService alberoContiService;

    @Autowired
    public void setAlberoCausaliDAO(AlberoCausaliDAO alberoCausaliDAO) {

	this.alberoCausaliDAO = alberoCausaliDAO;
    }

    @Autowired
    public void setAlberoContiService(AlberoContiService alberoContiService) {

	this.alberoContiService = alberoContiService;
    }

    @Override
    protected Class<AlberoCausali> getEntityClass() {

	return AlberoCausali.class;
    }

    @Override
    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_ALBEROCAUSALI" })
    public void delete(AlberoCausali entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    alberoCausaliDAO.delete(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ALBEROCAUSALI" })
    public List<AlberoCausali> findAll(Integer firstResult, Integer maxResult) {

	return alberoCausaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_ALBEROCAUSALI" })
    public AlberoCausali findById(PkId id) {

	return alberoCausaliDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_ALBEROCAUSALI" })
    public void insert(AlberoCausali entity) {

	if (validateEntity(entity)) {
	    alberoCausaliDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_ALBEROCAUSALI" })
    public void update(AlberoCausali entity) {

	if (validateEntity(entity)) {
	    alberoCausaliDAO.update(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_ALBEROCAUSALI" })
    public List<AlberoCausali> findByAlberoProc(AlberoCausali entity) {

	return alberoCausaliDAO.findByAlberoProc(entity);
    }

    protected boolean isDeleteAllowed(AlberoCausali entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    protected void childDelete(AlberoCausali entity) {

	Set<AlberoConti> alberocontis = entity.getAlberoContis();
	if (!alberocontis.isEmpty()) {
	    for (AlberoConti alberoconti : alberocontis) {
		alberoContiService.delete(alberoconti);
	    }
	}
    }
}
