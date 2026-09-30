package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcItabella4DAO;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcItabella4Service;

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
public class CcItabella4ServiceImpl extends BaseServiceImpl<CcItabella4, PkId> implements CcItabella4Service {

    private CcItabella4DAO ccitabella4DAO;

    @Autowired
    public void setCcItabella4DAO(CcItabella4DAO ccitabella4DAO) {

	this.ccitabella4DAO = ccitabella4DAO;
    }

    @Override
    protected Class<CcItabella4> getEntityClass() {

	return CcItabella4.class;
    }

    @Override
    public List<CcItabella4> findAll(Integer firstResult, Integer maxResult) {

	return ccitabella4DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcItabella4 entity) {

	if (validateEntity(entity)) {
	    ccitabella4DAO.insert(entity);
	}
    }

    @Override
    public CcItabella4 findById(PkId id) {

	return ccitabella4DAO.findById(id);
    }

    @Override
    public void update(CcItabella4 entity) {

	if (validateEntity(entity)) {
	    ccitabella4DAO.update(entity);
	}
    }

    @Override
    public void delete(CcItabella4 entity) {

	if (isDeleteAllowed(entity)) {
	    ccitabella4DAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcItabella4 entity) {

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
