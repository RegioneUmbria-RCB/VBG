package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDettagliotDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliorService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliotService;

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
public class CcIcalcoliDettagliotServiceImpl extends BaseServiceImpl<CcIcalcoliDettagliot, PkId> implements CcIcalcoliDettagliotService {

    private CcIcalcoliDettagliotDAO ccicalcolidettagliotDAO;

    @Autowired
    public void setCcIcalcoliDettagliotDAO(CcIcalcoliDettagliotDAO ccicalcolidettagliotDAO) {

	this.ccicalcolidettagliotDAO = ccicalcolidettagliotDAO;
    }

    private CcIcalcoliDettagliorService ccIcalcoliDettagliorService;

    @Autowired
    public void setCcIcalcoliDettagliorService(CcIcalcoliDettagliorService ccIcalcoliDettagliorService) {

	this.ccIcalcoliDettagliorService = ccIcalcoliDettagliorService;
    }

    @Override
    protected Class<CcIcalcoliDettagliot> getEntityClass() {

	return CcIcalcoliDettagliot.class;
    }

    @Override
    public List<CcIcalcoliDettagliot> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolidettagliotDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcoliDettagliot entity) {

	if (validateEntity(entity)) {
	    ccicalcolidettagliotDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcoliDettagliot findById(PkId id) {

	return ccicalcolidettagliotDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoliDettagliot entity) {

	if (validateEntity(entity)) {
	    ccicalcolidettagliotDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcoliDettagliot entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    ccicalcolidettagliotDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(CcIcalcoliDettagliot entity) {

	Set<CcIcalcoliDettaglior> ccIcalcoliDettagliors = entity.getCcIcalcoliDettagliors();
	for (CcIcalcoliDettaglior ccIcalcoliDettaglior : ccIcalcoliDettagliors) {
	    ccIcalcoliDettagliorService.delete(ccIcalcoliDettaglior);
	}
    }

    protected boolean isDeleteAllowed(CcIcalcoliDettagliot entity) {

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
