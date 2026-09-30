package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;
import it.gruppoinit.pal.gp.core.service.FoArjStepsBaseService;

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
public class FoArjStepsBaseServiceImpl extends BaseServiceImpl<FoArjStepsBase, String> implements FoArjStepsBaseService {

    private FoArjStepsBaseDAO foarjstepsbaseDAO;

    @Autowired
    public void setFoArjStepsBaseDAO(FoArjStepsBaseDAO foarjstepsbaseDAO) {

	this.foarjstepsbaseDAO = foarjstepsbaseDAO;
    }

    @Override
    protected Class<FoArjStepsBase> getEntityClass() {

	return FoArjStepsBase.class;
    }

    @Override
    public List<FoArjStepsBase> findAll(Integer firstResult, Integer maxResult) {

	return foarjstepsbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjStepsBase entity) {

	if (validateEntity(entity)) {
	    foarjstepsbaseDAO.insert(entity);
	}
    }

    @Override
    public FoArjStepsBase findById(String id) {

	return foarjstepsbaseDAO.findById(id);
    }

    @Override
    public void update(FoArjStepsBase entity) {

	if (validateEntity(entity)) {
	    foarjstepsbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(FoArjStepsBase entity) {

	if (isDeleteAllowed(entity)) {
	    foarjstepsbaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoArjStepsBase entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
