package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SitCartechDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SitCartech;
import it.gruppoinit.pal.gp.core.service.SitCartechService;

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
public class SitCartechServiceImpl extends BaseServiceImpl<SitCartech, PkId> implements SitCartechService {

    private SitCartechDAO sitcartechDAO;

    @Autowired
    public void setSitCartechDAO(SitCartechDAO sitcartechDAO) {

	this.sitcartechDAO = sitcartechDAO;
    }

    @Override
    protected Class<SitCartech> getEntityClass() {

	return SitCartech.class;
    }

    @Override
    public List<SitCartech> findAll(Integer firstResult, Integer maxResult) {

	return sitcartechDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(SitCartech entity) {

	if (validateEntity(entity)) {
	    sitcartechDAO.insert(entity);
	}
    }

    @Override
    public SitCartech findById(PkId id) {

	return sitcartechDAO.findById(id);
    }

    @Override
    public void update(SitCartech entity) {

	if (validateEntity(entity)) {
	    sitcartechDAO.update(entity);
	}
    }

    @Override
    public void delete(SitCartech entity) {

	if (isDeleteAllowed(entity)) {
	    sitcartechDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(SitCartech entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
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
