package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiCampiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiCampiBase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoVisuraContestiCampiBaseService;

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
public class FoVisuraContestiCampiBaseServiceImpl extends BaseServiceImpl<FoVisuraContestiCampiBase, PkId> implements
	FoVisuraContestiCampiBaseService {

    private FoVisuraContestiCampiBaseDAO fovisuracontesticampibaseDAO;

    @Autowired
    public void setFoVisuraContestiCampiBaseDAO(FoVisuraContestiCampiBaseDAO fovisuracontesticampibaseDAO) {

	this.fovisuracontesticampibaseDAO = fovisuracontesticampibaseDAO;
    }

    @Override
    protected Class<FoVisuraContestiCampiBase> getEntityClass() {

	return FoVisuraContestiCampiBase.class;
    }

    @Override
    public List<FoVisuraContestiCampiBase> findAll(Integer firstResult, Integer maxResult) {

	return fovisuracontesticampibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoVisuraContestiCampiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracontesticampibaseDAO.insert(entity);
	}
    }

    @Override
    public FoVisuraContestiCampiBase findById(PkId id) {

	return fovisuracontesticampibaseDAO.findById(id);
    }

    @Override
    public void update(FoVisuraContestiCampiBase entity) {

	if (validateEntity(entity)) {
	    fovisuracontesticampibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(FoVisuraContestiCampiBase entity) {

	if (isDeleteAllowed(entity)) {
	    fovisuracontesticampibaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoVisuraContestiCampiBase entity) {

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
