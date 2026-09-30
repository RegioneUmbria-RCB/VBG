package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeTempisticaDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.IstanzeTempisticaService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzeTempisticaServiceImpl extends BaseServiceImpl<IstanzeTempistica, PkId> implements IstanzeTempisticaService {

    private IstanzeTempisticaDAO istanzetempisticaDAO;

    @Autowired
    public void setIstanzeTempisticaDAO(IstanzeTempisticaDAO istanzetempisticaDAO) {

	this.istanzetempisticaDAO = istanzetempisticaDAO;
    }

    @Override
    protected Class<IstanzeTempistica> getEntityClass() {

	return IstanzeTempistica.class;
    }

    @Override
    public List<IstanzeTempistica> findAll(Integer firstResult, Integer maxResult) {

	return istanzetempisticaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeTempistica entity) {

	if (validateEntity(entity)) {
	    istanzetempisticaDAO.insert(entity);
	}
    }

    @Override
    public IstanzeTempistica findById(PkId id) {

	return istanzetempisticaDAO.findById(id);
    }

    @Override
    public void update(IstanzeTempistica entity) {

	if (validateEntity(entity)) {
	    istanzetempisticaDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeTempistica entity) {

	if (isDeleteAllowed(entity)) {
	    istanzetempisticaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzeTempistica entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
