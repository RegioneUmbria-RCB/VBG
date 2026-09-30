package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloTcontributoDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontribattivService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloTcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcolotcontributoRiduzService;

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
public class CcIcalcoloTcontributoServiceImpl extends BaseServiceImpl<CcIcalcoloTcontributo, PkId> implements CcIcalcoloTcontributoService {

    private CcIcalcoloDcontributoService ccIcalcoloDcontributoService;

    @Autowired
    public void setCcIcalcoloDcontributoService(CcIcalcoloDcontributoService ccIcalcoloDcontributoService) {

	this.ccIcalcoloDcontributoService = ccIcalcoloDcontributoService;
    }

    private CcIcalcoloDcontribattivService ccIcalcoloDcontribattivService;

    @Autowired
    public void setCcIcalcoloDcontribattivService(CcIcalcoloDcontribattivService ccIcalcoloDcontribattivService) {

	this.ccIcalcoloDcontribattivService = ccIcalcoloDcontribattivService;
    }

    private CcIcalcolotcontributoRiduzService ccIcalcolotcontributoRiduzService;

    @Autowired
    public void setCcIcalcolotcontributoRiduzService(CcIcalcolotcontributoRiduzService ccIcalcolotcontributoRiduzService) {

	this.ccIcalcolotcontributoRiduzService = ccIcalcolotcontributoRiduzService;
    }

    private CcIcalcoloTcontributoDAO ccicalcolotcontributoDAO;

    @Autowired
    public void setCcIcalcoloTcontributoDAO(CcIcalcoloTcontributoDAO ccicalcolotcontributoDAO) {

	this.ccicalcolotcontributoDAO = ccicalcolotcontributoDAO;
    }

    private CcIcalcoliService ccIcalcoliService;

    @Autowired
    public void setCcIcalcoliService(CcIcalcoliService ccIcalcoliService) {

	this.ccIcalcoliService = ccIcalcoliService;
    }

    @Override
    protected Class<CcIcalcoloTcontributo> getEntityClass() {

	return CcIcalcoloTcontributo.class;
    }

    @Override
    public List<CcIcalcoloTcontributo> findAll(Integer firstResult, Integer maxResult) {

	return ccicalcolotcontributoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcIcalcoloTcontributo entity) {

	if (validateEntity(entity)) {
	    ccicalcolotcontributoDAO.insert(entity);
	}
    }

    @Override
    public CcIcalcoloTcontributo findById(PkId id) {

	return ccicalcolotcontributoDAO.findById(id);
    }

    @Override
    public void update(CcIcalcoloTcontributo entity) {

	if (validateEntity(entity)) {
	    ccicalcolotcontributoDAO.update(entity);
	}
    }

    @Override
    public void delete(CcIcalcoloTcontributo entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    ccicalcolotcontributoDAO.delete(entity);
	    if (entity.getCcIcalcoli() != null) {
		ccIcalcoliService.delete(entity.getCcIcalcoli());
	    }
	}
    }

    @Override
    protected void childDelete(CcIcalcoloTcontributo entity) {

	Set<CcIcalcolotcontributoRiduz> ccIcalcolotcontributoRiduzs = entity.getCcIcalcolotcontributoRiduzs();
	for (CcIcalcolotcontributoRiduz ccIcalcolotcontributoRiduz : ccIcalcolotcontributoRiduzs) {
	    ccIcalcolotcontributoRiduzService.delete(ccIcalcolotcontributoRiduz);
	}
	Set<CcIcalcoloDcontribattiv> ccIcalcoloDcontribattivs = entity.getCcIcalcoloDcontribattivs();
	for (CcIcalcoloDcontribattiv ccIcalcoloDcontribattiv : ccIcalcoloDcontribattivs) {
	    ccIcalcoloDcontribattivService.delete(ccIcalcoloDcontribattiv);
	}
	Set<CcIcalcoloDcontributo> ccIcalcoloDcontributos = entity.getCcIcalcoloDcontributos();
	for (CcIcalcoloDcontributo ccIcalcoloDcontributo : ccIcalcoloDcontributos) {
	    ccIcalcoloDcontributoService.delete(ccIcalcoloDcontributo);
	}
    }

    protected boolean isDeleteAllowed(CcIcalcoloTcontributo entity) {

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
