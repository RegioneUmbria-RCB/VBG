package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcItabella2DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcItabella2Service;

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
public class CcItabella2ServiceImpl extends BaseServiceImpl<CcItabella2, PkId> implements CcItabella2Service {

    private CcItabella2DAO ccitabella2DAO;

    @Autowired
    public void setCcItabella2DAO(CcItabella2DAO ccitabella2DAO) {

	this.ccitabella2DAO = ccitabella2DAO;
    }

    @Override
    protected Class<CcItabella2> getEntityClass() {

	return CcItabella2.class;
    }

    @Override
    public List<CcItabella2> findAll(Integer firstResult, Integer maxResult) {

	return ccitabella2DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcItabella2 entity) {

	if (validateEntity(entity)) {
	    ccitabella2DAO.insert(entity);
	}
    }

    @Override
    public CcItabella2 findById(PkId id) {

	return ccitabella2DAO.findById(id);
    }

    @Override
    public void update(CcItabella2 entity) {

	if (validateEntity(entity)) {
	    ccitabella2DAO.update(entity);
	}
    }

    @Override
    public void delete(CcItabella2 entity) {

	if (isDeleteAllowed(entity)) {
	    ccitabella2DAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcItabella2 entity) {

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
