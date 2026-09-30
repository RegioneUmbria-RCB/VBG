/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtAooresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.ProtAooresponsabili;
import it.gruppoinit.pal.gp.core.domain.ProtAooresponsabiliId;
import it.gruppoinit.pal.gp.core.service.ProtAooresponsabiliService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class ProtAooresponsabiliServiceImpl extends BaseServiceImpl<ProtAooresponsabili, ProtAooresponsabiliId> implements ProtAooresponsabiliService {

    private ProtAooresponsabiliDAO protAooresponsabiliDAO;

    @Autowired
    public void setProtAooresponsabiliDAO(ProtAooresponsabiliDAO protAooresponsabiliDAO) {

	this.protAooresponsabiliDAO = protAooresponsabiliDAO;
    }

    @Override
    protected Class<ProtAooresponsabili> getEntityClass() {

	return ProtAooresponsabili.class;
    }

    @Override
    public void delete(ProtAooresponsabili entity) {

	protAooresponsabiliDAO.delete(entity);
    }

    @Override
    public List<ProtAooresponsabili> findAll(Integer firstResult, Integer maxResult) {

	return protAooresponsabiliDAO.findAll(null, null);
    }

    @Override
    public ProtAooresponsabili findById(ProtAooresponsabiliId id) {

	return protAooresponsabiliDAO.findById(id);
    }

    @Override
    public void insert(ProtAooresponsabili entity) {

	if (validateEntity(entity)) {
	    protAooresponsabiliDAO.insert(entity);
	}
    }

    @Override
    public void update(ProtAooresponsabili entity) {

	if (validateEntity(entity)) {
	    protAooresponsabiliDAO.update(entity);
	}
    }
}
