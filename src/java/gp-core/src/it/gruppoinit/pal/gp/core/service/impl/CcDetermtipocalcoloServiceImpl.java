package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcDetermtipocalcoloDAO;
import it.gruppoinit.pal.gp.core.domain.CcDetermtipocalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcDetermtipocalcoloService;

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
public class CcDetermtipocalcoloServiceImpl extends BaseServiceImpl<CcDetermtipocalcolo, PkId> implements CcDetermtipocalcoloService {

    private CcDetermtipocalcoloDAO ccdetermtipocalcoloDAO;

    @Autowired
    public void setCcDetermtipocalcoloDAO(CcDetermtipocalcoloDAO ccdetermtipocalcoloDAO) {

	this.ccdetermtipocalcoloDAO = ccdetermtipocalcoloDAO;
    }

    @Override
    protected Class<CcDetermtipocalcolo> getEntityClass() {

	return CcDetermtipocalcolo.class;
    }

    @Override
    public List<CcDetermtipocalcolo> findAll(Integer firstResult, Integer maxResult) {

	return ccdetermtipocalcoloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcDetermtipocalcolo entity) {

	if (validateEntity(entity)) {
	    ccdetermtipocalcoloDAO.insert(entity);
	}
    }

    @Override
    public CcDetermtipocalcolo findById(PkId id) {

	return ccdetermtipocalcoloDAO.findById(id);
    }

    @Override
    public void update(CcDetermtipocalcolo entity) {

	if (validateEntity(entity)) {
	    ccdetermtipocalcoloDAO.update(entity);
	}
    }

    @Override
    public void delete(CcDetermtipocalcolo entity) {

	if (isDeleteAllowed(entity)) {
	    ccdetermtipocalcoloDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcDetermtipocalcolo entity) {

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
