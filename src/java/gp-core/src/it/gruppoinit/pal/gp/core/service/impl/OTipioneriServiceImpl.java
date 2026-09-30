package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OTipioneriDAO;
import it.gruppoinit.pal.gp.core.domain.OTipioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OTipioneriService;

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
public class OTipioneriServiceImpl extends BaseServiceImpl<OTipioneri, PkId> implements OTipioneriService {

    private OTipioneriDAO otipioneriDAO;

    @Autowired
    public void setOTipioneriDAO(OTipioneriDAO otipioneriDAO) {

	this.otipioneriDAO = otipioneriDAO;
    }

    @Override
    protected Class<OTipioneri> getEntityClass() {

	return OTipioneri.class;
    }

    @Override
    public List<OTipioneri> findAll(Integer firstResult, Integer maxResult) {

	return otipioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OTipioneri entity) {

	if (validateEntity(entity)) {
	    otipioneriDAO.insert(entity);
	}
    }

    @Override
    public OTipioneri findById(PkId id) {

	return otipioneriDAO.findById(id);
    }

    @Override
    public void update(OTipioneri entity) {

	if (validateEntity(entity)) {
	    otipioneriDAO.update(entity);
	}
    }

    @Override
    public void delete(OTipioneri entity) {

	if (isDeleteAllowed(entity)) {
	    otipioneriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OTipioneri entity) {

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
