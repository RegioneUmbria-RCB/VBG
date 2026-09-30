package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcBasetipocalcoloDAO;
import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;
import it.gruppoinit.pal.gp.core.service.CcBasetipocalcoloService;

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
public class CcBasetipocalcoloServiceImpl extends BaseServiceImpl<CcBasetipocalcolo, String> implements CcBasetipocalcoloService {

    private CcBasetipocalcoloDAO ccbasetipocalcoloDAO;

    @Autowired
    public void setCcBasetipocalcoloDAO(CcBasetipocalcoloDAO ccbasetipocalcoloDAO) {

	this.ccbasetipocalcoloDAO = ccbasetipocalcoloDAO;
    }

    @Override
    protected Class<CcBasetipocalcolo> getEntityClass() {

	return CcBasetipocalcolo.class;
    }

    @Override
    public List<CcBasetipocalcolo> findAll(Integer firstResult, Integer maxResult) {

	return ccbasetipocalcoloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcBasetipocalcolo entity) {

	if (validateEntity(entity)) {
	    ccbasetipocalcoloDAO.insert(entity);
	}
    }

    @Override
    public CcBasetipocalcolo findById(String id) {

	return ccbasetipocalcoloDAO.findById(id);
    }

    @Override
    public void update(CcBasetipocalcolo entity) {

	if (validateEntity(entity)) {
	    ccbasetipocalcoloDAO.update(entity);
	}
    }

    @Override
    public void delete(CcBasetipocalcolo entity) {

	if (isDeleteAllowed(entity)) {
	    ccbasetipocalcoloDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcBasetipocalcolo entity) {

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
