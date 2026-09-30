package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PuFormatiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;
import it.gruppoinit.pal.gp.core.service.PuFormatiService;

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
public class PuFormatiServiceImpl extends BaseServiceImpl<PuFormati, PkId> implements PuFormatiService {

    private PuFormatiDAO puformatiDAO;

    @Autowired
    public void setPuFormatiDAO(PuFormatiDAO puformatiDAO) {

	this.puformatiDAO = puformatiDAO;
    }

    @Override
    protected Class<PuFormati> getEntityClass() {

	return PuFormati.class;
    }

    @Override
    public List<PuFormati> findAll(Integer firstResult, Integer maxResult) {

	return puformatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(PuFormati entity) {

	if (validateEntity(entity)) {
	    puformatiDAO.insert(entity);
	}
    }

    @Override
    public PuFormati findById(PkId id) {

	return puformatiDAO.findById(id);
    }

    @Override
    public void update(PuFormati entity) {

	if (validateEntity(entity)) {
	    puformatiDAO.update(entity);
	}
    }

    @Override
    public void delete(PuFormati entity) {

	if (isDeleteAllowed(entity)) {
	    puformatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(PuFormati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
