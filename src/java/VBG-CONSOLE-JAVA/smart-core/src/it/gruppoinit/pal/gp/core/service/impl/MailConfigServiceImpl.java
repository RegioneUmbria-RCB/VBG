package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MailConfigDAO;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigId;
import it.gruppoinit.pal.gp.core.service.MailConfigService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MailConfigServiceImpl extends BaseServiceImpl<MailConfig, MailConfigId> implements MailConfigService {

    private MailConfigDAO mailconfigDAO;

    @Autowired
    public void setMailConfigDAO(MailConfigDAO mailconfigDAO) {

	this.mailconfigDAO = mailconfigDAO;
    }

    @Override
    protected Class<MailConfig> getEntityClass() {

	return MailConfig.class;
    }

    @Override
    public List<MailConfig> findAll(Integer firstResult, Integer maxResult) {

	return mailconfigDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MailConfig entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    mailconfigDAO.insert(entity);
	}
    }

    @Override
    public MailConfig findById(MailConfigId id) {

	return mailconfigDAO.findById(id);
    }

    @Override
    public void update(MailConfig entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    mailconfigDAO.update(entity);
	}
    }

    private void dataIntegration(MailConfig entity, boolean insert) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'oggetto mailConfig passato è nullo");
	}
	if (entity.getUseauthentication() == null)
	    entity.setUseauthentication(Boolean.valueOf(false));
	if (entity.getUsessl() == null)
	    entity.setUsessl(new Integer(0));
	if (entity.getInUseauthentication() == null)
	    entity.setInUseauthentication(Boolean.valueOf(false));
	if (entity.getInUsessl() == null)
	    entity.setInUsessl(new Integer(0));
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MailConfig entity) {

    }

    @Override
    public void delete(MailConfig entity) {

	if (isDeleteAllowed(entity)) {
	    mailconfigDAO.delete(entity);
	}
    }

    @Override
    public MailConfig findMailConfig() {

	MailConfigId id = new MailConfigId();
	MailConfig mailConfig = this.findById(id);
	if (mailConfig == null) {
	    id.setSoftware(WebConstants.SOFTWARE_TT);
	    mailConfig = this.findById(id);
	}
	return mailConfig;
    }
}
