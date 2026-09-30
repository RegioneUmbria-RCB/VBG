package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcItabella3DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcItabella3Service;

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
public class CcItabella3ServiceImpl extends BaseServiceImpl<CcItabella3, PkId> implements CcItabella3Service {

    private CcItabella3DAO ccitabella3DAO;

    @Autowired
    public void setCcItabella3DAO(CcItabella3DAO ccitabella3DAO) {

	this.ccitabella3DAO = ccitabella3DAO;
    }

    @Override
    protected Class<CcItabella3> getEntityClass() {

	return CcItabella3.class;
    }

    @Override
    public List<CcItabella3> findAll(Integer firstResult, Integer maxResult) {

	return ccitabella3DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcItabella3 entity) {

	if (validateEntity(entity)) {
	    ccitabella3DAO.insert(entity);
	}
    }

    @Override
    public CcItabella3 findById(PkId id) {

	return ccitabella3DAO.findById(id);
    }

    @Override
    public void update(CcItabella3 entity) {

	if (validateEntity(entity)) {
	    ccitabella3DAO.update(entity);
	}
    }

    @Override
    public void delete(CcItabella3 entity) {

	if (isDeleteAllowed(entity)) {
	    ccitabella3DAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcItabella3 entity) {

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
