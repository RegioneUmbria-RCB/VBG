package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CcTipointerventoDAO;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloDcontributoService;
import it.gruppoinit.pal.gp.core.service.CcTipointerventoService;

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
public class CcTipointerventoServiceImpl extends BaseServiceImpl<CcTipointervento, PkId> implements CcTipointerventoService {

    private CcIcalcoloDcontributoService ccIcalcoloDcontributoService;

    @Autowired
    public void setCcIcalcoloDcontributoService(CcIcalcoloDcontributoService ccIcalcoloDcontributoService) {

	this.ccIcalcoloDcontributoService = ccIcalcoloDcontributoService;
    }

    private CcCoeffcontributoService ccCoeffcontributoService;

    @Autowired
    public void setCcCoeffcontributoService(CcCoeffcontributoService ccCoeffcontributoService) {

	this.ccCoeffcontributoService = ccCoeffcontributoService;
    }

    private CcTipointerventoDAO cctipointerventoDAO;

    @Autowired
    public void setCcTipointerventoDAO(CcTipointerventoDAO cctipointerventoDAO) {

	this.cctipointerventoDAO = cctipointerventoDAO;
    }

    @Override
    protected Class<CcTipointervento> getEntityClass() {

	return CcTipointervento.class;
    }

    @Override
    public List<CcTipointervento> findAll(Integer firstResult, Integer maxResult) {

	return cctipointerventoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcTipointervento entity) {

	if (validateEntity(entity)) {
	    cctipointerventoDAO.insert(entity);
	}
    }

    @Override
    public CcTipointervento findById(PkId id) {

	return cctipointerventoDAO.findById(id);
    }

    @Override
    public void update(CcTipointervento entity) {

	if (validateEntity(entity)) {
	    cctipointerventoDAO.update(entity);
	}
    }

    @Override
    public void delete(CcTipointervento entity) {

	if (isDeleteAllowed(entity)) {
	    cctipointerventoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcTipointervento entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	if(entity.getCcIcalcoloDcontributos().size()>0){
	if (ccIcalcoloDcontributoService.existRecordByCcTipointervento(entity)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_I_CALCOLO_DCONTRIBUTO", null));
	}
//	if (entity.getCcCoeffcontributos().size() > 0) {
	  if (ccCoeffcontributoService.existRecordByCcTipointervento(entity)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CC_COEFF_CONTRIBUTO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
