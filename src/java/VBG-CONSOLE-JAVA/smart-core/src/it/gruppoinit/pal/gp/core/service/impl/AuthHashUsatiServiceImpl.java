package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AuthHashUsatiDAO;
import it.gruppoinit.pal.gp.core.domain.AuthHashUsati;
import it.gruppoinit.pal.gp.core.service.AuthHashUsatiService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthHashUsatiServiceImpl extends BaseServiceImpl<AuthHashUsati, String> implements AuthHashUsatiService {

    private AuthHashUsatiDAO authHashUsatiDAO;

    @Autowired
    public void setAuthHashUsatiDAO(AuthHashUsatiDAO authHashUsatiDAO) {

	this.authHashUsatiDAO = authHashUsatiDAO;
    }

    @Override
    public void insert(AuthHashUsati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    authHashUsatiDAO.insert(entity);
	}
    }

    private void dataIntegration(AuthHashUsati entity) {

	if (entity == null) {
	    throw new RuntimeException("non valido");
	}
	if (entity.getDataUso() == null) {
	    entity.setDataUso(Calendar.getInstance().getTime());
	}
    }

    @Override
    public void update(AuthHashUsati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    authHashUsatiDAO.update(entity);
	}
    }

    @Override
    public void delete(AuthHashUsati entity) {

	if (isDeleteAllowed(entity)) {
	    authHashUsatiDAO.delete(entity);
	}
    }

    @Override
    public List<AuthHashUsati> findAll(Integer firstResult, Integer maxResult) {

	return authHashUsatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AuthHashUsati findById(String id) {

	return authHashUsatiDAO.findById(id);
    }

    @Override
    protected Class<AuthHashUsati> getEntityClass() {

	return AuthHashUsati.class;
    }
}
