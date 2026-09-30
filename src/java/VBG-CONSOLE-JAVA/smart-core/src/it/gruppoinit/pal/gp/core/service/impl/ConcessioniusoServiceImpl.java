package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ConcessioniusoDAO;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class ConcessioniusoServiceImpl extends BaseServiceImpl<Concessioniuso, PkId> implements ConcessioniusoService {

    private ConcessioniusoDAO concessioniusoDAO;

    @Autowired
    public void setConcessioniusoDAO(ConcessioniusoDAO concessioniusoDAO) {

	this.concessioniusoDAO = concessioniusoDAO;
    }

    @Override
    protected Class<Concessioniuso> getEntityClass() {

	return Concessioniuso.class;
    }

    @Override
    public List<Concessioniuso> findAll(Integer firstResult, Integer maxResult) {

	return concessioniusoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Concessioniuso entity) {

	if (validateEntity(entity)) {
	    concessioniusoDAO.insert(entity);
	}
    }

    @Override
    public Concessioniuso findById(PkId id) {

	return concessioniusoDAO.findById(id);
    }

    @Override
    public void update(Concessioniuso entity) {

	if (validateEntity(entity)) {
	    concessioniusoDAO.update(entity);
	}
    }

    @Override
    public void delete(Concessioniuso entity) {

	if (isDeleteAllowed(entity)) {
	    concessioniusoDAO.delete(entity);
	}
    }

    @Override
    public List<Concessioniuso> findByFilter(Concessioniuso entity) {

	return concessioniusoDAO.findByFilter(entity);
    }

    protected boolean isDeleteAllowed(Concessioniuso entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getMercatiUsos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_USO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
