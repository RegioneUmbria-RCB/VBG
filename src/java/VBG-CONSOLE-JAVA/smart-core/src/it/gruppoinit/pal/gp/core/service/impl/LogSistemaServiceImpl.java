package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LogSistemaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.LogSistema;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LogSistemaService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LogSistemaServiceImpl extends BaseServiceImpl<LogSistema, PkId> implements LogSistemaService {

    private LogSistemaDAO logSistemaDAO;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setLogSistemaDAO(LogSistemaDAO logSistemaDAO) {

	this.logSistemaDAO = logSistemaDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public void insert(LogSistema entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    logSistemaDAO.insert(entity);
	}
    }

    private void dataIntegration(LogSistema entity) {

	if (entity == null) {
	    entity = new LogSistema();
	}
	if (entity.getDataEvento() == null) {
	    entity.setDataEvento(new Date());
	}
	if (StringUtils.isBlank(entity.getUtenteConnesso())) {
	    UserDetails o = userSecurityService.getCurrentlyAuthenticatedUser();
	    if (o != null) {
		entity.setUtenteConnesso(o.getUsername());
	    }
	}
	if (StringUtils.isBlank(entity.getCodiceEvento())) {
	    entity.setCodiceEvento(LogSistemaService.CODICI_EVENTO.NON_DEFINITO.name());
	}
	if (StringUtils.isBlank(entity.getDescrizioneEvento())) {
	    if (StringUtils.isNotBlank(entity.getEvento())) {
		entity.setDescrizioneEvento(StringUtils.left(entity.getEvento(), 3999));
	    }
	}
	if (StringUtils.isBlank(entity.getEvento())) {
	    if (StringUtils.isNotBlank(entity.getDescrizioneEvento())) {
		entity.setEvento(entity.getDescrizioneEvento());
	    }
	}
	if (StringUtils.isNotBlank(entity.getDescrizioneEvento()) && entity.getDescrizioneEvento().length() > 4000) {
	    entity.setDescrizioneEvento(StringUtils.left(entity.getDescrizioneEvento(), 3999));
	}
    }

    @Override
    public void update(LogSistema entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    logSistemaDAO.update(entity);
	}
    }

    @Override
    public void delete(LogSistema entity) {

	logSistemaDAO.delete(entity);
    }

    @Override
    public List<LogSistema> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public LogSistema findById(PkId id) {

	return logSistemaDAO.findById(id);
    }

    @Override
    protected Class<LogSistema> getEntityClass() {

	return LogSistema.class;
    }

    @Override
    public void registraLogSistemaInNewTransaction(LogSistema entity) {

	this.insert(entity);
    }
}
