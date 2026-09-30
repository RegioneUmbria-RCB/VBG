package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcItabella1DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcItabella1Service;

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
public class CcItabella1ServiceImpl extends BaseServiceImpl<CcItabella1, PkId> implements CcItabella1Service {

    private CcItabella1DAO ccitabella1DAO;

    @Autowired
    public void setCcItabella1DAO(CcItabella1DAO ccitabella1DAO) {

	this.ccitabella1DAO = ccitabella1DAO;
    }

    @Override
    protected Class<CcItabella1> getEntityClass() {

	return CcItabella1.class;
    }

    @Override
    public List<CcItabella1> findAll(Integer firstResult, Integer maxResult) {

	return ccitabella1DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcItabella1 entity) {

	if (validateEntity(entity)) {
	    ccitabella1DAO.insert(entity);
	}
    }

    @Override
    public CcItabella1 findById(PkId id) {

	return ccitabella1DAO.findById(id);
    }

    @Override
    public void update(CcItabella1 entity) {

	if (validateEntity(entity)) {
	    ccitabella1DAO.update(entity);
	}
    }

    @Override
    public void delete(CcItabella1 entity) {

	if (isDeleteAllowed(entity)) {
	    ccitabella1DAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcItabella1 entity) {

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
