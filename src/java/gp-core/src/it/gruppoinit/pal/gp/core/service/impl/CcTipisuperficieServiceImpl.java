package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcTipisuperficieDAO;
import it.gruppoinit.pal.gp.core.domain.CcTipisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcTipisuperficieService;

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
public class CcTipisuperficieServiceImpl extends BaseServiceImpl<CcTipisuperficie, PkId> implements CcTipisuperficieService {

    private CcTipisuperficieDAO cctipisuperficieDAO;

    @Autowired
    public void setCcTipisuperficieDAO(CcTipisuperficieDAO cctipisuperficieDAO) {

	this.cctipisuperficieDAO = cctipisuperficieDAO;
    }

    @Override
    protected Class<CcTipisuperficie> getEntityClass() {

	return CcTipisuperficie.class;
    }

    @Override
    public List<CcTipisuperficie> findAll(Integer firstResult, Integer maxResult) {

	return cctipisuperficieDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcTipisuperficie entity) {

	if (validateEntity(entity)) {
	    cctipisuperficieDAO.insert(entity);
	}
    }

    @Override
    public CcTipisuperficie findById(PkId id) {

	return cctipisuperficieDAO.findById(id);
    }

    @Override
    public void update(CcTipisuperficie entity) {

	if (validateEntity(entity)) {
	    cctipisuperficieDAO.update(entity);
	}
    }

    @Override
    public void delete(CcTipisuperficie entity) {

	if (isDeleteAllowed(entity)) {
	    cctipisuperficieDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcTipisuperficie entity) {

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
