package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzepeopledDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.IstanzepeopledService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class IstanzepeopledServiceImpl extends BaseServiceImpl<Istanzepeopled, PkId> implements IstanzepeopledService {

    private IstanzepeopledDAO istanzepeopledDAO;

    @Autowired
    public void setIstanzepeopledDAO(IstanzepeopledDAO istanzepeopledDAO) {

	this.istanzepeopledDAO = istanzepeopledDAO;
    }

    @Override
    protected Class<Istanzepeopled> getEntityClass() {

	return Istanzepeopled.class;
    }

    @Override
    public List<Istanzepeopled> findAll(Integer firstResult, Integer maxResult) {

	return istanzepeopledDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzepeopled entity) {

	if (validateEntity(entity)) {
	    istanzepeopledDAO.insert(entity);
	}
    }

    @Override
    public Istanzepeopled findById(PkId id) {

	return istanzepeopledDAO.findById(id);
    }

    @Override
    public void update(Istanzepeopled entity) {

	if (validateEntity(entity)) {
	    istanzepeopledDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzepeopled entity) {

	if (isDeleteAllowed(entity)) {
	    istanzepeopledDAO.delete(entity);
	}
    }

    @Override
    public Istanzepeopled findByIstanza(Istanze istanze) {

	return istanzepeopledDAO.findByIstanza(istanze);
    }
}
