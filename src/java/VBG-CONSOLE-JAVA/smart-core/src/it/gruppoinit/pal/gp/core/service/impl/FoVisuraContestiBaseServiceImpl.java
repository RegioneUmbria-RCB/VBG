package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;
import it.gruppoinit.pal.gp.core.service.FoVisuraContestiBaseService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoVisuraContestiBaseServiceImpl extends BaseServiceImpl<FoVisuraContestiBase, String> implements FoVisuraContestiBaseService {

    private FoVisuraContestiBaseDAO fovisuracontestibaseDAO;

    @Autowired
    public void setFoVisuraContestiBaseDAO(FoVisuraContestiBaseDAO fovisuracontestibaseDAO) {

	this.fovisuracontestibaseDAO = fovisuracontestibaseDAO;
    }

    @Override
    protected Class<FoVisuraContestiBase> getEntityClass() {

	return FoVisuraContestiBase.class;
    }

    @Override
    public List<FoVisuraContestiBase> findAll(Integer firstResult, Integer maxResult) {

	return fovisuracontestibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoVisuraContestiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracontestibaseDAO.insert(entity);
	}
    }

    @Override
    public FoVisuraContestiBase findById(String id) {

	return fovisuracontestibaseDAO.findById(id);
    }

    @Override
    public void update(FoVisuraContestiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracontestibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(FoVisuraContestiBase entity) {

	if (isDeleteAllowed(entity)) {
	    fovisuracontestibaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoVisuraContestiBase entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
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
