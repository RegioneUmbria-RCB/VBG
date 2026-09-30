package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcDettaglisuperficieDAO;
import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcDettaglisuperficieService;

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
public class CcDettaglisuperficieServiceImpl extends BaseServiceImpl<CcDettaglisuperficie, PkId> implements CcDettaglisuperficieService {

    private CcDettaglisuperficieDAO ccdettaglisuperficieDAO;

    @Autowired
    public void setCcDettaglisuperficieDAO(CcDettaglisuperficieDAO ccdettaglisuperficieDAO) {

	this.ccdettaglisuperficieDAO = ccdettaglisuperficieDAO;
    }

    @Override
    protected Class<CcDettaglisuperficie> getEntityClass() {

	return CcDettaglisuperficie.class;
    }

    @Override
    public List<CcDettaglisuperficie> findAll(Integer firstResult, Integer maxResult) {

	return ccdettaglisuperficieDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcDettaglisuperficie entity) {

	if (validateEntity(entity)) {
	    ccdettaglisuperficieDAO.insert(entity);
	}
    }

    @Override
    public CcDettaglisuperficie findById(PkId id) {

	return ccdettaglisuperficieDAO.findById(id);
    }

    @Override
    public void update(CcDettaglisuperficie entity) {

	if (validateEntity(entity)) {
	    ccdettaglisuperficieDAO.update(entity);
	}
    }

    @Override
    public void delete(CcDettaglisuperficie entity) {

	if (isDeleteAllowed(entity)) {
	    ccdettaglisuperficieDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcDettaglisuperficie entity) {

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
