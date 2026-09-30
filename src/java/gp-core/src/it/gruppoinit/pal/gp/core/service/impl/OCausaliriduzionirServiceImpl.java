package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OCausaliriduzionirDAO;
import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OCausaliriduzionirService;

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
public class OCausaliriduzionirServiceImpl extends BaseServiceImpl<OCausaliriduzionir, PkId> implements OCausaliriduzionirService {

    private OCausaliriduzionirDAO ocausaliriduzionirDAO;

    @Autowired
    public void setOCausaliriduzionirDAO(OCausaliriduzionirDAO ocausaliriduzionirDAO) {

	this.ocausaliriduzionirDAO = ocausaliriduzionirDAO;
    }

    @Override
    protected Class<OCausaliriduzionir> getEntityClass() {

	return OCausaliriduzionir.class;
    }

    @Override
    public List<OCausaliriduzionir> findAll(Integer firstResult, Integer maxResult) {

	return ocausaliriduzionirDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OCausaliriduzionir entity) {

	if (validateEntity(entity)) {
	    ocausaliriduzionirDAO.insert(entity);
	}
    }

    @Override
    public OCausaliriduzionir findById(PkId id) {

	return ocausaliriduzionirDAO.findById(id);
    }

    @Override
    public void update(OCausaliriduzionir entity) {

	if (validateEntity(entity)) {
	    ocausaliriduzionirDAO.update(entity);
	}
    }

    @Override
    public void delete(OCausaliriduzionir entity) {

	if (isDeleteAllowed(entity)) {
	    ocausaliriduzionirDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OCausaliriduzionir entity) {

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
