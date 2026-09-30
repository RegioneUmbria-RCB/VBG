package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcTabellaCaratteristDAO;
import it.gruppoinit.pal.gp.core.domain.CcTabellaCaratterist;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcTabellaCaratteristService;

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
public class CcTabellaCaratteristServiceImpl extends BaseServiceImpl<CcTabellaCaratterist, PkId> implements CcTabellaCaratteristService {

    private CcTabellaCaratteristDAO cctabellacaratteristDAO;

    @Autowired
    public void setCcTabellaCaratteristDAO(CcTabellaCaratteristDAO cctabellacaratteristDAO) {

	this.cctabellacaratteristDAO = cctabellacaratteristDAO;
    }

    @Override
    protected Class<CcTabellaCaratterist> getEntityClass() {

	return CcTabellaCaratterist.class;
    }

    @Override
    public List<CcTabellaCaratterist> findAll(Integer firstResult, Integer maxResult) {

	return cctabellacaratteristDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcTabellaCaratterist entity) {

	if (validateEntity(entity)) {
	    cctabellacaratteristDAO.insert(entity);
	}
    }

    @Override
    public CcTabellaCaratterist findById(PkId id) {

	return cctabellacaratteristDAO.findById(id);
    }

    @Override
    public void update(CcTabellaCaratterist entity) {

	if (validateEntity(entity)) {
	    cctabellacaratteristDAO.update(entity);
	}
    }

    @Override
    public void delete(CcTabellaCaratterist entity) {

	if (isDeleteAllowed(entity)) {
	    cctabellacaratteristDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcTabellaCaratterist entity) {

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
