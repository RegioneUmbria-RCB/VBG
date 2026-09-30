package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtAssegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtAssegnazioni;
import it.gruppoinit.pal.gp.core.service.ProtAssegnazioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class ProtAssegnazioniServiceImpl extends BaseServiceImpl<ProtAssegnazioni, PkId> implements ProtAssegnazioniService {

    private ProtAssegnazioniDAO protassegnazioniDAO;

    @Autowired
    public void setProtAssegnazioniDAO(ProtAssegnazioniDAO protassegnazioniDAO) {

	this.protassegnazioniDAO = protassegnazioniDAO;
    }

    @Override
    protected Class<ProtAssegnazioni> getEntityClass() {

	return ProtAssegnazioni.class;
    }

    @Override
    public List<ProtAssegnazioni> findAll(Integer firstResult, Integer maxResult) {

	return protassegnazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtAssegnazioni entity) {

	if (validateEntity(entity)) {
	    protassegnazioniDAO.insert(entity);
	}
    }

    @Override
    public ProtAssegnazioni findById(PkId id) {

	return protassegnazioniDAO.findById(id);
    }

    @Override
    public void update(ProtAssegnazioni entity) {

	if (validateEntity(entity)) {
	    protassegnazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtAssegnazioni entity) {

	if (isDeleteAllowed(entity)) {
	    protassegnazioniDAO.delete(entity);
	}
    }
}
