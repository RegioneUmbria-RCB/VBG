package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OCausaliriduzionitService;

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
public class OCausaliriduzionitServiceImpl extends BaseServiceImpl<OCausaliriduzionit, PkId> implements OCausaliriduzionitService {

    private OCausaliriduzionitDAO ocausaliriduzionitDAO;

    @Autowired
    public void setOCausaliriduzionitDAO(OCausaliriduzionitDAO ocausaliriduzionitDAO) {

	this.ocausaliriduzionitDAO = ocausaliriduzionitDAO;
    }

    @Override
    protected Class<OCausaliriduzionit> getEntityClass() {

	return OCausaliriduzionit.class;
    }

    @Override
    public List<OCausaliriduzionit> findAll(Integer firstResult, Integer maxResult) {

	return ocausaliriduzionitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OCausaliriduzionit entity) {

	if (validateEntity(entity)) {
	    ocausaliriduzionitDAO.insert(entity);
	}
    }

    @Override
    public OCausaliriduzionit findById(PkId id) {

	return ocausaliriduzionitDAO.findById(id);
    }

    @Override
    public void update(OCausaliriduzionit entity) {

	if (validateEntity(entity)) {
	    ocausaliriduzionitDAO.update(entity);
	}
    }

    @Override
    public void delete(OCausaliriduzionit entity) {

	if (isDeleteAllowed(entity)) {
	    ocausaliriduzionitDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OCausaliriduzionit entity) {

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
