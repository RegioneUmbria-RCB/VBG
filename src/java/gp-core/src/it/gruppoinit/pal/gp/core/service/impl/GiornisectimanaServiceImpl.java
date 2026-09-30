package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GiornisectimanaDAO;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiornisectimanaServiceImpl extends BaseServiceImpl<Giornisettimana, Integer> implements GiornisectimanaService {

    private GiornisectimanaDAO giornisectimanaDAO;

    @Autowired
    public void setGiornisectimanaDAO(GiornisectimanaDAO giornisectimanaDAO) {

	this.giornisectimanaDAO = giornisectimanaDAO;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_GIORNISETTIMANA" })
    public void delete(Giornisettimana entity) {

	giornisectimanaDAO.delete(entity);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_GIORNISETTIMANA" })
    public List<Giornisettimana> findAll(Integer firstResult, Integer maxResult) {

	return giornisectimanaDAO.findAll(null, null);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_GIORNISETTIMANA" })
    public Giornisettimana findById(Integer id) {

	return giornisectimanaDAO.findById(id);
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_GIORNISETTIMANA" })
    public void insert(Giornisettimana entity) {

	if (validateEntity(entity)) {
	    giornisectimanaDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_GIORNISETTIMANA" })
    public void update(Giornisettimana entity) {

	if (validateEntity(entity)) {
	    giornisectimanaDAO.update(entity);
	}
    }

    @Override
    protected Class<Giornisettimana> getEntityClass() {

	return Giornisettimana.class;
    }
}
