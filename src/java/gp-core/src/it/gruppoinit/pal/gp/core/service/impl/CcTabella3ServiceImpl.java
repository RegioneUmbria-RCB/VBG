package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcTabella3DAO;
import it.gruppoinit.pal.gp.core.domain.CcTabella3;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcTabella3Service;

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
public class CcTabella3ServiceImpl extends BaseServiceImpl<CcTabella3, PkId> implements CcTabella3Service {

    private CcTabella3DAO cctabella3DAO;

    @Autowired
    public void setCcTabella3DAO(CcTabella3DAO cctabella3DAO) {

	this.cctabella3DAO = cctabella3DAO;
    }

    @Override
    protected Class<CcTabella3> getEntityClass() {

	return CcTabella3.class;
    }

    @Override
    public List<CcTabella3> findAll(Integer firstResult, Integer maxResult) {

	return cctabella3DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcTabella3 entity) {

	if (validateEntity(entity)) {
	    cctabella3DAO.insert(entity);
	}
    }

    @Override
    public CcTabella3 findById(PkId id) {

	return cctabella3DAO.findById(id);
    }

    @Override
    public void update(CcTabella3 entity) {

	if (validateEntity(entity)) {
	    cctabella3DAO.update(entity);
	}
    }

    @Override
    public void delete(CcTabella3 entity) {

	if (isDeleteAllowed(entity)) {
	    cctabella3DAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcTabella3 entity) {

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
