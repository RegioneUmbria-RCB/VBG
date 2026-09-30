package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OTabelladDAO;
import it.gruppoinit.pal.gp.core.domain.OTabellad;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OTabelladService;

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
public class OTabelladServiceImpl extends BaseServiceImpl<OTabellad, PkId> implements OTabelladService {

    private OTabelladDAO otabelladDAO;

    @Autowired
    public void setOTabelladDAO(OTabelladDAO otabelladDAO) {

	this.otabelladDAO = otabelladDAO;
    }

    @Override
    protected Class<OTabellad> getEntityClass() {

	return OTabellad.class;
    }

    @Override
    public List<OTabellad> findAll(Integer firstResult, Integer maxResult) {

	return otabelladDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OTabellad entity) {

	if (validateEntity(entity)) {
	    otabelladDAO.insert(entity);
	}
    }

    @Override
    public OTabellad findById(PkId id) {

	return otabelladDAO.findById(id);
    }

    @Override
    public void update(OTabellad entity) {

	if (validateEntity(entity)) {
	    otabelladDAO.update(entity);
	}
    }

    @Override
    public void delete(OTabellad entity) {

	if (isDeleteAllowed(entity)) {
	    otabelladDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OTabellad entity) {

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
