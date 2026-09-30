package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionirDAO;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionirService;

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
public class CcCausaliriduzionirServiceImpl extends BaseServiceImpl<CcCausaliriduzionir, PkId> implements CcCausaliriduzionirService {

    private CcCausaliriduzionirDAO cccausaliriduzionirDAO;

    @Autowired
    public void setCcCausaliriduzionirDAO(CcCausaliriduzionirDAO cccausaliriduzionirDAO) {

	this.cccausaliriduzionirDAO = cccausaliriduzionirDAO;
    }

    @Override
    protected Class<CcCausaliriduzionir> getEntityClass() {

	return CcCausaliriduzionir.class;
    }

    @Override
    public List<CcCausaliriduzionir> findAll(Integer firstResult, Integer maxResult) {

	return cccausaliriduzionirDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcCausaliriduzionir entity) {

	if (validateEntity(entity)) {
	    cccausaliriduzionirDAO.insert(entity);
	}
    }

    @Override
    public CcCausaliriduzionir findById(PkId id) {

	return cccausaliriduzionirDAO.findById(id);
    }

    @Override
    public void update(CcCausaliriduzionir entity) {

	if (validateEntity(entity)) {
	    cccausaliriduzionirDAO.update(entity);
	}
    }

    @Override
    public void delete(CcCausaliriduzionir entity) {

	if (isDeleteAllowed(entity)) {
	    cccausaliriduzionirDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcCausaliriduzionir entity) {

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
