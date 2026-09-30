package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoSottoscrizioniDAO;
import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoSottoscrizioniService;

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
public class FoSottoscrizioniServiceImpl extends BaseServiceImpl<FoSottoscrizioni, PkId> implements FoSottoscrizioniService {

    private FoSottoscrizioniDAO fosottoscrizioniDAO;

    @Autowired
    public void setFoSottoscrizioniDAO(FoSottoscrizioniDAO fosottoscrizioniDAO) {

	this.fosottoscrizioniDAO = fosottoscrizioniDAO;
    }

    @Override
    protected Class<FoSottoscrizioni> getEntityClass() {

	return FoSottoscrizioni.class;
    }

    @Override
    public List<FoSottoscrizioni> findAll(Integer firstResult, Integer maxResult) {

	return fosottoscrizioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoSottoscrizioni entity) {

	if (validateEntity(entity)) {
	    fosottoscrizioniDAO.insert(entity);
	}
    }

    @Override
    public FoSottoscrizioni findById(PkId id) {

	return fosottoscrizioniDAO.findById(id);
    }

    @Override
    public void update(FoSottoscrizioni entity) {

	if (validateEntity(entity)) {
	    fosottoscrizioniDAO.update(entity);
	}
    }

    @Override
    public void delete(FoSottoscrizioni entity) {

	if (isDeleteAllowed(entity)) {
	    fosottoscrizioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoSottoscrizioni entity) {

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
