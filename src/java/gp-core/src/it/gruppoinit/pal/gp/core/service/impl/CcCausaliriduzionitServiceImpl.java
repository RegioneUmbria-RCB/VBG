package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionirService;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcCausaliriduzionitServiceImpl extends BaseServiceImpl<CcCausaliriduzionit, PkId> implements CcCausaliriduzionitService {

    private CcCausaliriduzionirService causaliriduzionirService;

    @Autowired
    public void setCausaliriduzionirService(CcCausaliriduzionirService causaliriduzionirService) {

	this.causaliriduzionirService = causaliriduzionirService;
    }

    private CcCausaliriduzionitDAO cccausaliriduzionitDAO;

    @Autowired
    public void setCcCausaliriduzionitDAO(CcCausaliriduzionitDAO cccausaliriduzionitDAO) {

	this.cccausaliriduzionitDAO = cccausaliriduzionitDAO;
    }

    @Override
    protected Class<CcCausaliriduzionit> getEntityClass() {

	return CcCausaliriduzionit.class;
    }

    @Override
    public List<CcCausaliriduzionit> findAll(Integer firstResult, Integer maxResult) {

	return cccausaliriduzionitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcCausaliriduzionit entity) {

	if (validateEntity(entity)) {
	    cccausaliriduzionitDAO.insert(entity);
	}
    }

    @Override
    public CcCausaliriduzionit findById(PkId id) {

	return cccausaliriduzionitDAO.findById(id);
    }

    @Override
    public void update(CcCausaliriduzionit entity) {

	if (validateEntity(entity)) {
	    cccausaliriduzionitDAO.update(entity);
	}
    }

    @Override
    public void delete(CcCausaliriduzionit entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    cccausaliriduzionitDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(CcCausaliriduzionit entity) {

	super.childDelete(entity);
	Set<CcCausaliriduzionir> causaliriduzionirs = entity.getCcCausaliriduzionirs();
	for (CcCausaliriduzionir ccCausaliriduzionir : causaliriduzionirs) {
	    causaliriduzionirService.delete(ccCausaliriduzionir);
	}
    }

    protected boolean isDeleteAllowed(CcCausaliriduzionit entity) {

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
