package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;
import it.gruppoinit.pal.gp.core.service.FoVisuraCampiBaseService;

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
public class FoVisuraCampiBaseServiceImpl extends BaseServiceImpl<FoVisuraCampiBase, String> implements FoVisuraCampiBaseService {

    private FoVisuraCampiBaseDAO fovisuracampibaseDAO;

    @Autowired
    public void setFoVisuraCampiBaseDAO(FoVisuraCampiBaseDAO fovisuracampibaseDAO) {

	this.fovisuracampibaseDAO = fovisuracampibaseDAO;
    }

    @Override
    protected Class<FoVisuraCampiBase> getEntityClass() {

	return FoVisuraCampiBase.class;
    }

    @Override
    public List<FoVisuraCampiBase> findAll(Integer firstResult, Integer maxResult) {

	return fovisuracampibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoVisuraCampiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracampibaseDAO.insert(entity);
	}
    }

    @Override
    public FoVisuraCampiBase findById(String id) {

	return fovisuracampibaseDAO.findById(id);
    }

    @Override
    public void update(FoVisuraCampiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracampibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(FoVisuraCampiBase entity) {

	if (isDeleteAllowed(entity)) {
	    fovisuracampibaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoVisuraCampiBase entity) {

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
