package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcClassisuperficiDAO;
import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcClassisuperficiService;

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
public class CcClassisuperficiServiceImpl extends BaseServiceImpl<CcClassisuperfici, PkId> implements CcClassisuperficiService {

    private CcClassisuperficiDAO ccclassisuperficiDAO;

    @Autowired
    public void setCcClassisuperficiDAO(CcClassisuperficiDAO ccclassisuperficiDAO) {

	this.ccclassisuperficiDAO = ccclassisuperficiDAO;
    }

    @Override
    protected Class<CcClassisuperfici> getEntityClass() {

	return CcClassisuperfici.class;
    }

    @Override
    public List<CcClassisuperfici> findAll(Integer firstResult, Integer maxResult) {

	return ccclassisuperficiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcClassisuperfici entity) {

	if (validateEntity(entity)) {
	    ccclassisuperficiDAO.insert(entity);
	}
    }

    @Override
    public CcClassisuperfici findById(PkId id) {

	return ccclassisuperficiDAO.findById(id);
    }

    @Override
    public void update(CcClassisuperfici entity) {

	if (validateEntity(entity)) {
	    ccclassisuperficiDAO.update(entity);
	}
    }

    @Override
    public void delete(CcClassisuperfici entity) {

	if (isDeleteAllowed(entity)) {
	    ccclassisuperficiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcClassisuperfici entity) {

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
